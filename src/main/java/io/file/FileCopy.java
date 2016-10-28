package io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * �������ܶ���ʵ�ָ����ļ��� ���Ҷ���һ���ɹ���������ʹ�õ��ļ����Ʒ���copyFile()
 **/

public class FileCopy {
    public static void main(String[] args) {
        if (args.length != 2)                                  //������
            System.err.println("ʹ�÷�����java FileCopy <Դ�ļ���> <Ŀ���ļ���>");
        else {
            //����copyFile()������ʵ�ָ����ļ�������ʾ������Ϣ��
            try {
                copyFile(args[0], args[1]);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    //��̬����copyFile()����ʵ���ļ����ƣ�
    //�ڸ����ļ�֮ǰ������һЩ���ԣ���ȷ��һ��������
    public static void copyFile(String resourceFileName, String targetFileName)
            throws IOException {
        //���ȶ��������ļ������Ƕ���File���ʵ��
        File resourceFile = new File (resourceFileName);
        File targetFile= new File (targetFileName);

        //����ȷ��Դ�ļ����ڣ������ǿɶ����ļ�
        if (!resourceFile.exists())
            abort("δ����Դ�ļ���" + resourceFileName);
        if (!resourceFile.isFile())
            abort("���ܸ���Ŀ¼�� " + resourceFileName);
        if (!resourceFile.canRead())
            abort("Դ�ļ���" + resourceFileName +"���ǿɶ��ļ���");

        //���Ŀ����һ��Ŀ¼����Դ�ļ�����ΪĿ���ļ���
        if (targetFile.isDirectory())
            targetFile= new File(targetFile, resourceFile.getName());

        //���Ŀ���ļ����ڣ�����Ҫȷ�����ǿ�д���ļ���
        //������д��ǰѯ���û���
        //���Ŀ���ļ������ڣ���ôҪȷ����Ŀ¼���ڲ����ǿ�д�ġ�
        if (targetFile.exists()) {
            if (!targetFile.canWrite())
                abort("Ŀ���ļ����ǿ�д�ļ���" + targetFileName);

            //ѯ���û��Ƿ񸲸�Ŀ���ļ�
            System.out.print("�Ƿ񸲸����ڵ��ļ� " + targetFile.getName() + "? (Y/N): ");
            System.out.flush();

            //�õ��û��ķ�����Ϣ
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    System.in));
            String response = in.readLine();

            //����û���������Ϣ��
            //����õ��Ĵ��Ƿ񶨵ģ���ôȡ�����Ʋ�����
            if (!response.equals("Y") && !response.equals("y"))
                abort("�û�ȡ�����Ʋ�����");
        } else {
            //����ļ������ڣ����Ŀ¼�Ƿ���ڣ���Ŀ¼�Ƿ��ǿ�д�ġ�
            //���getParent()�������ؿ�ֵ��˵����Ŀ¼Ϊ��ǰĿ¼
            String parent = targetFile.getParent(); //Ŀ��Ŀ¼
            if (parent == null) //����ǿ�ֵ��ʹ�õ�ǰĿ¼
                parent = System.getProperty("user.dir");
            File directory = new File(parent); //����ת�����ļ�
            if (!directory.exists())
                abort("Ŀ��Ŀ¼ " + parent + "�����ڡ� " );
            if (directory.isFile())
                abort("ָ����Ŀ�� " + parent + "����Ŀ¼ " );
            if (!directory.canWrite())
                abort("ָ����Ŀ��Ŀ¼ " + parent + "���ǿ�д�� " );
        }

        //��ʼ�����ļ�
        FileInputStream resource = null;   //��ȡԴ�ļ���������
        FileOutputStream target = null;    //д��Ŀ���ļ���������
        try {
            resource = new FileInputStream(resourceFile); //����������
            target = new FileOutputStream(targetFile); //���������
            byte[] buffer = new byte[4096];
            int byteNum; //�����е��ֽ���Ŀ

            //��һ���ֽ������������У�Ȼ������д������
            //ѭ��ֱ���ļ�ĩβ����read()����-1ʱ��ֹͣ
            while ((byteNum = resource.read(buffer)) != -1)
                //��ȡ��ֱ���ļ�ĩβ
                target.write(buffer, 0, byteNum); //д��
                System.out.print("�ļ����Ƴɹ��� ");
        }

        //�ر���
        finally {
            if (resource != null)
                try {
                    resource.close();
                } catch (IOException e) {
                    ;
                }
            if (target != null)
                try {
                    target.close();
                } catch (IOException e) {
                    ;
                }
        }
    }

    //�׳��쳣
    private static void abort(String msg) throws IOException {
        throw new IOException("�ļ����ƣ� " + msg);
    }
}
