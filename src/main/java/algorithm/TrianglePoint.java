package algorithm;

/**
 * Created by wajian on 2016/8/17.
 */
public class TrianglePoint {

	//TODO
	//http://www.jb51.net/article/43205.htm
    class Point {
        double x;
        double y;

        public Point() {
            this.x = 0;
            this.y = 0;
        }
    }

    class Line {
        Point a;
        Point b;

        public Line() {
            this.a = new Point();
            this.b = new Point();
        }
    }

    //求两直线的交点，斜率相同的话res=u.a
    Point intersection(Line u,Line v){
        Point res = u.a;
        double t = ((u.a.x-v.a.x)*(v.b.y-v.a.y)-(u.a.y-v.a.y)*(v.b.x-v.a.x))
                /((u.a.x-u.b.x)*(v.b.y-v.a.y)-(u.a.y-u.b.y)*(v.b.x-v.a.x));
        res.x += (u.b.x-u.a.x)*t;
        res.y += (u.b.y-u.a.y)*t;
        return res;
    }

    //三角形外接圆圆心(外心)
    Point center(Point a,Point b,Point c) {
        //加上这个才没有编译器提示未初始化，因为new所以也写了构造方法
        Line u = new Line(),v = new Line();
        u.a.x=(a.x+b.x)/2;
        u.a.y=(a.y+b.y)/2;
        u.b.x=u.a.x+(u.a.y-a.y);
        u.b.y=u.a.y-(u.a.x-a.x);
        v.a.x=(a.x+c.x)/2;
        v.a.y=(a.y+c.y)/2;
        v.b.x=v.a.x+(v.a.y-a.y);
        v.b.y=v.a.y-(v.a.x-a.x);
        return intersection(u,v);
    }

}
