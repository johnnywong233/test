package algorithm;

/**
 * Created by Johnny on 2018/4/22.
 * 8皇后问题
 */
public class Queen8 {
    //棋盘格子的范围，以及皇后数量，应该分开定义
    private final Integer NUM = 8;
    //二维数组棋盘
    private int chessBoard[][] = new int[NUM][NUM];

    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.settleQueen(0);
        queen8.printChessBoard();
    }

    //打印
    private void printChessBoard() {
        for (int j = 0; j < NUM; j++) {
            for (int i = 0; i < NUM; i++) {
                System.out.print(chessBoard[i][j]);
            }
            System.out.println();
        }
    }

    //检查落点是否符合规则
    private boolean check(int x, int y) {
        for (int i = 0; i < y; i++) {
            //检查纵向
            if (chessBoard[x][i] == 1) {
                return false;
            }
            //检查左侧斜向
            if (x - i - 1 >= 0 && chessBoard[x - i - 1][y - 1 - i] == 1) {
                return false;
            }
            //检查右侧斜向
            if (x + i + 1 < NUM && chessBoard[x + 1 + i][y - 1 - i] == 1) {
                return false;
            }
        }
        return true;
    }

    boolean settleQueen(int y) {
        //行数超过8，说明找到答案
        if (y == NUM) {
            return true;
        }
        //遍历当前行，逐一格子校验
        for (int i = 0; i < NUM; i++) {
            //清0，以免在回溯时出现脏数据
            for (int x = 0; x < NUM; x++) {
                chessBoard[x][y] = 0;
            }
            //符合规则，则更改元素值，并进一步递归
            if (check(i, y)) {
                chessBoard[i][y] = 1;
                //如果递归返回true，说明下层已经找到，无需继续递归
                if (settleQueen(y + 1)) {
                    return true;
                }
            }
        }
        return false;
    }
}
