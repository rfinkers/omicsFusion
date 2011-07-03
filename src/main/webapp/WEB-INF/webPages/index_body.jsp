<%--
    Document   : index_body
    Created on : May 12, 2010, 5:18:41 PM
    Author     : Richard Finkers
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<h1><s:text name="omicsFusion.heading" /></h1>

<s:text name="omicsFusion.home.text" />

<s:form validate="false" action="/userRegistration/startSubmitWizard">
    <s:submit value="Start submission" cssClass="testSubject" />
</s:form>
<br/>
<s:text name="omicsFusion.lastUpdate"/>