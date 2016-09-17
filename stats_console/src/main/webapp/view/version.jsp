<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.util.Properties"%>
<%
	Properties properties = new Properties();
	InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties");
	properties.load(input);
	input.close();
	String version = properties.getProperty("application.version");
%>
Version: <%=version %>