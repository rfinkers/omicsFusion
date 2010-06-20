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


<s:form action="/userRegistration/dataUpload" enctype="multipart/form-data" >
    <s:file name="dataSheetFile" key="dataSheet" />
    <s:reset/><s:submit/>
</s:form>