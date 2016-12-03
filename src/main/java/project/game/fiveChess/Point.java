package project.game.fiveChess;

import java.awt.Color;

/**
 * Author: Johnny
 * Date: 2016/11/18
 * Time: 0:21
 * 棋子类
 */
public class Point {
    private int x;//棋盘中的x索引
    private int y;//棋盘中的y索引
    private Color color;//颜色
    public static final int DIAMETER = 30;//直径

    public Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {//拿到棋盘中x的索引
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {//获得棋子的颜色
        return color;
    }
}
