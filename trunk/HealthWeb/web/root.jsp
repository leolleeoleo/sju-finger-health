<%-- 
    Document   : root
    Created on : 2012/7/2, 下午 09:54:25
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <link href="./css/base.css" rel="stylesheet" type="text/css" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <body class="ColFixLtHdr">

        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="menu.html" %>
            <div id="mainContent">
                <h1>管理</h1>
                <form method="post" action="manage?action=manage">
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
            <%@ include file="copyright.html" %>
        </div>
    </body>
</html>