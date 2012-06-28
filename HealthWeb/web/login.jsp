<%-- 
    Document   : index
    Created on : 2012/2/29, 上午 06:04:16
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
                    <h1>健康點點名</h1>
                </div>
                <div id="sidebar1">
                    <h:form>
                        <h:commandLink value="首頁" action="HOME" />
                        <h:commandLink value="會員" action="#{user.login()}" />
                    </h:form>
                </div>
                <div id="mainContent">
                    <h1><h:outputText value="#{msgs.login}"/></h1>                
                    <h:form> 
                        <p><h:outputText value="#{user.info}"/></p>
                        <p>帳號:<h:inputText value="#{user.account}"/></p>
                        <p>密碼:<h:inputSecret value="#{user.password}"/></p>
                        <p><h:commandButton value="送出" action="#{user.login()}"/></p>
                    </h:form>
                </div>
                <div id="footer">
                    <p>Copyright ©2012 Health management system by Fingerprint / St. John's University  . All Rights Reserved.
                    </p>
                </div>
            </div>
        </body>
    </html>
</f:view>