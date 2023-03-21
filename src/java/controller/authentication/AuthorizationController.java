/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;


import dal.permission.AccountPermissionDBContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import model.Account;
import model.permission.Permission;

/**
 *
 * @author ACER
 */
public class AuthorizationController extends HttpServlet{
    public boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("account") != null;
    }
    
    public boolean isAuthorized(HttpServletRequest request, ArrayList<String> permit_list){
        Account acc = (Account)request.getSession().getAttribute("account");
        AccountPermissionDBContext accperDB = new AccountPermissionDBContext();
        String person_id = acc.getPerson_id();
        Account acc_permit = accperDB.getPermission(person_id);
        ArrayList<Permission> permissions = acc_permit.getPermissions();
        boolean flag = false;
        for(Permission per : permissions){
            for(String p : permit_list){
                if(per.getId() == Integer.parseInt(p)){
                    flag = true;
                }
            }
        }
        return flag;
    }
    
    public ArrayList<Permission> permissionList(HttpServletRequest request){
        Account acc = (Account)request.getSession().getAttribute("account");
        AccountPermissionDBContext accperDB = new AccountPermissionDBContext();
        String person_id = acc.getPerson_id();
        Account acc_permit = accperDB.getPermission(person_id);
        ArrayList<Permission> permissions = (ArrayList<Permission>)acc_permit.getPermissions();     
        return permissions;
    }
    
}
