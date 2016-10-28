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

/**
* ����������һ������TextArea����Ĵ���,
* �ڸ���������ʾ�򿪵��ı��ļ�������.
**/


public class TextFileViewer extends Frame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3542022951396686424L;

	String directory; // ��FileDialog����ʾ��Ĭ��Ŀ¼

    TextArea textarea; // ��ʾ�ı��ļ�������

    // ���췽���� ��һ���ļ������
    public TextFileViewer() {
        this(null, null);
    }

    // ���췽���� ��ʾ��ǰĿ¼�µ��ļ�
    public TextFileViewer(String filename) {
        this(null, filename);
    }

    // ���췽���� ����������ʾָ��Ŀ¼�е�ָ���ļ����ݵ�TextFileViewer����
    public TextFileViewer(String directory, String filename) {
        super(); // �������
        // ���û�����ʱ���ٴ���
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        // ����һ��������ʾ�ļ����ݵ�TextArea����
        textarea = new TextArea("", 24, 80);
        textarea.setFont(new Font("MonoSpaced", Font.PLAIN, 12));
        textarea.setEditable(false);
        this.add("Center", textarea);
        // ����һ������������ť�ؼ������
        Panel myPanel = new Panel();
        myPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        this.add(myPanel, "South");
        // ������ť�ؼ������Ҵ�������ť�¼�
        Font font = new Font("SansSerif", Font.BOLD, 14);
        Button btnOpenFile = new Button("���ļ�");
        Button btnClose = new Button("�ر�");
        btnOpenFile.addActionListener(this);
        btnOpenFile.setActionCommand("open");
        btnOpenFile.setFont(font);
        btnClose.addActionListener(this);
        btnClose.setActionCommand("close");
        btnClose.setFont(font);
        myPanel.add(btnOpenFile);
        myPanel.add(btnClose);
        this.pack();
        // ָ��Ŀ¼
        if (directory == null) {
            File f;
            if ((filename != null) && (f = new File(filename)).isAbsolute()) {
                directory = f.getParent();
                filename = f.getName();
            } else
                directory = System.getProperty("user.dir");
        }
        this.directory = directory; // ��סĿ¼
        setFile(directory, filename); // ���벢��ʾ�ļ�
    }

    //���벢����ʾָ��Ŀ¼�е�ָ���ļ�����
    public void setFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File f;
        FileReader in = null;
        // ��ȡ������ʾ�ļ�����
        // ��Ϊ���ڶ�ȡ�ı�������ʹ��FileReader������ʹ��FileInputStream.
        try {
            f = new File(directory, filename); // ����һ��file����
            in = new FileReader(f);               // ��һ��������ȡ�����ַ���
            char[] buffer = new char[4096]; // ÿ�ζ�ȡ4K�ַ�
            int len;                                       // ÿ�ζ�����ַ���
            textarea.setText("");                   // ����ı�����
            while ((len = in.read(buffer)) != -1) { // ��ȡһ���ַ�
                String s = new String(buffer, 0, len); // ת��Ϊһ���ַ���
                textarea.append(s); // ��ʾ�ı�
            }
            this.setTitle("TextFileViewer: " + filename); // ���ô��ڱ���
            textarea.setCaretPosition(0); // �����ļ���ʼλ��
        }
        // ��ʾ������Ϣ
        catch (IOException e) {
            textarea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("TextFileViewer: " + filename + ": I/O Exception");
        }
        // �ر�������
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    //��������ť�¼�
    @SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("open")) { // ����û��������򿪡���ť
            // ����һ���ļ��Ի�����ʾ�����µ��ļ�
            FileDialog myFileDialog = new FileDialog(this, "Open File", FileDialog.LOAD);
            myFileDialog.setDirectory(directory); // ����Ĭ��Ŀ¼
            // ��ʾ�Ի��򲢵ȴ��û�������
            myFileDialog.show();
            directory = myFileDialog.getDirectory(); // ��ס�µ�Ĭ��Ŀ¼
            setFile(directory, myFileDialog.getFile()); // ���벢��ʾѡ��
            myFileDialog.dispose(); // �رնԻ���
        } else if (cmd.equals("close")) // ����û������ˡ��رա���ť
            this.dispose(); //�رմ���
    }

    //TextFileViewer���Ա�����������ʹ��
    //Ҳ���Լ���main()��������Ϊһ����������.
    @SuppressWarnings("deprecation")
	static public void main(String[] args) throws IOException {
        // ����һ��TextFileViewer����
        Frame myFrame = new TextFileViewer((args.length == 1) ? args[0] : null);
        // ��TextFileViewer���ڹر�ʱ���˳�
        myFrame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
        // ����һ������
        myFrame.show();
    }
}
