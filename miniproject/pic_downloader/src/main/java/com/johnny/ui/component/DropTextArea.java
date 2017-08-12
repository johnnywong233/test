package com.johnny.ui.component;

import com.johnny.ui.MainFrame;

import javax.swing.JOptionPane;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DropTextArea extends JPopupTextArea
        implements DropTargetListener {
    private static final long serialVersionUID = 1L;

    public DropTextArea() {
        new DropTarget(this, 3, this);
    }

    public void dragEnter(DropTargetDragEvent dtde) {
    }

    public void dragOver(DropTargetDragEvent dtde) {
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    @SuppressWarnings("unchecked")
    public void drop(DropTargetDropEvent dtde) {
        try {
            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                setText("");
                dtde.acceptDrop(3);

                List<File> list = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                for (File file : list) {
                    if (file.isDirectory()) {
                        File dir = new File(file.getAbsolutePath());
                        if (dir.isDirectory()) {
                            File doc = new File(dir.getAbsolutePath() + "/描述.txt");
                            if (doc.exists())
                                try {
                                    BufferedReader br = new BufferedReader(new FileReader(doc));
                                    String str = br.readLine();
                                    br.close();
                                    if ((str == null) || (str.length() == 0) ||
                                            (str.split(" ").length < 2))
                                        continue;
                                    setText(str.split(" ")[1]);
                                    com.johnny.common.Common.PATH_DOWNLOAD = dir.getParent();
                                    com.johnny.common.Common.IS_UPDATE = true;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            else {
                                JOptionPane.showMessageDialog(MainFrame.getInstance(), "所需要的描述文档不存在，无法更新，T^T");
                            }
                        }
                    }
                }
                dtde.dropComplete(true);
                updateUI();
            } else {
                dtde.acceptDrop(3);
                String str = (String) dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);
                append(str + "\n");
                dtde.dropComplete(true);
                updateUI();
            }
        } catch (IOException | UnsupportedFlavorException ioe) {
            ioe.printStackTrace();
        }
    }
}