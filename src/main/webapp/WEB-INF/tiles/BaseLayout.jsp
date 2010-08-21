<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <!-- page title -->
        <title><tiles:insertAttribute name="title" /></title>

        <!-- favicon -->
        <link rel="icon" type="image/x-icon" href="<s:url value="/favicon.ico"/>" />
        <link rel="shortcut icon" type="image/x-icon" href="<s:url value="/favicon.ico"/>" />

        <!-- meta information -->
        <meta name="keywords" content="EU-SOL, CBSG, Wageningen UR Plantbreeding, tomato, potato" />
        <meta name="description" content="Research &amp; education for the EU-SOL project of the expertise group Wageningen UR Plant Breeding." />
        <meta name="reply-to" content="webmaster.plantbreeding@wur.nl" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<!--        <meta http-equiv="refresh" content="2;url<s:url/>"/> Only for wait.jsp / If thest?-->

        <!-- style sheets -->
        <link href="<s:url value="/css/themePBR.css"/>" rel="stylesheet" media="screen" type="text/css" title="Standaard" />

        <!-- taglib auto generated -->
        <s:head/>
        <sj:head/>

        <!-- javascript. jQuery/ jQueryUI libraries are loaded via an alternative tag instead of script element. -->
        <!-- mbMenu -->
        <script type="text/javascript" src="<s:url value="/js/mbMenu.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.metadata.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.hoverIntent.js"/>"></script>
        <!-- other -->
        <script type="text/javascript" src="<s:url value="/js/jquery.blockUI.2.31.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.breeDB.0.1.js"/>"></script>
    </head>
    <body>
        <s:div id="canvas">
            <!-- start of the header-->
            <s:div id="header">
                <tiles:insertAttribute name="header" />
            </s:div>
            <!-- end of the header-->
            <s:div id="contentcontainer" class="homepage">
                <s:div id="maincontent">
                    <s:div id="pagecontainer">
                        <!-- start of the navigation menu -->
                        <s:div id="context">
                            <tiles:insertAttribute name="menu" />
                        </s:div>
                        <!-- end of the navigation menu -->
                        <!-- start of the page bdy -->
                        <s:div id="content">
                            <tiles:insertAttribute name="body" />
                        </s:div>
                        <!-- end of the body -->
                    </s:div>
                    <!--  end pagecontainer -->
                </s:div>
                <!--  end maincontent -->
            </s:div>
            <!--  end contentcontainer -->
            <!-- start of the footer -->
            <s:div id ="footer">
                <tiles:insertAttribute name="footer" />
            </s:div>
            <!-- end of the footer -->
        </s:div>
        <!-- End canvas -->
    </body>
</html>