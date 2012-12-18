<%--
Document   : Plantbreeding navigation
Created on : Apr 10, 2009, 7:01:41 PM
Author     : Richard Finkers
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url value='/' var="home"/>

<sj:menu id="menuWithItems" cssStyle="width:80%; font-smaller">
    <sj:menuItem title="Home" />
    <sj:menuItem title="Submit analysis" menuIcon="ui-icon-extlink" href="http://www.jgeppert.com/category/java/struts2-jquery/"/>
    <sj:menuItem title="Previous analysis" href="results/showResultsSummary.jsp"/>
    <sj:menuItem title="Analysis methods" href="methods/index.jsp"/>
    <sj:menuItem title="Instructions" href="help/index.jsp"/>
    <sj:menuItem title="About" href="about.jsp"/>
</sj:menu>

<!-- start vertical menu -->
<div class="vertMenu">
    <table class="rootVoices vertical">
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/'/>', '_self')"><s:text name="menu.home"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/userRegistration/startSubmitWizard'/>', '_self')"><s:text name="menu.submit"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/results/showResultsSummary.jsp'/>', '_self')"><s:text name="menu.retrieve"/></td></tr>
        <tr><td class="rootVoice {menu: 'menu_1'}" onclick="window.open('<s:url value='/methods/index.jsp'/>', '_self')"><s:text name="menu.methods"/></td></tr>
        <tr><td class="rootVoice {menu: 'menu_1'}" onclick="window.open('<s:url value='/help/index.jsp'/>', '_self')"><s:text name="menu.help"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<s:url value='/about.jsp'/>', '_self')"><s:text name="menu.about"/></td></tr>

    </table>
</div>
<!-- end vertical menu -->
<!-- Methods submenu -->
<div id="menu_1" class="mbmenu">
    <a rel="title" ><s:text name="menu.methods.heading"/></a>
    <a href="<s:url action="methods/rf.jsp"/>" class="{img: 'ico_view.gif'}"><s:text name="menu.rf"/></a>
</div>
<!-- end menu -->