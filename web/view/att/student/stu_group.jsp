<%-- 
    Document   : stu_group
    Created on : Mar 24, 2023, 2:59:37 AM
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
        <h1>Hello ${sessionScope.account.person_id}!</h1>
        <h1 style="color:green">${requestScope.groups.size()} group(s) found for ${sessionScope.account.person_id}</h1>
        <table border="1px">
            <tr class="table-head">
                <td>No</td>
                <td>Group Name</td>
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
                        <a href="viewAttendance?gid=${group.id}">View attendance report</a>
                        <input type="hidden" name="sid" value="${sessionScope.account.person_id}">
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
