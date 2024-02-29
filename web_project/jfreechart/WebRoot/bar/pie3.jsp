<%@ page import="org.jfree.chart.ChartFactory" pageEncoding="UTF-8" %>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.plot.PiePlot3D" %>
<%@page import="org.jfree.chart.servlet.ServletUtilities" %>
<%@page import="org.jfree.data.general.DefaultPieDataset" %>
<%@page import="org.jfree.util.Rotation" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>漂亮的水晶饼图</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <!-- 利用setForegroundAlpha()方法可以设置3D饼图的透明度，利用setStartAngle()可以设置其开始角度，利用setDirection()方法可以设置其方向 -->
</head>

<body>
<%
    //设置数据集
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("出高级程序员", 0.55);
    dataset.setValue("项目经理", 0.1);
    dataset.setValue("软件架构师", 0.1);
    dataset.setValue("系统架构师", 0.1);
    dataset.setValue("其他", 0.2);
    //通过工厂类生成JFreeChart对象
    JFreeChart chart = ChartFactory.createPieChart3D("IT行业职业分布图", dataset, true, true, false);
    //获得3D水晶饼图对象
    PiePlot3D pieplot3d = (PiePlot3D) chart.getPlot();
    //设置开始角度
    pieplot3d.setStartAngle(150D);
    //设置方向为顺时针方向
    pieplot3d.setDirection(Rotation.CLOCKWISE);
    //设置透明度 0.5F为半透明，1为不透明，0为全透明 
    pieplot3d.setForegroundAlpha(0.5F);
    pieplot3d.setNoDataMessage("无数据显示");

    String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, null, session);
    String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename;

%>
<img alt="IT行业职业分布图" src="<%=graphURL %>" width="500" height="300" border="0" mapfile="#<%=filename %>">
</body>
</html>
