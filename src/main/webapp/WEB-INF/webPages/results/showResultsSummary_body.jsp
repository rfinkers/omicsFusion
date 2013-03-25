<%--
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : Richard Finkers
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<h2><s:text name="results.heading" /></h2>
<s:actionerror/>
<s:text name="results.introduction" />
<br/><br/>
<s:form action="summaryResults"  namespace="/results">
    <s:textfield name="sessionId" size="26"/>
    <sj:submit styleId="submitMoment" button="true" />
</s:form>