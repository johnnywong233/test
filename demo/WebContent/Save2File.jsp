<%@ page import="java.io.File" %>
<%@ page import="java.io.FileOutputStream" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9">
</head>
<body>
<%
    long time = System.currentTimeMillis();
    try {
        String ss = "aaaaa";
        File file = new File("D:\\Java_ex\\test\\src\\test\\resources\\qqq.txt");
        if (!file.exists())
            file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        for (int i = 0; i < 10; i++) {
            fos.write(ss.getBytes());
        }
    } catch (Exception e) {
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
