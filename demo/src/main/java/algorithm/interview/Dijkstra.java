package algorithm.interview;

import java.util.Scanner;

/**
 * Author: Johnny
 * Date: 2018/6/14
 * Time: 23:41
 * 输入：
 * 6 9
 * 0 1 1
 * 0 2 12
 * 1 2 9
 * 1 3 3
 * 2 4 5
 * 3 2 4
 * 3 4 13
 * 3 5 15
 * 4 5 4
 * 输出：
 * 0 1 8 4 13 17
 */
public class Dijkstra {
    static int INF = Integer.MAX_VALUE;//无法到达

    public static void main(String[] args) {
        int[] input = new int[]{6, 9, 0, 1, 1, 0, 2, 12, 1, 2, 9, 1, 3, 3, 2, 4, 5, 3, 2, 4, 3, 4, 13, 3, 5, 15, 4, 5, 4};
        Scanner read = new Scanner(System.in);
        // 输入points, edges 顶点数和边树
        int points = read.nextInt();
        int edges = read.nextInt();

        int[][] length = new int[points][points];
        // 初始化边（当前点到当前点为0）
        for (int i = 0; i < points; i++) {
            for (int j = 0; j < points; j++) {
                if (i == j) {
                    length[i][j] = 0;
                } else {
                    length[i][j] = INF;
                }
            }
        }
        read.nextLine();
        //输入顶点关系
        for (int i = 0; i < edges; i++) {
            String[] data = read.nextLine().split(" ");
            length[Integer.parseInt(data[0])][Integer.parseInt(data[1])] = Integer.parseInt(data[2]);
        }
        // 用一个一维数组存储当前点到其他点的距离，因为要根据二维表获取点到点的距离,不更改二维表的数据
        int[] distance = new int[points];

        // 初始化1点点到其余各个顶点的初始路程
        System.arraycopy(length[0], 0, distance, 0, points);

        // 定义一个flag数组。这个数组标记距离1最近的点被选择的数据
        boolean[] flag = new boolean[points];
        flag[0] = true;// 1点是最近的点，默认选择

        int min;
        int current = 0;
        for (int i = 0; i < points; i++) {
            min = INF;
            // 找寻距离1最短的点
            for (int j = 0; j < points; j++) {
                if (!flag[j] && distance[j] < min) {
                    min = distance[j];
                    current = j;
                }
            }
            // 设置这个距离1最短的点被选择
            flag[current] = true;
            // 获取这个最短的点到其他点的距离
            for (int m = 0; m < points; m++) {
                if (length[current][m] < INF) { // 判断是否为不可达
                    if (distance[m] > distance[current] + length[current][m]) {// 判断是否小于最短路径
                        distance[m] = distance[current] + length[current][m];
                    }
                }
            }
        }

        //输出最终的结果
        for (int i = 0; i < points; i++) {
            System.out.print(distance[i] + " ");
        }
    }
}
