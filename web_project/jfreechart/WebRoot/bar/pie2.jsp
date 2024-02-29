<%@ page import="org.jfree.chart.ChartFactory" pageEncoding="UTF-8" %>
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
    <title>饼图 </title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
</head>
<body>
<%
    //设置数据集
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("初高级程序员", 0.55);
    dataset.setValue("项目经理", 0.1);
    dataset.setValue("系统架构师", 0.1);
    dataset.setValue("软件架构师", 0.1);
    dataset.setValue("其它", 0.2);
    //通过工厂类生成JFreeChart对象
    JFreeChart chart = ChartFactory.createPieChart3D("IT行业职业分布图", dataset, true, false, false);
    PiePlot piePlot = (PiePlot) chart.getPlot();
    piePlot.setLabelFont(new Font("宋体", 0, 12));
    //没有数据的时候显示内容
    piePlot.setNoDataMessage("无数据显示");
    piePlot.setCircular(false);
    piePlot.setLabelGap(0.02D);
    String fileName = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, session);
    String graphURL = request.getContextPath() + "/DisplayChart?filename=" + fileName;
%>
<img alt="IT行业职业分布图" src="<%=graphURL %>" width="500" height="300" border="0" mapfile="#<%=fileName %>">
</body>
</html>