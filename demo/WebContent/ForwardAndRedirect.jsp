<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Forward and Redirect</title>
</head>
<body>
<!--Forward-->
<%-- <% request.getRequestDispatcher("index.jsp").forward(request, response); %> --%>
<!--Redirect-->
<% response.sendRedirect("index.jsp"); %>
</body>
</html>