<%--
    Document   : uploadExcel_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : finke002
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<s:text name="uploadExcel.progress" />

<h2><s:text name="uploadExcel.heading" /></h2>
<s:actionerror/>
<s:actionmessage/>
<s:fielderror/>
<s:text name="uploadExcel.explanation"/>

<!--    TODO: separate this from the lines above? Add instructions-->
<s:form action="/userRegistration/dataUpload" enctype="multipart/form-data" >
    <!-- TODO: use listKey instead of list?)-->
    <s:file name="dataSheetResponseFile" key="dataSheetResponseFile" />
    <s:select key="dataset.type" name="responseType" headerValue="-- Please select --" headerKey="select" list="#{'ph':'Phenomics','me':'Metabolomics','tr':'Transcriptomics','ma':'Markers'}"/>
    <s:file name="dataSheetPredictorFile" key="dataSheetPredictorFile"/>
    <s:select key="dataset.type" name="predictorType" headerValue="-- Please select --" headerKey="select" list="#{'ph':'Phenomics','me':'Metabolomics','tr':'Transcriptomics','ma':'Markers'}"/>
<%-- <s:file name="dataSheetPredictResponseFile" key="dataSheetPredictResponseFile"/> --%>
    <s:reset/><s:submit/>
</s:form>


