<!--
Fig. 6.01_01: JSPStructure.jsp
����: JSP�ļ��ṹ
-->
<%@ page language="java" contentType="text/html;charset=GB2312" %>
<HTML>
<HEAD>
    <TITLE> JSP�ļ��ṹ </TITLE>
</HEAD>

<BODY>
<%
    int a = 55, b = 45;
%>
����: 55 + 45 �Ľ����: <%= a + b%>
<BR><BR>
����: 55 - 45 �Ľ����: <%= a - b%>
</BODY>
</HTML>