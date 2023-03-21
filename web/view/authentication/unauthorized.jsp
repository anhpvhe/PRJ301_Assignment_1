<%-- 
    Document   : unauthorized
    Created on : Mar 21, 2023, 3:57:03 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>You are unauthorized to access this page.</h1>
        <br/>
        You will be redirected to Home Page after <span id="time"></span> seconds
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
                window.location.href = '../home';
            }
        }
        setInterval(counting,1000);       
        </script>
    </body>
</html>
