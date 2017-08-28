package awt;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileFilter;

/**
 * Created by wajian on 2016/8/18.
 */
public class RenameFiles extends JFrame {
    private static final long serialVersionUID = 4534371106024773867L;

    private final class ExtNameFileFilter implements FileFilter {
        private String extName;

        ExtNameFileFilter(String extName) {
            this.extName = extName;//save file extension name
        }

        @Override
        public boolean accept(File pathname) {
            //filter file extension name
            return pathname.getName().toUpperCase().endsWith(extName.toUpperCase());
        }
    }

    private JTextField forderField;
    private JTextField templetField;
    private File dir;
    private JTable table;
    private JTextField extNameField;
    private JSpinner startSpinner;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RenameFiles frame = new RenameFiles();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    private RenameFiles() {
        setResizable(false);
        setTitle("file batch rename");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 383, 409);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{72, 54, 60, 87, 91, 0};
        gbl_contentPane.rowHeights = new int[]{25, 25, 10, 25, 24, 25, 2,
                216, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        JLabel label = new JLabel();
        label.setText("file batch rename module:");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = GridBagConstraints.VERTICAL;
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.gridwidth = 3;
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        contentPane.add(label, gbc_label);

        JLabel label_1 = new JLabel();
        label_1.setText("file path:");
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.anchor = GridBagConstraints.EAST;
        gbc_label_1.fill = GridBagConstraints.VERTICAL;
        gbc_label_1.insets = new Insets(0, 0, 5, 5);
        gbc_label_1.gridx = 0;
        gbc_label_1.gridy = 1;
        contentPane.add(label_1, gbc_label_1);

        forderField = new JTextField();
        forderField.setText("");
        GridBagConstraints gbc_forderField = new GridBagConstraints();
        gbc_forderField.fill = GridBagConstraints.HORIZONTAL;
        gbc_forderField.insets = new Insets(0, 0, 5, 5);
        gbc_forderField.gridwidth = 3;
        gbc_forderField.gridx = 1;
        gbc_forderField.gridy = 1;
        contentPane.add(forderField, gbc_forderField);

        JButton button = new JButton();
        button.addActionListener(e -> do_button_actionPerformed(e));
        button.setText("browser");
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.anchor = GridBagConstraints.NORTHWEST;
        gbc_button.insets = new Insets(0, 0, 5, 0);
        gbc_button.gridx = 4;
        gbc_button.gridy = 1;
        contentPane.add(button, gbc_button);

        JSeparator separator_1 = new JSeparator();
        GridBagConstraints gbc_separator_1 = new GridBagConstraints();
        gbc_separator_1.fill = GridBagConstraints.BOTH;
        gbc_separator_1.insets = new Insets(0, 0, 5, 0);
        gbc_separator_1.gridwidth = 5;
        gbc_separator_1.gridx = 0;
        gbc_separator_1.gridy = 2;
        contentPane.add(separator_1, gbc_separator_1);

        JLabel label_5 = new JLabel();
        label_5.setText("使用#可以指定数字计数所占的位置，使用*可以插入原文件名：");
        GridBagConstraints gbc_label_5 = new GridBagConstraints();
        gbc_label_5.fill = GridBagConstraints.VERTICAL;
        gbc_label_5.insets = new Insets(0, 0, 5, 0);
        gbc_label_5.gridwidth = 5;
        gbc_label_5.gridx = 0;
        gbc_label_5.gridy = 3;
        contentPane.add(label_5, gbc_label_5);

        JLabel label_3 = new JLabel();
        label_3.setText("template:");
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.anchor = GridBagConstraints.EAST;
        gbc_label_3.fill = GridBagConstraints.VERTICAL;
        gbc_label_3.insets = new Insets(0, 0, 5, 5);
        gbc_label_3.gridx = 0;
        gbc_label_3.gridy = 4;
        contentPane.add(label_3, gbc_label_3);

        templetField = new JTextField();
        templetField.setText("catrestaurant###");
        GridBagConstraints gbc_templetField = new GridBagConstraints();
        gbc_templetField.anchor = GridBagConstraints.SOUTH;
        gbc_templetField.fill = GridBagConstraints.HORIZONTAL;
        gbc_templetField.insets = new Insets(0, 0, 5, 5);
        gbc_templetField.gridwidth = 3;
        gbc_templetField.gridx = 1;
        gbc_templetField.gridy = 4;
        contentPane.add(templetField, gbc_templetField);

        JLabel label_4 = new JLabel();
        label_4.setText("start when:");
        GridBagConstraints gbc_label_4 = new GridBagConstraints();
        gbc_label_4.fill = GridBagConstraints.VERTICAL;
        gbc_label_4.insets = new Insets(0, 0, 5, 5);
        gbc_label_4.gridx = 0;
        gbc_label_4.gridy = 5;
        contentPane.add(label_4, gbc_label_4);

        startSpinner = new JSpinner();
        GridBagConstraints gbc_startSpinner = new GridBagConstraints();
        gbc_startSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_startSpinner.insets = new Insets(0, 0, 5, 5);
        gbc_startSpinner.gridx = 1;
        gbc_startSpinner.gridy = 5;
        contentPane.add(startSpinner, gbc_startSpinner);

        JLabel label_2 = new JLabel();
        label_2.setText("extension name:");
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_label_2.insets = new Insets(0, 0, 5, 5);
        gbc_label_2.gridx = 2;
        gbc_label_2.gridy = 5;
        contentPane.add(label_2, gbc_label_2);

        JButton startButton = new JButton();
        startButton.addActionListener(e -> do_startButton_actionPerformed(e));

        extNameField = new JTextField();
        extNameField.setText("jpg");
        GridBagConstraints gbc_extNameField = new GridBagConstraints();
        gbc_extNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_extNameField.insets = new Insets(0, 0, 5, 5);
        gbc_extNameField.gridx = 3;
        gbc_extNameField.gridy = 5;
        contentPane.add(extNameField, gbc_extNameField);
        startButton.setText("start");
        GridBagConstraints gbc_startButton = new GridBagConstraints();
        gbc_startButton.anchor = GridBagConstraints.NORTH;
        gbc_startButton.insets = new Insets(0, 0, 5, 0);
        gbc_startButton.gridx = 4;
        gbc_startButton.gridy = 5;
        contentPane.add(startButton, gbc_startButton);

        JSeparator separator_2 = new JSeparator();
        GridBagConstraints gbc_separator_2 = new GridBagConstraints();
        gbc_separator_2.anchor = GridBagConstraints.NORTH;
        gbc_separator_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator_2.insets = new Insets(0, 0, 5, 0);
        gbc_separator_2.gridwidth = 5;
        gbc_separator_2.gridx = 0;
        gbc_separator_2.gridy = 6;
        contentPane.add(separator_2, gbc_separator_2);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridwidth = 5;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 7;
        contentPane.add(scrollPane, gbc_scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{
                "old filename", "new filename"}));
        scrollPane.setViewportView(table);
    }

    /**
     * 浏览按钮的事件处理方法
     */
    private void do_button_actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();// 创建文件选择器
        // 设置只选择文件夹
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = chooser.showOpenDialog(this);// 显示打开对话框
        if (option == JFileChooser.APPROVE_OPTION) {
            dir = chooser.getSelectedFile();// 获取选择的文件夹
        } else {
            dir = null;
        }
        forderField.setText(dir + "");// 显示文件夹信息
    }

    /**
     * 开始按钮的事件处理方法
     */
    private void do_startButton_actionPerformed(ActionEvent e) {
        String templet = templetField.getText();// 获取模板字符串
        if (templet.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请确定重命名模板", "信息对话框",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // 获取表格数据模型
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);// 清除表格数据
        int bi = (Integer) startSpinner.getValue();// 获取起始编号
        int index = templet.indexOf("#");// 获取第一个“#”的索引
        String code = templet.substring(index);// 获取模板中数字占位字符串
        // 把模板中数字占位字符串替换为指定格式
        templet = templet.replace(code, "%0" + code.length() + "d");
        String extName = extNameField.getText().toLowerCase();
        if (!extName.contains("."))
            extName = "." + extName;
        // 获取文件中文件列表数组
        File[] files = dir.listFiles(new ExtNameFileFilter(extName));
        for (File file : files) {// 变量文件数组
            // 格式化每个文件名称
            String name = String.format(templet, bi++) + extName;
            // 把文件的旧名称与新名称添加到表格的数据模型
            model.addRow(new String[]{file.getName(), name});
            File parentFile = file.getParentFile();// 获取文件所在文件夹对象
            File newFile = new File(parentFile, name);
            file.renameTo(newFile);// 文件重命名
        }
    }
}
