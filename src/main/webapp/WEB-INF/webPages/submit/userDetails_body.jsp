<%--
    Document   : userDetails_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : finke002
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<s:text name="userDetails.progress"/>

<s:text name="userDetails.heading" />
<s:text name="userDetails.explanation"/>

<s:actionerror/>
<%--<s:fielderror />--%>
<s:actionmessage/>
<p>Please provide the requested information and your license will be emailed to the given email address. Your data will never be shared with third parties, but only be used to notify you on software udates.</p>
<s:form action="/userRegistration/userDetails" >
    <s:textfield key="name"/>
    <s:textfield key="email" />
    <s:textfield key="affiliation" />
    <s:textfield key="country" />

    <s:submit/>
</s:form>