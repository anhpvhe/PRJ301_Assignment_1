/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendance;
import model.Course;
import model.Group;
import model.Lecturer;
import model.Room;
import model.Session;
import model.Student;
import model.TimeSlot;

/**
 *
 * @author ACER
 */
public class AttendanceDBContext extends DBContext<Attendance> {

    public void updateAtts(ArrayList<Attendance> atts, int sessionid) {
        ArrayList<PreparedStatement> stms = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            //UPDATE Session Record
            String sql_update_session = "UPDATE Session SET status = 1 WHERE sessionid = ?";
            PreparedStatement stm_update_session = connection.prepareStatement(sql_update_session);
            stm_update_session.setInt(1, sessionid);
            stm_update_session.executeUpdate();
            stms.add(stm_update_session);

            //PROCESS Attendace records
            for (Attendance att : atts) {
                if (att.getId() == 0) //INSERT
                {
                    String sql_insert_att = "INSERT INTO [Attendance]\n"
                            + "           ([sid]\n"
                            + "           ,[sessionid]\n"
                            + "           ,[status]\n"
                            + "           ,[description])\n"
                            + "     VALUES\n"
                            + "           (?\n"
                            + "           ,?\n"
                            + "           ,?\n"
                            + "           ,?)";
                    PreparedStatement stm_insert_att = connection.prepareStatement(sql_insert_att);
                    stm_insert_att.setString(1, att.getStudent().getId());
                    stm_insert_att.setInt(2, sessionid);
                    stm_insert_att.setBoolean(3, att.isStatus());
                    stm_insert_att.setString(4, att.getDescription());
                    stm_insert_att.executeUpdate();
                    stms.add(stm_insert_att);

                } else //UPDATE
                {
                    String sql_update_att = "UPDATE Attendance SET status = ?,description = ? WHERE attid = ?";
                    PreparedStatement stm_update_att = connection.prepareStatement(sql_update_att);
                    stm_update_att.setBoolean(1, att.isStatus());
                    stm_update_att.setString(2, att.getDescription());
                    stm_update_att.setInt(3, att.getId());
                    stm_update_att.executeUpdate();
                    stms.add(stm_update_att);
                }
            }

            connection.commit();
        } catch (SQLException ex) {

            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (PreparedStatement stm : stms) {
                try {
                    stm.close();
                } catch (SQLException ex) {
                    Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList<Attendance> getAttsBySessionID(int sessionid) { //get attendance and session info by session id
        ArrayList<Attendance> atts = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT s.sid,s.sname ,ses.sessionid,ses.date,ses.status as [session_status],l.lid,l.lname,g.gid,g.gname,c.cid,c.cname,r.rid,r.rname,t.tid,t.description,ISNULL(a.status,0) as [attendance_status], ISNULL(a.description,'') as [att_description],a.attid\n"
                    + "FROM Student s INNER JOIN Student_Group sg ON sg.sid = s.sid\n"
                    + "               INNER JOIN [Group] g ON g.gid = sg.gid\n"
                    + "               INNER JOIN [Session] ses ON ses.gid = g.gid\n"
                    + "               LEFT JOIN [Attendance] a ON a.sid = s.sid AND a.sessionid = ses.sessionid\n"
                    + "			   INNER JOIN Course c ON c.cid = g.cid		\n"
                    + "			   INNER JOIN Room r ON r.rid = ses.rid	\n"
                    + "               INNER JOIN TimeSlot t ON t.tid = ses.tid\n"
                    + "			   INNER JOIN Lecturer l ON l.lid=ses.lid\n"
                    + "WHERE ses.sessionid = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, sessionid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
                a.setId(rs.getInt("attid"));
                a.setStatus(rs.getBoolean("attendance_status"));
                a.setDescription(rs.getString("att_description"));
                Student s = new Student();
                s.setId(rs.getString("sid"));
                s.setName(rs.getString("sname"));
                a.setStudent(s);
                Session session = new Session();
                session.setId(rs.getInt("sessionid"));
                session.setDate(rs.getDate("date"));
                session.setStatus(rs.getBoolean("session_status"));
                Lecturer lecturer = new Lecturer();
                lecturer.setId((rs.getString("lid")));
                session.setLecturer(lecturer);
                TimeSlot timeSlot = new TimeSlot();
                timeSlot.setId(rs.getInt("slotId"));
                timeSlot.setDescription(rs.getString("description"));
                session.setSlot(timeSlot);
                Room room = new Room();
                room.setId(rs.getInt("rid"));
                room.setName(rs.getString("rname"));
                session.setRoom(room);
                Group group = new Group();
                Course course = new Course();
                course.setId(rs.getInt("cid"));
                course.setName(rs.getString("cname"));
                group.setCourse(course);
                group.setId(rs.getInt("gid"));
                group.setName(rs.getString("gname"));
                session.setGroup(group);
                a.setSession(session);
                atts.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return atts;
    }

    @Override
    public void insert(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Attendance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attendance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Attendance> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
