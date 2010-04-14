<%-- 
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : finke002
--%>

<%@page session="true" %>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<logic:notPresent name="org.apache.struts.action.MESSAGE" scope="application">
    <div  style="color: red">ERROR:  Application resources not loaded -- check servlet container logs for error messages.</div>
</logic:notPresent>
<logic:present name="org.apache.struts.action.MESSAGE" scope="application">
    <h2><bean:message key="step1.heading" /></h2>
    <bean:message key="step1.message"/>
    <html:form action="/step2.do?" method="post">
        <html:checkbox property="varSelection" titleKey="variable.selection"/>
        Select individual methods? / table form? / Show div?
        <html:submit/><html:reset/>
    </html:form>
</logic:present>
