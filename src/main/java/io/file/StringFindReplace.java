package io.file;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class StringFindReplace implements MouseListener, WindowListener {
    private Frame myFrame;

    private TextArea myTextArea;

    private TextField myTextField1, myTextField2;

    private Button myButton1;

    private Button myButton2;

    private Panel myPanel1, myPanel2, myPanel3, myPanel4, myPanel5;

    private Label myLabel1, myLabel2, myLabel3;

    //boolean myFlag = false;

    Dialog myDialog;

    Label textLab;

    Button myButton = new Button("ȷ��");

    public static void main(String args[]) {
        StringFindReplace myExam = new StringFindReplace();
        myExam.create();
    }

    //create()�������ڴ���������ʹ����йز��Һ��滻�ַ��������Ϣ�ĶԻ��� 
    public void create() {
        //�����Ǵ���������
        myFrame = new Frame("�ַ����Ĳ��Һ��滻");
        //myTextArea��������ϵ��ı�����
        myTextArea = new TextArea();
        //myTextField1����������������һ��滻ǰ���ַ������ı���
        myTextField1 = new TextField();
        //myTextField2�������������滻����ַ������ı���
        myTextField2 = new TextField();
        //��ʼ��������ť���󣬷ֱ�����ʵ�֡����ҡ��͡��滻������
        myButton1 = new Button("����");
        myButton2 = new Button("�滻");
        //���������Label����������ʾ�йص���ʾ��Ϣ
        myLabel1 = new Label("�ı�����");
        myLabel2 = new Label("�����һ��滻ǰ���ַ�����");
        myLabel3 = new Label("�滻����ַ�����");
        //��������5��Panel�������ڿ����������ϸ������λ�úʹ�С
        myPanel1 = new Panel();
        myPanel2 = new Panel();
        myPanel3 = new Panel();
        myPanel4 = new Panel();
        myPanel5 = new Panel();
        //myPanel1���ڿ����ı�����������ʾ��Ϣ�����λ��
        myPanel1.setLayout(new BorderLayout());
        myPanel1.add("North", myLabel1);
        myPanel1.add("Center", myTextArea);
        //myPanel2���ڿ��Ƶ�һ���ı���������ʾ��Ϣ�����λ��
        myPanel2.setLayout(new BorderLayout());
        myPanel2.add("North", myLabel2);
        myPanel2.add("Center", myTextField1);
        //myPanel3���ڿ��Ƶڶ����ı���������ʾ��Ϣ�����λ��
        myPanel3.setLayout(new BorderLayout());
        myPanel3.add("North", myLabel3);
        myPanel3.add("Center", myTextField2);
        //myPanel4���ڿ��ơ����ҡ���ť�͡��滻����ť�����λ��
        myPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        myPanel4.add(myButton1);
        myPanel4.add(myButton2);
        //myPanel5���ڿ���myPanel2��myPanel3��myPanel4�����λ��
        myPanel5.setLayout(new GridLayout(3, 1));
        myPanel5.add(myPanel2);
        myPanel5.add(myPanel3);
        myPanel5.add(myPanel4);
        //���ͨ��myFrame���������Ч��
        myFrame.setLayout(new GridLayout(1, 2));
        myFrame.add(myPanel1);
        myFrame.add(myPanel5);
        //Ϊ�����ҡ���ť�����滻����ť������������¼�������
        myButton1.addMouseListener(this);
        myButton2.addMouseListener(this);
        myFrame.addWindowListener(this);
        //���������������������Ĵ�С����������ɼ�
        myFrame.setSize(400, 260);
        myFrame.setVisible(true);

        /*
         * �����������ڴ���һ���Ի��򣬵��û����������ҡ���ť���ߡ��滻����ť�󣬸��ݽ����ʾ
         * һ���йؽ����Ϣ�ĶԻ��򣬶Ի�������һ��Label�����һ��ȷ�ϰ�ť��Label���������ʾ ���һ��滻�ַ����Ĵ���
         */
        myDialog = new Dialog(myFrame);
        myDialog.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20));
        textLab = new Label("");
        myDialog.add(textLab);
        myDialog.add(myButton);
        myButton.addMouseListener(this);
        myDialog.setSize(200, 100);

    }

    /** ����mouseClicked���ڴ�����굥�����¼���Ҳ���ǵ���굥���¼������󣬳���ͻ����÷�����ִ�� */
    @SuppressWarnings("deprecation")
	public void mouseClicked(MouseEvent e) {
        //�������������ڻ���¼�Դ��ť
        Button myBTN = (Button) (e.getSource());
        //�����if��䴦���¼�Դ�ǡ����ҡ���ť���滻����ťʱ�����
        if (myBTN.getLabel() == "����" || myBTN.getLabel() == "�滻") {
            /* String�ͱ���myString1��myString2����ı�����͵�һ���ı����е��������� */
            String myString1 = myTextArea.getText();
            String myString2 = myTextField1.getText();
            //����matchNum�����ַ���ƥ��Ĵ�������ʼֵΪ0
            int matchNum = 0;
            //cursorPos�����굱ǰ��λ��
            int cursorPos = myTextArea.getCaretPosition();
            //ʵ����һ��Match��Ķ���
            Match myClass = new Match();
            //�����if��䴦���������ҡ���ť�¼�
            if (myBTN.getLabel() == "����") {
                //ͨ������Match��ķ���strFind������ַ���ƥ��Ĵ���
                matchNum = myClass.strFind(myString1, myString2, cursorPos);
                //�����һ������������öԻ�����Label������ı�����
                textLab.setText("���ҵ�" + matchNum + "��");
                myDialog.show();
            }
            //��������if��䴦�������滻����ť�¼�
            if (myBTN.getLabel() == "�滻") {
                //����myString3��õڶ����ı����е��ַ���
                String myString3 = myTextField2.getText();
                //ͨ������Match���е�strReplace��ť�����ַ���ƥ�����
                matchNum = myClass.strReplace(myString1, myString2, myString3,
                        cursorPos);
                //�������öԻ�����Label������ı�����
                textLab.setText("���滻" + matchNum + "��");
                //������������ˢ���ַ����滻���ı������������ʾ
                StringBuffer taText = myClass.repStr;
                myTextArea.setText(taText.toString());
                myDialog.show();
            }
        }
        //�����if������ڴ����¼�ԴΪȷ�ϰ�ťʱ�����
        if (myBTN.getLabel() == "ȷ��") {
            //����ȷ�ϰ�ť����Ϣ��ʾ�Ի�����ʧ����������ʾ
            myDialog.hide();
            myFrame.show();
        }
    }

    //����������дMouseListener��WindowListener�ӿ��еķ���
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    //��дwindowClosing�������رմ���ʱ�������˳�
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
/** ��Match���ڴ����й��ַ������Һ��滻�㷨 */

class Match {
    StringBuffer repStr;

    /* ����strFind����ʵ���ַ������ң�����ƥ��Ĵ��� */
    public int strFind(String s1, String s2, int pos) {
        /* ����i��j�ֱ��ʾ������ģʽ���е�ǰ�ַ�����λ�ã�k��ʾƥ����� */
        int i, j, k = 0;
        //pos���������п�ʼ�Ƚϵ�λ��
        i = pos;
        j = 0;
        while (i < s1.length() && j < s2.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                ++i;
                ++j;
                if (j == s2.length()) {
                    //j=s2.length()��ʾ�ַ���ƥ��ɹ���ƥ�������1
                    k = k + 1;
                    //��ָʾ������ģʽ���е�ǰ�ַ��ı���i��j���л���
                    i = i - j + 1;
                    j = 0;
                }
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        return k;
    }

    /* ����strReplace����ʵ���ַ����滻�����������滻�Ĵ��� */
    public int strReplace(String s1, String s2, String s3, int pos) {
        /* ����i��j�ֱ��ʾ������ģʽ���е�ǰ�ַ�����λ�ã�k��ʾƥ����� */
        int i, j, k = 0;
        i = pos;
        j = 0;
        //��s1ת����StringBuffer�ͽ��в���
        repStr = new StringBuffer(s1);

        while (i < repStr.length() && j < s2.length()) {
            if (repStr.charAt(i) == s2.charAt(j)) {
                ++i;
                ++j;
                if (j == s2.length()) {
                    /* j=s2.length()��ʾ�ַ���ƥ��ɹ���ƥ�������1����������������ַ����滻 */
                    k = k + 1;
                    repStr.replace(i - j, i, s3);
                    //��j�������¸�ֵ��ʼ�µıȽ�
                    j = 0;
                }
            } else {
                i = i - j + 1;
                j = 0;
            }
        }
        return k;
    }
}