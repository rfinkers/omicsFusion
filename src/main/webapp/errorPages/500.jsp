<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%--<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="500" />--%>

  <%@ page isErrorPage="true" import="java.io.PrintWriter" %>

  <html><body>

  <h1 style="color: red">Error - 500</h1>

  <pre>
  <%
  // unwrap ServletExceptions.
  while (exception instanceof ServletException)
    exception = ((ServletException) exception).getRootCause();

  // print stack trace.
  exception.printStackTrace(new PrintWriter(out));
  %>
  </pre>

  </body></html>
