package com.johnny.ui.component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class JPopupTextArea extends JTextArea
        implements MouseListener, ActionListener {
    private static final long serialVersionUID = -406608462064697359L;
    private final JPopupMenu popupMenu;
    private final JMenuItem cutMenu;
    private final JMenuItem copyMenu;
    private final JMenuItem pasteMenu;
    private final JMenuItem selectAllMenu;
    private final JMenuItem formatMenu;

    JPopupTextArea() {
        this.popupMenu = new JPopupMenu();
        this.cutMenu = new JMenuItem("剪切");
        this.copyMenu = new JMenuItem("复制");
        this.pasteMenu = new JMenuItem("粘贴");
        this.selectAllMenu = new JMenuItem("全选");
        this.formatMenu = new JMenuItem("格式化");

        this.cutMenu.setAccelerator(KeyStroke.getKeyStroke(88, 2));
        this.copyMenu.setAccelerator(KeyStroke.getKeyStroke(67, 2));
        this.pasteMenu.setAccelerator(KeyStroke.getKeyStroke(86, 2));
        this.selectAllMenu.setAccelerator(KeyStroke.getKeyStroke(65, 2));
        this.formatMenu.setAccelerator(KeyStroke.getKeyStroke(70, 2));

        this.cutMenu.addActionListener(this);
        this.copyMenu.addActionListener(this);
        this.pasteMenu.addActionListener(this);
        this.selectAllMenu.addActionListener(this);
        this.formatMenu.addActionListener(this);

        this.popupMenu.add(this.cutMenu);
        this.popupMenu.add(this.copyMenu);
        this.popupMenu.add(this.pasteMenu);
        this.popupMenu.add(new JSeparator());
        this.popupMenu.add(this.selectAllMenu);
        this.popupMenu.add(new JSeparator());
        this.popupMenu.add(this.formatMenu);

        add(this.popupMenu);
        addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.copyMenu) {
            copy();
        }
        if (e.getSource() == this.pasteMenu) {
            paste();
        }
        if (e.getSource() == this.cutMenu) {
            cut();
        }
        if (e.getSource() == this.selectAllMenu) {
            selectAll();
        }
        if (e.getSource() == this.formatMenu) {
            setText(getText().replaceAll("[^\r\n]http://", "/\r\nhttps://").trim());
            setText(getText().replaceAll("[^\r\n]https://", "/\r\nhttps://").trim());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        popupMenuTrigger(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        popupMenuTrigger(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void popupMenuTrigger(MouseEvent e) {
        if (e.isPopupTrigger()) {
            requestFocusInWindow();
            this.cutMenu.setEnabled(isAbleToCopyAndCut());
            this.copyMenu.setEnabled(isAbleToCopyAndCut());
            this.pasteMenu.setEnabled(isAbleToPaste());
            this.selectAllMenu.setEnabled(isAbleToSelectAll());
            this.popupMenu.show(this, e.getX() + 3, e.getY() + 3);
        }
    }

    private boolean isAbleToSelectAll() {
        return (!"".equalsIgnoreCase(getText())) && (getText() != null);
    }

    private boolean isAbleToCopyAndCut() {
        return getSelectionStart() != getSelectionEnd();
    }

    private boolean isAbleToPaste() {
        Transferable content = getToolkit().getSystemClipboard().getContents(this);
        try {
            return content.getTransferData(DataFlavor.stringFlavor) instanceof String;
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}