<%-- 
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : finke002
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<h2><s:text name="submit.complete.heading" /></h2>
<s:actionerror/>
<s:actionmessage/>
<s:text name="submit.complete.explanation"/>

<h4><s:text name="submit.complete.user"/></h4>
<s:property value="#session.omicsFusionUser.email"/>

<h4><s:text name="submit.complete.upload"/></h4>


<h4><s:text name="submit.complete.methods"/></h4>

<s:form action="/analysis/pipeline" >
    <s:hidden value="go" name="ready"/>
</s:form>