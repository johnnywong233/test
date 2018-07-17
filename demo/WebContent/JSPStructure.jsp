<!--
Fig. 6.01_01: JSPStructure.jsp
功能: JSP文件结构
-->
<%@ page language="java" contentType="text/html;charset=GB2312" %>
<HTML>
<HEAD>
    <TITLE> JSP文件结构 </TITLE>
</HEAD>

<BODY>
<%
    int a = 55, b = 45;
%>
整数: 55 + 45 的结果是: <%= a + b%>
<BR><BR>
整数: 55 - 45 的结果是: <%= a - b%>
</BODY>
</HTML>