package project.game.snake.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.EventQueue;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 5398934425180961778L;

    //TODO: 不能撞墙，不能撞自己
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private MainFrame() {
        setTitle("snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        MyPanel myPanel = new MyPanel();
        getContentPane().add(myPanel);

        this.setResizable(false);
        this.pack();
    }
}