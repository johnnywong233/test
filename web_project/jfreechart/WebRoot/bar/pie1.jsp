<%@ page language="java" import="org.jfree.chart.ChartFactory" pageEncoding="UTF-8" %>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.plot.PiePlot" %>
<%@page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@page import="org.jfree.data.general.DefaultPieDataset" %>
<%@page import="java.awt.Font" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>简单的饼图</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

</head>

<body>
<%
    //默认的饼图数据集类，用来存储饼图显示的相关数据信息
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("品德", new Double(0.2D));
    dataset.setValue("体育", new Double(0.2D));
    dataset.setValue("音乐", new Double(0.2D));
    dataset.setValue("其余成绩", new Double(0.4D));
    //可利用该制图工厂类createPieChart来创建一个饼图的JFreeChart对象
    JFreeChart jfreeChart = ChartFactory.createPieChart("饼图示例", dataset, true, true, false);
    //饼图绘制类，可以用来设置饼图的相关属性
    PiePlot pieplot = (PiePlot) jfreeChart.getPlot();
    pieplot.setLabelFont(new Font("SansSerif", 0, 12));
    pieplot.setNoDataMessage("NO data available");
    pieplot.setCircular(false);
    pieplot.setLabelGap(0.02D);
    String filename = ServletUtilities.saveChartAsPNG(jfreeChart, 500, 300, null, session);
    String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;

%>
<img alt="饼图示例" src="<%=graphURL %>" width="500" height="300" border="0" mapfile="#<%=filename %>">
</body>
</html>
