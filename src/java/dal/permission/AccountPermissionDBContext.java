/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal.permission;

import dal.DBContext;
import dal.StudentDBContext;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.permission.Action;
import model.permission.Permission;

/**
 *
 * @author ACER
 */
public class AccountPermissionDBContext extends DBContext<Permission>{

    public String getUsername(String person_id){
        String sql = "SELECT username FROM Account WHERE person_id = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;
        String username = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, person_id);
            rs = stm.executeQuery();
            if (rs.next()) {              
                username = rs.getString("username");
                return username;
            }
        } catch (SQLException ex) {
        }
        return null;
    }
    
    public Account getPermission(String person_id) {
        String username = getUsername(person_id);
        if(username==null){}
        Account acc = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT acc.username, acc.person_id, per.per_id, per.per_name, act.action_id, act.action_name\n" +
"FROM [Account] acc INNER JOIN [Account_Permission] accper ON acc.username = accper.username	\n" +
"INNER JOIN Permission per ON per.per_id = accper.per_id		\n" +
"INNER JOIN Per_Action peract ON per.per_id = peract.per_id\n" +
"INNER JOIN [Action] act ON act.action_id = peract.action_id\n" +
"         WHERE acc.username = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();

            while (rs.next()) {
                Permission per = new Permission();
                Action act = new Action();
                
                if (acc == null) {
                    acc = new Account();
                    acc.setUsername(rs.getString("username"));
                    acc.setPerson_id(rs.getString("person_id"));
                }
                act.setId(rs.getInt("action_id"));
                act.setName(rs.getString("action_name"));
                per.setId(rs.getInt("per_id"));
                per.setName(rs.getString("per_name"));
                per.getActions().add(act);
                acc.getPermissions().add(per);               
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
        return acc;
    }
    
    @Override
    public void insert(Permission model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Permission model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Permission model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Permission get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Permission> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    
}
