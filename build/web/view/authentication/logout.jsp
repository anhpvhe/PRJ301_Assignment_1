<%-- 
    Document   : logout
    Created on : Mar 20, 2023, 1:44:01 PM
    Author     : ACER
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log Out Page</title>
    </head>
    <body>
        <h1>Logout successful!</h1>
        <br/>
        You will be directed to /login after <span id="time"></span> seconds
        <script> 
        var count =3;
        var time = document.getElementById('time');
        time.innerHTML = count;
        function counting()
        {
            count --;
            time.innerHTML = count;
            if(count <= 0 )
            {
                window.location.href = 'login';
            }
        }
        setInterval(counting,1000);       
        </script>
    </body>
</html>
