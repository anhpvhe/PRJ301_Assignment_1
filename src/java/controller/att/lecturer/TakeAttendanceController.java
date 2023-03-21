/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.att.lecturer;

import controller.authentication.AuthorizationController;
import dal.AttendanceDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Attendance;
import model.Student;

/**
 *
 * @author ACER
 */
public class TakeAttendanceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
                AttendanceDBContext db = new AttendanceDBContext();
                ArrayList<Attendance> atts = db.getAttsBySessionID(Integer.parseInt(request.getParameter("id")));
                request.setAttribute("atts", atts);
                request.getRequestDispatcher("../view/att/lecturer/att.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/view/authentication/unauthorized.jsp").forward(request, response);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
                String[] sids = request.getParameterValues("sid");
                int sessionid = Integer.parseInt(request.getParameter("sessionid"));
                ArrayList<Attendance> atts = new ArrayList<>();
                for (String sid : sids) {
                    Student s = new Student();
                    s.setId(sid);
                    Attendance a = new Attendance();
                    a.setId(Integer.parseInt(request.getParameter("aid"+sid)));
                    a.setStudent(s);
                    a.setStatus(request.getParameter("status" + sid).equals("present"));
                    a.setDescription(request.getParameter("description" + sid));
                    atts.add(a);
                }
                AttendanceDBContext db = new AttendanceDBContext();
                db.updateAtts(atts, sessionid);
                response.sendRedirect("takeattend?id=" + sessionid);
            } else {
                request.getRequestDispatcher("/view/authentication/unauthorized.jsp").forward(request, response);
            }
        }

    }

}
