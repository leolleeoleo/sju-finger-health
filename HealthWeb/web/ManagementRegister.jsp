<%-- 
    Document   : climb
    Created on : 2012/6/29, 下午 11:59:24
    Author     : Leo
--%>

<%@page import="edu.sju.ee98.health.sql.Register"%>
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
                <h1>建立/修改帳號</h1>
                <jsp:useBean id="register" class="edu.sju.ee98.health.web.beans.RegisterBean" scope="request"/>
                <jsp:setProperty name="register" property="*"/>
                <% String modify;%>
                <% if ((modify = request.getParameter("modify")) != null && modify.equals("submit")) {%>
                <jsp:getProperty name="register" property="modify"/>
                <% }%>
                <form method="post" action="S_Member?action=ManagementRegister&modify=submit">
                    <p>
                        編號：<input type="text" name="rid" value="<jsp:getProperty name="register" property="rid"/>"/>
                    </p>
                    <p>
                        帳號:<input type="text" name="account" value="<jsp:getProperty name="register" property="account"/>"/>
                        密碼:<input type="password" name="password" value="<jsp:getProperty name="register" property="password"/>"/>
                    </p>
                    <p>
                        地區:<input type="text" name="region" value="<jsp:getProperty name="register" property="region"/>"/>
                        名稱:<input type="text" name="name" value="<jsp:getProperty name="register" property="name"/>"/>
                    </p>
                    <p><input type="reset" value="重設"/><input type="submit" value="送出"/></p>
                </form>
                <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
                <h1>登入站列表</h1>
                <table id="mainContent">
                    <%
                        ArrayList<Table> list = Manager.SQL().listRegister();
                        for (int i = 0; i < list.size(); i++) {
                            Register r = (Register) list.get(i);
                    %>
                    <tr>
                        <td><%=r.getRID()%></td>
                        <td><%=r.getACCOUNT() %></td>
                        <td><%=r.getREGION()%></td>
                        <td><%=r.getNAME()%></td>
                        <td>
                            <form method="post" action="S_Member?action=ManagementRegister&registerByid=<%=r.getRID()%>">
                                <input type="submit" value="編輯"/>
                            </form>
                        </td>
                    </tr>
                    <%}%>
                </table>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>