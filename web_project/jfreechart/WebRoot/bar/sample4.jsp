<%@ page language="java" import="org.jfree.chart.ChartFactory" pageEncoding="UTF-8" %>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.axis.AxisLocation" %>
<%@page import="org.jfree.chart.labels.ItemLabelAnchor" %>
<%@page import="org.jfree.chart.labels.ItemLabelPosition" %>
<%@page import="org.jfree.chart.labels.StandardCategoryItemLabelGenerator" %>
<%@page import="org.jfree.chart.plot.CategoryPlot" %>
<%@page import="org.jfree.chart.plot.PlotOrientation" %>
<%@page import="org.jfree.chart.renderer.category.BarRenderer3D" %>
<%@page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@page import="org.jfree.data.category.CategoryDataset" %>
<%@page import="org.jfree.data.general.DatasetUtilities" %>
<%@page import="org.jfree.ui.TextAnchor" %>
<%@page import="java.awt.Color" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>在某个柱子上显示相应的数值</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <!--

        org.jfree.chart.JFreeChart：
            图表对象，任何类型的图表的最终表现形式都是在该对象进行一些属性的定制。JFreeChart引擎本身提供了一个工厂类用于创建不同类型的图表对象
        org.jfree.data.category.XXXDataSet:
            数据集对象，用于提供显示图表所用的数据。根据不同类型的图表对应着很多类型的数据集对象类
        org.jfree.chart.plot.XXXPlot：
                图表区域对象，基本上这个对象决定着什么样式的图表，创建该对象的时候需要Axis、Renderer以及数据集对象的支持
        org.jfree.chart.axis.XXXAxis：
                用于处理图表的两个轴：纵轴和横轴
        org.jfree.chart.render.XXXRender：
            负责如何显示一个图表对象
        org.jfree.chart.urls.XXXURLGenerator:
            用于生成Web图表中每个项目的鼠标点击链接
        XXXXXToolTipGenerator:
            用于生成图象的帮助提示，不同类型图表对应不同类型的工具提示类

     -->
</head>

<body>
<%
    double[][] data = new double[][]{
            {1310, 1220, 1110, 1000},
            {720, 700, 680, 640},
            {1130, 1020, 980, 800},
            {440, 400, 360, 300}};
    String[] rowKeys = {"pig", "beef", "chicken", "fish"};
    String[] coumnKeys = {"深圳", "广州", "东莞", "佛山"};
    CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, coumnKeys, data);
    JFreeChart chart = ChartFactory.createBarChart3D("肉类销售统计图", "type", "amount", dataset, PlotOrientation.VERTICAL, true, true, false);
    CategoryPlot plot = chart.getCategoryPlot();
    //设置网络背景颜色
    plot.setBackgroundPaint(Color.gray);
    //设置网络竖线颜色
    plot.setDomainGridlinePaint(Color.pink);
    //设置网络横线颜色
    plot.setRangeGridlinePaint(Color.pink);
    //显示每个柱的数值，并修改数值的具体属性
    BarRenderer3D renderer = new BarRenderer3D();
    renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    renderer.setBaseItemLabelsVisible(true);
    //默认的数字显示在柱子中，通过如下两句可调整数字的显示
    //注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题
    renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
    renderer.setItemLabelAnchorOffset(10D);
    //设置每个地区包含的平行柱之间的距离
    plot.setRenderer(renderer);
    //设置地区，销量显示位置
    //将下放的肉类放到上方
    plot.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
    //将默认放到左边的销量放到右边
    plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
    String filename = ServletUtilities.saveChartAsPNG(chart, 700, 400, null, session);
    String graphURl = request.getContextPath() + "/DisplayChart?filename=" + filename;

%>
<img alt="肉类销售统计图" src="<%=graphURl %>" width="700" height="400" mapfile="#<%=filename %>">
</body>
</html>