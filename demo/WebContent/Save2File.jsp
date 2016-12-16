<%-- <%@ page import="org.springframework.web.context.support.*,org.springframework.context.*" %>  --%>
<%-- <%@ page import="netscape.ldap.factory.JSSESocketFactory,netscape.ldap.LDAPSearchResults" %> 
<%@ page import="netscape.ldap.LDAPConnection,netscape.ldap.LDAPAttribute,netscape.ldap.LDAPEntry" %> --%>
<%@ page import="java.util.*" %> 
<%@ page import="java.io.*" %> 
<html>
<head>
 <meta http-equiv="X-UA-Compatible" content="IE=9">
</head>
<body>
<% 
long time = System.currentTimeMillis();
try{
	String ss ="aaaaa";
    File file = new File("D:\\Java_ex\\test\\src\\test\\resources\\qqq.txt");
	if (!file.exists())
		 file.createNewFile(); 
	FileOutputStream fos = new FileOutputStream(file);
	for(int i=0; i<10; i++){
		fos.write(ss.getBytes());
	}
}
catch(Exception e){
	java.io.ByteArrayOutputStream byteout = new java.io.ByteArrayOutputStream();
	java.io.PrintStream s = new java.io.PrintStream(byteout);
	e.printStackTrace(s);
%>
<%=byteout.toString()%>
<%
}
%>
</body>
</html>
