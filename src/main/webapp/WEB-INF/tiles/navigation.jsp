<%--
Document   : Plantbreeding navigation
Created on : Apr 10, 2009, 7:01:41 PM
Author     : Richard Finkers
--%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<sj:menu id="menuWithItems" cssStyle="width:75%">
    <s:url var="home" value='/'/>
    <s:text var="menu.home" name="menu.home"/>
    <sj:menuItem title="%{menu.home}" menuIcon="ui-icon-home"  href="%{home}" />
    <s:url var="submit" value="/userRegistration/startSubmitWizard"/>
    <s:text var="menu.submit" name="menu.submit"/>
    <sj:menuItem title="%{menu.submit}" menuIcon="ui-icon-extlink" href="%{submit}"/>
    <s:url var="result" value="/results/showResultsSummary.jsp"/>
    <s:text var="menu.retrieve" name="menu.retrieve"/>
    <sj:menuItem title="%{menu.retrieve}" href="%{result}"/>
    <s:url var="method" value="/methods/index.jsp" />
    <s:text var="menu.method" name="menu.methods"/>
    <sj:menuItem title="%{menu.method}" href="%{method}"/>
    <s:url var="instruction" value="/help/index.jsp" />
    <s:text var="menu.help" name="menu.help"/>
    <sj:menuItem title="%{menu.help}" href="%{instruction}"/>
    <s:url var="about" value="/about.jsp" />
    <s:text var="menu.about" name="menu.about"/>
    <sj:menuItem title="%{menu.about}" href="%{about}"/>
</sj:menu>
