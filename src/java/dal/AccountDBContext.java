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
import model.Account;
import model.permission.Action;
import model.permission.Permission;

/**
 *
 * @author ACER
 */
public class AccountDBContext extends DBContext<Account> {

    public Account get(String username, String password) {
        String sql = "SELECT acc.username, acc.person_id, per.per_id, per.per_name, act.action_id, act.action_name\n" +
"FROM [Account] acc INNER JOIN [Account_Permission] accper ON acc.username = accper.username	\n" +
"INNER JOIN Permission per ON per.per_id = accper.per_id		\n" +
"INNER JOIN Per_Action peract ON per.per_id = peract.per_id\n" +
"INNER JOIN [Action] act ON act.action_id = peract.action_id\n" +
"         WHERE acc.username =  ? AND [password] = ?"; 
// `1 acc se luon co it nhat 1 permission
        PreparedStatement stm = null;
        ResultSet rs = null;
        Account acc = null;
        Permission per = null;
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                
                Action act = new Action();             
                if (acc == null) {
                    acc = new Account();
                    per = new Permission();
                    acc.setUsername(rs.getString("username"));
                    acc.setPerson_id(rs.getString("person_id"));
                }
                act.setId(rs.getInt("action_id"));
                act.setName(rs.getString("action_name"));
                per.setId(rs.getInt("per_id"));
                per.setName(rs.getString("per_name"));
                per.getActions().add(act);
                acc.getPermissions().add(per);               
                return acc;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                stm.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public void insert(Account model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Account model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Account model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Account get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Account> all() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
