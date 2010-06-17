<%--
    Document   : index_body
    Created on : May 12, 2010, 5:18:41 PM
    Author     : finke002
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<s:text name="welcome.heading" />

<h1>omicsFusion</h1>

Flow of the website
<ul>
    <li>1 - Enter user details and show disclaimer?</li>
    <li>Automatically generate a session token</li>
    <li>2 - Upload excel</li>
    <li>check excel</li>
    <li>3 - Select methods</li>
    <li>4 - run analysis</li>
    <li>submit to SGE queue</li>
    <li>5 - email results</li>
    <li>show results using link in email</li>
</ul>

<s:a value="/submit/userDetails.jsp">Start</s:a>