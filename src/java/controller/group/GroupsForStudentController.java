/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.group;

import controller.authentication.AuthorizationController;
import dal.GroupDBContext;
import dal.StudentDBContext;
import dal.TimeSlotDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import model.Account;
import model.Group;
import model.TimeSlot;
import util.DateTimeHelper;

/**
 *
 * @author ACER
 */
public class GroupsForStudentController extends HttpServlet{
    
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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AuthorizationController check = new AuthorizationController();
        ArrayList<String> permit_list = new ArrayList<>();
        permit_list.add("1");
        permit_list.add("2");
        permit_list.add("4");
        if (!check.isAuthenticated(request)) {
            request.getRequestDispatcher("/view/authentication/unauthenticated.jsp").forward(request, response);
        } else {
            if (check.isAuthorized(request, permit_list)) {
                GroupDBContext groupDB = new GroupDBContext();
                Account acc = (Account)request.getSession().getAttribute("account");
                String student_id = acc.getPerson_id();
                ArrayList<Group> groups = groupDB.getGroupsFromStudent(student_id);
                request.setAttribute("groups", groups);
                request.getRequestDispatcher("../view/att/student/stu_group.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/view/authentication/unauthorized.jsp").forward(request, response);
            }
        }
    }
}
