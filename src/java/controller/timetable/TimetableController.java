/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.timetable;

import controller.authentication.AuthorizationController;
import dal.LecturerDBContext;
import dal.TimeSlotDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import model.Session;
import model.TimeSlot;
import model.permission.Permission;
import util.DateTimeHelper;

/**
 *
 * @author ACER
 */
public class TimetableController extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthorizationController check = new AuthorizationController();
        ArrayList<Permission> permit_list = check.permissionList(request);
        if (!check.isAuthenticated(request)) {
            request.setAttribute("invalidSession", true);
            request.getRequestDispatcher("/view/authentication/unauthenticated.jsp").forward(request, response);
        } else {
            for(Permission permit:permit_list){
                if(permit.getId()==1||permit.getId()==2){
                    
                }
            }
            
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
