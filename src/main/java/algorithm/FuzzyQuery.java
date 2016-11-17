package algorithm;

import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wajian on 2016/8/16.
 */
public class FuzzyQuery {
    //http://www.jb51.net/article/83264.htm
    public static void main(String args[]) {
        JFrame frame = new MediaFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class MediaFrame extends JFrame implements ActionListener, ListSelectionListener {
    private JList list;
    private DefaultListModel m;
    private JButton btn;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JButton btn4;
    private JFileChooser chooser;
    private JTextField textField;
    Map<Integer, String> hashtable = new Hashtable<Integer, String>();
    private int i = 0;
    int s = 0;

    public MediaFrame() {
        setTitle("Media");
        setSize(600, 500);

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        JLabel label = new JLabel("查询的歌曲名:");
        textField = new JTextField();
        menu.add(label);
        menu.add(textField);

        JToolBar TB = new JToolBar();

        m = new DefaultListModel();

        list = new JList(m);
        list.setFixedCellWidth(100);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);

        JScrollPane pane = new JScrollPane(list);

        chooser = new JFileChooser();

        btn = new JButton("add song");
        btn.addActionListener(this);

        btn1 = new JButton("delete song");
        btn1.addActionListener(this);

        btn2 = new JButton("clear list");
        btn2.addActionListener(this);

        btn3 = new JButton("find song");
        btn3.addActionListener(this);

        btn4 = new JButton("sort");
        btn4.addActionListener(this);

        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(5, 1));

        panel.add(btn);
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);

        TB.setLayout(new GridLayout(1, 2));

        TB.add(pane);
        TB.add(panel);

        add(TB, BorderLayout.WEST);
    }

    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == btn) {
            i++;
            chooser.setCurrentDirectory(new File("."));

            int result = chooser.showOpenDialog(MediaFrame.this);

            if (result == JFileChooser.APPROVE_OPTION) {
                System.out.println(i);

                String name = chooser.getSelectedFile().getPath();

                String str1 = name;

                int str2 = name.lastIndexOf("//");

                String name1 = name.substring(str2 + 1, str1.length());

                //截取最后一个"/"之前的所有字符串
                int str3 = name1.lastIndexOf(".");
                String name2 = name1.substring(0, str3);

                //截取"."后面所有字符串后缀
                hashtable.put(i, name2);
                m.add(0, hashtable.get(i));
                System.out.println(hashtable);
            }
        }

        if (event.getSource() == btn1) {
            m.removeElement(list.getSelectedValue());
            System.out.println(m);
        }

        if (event.getSource() == btn2) {
            System.out.println(m);
            i = 0;
            hashtable.clear();
            m.clear();
        }

        if (event.getSource() == btn3) {
            int[] a = new int[m.getSize()];

            try {
                int j;
                String name = textField.getText();

                System.out.println(m.getSize());

                for (j = 1; j <= m.getSize(); j++) {
                    Pattern p = Pattern.compile("^" + name + "+");//正则表达式选取以你填的单词为首的所有查询结果
                    Matcher match = p.matcher((String) hashtable.get(j));

                    if (match.find()) {
                        s++;
                        //记录索引结点到数组中a[]中
                        a[s] = a[s] + m.getSize() - j;
                        System.out.println(hashtable.get(j));
                        System.out.println(a[s]);
                    }
                }
                //可以选择不多个选项(因为前面设置了JList可以多项选择)
                list.setSelectedIndices(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (event.getSource() == btn4) {
            //TODO
            //int j;
            //for (j = 0;j<m.length();j++)
            //{
            //if (hashtable.containsValue(Integer.parseInt(j)+"*")
            //hashtable.put(j,
            //}

        }
    }

    public void valueChanged(ListSelectionEvent event) {
        System.out.println(list.getSelectedIndex());
    }

//    // 模糊查询方法
//    public List<person> query() {
//        List<person> list = new ArrayList<>();
//        Connection con = null;
//        Scanner sc = new Scanner(System.in);
//        System.err.println("enter name:");
//        String name = sc.nextLine();
//        System.err.println("enter id:");
//        String id = sc.nextLine();
//        System.err.println("enter tel:");
//        String tel = sc.nextLine();
//        System.err.println("enter sex:");
//        String sex = sc.nextLine();
//        String sql = "select id,name,tel,sex from students "
//                // 技巧在此，合理拼接字符串
//                + "where 1=1";
//        List<Object> list1 = new ArrayList<Object>();
//        //使用 commons-lang包
//        if (StringUtils.isNotEmpty(name)) {
//            sql += " and title like ?";
//            list1.add("%" + name + "%");
//        }
//
//        if (!StringUtils.isEmpty(id)) {
//            sql += " and content like ?";
//            list1.add("%" + id + "%");
//        }
//
//        if (!StringUtils.isEmpty(tel)) {
//            sql += " and addr like ?";
//            list1.add("%" + tel + "%");
//        }
//        try {
//            con = DSUtlis.getConnection();
//            // SQL语句组成完成以后，就生成pst对象
//            PreparedStatement pst = con.prepareStatement(sql);
//            // 设置?的值
//            for (int i = 0; i < list1.size(); i++) {
//                pst.setObject(i + 1, list.get(i));
//            }
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                person p = new person();
//                p.setId(rs.getString("id"));
//                p.setName(rs.getString("name"));
//                p.setTel(rs.getString("tel"));
//                p.setSex(rs.getString("sex").equals("1") ? "男" : "女");
//                list.add(p);
//            }
//            rs.close();
//            pst.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }
}
