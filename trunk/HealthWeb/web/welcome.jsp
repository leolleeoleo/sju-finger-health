<%-- 
    Document   : welcome
    Created on : 2012/6/28, �U�� 03:53:51
    Author     : Leo
--%>

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@page contentType="text/html;charset=Big5"%> 
<html>
    <head>
        <title>�Ĥ@��JSF�{��</title>
    </head>
    <body>    
        <f:view>     
            <h:outputText value="#{user.account}"/> �z�n�I   
            <h3>�w��ϥ� JavaServer Faces�I</h3>  
        </f:view>
    </body>
</html>