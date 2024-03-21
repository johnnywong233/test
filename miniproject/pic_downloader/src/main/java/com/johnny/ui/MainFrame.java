package com.johnny.ui;

import com.johnny.common.Common;
import com.johnny.common.Console;
import com.johnny.service.DownloadService;
import com.johnny.service.creator.HtmlCreator;
import com.johnny.ui.component.DropTextArea;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static MainFrame instance = null;
    public JLabel albumListCountLabel;
    public JProgressBar albumListProgressBar;
    public JButton downloadBtn;
    public JLabel processUnitCountLabel;
    public JProgressBar processUnitProgressBar;
    public JProgressBar progressBar;
    private JTextArea albumTextArea;
    private JTextArea infoTextArea;
    private JCheckBox isDownloadRawCheckBox;
    private JTextField pathTextField;

    private MainFrame() {
        initComponents();
        setBounds(350, 100, getWidth(), getHeight());
        String ext = "";

        setTitle("豆瓣相册下载" + Common.VERSION + ext);
        Console.setArea(this.infoTextArea);

        this.progressBar.setOrientation(0);
        this.progressBar.setMinimum(0);
        this.progressBar.setStringPainted(true);
        this.progressBar.setPreferredSize(new Dimension(300, 20));
        this.progressBar.setBorderPainted(true);

        this.albumListProgressBar.setOrientation(0);
        this.albumListProgressBar.setMinimum(0);
        this.albumListProgressBar.setStringPainted(true);
        this.albumListProgressBar.setPreferredSize(new Dimension(300, 20));
        this.albumListProgressBar.setBorderPainted(true);

        this.processUnitProgressBar.setOrientation(0);
        this.processUnitProgressBar.setMinimum(0);
        this.processUnitProgressBar.setStringPainted(true);
        this.processUnitProgressBar.setPreferredSize(new Dimension(300, 20));
        this.processUnitProgressBar.setBorderPainted(true);

        this.pathTextField.setText(Common.PATH_DOWNLOAD);
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LookAndFeelInfo[] arrayOfLookAndFeelInfo;
                int j = (arrayOfLookAndFeelInfo = UIManager.getInstalledLookAndFeels()).length;
                for (int i = 0; i < j; i++) {
                    LookAndFeelInfo info = arrayOfLookAndFeelInfo[i];
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "未找到新皮肤，请升级JDK到6.0 update 10");
            }
            MainFrame.getInstance().setVisible(true);
        });
       /* if (VersionChecker.haveNewVersion()) {
            int result = JOptionPane.showConfirmDialog(getInstance(),
                    "有新版本，是否立即查看？", "版本提示", 0,
                    3);
            if (result == 0)
                Common.openURLWithBrowse("http://www.douban.com/note/206320326/",
                        getInstance());
        }*/
    }

    private void initComponents() {
        JScrollPane infoScrollPane = new JScrollPane();
        this.infoTextArea = new JTextArea();
        this.downloadBtn = new JButton();
        JTabbedPane tabbedPane = new JTabbedPane();
        JScrollPane albumScrollPane = new JScrollPane();
        this.albumTextArea = new DropTextArea();
        JPanel pathPanel = new JPanel();
        this.pathTextField = new JTextField();
        JButton changePathBtn = new JButton();
        JLabel pathLabel = new JLabel();
        JLabel isDownloadRawLabel = new JLabel();
        this.isDownloadRawCheckBox = new JCheckBox();
        this.progressBar = new JProgressBar();
        JButton jButton1 = new JButton();
        this.albumListProgressBar = new JProgressBar();
        this.albumListCountLabel = new JLabel();
        this.processUnitProgressBar = new JProgressBar();
        this.processUnitCountLabel = new JLabel();
        JMenuBar jMenuBar1 = new JMenuBar();
        JMenu jMenu1 = new JMenu();
        JMenuItem jMenuItem1 = new JMenuItem();
        JMenuItem jMenuItem4 = new JMenuItem();
        JSeparator jSeparator1 = new JSeparator();
        JMenuItem jMenuItem2 = new JMenuItem();
        JMenu jMenu4 = new JMenu();
        JMenuItem jMenuItem5 = new JMenuItem();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.infoTextArea.setColumns(20);
        this.infoTextArea.setRows(5);
        infoScrollPane.setViewportView(this.infoTextArea);

        this.downloadBtn.setText("下载");
        this.downloadBtn.addActionListener(MainFrame.this::downloadBtnActionPerformed);
        this.albumTextArea.setColumns(20);
        this.albumTextArea.setRows(5);
        this.albumTextArea
                .setToolTipText("按照相册地址下载所有图片~");
        albumScrollPane.setViewportView(this.albumTextArea);

        tabbedPane.addTab("相册地址", albumScrollPane);

        changePathBtn.setText("修改");
        changePathBtn.addActionListener(MainFrame.this::changePathBtnActionPerformed);
        pathLabel.setText("保存路径：");

        isDownloadRawLabel.setText("保存大图：");

        this.isDownloadRawCheckBox.setSelected(true);

        GroupLayout pathPanelLayout = new GroupLayout(
                pathPanel);
        pathPanel.setLayout(pathPanelLayout);
        pathPanelLayout
                .setHorizontalGroup(pathPanelLayout
                        .createParallelGroup(
                                Alignment.LEADING)
                        .addGroup(
                                pathPanelLayout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                pathPanelLayout
                                                        .createParallelGroup(
                                                                Alignment.LEADING)
                                                        .addGroup(
                                                                Alignment.TRAILING,
                                                                pathPanelLayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                pathLabel)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                this.pathTextField,
                                                                                -1,
                                                                                188,
                                                                                32767)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                changePathBtn))
                                                        .addGroup(
                                                                pathPanelLayout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                isDownloadRawLabel)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                this.isDownloadRawCheckBox)))
                                        .addContainerGap()));
        pathPanelLayout
                .setVerticalGroup(pathPanelLayout
                        .createParallelGroup(
                                Alignment.LEADING)
                        .addGroup(
                                pathPanelLayout
                                        .createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addGroup(
                                                pathPanelLayout
                                                        .createParallelGroup(
                                                                Alignment.BASELINE)
                                                        .addComponent(
                                                                changePathBtn)
                                                        .addComponent(pathLabel)
                                                        .addComponent(
                                                                this.pathTextField,
                                                                -2,
                                                                -1,
                                                                -2))
                                        .addPreferredGap(
                                                LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                                pathPanelLayout
                                                        .createParallelGroup(
                                                                Alignment.BASELINE)
                                                        .addComponent(
                                                                isDownloadRawLabel)
                                                        .addComponent(
                                                                this.isDownloadRawCheckBox))
                                        .addContainerGap(17, 32767)));

        tabbedPane.addTab("设置", pathPanel);

        jButton1.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/com.johnny/resources/images/ui/icon-dir.gif"))));
        jButton1.addActionListener(MainFrame.this::jButton1ActionPerformed);
        this.albumListCountLabel.setText("[0/0] ");

        this.processUnitCountLabel.setText("[0/0] ");

        jMenu1.setText("程序");

        jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(
                79,
                2));
        jMenuItem1.setText("打开存储目录");
        jMenuItem1.addActionListener(MainFrame.this::jMenuItem1ActionPerformed);
        jMenu1.add(jMenuItem1);

        jMenuItem4.setAccelerator(KeyStroke.getKeyStroke(
                72,
                2));
        jMenuItem4.setText("手动生成页面");
        jMenuItem4.addActionListener(MainFrame.this::jMenuItem4ActionPerformed);
        jMenu1.add(jMenuItem4);
        jMenu1.add(jSeparator1);

        jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(
                69,
                2));
        jMenuItem2.setText("关闭程序");
        jMenuItem2.addActionListener(MainFrame.this::jMenuItem2ActionPerformed);
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("帮助帖");

        jMenuItem5.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/com.johnny/resources/images/ui/face.jpg"))));
        jMenuItem5.setText("猛击这里~");
        jMenuItem5.addActionListener(MainFrame.this::jMenuItem5ActionPerformed);
        jMenu4.add(jMenuItem5);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(
                getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.TRAILING)
                                                .addComponent(
                                                        infoScrollPane,
                                                        Alignment.LEADING,
                                                        -1,
                                                        498, 32767)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        tabbedPane,
                                                                        -1,
                                                                        346,
                                                                        32767)
                                                                .addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        this.downloadBtn,
                                                                        -2,
                                                                        145,
                                                                        -2))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.TRAILING)
                                                                                .addComponent(
                                                                                        this.processUnitProgressBar,
                                                                                        -1,
                                                                                        447,
                                                                                        32767)
                                                                                .addComponent(
                                                                                        this.albumListProgressBar,
                                                                                        -1,
                                                                                        447,
                                                                                        32767)
                                                                                .addComponent(
                                                                                        this.progressBar,
                                                                                        -1,
                                                                                        447,
                                                                                        32767))
                                                                .addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(
                                                                        layout.createParallelGroup(
                                                                                Alignment.TRAILING)
                                                                                .addComponent(
                                                                                        jButton1,
                                                                                        -2,
                                                                                        44,
                                                                                        -2)
                                                                                .addComponent(
                                                                                        this.albumListCountLabel)
                                                                                .addComponent(
                                                                                        this.processUnitCountLabel))))
                                .addContainerGap()));
        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING,
                                                false)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(
                                                                        tabbedPane,
                                                                        -1,
                                                                        124,
                                                                        32767))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addGap(35, 35,
                                                                        35)
                                                                .addComponent(
                                                                        this.downloadBtn,
                                                                        -1,
                                                                        102,
                                                                        32767)))
                                .addPreferredGap(
                                        LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(infoScrollPane,
                                        -1,
                                        184, 32767)
                                .addPreferredGap(
                                        LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.LEADING,
                                                false)
                                                .addComponent(
                                                        this.progressBar,
                                                        -1,
                                                        -1,
                                                        32767)
                                                .addComponent(
                                                        jButton1,
                                                        -1,
                                                        -1,
                                                        32767))
                                .addPreferredGap(
                                        LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.TRAILING,
                                                false)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        this.processUnitProgressBar,
                                                                        -2,
                                                                        -1,
                                                                        -2)
                                                                .addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        this.albumListProgressBar,
                                                                        -2,
                                                                        -1,
                                                                        -2))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        this.processUnitCountLabel)
                                                                .addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.RELATED,
                                                                        -1,
                                                                        32767)
                                                                .addComponent(
                                                                        this.albumListCountLabel)))
                                .addContainerGap()));

        pack();
    }

    private void jMenuItem5ActionPerformed(ActionEvent evt) {
        Common.openURLWithBrowse("https://www.douban.com/note/206320326/", this);
    }

    private void jMenuItem4ActionPerformed(ActionEvent evt) {
        new Thread(() -> {
            JFileChooser chooser = new JFileChooser(Common.PATH_APP);
            chooser.setDialogTitle("请选择要生成文件的目录");
            chooser.setMultiSelectionEnabled(true);
            chooser.setFileSelectionMode(1);
            chooser.showOpenDialog(MainFrame.getInstance());
            File[] dirs = chooser.getSelectedFiles();
            Console.print("开始生成，请骚等~");
            if (dirs.length != 0) {
                try {
                    for (File dir : dirs) {
                        HtmlCreator.createAlbumHTML(dir.getAbsolutePath());
                        Console.print("[Finish]" + dir.getAbsolutePath());
                    }
                    Console.print("生成完毕");
                } catch (IOException e) {
                    Console.print("生成错误");
                    Console.print(e.getMessage());
                }
            }
        }).start();
    }

    private void jMenuItem1ActionPerformed(ActionEvent evt) {
        openDir();
    }

    private void jMenuItem2ActionPerformed(ActionEvent evt) {
        System.exit(0);
    }

    private void changePathBtnActionPerformed(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser(Common.PATH_APP);
        chooser.setFileSelectionMode(1);
        chooser.showSaveDialog(this);
        File dir = chooser.getSelectedFile();
        if (dir != null) {
            Common.PATH_DOWNLOAD = dir.getAbsolutePath();
            this.pathTextField.setText(Common.PATH_DOWNLOAD);
        }
    }

    private void openDir() {
        Desktop desktop = Desktop.getDesktop();
        try {
            File dir = new File(Common.PATH_DOWNLOAD);
            if (dir.exists()) {
                desktop.open(dir);
            } else {
                JOptionPane.showMessageDialog(this, "暂无图片");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        openDir();
    }

    private void downloadBtnActionPerformed(ActionEvent evt) {
        new Thread(MainFrame.this::download).start();
    }

    private void download() {
        this.downloadBtn.setEnabled(false);

        if (Common.IS_UPDATE) {
            Common.IS_UPDATE = false;
        } else {
            Common.PATH_DOWNLOAD = this.pathTextField.getText();
        }
        File file = new File(Common.PATH_DOWNLOAD);
        if (!file.exists()) {
            file.mkdirs();
        }

        String[] urls = this.albumTextArea.getText().split("[\\t \\n]+");
        List<String> urlList = new ArrayList<>();
        boolean flag = true;
        for (String url : urls) {
            if ((!url.startsWith("https://")) && (!url.startsWith("https://"))) {
                JOptionPane.showMessageDialog(getInstance(),
                        "地址格式错误，请检查后重新输入");
                this.downloadBtn.setEnabled(true);
                flag = false;
                break;
            }

            if ((url.contains("douban.com")) &&
                    (url.contains("?start="))) {
                url = url.substring(0, url.indexOf("?start="));
            }

            if ((url.contains("?")) || ("/".equals(url.substring(url.length() - 1)))) {
                urlList.add(url);
            } else {
                urlList.add(url + "/");
            }
            System.out.println(url);
        }

        if (flag) {
            Common.IS_DOWNLOAD_RAW = this.isDownloadRawCheckBox.isSelected();

            DownloadService.download(urlList);
        }
    }
}