<%@ page language="java" isErrorPage="true" %>
<head>
<title>Error!</title>
</head>
Ocurri� un error en la aplicaci�n
<% if (exception != null) { %>
<pre><% exception.printStackTrace(new java.io.PrintWriter(out)); %>
</pre>
<% } else { %>
Please check your log files for further information.
<% } %>