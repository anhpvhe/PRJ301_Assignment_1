/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Attendance;
import model.Course;
import model.Department;
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
public class StudentDBContext extends DBContext<Student> {

    public int countSessionFromGroup(int gid) { // count number of sessinons by gourp id (total number of sessinos)
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
    
    public int countSessionAttended(String sid, int gid) { // count number of sessinons that have attended, search by student id and group id, tag status true
        int count = 0;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT count(ses.sessionid) as countSessions, ses.gid, g.cid\n"
                    + "                    FROM Student s INNER JOIN Student_Group sg ON sg.sid = s.sid\n"
                    + "                                 INNER JOIN [Group] g ON g.gid = sg.gid\n"
                    + "                                 INNER JOIN [Session] ses ON ses.gid = g.gid\n"
                    + "                                  LEFT JOIN [Attendance] a ON a.sid = s.sid AND a.sessionid = ses.sessionid\n"
                    + "                  		   INNER JOIN Course c ON c.cid = g.cid		\n"
                    + "                  			   INNER JOIN Room r ON r.rid = ses.rid\n"
                    + "                                  INNER JOIN TimeSlot t ON t.tid = ses.tid\n"
                    + "                  		   INNER JOIN Lecturer l ON l.lid=ses.lid\n"
                    + "                   WHERE s.sid= ? and g.gid = ? and ses.status = 1 and a.status = 1\n"
                    + "				   group by ses.gid, g.cid";
            stm = connection.prepareStatement(sql);
            stm.setString(1, sid);
            stm.setInt(2, gid);
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
    
    public int countSessionAbsent(String sid, int gid) { // count number of sessinons that have been absent, search by student id and group id, tag status false
        int count = 0;
        PreparedStatement stm = null; //thi nghiem
        ResultSet rs = null;
        try {
            String sql = "SELECT count(ses.sessionid) as countSessions, ses.gid, g.cid\n"
                    + "                    FROM Student s INNER JOIN Student_Group sg ON sg.sid = s.sid\n"
                    + "                                 INNER JOIN [Group] g ON g.gid = sg.gid\n"
                    + "                                 INNER JOIN [Session] ses ON ses.gid = g.gid\n"
                    + "                                  LEFT JOIN [Attendance] a ON a.sid = s.sid AND a.sessionid = ses.sessionid\n"
                    + "                  		   INNER JOIN Course c ON c.cid = g.cid		\n"
                    + "                  			   INNER JOIN Room r ON r.rid = ses.rid\n"
                    + "                                  INNER JOIN TimeSlot t ON t.tid = ses.tid\n"
                    + "                  		   INNER JOIN Lecturer l ON l.lid=ses.lid\n"
                    + "                   WHERE s.sid= ? and g.gid = ? and ses.status = 1 and a.status = 0\n"
                    + "				   group by ses.gid, g.cid";
            stm = connection.prepareStatement(sql);
            stm.setString(1, sid);
            stm.setInt(2, gid);
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

    public int countStudentInGroup(int gid) { //count number of students in group
        int count = 0;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "select g.gid, g.gname, g.cid, count(s.sid) as countStudent\n"
                    + "	from Student s join Student_Group sg on s.sid = sg.sid\n"
                    + "	join [Group] g on g.gid = sg.gid\n"
                    + "	where g.gid = ?\n"
                    + "	group by g.gid, g.gname, g.cid";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, gid);
            rs = stm.executeQuery();
            while (rs.next()) {
                count = rs.getInt("countStudent");
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

    public ArrayList<Student> getStudentsFromGroup(int gid) { //get lists of students from group id
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "select g.gid, g.gname, g.cid, s.sid, s.sname\n"
                    + "	from Student s join Student_Group sg on s.sid = sg.sid\n"
                    + "	join [Group] g on g.gid = sg.gid\n"
                    + "	where g.gid = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, gid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("sid"));
                student.setName(rs.getString("sname"));
                students.add(student);
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
        return students;
    }

    public ArrayList<Student> getStudentsFromSession(int sessionid) { //get lsits of students from session id
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "select ses.sessionid, g.gid, g.gname, g.cid, s.sid, s.sname\n"
                    + "	from Student s \n"
                    + "				join Student_Group sg on s.sid = sg.sid\n"
                    + "				join [Group] g on g.gid = sg.gid\n"
                    + "				join [Session] ses on g.gid = ses.gid\n"
                    + "	where ses.sessionid = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, sessionid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("sid"));
                student.setName(rs.getString("sname"));
                students.add(student);
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
        return students;
    }

    public ArrayList<Attendance> getAttsBySID(String sid, int gid) { //get attendance lists of a student on a subject by his student id and group id 
        ArrayList<Attendance> atts = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT s.sid,s.sname ,ses.sessionid,ses.date,ses.status as [session_status],l.lid,l.lname,g.gid,g.gname,t.tid,c.cid,c.cname,r.rid,r.rname,t.tid,t.description,ISNULL(a.status,0) as [attendance_status], ISNULL(a.description,'') as [att_description],a.attid\n"
                    + "                    FROM Student s INNER JOIN Student_Group sg ON sg.sid = s.sid\n"
                    + "                                 INNER JOIN [Group] g ON g.gid = sg.gid\n"
                    + "                                 INNER JOIN [Session] ses ON ses.gid = g.gid\n"
                    + "                                  LEFT JOIN [Attendance] a ON a.sid = s.sid AND a.sessionid = ses.sessionid\n"
                    + "                  		   INNER JOIN Course c ON c.cid = g.cid		\n"
                    + "                  			   INNER JOIN Room r ON r.rid = ses.rid\n"
                    + "                                  INNER JOIN TimeSlot t ON t.tid = ses.tid\n"
                    + "                  		   INNER JOIN Lecturer l ON l.lid=ses.lid\n"
                    + "                   WHERE s.sid= ? and g.gid = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, sid);
            stm.setInt(2, gid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance();
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
                lecturer.setName((rs.getString("lname")));
                session.setLecturer(lecturer);
                TimeSlot slot = new TimeSlot();
                slot.setId(rs.getInt("tid"));
                slot.setDescription(rs.getString("description"));
                session.setSlot(slot);
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
            System.out.println("loi lsy ra attend");
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

    public Student getTimeTable(String sid, Date from, Date to) { //get the timetable in time range of ? and ? given the student id
        Student student = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT s.sid,s.sname,ses.sessionid,ses.date,ses.status\n"
                    + "	,g.gid,g.gname,c.cid,c.cname,r.rid,r.rname,l.lid,l.lname,t.tid,t.description		\n"
                    + "FROM Student s INNER JOIN [Student_Group]  sg ON s.sid = sg.sid\n"
                    + "						INNER JOIN [Group] g ON g.gid = sg.gid\n"
                    + "						INNER JOIN [Course] c ON g.cid = c.cid\n"
                    + "						INNER JOIN [Session] ses ON g.gid = ses.gid\n"
                    + "						INNER JOIN [TimeSlot] t ON t.tid = ses.tid\n"
                    + "						INNER JOIN [Room] r ON r.rid = ses.rid\n"
                    + "						INNER JOIN [Lecturer] l ON l.lid = ses.lid\n"
                    + "WHERE s.sid = ? AND ses.date >= ? AND ses.date <= ? ORDER BY s.sid,g.gid";
            stm = connection.prepareStatement(sql);
            stm.setString(1, sid);
            stm.setDate(2, from);
            stm.setDate(3, to);
            rs = stm.executeQuery();
            Group currentGroup = new Group();
            currentGroup.setId(-1);
            while (rs.next()) {
                if (student == null) {
                    student = new Student();
                    student.setId(rs.getString("sid"));
                    student.setName(rs.getString("sname"));
                }
                int gid = rs.getInt("gid");
                if (gid != currentGroup.getId()) {
                    currentGroup = new Group();
                    currentGroup.setId(rs.getInt("gid"));
                    currentGroup.setName(rs.getString("gname"));
                    Course c = new Course();
                    c.setId(rs.getInt("cid"));
                    c.setName(rs.getString("cname"));
                    currentGroup.setCourse(c);
                    student.getGroups().add(currentGroup);
                }
                Session ses = new Session();
                ses.setId(rs.getInt("sessionid"));
                ses.setDate(rs.getDate("date"));
                ses.setStatus(rs.getBoolean("status"));
                ses.setGroup(currentGroup);

                Lecturer l = new Lecturer();
                l.setId(rs.getString("lid"));
                l.setName(rs.getString("lname"));
                ses.setLecturer(l);

                Room r = new Room();
                r.setId(rs.getInt("rid"));
                r.setName(rs.getString("rname"));
                ses.setRoom(r);

                TimeSlot t = new TimeSlot();
                t.setId(rs.getInt("tid"));
                t.setDescription(rs.getString("description"));
                ses.setSlot(t);

                currentGroup.getSessions().add(ses);

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
        return student;
    }

    public Student get(String sid) { //get student from student id
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT s.[sid],s.sname,s.gender,s.dob,d.did,d.dname \n"
                    + "FROM Student s INNER JOIN Department d\n"
                    + "ON s.did = d.did WHERE s.[sid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, sid);
            rs = stm.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setId(rs.getString("sid"));
                s.setName(rs.getString("sname"));
                s.setGender(rs.getBoolean("gender"));
                s.setDob(rs.getDate("dob"));

                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                s.setDept(d);
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public ArrayList<Student> searchByIDs(ArrayList<Integer> dids) {
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT s.[sid],s.sname,s.gender,s.dob,d.did,d.dname \n"
                    + "FROM Student s INNER JOIN Department d\n"
                    + "ON s.did = d.did WHERE d.did IN (";
            String params = "";
            for (Integer did : dids) {
                params += "?,";
            }
            params = params.substring(0, params.length() - 1);
            sql += params + ")";
            stm = connection.prepareStatement(sql);
            for (int i = 0; i < dids.size(); i++) {
                stm.setInt(i + 1, dids.get(i));
            }
            rs = stm.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getString("sid"));
                s.setName(rs.getString("sname"));
                s.setGender(rs.getBoolean("gender"));
                s.setDob(rs.getDate("dob"));

                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                s.setDept(d);

                students.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }

    @Override
    public void insert(Student model) {
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO Student(sname,gender,dob,did) VALUES(?,?,?,?)";
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setBoolean(2, model.isGender());
            stm.setDate(3, model.getDob());
            stm.setInt(4, model.getDept().getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Student model) {
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE [Student]\n"
                    + "   SET [sname] = ?\n"
                    + "      ,[gender] = ?\n"
                    + "      ,[dob] = ?\n"
                    + "      ,[did] = ?\n"
                    + " WHERE [sid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getName());
            stm.setBoolean(2, model.isGender());
            stm.setDate(3, model.getDob());
            stm.setInt(4, model.getDept().getId());
            stm.setString(5, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void delete(Student model) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE Student WHERE [sid] = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, model.getId());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Student get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Student> all() {
        ArrayList<Student> students = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT s.[sid],s.sname,s.gender,s.dob,d.did,d.dname \n"
                    + "FROM Student s INNER JOIN Department d\n"
                    + "ON s.did = d.did";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getString("sid"));
                s.setName(rs.getString("sname"));
                s.setGender(rs.getBoolean("gender"));
                s.setDob(rs.getDate("dob"));

                Department d = new Department();
                d.setId(rs.getInt("did"));
                d.setName(rs.getString("dname"));
                s.setDept(d);

                students.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(StudentDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }
    public static void main(String[] args) {
        ArrayList<Attendance> getAttsBySID = new StudentDBContext().getAttsBySID("he170001", 2);
        for (Attendance attendance : getAttsBySID) {
            System.out.println(attendance);
        }
        int num = new  StudentDBContext().countSessionAbsent("he170001",1);
        System.out.println(num);
    }

}
