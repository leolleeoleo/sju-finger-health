<%-- 
    Document   : member
    Created on : 2012/2/29, 上午 06:04:16
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>首頁</title>
    </head>

    <body class="ColFixLtHdr">

        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="menu.html" %>
            <%@ include file="member.html" %>
            <div id="mainContent">

                <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
                <h1>你好<jsp:getProperty name="user" property="name"/></h1>
                <p>Lorem ipsum dolor sit.</p>
                <h2>H2 層級標題 </h2>
                <p>Lorem ipsum dolor sit.</p>
            </div>
            <%@ include file="copyright.html" %>
        </div>
    </body>
</html>