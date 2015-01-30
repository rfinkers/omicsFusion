<%--
    Document   : uploadExcel_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : Richard Finkers
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:text name="uploadExcel.progress" />
<sj:a openDialog="myclickdialog" button="true" buttonIcon="ui-icon-newwin" cssStyle="allign:right" >help</sj:a>
<sj:dialog id="myclickdialog" autoOpen="false" modal="true" title="help"><s:text name="userDetails.explanation"/></sj:dialog>
    <!-- TODO: correct help text -->

    <h2><s:text name="uploadExcel.heading" /></h2>
<s:actionerror theme="bootstrap"/>
<s:actionmessage theme="jquery"/>
<s:fielderror theme="jquery"/>

<s:text name="uploadExcel.explanation"/>

<!--    TODO: separate this from the lines above? Add instructions-->
<s:form action="dataUpload" enctype="multipart/form-data" method="POST"
        theme="bootstrap" namespace="/userRegistration" label="Upload"
        cssClass="form-horizontal">
    <!-- TODO: use listKey instead of list?)-->
    <s:file name="dataSheetResponseFile"
            key="dataSheetResponseFile"
            tooltip="Select an excel sheet which contains the response variables."/>
    <s:select name="responseType"
              key="dataset.type"
              tooltip="Select the type of the response sheet."
              headerValue="-- Please select --"
              headerKey="select"
              list="#{'ph':'Phenomics','me':'Metabolomics','tr':'Transcriptomics','ma':'Markers'}" />
    <s:file name="dataSheetPredictorFile"
            key="dataSheetPredictorFile"
            tooltip="Select an excel sheet which contains the predictor variables."/>
    <s:select name="predictorType"
              key="dataset.type"
              tooltip="Select the type of the predictor sheet."
              headerValue="-- Please select --"
              headerKey="select"
              list="#{'ph':'Phenomics','me':'Metabolomics','tr':'Transcriptomics','ma':'Markers'}" />
    <%-- <s:file name="dataSheetPredictResponseFile" key="dataSheetPredictResponseFile"/> --%>
    <div class="form-actions">
        <sj:submit cssClass="btn btn-inverse"
                   validate="false"
                   validateFunction="bootstrapValidation"
                   indicator="indicator"/>
        <s:reset cssClass="btn btn-inverse"/>
    </div>
</s:form>
<img id="indicator" src="<s:url value="/images/busy.gif"/>" alt="loading..." style="display:none"/>
