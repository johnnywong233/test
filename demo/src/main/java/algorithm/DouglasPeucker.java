package algorithm;

import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

/**
 * @author Johnny
 * @date 2018/9/18-14:06
 * https://www.cnblogs.com/xdlwd086/p/5100425.html
 * https://blog.csdn.net/qq_27717921/article/details/52316791
 */
public class DouglasPeucker {
    private String filePath;
    private double threshold;
    private ArrayList<Point> pointList;
    private ArrayList<String[]> listTemp;//存放从文件中读入的数据
    private ArrayList<Line> lineList = new ArrayList<>();
    private ArrayList<Line> resultLine = new ArrayList<>();//存储一条曲线压缩完成后的结果

    //存储所有被压缩完成的线段;
    private ArrayList<ArrayList<Line>> compressionCollection = new ArrayList<>();

    public DouglasPeucker(String filePath, double threshold) {
        this.filePath = filePath;
        this.threshold = threshold;
        readDataFile();
        initPointAndLine();
    }

    private void readDataFile() {
        File file = new File(filePath);
        listTemp = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            String[] strTemp;
            while ((str = in.readLine()) != null) {
                strTemp = str.split(" ");
                listTemp.add(strTemp);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPointAndLine() {
        Line line;
        for (String[] aListTemp : listTemp) {
            pointList = new ArrayList<>();
            Point point;
            String st;
            int start;
            int end1;
            int end2;
            for (String aStr : aListTemp) {
                st = aStr;
                start = st.indexOf("(");
                end1 = st.indexOf(",");
                end2 = st.indexOf(")");
                point = new Point(st.substring(start + 1, end1), st.substring(end1 + 1, end2));
                pointList.add(point);

            }
            line = new Line(pointList);
            lineList.add(line);
        }
    }

    private void compressLine(Line line) {
        Line lineTemp;
        if (line.getDistance() < threshold) {
            //说明可以利用这条线段来代替曲线
            lineTemp = new Line(line.getStart(), line.getEnd());
            resultLine.add(lineTemp);
        } else {
            ArrayList<Point> pointsTemp1 = new ArrayList<>();
            ArrayList<Point> pointsTemp2 = new ArrayList<>();
            for (int i = 0; i <= line.getIndex(); i++) {
                pointsTemp1.add(line.getLinePoints().get(i));

            }
            for (int j = line.getIndex(); j < line.getLinePoints().size(); j++) {
                pointsTemp2.add(line.getLinePoints().get(j));
            }
            //分两段进行压缩，分段的位置极为距离最大的点
            compressLine(new Line(pointsTemp1));
            compressLine(new Line(pointsTemp2));

        }
        compressionCollection.add(resultLine);
    }

    public void startCompression() {
        for (Line line : lineList) {
            line.printLine();
            resultLine.clear();//这里负责的是清除前一条曲线压缩的线段
            compressLine(line);
            System.out.print("在结果集合中曲线被压缩成" + resultLine.size() + "段");
            //每压缩一条曲线则会初始化一次resultLine，则把它打印出来
            for (Line aLine : resultLine) {
                aLine.printLine();
            }
            System.out.println();
        }
        //打印出压缩结果
    }


    @Data
    class Line {
        private Point start;
        private Point end;
        private ArrayList<Point> linePoints = new ArrayList<>();
        private double A;
        private double B;
        private double C;
        private int index;//最大距离所对应曲线上的点的索引号
        private double distance;//最大距离，与阈值比较

        //已知了线的两个端点，求两个端点所连线段到线的最大距离
        Line(Point start, Point end) {
            this.start = start;
            this.end = end;
            linePoints.add(start);
            linePoints.add(end);
        }

        Line(ArrayList<Point> linePoints) {
            this.linePoints = linePoints;
            this.start = linePoints.get(0);
            this.end = linePoints.get(linePoints.size() - 1);
            initABC();
            computeLineToLine();

        }

        void computeLineToLine() {
            double maxDist = Double.MIN_VALUE;
            double dist;
            index = 0;
            for (int i = 0; i < linePoints.size(); i++) {
                dist = computePointToLineDist(linePoints.get(i));
                if (dist > maxDist) {
                    maxDist = dist;
                    index = i;
                }
            }
            this.distance = maxDist;
        }

        double computePointToLineDist(Point point) {
            return (A * point.getX() + B * point.getY() + C) / Math.sqrt(A * A + B * B);
        }

        void initABC() {
            this.A = start.getY() - end.getY();
            this.B = end.getX() - start.getX();
            this.C = start.getY() * (-B) - start.getX() * A;
        }

        void printLine() {
            System.out.print("-------");
            for (Point point : linePoints) {
                point.printPoint();
            }
            System.out.print("-------");
        }
    }

    @Data
    class Point {
        private double x;
        private double y;

        Point(String x, String y) {
            this.x = Double.parseDouble(x);
            this.y = Double.parseDouble(y);
        }

        void printPoint() {
            System.out.print(MessageFormat.format("({0},{1})", this.x, this.y));
        }
    }
}
