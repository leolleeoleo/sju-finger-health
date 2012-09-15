<%-- 
    Document   : climb
    Created on : 2012/6/29, 下午 11:59:24
    Author     : Leo
--%>

<%@page import="edu.sju.ee98.health.sql.Fingerprint"%>
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
                <h1>登記指紋</h1>
                <jsp:plugin type="applet" code="edu.sju.ee98.health.applet.UpdateFingerprint" 
                            codebase="applet" archive="HealthApplet.jar, jssc.jar, FingerModule.jar" 
                            width="300" height="200">
                    <jsp:fallback>Plugin tag OBJECT or EMBED not supported by browser.</jsp:fallback>
                </jsp:plugin>
                <h1>指紋列表</h1>
                <table id="mainContent">
                    <%
                        ArrayList<Table> list = Manager.SQL().listFingerprint();
                        for (int i = 0; i < list.size(); i++) {
                            Fingerprint f = (Fingerprint) list.get(i);
                    %>
                    <tr>
                        <td><%= Manager.SQL().getUser(f.getUID()).getNAME()%></td>
                        <td><%= f.getMD5()%></td>
                    </tr>
                    <%}%>
                </table>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>