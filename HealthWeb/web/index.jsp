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
        <title>首頁</title>
    </head>
    <link href="./css/base.css" rel="stylesheet" type="text/css" />

    <body class="ColFixLtHdr">

        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="MenuList.html" %>
            <div id="mainContent">
                <h1> 主要內容 </h1>
                <p>Lorem ipsum dolor sit.</p>
                <h2>H2 層級標題 </h2>
                <p>Lorem ipsum dolor sit.</p>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>