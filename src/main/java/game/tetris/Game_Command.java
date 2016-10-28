package game.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by johnny on 2016/9/15.
 */
public class Game_Command implements ActionListener {
    static final int button_play = 1; // 给按钮分配编号
    static final int button_levelup = 2;
    static final int button_leveldown = 3;
    static final int button_quit = 4;
    static final int button_pause = 5;
    static boolean pause_resume = true;
    int curButton; // 当前按钮
    Game_Draw scr;


    // 控制按钮类的构造方法
    Game_Command(int button, Game_Draw scr) {
        curButton = button;
        this.scr = scr;
    }


    // 按钮执行方法
    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
        switch (curButton) {
            case 1:
                if (!Game_Layout.isPlay) {
                    scr.initScr();
                    Game_Layout.isPlay = true;
                    Game_Layout.score = 0;
                    Game_Layout.scoreField.setText("0");
                    Game_Layout.timer.resume();
                }
                scr.requestFocus();
                break;
            case 2:
                if (Game_Layout.level < 10) {
                    Game_Layout.level++;
                    Game_Layout.levelField.setText("" + Game_Layout.level);
                    Game_Layout.score = 0;
                    Game_Layout.scoreField.setText("" + Game_Layout.score);
                }
                scr.requestFocus();
                break;
            case 3:
                if (Game_Layout.level > 1) {
                    Game_Layout.level--;
                    Game_Layout.levelField.setText("" + Game_Layout.level);
                    Game_Layout.score = 0;
                    Game_Layout.scoreField.setText("" + Game_Layout.score);
                }
                scr.requestFocus();
                break;
            case 4:
                System.exit(0);
        }
    }
}
