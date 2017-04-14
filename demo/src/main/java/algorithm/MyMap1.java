package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MyMap1 {
    /* ---城市路线对象--- */
    private class Way {
        String from;
        String to;
        int cost;
    }

    //use HashMap to store all the cities path
    private Map<String, List<String>> map = new HashMap<>();// 储存所有城市路线
    private List<String> reachedWay = new ArrayList<>();// 储存到达目的地所经过的城市
    private Map<Integer, String> routeMap = new HashMap<>();// 储存到达目的地所经过的城市和所用的时间，key为时间，value为reachedWay

    // int shortestTime=0; //储存最短时间，用于只输出最短路径的情况

    /* ---添加路线，双向添加--- */
	private void addRoute(String city1, String city2, int cost) {
        List cityList1 = map.get(city1);// 城市1路线集合
        if (cityList1 == null) {
            cityList1 = new ArrayList();
            map.put(city1, cityList1);
        }

        Way way1 = new Way();
        way1.from = city1;
        way1.to = city2;
        way1.cost = cost;

        //add the way if it does not exist
        if (!cityList1.contains(way1)) {
            cityList1.add(way1);
        }

        List cityList2 = map.get(city2);// 城市2路线集合
        if (cityList2 == null) {
            cityList2 = new ArrayList();
            map.put(city2, cityList2);
        }
        Way way2 = new Way();
        way2.from = city2;
        way2.to = city1;
        way2.cost = cost;

        //add the way if it does not exist
        if (!cityList2.contains(way2)) {
            cityList2.add(way2);
        }
    }

    //compute the shortest path, and time
    public void run(String from, String to) {
        int tempTime = 0;// 储存所花时间的临时变量
        if (reachedWay.contains(from)) {// 走过的不走
            return;
        }
        reachedWay.add(from);// 把经过的城市加入到城市集合中
        if (reachedWay.size() > 1) {
			/* ---计算所花时间--- */
            List initList = map.get(reachedWay.get(0));

            for (Object list : initList) {
                Way w = (Way) list;
                if (w.to.equals(reachedWay.get(1))) {
                    tempTime += w.cost;

			/* ---用于不需要循环输出所有路线，只输出最短路径，效率很高--- */
                    // if(shortestTime!=0&&tempTime>shortestTime){
                    // return;
                    // }
                }
            }

            for (int i = 1; i < reachedWay.size(); i++) {
                // 所经过的城市用时加起来
                List toList = map.get(reachedWay.get(i));
                for (Object list : toList) {
                    Way w = (Way) list;
                    if (i + 1 < reachedWay.size()) {
                        if (w.to.equals(reachedWay.get(i + 1))) {
                            tempTime += w.cost;

				/* ---用于不需要循环输出所有路线，只输出最短路径，效率很高--- */
                            // if(shortestTime!=0&&tempTime>shortestTime){
                            // return;
                            // }
                        }
                    }
                }
            }
        }

        //start point equals end point, which means arrival
        if (from.equals(to)) {
            // shortestTime=tempTime;
            String route = reachedWay.get(0);
            for (int i = 1; i < reachedWay.size(); i++) {
                route += "->" + reachedWay.get(i);
            }
            System.out.println(route + "\ttime used：" + tempTime);
            routeMap.put(tempTime, route);
            reachedWay.remove(reachedWay.size() - 1);// 到达后退回去，走下一路线
            return;
        }

        //still finding the path
        // 获得from城市能够到达到城市列表
        List routeList = map.get(from);
        for (Object aRouteList : routeList) {
            Way way = (Way) aRouteList;
            run(way.to, to);
        }
        reachedWay.remove(reachedWay.size() - 1);// 走完退回去，走下一路线
    }

    //print the path use the shortest time
    public void show(String city1, String city2) {
        run(city1, city2);
        Set<Integer> s = routeMap.keySet();
        Object[] a = s.toArray();
        int shortestTime = (Integer) a[0];
        for (Object anA : a) {
            if ((Integer) anA < shortestTime) {
                shortestTime = (Integer) anA;
            }
        }
        System.out.println("\nthe shortest path:" + routeMap.get(shortestTime) + "\tthe time used:"
                + shortestTime);
    }


    public static void main(String[] args) {
        MyMap1 map = new MyMap1();
        map.addRoute("京", "汉", 10);
        map.addRoute("京", "沪", 4);
        map.addRoute("京", "乌", 36);
        map.addRoute("汉", "沪", 3);
        map.addRoute("汉", "乌", 18);
        map.addRoute("汉", "渝", 7);
        map.addRoute("圳", "汉", 3);
        map.addRoute("圳", "沪", 4);
        map.addRoute("圳", "乌", 45);
        map.addRoute("圳", "渝", 16);
        
        map.show("汉", "沪");
    }

}