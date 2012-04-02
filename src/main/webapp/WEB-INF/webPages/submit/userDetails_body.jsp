<%--
    Document   : userDetails_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : Richard Finkers
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:text name="userDetails.progress"/>
<sj:dialog id="myclickdialog" autoOpen="false" modal="true" title="help"><s:text name="userDetails.explanation"/></sj:dialog>
<sj:a openDialog="myclickdialog" button="true" buttonIcon="ui-icon-newwin" cssStyle="allign:right">help</sj:a>
<h2><s:text name="userDetails.heading" /></h2>
<s:actionerror theme="jquery"/>
<s:actionmessage theme="jquery"/>

<s:text name="userDetails.explanation"/>
<s:form action="/userRegistration/userDetails" name="userDetails" method="POST" namespace="/userRegistration" >
    <sj:textfield key="name" tooltip="Please enter your name."  tooltipCssClass="ui-icon-info" helpText="Please enter your name." />
    <sj:textfield key="email" tooltip="Please enter your email." helpText="Please enter your email address." />
    <sj:textfield key="affiliation" tooltip="Please enter the name of your organization." helpText="Which organization are you employed." />
    <sj:textfield key="country" tooltip="Please enter your country." helpText="From which country are you ariginating." />
</s:form>

<sj:submit formIds="userDetails" button="true" />