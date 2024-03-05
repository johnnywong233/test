package algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by johnny on 2016/8/30.
 * 图的遍历
 */
public class GraphDemo {

    // 邻接矩阵存储图
    // --A B C D E F G H I
    // A 0 1 0 0 0 1 1 0 0
    // B 1 0 1 0 0 0 1 0 1
    // C 0 1 0 1 0 0 0 0 1
    // D 0 0 1 0 1 0 1 1 1
    // E 0 0 0 1 0 1 0 1 0
    // F 1 0 0 0 1 0 1 0 0
    // G 0 1 0 1 0 1 0 1 0
    // H 0 0 0 1 1 0 1 0 0
    // I 0 1 1 1 0 0 0 0 0

    // 顶点数
    private final int number = 9;
    // 顶点
    private final String[] vertexes = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    // 边
    private final int[][] edges = {
            {0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 0, 1, 0, 0, 0, 1, 0, 1}, {0, 1, 0, 1, 0, 0, 0, 0, 1},
            {0, 0, 1, 0, 1, 0, 1, 1, 1}, {0, 0, 0, 1, 0, 1, 0, 1, 0}, {1, 0, 0, 0, 1, 0, 1, 0, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 0}, {0, 0, 0, 1, 1, 0, 1, 0, 0}, {0, 1, 1, 1, 0, 0, 0, 0, 0}
    };
    // 记录顶点是否被访问
    private boolean[] flag;

    //http://www.phpxs.com/code/1002279/
    public static void main(String[] args) {
        GraphDemo graph = new GraphDemo();
        System.out.println("图的深度遍历操作(递归):");
        graph.dfsTraverse();
        System.out.println("\n-------------");
        System.out.println("图的广度遍历操作:");
        graph.bfsTraverse();
    }

    // 图的深度遍历操作(递归)
    private void dfsTraverse() {
        flag = new boolean[number];
        for (int i = 0; i < number; i++) {
            if (!flag[i]) {// 当前顶点没有被访问
                dfs(i);
            }
        }
    }

    // 图的深度优先递归算法
    private void dfs(int i) {
        flag[i] = true;// 第i个顶点被访问
        System.out.print(vertexes[i] + " ");
        for (int j = 0; j < number; j++) {
            if (!flag[j] && edges[i][j] == 1) {
                dfs(j);
            }
        }
    }

    // 图的广度遍历操作
    private void bfsTraverse() {
        flag = new boolean[number];
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < number; i++) {
            if (!flag[i]) {
                flag[i] = true;
                System.out.print(vertexes[i] + " ");
                queue.add(i);
                while (!queue.isEmpty()) {
                    int j = queue.poll();
                    for (int k = 0; k < number; k++) {
                        if (edges[j][k] == 1 && !flag[k]) {
                            flag[k] = true;
                            System.out.print(vertexes[k] + " ");
                            queue.add(k);
                        }
                    }
                }
            }
        }
    }
}
