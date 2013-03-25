<%--
    Document   : userDetails_body.jsp
    Created on : May 12, 2010, 5:18:41 PM
    Author     : Richard Finkers
Form
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<script type="text/javascript">
    $.subscribe('before', function(event, data) {
        var fData = event.originalEvent.formData;
        alert('About to submit: \n\n' + fData[0].value + ' to target ' + event.originalEvent.options.target + ' with timeout ' + event.originalEvent.options.timeout);
        var form = event.originalEvent.form[0];
        if (form.name.value.length < 2) {
            alert('Please enter a name with min 2 characters');
            // Cancel Submit comes with 1.8.0
            event.originalEvent.options.submit = false;
        }
    });
</script>

<s:text name="userDetails.progress"/>
<sj:dialog id="myclickdialog" autoOpen="false" modal="true" title="help"><s:text name="userDetails.explanation"/></sj:dialog>
<sj:a openDialog="myclickdialog" button="true" buttonIcon="ui-icon-newwin" cssStyle="allign:right">help</sj:a>
<h2><s:text name="userDetails.heading" /></h2>
<s:actionerror theme="jquery"/>
<s:actionmessage theme="jquery"/>

<s:text name="userDetails.explanation"/>
<img id="indicator" src="<s:url value="/images/busy.gif"/>" alt="loading..." style="display:none"/>
<s:form action="userDetails" name="userDetails" method="POST" namespace="/userRegistration" class="form-horizontal" >
    <sj:textfield key="name"
                  id="name"
                  tooltip="Please enter your name."
                  helpText="Please enter your name."
                  cssClass="input_text"
                  tooltipCssClass="ui-icon-info"/>
    <sj:textfield key="email"
                  tooltip="Please enter your email."
                  helpText="Please enter your email address." />
    <sj:textfield key="affiliation"
                  tooltip="Please enter the name of your organization."
                  helpText="Which organization are you employed." />
    <sj:textfield key="country"
                  tooltip="Please enter your country."
                  helpText="From which country are you ariginating." />
</s:form>
<sj:submit formIds="userDetails"
           button="true"
           timeout="2500"
           indicator="indicator"
           onBeforeTopics="before"
           effect="highlight"
           effectOptions="{ color : '#222222' }"
           effectDuration="3000"/>