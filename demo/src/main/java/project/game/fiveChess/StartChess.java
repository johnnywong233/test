package project.game.fiveChess;

import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

/**
 * Author: Johnny
 * Date: 2016/11/18
 * Time: 0:22
 */
public class StartChess extends JFrame {
    private static final long serialVersionUID = 4452245453242087401L;
    private ChessBoard chessBoard;
    private JButton startButton, backButton, exitButton;

    private JMenuItem startMenuItem, exitMenuItem, backMenuItem;

    //重新开始，退出，和悔棋菜单项
    private StartChess() {
        setTitle("单机版五子棋");//设置标题
        chessBoard = new ChessBoard();

        Container contentPane = getContentPane();
        contentPane.add(chessBoard);
        chessBoard.setOpaque(true);

        //创建和添加菜单
        JMenuBar menuBar = new JMenuBar();
        JMenu sysMenu = new JMenu("系统");
        //初始化菜单项
        startMenuItem = new JMenuItem("重新开始");
        exitMenuItem = new JMenuItem("退出");
        backMenuItem = new JMenuItem("悔棋");
        //将三个菜单项添加到菜单上
        sysMenu.add(startMenuItem);
        sysMenu.add(exitMenuItem);
        sysMenu.add(backMenuItem);
        //初始化按钮事件监听器内部类
        MyItemListener lis = new MyItemListener();
        //将三个菜单注册到事件监听器上
        this.startMenuItem.addActionListener(lis);
        backMenuItem.addActionListener(lis);
        exitMenuItem.addActionListener(lis);
        menuBar.add(sysMenu);//将系统菜单添加到菜单栏上
        setJMenuBar(menuBar);//将menuBar设置为菜单栏

        JPanel toolbar = new JPanel();
        //三个按钮初始化
        startButton = new JButton("重新开始");
        exitButton = new JButton("退出");
        backButton = new JButton("悔棋");
        //将工具面板按钮用FlowLayout布局
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        //将三个按钮添加到工具面板
        toolbar.add(startButton);
        toolbar.add(exitButton);
        toolbar.add(backButton);
        //将三个按钮注册监听事件
        startButton.addActionListener(lis);
        exitButton.addActionListener(lis);
        backButton.addActionListener(lis);
        //将工具面板布局到界面”南方“也就是下方
        add(toolbar, BorderLayout.SOUTH);
        add(chessBoard);//将面板对象添加到窗体上
        //设置界面关闭事件
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(800,800);
        pack();//自适应大小

    }

    private class MyItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();//获得事件源
            if (obj == StartChess.this.startMenuItem || obj == startButton) {
                //重新开始
                //JFiveFrame.this内部类引用外部类
                System.out.println("重新开始");
                chessBoard.restartGame();
            } else if (obj == exitMenuItem || obj == exitButton)
                System.exit(0);
            else if (obj == backMenuItem || obj == backButton) {
                System.out.println("悔棋...");
                chessBoard.goBack();
            }
        }
    }

    //http://www.jb51.net/article/79393.htm
    public static void main(String[] args) {
        StartChess f = new StartChess();//创建主框架
        f.setVisible(true);//显示主框架
    }
}

/**
 * Author: Johnny
 * Date: 2016/11/18
 * Time: 0:21
 * 棋子类
 */
@Data
class Point {
    private int x;//棋盘中的x索引
    private int y;//棋盘中的y索引
    private Color color;//颜色
    static final int DIAMETER = 30;//直径

    Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}

/**
 * Author: Johnny
 * Date: 2016/11/18
 * Time: 0:19
 */
class ChessBoard extends JPanel implements MouseListener {
    private static final long serialVersionUID = -4864777310334461609L;
    private static final int MARGIN = 30;//边距
    private static final int GRID_SPAN = 35;//网格间距
    private static final int ROWS = 15;//棋盘行数
    private static final int COLS = 15;//棋盘列数

    private Point[] chessList = new Point[(ROWS + 1) * (COLS + 1)];//初始每个数组元素为null
    private boolean isBlack = true;//默认开始是黑棋先
    private boolean gameOver = false;//游戏是否结束
    private int chessCount;//当前棋盘棋子的个数
    private int xIndex, yIndex;//当前刚下棋子的索引

    private Image img;

    ChessBoard() {
        // setBackground(Color.blue);
        img = Toolkit.getDefaultToolkit().getImage("board.jpg");//so where is this jpg?
        addMouseListener(this);
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {

            }

            public void mouseMoved(MouseEvent e) {
                int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                //将鼠标点击的坐标位置转成网格索引
                int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
                //游戏已经结束不能下
                //落在棋盘外不能下
                //x，y位置已经有棋子存在，不能下
                if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameOver || findChess(x1, y1))
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    //设置成默认状态
                else setCursor(new Cursor(Cursor.HAND_CURSOR));

            }
        });
    }


    //绘制
    public void paintComponent(Graphics g) {

        super.paintComponent(g);//画棋盘

        int imgWidth = img.getWidth(this);
        int imgHeight = img.getHeight(this);//获得图片的宽度与高度
        int FWidth = getWidth();
        int FHeight = getHeight();//获得窗口的宽度与高度
        int x = (FWidth - imgWidth) / 2;
        int y = (FHeight - imgHeight) / 2;
        g.drawImage(img, x, y, null);


        for (int i = 0; i <= ROWS; i++) {//画横线
            g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + COLS * GRID_SPAN, MARGIN + i * GRID_SPAN);
        }
        for (int i = 0; i <= COLS; i++) {//画竖线
            g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + ROWS * GRID_SPAN);

        }

        //画棋子
        for (int i = 0; i < chessCount; i++) {
            //网格交叉点x，y坐标
            int xPos = chessList[i].getX() * GRID_SPAN + MARGIN;
            int yPos = chessList[i].getY() * GRID_SPAN + MARGIN;
            g.setColor(chessList[i].getColor());//设置颜色
            // g.fillOval(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2,
            //Point.DIAMETER, Point.DIAMETER);
            //g.drawImage(shadows, xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, Point.DIAMETER, Point.DIAMETER, null);
            Color colortemp = chessList[i].getColor();
            if (colortemp == Color.black) {
                RadialGradientPaint paint = new RadialGradientPaint(xPos - Point.DIAMETER / 2 + 25, yPos - Point.DIAMETER / 2 + 10, 20, new float[]{0f, 1f}
                        , new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(paint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

            } else if (colortemp == Color.white) {
                RadialGradientPaint paint = new RadialGradientPaint(xPos - Point.DIAMETER / 2 + 25, yPos - Point.DIAMETER / 2 + 10, 70, new float[]{0f, 1f}
                        , new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(paint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

            }

            Ellipse2D e = new Ellipse2D.Float(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2, 34, 35);
            ((Graphics2D) g).fill(e);
            //标记最后一个棋子的红矩形框

            if (i == chessCount - 1) {//如果是最后一个棋子
                g.setColor(Color.red);
                g.drawRect(xPos - Point.DIAMETER / 2, yPos - Point.DIAMETER / 2,
                        34, 35);
            }
        }
    }

    public void mousePressed(MouseEvent e) {//鼠标在组件上按下时调用

        //游戏结束时，不再能下
        if (gameOver) return;

        String colorName = isBlack ? "黑棋" : "白棋";

        //将鼠标点击的坐标位置转换成网格索引
        xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
        yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;

        //落在棋盘外不能下
        if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)
            return;

        //如果x，y位置已经有棋子存在，不能下
        if (findChess(xIndex, yIndex)) return;

        //可以进行时的处理
        Point ch = new Point(xIndex, yIndex, isBlack ? Color.black : Color.white);
        chessList[chessCount++] = ch;
        repaint();//通知系统重新绘制


        //如果胜出则给出提示信息，不能继续下棋

        if (isWin()) {
            String msg = String.format("恭喜，%s赢了！", colorName);
            JOptionPane.showMessageDialog(this, msg);
            gameOver = true;
        }
        isBlack = !isBlack;
    }

    //覆盖mouseListener的方法
    public void mouseClicked(MouseEvent e) {
        //鼠标按键在组件上单击时调用
    }

    public void mouseEntered(MouseEvent e) {
        //鼠标进入到组件上时调用
    }

    public void mouseExited(MouseEvent e) {
        //鼠标离开组件时调用
    }

    public void mouseReleased(MouseEvent e) {
        //鼠标按钮在组件上释放时调用
    }

    //在棋子数组中查找是否有索引为x，y的棋子存在
    private boolean findChess(int x, int y) {
        for (Point c : chessList) {
            if (c != null && c.getX() == x && c.getY() == y)
                return true;
        }
        return false;
    }


    private boolean isWin() {
        int continueCount = 1;//连续棋子的个数

        //横向向西寻找
        for (int x = xIndex - 1; x >= 0; x--) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, yIndex, c) != null) {
                continueCount++;
            } else
                break;
        }
        //横向向东寻找
        for (int x = xIndex + 1; x <= COLS; x++) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, yIndex, c) != null) {
                continueCount++;
            } else
                break;
        }
        if (continueCount >= 5) {
            return true;
        } else
            continueCount = 1;

        //继续另一种搜索纵向
        //向上搜索
        for (int y = yIndex - 1; y >= 0; y--) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(xIndex, y, c) != null) {
                continueCount++;
            } else
                break;
        }
        //纵向向下寻找
        for (int y = yIndex + 1; y <= ROWS; y++) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(xIndex, y, c) != null)
                continueCount++;
            else
                break;

        }
        if (continueCount >= 5)
            return true;
        else
            continueCount = 1;


        //继续另一种情况的搜索：斜向
        //东北寻找
        for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= COLS; x++, y--) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null) {
                continueCount++;
            } else break;
        }
        //西南寻找
        for (int x = xIndex - 1, y = yIndex + 1; x >= 0 && y <= ROWS; x--, y++) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null) {
                continueCount++;
            } else break;
        }
        if (continueCount >= 5)
            return true;
        else continueCount = 1;


        //继续另一种情况的搜索：斜向
        //西北寻找
        for (int x = xIndex - 1, y = yIndex - 1; x >= 0 && y >= 0; x--, y--) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null)
                continueCount++;
            else break;
        }
        //东南寻找
        for (int x = xIndex + 1, y = yIndex + 1; x <= COLS && y <= ROWS; x++, y++) {
            Color c = isBlack ? Color.black : Color.white;
            if (getChess(x, y, c) != null)
                continueCount++;
            else break;
        }
        return continueCount >= 5;
    }

    private Point getChess(int xIndex, int yIndex, Color color) {
        for (Point p : chessList) {
            if (p != null && p.getX() == xIndex && p.getY() == yIndex
                    && p.getColor() == color)
                return p;
        }
        return null;
    }


    void restartGame() {
        //清除棋子
        for (int i = 0; i < chessList.length; i++) {
            chessList[i] = null;
        }
        //恢复游戏相关的变量值
        isBlack = true;
        gameOver = false; //游戏是否结束
        chessCount = 0; //当前棋盘棋子个数
        repaint();
    }

    //悔棋
    void goBack() {
        if (chessCount == 0)
            return;
        chessList[chessCount - 1] = null;
        chessCount--;
        if (chessCount > 0) {
            xIndex = chessList[chessCount - 1].getX();
            yIndex = chessList[chessCount - 1].getY();
        }
        isBlack = !isBlack;
        repaint();
    }

    //矩形Dimension

    public Dimension getPreferredSize() {
        return new Dimension(MARGIN * 2 + GRID_SPAN * COLS, MARGIN * 2
                + GRID_SPAN * ROWS);
    }
}
