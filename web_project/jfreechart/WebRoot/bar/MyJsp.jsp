<%@page import="com.itextpdf.text.*" pageEncoding="UTF-8" %>
<%@page import="com.itextpdf.text.pdf.BaseFont" %>
<%@page import="com.itextpdf.text.pdf.PdfPCell" %>
<%@page import="com.itextpdf.text.pdf.PdfPTable" %>
<%@page import="com.itextpdf.text.pdf.PdfWriter" %>
<%@page import="org.jfree.chart.ChartFactory" %>
<%@page import="org.jfree.chart.JFreeChart" %>
<%@page import="org.jfree.chart.title.TextTitle" %>
<%@page import="org.jfree.data.time.Month" %>
<%@page import="org.jfree.data.time.TimeSeries" %>
<%@page import="org.jfree.data.time.TimeSeriesCollection" %>
<%@page import="java.awt.image.BufferedImage" %>
<%@page import="java.io.*" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>My JSP 'MyJsp.jsp' starting page</title>

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

    //AsianFontMapper mapper = new AsianFontMapper("STSong-Light","UniGB-UCS2-H");// 解决中文乱码

    //BaseFont bf = BaseFont.createFont("STSongStd-Light",
    //	"UniGB-UCS2-H", false);
    //Font f = new Font(bf, 12, Font.NORMAL, BaseColor.BLUE);

    BaseFont bfChinese;
    try {
        bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
    } catch (DocumentException e) {
        throw new RuntimeException(e);
    }
    Font chineseFont = new Font(bfChinese, 12, Font.BOLD);
    //访问量统计时间线
    TimeSeries timeSeries2006 = new TimeSeries("2006年度");
    TimeSeries timeSeries2007 = new TimeSeries("2007年度");

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

    JFreeChart chart = ChartFactory.createTimeSeriesChart("访问量统计时间线", "month", "visitAmount", lineDataset, true, true, true);
    //设置子标题
    //TextTitle subtitle = new TextTitle("2006/2007年度访问量对比",new Font("黑体",Font.BOLD,12));
    TextTitle subtitle = new TextTitle("2006/2007年度访问量对比");
    chart.addSubtitle(subtitle);
    //设置主标题
    //chart.setTitle(new TextTitle("阿蜜果blog访问量统计",new Font("隶书",Font.ITALIC,15)));
    chart.setTitle(new TextTitle("阿蜜果blog访问量统计"));
    chart.setAntiAlias(true);
    //String filename = ServletUtilities.saveChartAsPNG(chart,500,300,null,session);
    //String graphURL = request.getContextPath()+"/DisplayChart?filename="+filename;
    BufferedImage bufferImage = chart.createBufferedImage(500, 300);


    Document document = new Document(PageSize.A4);
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    try {

        PdfWriter writer = PdfWriter.getInstance(document, buffer);
        document.open();

        Image image = Image.getInstance(bufferImage, null, false);

        float[] widths = {1f, 4f};
        //float[] widths = {100f};
        PdfPTable table = new PdfPTable(widths);
        table.addCell(new PdfPCell(new Paragraph("图片测试", chineseFont)));
        table.addCell(new PdfPCell(new Paragraph("")));
        table.addCell(new PdfPCell(new Paragraph("图片测试", chineseFont)));
        table.addCell(new PdfPCell(new Paragraph("")));
        //插入图片
        table.addCell(image);

        //调整图片大小
        table.addCell("This two");
        table.addCell(new PdfPCell(image, true));

        //不调整图片大小
        //(小结：这样就不显示图片了,最后发现是因为图片太大，超过了页面的显示范围了，换一个小点的像素就显示了)
        //table.addCell("This there");
        //table.addCell(new PdfPCell(image,false));
        image.setAbsolutePosition(0, 0);
        table.addCell(image);
        document.add(table);
    } catch (DocumentException | IOException e) {
        e.printStackTrace();
    } finally {
        document.close();
    }
    OutputStream o = null;
    try {
        o = response.getOutputStream();
        DataOutput output = new DataOutputStream(o == null ? (response.getOutputStream()) : o);
        byte[] bytes = buffer.toByteArray();
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        for (byte aByte : bytes) {
            output.writeByte(aByte);
        }
        o.flush();
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (o != null) {
            out.clear();
            out = pageContext.pushBody();
            o.close();
        }
    }
%>

</body>
</html>
