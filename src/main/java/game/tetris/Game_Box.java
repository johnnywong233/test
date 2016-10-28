package game.tetris;

/**
 * Created by johnny on 2016/9/15.
 * 方块类
 */
public class Game_Box {
    static int[][] pattern = {
            { 0x0f00, 0x4444, 0x0f00, 0x4444 },// 长条形
            { 0x04e0, 0x0464, 0x00e4, 0x04c4 },//T型
            { 0x4620, 0x6c00, 0x4620, 0x6c00 },//右Z型
            { 0x2640, 0xc600, 0x2640, 0xc600 },//Z型
            { 0x6220, 0x1700, 0x2230, 0x0740 },//L型
            { 0x6440, 0x0e20, 0x44c0, 0x8e00 },//左L型
            { 0x0660, 0x0660, 0x0660, 0x0660 }  //田字形
    };
    int blockType; // 块的模式号（0-6）
    int turnState; // 块的翻转状态（0-3）
    int blockState; // 块的下落状态
    int row;//行
    int col;//列
    Game_Draw scr; //声明类型


    // 块类的构造方法
    Game_Box(Game_Draw game_scr) {
        this.scr = game_scr;
        blockType =(int)(Math.random()*7);
//turnState = (int) Math.random()*3 ;
        blockState = 1;
        row = game_scr.getInitRow();
        col = game_scr.getInitCol();
    }


    // 重新初始化块，并显示新块
    public void reset() {
        blockType = (int)(Math.random()*7) ;//随机从7中生成一中方块
//turnState = (int) (Math.random()*3) ;
        blockState = 1;
        row = scr.getInitRow();
        col = scr.getInitCol();
        dispBlock(1);
    }


    // 实现“块”翻转的方法
    public void leftTurn() {
        if (assertValid(blockType, (turnState + 1) % 4, row, col)) {
            dispBlock(0);
            turnState = (turnState + 1) % 4;
            dispBlock(1);
        }
    }


    // 实现“块”的左移的方法
    public void leftMove() {
        if (assertValid(blockType, turnState, row, col - 1)) {
            dispBlock(0);
            col--;
            dispBlock(1);
        }
    }


    // 实现块的右移
    public void rightMove() {
        if (assertValid(blockType, turnState, row, col + 1)) {
            dispBlock(0);
            col++;
            dispBlock(1);
        }
    }


    // 实现块落下的操作的方法
    public boolean fallDown() {
        if (blockState == 2)
            return false;
        if (assertValid(blockType, turnState, row - 1, col)) {
            dispBlock(0);
            row--;
            dispBlock(1);
            return (true);
        } else {
            blockState = 2;
            dispBlock(2);
            return false;
        }
    }


    // 判断是否正确的方法
    boolean assertValid(int t, int s, int row, int col) {
        int k =0x8000;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((int) (pattern[t][s] & k) != 0) {
                    int temp = scr.getScrArrXY(row - i, col + j);
                    if (temp < 0 || temp == 2)
                        return false;
                }
                k = k/2;
            }
        }
        return true;
    }


    // 同步显示的方法
    public synchronized void dispBlock(int s) {
        int k =0x8000;
        System.out.println((int) pattern[blockType][turnState]);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (((int) pattern[blockType][turnState] & k) != 0) {
                    scr.drawUnit(row - i, col + j, s);
                }
                k = k /2;
            }
        }
    }
}


