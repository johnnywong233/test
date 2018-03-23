<%@ page language="java" import="org.jfree.chart.ChartFactory" pageEncoding="UTF-8" %>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.plot.PlotOrientation" %>
<%@page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@page import="org.jfree.data.category.CategoryDataset" %>
<%@page import="org.jfree.data.general.DatasetUtilities" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>用不同颜色来表示不同的种类</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    用不同颜色来表示不同的种类，或者在种类上标上具体数值，亦或需要加上3D水晶效果
    -->

</head>

<body>
<%
    double[][] data = new double[][]{{1310}, {720}, {1130}, {440}};
    String[] rowKeys = {"pig", "beef", "chicken", "fish"};
    String[] columnKeys = {""};
    CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
    //true:该参数可用来显示下方的方框
    JFreeChart chart = ChartFactory.createBarChart3D("广州肉类销量统计图", "type", "amout", dataset, PlotOrientation.VERTICAL, true, false, false);
    String fileName = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, session);
    String graphURL = request.getContextPath() + "/DisplayChart?filename=" + fileName;
%>
<img alt="广州肉类销量统计图" src="<%=graphURL %>" width="500" height="300" border="0" usemap="#<%=fileName %>"/>
</body>
</html>
