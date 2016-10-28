package game.tetris;

/**
 * Created by johnny on 2016/9/15.
 */
public class Game_MyTimer extends Thread{
    private Game_Draw scr;


    Game_MyTimer(Game_Draw scr) {
        this.scr = scr;
    }


    @SuppressWarnings("deprecation")
	public void run() {
        while (true) {
            try {
                sleep((10 - Game_Layout.level + 1) * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!scr.getBlock().fallDown()) {
                scr.deleteFullLine();
                if (scr.isGameEnd()) {
                    Game_Layout.isPlay = false;
                    stop();
                } else
                    scr.getBlock().reset();
            }
        }
    }
}
