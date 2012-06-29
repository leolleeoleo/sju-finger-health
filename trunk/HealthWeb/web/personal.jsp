<%-- 
    Document   : personal
    Created on : 2012/6/29, 下午 05:28:57
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
                <h1>個人資料</h1>
                <p>姓名：<jsp:getProperty name="user" property="name"/></p>
                <p>身分證統一編號：<jsp:getProperty name="user" property="id"/></p>
                <p>生日：<jsp:getProperty name="user" property="birth"/></p>
                <p>地址：<jsp:getProperty name="user" property="address"/></p>
                <p>電子郵件：<jsp:getProperty name="user" property="email"/></p>
                <p>電話：<jsp:getProperty name="user" property="phone"/></p>
            </div>
            <%@ include file="copyright.html" %>
        </div>
    </body>
</html>