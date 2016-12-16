package io.file;

import java.io.File;

/**
 * ��������ʾ��File��������Ŀ¼�������ļ�����
 */

public class ClsFileDemo {
    private final static String myUsage = "�÷���\n" + "�½���ClsFileDemo path1 "
            + "���½�һ��Ŀ¼\n" + "ɾ����ClsFileDemo -d path1 " + "��ɾ��һ��Ŀ¼/�ļ�\n"
            + "��������ClsFileDemo -r path1 path2" + "��path1������Ϊpath2\n";

    private static void myUsage() {
        System.err.println(myUsage); //��ʾ�����ʹ�÷���
        System.exit(1);
    }

    //�÷���������ʾ������Ϣ
    private static void fileInfo(File f) {
        System.out.println( //��ʾ������Ϣ
                "Absolute path: " + f.getAbsolutePath() + "\n Can read: "
                        + f.canRead() + "\n Can write: " + f.canWrite()
                        + "\n getName: " + f.getName() + "\n getParent: "
                        + f.getParent() + "\n getPath: " + f.getPath()
                        + "\n length: " + f.length() + "\n lastModified: "
                        + f.lastModified());
        if (f.isFile())
            System.out.println("it's a file");
        else if (f.isDirectory())
            System.out.println("it's a directory");
    }

    public static void main(String[] args) {
        if (args.length < 1)
            myUsage(); //���������ʱ��ʾ�÷���Ϣ
        if (args[0].equals("-r")) {
            if (args.length != 3)
                myUsage();
            File old = new File(args[1]), rname = new File(args[2]);
            old.renameTo(rname); //������
            System.out.println(old + " ������Ϊ��" + rname);
            fileInfo(old);
            fileInfo(rname);
            return; //�˳�
        }
        int count = 0;
        boolean del = false;
        if (args[0].equals("-d")) {
            count++;
            del = true;
        }
        for (; count < args.length; count++) {
            File f = new File(args[count]);
            if (f.exists()) { //��������ʾĿ¼��Ϣ��ɾ��Ŀ¼
                System.out.println(f + " �Ѿ�����");
                if (del) {
                    System.out.println("����ɾ��..." + f);
                    f.delete();
                    System.out.println(f + "�Ѿ�ɾ����");
                }
            } else { //�������򴴽�Ŀ¼
                if (!del) {
                    f.mkdirs();
                    System.out.println(f + "�Ѿ�������");
                }
            }
            fileInfo(f); //����fileInfo����
        }
    }
}
