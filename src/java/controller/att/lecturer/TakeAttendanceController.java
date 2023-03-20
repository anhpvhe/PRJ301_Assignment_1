/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.att.lecturer;

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
        AttendanceDBContext db = new AttendanceDBContext();
        ArrayList<Attendance> atts = db.getAttsBySessionID(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("atts", atts);
        request.getRequestDispatcher("../view/att/lecturer/att.jsp").forward(request, response);
    } 
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String[] sids = request.getParameterValues("sid");
        int sessionid = Integer.parseInt(request.getParameter("sessionid"));
        ArrayList<Attendance> atts = new ArrayList<>();
        for (String sid : sids) {
            Student s = new Student();
            s.setId(sid);
            Attendance a = new Attendance();
            a.setStudent(s);
            a.setStatus(request.getParameter("status"+sid).equals("present"));
            a.setDescription(request.getParameter("description"+sid));
            atts.add(a);
        }
        AttendanceDBContext db = new AttendanceDBContext();
        db.updateAtts(atts, sessionid);
        response.sendRedirect("takeattend?id="+sessionid);
    }
    
}
