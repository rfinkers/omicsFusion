<%--
    Document   : userDetails_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : Richard Finkers
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:text name="userDetails.progress"/>
<sj:dialog id="myclickdialog" autoOpen="false" modal="true" title="help">
    <s:text name="userDetails.explanation"/></sj:dialog>
<sj:a openDialog="myclickdialog" button="true" buttonIcon="ui-icon-newwin"
      cssStyle="allign:right">help</sj:a>
<h2><s:text name="userDetails.heading" /></h2>
<s:actionerror theme="bootstrap"/>

<s:text name="userDetails.explanation"/>
<s:form action="userDetails" name="userDetails" theme="bootstrap"
        label="User details" method="POST" namespace="/userRegistration"
        cssClass="form-horizontal" enctype="multipart/form-data">
    <sj:textfield key="name"
                  id="name"
                  tooltip="Please enter your name."
                  inputAppendIcon="user"
                  required="true"/>
    <sj:textfield key="email"
                  tooltip="Please enter your email."
                  inputAppendIcon="envelope"
                  required="true"/>
    <sj:textfield key="affiliation"
                  tooltip="Please enter the name of your organization."/>
    <sj:textfield key="country"
                  tooltip="Please enter your country."/>
    <div class="form-actions">
        <sj:submit cssClass="btn btn-inverse"
                   formIds="userDetails"
                   validate="false"
                   validateFunction="bootstrapValidation"/>
    </div>
</s:form>
<!--<img id="indicator" src="images/indicator.gif" alt="Loading..." style="display:none"/>-->