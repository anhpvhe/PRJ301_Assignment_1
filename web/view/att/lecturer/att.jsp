<%-- 
    Document   : att
    Created on : Mar 20, 2023, 3:42:37 PM
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
        <form action="logout" method="POST">
            <button type="submit">Log Out</button>
        </form>
        <form action="takeattend" method="POST">
            <table border="1px">
                <tr>
                    <td>Seq</td>
                    <td>Id</td>
                    <td>Name</td>
                    <td>Absent/Present</td>
                    <td>Description</td>
                </tr>
                <c:forEach items="${requestScope.atts}" var="a" varStatus="loop">
                    <tr>
                        <td>${loop.index}</td>
                        <td>${a.student.id}
                            <input type="hidden" name="sid" value="${a.student.id}" />
                            <input type="hidden" name="aid${a.student.id}" value="${a.id}" />
                        </td>
                        <td>${a.student.name}</td>
                        <td><input type="radio"
                                   <c:if test="${!a.status}">
                            checked="checked"
                            </c:if>
                            name="status${a.student.id}" value="absent"/>Absent
                            <input type="radio"
                                   <c:if test="${a.status}">
                            checked="checked"
                            </c:if>
                            name="status${a.student.id}" value="present"/>Present
                        </td>
                        <td><input type="text" value="${a.description}" name="description${a.student.id}"/></td>
                    </tr>
                </c:forEach>
            </table>
            <input type="hidden" name="sessionid" value="${param.id}"/>
            <input type="submit" value="Save"/>            
        </form>
            <a href="timetable?lid=${sessionScope.account.person_id}"><button>Go back</button></a>

    </body>
</html>
