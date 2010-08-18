<%@ taglib prefix="s" uri="/struts-tags"%>

<s:text name="analysis.methods.progress"/>
<h2><s:text name="analysis.methods.heading" /></h2>
<s:actionerror/>
<%--<s:fielderror />--%>
<s:actionmessage/>
<s:text name="analisis.methods.explanation"/>

<s:form action="/userRegistration/methodSelection">
    <s:checkbox key="analysis.method.lasso" name="lasso"/>
    <s:checkbox key="analysis.method.elasticNet" name="elasticNet"/>
    <s:checkbox key="analysis.method.pcr" name="pcr"/>
    <s:checkbox key="analysis.method.pls" name="pls"/>
    <s:checkbox key="analysis.method.rf" name="rf"/>
    <s:checkbox key="analysis.method.ridge" name="ridge"/>
    <s:checkbox key="analysis.method.svm" name="svm"/>
    <s:checkbox key="analysis.method.spls" name="spls"/>
    <s:checkbox key="analysis.method.univariate" name="univariate"/>

    <s:text name="analysis.methods.variable.selection"/>

    <s:submit key="method.submit"/> <s:reset key="button.reset"/>
</s:form>