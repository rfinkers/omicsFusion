<%--
    Document   : userDetails_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : Richard Finkers
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<s:text name="userDetails.progress"/>

<h2><s:text name="userDetails.heading" /></h2>
<s:actionerror/>
<s:actionmessage/>

<s:text name="userDetails.explanation"/>
<s:form action="/userRegistration/userDetails" >
    <%--<s:textfield name="user.username" label="Username"/>--%>
    <s:textfield key="name" />
    <s:textfield key="email" />
    <s:textfield key="affiliation" />
    <s:textfield key="country" />
    <s:submit/>
</s:form>