<%-- 
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : Richard Finkers
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%--<%@taglib uri="http://displaytag.sf.net" prefix="display" %>--%>

<h2><s:text name="results.heading" /></h2>
<s:text name="results.summary.introduction"/>


<%--<display:table name="requestScope.status" class="displayTable" export="false" pagesize="50"/>--%>
<!--TODO: finish when a final object is available-->
<% String table = (String) request.getAttribute("table"); %>
<%= table%>
