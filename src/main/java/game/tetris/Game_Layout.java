package game.tetris;

import javax.swing.*;
import java.awt.*;

/**
 * Created by johnny on 2016/9/15.
 */
public class Game_Layout extends JFrame{
	private static final long serialVersionUID = 198665761980976795L;
	static int level = 1;
    static int score = 0;
    static TextField scoreField;
    static TextField levelField;
    static boolean isPlay = false;
    static Game_MyTimer timer;

    // 俄罗斯方块类的构造方法
    @SuppressWarnings("deprecation")
	Game_Layout() {
        setTitle("俄罗斯方块");
        setSize(620, 480);
        setLayout(new GridLayout(1, 2));//整体分为两个部分
        Game_Draw gameScr = new Game_Draw();
        gameScr.addKeyListener(gameScr);//就收键盘监听,监听的内容是游戏主界面
        timer = new Game_MyTimer(gameScr);
        timer.setDaemon(true);
        timer.start();
        timer.suspend();
        add(gameScr);
        JPanel rightScr = new JPanel();
        rightScr.setLayout(new GridLayout(2, 1, 0,0));
//rightScr.setSize(120, 480);
//LeftScr.setSize(1000, 40);
        add(rightScr);
//add(LeftScr);
// 右边信息窗体的布局
        Game_MyPanel infoScr = new Game_MyPanel();
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
        Game_MyPanel controlScr = new Game_MyPanel(); //控制面板
        controlScr.setLayout(new GridLayout(4, 1, 0, 10));//5行 1列 水平间隔为0 垂直为 5
        rightScr.add(controlScr);
// 定义按钮play
        JButton play_b = new JButton("开始游戏");
//play_b.setSize(new Dimension(50, 20));
        play_b.addActionListener(new Game_Command(1, gameScr));
// 定义按钮Level UP
        JButton level_up_b = new JButton("提高级数");
        level_up_b.addActionListener(new Game_Command(2, gameScr));
// 定义按钮Level Down
        JButton level_down_b = new JButton("降低级数");
        level_down_b.addActionListener(new Game_Command(3, gameScr));
// 定义按钮Quit
        JButton quit_b = new JButton("退出游戏");
        quit_b.addActionListener(new Game_Command(4, gameScr));
        controlScr.add(play_b);
        controlScr.add(level_up_b);
        controlScr.add(level_down_b);
//controlScr.add(pause_b);
        controlScr.add(quit_b);
        setVisible(true);
        gameScr.requestFocus();
    }
}
