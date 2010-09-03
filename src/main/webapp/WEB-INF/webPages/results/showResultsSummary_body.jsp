<%-- 
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : finke002
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>

<s:text name="welcome.heading" />
show results
<s:actionerror/>
get the sessionId from an input field
<s:form action="/results/summaryResults" >
    <s:textfield name="sessionId"/>
    <s:submit/>
</s:form>