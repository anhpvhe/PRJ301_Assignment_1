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
import model.Student;

/**
 *
 * @author ACER
 */
public class GroupDBContext extends DBContext<Group> {

    @Override
    public void insert(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Group get(int id) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        String sql = "select g.gid, g.gname, g.cid, c.cname  from [Group] g JOIN Course c on g.cid = c.cid where g.gid = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                Group group = new Group();
                group.setId(id);
                group.setName(rs.getString("gname"));
                Course course = new Course();
                course.setId(rs.getInt("cid"));
                course.setName(rs.getString("cname"));
                group.setCourse(course);
                return group;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupDBContext.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();

            } catch (SQLException ex) {
                Logger.getLogger(GroupDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();

            } catch (SQLException ex) {
                Logger.getLogger(GroupDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();

            } catch (SQLException ex) {
                Logger.getLogger(GroupDBContext.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public ArrayList<Group> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Group> getGroupsFromStudent(String sid) { //get list of groups by student id
        ArrayList<Group> groups = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "select g.gid, g.gname, g.cid, c.cname, s.sid, s.sname  \n"
                    + "from [Group] g \n"
                    + "INNER JOIN Student_Group sg on sg.gid = g.gid\n"
                    + "INNER JOIN Student s on s.sid=sg.sid\n"
                    + "INNER JOIN Course c on g.cid = c.cid \n"
                    + "where s.sid = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, sid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Group g = new Group();
                Course course = new Course();
                course.setId(rs.getInt("cid"));
                course.setName(rs.getString("cname"));
                g.setCourse(course);
                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                groups.add(g);
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
        return groups;
    }

    public ArrayList<Group> getGroupsFromLecturer(String lid) { //get list of groups by lecturer id
        ArrayList<Group> groups = new ArrayList<>();
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT DISTINCT  g.gid,g.gname,c.cid,c.cname\n"
                    + "FROM [Session] ses INNER JOIN [Group] g ON ses.gid = g.gid	\n"
                    + "INNER JOIN Course c ON c.cid = g.cid		\n"
                    + "         WHERE ses.lid = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, lid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Group g = new Group();
                Course course = new Course();
                course.setId(rs.getInt("cid"));
                course.setName(rs.getString("cname"));
                g.setCourse(course);
                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                groups.add(g);
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
        return groups;
    }

}
