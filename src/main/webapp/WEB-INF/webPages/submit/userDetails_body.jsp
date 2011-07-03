<%--
    Document   : userDetails_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : Richard Finkers
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:text name="userDetails.progress"/>

<h2><s:text name="userDetails.heading" /></h2>
<s:actionerror/>
<s:actionmessage/>

<s:text name="userDetails.explanation"/>
<s:form action="/userRegistration/userDetails" >
    <%--<s:textfield name="user.username" label="Username"/>--%>
    <sj:textfield key="name" />
    <sj:textfield key="email" />
    <sj:textfield key="affiliation" />
    <sj:textfield key="country" />
    <s:reset cssClass="testSubject"/>
    <s:submit />
</s:form>