<%-- 
    Document   : CBSG header.jsp
    Created on : Apr 10, 2009, 6:58:31 PM
    Author     : finke002
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<div id="logobar">
    <div id="logo">
        <html:link bundle="images" href="http://www.cbsg.nl" target="_bank"><html:img bundle="images" pageKey="cbsg.logo" altKey="cbsg.alt" height="35"/></html:link>
        <%--<html:link bundle="images" href="http://cordis.europa.eu/fp6" target="_blank"><html:img bundle="images" pageKey="euFw6.logo" altKey="euFw6.alt" /></html:link>--%>
    </div>
    <!-- @todo: url from properties file.. height control of the logo?end logobar -->
    <logic:notPresent name="user" scope="session">
        <div id="login"> Welcome, guest</div><br>
    </logic:notPresent>
    <logic:present name="user" scope="session">
        <%--TODO: Implement usage of jsp:getProperty in e.g. the Trait graph / accession report pages?--%>
        <div id="login">Welcome, <jsp:getProperty name="user" property="firstName"/> <jsp:getProperty name="user" property="lastName"/></div><br >
    </logic:present>
    <hr>
</div>