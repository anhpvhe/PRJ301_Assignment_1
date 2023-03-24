/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author ACER
 */
public class DateTimeHelper {
    public static ArrayList<java.sql.Date> getListDates(java.sql.Date from, java.sql.Date to) {
        ArrayList<java.sql.Date> dates = new ArrayList<>();
        java.sql.Date loop = from;
        while(loop.compareTo(to) <= 0)
        {
            dates.add(loop);
            java.util.Date d = convertSqlToUtilDate(loop);
            d = addDays(d, 1);
            d = keepOnlyDatePart(d);
            loop = convertUtilToSqlDate(d);
        }
        return dates;
    }
    
    public static java.util.Date addDays(java.util.Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static java.sql.Date convertUtilToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static java.util.Date convertSqlToUtilDate(java.sql.Date sqlDate) {
        return new java.util.Date(sqlDate.getTime());
    }

    public static java.util.Date keepOnlyDatePart(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        return formattedDate;
    }
    
    public static String getFirstDayOfWeek(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDate firstDayOfWeek = date.with(DayOfWeek.MONDAY);
        return firstDayOfWeek.format(formatter);
    }
    
    public static String getLastDayOfWeek(String dateString) { //any day in the week
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        LocalDate lastDayOfWeek = date.with(DayOfWeek.SUNDAY);
        return lastDayOfWeek.format(formatter);
    }
    
    public static String getDate7DaysAgo(String inputDate) {
        // Convert input date string to LocalDate object
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Calculate the date 7 days ago
        LocalDate date7DaysAgo = date.minusDays(7);
        
        // Format the date as a string and return it
        return date7DaysAgo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
    public static String getDate7DaysAhead(String inputDate) {
        // Convert input date string to LocalDate object
        LocalDate date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Calculate the date 7 days ahead
        LocalDate date7DaysAhead = date.plusDays(7);
        
        // Format the date as a string and return it
        return date7DaysAhead.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
//    public static String getLastDayOfWeek(String dateString) { //input Monday here
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate date = LocalDate.parse(dateString, formatter);
//        LocalDate lastDayOfWeek = date.with(DayOfWeek.SUNDAY).plusDays(6);
//        return lastDayOfWeek.format(formatter);
//    }
    
}
