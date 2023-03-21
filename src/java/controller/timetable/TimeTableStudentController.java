/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.timetable;

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
import model.TimeSlot;
import util.DateTimeHelper;

/**
 *
 * @author ACER
 */
public class TimeTableStudentController extends HttpServlet {

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
                String sid = request.getParameter("sid");
//                Date from = Date.valueOf(request.getParameter("from"));
//                Date to = Date.valueOf(request.getParameter("to"));
                String currentDay = DateTimeHelper.getCurrentDate();
                Date from = Date.valueOf(DateTimeHelper.getFirstDayOfWeek(currentDay));
                    Date to = Date.valueOf(DateTimeHelper.getLastDayOfWeek(currentDay));
                TimeSlotDBContext timeDB = new TimeSlotDBContext();
                ArrayList<TimeSlot> slots = timeDB.all();
                request.setAttribute("slots", slots);

                ArrayList<Date> dates = DateTimeHelper.getListDates(from, to);
                request.setAttribute("dates", dates);

                StudentDBContext stuDB = new StudentDBContext();
                model.Student student = stuDB.getTimeTable(sid, from, to);
                request.setAttribute("s", student);
                request.getRequestDispatcher("../view/att/student/stu_timetable.jsp").forward(request, response);
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
