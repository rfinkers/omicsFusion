<%-- 
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : Richard Finkers
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<s:text name="welcome.heading" />

get the sessionId from an input field
<s:form action="/runStatus/runStatus" >
    <s:textfield name="sessionId"/>
    <s:submit/>
</s:form>