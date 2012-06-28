<%-- 
    Document   : welcome
    Created on : 2012/6/28, 下午 03:53:51
    Author     : Leo
--%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@page contentType="text/html;charset=Big5"%> 
<html>
    <head>
        <title>第一個JSF程式</title>
    </head>
    <body>    
        <f:view>     
            <h:outputText value="#{user.account}"/> 您好！   
            <h3>歡迎使用 JavaServer Faces！</h3>  
        </f:view>
    </body>
</html>