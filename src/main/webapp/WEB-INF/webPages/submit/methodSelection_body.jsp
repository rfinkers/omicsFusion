<%@ taglib prefix="s" uri="/struts-tags"%>

<s:text name="analysis.methods.progress"/>
<h2><s:text name="analysis.methods.heading" /></h2>
<s:actionerror/>
<%--<s:fielderror />--%>
<s:actionmessage/>
<s:text name="analisis.methods.explanation"/>

<s:form action="/userRegistration/methodSelection">
    <!--    Univariate TODO: resource bundle-->
    Machine learning
    <s:checkbox key="analysis.method.rf" name="rf" />
    <s:checkbox key="analysis.method.svm" name="svm" checked="checked"/>
    Variable selection
    <s:checkbox key="analysis.method.lasso" name="lasso" checked="checked"/>
    <s:checkbox key="analysis.method.elasticNet" name="elasticNet" checked="checked"/>
    <s:checkbox key="analysis.method.spls" name="spls" checked="checked" selectAllLabel="Select All"/>
    Regression
    <s:checkbox key="analysis.method.ridge" name="ridge" checked="checked"/>
    <s:checkbox key="analysis.method.pcr" name="pcr" checked="checked"/>
    <s:checkbox key="analysis.method.pls" name="pls"checked="checked" />    
    Univariate
    <s:checkbox key="analysis.method.univariate" name="univariate" checked="checked"/>
    <!--TODO: select all / deselect all buttons-->
    <s:text name="analysis.methods.variable.selection"/>

    <s:submit key="method.submit"/> <s:reset key="button.reset"/>
</s:form>