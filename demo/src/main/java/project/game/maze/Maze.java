package project.game.maze;

import lombok.Data;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Stack;

/**
 * Author: Johnny
 * Date: 2016/11/17
 * Time: 23:56
 */
public class Maze extends JPanel {
    private static final long serialVersionUID = -8300339045454852626L;
    // width 每个格子的宽度和高度
    private int num, width, padding;
    private Lattice[][] maze;
    private int ballX, ballY;
    private boolean drawPath = false;

    private Maze(int m, int wi, int p) {
        num = m;
        width = wi;
        padding = p;
        maze = new Lattice[num][num];
        for (int i = 0; i <= num - 1; i++) {
            for (int j = 0; j <= num - 1; j++) {
                maze[i][j] = new Lattice(i, j);
            }
        }
        createMaze();
        setKeyListener();
        this.setFocusable(true);
    }

    private void init() {
        for (int i = 0; i <= num - 1; i++) {
            for (int j = 0; j <= num - 1; j++) {
                maze[i][j].setFather(null);
                maze[i][j].setFlag(Lattice.NOTINTREE);
            }
        }
        ballX = 0;
        ballY = 0;
        drawPath = false;
        createMaze();
        setKeyListener();
        this.setFocusable(true);
        repaint();
    }

    private int getCenterX(int x) {
        return padding + x * width + width / 2;
    }

    private int getCenterY(int y) {
        return padding + y * width + width / 2;
    }

    private int getCenterX(Lattice p) {
        return padding + p.getY() * width + width / 2;
    }

    private int getCenterY(Lattice p) {
        return padding + p.getX() * width + width / 2;
    }

    private void checkIsWin() {
        if (ballX == num - 1 && ballY == num - 1) {
            JOptionPane.showMessageDialog(null, "YOU WIN !", "你走出了迷宫。",
                    JOptionPane.PLAIN_MESSAGE);
            init();
        }
    }

    synchronized private void move(int c) {
        int tx = ballX, ty = ballY;
        // System.out.println(c);
        switch (c) {
            case KeyEvent.VK_LEFT:
                ty--;
                break;
            case KeyEvent.VK_RIGHT:
                ty++;
                break;
            case KeyEvent.VK_UP:
                tx--;
                break;
            case KeyEvent.VK_DOWN:
                tx++;
                break;
            case KeyEvent.VK_SPACE:
                drawPath = !drawPath;
                break;
            default:
        }
        if (!isOutOfBorder(tx, ty)
                && (maze[tx][ty].getFather() == maze[ballX][ballY]
                || maze[ballX][ballY].getFather() == maze[tx][ty])) {
            ballX = tx;
            ballY = ty;
        }
    }

    private void setKeyListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int c = e.getKeyCode();
                move(c);
                repaint();
                checkIsWin();
            }
        });
    }

    private boolean isOutOfBorder(Lattice p) {
        return isOutOfBorder(p.getX(), p.getY());
    }

    private boolean isOutOfBorder(int x, int y) {
        return (x > num - 1 || y > num - 1 || x < 0 || y < 0);
    }

    private Lattice[] getNeis(Lattice p) {
        // 顺序为上右下左
        final int[] adds = {-1, 0, 1, 0, -1};
        if (isOutOfBorder(p)) {
            return null;
        }
        Lattice[] ps = new Lattice[4];
        int xt;
        int yt;
        for (int i = 0; i <= 3; i++) {
            xt = p.getX() + adds[i];
            yt = p.getY() + adds[i + 1];
            if (isOutOfBorder(xt, yt)) {
                continue;
            }
            ps[i] = maze[xt][yt];
        }
        return ps;
    }

    private void createMaze() {
        Random random = new Random();
        int rx = Math.abs(random.nextInt()) % num;
        int ry = Math.abs(random.nextInt()) % num;
        Stack<Lattice> s = new Stack<>();
        Lattice p = maze[rx][ry];
        Lattice neis[];
        s.push(p);
        while (!s.isEmpty()) {
            p = s.pop();
            p.setFlag(Lattice.INTREE);
            neis = getNeis(p);
            int ran = Math.abs(random.nextInt()) % 4;
            for (int a = 0; a <= 3; a++) {
                ran++;
                ran %= 4;
                assert neis != null;
                if (neis[ran] == null || neis[ran].getFlag() == Lattice.INTREE) {
                    continue;
                }
                s.push(neis[ran]);
                neis[ran].setFather(p);
            }
        }
        changeFather(maze[0][0], null);
    }

    private void changeFather(Lattice p, Lattice f) {
        if (p.getFather() == null) {
            p.setFather(f);
        } else {
            changeFather(p.getFather(), p);
        }
    }

    private void clearFence(int i, int j, int fx, int fy, Graphics g) {
        int sx = padding + ((j > fy ? j : fy) * width),
                sy = padding + ((i > fx ? i : fx) * width),
                dx = (i == fx ? sx : sx + width),
                dy = (i == fx ? sy + width : sy);
        if (sx != dx) {
            sx++;
            dx--;
        } else {
            sy++;
            dy--;
        }
        g.drawLine(sx, sy, dx, dy);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= num; i++) {
            g.drawLine(padding + i * width, padding, padding + i * width,
                    padding + num * width);
        }
        for (int j = 0; j <= num; j++) {
            g.drawLine(padding, padding + j * width, padding + num * width,
                    padding + j * width);
        }
        g.setColor(this.getBackground());
        for (int i = num - 1; i >= 0; i--) {
            for (int j = num - 1; j >= 0; j--) {
                Lattice f = maze[i][j].getFather();
                if (f != null) {
                    int fx = f.getX(), fy = f.getY();
                    clearFence(i, j, fx, fy, g);
                }
            }
        }
        g.drawLine(padding, padding + 1, padding, padding + width - 1);
        int last = padding + num * width;
        g.drawLine(last, last - 1, last, last - width + 1);
        g.setColor(Color.RED);
        g.fillOval(getCenterX(ballY) - width / 3, getCenterY(ballX) - width / 3,
                width / 2, width / 2);
        if (drawPath) {
            drawPath(g);
        }
    }

    private void drawPath(Graphics g) {
        Color pathColor = Color.ORANGE, bothPathColor = Color.PINK;
        if (drawPath) {
            g.setColor(pathColor);
        } else {
            g.setColor(this.getBackground());
        }
        Lattice p = maze[num - 1][num - 1];
        while (p.getFather() != null) {
            p.setFlag(2);
            p = p.getFather();
        }
        g.fillOval(getCenterX(p) - width / 3, getCenterY(p) - width / 3,
                width / 2, width / 2);
        p = maze[0][0];
        while (p.getFather() != null) {
            if (p.getFlag() == 2) {
                p.setFlag(3);
                g.setColor(bothPathColor);
            }
            g.drawLine(getCenterX(p), getCenterY(p), getCenterX(p.getFather()),
                    getCenterY(p.getFather()));
            p = p.getFather();
        }
        g.setColor(pathColor);
        p = maze[num - 1][num - 1];
        while (p.getFather() != null) {
            if (p.getFlag() == 3) {
                break;
            }
            g.drawLine(getCenterX(p), getCenterY(p), getCenterX(p.getFather()),
                    getCenterY(p.getFather()));
            p = p.getFather();
        }
    }

    //TODO:并不能空格操作。
    //http://www.jb51.net/article/83757.htm
    public static void main(String[] args) {
        final int n = 30, width = 600, padding = 20, lx = 200, ly = 100;
        JPanel p = new Maze(n, (width - padding - padding) / n, padding);
        JFrame frame = new JFrame("MAZE(按空格键显示或隐藏路径)");
        frame.getContentPane().add(p);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width + padding, width + padding + padding);
        frame.setLocation(lx, ly);
        frame.setVisible(true);
    }
}

@Data
class Lattice {
    static final int INTREE = 1;
    static final int NOTINTREE = 0;
    private int x = -1;
    private int y = -1;
    private int flag = NOTINTREE;
    private Lattice father = null;

    Lattice(int xx, int yy) {
        x = xx;
        y = yy;
    }
}
