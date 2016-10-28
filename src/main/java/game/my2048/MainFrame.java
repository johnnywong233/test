package game.my2048;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by johnny on 2016/10/6.
 *
 */
public class MainFrame extends JFrame{
    JFrame frame = new JFrame();
    JLabel label[] = new JLabel[16];
    Box b = new Box();
    GridLayout layout = new GridLayout(5,4,1,1);

    //TODO
    //https://my.oschina.net/guifengqing/blog/229488
    public static void main(String[] agrs){
        MainFrame demo = new MainFrame();
        demo.start();
    }

    public void start(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new MyKeyListener());
        DrawPanel d = new DrawPanel();
        frame.add(d);
        frame.setSize(200,200);
        frame.setVisible(true);
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode())
            {
                case KeyEvent.VK_DOWN:{
                    b.control(2);
                    if(b.suiji>0){
                        b.getsuiji();}
                    frame.repaint();
                    break;
                }
                case KeyEvent.VK_UP:{
                    b.control(1);
                    if(b.suiji>0){
                        b.getsuiji();}
                    frame.repaint();
                    break;
                }
                case KeyEvent.VK_LEFT:{
                    b.control(3);
                    if(b.suiji>0){
                        b.getsuiji();}
                    frame.repaint();
                    break;
                }
                case KeyEvent.VK_RIGHT:{
                    b.control(4);
                    if(b.suiji>0){
                        b.getsuiji();}
                    frame.repaint();
                    break;
                }
            }
        }
    }

    public class DrawPanel extends JPanel {
        Image image[] = new Image[16];
        public void paint(Graphics g){
            super.paint(g);
            for(int i = 0;i<4;i++){
                for(int j = 0;j<4;j++){
                    image[i*4+j] = new ImageIcon("images/"+b.square[i][j]+".jpg").getImage();
                    g.drawImage(image[i*4+j],j*40,i*40,this);
                }
            }
        }
    }
}
