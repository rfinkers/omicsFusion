    <%--
    Document   : Plantbreeding navigation
    Created on : Apr 10, 2009, 7:01:41 PM
    Author     : Richard Finkers
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- start vertical menu -->
<s:div class="vertMenu">
    <table class="rootVoices vertical">
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/'/>','_self')"><s:text name="menu.home"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/submit/userDetails.jsp'/>','_self')"><s:text name="menu.submit"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/results/showResultsSummary.jsp'/>','_self')"><s:text name="menu.retrieve"/></td></tr>
    </table>
</s:div>
<!-- end vertical menu -->
<!-- end menu -->