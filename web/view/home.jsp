<%-- 
    Document   : home
    Created on : Mar 20, 2023, 7:45:33 PM
    Author     : ACER
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello ${sessionScope.account.person_id} </h1>
        <!--        <form action='student/timetable' method="POST">
                    <input type="hidden" name="sid" value="${sessionScope.account.person_id}">
                    <input type='submit' value='View Timetable'/>
                </form>-->
        <form action="logout" method="POST">
            <button type="submit">Log Out</button>
        </form>
        <c:forEach var="permission" items="${sessionScope.permissions}">
            <c:choose>
                <c:when test="${permission.id == 3}">
                    <input type="hidden" name="lid" value="${sessionScope.account.person_id}">
                    <a href="lecturer/timetable?lid=${sessionScope.account.person_id}"><button>View Lecturer Timetable</button></a>
                </c:when>
                <c:when test="${permission.id == 4}">
                    <input type="hidden" name="sid" value="${sessionScope.account.person_id}">
                    <a href="student/timetable?sid=${sessionScope.account.person_id}"><button>View Student Timetable</button></a>
                </c:when>
                <c:when test="${permission.id == 1}">
                    <input type="hidden" name="sid" value="${sessionScope.account.person_id}">
<!--                    <a href="lecturer/timetable?lid=${sessionScope.account.person_id}"><button>View Lecturer Timetable</button></a>-->
                    <a href="student/timetable?sid=${sessionScope.account.person_id}"><button>View Student Timetable</button></a>
                </c:when>
            </c:choose>
        </c:forEach>
    </body>
</html>
