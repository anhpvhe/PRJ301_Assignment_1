<%-- 
    Document   : unauthenticated
    Created on : Mar 21, 2023, 2:37:33 AM
    Author     : ACER
--%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%-- set the default value for the url variable --%>
        <c:set var="url" value="login" />
        <%-- check if the invalidSession attribute is true and update the url variable accordingly --%>
        <c:if test="${requestScope.invalidSession eq true}">
            <c:set var="url" value="./login" />
        </c:if>

        <h1>You have not logged in. Please log in to continue.</h1>
        <br/>
        You will be redirected to Login Page after <span id="time"></span> seconds
        <script>
            var count = 3;
            var time = document.getElementById('time');
            time.innerHTML = count;
            function counting()
            {
                count--;
                time.innerHTML = count;
                if (count <= 0)
                {
                    window.location.href = '<%= request.getContextPath() %>/<c:out value="${url}" />';
                            }
                        }
                        setInterval(counting, 1000);
        </script>
    </body>
</html>
