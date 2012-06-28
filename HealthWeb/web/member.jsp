<%-- 
    Document   : member
    Created on : 2012/6/28, 下午 06:18:01
    Author     : Leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@taglib prefix="h" uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<f:view>
    <f:loadBundle basename="edu.sju.ee98.health.web.language.messages" var="msgs"/>
    <html>
        <link href="./css/base.css" rel="stylesheet" type="text/css" />
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title><h:outputText value="#{msgs.title}"/></title>
        </head>

        <body class="ColFixLtHdr">

            <div id="container">
                <div id="header">
                    <h1>會員</h1>
                </div>
                <div id="sidebar1">
                    <h:form>
                        <h:commandLink value="首頁" action="HOME" />
                        <h:commandLink value="會員" action="#{user.login()}" />
                    </h:form>
                </div>
                <div id="mainContent">
                    <h1>你好<h:outputText value="#{user.name}"/></h1>
                    <p>Lorem ipsum dolor sit.</p>
                    <h2>H2 層級標題 </h2>
                    <p>Lorem ipsum dolor sit.</p>
                </div>
                <div id="footer">
                    <p>Copyright ©2012 Health management system by Fingerprint / St. John's University  . All Rights Reserved.
                    </p>
                </div>
            </div>
        </body>
    </html>
</f:view>
