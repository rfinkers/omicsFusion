<%@ taglib prefix="s" uri="/struts-tags"%>


<h1><s:text name="omicsFusion.heading" /></h1>
<s:actionerror/>
<%--<s:fielderror />--%>
<s:actionmessage/>
<s:form action="/userRegistration/methodSelection">
    <s:text name="analysis.methods"/>

    <s:checkbox key="analysis.method.lasso" name="lasso"/>
    <s:checkbox key="analysis.method.elasticNet1" name="elasticNet1"/>
    <s:checkbox key="analysis.method.elasticNet2" name="elasticNet2"/>
    <s:checkbox key="analysis.method.pcr" name="pcr"/>
    <s:checkbox key="analysis.method.pls" name="pls"/>
    <s:checkbox key="analysis.method.rf" name="rf"/>
    <s:checkbox key="analysis.method.ridge" name="ridge"/>
    <s:checkbox key="analysis.method.svm" name="svm"/>
    <s:checkbox key="analysis.method.spls" name="spls"/>
    <s:checkbox key="analysis.method.univariate" name="univariate"/>

    <s:text name="analysis.variableSelection"/>

    <s:submit key="method.submit"/> <s:reset key="button.reset"/>
</s:form>