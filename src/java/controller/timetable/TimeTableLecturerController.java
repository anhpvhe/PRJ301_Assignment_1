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
import util.DateTimeHelper;

/**
 *
 * @author ACER
 */
public class TimeTableLecturerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthorizationController check = new AuthorizationController();
        ArrayList<String> permit_list = new ArrayList<>();
        permit_list.add("1");
        permit_list.add("2");
        permit_list.add("3");
        if (!check.isAuthenticated(request)) {
            request.setAttribute("invalidSession", true);
            request.getRequestDispatcher("/view/authentication/unauthenticated.jsp").forward(request, response);
        } else {
            if (check.isAuthorized(request, permit_list)) {
                String lid = request.getParameter("lid");
//                
                String currentDay = DateTimeHelper.getCurrentDate();
                Date from = Date.valueOf(DateTimeHelper.getFirstDayOfWeek(currentDay));
                Date to = Date.valueOf(DateTimeHelper.getLastDayOfWeek(currentDay));
//                Date from = Date.valueOf(request.getParameter("from"));
//                Date to = Date.valueOf(request.getParameter("to"));
                ArrayList<Date> dates = DateTimeHelper.getListDates(from, to);
                TimeSlotDBContext dbSlot = new TimeSlotDBContext();
                ArrayList<TimeSlot> slots = dbSlot.all();
                LecturerDBContext lecDb = new LecturerDBContext();
                ArrayList<Session> sessions = lecDb.getSessions(lid);

                request.setAttribute("slots", slots);
                request.setAttribute("dates", dates);
                request.setAttribute("sessions", sessions);
                request.getRequestDispatcher("../view/att/lecturer/lec_timetable.jsp").forward(request, response);
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
