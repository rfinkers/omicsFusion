<%--
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : Richard Finkers
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<s:text name="submit.complete.progress"/>
<h2><s:text name="submit.complete.heading" /></h2>
<s:actionerror theme="jquery" />
<s:actionmessage theme="jquery" />
<s:text name="submit.complete.salutation"/> <s:property value="#session.omicsFusionUser.userName"/>,
<s:text name="submit.complete.explanation"/>

<h4><s:text name="submit.complete.user"/></h4>
<ul>
    <li><s:text name="email"/> : <s:property value="#session.omicsFusionUser.email"/></li>
    <li><s:text name="affiliation"/> : <s:property value="#session.omicsFusionUser.affiliation"/></li>
    <li><s:text name="country"/> : <s:property value="#session.omicsFusionUser.country"/></li>
</ul>

<h4><s:text name="submit.complete.upload"/></h4>
<s:text name="dataSheetPredictorFile"/> : <s:property value="#session.sheets.predictor"/><br/>
<s:text name="dataSheetResponseFile"/> : <s:property value="#session.sheets.response"/>

<h4><s:text name="submit.complete.methods"/></h4>
<ul>
    <s:iterator value="#session.methods">
        <li><s:property/></li>
    </s:iterator>
</ul>

<s:form action="/analysis/pipeline" method="POST" namespace="/userRegistration">
    <s:hidden value="go" name="ready"/>
    <s:submit styleId="submitMoment" />
</s:form>