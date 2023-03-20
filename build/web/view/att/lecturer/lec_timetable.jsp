<%-- 
    Document   : timetable
    Created on : Mar 20, 2023, 3:42:43 PM
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
        <table border="1px">
            <tr>
                <td></td>
            <c:forEach items="${requestScope.dates}" var="d">
                    <td>${d} <br/>
                    <fmt:formatDate value="${d}" pattern="EEEE" />

                    </td>
                </c:forEach>
            <tr/>
            <c:forEach items="${requestScope.slots}" var="s">
                <tr>
                    <td>${s.description}</td>
                    <c:forEach items="${requestScope.dates}" var="d">
                        <td>
                            <c:forEach items="${requestScope.sessions}" var="ses">
                                <c:if test="${ses.slot.id eq s.id and ses.date eq d}">
                                    ${ses.group.name}-${ses.group.course.name} <br/>
                                    ${ses.room.name} 
                                    -
                                    <c:if test="${ses.status}">-(attended)</c:if>
                                    <c:if test="${!ses.status}">-
                                        <a href="takeattend?id=${ses.id}">take attend</a>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                        </td>
                    </c:forEach>
                <tr/> 
            </c:forEach>
        </table>
    </body>
</html>
