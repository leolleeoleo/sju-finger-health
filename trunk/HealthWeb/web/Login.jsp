<%-- 
    Document   : index
    Created on : 2012/2/29, 上午 06:04:16
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="./css/base.css" rel="stylesheet" type="text/css" />
    </head>

    <body class="ColFixLtHdr">
        <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="MenuList.html" %>
            <div id="mainContent">
                <h1>登入</h1>
                <%=user.getInfo() == null ? "" : user.getInfo()%>
                <form method="post" action="S_Member?action=MemberIndex">
                    <p>
                        <label>帳號:
                            <input type="text" name="account"/>
                        </label>
                    </p>
                    <p>
                        <label>密碼:
                            <input type="password" name="password"/>
                        </label>
                    </p>
                    <p>
                        <label>
                            <input type="submit" value="送出" />
                        </label>
                    </p>
                </form>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>