package io.file;

import java.awt.Button;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextFileViewer extends Frame implements ActionListener {
    private static final long serialVersionUID = -3542022951396686424L;

    String directory;

    TextArea textarea;

    public TextFileViewer() {
        this(null, null);
    }

    public TextFileViewer(String filename) {
        this(null, filename);
    }

    public TextFileViewer(String directory, String filename) {
        super();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        textarea = new TextArea("", 24, 80);
        textarea.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
        textarea.setEditable(false);
        this.add("Center", textarea);
        Panel myPanel = new Panel();
        myPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        this.add(myPanel, "South");
        Font font = new Font("SansSerif", Font.BOLD, 14);
        Button btnOpenFile = new Button("open");
        Button btnClose = new Button("quit");
        btnOpenFile.addActionListener(this);
        btnOpenFile.setActionCommand("open");
        btnOpenFile.setFont(font);
        btnClose.addActionListener(this);
        btnClose.setActionCommand("close");
        btnClose.setFont(font);
        myPanel.add(btnOpenFile);
        myPanel.add(btnClose);
        this.pack();
        if (directory == null) {
            File f;
            if ((filename != null) && (f = new File(filename)).isAbsolute()) {
                directory = f.getParent();
                filename = f.getName();
            } else
                directory = System.getProperty("user.dir");
        }
        this.directory = directory;
        setFile(directory, filename);
    }

    public void setFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File f;
        FileReader in = null;
        try {
            f = new File(directory, filename);
            in = new FileReader(f);
            char[] buffer = new char[4096];
            int len;
            textarea.setText("");
            while ((len = in.read(buffer)) != -1) {
                String s = new String(buffer, 0, len);
                textarea.append(s);
            }
            this.setTitle("TextFileViewer: " + filename);
            textarea.setCaretPosition(0);
        } catch (IOException e) {
            textarea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("TextFileViewer: " + filename + ": I/O Exception");
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("open")) {
            FileDialog myFileDialog = new FileDialog(this, "Open File", FileDialog.LOAD);
            myFileDialog.setDirectory(directory);
            myFileDialog.show();
            directory = myFileDialog.getDirectory();
            setFile(directory, myFileDialog.getFile());
            myFileDialog.dispose();
        } else if (cmd.equals("close"))
            this.dispose();
    }

    @SuppressWarnings("deprecation")
    static public void main(String[] args) throws IOException {
        Frame myFrame = new TextFileViewer((args.length == 1) ? args[0] : null);
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        myFrame.show();
    }
}
