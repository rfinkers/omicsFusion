<%--
    Document   : Results index page
    Created on : May 13, 2010, 11:26:43 AM
    Author     : Richard Finkers
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sjc" uri="/struts-jquery-chart-tags"%>

<s:actionerror/>
<s:actionmessage/>
<br/>
<%--<a href="<s:url action='summaryResults'><s:param name ='sessionId'>${resultSession}</s:param></s:url>"><s:submit value="Return to table"/></a>--%>
<script type="text/javascript">
    $.subscribe('chartHover', function(event, data) {
        $("#topicsHover").text(event.originalEvent.pos.x.toFixed(2) + ',' + event.originalEvent.pos.y.toFixed(2));
    });
    $.subscribe('chartClick', function(event, data) {
        var item = event.originalEvent.item;
        if (item) {
            $("#topicsClick").text("You clicked point "
                    + item.dataIndex
                    + " ("
                    + item.datapoint[0]
                    + ","
                    + item.datapoint[1]
                    + ") in "
                    + item.series.label
                    + ".");
            event.originalEvent.plot.highlight(item.series, item.datapoint);
        }
    });
</script>

<h2>${session.responseName} vs. ${session.predictorName}</h2>
<sjc:chart id="chartPoints"
           cssStyle="width: 790px; height: 500px;"
           onClickTopics="chartClick"
           onHoverTopics="chartHover"
           yaxisLabel="%{#session.responseName}"
           xaxisLabel="%{#session.predictorName}"
           >
    <sjc:chartData
        label="data"
        list="points"
        points="{ show: true }"
        lines="{ show: false }"
        clickable="true"
        hoverable="true"
        javascriptTooltip="true"
        />
    <sjc:chartData
        label="regression"
        list="regression"
        points="{ show: false }"
        lines="{ show:true }"
        />
</sjc:chart>
<div id="topicsHover"></div>
<div id="topicsClick"></div>
<br/>
<a href="<s:url action='summaryResults'>
       <s:param name ='sessionId'>${resultSession}</s:param>
   </s:url>" class="button" onclick="this.blur();">
    <!--    TODO: s:text tag for Return to table.-->
    <span>Return to table</span>
</a>
