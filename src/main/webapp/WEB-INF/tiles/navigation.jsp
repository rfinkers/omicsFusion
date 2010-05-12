    <%--
    Document   : Plantbreeding navigation
    Created on : Apr 10, 2009, 7:01:41 PM
    Author     : Richard Finkers
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

Test: Navigation

<!-- start vertical menu -->
<s:div class="vertMenu">
    <table class="rootVoices vertical" cellspacing='0' cellpadding='0' border='0'>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<html:rewrite page='/'/>','_self')"><bean:message key="menu.home"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<html:rewrite page='/submit/'/>','_self')"><bean:message key="menu.submit"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<html:rewrite page='/retrieve/'/>','_self')"><bean:message key="menu.retrieve"/></td></tr>
    </table>
</s:div>
<!-- end vertical menu -->


<!-- end menues -->