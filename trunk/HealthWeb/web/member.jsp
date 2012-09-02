<%-- 
    Document   : member
    Created on : 2012/2/29, 上午 06:04:16
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>首頁</title>
        <link href="./css/base.css" rel="stylesheet" type="text/css" />
    </head>

    <body class="ColFixLtHdr">
        <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>

        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="menu.html" %>
            <%@ include file="member.html" %>
            <%if (user.getUser().getGROUP() == 1) {%><%@ include file="manager.html" %><% }%>
            <div id="mainContent">

                <h1>你好<jsp:getProperty name="user" property="name"/></h1>
                <p></p>
                <h2></h2>
                <p></p>
            </div>
            <%@ include file="copyright.html" %>
        </div>
    </body>
</html>