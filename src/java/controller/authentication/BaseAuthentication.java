/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Account;

/**
 *
 * @author ACER
 */
public abstract class BaseAuthentication extends HttpServlet{
    
    private boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("account") != null;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(isAuthenticated(request))
        {
            //do business
            doGet(request, response, (Account)request.getSession().getAttribute("account"));
        }
        else
        {
            response.getWriter().println("access denied!");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(isAuthenticated(request))
        {
            //do business
            doPost(request, response, (Account)request.getSession().getAttribute("account"));
        }
        else
        {
            response.getWriter().println("access denied!");
        }
    }
    
    protected abstract void doGet(HttpServletRequest request, HttpServletResponse response,Account account)
            throws ServletException, IOException;
    protected abstract void doPost(HttpServletRequest request, HttpServletResponse response,Account account)
            throws ServletException, IOException;
    
}
