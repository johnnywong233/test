package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import static oracle.net.aso.C00.x;
import static oracle.net.aso.C00.y;

/**
 * Created by wajian on 2016/8/30.
 * A*搜索算法，A星算法。
 * 这是一种在图形平面上，有多个节点的路径，求出最低通过成本的算法。
 * 常用于游戏中的NPC的移动计算，或在线游戏的BOT的移动计算上。
 * 该算法像Dijkstra算法一样，可以找到一条最短路径；也像BFS一样，进行启发式的搜索。
 * 在此算法中，如果以 g(n)表示从起点到任意顶点n的实际距离，
 * h(n)表示任意顶点n到目标顶点的估算距离，
 * 那么 A*算法的公式为：f(n)=g(n)+h(n)。 这个公式遵循以下特性：
 * 如果h(n)为0，只需求出g(n)，即求出起点到任意顶点n的最短路径，则转化为单源最短路径问题，即Dijkstra算法
 * 如果h(n)<=“n到目标的实际距离”，则一定可以求出最优解。而且h(n)越小，需要计算的节点越多，算法效率越低。
 * http://www.phpxs.com/code/1001767/
 */
public class AStar {
    /** 搜索区域 0:不能走 1:能走 */
    private int[][] searchArea;
    /** 记录节点状态 */
    private int[][] searchAreaStatus;
    private int width;
    private int height;

    /** 开启列表 */
    private PriorityQueue<AStarNode> openList;
    /** FComparator */
    public static Comparator<AStarNode> fComparator = new Comparator<AStarNode>() {
        @Override
        public int compare(AStarNode c1, AStarNode c2) {
            return (int) (c1.getF() - c2.getF());
        }
    };

    /**
     * AStart
     * @param searchArea 搜索区域
     * @param width      搜索区域的宽
     * @param height     搜索区域的高
     */
    public AStar(int[][] searchArea, int width, int height) {
        this.width = width;
        this.height = height;
        this.searchArea = searchArea;
        this.searchAreaStatus = new int[this.height][this.width];
        this.openList = new PriorityQueue<>(10, fComparator);
    }

    /**
     * 查找路径
     *
     * @param x1 起点A的x坐标
     * @param y1 起点A的y坐标
     * @param x2 终点B的x坐标
     * @param y2 终点B的y坐标
     * @return   起点A到终点B的路径
     */
    public List<AStarNode> find(int x1, int y1, int x2, int y2) {
        AStarNode startNode = new AStarNode(x1, y1);
        AStarNode endNode = new AStarNode(x2, y2);

        return this.find(startNode, endNode);
    }

    /**
     * find 搜索
     * @param startNode 起点
     * @param endNode   终点
     * @return          路径
     */
    private List<AStarNode> find(AStarNode startNode, AStarNode endNode) {
        List<AStarNode> resultList = new ArrayList<AStarNode>();
        /* 是否找到 */
        boolean findFalg = false;

        /* 1.从起点A开始，并将它添加到 “开启列表”。 */
        this.openList.add(startNode);
        searchAreaStatus[startNode.getX()][startNode.getY()] = AStarConstants.NOTE_STATUS_OPEN;

        AStarNode curNode = this.openList.poll();
        while (curNode != null) {
            /* c）对于当前方格临近的8个方格的每一个....（For Each） */
            // System.out.println("find@AStar curNode=" + curNode);
            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 0:// 右
                        check(curNode.getX(),     curNode.getY() + 1, endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                        break;
                    case 1:// 下
                        check(curNode.getX() + 1, curNode.getY(),     endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                        break;
                    case 2:// 左
                        check(curNode.getX(),     curNode.getY() - 1, endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                        break;
                    case 3:// 上
                        check(curNode.getX() - 1, curNode.getY(),     endNode, curNode, AStarConstants.COST_ORTHOGONAL); // 10
                        break;
                    case 4:// 右上
                        check(curNode.getX() - 1, curNode.getY() + 1, endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                        break;
                    case 5:// 右下
                        check(curNode.getX() + 1, curNode.getY() + 1, endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                        break;
                    case 6:// 左上
                        check(curNode.getX() - 1, curNode.getY() - 1, endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                        break;
                    case 7:// 左下
                        check(curNode.getX() + 1, curNode.getY() - 1, endNode, curNode, AStarConstants.COST_DIAGONAL); // 14
                        break;
                } // end switch
            } // end for

            // 加入关闭列表
            findFalg = this.addClosedList(curNode, endNode);
            if (findFalg) {
                break;
            }

            /* a）寻找开启列表上最小F值的方格。将它作为当前方格。 */
            curNode = this.openList.poll();
        } // while

        if (findFalg) {
            // 有
            read(resultList, curNode);
            return resultList;
        } else {
            /* 无法找到目标方格并且开启列表是空的时候，不存在路径。 */
            return resultList;
        }
    }

    /**
     * 读取所有节点，从起点开始返
     *
     * @param resultList
     * @param node
     */
    private void read(List<AStarNode> resultList, AStarNode node) {
        if (node.getParent() != null) {
            read(resultList, node.getParent());
        }
        resultList.add(node);
    }

    /**
     * hasNearbyUnwalkable
     * @param x x坐标
     * @param y y坐标
     * @param parent 父
     * @return
     */
    private boolean hasNearbyUnwalkable(int x, int y, AStarNode parent) {
        boolean bRes = false;
        if (x != parent.getX() && y != parent.getY()) {
            if (this.searchArea[parent.getX()][y] == AStarConstants.NOTE_UNWALKABLE) {
                bRes = true;
            }
            if (this.searchArea[x][parent.getY()] == AStarConstants.NOTE_UNWALKABLE) {
                bRes = true;
            }
        }
        return bRes;
    }

    /**
     * 检查 当前节点周围的节点，是否能行，是否在开启列表中，是否在关闭列表中 如果不在关闭与开启列表中则加入开启列表，如果在开启中则更新节点G值信息
     *
     * @param x       x坐标
     * @param y       y坐标
     * @param endNode 终点
     * @param parent 父
     * @param step 步长
     * @return
     */
    private boolean check(int x, int y, AStarNode endNode, AStarNode parent, int step) {
        // System.out.println("  check@AStar (" + x + "," + y + ")" + parentNode);
        try {
            if (this.searchArea[x][y] == AStarConstants.NOTE_UNWALKABLE) {
                /* 如果不能走，忽略它。*/
                return false;
            }

            if (this.searchAreaStatus[x][y] == AStarConstants.NOTE_STATUS_CLOSED) {
                /* 如果它在关闭列表上，忽略它。 */
                return false;
            }

            /* 否则，执行以下操作。 */
            if (this.searchAreaStatus[x][y] == AStarConstants.NOTE_STATUS_NONE) {
                if (hasNearbyUnwalkable(x, y, parent)) {
                    return false;
                }

                /* 如果不在开启列表中，把它添加到开启列表。使当前方格成为这个方格的父。记录的方格F值，G值和H值。*/
                this.addOpenList(x, y, endNode, parent, step);
                this.searchAreaStatus[x][y] = AStarConstants.NOTE_STATUS_OPEN;
                return true;
            } else if (this.searchAreaStatus[x][y] == AStarConstants.NOTE_STATUS_OPEN) {
                /* 如果在开启列表了，检查看看采用G值来衡量这个路径到那个方格是否是更好的。*/
                this.updateOpenList(x, y, endNode, parent, step);
                return true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;// 下标越界
        }
        return false;
    }

    /**
     * 添加到关闭列表
     *
     * @param node    节点
     * @param endNode 终点
     * @return true:路径被发现
     */
    private boolean addClosedList(AStarNode node, AStarNode endNode) {
        if (node.getX() == endNode.getX() && node.getY() == endNode.getY()) {
            /* 在目标方格添加到关闭列表的情况下，路径已经被发现 */
            return true;
        }

        this.searchAreaStatus[node.getX()][node.getY()] = AStarConstants.NOTE_STATUS_CLOSED;
        return false;
    }

    /**
     * 添加到开启列表
     * 使当前方格成为这个方格的父。记录的方格F值，G值和H值。
     *
     * @param x x坐标
     * @param y y坐标
     * @param endNode 终点
     * @param parent  父
     * @param step    步长
     * @return
     */
    private boolean addOpenList(int x, int y, AStarNode endNode, AStarNode parent, int step) {
        /* 使当前方格成为这个方格的父。 */
        AStarNode node = new AStarNode(x, y, parent);
        /* 记录的方格F值，G值和H值。 */
        this.count(node, endNode, step);

        this.openList.add(node);
        // System.out.println("    putOpenTable@AStar " + node + parentNode);

        return true;
    }

    /**
     * 已经在开启列表了更新节点F值
     * 更低的G值意味着这是一个更好的路径。如果是这样，把方格的父改为当前方格，并重新计算方格的G值和F值。如果你保持开启列表排序F值，由于这个变化你可能需重存列表。
     *
     * @param x x坐标
     * @param y y坐标
     * @param endNode 终点
     * @param parent 父
     * @param step 步长
     * @return
     */
    private boolean updateOpenList(int x, int y, AStarNode endNode, AStarNode parent, int step) {
        AStarNode node = new AStarNode(x, y);
        for (AStarNode nd : this.openList) {
            if (node.equals(nd)) {
                node = nd;
                break ;
            }
        }
        int g = parent.getG() + step;
        if (g < node.getG()) {
            /* 如果是更低的G值意味着这是一个更好的路径。把方格的父改为当前方格 */
            node.setParent(parent);
            /* 并重新计算方格的G值和F值。*/
            this.count(node, endNode, step);

            /* 如果你保持开启列表按F值排序，由于这个变化你可能需重存列表。 */
            this.openList.remove(node);
            this.openList.add(node);

            return true;
        }
        return false;
    }

    /**
     * 计算GHF
     *
     * @param node 节点
     * @param endNode 终点
     * @param step 步长
     */
    private void count(AStarNode node, AStarNode endNode, int step) {
        this.countG(node, node.getParent(), step);
        this.countH(node, endNode);
        this.countF(node);
    }

    /**
     * 计算G值
     * 将指定每个移动水平或垂直方成本为10，对角线移动成本为14
     * 找出那个方块的父亲的G值，然后加10或14取决于它从父方格是正交（非对角线）还是对角线。
     *
     * @param node 节点
     * @param parent 父
     * @param step 步长
     */
    private void countG(AStarNode node, AStarNode parent, int step) {
        if (parent == null) {
            node.setG(step);
        } else {
            node.setG(parent.getG() + step);
        }
    }

    /**
     * 计算H值
     * 曼哈顿方法
     * 计算从当前方块到目标方水平和垂直方向移动方块的总数
     * 将总数乘以10
     *
     * @param node 节点
     * @param endNode 终点
     */
    private void countH(AStarNode node, AStarNode endNode) {
        node.setH((Math.abs(endNode.getX() - node.getX()) + Math.abs(endNode.getY() - node.getY())) * 10);
    }

    /**
     * 计算F值
     * F = G + H
     *
     * @param node 节点
     */
    private void countF(AStarNode node) {
        node.setF(node.getG() + node.getH());
    }
}

//http://www.oschina.net/code/snippet_42170_36579
class AStarNode{
    private int index;
    private final int x;
    private final int y;
    private float f;
    private float g;
    private float h;

    private AStarNode parent;

    public AStarNode(int x, int y) {
        this.x = x;
        this.y = y;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public AStarNode getParent() {
        return parent;
    }

    public void setParent(AStarNode parent) {
        this.parent = parent;
    }
}