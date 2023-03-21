/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import dal.AccountDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Account;
import model.permission.Permission;

/**
 *
 * @author ACER
 */
public class LoginController extends HttpServlet{
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AccountDBContext db = new AccountDBContext();
        Account account = db.get(username, password);
        if(account != null)
        {           
            ArrayList<Permission> permissions = account.getPermissions();
            request.getSession().setAttribute("permissions", permissions);
            request.getSession().setAttribute("account", account);
//            response.getWriter().println("login successful!");
        request.getRequestDispatcher("view/authentication/login_success.jsp").forward(request, response);
        }
        else
        {
//            response.getWriter().println("login failed!");
            request.getRequestDispatcher("view/authentication/login_failed.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("view/authentication/login.jsp").forward(request, response);
    } 
    
}
