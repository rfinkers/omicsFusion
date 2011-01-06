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
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/userRegistration/startSubmitWizard'/>','_self')"><s:text name="menu.submit"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/results/showResultsSummary.jsp'/>','_self')"><s:text name="menu.retrieve"/></td></tr>
        <tr><td class="rootVoice {menu: 'menu_1'}" onclick="window.open('<s:url value='/methods/methodsSummary.jsp'/>','_self')"><s:text name="menu.methods"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/about.jsp'/>','_self')"><s:text name="menu.about"/></td></tr>

    </table>
</s:div>
<!-- end vertical menu -->
<!-- Methods submenu -->
<div id="menu_1" class="mbmenu">
    <a rel="title" ><bean:message key="menu.search.heading"/></a>
    <a href="<s:url action="methods/rf.jsp"/>" styleClass="{img: 'ico_view.gif'}"><s:text name="menu.rf"/></a> 
</div>
<!-- end menu -->