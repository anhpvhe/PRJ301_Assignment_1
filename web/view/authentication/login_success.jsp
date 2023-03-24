<%-- 
    Document   : loginsuccess
    Created on : Mar 20, 2023, 7:30:28 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>FPT Academic Portal</title>
    </head>
    <style>
        .logform{
            margin: 0 auto;
            display: flex;
            background-image: url("img/fpt-uni.jpg");
            justify-content: space-between;
            width: 80%;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0px 2px 5px rgba(0,0,0,0.1);
        }
    </style>
    <body>
        <h1>Login successfully!</h1>
        <div class="logform">
            <form action='home'>
                <input type='submit' value='Back to home page.'/>
            </form>
        </div>
    </body>
</html>
