<%-- 
    Document   : Layout of the site
    Created on : Apr 10, 2009, 6:36:51 PM
    Author     : Richard Finkers
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="tiles" uri="http://struts.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<html>
    <head>
        <!-- favicon -->
        <link rel="icon" type="image/x-icon" href="<html:rewrite page="/favicon.ico"/>" />
        <link rel="shortcut icon" type="image/x-icon" href="<html:rewrite page="/favicon.ico"/>" />
        <!-- current page -->
        <html:base/>
        <!-- meta information -->
        <meta name="keywords" content="EU-SOL, CBSG, Wageningen UR Plantbreeding, tomato, potato" />
        <meta name="description" content="Research &amp; education for the EU-SOL project of the expertise group Wageningen UR Plant Breeding." />
        <meta name="reply-to" content="webmaster.plantbreeding@wur.nl" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <!-- stylesheets -->
        <%--TODO: Different css themes for each project? Remove hardcoded entries --%>
        <link href="<html:rewrite page="/css/themeEU-SOL.css"/>" rel="stylesheet" media="screen" type="text/css" title="Standaard" />
        <!-- page title -->
        <title><tiles:getAsString name="title" /></title>
        <!-- javascript-->

        <script type="text/javascript" src="<html:rewrite page="/js/jquery-1.4.min.js"/>"></script>
        <script type="text/javascript" src="<html:rewrite page="/js/mbMenu.js"/>"></script>
        <script type="text/javascript" src="<html:rewrite page="/js/jquery.metadata.js"/>"></script>
        <script type="text/javascript" src="<html:rewrite page="/js/jquery.hoverIntent.js"/>"></script>
        <script type="text/javascript" src="<html:rewrite page="/js/menu.js"/>"></script>
        <script type="text/javascript" src="<html:rewrite page="/js/jquery.blockUI.2.31.js"/>"></script>
        <script type="text/javascript" src="<html:rewrite page="/js/jquery.breeDB.0.1.js"/>"></script>
    </head>
    <body>
        <div id="canvas">
            <!-- start of the header-->
            <div id="header">
                <c:set var="title" ><tiles:getAsString name="title" /></c:set>
                <%--TODO: test for interface parameter / load right header file and remove hardcoded EU-SOL reference--%>
                <jsp:include page="/WEB-INF/tiles/omicsFusion/header.jsp">
                    <jsp:param name="title" value="${title}" />
                </jsp:include>
            </div>
            <!-- end of the header-->
            <div id="contentcontainer" class="homepage">
                <div id="maincontent">
                    <div id="pagecontainer">
                        <!-- start of the navigation menu -->
                        <div id="context">
                            <%--TODO: test for interface parameter / load right header file and remove hardcoded EU-SOL reference--%>
                            <jsp:include page="/WEB-INF/tiles/omicsFusion/navigation.jsp" />
                        </div>
                        <!-- end of the navigation menu -->
                        <!-- start of the page bdy -->
                        <div id="content">
                            <tiles:insert attribute="body" />
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
                <jsp:include page="/WEB-INF/tiles/footer.jsp" />
            </div>
            <!-- end of the footer -->
        </div>
        <!-- End canvas -->
    </body>
</html>