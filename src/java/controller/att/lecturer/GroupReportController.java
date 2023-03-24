/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.att.lecturer;

import controller.authentication.AuthorizationController;
import dal.SessionDBContext;
import dal.StudentDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import model.Account;
import model.Attendance;
import model.Session;
import model.Student;

/**
 *
 * @author ACER
 */
public class GroupReportController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthorizationController check = new AuthorizationController();
        ArrayList<String> permit_list = new ArrayList<>();
        permit_list.add("1");
        permit_list.add("2");
        permit_list.add("3");
        if (!check.isAuthenticated(request)) {
            request.getRequestDispatcher("/view/authentication/unauthenticated.jsp").forward(request, response);
        } else {
            if (check.isAuthorized(request, permit_list)) {
                                int gid;
                if (request.getParameter("gid") == null) {
                     gid = 2;
                } else {
                     gid = Integer.parseInt(request.getParameter("gid"));
                    System.out.println("gid is " + gid);
                }
                Account acc = (Account) request.getSession().getAttribute("account");
                String lecturer_id = acc.getPerson_id();
                SessionDBContext sesDB = new SessionDBContext();
                ArrayList<Session> sessions = sesDB.getSessionsFromGroup(gid);
                request.setAttribute("sessions", sessions);
                StudentDBContext stuDB = new StudentDBContext();
                ArrayList<Student> students = stuDB.getStudentsFromGroup(gid);
                for (Student student : students) {
                    System.out.println(student);
                }
                HashMap<String, Double> percentage = new HashMap<String, Double>();
                for (Student student : students) {
                    ArrayList<Attendance> atts = new StudentDBContext().getAttsBySID(student.getId(), gid);
                    student.setAtts(atts);
                    int presentSes = new StudentDBContext().countSessionAttended(student.getId(), gid); //attended sessions
                    System.out.println(percentage);
                    int absentSes = new StudentDBContext().countSessionAbsent(student.getId(), gid); //absent sessions
                    System.out.println(absentSes);
                    double absent = ((double) absentSes / presentSes) * 100;
                    System.out.println(absent);
                    percentage.put(student.getId(), absent);
                }
                request.setAttribute("students", students);
                int totalSes = new StudentDBContext().countSessionFromGroup(gid); //total number of sessions
                request.setAttribute("totalSes", totalSes);
                int totalStudents = students.size();
                request.setAttribute("totalStudents", totalStudents);
                request.setAttribute("percentage", percentage);

                request.getRequestDispatcher("/view/att/lecturer/lec_groupreport.jsp").forward(request, response);
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
