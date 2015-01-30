<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html version="HTML+RDFa 1.1" lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
      xmlns:dc="http://purl.org/dc/elements/1.1/"
      xmlns:foaf="http://xmlns.com/foaf/0.1/">

    <head>
        <!-- page title -->
        <title property="dc:title"><tiles:insertAttribute name="title" /></title>

        <!-- favicon -->
        <link rel="icon" type="image/x-icon" href="<s:url value="/favicon.ico"/>" />
        <link rel="shortcut icon" type="image/x-icon" href="<s:url value="/favicon.ico"/>" />

        <!-- RDF description -->
        <link rel="meta" type="application/rdf+xml" title="FOAF" href="<s:url value="/OmicsFusion.rdf" />"/>
        <rdf:RDF>
            <!-- TODO: Further test this implementation? -->
        </rdf:RDF>

        <!-- meta information -->
        <meta name="keywords" content="omicsFusion, Wageningen UR Plantbreeding, tomato, potato, lasso, ridge, pls, spls, univariate, anova, random forrest" />
        <meta rel="dc:description" name="description" content="Research &amp; education tools from Wageningen UR Plant Breeding." />
        <meta name="reply-to" content="webmaster.plantbreeding@wur.nl" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta rel="dc:creator" href="http://nl.linkedin.com/in/richardfinkers" />
        <meta rel="dc:publisher" href="http://www.plantbreeding.wur.nl" />
        <meta rel="dc:subject" href="http://dbpedia.org/page/Plant_breeding" />

        <!-- style sheets -->
        <link href="<s:url value="/css/themePBR.css"/>" rel="stylesheet" media="screen" type="text/css" title="Standaard" />

        <!-- taglib auto generated -->
        <s:head/>
        <!-- javascript. jQuery/ jQueryUI libraries are loaded this tag instead of the script element. -->
        <sj:head jqueryui="true" loadAtOnce="true" jquerytheme="cupertino" loadFromGoogle="true" />
        <!-- bootstrap plugin -->
        <sb:head includeScripts="true" includeScriptsValidation="true"/>

        <!-- other -->
        <script type="text/javascript" src="<s:url value="/js/jquery.blockUI.2.57.js"/>"></script>
        <script type="text/javascript" src="<s:url value="/js/jquery.breeDB.0.1.js"/>"></script>
        <script type="text/javascript">
            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-18743767-2']);
            _gaq.push(['_trackPageview']);

            (function() {
                var ga = document.createElement('script');
                ga.type = 'text/javascript';
                ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(ga, s);
            })();
        </script>
        <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
        <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <!-- custom scripts for individual pages -->
        <tiles:insertAttribute name="scripts" />
    </head>
    <body>
        <sj:div id="canvas">
            <!-- start of the header-->
            <sj:div id="header">
                <tiles:insertAttribute name="header" />
            </sj:div>
            <!-- end of the header-->
            <sj:div id="contentcontainer" cssClass="homepage">
                <sj:div id="maincontent">
                    <sj:div id="pagecontainer">
                        <!-- start of the navigation menu -->
                        <sj:div id="context">
                            <tiles:insertAttribute name="menu" />
                        </sj:div>
                        <!-- end of the navigation menu -->
                        <!-- start of the page bdy -->
                        <sj:div id="content">
                            <tiles:insertAttribute name="body" />
                        </sj:div>
                        <!-- end of the body -->
                    </sj:div>
                    <!--  end pagecontainer -->
                </sj:div>
                <!--  end maincontent -->
            </sj:div>
            <!--  end contentcontainer -->
            <!-- start of the footer -->
            <sj:div id ="footer">
                <tiles:insertAttribute name="footer" />
            </sj:div>
            <!-- end of the footer -->
        </sj:div>
        <!-- End canvas -->
    </body>
</html>