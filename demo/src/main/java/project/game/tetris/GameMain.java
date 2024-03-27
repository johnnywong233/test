package project.game.tetris;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by johnny on 2016/9/15.
 * main class
 */
public class GameMain {
    //http://www.phpxs.com/code/1001579/
    public static void main(String[] args) {
        GameLayout ers = new GameLayout();
        ers.isEnabled();
        ers.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}

/**
 * Created by johnny on 2016/9/15.
 * 方块类
 */
class Box {
    private static final int[][] pattern = {
            {0x0f00, 0x4444, 0x0f00, 0x4444},// 长条形
            {0x04e0, 0x0464, 0x00e4, 0x04c4},//T型
            {0x4620, 0x6c00, 0x4620, 0x6c00},//右Z型
            {0x2640, 0xc600, 0x2640, 0xc600},//Z型
            {0x6220, 0x1700, 0x2230, 0x0740},//L型
            {0x6440, 0x0e20, 0x44c0, 0x8e00},//左L型
            {0x0660, 0x0660, 0x0660, 0x0660}  //田字形
    };
    private int blockType; // 块的模式号（0-6）
    private int turnState; // 块的翻转状态（0-3）
    private int blockState; // 块的下落状态
    private int row;//行
    private int col;//列
    private final GameDraw scr; //声明类型


    // 块类的构造方法
    Box(GameDraw gameScr) {
        this.scr = gameScr;
        blockType = (int) (Math.random() * 7);
//turnState = (int) Math.random()*3 ;
        blockState = 1;
        row = gameScr.getInitRow();
        col = gameScr.getInitCol();
    }


    // 重新初始化块，并显示新块
    public void reset() {
        blockType = (int) (Math.random() * 7);//随机从7中生成一中方块
//turnState = (int) (Math.random()*3) ;
        blockState = 1;
        row = scr.getInitRow();
        col = scr.getInitCol();
        dispBlock(1);
    }


    // 实现“块”翻转的方法
    void leftTurn() {
        if (assertValid(blockType, (turnState + 1) % 4, row, col)) {
            dispBlock(0);
            turnState = (turnState + 1) % 4;
            dispBlock(1);
        }
    }


    // 实现“块”的左移的方法
    void leftMove() {
        if (assertValid(blockType, turnState, row, col - 1)) {
            dispBlock(0);
            col--;
            dispBlock(1);
        }
    }


    // 实现块的右移
    void rightMove() {
        if (assertValid(blockType, turnState, row, col + 1)) {
            dispBlock(0);
            col++;
            dispBlock(1);
        }
    }


    // 实现块落下的操作的方法
    boolean fallDown() {
        if (blockState == 2) {
            return false;
        }
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
    private boolean assertValid(int t, int s, int row, int col) {
        int k = 0x8000;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((pattern[t][s] & k) != 0) {
                    int temp = scr.getScrArrXY(row - i, col + j);
                    if (temp < 0 || temp == 2) {
                        return false;
                    }
                }
                k = k / 2;
            }
        }
        return true;
    }


    // 同步显示的方法
    private synchronized void dispBlock(int s) {
        int k = 0x8000;
        System.out.println(pattern[blockType][turnState]);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((pattern[blockType][turnState] & k) != 0) {
                    scr.drawUnit(row - i, col + j, s);
                }
                k = k / 2;
            }
        }
    }
}

/**
 * Layout class
 */
class GameLayout extends JFrame {
    private static final long serialVersionUID = 198665761980976795L;
    static int level = 1;
    static int score = 0;
    static TextField scoreField;
    static TextField levelField;
    static boolean isPlay = false;
    static GameMyTimer timer;

    @SuppressWarnings("deprecation")
    GameLayout() {
        setTitle("俄罗斯方块");
        setSize(620, 480);
        setLayout(new GridLayout(1, 2));//整体分为两个部分
        GameDraw gameScr = new GameDraw();
        gameScr.addKeyListener(gameScr);//就收键盘监听,监听的内容是游戏主界面
        timer = new GameMyTimer(gameScr);
        timer.setDaemon(true);
        timer.start();
        timer.suspend();
        add(gameScr);
        JPanel rightScr = new JPanel();
        rightScr.setLayout(new GridLayout(2, 1, 0, 0));
        //rightScr.setSize(120, 480);
        //LeftScr.setSize(1000, 40);
        add(rightScr);
        //add(LeftScr);
        // 右边信息窗体的布局
        MyPanel infoScr = new MyPanel();
        infoScr.setLayout(new GridLayout(4, 1, 0, 0));//4行一列 水平0 垂直5
        //infoScr.setSize(120, 300);
        rightScr.add(infoScr);
        // 定义标签和初始值
        JLabel scorep = new JLabel("分数:");
        JLabel levelp = new JLabel("级数:");
        scoreField = new TextField(8);//定义文本长度
        levelField = new TextField(8);
        scoreField.setEditable(false);//文本不可以编辑
        levelField.setEditable(false);
        infoScr.add(scorep);
        infoScr.add(scoreField);
        infoScr.add(levelp);
        infoScr.add(levelField);
        //scorep.setSize(10,10);
        //scoreField.setSize(new Dimension(20, 60));
        //levelp.setSize(new Dimension(20, 60));
        //levelField.setSize(new Dimension(20, 60));
        scoreField.setText("0");
        levelField.setText("1");
        // 右边控制按钮窗体的布局
        MyPanel controlScr = new MyPanel(); //控制面板
        controlScr.setLayout(new GridLayout(4, 1, 0, 10));//5行 1列 水平间隔为0 垂直为 5
        rightScr.add(controlScr);
        // 定义按钮play
        JButton playB = new JButton("开始游戏");
        //play_b.setSize(new Dimension(50, 20));
        playB.addActionListener(new Command(1, gameScr));
        // 定义按钮Level UP
        JButton levelUpButton = new JButton("提高级数");
        levelUpButton.addActionListener(new Command(2, gameScr));
        // 定义按钮Level Down
        JButton levelDownButton = new JButton("降低级数");
        levelDownButton.addActionListener(new Command(3, gameScr));
        // 定义按钮Quit
        JButton quitB = new JButton("退出游戏");
        quitB.addActionListener(new Command(4, gameScr));
        controlScr.add(playB);
        controlScr.add(levelUpButton);
        controlScr.add(levelDownButton);
        //controlScr.add(pause_b);
        controlScr.add(quitB);
        setVisible(true);
        gameScr.requestFocus();
    }
}

/**
 * Draw class
 */
class GameDraw extends Canvas implements KeyListener {
    private static final long serialVersionUID = 5699414920465296745L;
    private final int rowNum; // 正方格的行数
    private final int columnNum; // 正方格的列数
    private final int maxAllowRowNum; // 允许有多少行未削
    private final int blockInitRow; // 新出现块的起始行坐标
    private final int blockInitCol; // 新出现块的起始列坐标
    private final int[][] scrArr; // 屏幕数组
    private final Box b = new Box(this); // 对方快的引用

    // 画布类的构造方法
    GameDraw() {
        rowNum = 15;
        columnNum = 10;
        maxAllowRowNum = rowNum - 2;
        blockInitRow = rowNum;
        blockInitCol = columnNum - 7;
        scrArr = new int[32][32];
    }

    // 初始化屏幕，并将屏幕数组清零的方法
    void initScr() {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                scrArr[i][j] = 0;
            }
        }
        b.reset();
        repaint();
    }

    // 重新刷新画布方法
    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                drawUnit(i, j, scrArr[i][j]);
            }
        }
    }

    // 画方块的方法
    void drawUnit(int row, int col, int type) {
        System.out.println(getSize().height);
        scrArr[row][col] = type;
        Graphics g = getGraphics();
        // 表示画方快的方法
        switch (type) {
            case 0:
                g.setColor(Color.BLACK);
                break; // 以背景为颜色画
            case 1:
                g.setColor(Color.blue);
                break; // 画正在下落的方块
            case 2:
                g.setColor(Color.GRAY);
                break; // 画已经落下的方法
            default:
        }
        int unitSize = 30;
        g.fill3DRect(col * unitSize, getSize().height - (row + 1) * unitSize,
                unitSize, unitSize, true);
        g.dispose();
    }


    Box getBlock() {
        return b; // 返回block实例的引用
    }


    // 返回屏幕数组中(row,col)位置的属性值
    int getScrArrXY(int row, int col) {
        if (row < 0 || row >= rowNum || col < 0 || col >= columnNum) {
            return (-1);
        } else {
            return (scrArr[row][col]);
        }
    }


    // 返回新块的初始行坐标方法
    int getInitRow() {
        return (blockInitRow); // 返回新块的初始行坐标
    }


    // 返回新块的初始列坐标方法
    int getInitCol() {
        return (blockInitCol); // 返回新块的初始列坐标
    }


    // 满行删除方法
    void deleteFullLine() {
        int fullLineNum = 0;
        int k = 0;
        for (int i = 0; i < rowNum; i++) {
            boolean isfull = true;
            for (int j = 0; j < columnNum; j++) {
                if (scrArr[i][j] == 0) {
                    k++;
                    isfull = false;
                    break;
                }
            }
            if (isfull) {
                fullLineNum += 100;
            }
            if (k != 0 && k - 1 != i && !isfull) {
                for (int j = 0; j < columnNum; j++) {
                    if (scrArr[i][j] == 0) {
                        drawUnit(k - 1, j, 0);
                    } else {
                        drawUnit(k - 1, j, 2);
                    }
                    scrArr[k - 1][j] = scrArr[i][j];
                }
            }
        }
        for (int i = k - 1; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                drawUnit(i, j, 0);
                scrArr[i][j] = 0;
            }
        }
        GameLayout.score += fullLineNum;
        GameLayout.scoreField.setText("" + GameLayout.score);
    }


    // 判断游戏是否结束方法
    boolean isGameEnd() {
        for (int col = 0; col < columnNum; col++) {
            if (scrArr[maxAllowRowNum][col] != 0) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }


    // 处理键盘输入的方法
    @Override
    public void keyPressed(KeyEvent e) {
//boolean T=true;
        if (!GameLayout.isPlay) {
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                b.fallDown();
                break;
            case KeyEvent.VK_LEFT:
                b.leftMove();
                break;
            case KeyEvent.VK_RIGHT:
                b.rightMove();
                break;
            case KeyEvent.VK_SPACE:
                b.leftTurn();
                break;
            default:
        }

    }
}

/**
 *
 */
class MyPanel extends Panel {
    private static final long serialVersionUID = 1085036222452518986L;

    @Override
    public Insets getInsets() {
        //顶 左 底 右
        return new Insets(30, 60, 30, 60);
    }
}

/**
 * Timer control
 */
class GameMyTimer extends Thread {
    private final GameDraw scr;


    GameMyTimer(GameDraw scr) {
        this.scr = scr;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run() {
        while (true) {
            try {
                sleep((10 - GameLayout.level + 1) * 100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!scr.getBlock().fallDown()) {
                scr.deleteFullLine();
                if (scr.isGameEnd()) {
                    GameLayout.isPlay = false;
                    stop();
                } else {
                    scr.getBlock().reset();
                }
            }
        }
    }
}

/**
 * define command class
 */
class Command implements ActionListener {
    // 给按钮分配编号
    static int buttonPlay = 1;
    static int buttonLevelUp = 2;
    static int buttonLevelDown = 3;
    static int buttonQuit = 4;
    static int buttonPause = 5;
    static boolean pauseResume = true;
    // 当前按钮
    private final int curButton;
    private final GameDraw scr;

    Command(int button, GameDraw scr) {
        curButton = button;
        this.scr = scr;
    }

    // 按钮执行方法
    @Override
    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        switch (curButton) {
            case 1:
                if (!GameLayout.isPlay) {
                    scr.initScr();
                    GameLayout.isPlay = true;
                    GameLayout.score = 0;
                    GameLayout.scoreField.setText("0");
                    GameLayout.timer.resume();
                }
                scr.requestFocus();
                break;
            case 2:
                if (GameLayout.level < 10) {
                    GameLayout.level++;
                    GameLayout.levelField.setText("" + GameLayout.level);
                    GameLayout.score = 0;
                    GameLayout.scoreField.setText("" + GameLayout.score);
                }
                scr.requestFocus();
                break;
            case 3:
                if (GameLayout.level > 1) {
                    GameLayout.level--;
                    GameLayout.levelField.setText("" + GameLayout.level);
                    GameLayout.score = 0;
                    GameLayout.scoreField.setText("" + GameLayout.score);
                }
                scr.requestFocus();
                break;
            case 4:
                System.exit(0);
            default:
        }
    }
}
