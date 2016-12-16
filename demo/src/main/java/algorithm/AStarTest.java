package algorithm;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wajian on 2016/8/30.
 */
public class AStarTest {

    public static void main(String[] args) {
        AStarTest test = new AStarTest();
        //test.testTree();
        //test.testBinaryHeap();
        test.testAStar();
    }

    public void testAStar() {
        int col = 10;
        int row = 10;

        Grid grid = new Grid(col, row);

        for (int i = 0; i < grid.getNumCols(); i++) {
            for (int j = 0; j < grid.getNumRows(); j++) {
                Node node = grid.getNode(i, j);
                Random random = new Random();
                int value = random.nextInt(100);
                boolean b = (value % 10 != 0);
                node.setWalkable(b);
                if (i == col - 1 && j == row - 1) {
                    node.setWalkable(false);
                }
                b = node.getWalkable();
                if (b) {
                    System.out.print("○  ");
                } else {
                    System.out.print("※  ");
                }
            }
            System.out.print("\n");
        }

        System.out.print("---------------------------------------------------\n");

        NewAStar astar = new NewAStar();
        List<Node> path = null;
        grid.setStart(0, 0);
        grid.setEnd(col - 1, row - 1);
        if (astar.findPath(grid)) {
            path = astar.getPath();
        }
        for (int i = 0; i < grid.getNumCols(); i++) {
            for (int j = 0; j < grid.getNumRows(); j++) {
                Node node = grid.getNode(i, j);
                boolean walkable = node.getWalkable();
                if (path != null && path.contains(node)) {
                    System.out.print("●  ");
                } else if (walkable) {
                    System.out.print("○  ");
                } else {
                    System.out.print("※  ");
                }
            }
            System.out.print("\n");
        }
    }

//    public void testBinaryHeap() {
//        BinaryHeap<Float> heap = new BinaryHeap<>(new MyComparator());
////        heap.push(23.323f);
////        heap.push(40.97f);
////        heap.push(12.2f);
////        heap.push(9.009f);
////        heap.push(3.32f);
////        heap.push(50.2314f);
//        //heap.push(13.899494f);
//        //heap.push(13.313708f);
//        //heap.push(13.899495f);
//
//        float[] values = {13.899494f, 13.899495f, 13.899495f, 14.485281f};
//        for(float v : values) {
//            heap.push(v);
//        }
//
//        List<Float> stack = heap.toStack();
//        for(float value : stack) {
//            System.out.print(value + "  ");
//        }
//        System.out.println("\n");
//        System.out.println(heap.shift());
//    }
//
//    public void testTree() {
//        TreeNode<Integer> root = new TreeNode<>(100);
//        TreeNode<Integer> n101 = new TreeNode<>(101);
//        TreeNode<Integer> n102 = new TreeNode<>(102);
//        TreeNode<Integer> n103 = new TreeNode<>(103);
//        TreeNode<Integer> n104 = new TreeNode<>(104);
//
//        TreeNode<Integer> n1021 = new TreeNode<>(1021);
//        TreeNode<Integer> n1022 = new TreeNode<>(1022);
//        TreeNode<Integer> n1023 = new TreeNode<>(1023);
//
//        TreeNode<Integer> n10231 = new TreeNode<>(10231);
//
//        Tree tree = new Tree(root, (TreeNode node) -> {
//            int depth = node.getDepth();
//            int rootBindData = root.getBindData();
//            if(depth == 0 && rootBindData == 0) {
//                return;
//            }
//            if(rootBindData == 0) {
//                depth -= 1;
//            }
//            StringBuilder sb = new StringBuilder();
//            if(depth > 0) {
//                for(int i = 0; i < depth; i++) {
//                    sb.append("  ");
//                }
//                sb.append("---");
//            }
//            System.out.println(sb.toString() + node.getBindData());
//        });
//        tree.appendNode(n101);
//        tree.appendNode(n102);
//        tree.appendNode(n103);
//        tree.appendNode(n104);
//
//        tree.appendNode(n1021, n102);
//        tree.appendNode(n1022, n102);
//        tree.appendNode(n1023, n102);
//
//        tree.appendNode(n10231, n1023);
//
//        System.out.println(tree.getDepth());
//        tree.recursiveTraversal();
//
//        System.out.println("----------------------------------------------");
//        tree.removeNode(n1022);
//        tree.removeNode(n1023);
//        System.out.println(tree.getDepth());
//        tree.traversal();
//    }
}


/* self-define comparator*/
class MyComparator implements Comparator<Float> {
    @Override
    public int compare(Float obj1, Float obj2) {
        float a = obj1;
        float b = obj2;
        if (b - a > 0) {
            return 1;
        }
        return 0;
    }
}

/*A星寻路算法，网格节点*/
class Node {

    //节点在二维网格中的索引
    private int index;
    private final int x, y;
    private float f;
    private float g;
    private float h;

    private boolean walkable = true;
    private float costMultiplier = 1.0f;

    private Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void setWalkable(boolean walkable) {
        this.walkable = walkable;
    }

    public float getCostMultiplier() {
        return costMultiplier;
    }

    public void setCostMultiplier(float costMultiplier) {
        this.costMultiplier = costMultiplier;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setParent(Node node) {
        this.parent = node;
    }

    public Node getParent() {
        return parent;
    }

    public boolean getWalkable() {
        return walkable;
    }

    public float getF() {
        return f;
    }

    public void setF(float f) {
        this.f = f;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }
}

/*网格类*/
class Grid {

    private Node start;
    private Node end;
    private final Stack<Stack<Node>> nodes;

    private final int numCols;
    private final int numRows;

    /**
     * 构造方法，创建一个纯数据化的节点网格
     *
     * @param numCols
     * @param numRows
     */
    public Grid(int numCols, int numRows) {
        this.numCols = numCols;
        this.numRows = numRows;
        nodes = new Stack<>();
        for (int i = 0; i < numCols; i++) {
            Stack<Node> stack = new Stack<>();
            nodes.add(stack);
            for (int j = 0; j < numRows; j++) {
                Node node = new Node(i, j);
                node.setIndex(j + i * numRows);
                stack.add(node);
            }
        }
    }

    /**
     * 获取二维索引处的节点
     *
     * @param x 横向索引位置
     * @param y 纵向索引位置
     * @return （x,y)二维索引处的节点
     */
    public Node getNode(int x, int y) {
        Stack<Node> stack = nodes.get(x);
        if (stack != null) {
            return stack.get(y);
        }
        return null;
    }

    /**
     * 设置寻路的起始节点
     *
     * @param x 横向索引位置
     * @param y 纵向索引位置
     */
    public void setStart(int x, int y) {
        start = getNode(x, y);
    }

    /**
     * 设置寻路的结束节点
     *
     * @param x 横向索引位置
     * @param y 纵向索引位置
     */
    public void setEnd(int x, int y) {
        end = getNode(x, y);
    }

    /**
     * 设置（x, y)二维索引处的节点是否可以行走
     *
     * @param x    横向索引位置
     * @param y    纵向索引位置
     * @param able true为可行走
     */
    public void setWalkable(int x, int y, boolean able) {
        getNode(x, y).setWalkable(able);
    }

    /**
     * 获取网格行数
     *
     * @return 网格行数
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * 获取网格列数
     *
     * @return 网格列数
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * 获取寻路的起始节点
     *
     * @return 一次寻路的起始节点
     */
    public Node getStart() {
        return start;
    }

    /**
     * 获取寻路的结束节点
     *
     * @return 一次寻路的结束节点
     */
    public Node getEnd() {
        return end;
    }

    /**
     * 得到一个点下的所有节点
     *
     * @param xPos      点的横向位置
     * @param yPos      点的纵向位置
     * @param exception 例外格，若其值不为空，则在得到一个点下的所有节点后会排除这些例外格
     * @return 共享此点的所有节点
     */
    public List<Node> getNodesUnderPoint(float xPos, float yPos, List<Node> exception) {
        List<Node> result = new ArrayList<>();

        boolean xIsInt = xPos % 1 == 0;
        boolean yIsInt = yPos % 1 == 0;

        if (xIsInt && yIsInt) {
            //点由四节点共享情况
            int intX = (int) xPos;
            int intY = (int) yPos;
            result.add(getNode(intX - 1, intY - 1));
            result.add(getNode(intX, intY - 1));
            result.add(getNode(intX - 1, intY));
            result.add(getNode(intX, intY));
        } else if (xIsInt && !yIsInt) {
            //点由2节点共享情况
            //点落在两节点左右临边上
            int intX = (int) xPos;
            int intY = (int) yPos;
            result.add(getNode(intX - 1, intY));
            result.add(getNode(intX, intY));
        } else if (!xIsInt && yIsInt) {
            //点落在两节点上下临边上
            int intX = (int) xPos;
            int intY = (int) yPos;
            result.add(getNode(intX, intY - 1));
            result.add(getNode(intX, intY));
        } else {
            //点由一节点独享情况
            result.add(getNode((int) xPos, (int) yPos));
        }
        //在返回结果前检查结果中是否包含例外点，若包含则排除掉
        if (exception != null && exception.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                if (exception.contains(result.get(i))) {
                    result.remove(i);
                    i--;
                }
            }
        }
        return result;
    }

    /**
     * 得到一个点下的所有节点
     *
     * @param xPos 点的横向位置
     * @param yPos 点的纵向位置
     * @return 共享此点的所有节点
     */
    public List<Node> getNodesUnderPoint(float xPos, float yPos) {
        return getNodesUnderPoint(xPos, yPos, null);
    }

    /**
     * 判断两节点之间是否存在障碍物
     *
     * @param startX 起始点X坐标
     * @param startY 起始点Y坐标
     * @param endX   结束点X坐标
     * @param endY   结束点Y坐标
     * @return true存在障碍物
     */
    public boolean hasBarrier(int startX, int startY, int endX, int endY) {
        //如果起点终点是同一个点那傻子都知道它们间是没有障碍物的
        if (startX == endX && startY == endY) {
            return false;
        }

        //两节点中心位置
        NewPoint point1 = new NewPoint((float) (startX + 0.5), (float) (startY + 0.5));
        NewPoint point2 = new NewPoint((float) (endX + 0.5), (float) (endY + 0.5));

        //根据起点终点间横纵向距离的大小来判断遍历方向
        float distX = Math.abs(endX - startX);
        float distY = Math.abs(endY - startY);
        //遍历方向，为true则为横向遍历，否则为纵向遍历
        boolean loopDirection = distX > distY;
        //起始点与终点的连线方程
        LineFunction lineFunction;
        //循环递增量
        float i;
        //循环起始值
        float loopStart;
        //循环终结值
        float loopEnd;
        //起终点连线所经过的节点
        List<Node> passedNodeList;
        //为了运算方便，以下运算全部假设格子尺寸为1，格子坐标就等于它们的行、列号
        if (loopDirection) {
            lineFunction = new LineFunction(point1, point2, 0);
            loopStart = Math.min(startX, endX);
            loopEnd = Math.max(startX, endX);

            //开始横向遍历起点与终点间的节点看是否存在障碍(不可移动点)
            for (i = loopStart; i <= loopEnd; i++) {
                //由于线段方程是根据终起点中心点连线算出的，所以对于起始点来说需要根据其中心点
                //位置来算，而对于其他点则根据左上角来算
                if (i == loopStart) {
                    i += .5;
                }
                //根据x得到直线上的y值
                float yPos = lineFunction.function(i);

                //检查经过的节点是否有障碍物，若有则返回true
                passedNodeList = getNodesUnderPoint(i, yPos);
                for (Node passedNode : passedNodeList) {
                    if (passedNode.isWalkable() == false) {
                        return true;
                    }
                }
                if (i == loopStart + .5) {
                    i -= .5;
                }
            }
        } else {
            lineFunction = new LineFunction(point1, point2, 1);
            loopStart = Math.min(startY, endY);
            loopEnd = Math.max(startY, endY);

            //开始纵向遍历起点与终点间的节点看是否存在障碍(不可移动点)
            for (i = loopStart; i <= loopEnd; i++) {
                if (i == loopStart) {
                    i += .5;
                }
                //根据y得到直线上的x值
                float xPos = lineFunction.function(i);
                passedNodeList = getNodesUnderPoint(xPos, i);
                for (Node passedNode : passedNodeList) {
                    if (passedNode.isWalkable() == false) {
                        return true;
                    }
                }
                if (i == loopStart + .5) {
                    i -= .5;
                }
            }
        }
        return false;
    }
}

/*浮点数点对象*/
class NewPoint {

    private float x, y;

    public NewPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

/* 直线函数y=ax+b */
class LineFunction {

    private final NewPoint point1;
    private final NewPoint point2;
    private final int type;

    private float a;
    private float b;

    private final int funid;

    public LineFunction(NewPoint point1, NewPoint point2, int type) {
        this.point1 = point1;
        this.point2 = point2;
        this.type = type;
        funid = getFunctionId();
    }

    private float function1(float y) {
        return point1.getX();
    }

    private float function2(float x) {
        return point1.getY();
    }

    private float function3(float x) {
        return a * x + b;
    }

    private float function4(float y) {
        return (y - b) / a;
    }

    private int getFunctionId() {
        // 先考虑两点在一条垂直于坐标轴直线的情况，此时直线方程为 y = a 或者 x = a 的形式
        if (point1.getX() == point2.getX()) {
            if (type == 0) {
                //两点所确定直线垂直于y轴，不能根据x值得到y值
                return 0;
            } else if (type == 1) {
                return 1;
            }
        } else if (point1.getY() == point2.getY()) {
            if (type == 0) {
                return 2;
            } else if (type == 1) {
                //两点所确定直线垂直于x轴，不能根据y值得到x值
                return 0;
            }
        }

        // 当两点确定直线不垂直于坐标轴时直线方程设为 y = ax + b
        // 根据
        // y1 = ax1 + b
        // y2 = ax2 + b
        // 上下两式相减消去b, 得到 a = ( y1 - y2 ) / ( x1 - x2 )
        a = (point1.getY() - point2.getY()) / (point1.getX() - point2.getX());
        //将a的值代入任一方程式即可得到b
        b = point1.getY() - a * point1.getX();
        //把a,b值代入即可得到结果函数
        if (type == 0) {
            return 3;
        } else if (type == 1) {
            return 4;
        }
        return 0;
    }

    public float function(float value) {
        switch (funid) {
            case 1:
                return function1(value);
            case 2:
                return function2(value);
            case 3:
                return function3(value);
            case 4:
                return function4(value);
            default:
                break;
        }
        return 0;
    }
}

/* 启发函数/估价函数枚举 */
class Heuristic {

    //曼哈顿启发函数
    public static final String MANHATTAN = "manhattan";
    //欧几里得几何启发函数
    public static final String EUCLIDIAN = "euclidian";
    //对角启发函数
    public static final String DIAGONAL = "diagonal";
}

/*A星寻路算法*/
class NewAStar {

    private BinaryHeap<Node> open;
    private Stack<Node> closed;
    private Stack<Node> path;
    private Grid grid;
    private Node endNode;
    private Node startNode;
    private float straightCost = 1.0f;
    private float diagCost = (float) (Math.sqrt(2));
    private boolean retractable = false;
    private String heuristicName;

    private Comparator<Node> comparator;

    private Lock lock = new ReentrantLock();

    /**
     * 构造方法
     *
     * @param retractable   以死角点为寻路终点时，是否启用以死角点最近的可行点为路径终点
     * @param heuristicName 启发函数，为Heuristic类的枚举常量
     */
    public NewAStar(String heuristicName, boolean retractable) {
        this.heuristicName = heuristicName;
        this.retractable = retractable;
        //node1小于、等于或大于node2，分别返回负整数、零或正整数
        comparator = (Node node1, Node node2) -> {
            if (node1.getF() < node2.getF()) {
                return 1;
            } else if (node1.getF() > node2.getF()) {
                return -1;
            }
            return 0;
        };
    }

    /**
     * 构造方法
     *
     * @param heuristicName 路径代价分析函数
     */
    public NewAStar(String heuristicName) {
        this(heuristicName, true);
    }

    /**
     * 构造方法 默认为对角线启发函数
     */
    public NewAStar() {
        this(Heuristic.DIAGONAL);
    }

    /**
     * 启发函数计算
     *
     * @param node
     * @return
     */
    private float heuristic(Node node) {
        switch (heuristicName) {
            case Heuristic.DIAGONAL:
                return this.diagonal(node);
            case Heuristic.EUCLIDIAN:
                return this.euclidian(node);
            default:
                return this.manhattan(node);
        }
    }

    /**
     * 找寻路径
     *
     * @param grid 网格对象
     * @return true为找到了路径，调用getPath获取路径节点列表
     */
    public boolean findPath(Grid grid) {
        try {
            lock.lock();
            this.grid = grid;
            this.open = new BinaryHeap(comparator);
            this.closed = new Stack<>();
            this.startNode = grid.getStart();
            this.endNode = grid.getEnd();
            this.startNode.setG(0);
            this.startNode.setH(heuristic(startNode));
            this.startNode.setF(startNode.getG() + startNode.getH());
            return search();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 寻路算法
     *
     * @return true寻路成功
     */
    private boolean search() {
        Node node = startNode;
        while (node != endNode) {
            int startX = Math.max(0, node.getX() - 1);
            int endX = Math.min(grid.getNumCols() - 1, node.getX() + 1);
            int startY = Math.max(0, node.getY() - 1);
            int endY = Math.min(grid.getNumRows() - 1, node.getY() + 1);

            //围绕node的待测试节点
            for (int i = startX; i <= endX; i++) {
                for (int j = startY; j <= endY; j++) {
                    Node test = grid.getNode(i, j);
                    if (test == node) {
                        continue;
                    }
                    Node vnode = grid.getNode(node.getX(), test.getY());
                    Node hnode = grid.getNode(test.getX(), node.getY());
                    if (retractable) {
                        if (test.getWalkable() == false || !isDiagonalWalkable(node, test)) {
                            //设其代价为超级大的一个值，比大便还大哦~
                            test.setCostMultiplier(1000);
                        } else {
                            test.setCostMultiplier(1);
                        }
                    } else {
                        if (test.getWalkable() == false || vnode.getWalkable() == false || hnode.getWalkable() == false) {
                            continue;
                        }
                    }

                    //直线成本（三角形，设边为1，其值为边长）
                    float cost = straightCost;
                    if (!((node.getX() == test.getX()) || (node.getY() == test.getY()))) {
                        //斜线成本（三角形，设边为1，斜边三角函数的值约为2的平方根）
                        cost = diagCost;
                    }
                    float g = node.getG() + cost * test.getCostMultiplier();
                    float h = heuristic(test);
                    float f = g + h;
                    if (isOpen(test) || isClosed(test)) {
                        if (test.getF() > f) {
                            test.setF(f);
                            test.setG(g);
                            test.setH(h);
                            test.setParent(node);
                        }
                    } else {
                        test.setF(f);
                        test.setG(g);
                        test.setH(h);
                        test.setParent(node);
                        open.push(test);
                    }
                }
            }
            closed.push(node);
            if (open.getSize() == 0) {
                //"无可行路径"
                System.out.println("找不到可用路径");
                return false;
            }
            node = open.shift();
        }
        buildPath();
        return true;
    }

    /**
     * 判断两个节点的对角线路线是否可走
     *
     * @return true可走
     */
    private boolean isDiagonalWalkable(Node node1, Node node2) {
        Node nearByNode1 = grid.getNode(node1.getX(), node2.getY());
        Node nearByNode2 = grid.getNode(node2.getX(), node1.getY());
        return nearByNode1.getWalkable() && nearByNode2.getWalkable();
    }

    /**
     * 移除list中从startIndex处开始的len个元素
     *
     * @param list       列表对象
     * @param startIndex 开始删除位置
     * @param len        删除的个数
     */
    private void removeListElement(List<Node> list, int startIndex, int len) {
        int index = startIndex;
        for (int j = 0; j < len; j++) {
            list.remove(index++);
        }
    }

    /**
     * 移除stack中从startIndex处和其后的所有元素
     *
     * @param stack      列表对象
     * @param startIndex 移除元素的起始位置
     */
    private void removeStackElement(Stack<Node> stack, int startIndex) {
        int endIndex = stack.size();
        while (endIndex != startIndex) {
            stack.pop();
            endIndex--;
        }
    }

    /**
     * 构建路径
     */
    private void buildPath() {
        path = new Stack<>();
        Node node = endNode;
        path.push(node);
        //不包含起始节点
        while (node != startNode) {
            node = node.getParent();
            path.add(0, node);
        }
        if (retractable) {
            //排除无法移动点
            int len = path.size();
            for (int i = 0; i < len; i++) {
                if (path.get(i).getWalkable() == false) {
                    //removeListElement(path, i, len - i);
                    removeStackElement(path, i);
                    break;
                } else if (len == 1 && !isDiagonalWalkable(startNode, endNode)) {
                    //由于之前排除了起始点，所以当路径中只有一个元素时候判断该元素与起始点是否是不可穿越关系，若是，则连最后这个元素也给他弹出来~
                    path.remove(0);
                } else if (i < len - 1 && !isDiagonalWalkable(path.get(i), path.get(i + 1))) {
                    //判断后续节点间是否存在不可穿越点，若有，则把此点之后的元素全部拿下
                    //removeListElement(path, i + 1, len - i - 1);
                    removeStackElement(path, i + 1);
                    break;
                }
            }
        }
    }

    /**
     * 判断节点是否在开放列表中
     *
     * @param node 带判断的节点
     * @return true在开放列表中
     */
    private boolean isOpen(Node node) {
        return open.toStack().contains(node);
    }

    /**
     * 判断节点是否在关闭列表中
     *
     * @param node 待判断的节点
     * @return true在关闭列表中
     */
    private boolean isClosed(Node node) {
        return closed.contains(node);
    }

    /**
     * 获取路径
     *
     * @return 寻到的路径
     */
    public Stack<Node> getPath() {
        //path.add(grid.getStart());
        return path;
    }

    /**
     * 获取已访问过得节点列表
     *
     * @return 已访问的节点列表
     */
    public Stack<Node> getVisited() {
        Stack<Node> stack = new Stack<>();
        stack.addAll(closed);
        stack.addAll(open.toStack());
        return stack;
    }

    /**
     * 曼哈顿启发函数
     *
     * @param node
     * @return
     */
    private float manhattan(Node node) {
        return Math.abs(node.getX() - endNode.getX()) * straightCost + Math.abs(node.getY() + endNode.getY()) * straightCost;
    }

    /**
     * 欧几里得几何启发函数
     *
     * @param node
     * @return
     */
    private float euclidian(Node node) {
        float dx = node.getX() - endNode.getX();
        float dy = node.getY() - endNode.getY();
        return (float) (Math.sqrt(dx * dx + dy * dy) * straightCost);
    }

    /**
     * 对角启发函数
     *
     * @param node
     * @return
     */
    private float diagonal(Node node) {
        float dx = Math.abs(node.getX() - endNode.getX());
        float dy = Math.abs(node.getY() - endNode.getY());
        float diag = Math.min(dx, dy);
        float straight = dx + dy;
        return diagCost * diag + straightCost * (straight - 2 * diag);
    }
}

class BinaryHeap<T> {

    private final Stack<T> heap;
    private final Comparator<T> comparator;

    /**
     * 构造方法
     *
     * @param comparator 元素比较器
     */
    public BinaryHeap(Comparator<T> comparator) {
        heap = new Stack<>();
        this.comparator = comparator;
    }

    /**
     * 获取二叉堆元素数量
     *
     * @return 元素数量
     */
    public int getSize() {
        return heap.size();
    }

    /**
     * 交换元素，将最大值或最小值置于顶端
     *
     * @param objIndex    当前元素位置
     * @param parentIndex 父元素位置
     */
    private void swap(int objIndex, int parentIndex) {
        T temp = heap.get(objIndex);
        while (objIndex > 0) {
            //只有objIndex>0才有可能有parent
            //（大堆）如果新插入的数据大于parent的数据，则应不断上移与parent交换位置
            //（小堆）如果新插入的数据小于parent的数据，则应不断上移与parent交换位置
            T parentObj = heap.get(parentIndex);
            if (comparator.compare(temp, parentObj) > 0) {
                heap.set(objIndex, parentObj);
                objIndex = parentIndex;
                //parent索引的算法
                parentIndex = (parentIndex - 1) >> 1;
            } else {
                break;
            }
        }
        heap.set(objIndex, temp);
    }

    /**
     * 修改堆，将元素obj修改为newObj，并计算排序
     *
     * @param obj    旧的元素
     * @param newObj 新的元素
     * @return 是否修改成功
     */
    public boolean modify(T obj, T newObj) {
        int objIndex = heap.indexOf(obj);
        if (objIndex < 0) {
            return false;
        }
        heap.set(objIndex, newObj);
        int parentIndex = (objIndex - 1) >> 1;
        swap(objIndex, parentIndex);
        return true;
    }

    /**
     * 增加元素
     *
     * @param obj 待增加的元素
     */
    public void push(T obj) {
        heap.add(obj);
        int size = heap.size();
        int parentIndex = (size - 2) >> 1;
        int objIndex = size - 1;
        swap(objIndex, parentIndex);
    }

    /**
     * 弹出堆顶的数据
     *
     * @return 堆顶元素
     */
    public T shift() {
        if (heap.size() > 1) {
            T r = heap.get(0);
            T last = heap.pop();
            heap.set(0, last);
            int parentIndex = 0;
            int childIndex = 1;
            T parent = heap.get(parentIndex);
            while (childIndex < heap.size()) {
                T child = heap.get(childIndex);
                T nextChild;
                int nextIndex = childIndex + 1;
                if (nextIndex >= heap.size()) {
                    nextChild = null;
                } else {
                    nextChild = heap.get(nextIndex);
                }
                if (nextChild != null && comparator.compare(child, nextChild) < 0) {
                    childIndex = nextIndex;
                }
                child = heap.get(childIndex);
                if (comparator.compare(parent, child) < 0) {
                    heap.set(parentIndex, child);
                    parentIndex = childIndex;
                    childIndex = (childIndex << 1) + 1;
                } else {
                    break;
                }
            }
            heap.set(parentIndex, parent);
            return r;
        }
        return heap.pop();
    }

    @Override
    public String toString() {
        return heap.toString();
    }

    /**
     * 转换为栈列表
     *
     * @return 元素栈
     */
    public List<T> toStack() {
        return heap;
    }

    /**
     * 转换为数组
     *
     * @return 元素数组
     */
    public T[] toArray() {
        return (T[]) (heap.toArray());
    }
}