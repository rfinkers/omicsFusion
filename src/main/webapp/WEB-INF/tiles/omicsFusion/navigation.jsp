    <%--
    Document   : Plantbreeding navigation
    Created on : Apr 10, 2009, 7:01:41 PM
    Author     : Richard Finkers
--%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<!-- start vertical menu -->
<div class="vertMenu">
    <table class="rootVoices vertical" cellspacing='0' cellpadding='0' border='0'>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<html:rewrite page='/'/>','_self')"><bean:message key="menu.home"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<html:rewrite page='/submit/'/>','_self')"><bean:message key="menu.submit"/></td></tr>
        <tr><td class="rootVoice {menu: 'empty'}" onclick="window.open('<html:rewrite page='/retrieve/'/>','_self')"><bean:message key="menu.retrieve"/></td></tr>
    </table>
</div>
<!-- end vertical menu -->


<!-- end menues -->