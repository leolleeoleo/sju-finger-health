<%-- 
    Document   : expend
    Created on : 2012/6/30, 下午 02:43:50
    Author     : Leo
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="edu.sju.ee98.health.web.Manager"%>
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
                <h1>消費紀錄</h1>
                <%
                    ArrayList<String> expend = Manager.SQL().selectExpend(user.getUser());
                    for (int i = 0; i < expend.size(); i++) {
                %>
                <p><%out.print(expend.get(i));%></p>
                <%
                    }
                %>
            </div>
            <%@ include file="copyright.html" %>
        </div>
    </body>
</html>