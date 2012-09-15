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
                <h1>建立/修改帳號</h1>
                <jsp:useBean id="account" class="edu.sju.ee98.health.web.beans.AccountBean" scope="request"/>
                <jsp:setProperty name="account" property="*"/>
                <% String register;%>
                <% if ((register = request.getParameter("register")) != null && register.equals("submit")) {%>
                <jsp:getProperty name="account" property="register"/>
                <% }%>
                <form method="post" action="S_Member?action=ManagementAccount&register=submit">
                    <p>
                        身分證統一編號：<input type="text" name="uid" value="<jsp:getProperty name="account" property="uid"/>"/>
                        群組：<input type="text" name="gid" value="<jsp:getProperty name="account" property="gid"/>"/>
                    </p>
                    <p>
                        帳號:<input type="text" name="account" value="<jsp:getProperty name="account" property="account"/>"/>
                        密碼:<input type="password" name="password" value="<jsp:getProperty name="account" property="password"/>"/>
                    </p>
                    <p>
                        姓:<input type="text" name="lastName" value="<jsp:getProperty name="account" property="lastName"/>"/>
                        名:<input type="text" name="firstName" value="<jsp:getProperty name="account" property="firstName"/>"/>
                    </p>
                    <p>
                        生日:<input type="text" name="birthday" value="<jsp:getProperty name="account" property="birthday"/>"/>
                        地址:<input type="text" name="address" value="<jsp:getProperty name="account" property="address"/>"/>
                    </p>
                    <p>
                        電子郵件:<input type="text" name="email" value="<jsp:getProperty name="account" property="email"/>"/>
                        電話:<input type="text" name="phone" value="<jsp:getProperty name="account" property="phone"/>"/>
                    </p>
                    <p><input type="reset" value="重設"/><input type="submit" value="送出"/></p>
                </form>
                <h1>帳號列表</h1>
                <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
                <table id="mainContent">
                    <%
                        ArrayList<Table> list = Manager.SQL().listUser();
                        for (int i = 0; i < list.size(); i++) {
                            User u = (User) list.get(i);
                    %>
                    <tr>
                        <td><%=u.getUID()%></td>
                        <td><%=u.getGROUP()%></td>
                        <td><%=u.getLAST_NAME() + u.getFIRST_NAME()%></td>
                        <td><%=u.getBIRTHDAY()%></td>
                        <td><%=u.getADDRESS()%></td>
                        <td><%=u.getEmail()%></td>
                        <td><%=u.getPHONE()%></td>
                        <td>
                            <form method="post" action="S_Member?action=ManagementAccount&userByid=<%=u.getUID()%>">
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