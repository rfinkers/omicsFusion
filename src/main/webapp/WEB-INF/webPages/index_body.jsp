<%--
    Document   : index_body
    Created on : May 12, 2010, 5:18:41 PM
    Author     : finke002
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<h1><s:text name="omicsFusion.heading" /></h1>

<s:text name="omicsFusion.home.text" />

<%--<s:url id="ajax" value="/submit/userDetails.jsp"/>
<sj:a href="%{ajax}" button="true" targets="content" indicator="indicator" buttonIcon="ui-icon-play">Submit</sj:a>
<sj:submit href="%{ajax}" button="true" value="Start wizard"/>--%>
<s:form validate="false" action="/submit/userDetails.jsp">
    <s:submit value="Start submission" />
</s:form>
<br/><br/>
<s:text name="omicsFusion.lastUpdate"/>
<!--TODO:different button?-->