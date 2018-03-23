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

    <title>显示多个城市的不同肉类</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

</head>

<body>
<%
    double[][] data = new double[][]{
            {1310, 1220, 1110, 1000},
            {720, 700, 680, 640},
            {1130, 1020, 980, 800},
            {440, 400, 360, 300}};
    String[] rowKeys = {"pig", "beef", "chicken", "fish"};
    String[] columnKeys = {"广州", "深圳", "东莞", "佛山"};
    CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);
    JFreeChart chart = ChartFactory.createBarChart3D("四个城市的四个肉类的统计图", "type", "amout", dataset, PlotOrientation.VERTICAL, true, false, false);
    String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, session);
    String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
%>
<img alt="四个城市的四个肉类的销售情况" src="<%=graphURL %>" width="500" height="300" border="0" mapfile="#<%=filename %>">
</body>
</html>