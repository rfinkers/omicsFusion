<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
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

        <!-- stylesheets -->
        <link href="<s:url value="/css/themeEU-SOL.css"/>" rel="stylesheet" media="screen" type="text/css" title="Standaard" />

        <!-- javascript-->
        <script type="text/javascript" src="<s:url value="/js/jquery-1.4.min.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/mbMenu.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.metadata.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.hoverIntent.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/menu.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.blockUI.2.31.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.breeDB.0.1.js"/>"></script>
    </head>
    <body>
        <div id="canvas">
            <!-- start of the header-->
            <div id="header">
                <tiles:insertAttribute name="header" />
            </div>
            <!-- end of the header-->
            <div id="contentcontainer" class="homepage">
                <div id="maincontent">
                    <div id="pagecontainer">
                        <!-- start of the navigation menu -->
                        <div id="context">
                            <tiles:insertAttribute name="menu" />
                        </div>
                        <!-- end of the navigation menu -->
                        <!-- start of the page bdy -->
                        <div id="content">
                            <tiles:insertAttribute name="body" />
                        </div>
                        <!-- end of the body -->
                    </div>
                    <!--  end pagecontainer -->
                </div>
                <!--  end maincontent -->
            </div>
            <!--  end contentcontainer -->
            <!-- start of the footer -->
            <div id ="footer">
                <tiles:insertAttribute name="footer" />
            </div>
            <!-- end of the footer -->
        </div>
        <!-- End canvas -->
    </body>
</html>