/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.att.student;

import controller.authentication.AuthorizationController;
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
import model.Attendance;
import model.TimeSlot;
import util.DateTimeHelper;

/**
 *
 * @author ACER
 */
public class StudentAttendanceController extends HttpServlet{
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthorizationController check = new AuthorizationController();
        ArrayList<String> permit_list = new ArrayList<>();
        permit_list.add("1");
        permit_list.add("2");
        permit_list.add("4");
        if (!check.isAuthenticated(request)) {
            request.getRequestDispatcher("/view/authentication/unauthenticated.jsp").forward(request, response);
        } else {
            if (check.isAuthorized(request, permit_list)) {
                int gid = Integer.parseInt(request.getParameter("gid"));
                Account acc = (Account)request.getSession().getAttribute("account");
                String student_id = acc.getPerson_id();
                StudentDBContext stuDB = new StudentDBContext();
                ArrayList<Attendance> atts = stuDB.getAttsBySID(student_id, gid);
                request.setAttribute("atts", atts);
                int totalSes = stuDB.countSessionFromGroup(gid); //total number of sessions
                int presentSes = stuDB.countSessionAttended(student_id, gid); //attended sessions
                int absentSes = stuDB.countSessionAbsent(student_id, gid); //absent sessions
                double percentage = ((double) absentSes / presentSes) * 100;
                request.setAttribute("percentage", percentage); //double type
                request.setAttribute("absentSes", absentSes);
                request.setAttribute("presentSes", presentSes);
                request.setAttribute("totalSes", totalSes);
                request.getRequestDispatcher("../view/att/student/stu_attendance.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/view/authentication/unauthorized.jsp").forward(request, response);
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
