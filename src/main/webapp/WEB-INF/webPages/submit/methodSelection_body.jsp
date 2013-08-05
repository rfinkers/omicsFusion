<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:text name="analysis.methods.progress"/>
<h2><s:text name="analysis.methods.heading" /></h2>
<s:actionerror theme="jquery" />
<s:actionmessage theme="jquery" />
<s:text name="analisis.methods.explanation"/>

<s:form action="methodSelection"
        method="POST"
        namespace="/userRegistration"
        cssClass="form-vertical"
        theme="bootstrap"
        label="Methods">
    <s:checkbox key="analysis.method.rf" name="rf" />
    <%--<s:checkbox key="analysis.method.svm" name="svm" disabled="true"/>--%>
    <s:checkbox key="analysis.method.lasso"
                name="lasso" checked="checked" tooltip="Lasso regression"/>
    <s:checkbox key="analysis.method.elasticNet"
                name="elasticNet" checked="checked"/>
    <s:checkbox key="analysis.method.spls"
                name="spls" selectAllLabel="Select All"/>
    <s:checkbox key="analysis.method.ridge"
                name="ridge" checked="checked"/>
    <s:checkbox key="analysis.method.pcr"
                name="pcr" checked="checked"/>
    <s:checkbox key="analysis.method.pls"
                name="pls"checked="checked" />
    <s:checkbox key="analysis.method.univariate"
                name="univariate" checked="checked"/>
    <!--TODO: select all / deselect all buttons-->
    <!--TODO: is there a way to add categories to the different type of methods in the form?
    <s:text name="analysis.methods.variable.selection"/>-->
    <div class="form-actions">
        <sj:submit key="method.submit"
                   cssClass="btn btn-inverse"
                   validate="false"
                   validateFunction="bootstrapValidation"/>
        <s:reset key="button.reset"
                 cssClass="btn btn-inverse"
                 validate="false"
                 validateFunction="bootstrapValidation"/>
    </div>
</s:form>