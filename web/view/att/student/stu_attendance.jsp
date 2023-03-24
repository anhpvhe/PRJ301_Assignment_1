<%-- 
    Document   : stu_attendance
    Created on : Mar 24, 2023, 3:50:08 AM
    Author     : ACER
--%>


<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <table>
            <tr>
                <td>No</td>
                <td>Date</td>
                <td>Time Slot</td>
                <td>Room</td>
                <td>Lecturer</td>
                <td>Group Name</td>
                <td>Attendance Status</td>
                <td>Lecturer's Comment</td>
            </tr>
            <c:forEach items="${requestScope.atts}" var="att" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>
                        <fmt:formatDate value="${att.session.date}" pattern="EEEE dd/MM/yyyy"/>
                    </td>
                    <td>${att.session.timeslot.id} - ${att.session.timeslot.description} </td>
                    <td>${att.session.room.name}</td>
                    <td>${att.session.lecturer.name}</td>
                    <td>${att.session.group.name}</td>
                    <td>
                <c:if test="${att.status}"> Present
                    Present
                </c:if>
                <c:if test="${!att.status && att.session.status != 1}">
                    <p>Future</p>
                </c:if>
                <c:if test="${!att.status && att.session.status == 1}">
                    <p>Absent</p>
                </c:if>
                    </td>
                <td>${att.comment}</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="10">
                    <p style="text-transform: uppercase; font-weight: bold"> Absent: ${requestScope.percentage} %  
            Absent so far (${requestScope.absentSes} absent on ${requestScope.totalSes} total).
        </p>
    </td>
</tr>
</table>
</body>
</html>
