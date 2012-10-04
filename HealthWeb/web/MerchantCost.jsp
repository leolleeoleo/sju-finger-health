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
            <%@ include file="MerchantList.html" %>
            <div id="mainContent">
                <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
                <h1>消費扣點</h1>
                <jsp:plugin type="applet" code="edu.sju.ee98.health.applet.MerchantExp" 
                            codebase="applet" archive="HealthApplet.jar, jssc.jar, FingerModule.jar" 
                            width="300" height="200">
                    <jsp:params>
                        <jsp:param name="account" value="<%= user.getUser().getACCOUNT()%>" />
                        <jsp:param name="password" value="<%= user.getUser().getPASSWORD()%>" />
                        <jsp:param name="host" value="health.servehttp.com" />
                        <jsp:param name="port" value="1201" />
                    </jsp:params>
                    <jsp:fallback>Plugin tag OBJECT or EMBED not supported by browser.</jsp:fallback>
                </jsp:plugin>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>