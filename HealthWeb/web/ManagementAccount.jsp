<%-- 
    Document   : climb
    Created on : 2012/6/29, 下午 11:59:24
    Author     : Leo
--%>

<%@page import="edu.sju.ee98.health.sql.User"%>
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
                <h1>帳號列表</h1>
                <table id="mainContent">
                    <%
                        ArrayList<Table> list = Manager.SQL().listUser();
                        for (int i = 0; i < list.size(); i++) {
                            User u = (User) list.get(i);
                    %><tr>
                        <td><%
                            out.print(u.getUID());
                            %></td>
                        <td><%
                            out.print(u.getGROUP());
                            %></td>
                        <td><%
                            out.print(u.getLAST_NAME());
                            out.print(u.getFIRST_NAME());
                            %></td>
                        <td><%
                            out.print(u.getBIRTHDAY());
                            %></td>
                        <td><%
                            out.print(u.getADDRESS());
                            %></td>
                        <td><%
                            out.print(u.getEmail());
                            %></td>
                        <td><%
                            out.print(u.getPHONE());
                            %></td>
                    </tr><%}%>
                </table>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>