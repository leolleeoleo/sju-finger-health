<%-- 
    Document   : climb
    Created on : 2012/6/29, 下午 11:59:24
    Author     : Leo
--%>

<%@page import="edu.sju.ee98.health.sql.Miles"%>
<%@page import="edu.sju.ee98.sql.Table"%>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.sju.ee98.health.web.Manager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>首頁</title>
        <link href="./css/base.css" rel="stylesheet" type="text/css" />
    </head>

    <body class="ColFixLtHdr">

        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="MenuList.html" %>
            <%@ include file="MemberList.html" %>
            <%@ include file="ManagementList.html" %>
            <div id="mainContent">
                <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
                <h1>里程列表</h1>
                <table id="mainContent">
                    <%
                        ArrayList<Table> list = Manager.SQL().listMiles();
                        for (int i = 0; i < list.size(); i++) {
                            Miles m = (Miles) list.get(i);
                    %><tr>
                        <td><%
                            out.print(m.getREGISTER_A());
                            %></td>
                        <td><%
                            out.print(m.getREGISTER_B());
                            %></td>
                        <td><%
                            out.print(m.getMETER());
                            %></td>
                    </tr><%}%>
                </table>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>