<%--
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : Richard Finkers
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<h2><s:text name="results.heading" /></h2>
<s:actionerror/>
<s:text name="results.introduction" />
<br/><br/>
<s:form action="/results/summaryResults" >
    <s:textfield name="sessionId" size="26"/>
    <s:submit styleId="submitMoment" />
</s:form>