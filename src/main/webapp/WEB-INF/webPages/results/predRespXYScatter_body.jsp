<%--
    Document   : Results index page
    Created on : May 13, 2010, 11:26:43 AM
    Author     : Richard Finkers
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>

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

<sjc:chart id="chartPoints" cssStyle="width: 800px; height: 500px;">
    <%-- Data set --%>
    <sjc:chartData
        label="List -Points-"
        list="points"
        points="{ show: true }"
        lines="{ show: true }"
        />
    <%-- Regression Line
    <sjc:chartData
        label="Map -Integer, Integer-"
        list="pointsFromMap"
        />--%>
</sjc:chart>

<a href="<s:url action='summaryResults'>
       <s:param name ='sessionId'>${resultSession}</s:param>
   </s:url>" class="button" onclick="this.blur();">
    //TODO: s:text tag.
    <span>Return to table</span>
</a>
