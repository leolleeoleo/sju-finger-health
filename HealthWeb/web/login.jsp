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

        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="menu.html" %>
            <div id="mainContent">
                <h1>登入</h1>                
                <form method="post" action="member?action=member">
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