<%@ page language="java" import="org.jfree.chart.ChartFactory" pageEncoding="UTF-8" %>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@page import="org.jfree.chart.title.TextTitle" %>
<%@page import="org.jfree.data.time.Month" %>
<%@page import="org.jfree.data.time.TimeSeries" %>
<%@page import="org.jfree.data.time.TimeSeriesCollection" %>
<%@page import="java.awt.Font" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>双曲线图</title>

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
    //访问量统计时间线
    TimeSeries timeSeries2006 = new TimeSeries("2006年度", Month.class);
    TimeSeries timeSeries2007 = new TimeSeries("2007年度", Month.class);

    //时间曲线数据集合
    TimeSeriesCollection lineDataset = new TimeSeriesCollection();
    //构造数据集合
    timeSeries2006.add(new Month(1, 2007), 7200);
    timeSeries2006.add(new Month(2, 2007), 7000);
    timeSeries2006.add(new Month(3, 2007), 4200);
    timeSeries2006.add(new Month(4, 2007), 8200);
    timeSeries2006.add(new Month(5, 2007), 7300);
    timeSeries2006.add(new Month(6, 2007), 8200);
    timeSeries2006.add(new Month(7, 2007), 9200);
    timeSeries2006.add(new Month(8, 2007), 7300);
    timeSeries2006.add(new Month(9, 2007), 9400);
    timeSeries2006.add(new Month(10, 2007), 7500);
    timeSeries2006.add(new Month(11, 2007), 6600);
    timeSeries2006.add(new Month(12, 2007), 3500);
    timeSeries2007.add(new Month(1, 2007), 10200);
    timeSeries2007.add(new Month(2, 2007), 9000);
    timeSeries2007.add(new Month(3, 2007), 6200);
    timeSeries2007.add(new Month(4, 2007), 8200);
    timeSeries2007.add(new Month(5, 2007), 8200);
    timeSeries2007.add(new Month(6, 2007), 11200);
    timeSeries2007.add(new Month(7, 2007), 13200);
    timeSeries2007.add(new Month(8, 2007), 8300);
    timeSeries2007.add(new Month(9, 2007), 10400);
    timeSeries2007.add(new Month(10, 2007), 12500);
    timeSeries2007.add(new Month(11, 2007), 10600);
    timeSeries2007.add(new Month(12, 2007), 10500);
    lineDataset.addSeries(timeSeries2006);
    lineDataset.addSeries(timeSeries2007);

    JFreeChart chart = ChartFactory.createTimeSeriesChart("访问量统计时间线", "month", "visitamount", lineDataset, true, true, true);
    //设置子标题
    TextTitle subtitle = new TextTitle("2006/2007年度访问量对比", new Font("黑体", Font.BOLD, 12));
    chart.addSubtitle(subtitle);
    //设置主标题
    chart.setTitle(new TextTitle("阿蜜果blog访问量统计", new Font("隶书", Font.ITALIC, 15)));
    chart.setAntiAlias(true);
    String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, session);
    String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;
%>
<img alt="阿蜜果blog访问量统计" src="<%=graphURL %>" width="500" height="300" border="0" mapfile="#<%=filename %>">
</body>
</html>
