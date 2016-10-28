package awt;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;

/**
 * Created by wajian on 2016/8/30.
 * Java版打字练习软件
 */
public class RapidTyping extends JFrame implements Runnable{

    private static final long serialVersionUID = -2831332650077025158L;

    JPanel contentPane;
    JPanel jPanel1 = new JPanel();
    JButton jButton1 = new JButton();
    JSlider jSlider1 = new JSlider();
    JLabel jLabel1 = new JLabel();
    JButton jButton2 = new JButton();
    JLabel jLabel2 = new JLabel();
    int count = 1, rapidity = 5; // count 当前进行的个数, rapidity 游标的位置
    int zhengque = 0, cuowu = 0;
    int rush[] = { 10, 20, 30 }; // 游戏每关的个数 可以自由添加
    int rush_count = 0; // 记录关数
    char list[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; // 随机出现的数字
    // 可以自由添加
    Vector number = new Vector();
    String paiduan = "true";
    AudioClip Musci_anjian, Music_shibai, Music_chenggong;

    public RapidTyping() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            // -----------------声音文件---------------------
            Musci_anjian = Applet.newAudioClip(new File("sounds//anjian.wav")
                    .toURL());
            Music_shibai = Applet.newAudioClip(new File("sounds//shibai.wav")
                    .toURL());
            Music_chenggong = Applet.newAudioClip(new File(
                    "sounds//chenggong.wav").toURL());
            // ---------------------------------------
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 初始化
     *
     * @throws Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(588, 530));
        setTitle("Frame Title");
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setBounds(new Rectangle(4, 4, 573, 419));
        jPanel1.setLayout(null);
        jButton1.setBounds(new Rectangle(277, 442, 89, 31));
        jButton1.setText("开始");
        jButton1.addActionListener(new Frame1_jButton1_actionAdapter(this));
        jSlider1.setBounds(new Rectangle(83, 448, 164, 21));
        jSlider1.setMaximum(100);
        jSlider1.setMinimum(0);
        jSlider1.setValue(95);
        jLabel1.setText("速度");
        jLabel1.setBounds(new Rectangle(35, 451, 39, 18));
        jButton2.setBounds(new Rectangle(408, 442, 89, 31));
        jButton2.setText("结束");
        jButton2.addActionListener(new Frame1_jButton2_actionAdapter(this));
        jLabel2.setText("第一关:10个");
        jLabel2.setBounds(new Rectangle(414, 473, 171, 21));
        contentPane.add(jPanel1);
        contentPane.add(jButton2);
        contentPane.add(jButton1);
        contentPane.add(jSlider1);
        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        this.addKeyListener(new MyListener());
        jButton1.addKeyListener(new MyListener());
        jSlider1.addKeyListener(new MyListener());
        jSlider1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                rapidity = 100 - jSlider1.getValue();
            }
        });
    }

    /**
     * 主线程
     */
    public void run() {
        number.clear();
        zhengque = 0;
        cuowu = 0;
        paiduan = "true";
        while (count <= rush[rush_count]) {
            try {
                Thread t = new Thread(new Tthread());
                t.start();
                count += 1;
                Thread.sleep(50 + (int) (Math.random() * 500)); // 生产下组停顿时间
                // 最快0.05秒.最慢0.5秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) { // 等待最后一个字符消失
            if (number.size() == 0) {
                break;
            }
        }
        if (zhengque == 0) { // 为了以后相除..如果全部正确或者错误就会出现错误. 所以..
            zhengque = 1;
        }

        if (paiduan.equals("true")) { // 判断是否是自然结束
            if (cuowu <= 2) { // 错误不超过2个的过关
                JOptionPane.showMessageDialog(null, "恭喜你过关了");
                rush_count += 1; // 自动加1关
                if (rush_count < rush.length) {
                    if (rapidity > 10) { // 当速度大于10的时候在-5提加速度.怕速度太快
                        rapidity -= 5; // 速度自动减10毫秒
                        jSlider1.setValue(rapidity); // 选择位置
                    }
                    Thread t = new Thread(this);
                    t.start();
                } else {
                    JOptionPane.showMessageDialog(null, "牛B...你通关了..");
                    rush_count = 0;
                    count = 0;
                }
            } else {
                JOptionPane.showMessageDialog(null, "请再接再励");
                rush_count = 0;
                count = 0;
            }
        } else {
            rush_count = 0;
            count = 0;
        }
    }

    public void jButton1_actionPerformed(ActionEvent e) {
        Thread t = new Thread(this);
        t.start();
    }

    public void jButton2_actionPerformed(ActionEvent e) {
        count = rush[rush_count] + 1;
        paiduan = "flase";
    }

    /**
     * 字符下移线程
     */
    class Tthread implements Runnable {
        public void run() {
            boolean fo = true;
            int Y = 0, X = 0;
            JLabel show = new JLabel();
            show.setFont(new java.awt.Font("宋体", Font.PLAIN, 33));
            jPanel1.add(show);
            X = 10 + (int) (Math.random() * 400);
            String parameter = list[(int) (Math.random() * list.length)] + "";
            Bean bean = new Bean();
            bean.setParameter(parameter);
            bean.setShow(show);
            number.add(bean);
            show.setText(parameter);
            while (fo) {
                // ---------------------字符下移--------------------
                show.setBounds(new Rectangle(X, Y += 2, 33, 33));
                try {
                    Thread.sleep(rapidity);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Y >= 419) {
                    fo = false;
                    for (int i = number.size() - 1; i >= 0; i--) {
                        Bean bn = ((Bean) number.get(i));
                        if (parameter.equalsIgnoreCase(bn.getParameter())) {
                            cuowu += 1;
                            jLabel2.setText("正确:" + zhengque + "个,错误:" + cuowu
                                    + "个");
                            number.removeElementAt(i);
                            Music_shibai.play();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 键盘监听器
     *
     */
    class MyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            String uu = e.getKeyChar() + "";
            for (int i = 0; i < number.size(); i++) {
                Bean bean = ((Bean) number.get(i));
                if (uu.equalsIgnoreCase(bean.getParameter())) {
                    zhengque += 1;
                    number.removeElementAt(i);
                    bean.getShow().setVisible(false);
                    jLabel2.setText("正确:" + zhengque + "个,错误:" + cuowu + "个");
                    Music_chenggong.play();
                    break;
                }
            }
            Musci_anjian.play();
        }
    }

    //http://www.phpxs.com/code/1002083/
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        RapidTyping frame = new RapidTyping();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setTitle("java版打字练习软件");
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }
}


class Frame1_jButton2_actionAdapter implements ActionListener {
    private RapidTyping adaptee;

    Frame1_jButton2_actionAdapter(RapidTyping adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}


class Frame1_jButton1_actionAdapter implements ActionListener {
    private RapidTyping adaptee;

    Frame1_jButton1_actionAdapter(RapidTyping adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton1_actionPerformed(e);
    }
}


/**下落的字符类 */
class Bean {
    String parameter = null;
    JLabel show = null;

    public JLabel getShow() {
        return show;
    }

    public void setShow(JLabel show) {
        this.show = show;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}