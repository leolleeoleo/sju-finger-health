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
                <h1>建立/修改里程表</h1>
                <jsp:useBean id="mile" class="edu.sju.ee98.health.web.beans.MileBean" scope="request"/>
                <jsp:setProperty name="mile" property="*"/>
                <% String modify;%>
                <% if ((modify = request.getParameter("modify")) != null && modify.equals("submit")) {%>
                <jsp:getProperty name="mile" property="modify"/>
                <% }%>
                <form method="post" action="S_Member?action=ManagementRegister&modify=submit">
                    <p>
                        編號A：<input type="text" name="ridA" value="<jsp:getProperty name="mile" property="ridA"/>"/>
                        編號B：<input type="text" name="ridB" value="<jsp:getProperty name="mile" property="ridB"/>"/>
                    </p>
                    <p>
                        距離:<input type="text" name="meter" value="<jsp:getProperty name="mile" property="meter"/>"/>
                    </p>
                    <p><input type="reset" value="重設"/><input type="submit" value="送出"/></p>
                </form>
                <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
                <h1>里程列表</h1>
                <table id="mainContent">
                    <%
                        ArrayList<Table> list = Manager.SQL().listMiles();
                        for (int i = 0; i < list.size(); i++) {
                            Miles m = (Miles) list.get(i);
                    %>
                    <tr>
                        <td><%=Manager.SQL().getRegister(m.getREGISTER_A()).getNAME() %></td>
                        <td><%=Manager.SQL().getRegister(m.getREGISTER_B()).getNAME() %></td>
                        <td><%=m.getMETER()%></td>
                        <td>
                            <form method="post" action="S_Member?action=ManagementMiles&MileByid=<%=m.getREGISTER_A()%>">
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