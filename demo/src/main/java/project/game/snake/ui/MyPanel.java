package project.game.snake.ui;

import project.game.snake.model.Direction;
import project.game.snake.model.Point;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyPanel extends JPanel {
    private static final long serialVersionUID = -989064855059247656L;
    private static final int COLS = 15;
    private static final int ROWS = 20;
    private static final int SPAN = 20;

    private List<Image> imgs;//pic resources

    private Random random = new Random();

    private List<Point> snake = new ArrayList<>(0);
    private Direction d = Direction.UP;

    private Direction d1 = Direction.UP;

    private List<Point> food = new ArrayList<>(0);

    private boolean play = true;//game start

    MyPanel() {
        try {
            Image bg = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/bg.png"));
            imgs = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                ClassLoader classLoader = getClass().getClassLoader();
                imgs.add(ImageIO.read(new File(classLoader.getResource(i + ".png").getFile())));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "read error");
            System.exit(0);
        }

        snake.add(new Point(COLS / 2, ROWS / 2, random.nextInt(2)));

        addFood();

        Timer timer = new Timer(400, arg0 -> {
            if (food.size() == 0) {
                addFood();
            }

            if (d == Direction.UP && d1 == Direction.DOWN) {
                d1 = Direction.UP;
            }

            if (d == Direction.DOWN && d1 == Direction.UP) {
                d1 = Direction.DOWN;
            }

            if (d == Direction.LEFT && d1 == Direction.RIGHT) {
                d1 = Direction.LEFT;
            }

            if (d == Direction.RIGHT && d1 == Direction.LEFT) {
                d1 = Direction.RIGHT;
            }

            d = d1;
            switch (d) {
                case UP:
                    movePoint(0, -1);
                    break;
                case DOWN:
                    movePoint(0, 1);
                    break;
                case LEFT:
                    movePoint(-1, 0);
                    break;
                case RIGHT:
                    movePoint(1, 0);
                    break;
                case STOP:
                    break;

                default:
                    break;
            }
        });

        timer.start();

        setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case 38:// up
                        d1 = Direction.UP;
                        break;
                    case 40:// down
                        d1 = Direction.DOWN;
                        break;
                    case 37:// left
                        d1 = Direction.LEFT;
                        break;
                    case 39:// right
                        d1 = Direction.RIGHT;
                        break;
                    case 32:// stop
                        d1 = Direction.STOP;
                        break;
                    case 27:// exit
                        System.exit(0);
                        break;
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                MyPanel.this.requestFocusInWindow();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                //move mouse
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g1) {
        BufferedImage bg = new BufferedImage(COLS * SPAN, ROWS * SPAN,
                BufferedImage.TYPE_INT_ARGB);
        Graphics g = bg.getGraphics();

        g.drawImage(bg, 0, 0, COLS * SPAN, ROWS * SPAN, this);
        g.setColor(SystemColor.WHITE);
        g.fillRect(0, 0, bg.getWidth(), bg.getHeight());

        for (Point p : snake) {
            g.drawImage(imgs.get(p.getValue()), p.getX() * SPAN, p.getY() * SPAN, SPAN, SPAN, this);
        }

        for (Point p : food) {
            g.drawImage(imgs.get(p.getValue()), p.getX() * SPAN, p.getY() * SPAN, SPAN, SPAN, this);
        }
        g1.drawImage(bg, 0, 0, this);
    }

    private void movePoint(int offsetX, int offsetY) {
        if (!play) {
            return;
        }

        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }
        snake.get(0).setX(snake.get(0).getX() + offsetX);
        snake.get(0).setY(snake.get(0).getY() + offsetY);

        int index = food.indexOf(snake.get(0));
        if (index > -1) {
            snake.add(food.get(index));
            food.remove(food.get(index));
        }
        repaint();
        if (isWin()) {
            JOptionPane.showMessageDialog(null, "you win");
            play = false;
        }
    }

    private void addFood() {
        Point p = new Point(random.nextInt(COLS), random.nextInt(ROWS), random.nextInt(2));
        while (snake.contains(p)) {
            p = new Point(random.nextInt(COLS), random.nextInt(ROWS), random.nextInt(2));
        }
        food.add(p);
    }

    private boolean isWin() {
        return false;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COLS * SPAN, ROWS * SPAN);
    }

}