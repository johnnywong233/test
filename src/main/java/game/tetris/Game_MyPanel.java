package game.tetris;

import java.awt.Insets;
import java.awt.Panel;

/**
 * Created by johnny on 2016/9/15.
 */
public class Game_MyPanel extends Panel{
	private static final long serialVersionUID = 1085036222452518986L;

	public Insets getInsets() {
        return new Insets(30, 60, 30, 60);//顶 左 底 右
    }
}
