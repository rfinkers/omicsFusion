<%--
    Document   : Results index page
    Created on : May 13, 2010, 11:26:43 AM
    Author     : finke002
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!--FIXME: Image map-->
<s:actionerror/>
<s:actionmessage/>


<s:url id='chartUrl' action='chart'>
    <s:param name='chartId'>${chartType}</s:param>
</s:url>
<img alt="Custom generated XY scatterplot for a predictor and response value"  src='${chartUrl}' usemap="#imageMap" />
<br/>
<!--TODO: FORM with button instead of link-->
<br/>
<%--<a href="<s:url action='summaryResults'><s:param name ='sessionId'>${resultSession}</s:param></s:url>"><s:submit value="Return to table"/></a>--%>
<a href="<s:url action='summaryResults'><s:param name ='sessionId'>${resultSession}</s:param></s:url>" class="button" onclick="this.blur();"><span>Return to table</span></a>