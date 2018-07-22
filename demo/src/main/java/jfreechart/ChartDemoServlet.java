package jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wajian on 2016/8/28.
 */
public class ChartDemoServlet extends HttpServlet {
    //http://www.phpxs.com/code/1001530/
    private static final long serialVersionUID = 1L;

    public ChartDemoServlet() {
        super();
    }


    @Override
    @SuppressWarnings("deprecation")
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

        response.setContentType("image/jpeg");
//        DefaultPieDataset data = getDataSet();
//        CategoryDataset dataset = getDataSet2();
        DefaultCategoryDataset linedataset = createDataset();

        //JFreeChart chart = ChartFactory.createPieChart3D("水果产量图", data, true, false, false);

        /**
         JFreeChart chart = ChartFactory.createBarChart3D(
         "水果产量图", // 图表标题
         "水果", // 目录轴的显示标签
         "产量", // 数值轴的显示标签
         dataset, // 数据集
         PlotOrientation.VERTICAL, // 图表方向：水平、垂直
         true,  // 是否显示图例(对于简单的柱状图必须是 false)
         false, // 是否生成工具
         false  // 是否生成 URL 链接
         );
         **/

        JFreeChart chart = ChartFactory.createLineChart(
                "折线图", // 图表标题
                "时间", // 横轴显示标签
                "销售额(百万)", // 纵轴显示标签
                linedataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                true, // 是否显示图例标识(对于简单的柱状图必须是 false)
                true, // 是否生成工具
                false // 是否生成 URL 链接
        );

     /*
  JFreeChart chart=ChartFactory.createTimeSeriesChart(
    "编程词典全国销量统计"
    , "销售月份"
    , "销量（份）"
    , createtimedata()
    , false
    , false
    , false
    );
  */
  /*
   * jfreechart1.0.13生成的图片中，中文都以方框形式显示,中文乱码解决如下（包括：饼图、柱状图、时序图、折线图）。
   */
        /***设置饼图相关属性***/
  /*
  PiePlot pieplot = (PiePlot) chart.getPlot();//获取饼图区域对象。
  pieplot.setLabelFont(new java.awt.Font("宋体",0,12));
  pieplot.setNoDataMessage("无数据显示"); //没有数据的时候显示的内容
  pieplot.setCircular(false);
  pieplot.setLabelGap(0.02D);
  TextTitle txtTitle = null;
  txtTitle = chart.getTitle();
  java.awt.Font font = new java.awt.Font("宋体", Font.BOLD, 16);
  txtTitle.setFont(font);//解决标题中文乱码
  chart.getLegend().setItemFont(font);
  chart.setBackgroundPaint(new Color(232,232,232));//设定背景
  pieplot.setBackgroundPaint(Color.white); //设定图表数据显示部分背景色
  */

        /***设置柱状图、折线图相关属性***/

        CategoryPlot catplot = chart.getCategoryPlot();  //获取柱状图、折线图区域对象。
        CategoryAxis domainAxis = catplot.getDomainAxis();
        catplot.setNoDataMessage("无数据显示");//没有数据的时候显示的内容
        //列表标题
        TextTitle txtTitle;
        txtTitle = chart.getTitle();
        txtTitle.setFont(new java.awt.Font("黑体", Font.BOLD, 14));
        //水平底部列表
        domainAxis.setLabelFont(new java.awt.Font("黑体", Font.BOLD, 14));
        //水平底部标题
        domainAxis.setTickLabelFont(new java.awt.Font("宋体", Font.BOLD, 12));
        //垂直标题
        ValueAxis rangeAxis = catplot.getRangeAxis();//获取柱状
        rangeAxis.setLabelFont(new java.awt.Font("黑体", Font.BOLD, 15));
        chart.getLegend().setItemFont(new java.awt.Font("黑体", Font.BOLD, 15));
        //获得renderer
        LineAndShapeRenderer lineAndShapeRenderer = (LineAndShapeRenderer) catplot.getRenderer();
        //series 点（即数据点）可见
        lineAndShapeRenderer.setBaseShapesVisible(true);
     /*
     lineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(2.0F, 1, 1, 1.0F, new float[] {
       10F, 6F
       }, 0.0F)); //这里是虚线，默认是直线
     */


        /***设置时序图相关属性***/
        /**
         XYPlot xyplot = (XYPlot) chart.getPlot();//获取时序图区域对象。
         //列表标题
         TextTitle txtTitle = null;
         txtTitle = chart.getTitle();
         txtTitle.setFont(new java.awt.Font("黑体",Font.BOLD,15));
         //纵轴字体
         xyplot.getRangeAxis().setLabelFont(new java.awt.Font("宋体", Font.BOLD, 12));
         //横轴框里的标题字体:显示图例
         //chart.getLegend().setItemFont(new java.awt.Font("宋体", Font.ITALIC, 12));
         //横轴列表字体
         xyplot.getDomainAxis().setTickLabelFont(new java.awt.Font("新宋体", 1, 12));
         //横轴小标题字体
         xyplot.getDomainAxis().setLabelFont(new java.awt.Font("新宋体", 1, 12));
         chart.setBackgroundPaint(new Color(252,175,134)); //设置背景色
         xyplot.setDomainGridlinesVisible(false);  //设置网格不显示

         //获取时间轴对象
         DateAxis dateAxis = (DateAxis) xyplot.getDomainAxis();
         //dateAxis.setLabelFont(new java.awt.Font("黑体", Font.ITALIC , 18));   //设置时间轴字体
         dateAxis.setLowerMargin(0.0);//设置时间轴上显示的最小值
         DateFormat format = new SimpleDateFormat("MM月份");   //创建日期格式对象
         //创建DateTickUnit对象
         DateTickUnit dtu = new DateTickUnit(DateTickUnitType.DAY,29,format);
         dateAxis.setTickUnit(dtu);//设置日期轴的日期标签
         **/

        //chart.getTitle().setFont(new java.awt.Font("宋体", Font.BOLD, 15));
        //chart.getLegend().setItemFont(new java.awt.Font("黑体", Font.BOLD, 15));
        //CategoryAxis domainAxis = plot.getDomainAxis();
     /*------设置X轴坐标上的文字-----------*/
        //domainAxis.setTickLabelFont(new java.awt.Font("黑体", Font.PLAIN, 11));
     /*------设置X轴的标题文字------------*/
        //domainAxis.setLabelFont(new java.awt.Font("宋体", Font.PLAIN, 12));
        //NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
     /*------设置Y轴坐标上的文字-----------*/
        //numberaxis.setTickLabelFont(new java.awt.Font("黑体", Font.PLAIN, 12));
     /*------设置Y轴的标题文字------------*/
        //numberaxis.setLabelFont(new java.awt.Font("黑体", Font.PLAIN, 12));


        FileOutputStream jpg = null;
        try {
            jpg = new FileOutputStream("D:\\ok_bing.jpg");
   /*
    * 第二个参数如果为100，会报异常：
    * java.lang.IllegalArgumentException: The 'quality' must be in the range 0.0f to 1.0f
    * 限制quality必须小于等于1,把100改成 0.1f。
    */
            ChartUtilities.writeChartAsJPEG(jpg, 0.99f, chart, 600, 300, null);
        } catch (Exception e) {
            System.out.println("[e]" + e);
        } finally {
            try {
                if (jpg != null) {
                    jpg.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取一个演示用的简单数据集对象
     */
    //生成饼图数据
    private static DefaultPieDataset getDataSet() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("apple", 100);
        dataset.setValue("pear", 200);
        dataset.setValue("grape", 300);
        dataset.setValue("banana", 400);
        dataset.setValue("litchi", 500);
        return dataset;
    }

    //生成柱状图数据
    private static CategoryDataset getDataSet2() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100, "BJ", "apple");
        dataset.addValue(100, "SH", "apple");
        dataset.addValue(100, "GZ", "apple");
        dataset.addValue(200, "BJ", "pear");
        dataset.addValue(200, "SH", "pear");
        dataset.addValue(200, "GZ", "pear");
        dataset.addValue(300, "BJ", "grape");
        dataset.addValue(300, "SH", "grape");
        dataset.addValue(300, "GZ", "grape");
        dataset.addValue(400, "BJ", "banana");
        dataset.addValue(400, "SH", "banana");
        dataset.addValue(400, "GZ", "banana");
        dataset.addValue(500, "BJ", "litchi");
        dataset.addValue(500, "SH", "litchi");
        dataset.addValue(500, "GZ", "litchi");
        return dataset;
    }

    //生成折线图数据
    private static DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
        //  各曲线名称
        String series1 = "冰箱";
        String series2 = "彩电";
        String series3 = "洗衣机";

        //横轴名称(列名称)
        String type1 = "1月";
        String type2 = "2月";
        String type3 = "3月";
        String type4 = "4月";
        String type5 = "5月";

        linedataset.addValue(0.0, series1, type1);
        linedataset.addValue(4.2, series1, type2);
        linedataset.addValue(3.9, series1, type3);
        linedataset.addValue(3.2, series1, type4);
        linedataset.addValue(3.0, series1, type5);

        linedataset.addValue(1.0, series2, type1);
        linedataset.addValue(5.2, series2, type2);
        linedataset.addValue(7.9, series2, type3);
        linedataset.addValue(8.9, series2, type4);
        linedataset.addValue(9.2, series2, type5);

        linedataset.addValue(2.0, series3, type1);
        linedataset.addValue(9.2, series3, type2);
        linedataset.addValue(8.9, series3, type3);
        linedataset.addValue(9.9, series3, type4);
        linedataset.addValue(10.9, series3, type5);

        return linedataset;
    }

    //生成时序图数据
    public static XYDataset createtimedata() {
        TimeSeries timeseries = new TimeSeries("Data");
        Day day = new Day(1, 1, 2008);
        double d = 3000D;
        for (int i = 0; i < 365; i++) {
            d = d + (Math.random() - 0.5) * 10;
            timeseries.add(day, d);
            day = (Day) day.next();
        }
        return new TimeSeriesCollection(timeseries);
    }
}
