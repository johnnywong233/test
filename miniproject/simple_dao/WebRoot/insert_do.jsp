<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.johnny.factory.DAOFactory" %>
<%@page import="com.johnny.vo.Myemp" %>
<html>
<head><title>This is DAO demo check</title></head>
<% request.setCharacterEncoding("UTF-8"); %>
<body>
<%
    Myemp emp = new Myemp();
    emp.setUsername(request.getParameter("username"));
    emp.setPassword(request.getParameter("password"));
    try {
        if (DAOFactory.getIEmpDAOInstance().doCreate(emp)) {
%>
<h3>register success！</h3>
<%
} else {
%>
<h3>register fail！</h3>
<%
    }
%>
<%
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
</body>
</html>