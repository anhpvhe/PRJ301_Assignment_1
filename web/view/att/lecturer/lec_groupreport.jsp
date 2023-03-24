<%-- 
    Document   : lec_groupreport
    Created on : Mar 24, 2023, 4:58:53 AM
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

        <div style="overflow-x:auto;">
            <table border="1px">
                <c:forEach items="${requestScope.students}" var="student" varStatus="loop1">
                    <tr>
                        <td>No</td>
                        <td>Student Code</td>
                        <td>Student Name</td>
                        <td>Absent</td>
                        <c:forEach items="${requestScope.student.atts}" var="att" varStatus="loop2">
                            <td> S${loop2.index + 1} - R${att.session.room.name}<br/>
                                Lecturer: ${att.session.lecturer.name}<br/>                                    
                                <fmt:formatDate value="${att.session.date}" pattern="dd/MM"></fmt:formatDate>
                                </td>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${requestScope.students}" var="student" varStatus="loop1">
                        <tr>
                            <td>${loop1.index + 1}</td>
                            <td>${student.id}</td>
                            <td>${student.name}</td>
                            <td>

                                ${requestScope.percentage.value}%

                            </td>
                            
                            
                            <c:forEach items="${requestScope.sessions}" var="session" varStatus="loop3">
                                <c:forEach items="${requestScope.student.atts}" var="att" varStatus="loop2">

                                    <td>
                                        <c:if test="${att.session.id eq session.id && att.student.id eq student.id}">
                                            <c:if test="${att.status}">
                                                <p>P</p>
                                            </c:if>
                                            <c:if test="${!att.status && att.session.status}">
                                                <p>A</p>
                                            </c:if>
                                            <c:if test="${!att.status && !att.session.status}">
                                                <p>-</p>
                                            </c:if>
                                        </c:if>
                                    </td>
                                </c:forEach>
                            </c:forEach>
                                    
                                    
                        </c:forEach>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </body>
</html>
