package jfreechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Created by johnny on 2016/10/7.
 * simple and silly demo of jfeee.chart
 */
public class LineChartTest {
    //http://www.codeceo.com/article/jfreechart-struts2.html
    public static void main(String[] args) throws IOException {
        //step 1: create CategoryDataset object（prepare data set）
        CategoryDataset dataset = createDataset();
        //step 2：根据Dataset 生成JFreeChart对象，以及做相应的设置
        JFreeChart jfreeChart = createChart(dataset);
        //step 3：output JFreeChart object to a jpg
        saveAsFile("D:\\Java_ex\\test\\src\\test\\resources\\LineChart.jpg", jfreeChart, 800, 600);
    }

    /**
     * 创建一个dataset，该dataset包含图表要显示的数据
     *
     * @return CategoryDataset
     */
    private static CategoryDataset createDataset() {
        // 图例名称
        String[] lines = {"文学类", "科技类", "财经类", "娱乐类"};
        // 类别
        String[] categories = {"2008年", "2009年", "2010年", "2012年", "2013年"};
        Random random = new Random();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // 使用循环向数据集合中添加数据
        for (String line : lines) {
            for (String category : categories) {
                dataset.addValue(100000 + random.nextInt(100000), line, category);
            }
        }
        return dataset;
    }

    /**
     * 根据PieDataset创建JFreeChart对象
     *
     * @return JFreeChart
     */
    private static JFreeChart createChart(CategoryDataset categoryDataset) {
        //JFreeChart类是一个制图对象类，先用它来创建一个制图对象chart
        //ChartFactory类是制图工厂类，用它来为制图对象chart完成实例化
        //createLineChart()是制图工厂的一个方法，用来创建一个常规的折线图对象
        JFreeChart chart = ChartFactory.createLineChart(
                "图书销量统计图",                 //图表标题
                "年份",                        //X轴标题
                "销售数量(本)",                        //Y轴标题
                categoryDataset,              //数据集
                PlotOrientation.VERTICAL,     //绘制方向
                true,                         //是否显示图例
                false,                        //是否采用标准生成器
                false                         //是否支持超链接
        );
        //通过JFreeChart对象的 setTitle方法，修改统计图表的标题部分（包括修改图表标题内容、字体大小等）
        chart.setTitle(new TextTitle("图书销量统计图", new Font("黑体", Font.ITALIC, 22)));
        //调用 JFreeChart对象的 getLegend(int index)方法，取得该图表的指定索引的图例对象，通过 LegendTitle对象来修改统计图表的图例
        LegendTitle legend = chart.getLegend(0);
        //设置图例的字体和字体大小，即位于下方的字的字体和大小
        legend.setItemFont(new Font("宋体", Font.BOLD, 14));
        // 设置画布背景色
        chart.setBackgroundPaint(new Color(192, 228, 106));
        //取得折线图的绘图(plot)对象
        CategoryPlot plot = chart.getCategoryPlot();
        //设置数据区的背景透明度，范围在0.0～1.0间
        plot.setBackgroundAlpha(0.5f);
        // 设置数据区的前景透明度，范围在0.0～1.0间
        plot.setForegroundAlpha(0.5f);
        // 设置横轴字体
        plot.getDomainAxis().setLabelFont(new Font("黑体", Font.BOLD, 14));
        // 设置坐标轴标尺值字体
        plot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.BOLD, 12));
        // 设置纵轴字体
        plot.getRangeAxis().setLabelFont(new Font("黑体", Font.BOLD, 14));
        // 设置绘图区背景色
        plot.setBackgroundPaint(Color.WHITE);
        // 设置水平方向背景线颜色
        plot.setRangeGridlinePaint(Color.BLACK);
        // 设置是否显示水平方向背景线,默认值为true
        plot.setRangeGridlinesVisible(true);
        // 设置垂直方向背景线颜色
        plot.setDomainGridlinePaint(Color.BLACK);
        // 设置是否显示垂直方向背景线,默认值为false
        plot.setDomainGridlinesVisible(true);
        // 没有数据时显示的消息
        plot.setNoDataMessage("没有相关统计数据");
        plot.setNoDataMessageFont(new Font("黑体", Font.CENTER_BASELINE, 16));
        plot.setNoDataMessagePaint(Color.RED);
        // 获取折线对象
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot
                .getRenderer();
        //设置折点处以某种形状凸出
        renderer.setShapesVisible(true);
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setFillPaint(java.awt.Color.WHITE);
        //设置显示折点处的数据值
        //renderer.setBaseItemLabelGenerator (new StandardCategoryItemLabelGenerator ());
        //renderer.setItemLabelFont (new Font ("黑体", Font.PLAIN, 12));
        //renderer.setItemLabelsVisible (true);
        BasicStroke realLine = new BasicStroke(2.0f); // 设置实线
        float dashes[] = {8.0f}; // 定义虚线数组
        BasicStroke brokenLine = new BasicStroke(2.0f, // 线条粗细
                BasicStroke.CAP_SQUARE, // 端点风格
                BasicStroke.JOIN_MITER, // 折点风格
                8.f, // 折点处理办法
                dashes, // 虚线数组
                0.0f); // 虚线偏移量
        // 利用虚线绘制
        renderer.setSeriesStroke(0, brokenLine);
        // 利用虚线绘制
        renderer.setSeriesStroke(1, brokenLine);
        // 利用实线绘制
        renderer.setSeriesStroke(2, realLine);
        // 利用实线绘制
        renderer.setSeriesStroke(3, realLine);
        //设置折线的颜色
        renderer.setSeriesPaint(0, Color.BLACK);
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesPaint(2, Color.BLUE);
        renderer.setSeriesPaint(3, Color.MAGENTA);
        return chart;
    }

    /**
     * 保存图表为文件
     */
    private static void saveAsFile(String filePath, JFreeChart jfreeChart,
                                   int weight, int height) throws IOException {
        //输出图表到文件，saveCharAsJPEG()方法的参数(File file,JFreeChart chart,int width,int height)
        ChartUtilities.saveChartAsJPEG(new File(filePath), jfreeChart, weight, height);
    }
}
