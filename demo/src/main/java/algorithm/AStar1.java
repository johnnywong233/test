package algorithm;

import lombok.Data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by wajian on 2016/8/30.
 * A* algorithm
 * A*搜索算法，A星算法。
 * 这是一种在图形平面上，有多个节点的路径，求出最低通过成本的算法。
 * 常用于游戏中的NPC的移动计算，或在线游戏的BOT的移动计算上。
 * 该算法像Dijkstra算法一样，可以找到一条最短路径；也像BFS一样，进行启发式的搜索。
 * 在此算法中，如果以 g(n)表示从起点到任意顶点n的实际距离，
 * h(n)表示任意顶点n到目标顶点的估算距离，
 * 那么 A*算法的公式为：f(n)=g(n)+h(n)。 这个公式遵循以下特性：
 * 如果h(n)为0，只需求出g(n)，即求出起点到任意顶点n的最短路径，则转化为单源最短路径问题，即Dijkstra算法
 * 如果h(n)<=“n到目标的实际距离”，则一定可以求出最优解。而且h(n)越小，需要计算的节点越多，算法效率越低。
 */
public class AStar1 {

    // 迷宫图
    private Point[][] maze;
    // 起始节点
    private Point start;
    // 终止节点
    private Point goal;

    // 开启队列，用于存放待处理的节点
    private Queue<Point> openQueue = null;
    // 关闭队列，用于存放已经处理过的节点
    private Queue<Point> closedQueue = null;

    // 起始节点到某个节点的距离
    private int[][] fList = null;
    // 某个节点到目的节点的距离
    private int[][] gList = null;
    // 起始节点经过某个节点到目的节点的距离
    private int[][] hList = null;

    /**
     * 打印行走路径
     * <p>
     * 经过的点用'*'表示,
     * 未经过的点用'.'表示，
     * 起始节点用'r'表示,
     * 目的节点用'a'表示
     * 士兵用'x'表示
     */
    private void printPath() {
        System.out.println("================ printPath ================");
        Point fatherPoint;
        char[][] result = new char[7][8];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                result[i][j] = '.';
            }
        }

        int step = 0;
        fatherPoint = maze[goal.getX()][goal.getY()];
        while (fatherPoint != null) {
            if (fatherPoint.equals(start)) {
                result[fatherPoint.getX()][fatherPoint.getY()] = 'r';
            } else if (fatherPoint.equals(goal)) {
                result[fatherPoint.getX()][fatherPoint.getY()] = 'a';
                step++;
            } else if (fatherPoint.getValue() == 'x') {
                result[fatherPoint.getX()][fatherPoint.getY()] = 'x';
                step += 2;
            } else {
                result[fatherPoint.getX()][fatherPoint.getY()] = '*';
                step++;
            }
            fatherPoint = fatherPoint.getFather();
        }
        // 打印行走步数
        System.out.println("step is : " + step);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 构造函数
     *
     * @param maze  迷宫图
     * @param start 起始节点
     * @param goal  目的节点
     */
    private AStar1(Point[][] maze, Point start, Point goal) {
        this.maze = maze;
        this.start = start;
        this.goal = goal;

        openQueue = new LinkedList<>();
        closedQueue = new LinkedList<>();

        fList = new int[maze.length][maze[0].length];
        gList = new int[maze.length][maze[0].length];
        hList = new int[maze.length][maze[0].length];

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                fList[i][j] = Integer.MAX_VALUE;
                gList[i][j] = Integer.MAX_VALUE;
                hList[i][j] = Integer.MAX_VALUE;
            }
        }
        init();
    }

    /**
     * 初始化
     * 将起始节点添加至开启列表，初始化：
     * 1) 起始节点到当前节点（起始节点）的距离
     * 2) 当前节点（起始节点）到目的节点的距离
     * 3) 起始节点经过当前节点（起始节点）到目的节点的距离
     */
    private void init() {
        openQueue.offer(start);
        int startX = start.getX();
        int startY = start.getY();
        int goalX = goal.getX();
        int goalY = goal.getY();

        // 起始节点到当前节点的距离
        gList[startX][startY] = 0;
        // 当前节点到目的节点的距离
        hList[startX][startY] = getDistance(startX, startY, goalX, goalY);
        // f(x) = g(x) + h(x)
        fList[startX][startY] = gList[startX][startY]
                + hList[startX][startY];
    }

    /**
     * 启动搜索迷宫过程主入口
     * <p>
     * 从开启列表中搜索F值最小（即：起始节点 经过某一节点 到目的节点 距离最短），
     * 将选取的节点作为当前节点，并更新当前节点的邻居节点信息（G、H、F值）以及
     * 开启列表与关闭列表的成员。
     */
    public void start() {
        Point currentPoint;

        while ((currentPoint = findShortestFPoint()) != null) {
            if (currentPoint.getX() == goal.getX()
                    && currentPoint.getY() == goal.getY()) {
                return;
            }
            updateNeighborPoints(currentPoint);
        }
    }

    //http://joshuasabrina.iteye.com/blog/1811065
    public static void main(String[] args) {
        // 原始迷宫图
        char[][] mazeRaw = {{'#', '.', '#', '#', '#', '#', '#', '.'},
                {'#', '.', 'a', '#', '.', '.', 'r', '.'},
                {'#', '.', '.', '#', 'x', '.', '.', '.'},
                {'.', '.', '#', '.', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '#', '#', '.', '.'},
                {'.', '#', '.', '.', '.', '.', '.', '.'},
                {'.', '.', '.', '.', '.', '.', '.', '.'}};

        // 节点迷宫图
        Point[][] maze = new Point[mazeRaw.length][mazeRaw[0].length];
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Point(i, j, mazeRaw[i][j]);
            }
        }
        // 起始节点
        Point start = maze[1][6];
        // 目的节点
        Point goal = maze[1][2];

        AStar1 astar = new AStar1(maze, start, goal);
        // 启动搜索迷宫过程
        astar.start();
        // 打印行驶路径
        astar.printPath();
    }

    /**
     * 检查位置是否有效
     * 如果当前位置存在、不是墙，且不在关闭列表中，则返回"true"，表示为有效位置；
     * 否则，返回"false"。
     * 输入： 待检查位置的横坐标值
     * 待检查位置的纵坐标值
     * 输出： 是否有效
     */
    private boolean checkPosValid(int x, int y) {
        // 检查x,y是否越界， 并且当前节点不是墙
        if ((x >= 0 && x < maze.length) && (y >= 0 && y < maze[0].length)
                && (maze[x][y].getValue() != '#')) {
            // 检查当前节点是否已在关闭队列中，若存在，则返回 "false"
            Iterator<Point> it = closedQueue.iterator();
            Point point;
            while (it.hasNext()) {
                if ((point = it.next()) != null) {
                    if (point.getX() == x && point.getY() == y) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 获取当前位置到目的位置的距离
     * <p>
     * 距离衡量规则： 横向移动一格或纵向移动一格的距离为1.
     * <p>
     * 输入： 当前位置的横坐标值
     * 当前位置的纵坐标值
     * 目的位置的横坐标值
     * 目的位置的纵坐标值
     * <p>
     * 输出： 当前位置到目的位置的距离
     */
    private int getDistance(int currentX, int currentY, int goalX, int goalY) {
        return Math.abs(currentX - goal.getX())
                + Math.abs(currentY - goal.getY());
    }

    /**
     * 找寻最短路径所经过的节点
     *
     *   从开启列表中找寻F值最小的节点，将其从开启列表中移除，并置入关闭列表。
     *
     * 输出：最短路径所经过的节点
     */
    private Point findShortestFPoint() {
        Point currentPoint;
        Point shortestFPoint = null;
        int shortestFValue = Integer.MAX_VALUE;

        for (Point anOpenQueue : openQueue) {
            currentPoint = anOpenQueue;
            if (fList[currentPoint.getX()][currentPoint.getY()] <= shortestFValue) {
                shortestFPoint = currentPoint;
                shortestFValue = fList[currentPoint.getX()][currentPoint.getY()];
            }
        }

        if (shortestFValue != Integer.MAX_VALUE) {
            openQueue.remove(shortestFPoint);
            closedQueue.offer(shortestFPoint);
        }

        return shortestFPoint;
    }

    /**
     * 更新邻居节点
     *
     *   依次判断上、下、左、右方向的邻居节点，如果邻居节点有效，则更新距离矢量表。
     *
     * 输入： 当前节点
     */
    private void updateNeighborPoints(Point currentPoint) {
        int currentX = currentPoint.getX();
        int currentY = currentPoint.getY();

        // 上
        if (checkPosValid(currentX - 1, currentY)) {
            updatePoint(maze[currentX][currentY],
                    maze[currentX - 1][currentY]);
        }
        // 下
        if (checkPosValid(currentX + 1, currentY)) {
            updatePoint(maze[currentX][currentY],
                    maze[currentX + 1][currentY]);
        }
        // 左
        if (checkPosValid(currentX, currentY - 1)) {
            updatePoint(maze[currentX][currentY],
                    maze[currentX][currentY - 1]);
        }
        // 右
        if (checkPosValid(currentX, currentY + 1)) {
            updatePoint(maze[currentX][currentY],
                    maze[currentX][currentY + 1]);
        }
    }

    /**
     * 更新节点
     *
     *   依次计算：1) 起始节点到当前节点的距离; 2) 当前节点到目的位置的距离; 3) 起始节点经过当前节点到目的位置的距离
     *   如果当前节点在开启列表中不存在，则：置入开启列表，并且“设置”1)/2)/3)值；
     *   否则，判断 从起始节点、经过上一节点到当前节点、至目的地的距离 < 上一次记录的从起始节点、到当前节点、至目的地的距离，
     *   如果有更短路径，则更新1)/2)/3)值
     *
     * 输入： 上一跳节点（又：父节点）
     *       当前节点
     */
    private void updatePoint(Point lastPoint, Point currentPoint) {
        int lastX = lastPoint.getX();
        int lastY = lastPoint.getY();
        int currentX = currentPoint.getX();
        int currentY = currentPoint.getY();

        // 起始节点到当前节点的距离
        int temp = gList[lastX][lastY] + 1;
        // 如果当前节点是看守
        if (maze[currentX][currentY].getValue() == 'x') {
            ++temp;
        }
        // 当前节点到目的位置的距离
        int tempH = getDistance(currentX, currentY, goal.getX(), goal.getY());
        // f(x) = g(x) + h(x)
        int tempF = temp + tempH;

        // 如果当前节点在开启列表中不存在，则：置入开启列表，并且“设置”
        // 1) 起始节点到当前节点距离
        // 2) 当前节点到目的节点的距离
        // 3) 起始节点到目的节点距离
        if (!openQueue.contains(currentPoint)) {
            openQueue.offer(currentPoint);
            currentPoint.setFather(lastPoint);

            // 起始节点到当前节点的距离
            gList[currentX][currentY] = temp;
            // 当前节点到目的节点的距离
            hList[currentX][currentY] = tempH;
            // f(x) = g(x) + h(x)
            fList[currentX][currentY] = tempF;
        } else {

            // 如果当前节点在开启列表中存在，并且，
            // 从起始节点、经过上一节点到当前节点、至目的地的距离 < 上一次记录的从起始节点、到当前节点、至目的地的距离，
            // 则：“更新”
            // 1) 起始节点到当前节点距离
            // 2) 当前节点到目的节点的距离
            // 3) 起始节点到目的节点距离
            if (tempF < fList[currentX][currentY]) {
                // 起始节点到当前节点的距离
                gList[currentX][currentY] = temp;
                // 当前节点到目的位置的距离
                hList[currentX][currentY] = tempH;
                // f(x) = g(x) + h(x)
                fList[currentX][currentY] = tempF;
                // 更新当前节点的父节点
                currentPoint.setFather(lastPoint);
            }
        }
    }
}

@Data
class Point {
    // 节点横坐标
    private int x;
    // 节点纵坐标
    private int y;

    // 节点值
    private char value;
    // 父节点
    private Point father;

    /**
     * 构造函数
     *
     * @param x 节点横坐标
     * @param y 节点纵坐标
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 构造函数
     *
     * @param x     节点横坐标
     * @param y     节点纵坐标
     * @param value 节点值
     */
    Point(int x, int y, char value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
