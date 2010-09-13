<%-- 
    Document   : index_body
    Created on : Apr 10, 2009, 7:09:46 PM
    Author     : finke002
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%--<%@taglib uri="http://displaytag.sf.net" prefix="display" %>--%>

<s:text name="welcome.heading" />
<p>Results Overview</p>

<%--<display:table name="requestScope.status" class="displayTable" export="false" pagesize="50"/>--%>
<!--TODO: finish when a final object is available-->
<% String table = (String) request.getAttribute("table"); %>
<%= table %>
