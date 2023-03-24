<%-- 
    Document   : timetable
    Created on : Mar 20, 2023, 3:43:46 PM
    Author     : ACER
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student Timetable</title>
        
    </head>
    <style>
        .body{
            font-family: Arial, sans-serif;
            background-image: url("img/fpt-uni.jpg");
            background-position-y: center;
            background-size: cover;
        }

        .logoutform{
            display: flex;
            justify-content: right;
            align-items: center;
        }
        </style>
    <body>
        <div class="logoutform">
        <form action="../logout" method="POST">
            <button type="submit">Log Out</button>
        </form>
        </div>

        <h2>Timetable for ${sessionScope.account.person_id} </h2> 
        <select name="dateTime">
            <option value="time">${requestScope.currentDate}</option>
        </select>
        <table border="1px"> 
            <tr>
                <td></td>
            <c:forEach items="${requestScope.dates}" var="d">
                <td>${d}<br/><fmt:formatDate value="${d}" pattern="EEEE"/>
                </td>
            </c:forEach>

        </tr>
        <c:forEach items="${requestScope.slots}" var="slot"> 
            <tr>
                <td>${slot.description}</td>
            <c:forEach items="${requestScope.dates}" var="d">
                <td>
                <c:forEach items="${requestScope.s.groups}" var="g">
                    <c:forEach items="${g.sessions}" var="ses">
                        <c:if test="${ses.date eq d and ses.slot.id eq slot.id}">
                            ${g.name}(${g.course.name}) <br/>
                            ${ses.lecturer.name}- At ${ses.room.name} <br/>
                            <c:if test="${ses.status}">
                                <p style="color:green">Present</p>
                            </c:if>
                            <c:if test="${!ses.status}">
                                <p style="color:red">Absent</p>
                            </c:if>    
                        </c:if>
                    </c:forEach>
                </c:forEach>
                </td>
            </c:forEach>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
