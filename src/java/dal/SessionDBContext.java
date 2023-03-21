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
import model.Course;
import model.Group;
import model.Lecturer;
import model.Room;
import model.Session;
import model.TimeSlot;

/**
 *
 * @author ACER
 */
public class SessionDBContext extends DBContext<Session>{

    @Override
    public void insert(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Session model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Session get(int id) {
        Session session = new Session();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT ses.sessionid,ses.date,ses.status,ses.lid,g.gid,g.gname,c.cid,c.cname,r.rid,r.rname,t.tid,t.description\n" +
"FROM [Session] ses INNER JOIN [Group] g ON ses.gid = g.gid	\n" +
"INNER JOIN Course c ON c.cid = g.cid		\n" +
"INNER JOIN Room r ON r.rid = ses.rid	\n" +
"INNER JOIN TimeSlot t ON t.tid = ses.tid\n" +
"         WHERE ses.sessionid = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                session.setId(id);
                session.setDate(rs.getDate("date"));
                session.setStatus(rs.getBoolean("status"));
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return session;
    }

    @Override
    public ArrayList<Session> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
 
    public ArrayList<Session> getSessionsFromGroup(int gid) {
        ArrayList<Session> sessions = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT ses.sessionid,ses.date,ses.status,ses.lid,g.gid,g.gname,c.cid,c.cname,r.rid,r.rname,t.tid,t.description\n" +
"FROM [Session] ses INNER JOIN [Group] g ON ses.gid = g.gid	\n" +
"INNER JOIN Course c ON c.cid = g.cid		\n" +
"INNER JOIN Room r ON r.rid = ses.rid	\n" +
"INNER JOIN TimeSlot t ON t.tid = ses.tid\n" +
"         WHERE g.gid =?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, gid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Session session = new Session();
                session.setId(rs.getInt("sessionid"));
                session.setDate(rs.getDate("date"));
                session.setStatus(rs.getBoolean("status"));
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
                sessions.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sessions;
    }
    
    public int countSessionFromGroup(int gid) {
        int count = 0;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "select count(ses.sessionid) as countSessions, ses.gid, g.cid\n" +
"	from Session ses \n" +
"	join [Group] g on ses.gid = g.gid\n" +
"	where g.gid = ?\n" +
"	group by ses.gid, g.cid ";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, gid);
            rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countSessions");
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return count;
    }
    
    public ArrayList<Session> getSessionsByCourse(int cid) {
        ArrayList<Session> sessions = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT ses.sessionid,ses.date,ses.status,ses.lid,g.gid,g.gname,c.cid,c.cname,r.rid,r.rname,t.tid,t.description\n" +
"FROM [Session] ses INNER JOIN [Group] g ON ses.gid = g.gid	\n" +
"INNER JOIN Course c ON c.cid = g.cid		\n" +
"INNER JOIN Room r ON r.rid = ses.rid	\n" +
"INNER JOIN TimeSlot t ON t.tid = ses.tid\n" +
"         WHERE c.cid =?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, cid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Session session = new Session();
                session.setId(rs.getInt("sessionid"));
                session.setDate(rs.getDate("date"));
                session.setStatus(rs.getBoolean("status"));
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
                sessions.add(session);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                stm.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return sessions;
    }
    
}
