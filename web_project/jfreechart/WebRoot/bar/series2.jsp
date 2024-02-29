<%@ page import="org.jfree.chart.ChartFactory" pageEncoding="UTF-8" %>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.labels.ItemLabelAnchor" %>
<%@page import="org.jfree.chart.labels.ItemLabelPosition" %>
<%@page import="org.jfree.chart.labels.StandardXYItemLabelGenerator" %>
<%@page import="org.jfree.chart.plot.XYPlot" %>
<%@page import="org.jfree.chart.renderer.xy.XYItemRenderer" %>
<%@page import="org.jfree.chart.renderer.xy.XYLineAndShapeRenderer" %>
<%@page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@page import="org.jfree.chart.title.TextTitle" %>
<%@page import="org.jfree.data.time.Month" %>
<%@page import="org.jfree.data.time.TimeSeries" %>
<%@page import="org.jfree.data.time.TimeSeriesCollection" %>
<%@page import="org.jfree.ui.RectangleInsets" %>
<%@page import="org.jfree.ui.TextAnchor" %>
<%@page import="java.awt.Color" %>
<%@page import="java.awt.Font" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>曲线图(显示各数据点及其数值)</title>

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
    TimeSeries timeSeries = new TimeSeries("阿蜜果blog访问量统计", Month.class);
    //时间曲线数据集合
    TimeSeriesCollection lineDataset = new TimeSeriesCollection();
    //构造数据集合
    timeSeries.add(new Month(1, 2007), 11200);
    timeSeries.add(new Month(2, 2007), 9000);
    timeSeries.add(new Month(3, 2007), 6200);
    timeSeries.add(new Month(4, 2007), 8200);
    timeSeries.add(new Month(5, 2007), 8200);
    timeSeries.add(new Month(6, 2007), 12200);
    timeSeries.add(new Month(7, 2007), 13200);
    timeSeries.add(new Month(8, 2007), 8300);
    timeSeries.add(new Month(9, 2007), 12400);
    timeSeries.add(new Month(10, 2007), 12500);
    timeSeries.add(new Month(11, 2007), 13600);
    timeSeries.add(new Month(12, 2007), 12500);
    lineDataset.addSeries(timeSeries);

    JFreeChart chart = ChartFactory.createTimeSeriesChart("访问量统计时间线", "month", "visitamount", lineDataset, true, true, true);
    XYPlot plot = (XYPlot) chart.getXYPlot();
    XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) plot.getRenderer();
    //设置网格背景颜色
    plot.setBackgroundPaint(Color.gray);
    //设置网格竖线颜色
    plot.setDomainGridlinePaint(Color.pink);
    //设置网格横线颜色
    plot.setRangeGridlinePaint(Color.pink);
    //设置曲线图与xy轴的距离
    plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));
    //设置曲线是否显示数据点
    xylineandshaperenderer.setBaseShapesVisible(true);
    //设置曲线显示各数据点的值
    XYItemRenderer xyitem = plot.getRenderer();
    xyitem.setBaseItemLabelsVisible(true);
    xyitem.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
    xyitem.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
    xyitem.setBaseItemLabelFont(new Font("Dialog", 1, 14));
    plot.setRenderer(xyitem);
    //设置子标题
    TextTitle subtitle = new TextTitle("2007年度", new Font("黑体", Font.BOLD, 12));
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
