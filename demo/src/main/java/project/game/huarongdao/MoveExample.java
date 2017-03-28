package project.game.huarongdao;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Author: Johnny
 * Date: 2017/1/14
 * Time: 22:14
 */
public class MoveExample {
    public static void main(String args[]) {
        new Hua_Rong_Road();
    }
}

class Person extends JButton implements FocusListener {
    private static final long serialVersionUID = 5558165565531610960L;
    int number;
    private Color c;

    Person(int number, String s) {
        super(s);
        this.number = number;
        c = getBackground();
        setFont(new Font("宋体", Font.CENTER_BASELINE, 14));
        addFocusListener(this);
    }

    public void focusGained(FocusEvent e) {
        setBackground(Color.cyan);
    }

    public void focusLost(FocusEvent e) {
        setBackground(c);
    }
}

class Hua_Rong_Road extends JFrame implements KeyListener, MouseListener, ActionListener {
    private static final long serialVersionUID = 6961895641557715088L;
    private Person person[] = new Person[10];
    private JButton left, right, above, below;
    private JButton restart = new JButton("restart..");
    private Container con;

    Hua_Rong_Road() {
        init();
        setBounds(100, 100, 320, 360);
        setVisible(true);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init() {
        con = getContentPane();
        con.setLayout(null);
        con.add(restart);
        restart.setBounds(100, 5, 120, 25);
        restart.addActionListener(this);
        String name[] = {"曹操", "关羽", "张", "刘", "马", "许", "兵", "兵", "兵", "兵"};
        for (int i = 0; i < name.length; i++) {
            person[i] = new Person(i, name[i]);
            person[i].addKeyListener(this);
            person[i].addMouseListener(this);
            con.add(person[i]);
        }
        person[0].setBounds(104, 54, 100, 100);
        person[1].setBounds(104, 154, 100, 50);
        person[2].setBounds(54, 154, 50, 100);
        person[3].setBounds(204, 154, 50, 100);
        person[4].setBounds(54, 54, 50, 100);
        person[5].setBounds(204, 54, 50, 100);
        person[6].setBounds(54, 254, 50, 50);
        person[7].setBounds(204, 254, 50, 50);
        person[8].setBounds(104, 204, 50, 50);
        person[9].setBounds(154, 204, 50, 50);
        person[9].requestFocus();
        left = new JButton();
        right = new JButton();
        above = new JButton();
        below = new JButton();
        con.add(left);
        con.add(right);
        con.add(above);
        con.add(below);
        left.setBounds(49, 49, 5, 260);
        right.setBounds(254, 49, 5, 260);
        above.setBounds(49, 49, 210, 5);
        below.setBounds(49, 304, 210, 5);
        con.validate();
    }

    public void keyPressed(KeyEvent e) {
        Person man = (Person) e.getSource();
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            goDown(man);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            goUp(man);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            goLeft(man);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            goRight(man);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        Person man = (Person) e.getSource();
        int x, y;
        x = e.getX();
        y = e.getY();
        int w = man.getBounds().width;
        int h = man.getBounds().height;
        if (y > h / 2) {
            goDown(man);
        }
        if (y < h / 2) {
            goUp(man);
        }
        if (x < w / 2) {
            goLeft(man);
        }
        if (x > w / 2) {
            goRight(man);
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    private void goDown(Person man) {
        boolean move = true;
        Rectangle manRect = man.getBounds();
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        y = y + 50;
        manRect.setLocation(x, y);
        Rectangle belowRect = below.getBounds();
        for (int i = 0; i < 10; i++) {
            Rectangle personRect = person[i].getBounds();
            if ((manRect.intersects(personRect)) && (man.number != i)) {
                move = false;
            }
        }
        if (manRect.intersects(belowRect)) {
            move = false;
        }
        if (move == true) {
            man.setLocation(x, y);
        }
    }

    private void goUp(Person man) {
        boolean move = true;
        Rectangle manRect = man.getBounds();
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        y = y - 50;
        manRect.setLocation(x, y);
        Rectangle aboveRect = above.getBounds();
        for (int i = 0; i < 10; i++) {
            Rectangle personRect = person[i].getBounds();
            if ((manRect.intersects(personRect)) && (man.number != i)) {
                move = false;
            }
        }
        if (manRect.intersects(aboveRect)) {
            move = false;
        }
        if (move == true) {
            man.setLocation(x, y);
        }
    }

    private void goLeft(Person man) {
        boolean move = true;
        Rectangle manRect = man.getBounds();
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        x = x - 50;
        manRect.setLocation(x, y);
        Rectangle leftRect = left.getBounds();
        for (int i = 0; i < 10; i++) {
            Rectangle personRect = person[i].getBounds();
            if ((manRect.intersects(personRect)) && (man.number != i)) {
                move = false;
            }
        }
        if (manRect.intersects(leftRect)) {
            move = false;
        }
        if (move == true) {
            man.setLocation(x, y);
        }
    }

    //TODO:try to refactor
    @SuppressWarnings("unused")
    private void go(Person man) {
        boolean move = true;
        Rectangle manRect = man.getBounds();
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        x = x - 50;
        manRect.setLocation(x, y);
        Rectangle leftRect = left.getBounds();
        for (int i = 0; i < 10; i++) {
            Rectangle personRect = person[i].getBounds();
            if ((manRect.intersects(personRect)) && (man.number != i)) {
                move = false;
            }
        }
        if (manRect.intersects(leftRect)) {
            move = false;
        }
        if (move == true) {
            man.setLocation(x, y);
        }
    }

    private void goRight(Person man) {
        boolean move = true;
        Rectangle manRect = man.getBounds();
        int x = man.getBounds().x;
        int y = man.getBounds().y;
        x = x + 50;
        manRect.setLocation(x, y);
        Rectangle rightRect = right.getBounds();
        for (int i = 0; i < 10; i++) {
            Rectangle personRect = person[i].getBounds();
            if ((manRect.intersects(personRect)) && (man.number != i)) {
                move = false;
            }
        }
        if (manRect.intersects(rightRect)) {
            move = false;
        }
        if (move == true) {
            man.setLocation(x, y);
        }
    }

    public void actionPerformed(ActionEvent e) {
        con.removeAll();
        init();
        validate();
        repaint();
    }
}

