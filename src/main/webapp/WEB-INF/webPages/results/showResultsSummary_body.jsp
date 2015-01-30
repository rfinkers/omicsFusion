<%--
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : Richard Finkers
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<h2><s:text name="results.heading" /></h2>
<s:actionerror theme="jquery"/>
<s:text name="results.introduction" />
<br/><br/>
<s:form action="summaryResults"
        namespace="/results"
        theme="bootstrap"
        cssClass="form-horizontal"
        label="Results"
        >
    <s:textfield name="sessionId"
                 key=""
                 size="26"
                 tooltip="Enter your unique session identifyer. This ID was sent to you by email after submitting your analyis."/>
    <div class="form-actions">
        <sj:submit styleId="submitMoment"
                   cssClass="btn btn-inverse"
                   validate="false" />
    </div>
</s:form>