package com.johnny.ui;

import com.johnny.common.Common;
import com.johnny.common.Console;
import com.johnny.service.creator.HtmlCreator;
import com.johnny.service.download.DownloadManager;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map.Entry;

public class FailFileFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static FailFileFrame instance = null;
    private static List<String> finishedAlbumPathList = null;
    private JLabel countLabel;
    private JTextArea failFileInfoTextArea;

    private FailFileFrame() {
        initComponents();
        init();
        setTitle("下载失败文件列表");
        setLocationRelativeTo(MainFrame.getInstance());
    }

    private static FailFileFrame getInstance() {
        if (instance == null) {
            instance = new FailFileFrame();
        }
        return instance;
    }

    public static FailFileFrame getInstance(List<String> finishedAlbumPathList) {
        return getInstance();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new FailFileFrame().setVisible(true));
    }

    private void init() {
        this.failFileInfoTextArea.setText("");
        for (Entry image : Common.failFileMap.entrySet()) {
            this.failFileInfoTextArea.append("下载失败：" + (String) image.getKey() + " ——> " +
                    image.getValue() + "\r\n");
        }

        this.countLabel.setText(String.valueOf(Common.failFileMap.size()));
    }

    private void initComponents() {
        JButton reloadBtn = new JButton();
        JButton cancelButton = new JButton();
        JLabel cLabel = new JLabel();
        JScrollPane jScrollPane1 = new JScrollPane();
        this.failFileInfoTextArea = new JTextArea();
        this.countLabel = new JLabel();

        setDefaultCloseOperation(3);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                FailFileFrame.this.formWindowClosing(evt);
            }
        });
        reloadBtn.setText("重新下载");
        reloadBtn.addActionListener(FailFileFrame.this::reloadBtnActionPerformed);
        cancelButton.setText("取消");
        cancelButton.addActionListener(FailFileFrame.this::cancelButtonActionPerformed);
        cLabel.setText("文件个数：");

        this.failFileInfoTextArea.setColumns(20);
        this.failFileInfoTextArea.setRows(5);
        jScrollPane1.setViewportView(this.failFileInfoTextArea);

        this.countLabel.setText("0");

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
                                                Alignment.LEADING)
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        reloadBtn,
                                                                        -2,
                                                                        183,
                                                                        -2)
                                                                .addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(
                                                                        cancelButton,
                                                                        -1,
                                                                        181,
                                                                        32767))
                                                .addGroup(
                                                        layout.createSequentialGroup()
                                                                .addComponent(
                                                                        cLabel)
                                                                .addPreferredGap(
                                                                        LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(
                                                                        this.countLabel))
                                                .addComponent(
                                                        jScrollPane1,
                                                        -1,
                                                        376, 32767))
                                .addContainerGap()));
        layout.setVerticalGroup(layout
                .createParallelGroup(Alignment.LEADING)
                .addGroup(
                        Alignment.TRAILING,
                        layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(cLabel)
                                                .addComponent(this.countLabel))
                                .addPreferredGap(
                                        LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1,
                                        -1,
                                        197, 32767)
                                .addGap(19, 19, 19)
                                .addGroup(
                                        layout.createParallelGroup(
                                                Alignment.BASELINE)
                                                .addComponent(reloadBtn)
                                                .addComponent(cancelButton))
                                .addGap(23, 23, 23)));

        pack();
    }

    private void formWindowClosing(WindowEvent evt) {
        System.out.println("FailFileFrame.formWindowClosing()");
        closeWindow();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        closeWindow();
    }

    private void closeWindow() {
        Common.failFileMap.clear();
        setVisible(false);

        createAlbumHTML();
    }

    private void createAlbumHTML() {
        if ((finishedAlbumPathList != null) && (finishedAlbumPathList.size() != 0)) {
            Console.print("【正在生成HTML文档,请稍等...】");
            HtmlCreator.createAlbumHTML(finishedAlbumPathList);
            finishedAlbumPathList = null;
            Console.print("【FINISH】");

            MainFrame.getInstance().downloadBtn.setEnabled(true);
        }
    }

    private void reloadBtnActionPerformed(ActionEvent evt) {
        new Thread(() -> {
            FailFileFrame.getInstance().setVisible(false);
            int flag = DownloadManager.downloadFailFile();
            if (flag == 0) {
                FailFileFrame.getInstance().init();
                FailFileFrame.getInstance().setVisible(true);
            } else {
                FailFileFrame.this.createAlbumHTML();

                MainFrame.getInstance().downloadBtn.setEnabled(true);
            }
        }).start();
    }
}