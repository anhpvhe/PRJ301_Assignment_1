<%-- 
    Document   : lec_group
    Created on : Mar 24, 2023, 4:17:47 AM
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
        <h1>Hello ${sessionScope.account.person_id}!!</h1>
        <table>
            <tr>
                <td>No</td>
                <td>Group name</td>
                <td>Course ID</td>
                <td>Course Name</td>
                <td>View report</td>
            </tr>
            <c:forEach items="${requestScope.groups}" var="group" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${group.name}</td>
                    <td>${group.course.id}</td>
                    <td>${group.course.name}</td>
                    <td>
                        <a href="groupReport?gid=${group.id}">View attendance report</a>
                        <input type="hidden" name="sid" value="${sessionScope.account.person_id}">
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
