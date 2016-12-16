package io.file.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Test  {
	//�������֪����261��Java�������⡷4.24
	public static void main(String[] args) {
//		String[] files = new String[]{"1.txt","2.txt","3.txt"};
//		try {
//			zip(files,"out.zip");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		try {
//			unzip("out.zip",".");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		try {
//			zipDir("111","111.zip");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		try {
			unzipDir("111.zip",".");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ļ�ѹ��
	 * files:Ҫѹ�����ļ�
	 * destZip:ѹ��֮����ļ�
	 */
	public static void zip(String[] files,String destZip) throws IOException {
		FileOutputStream fos = new FileOutputStream(destZip);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		ZipOutputStream zos = new ZipOutputStream(bos);

		//ѭ�����ļ���ѹ����zip��
		for (int i = 0; i < files.length; i++) {
			String file = files[i];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			// ��ȡ�ļ�����������Ŀ����ӵ�zip��
			File f = new File(file);
			ZipEntry zl = new ZipEntry(f.getName());
			zos.putNextEntry(zl);

			int tmp = -1;
			while ((tmp = bis.read())!= -1) {
				zos.write(tmp);
			}
			zos.closeEntry();
			bis.close();
		}
		zos.close();
	}
	
	/**
	 * �ļ���ѹ��
	 * zipfile:Ҫ��ѹ����zip�ļ�
	 * destpath����ѹ���ļ����ŵ�Ŀ¼
	 */
	public static void unzip(String zipfile, String destpath)
			throws IOException {
		FileInputStream fis = new FileInputStream(zipfile);
		ZipInputStream zis = new ZipInputStream(fis);
		ZipEntry zl = null;

		//fix destpath:
		if(!destpath.endsWith(File.separator)){
			destpath = destpath + File.separator;
		}
		
		while ((zl = zis.getNextEntry()) != null) {
			if (zl.isDirectory()) {
				File f = new File(destpath + zl.getName());
				f.mkdirs();
			} else {
				String file = zl.getName();
				FileOutputStream fos = new FileOutputStream(destpath + file);
				int tmp = -1;
				while ((tmp = zis.read()) != -1) {
					fos.write(tmp);
				}
				zis.closeEntry();
				fos.close();
			}
		}
		zis.close();
	}
	
	/**
	 * ѹ��Ŀ¼ 
	 * srcDir:��Ҫѹ����ԴĿ¼
	 * zipFileName��ѹ������ļ���
	 */
	public static void zipDir(String srcDir, String zipFileName)
			throws IOException {
		File dir = new File(srcDir);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		zipDir(out, dir, "");
		out.close();
	}
	
	private static void zipDir(ZipOutputStream out, File f, String base)
			throws IOException {
		if (f.isDirectory()) {
			//no need to put a directory zip entry in
			
			base = base + f.getName() + File.separator;
			File[] fl = f.listFiles();
			for (int i = 0; i < fl.length; i++) {
				zipDir(out, fl[i], base);
			}
			
		} else {
			out.putNextEntry(new ZipEntry(base + f.getName()));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.closeEntry();
			in.close();
		}
	}
	
	 /**
	  * ��ѹ��Ŀ¼
	  * zipFileName:��Ҫ��ѹ����zip�ļ�
	  * outputDirectory:��ѹ�����ļ�����·��
	  */
	 public static void unzipDir(String zipFileName, String outputDirectory)
	    throws IOException {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
		ZipEntry z = null;
		
		//fix outputDirectory:
		if(!outputDirectory.endsWith(File.separator)){
			outputDirectory = outputDirectory + File.separator;
		}
		
		while ((z = in.getNextEntry()) != null) {
			if (z.isDirectory()) {
				String name = z.getName();
//				System.out.println("directory: "+name);
				//A directory zip entry is defined to be one whose name ends with a '/'.
				name = name.substring(0, name.length() - 1);
				File f = new File(outputDirectory + name);
				f.mkdir();
			} else {
				String name = z.getName();
//				System.out.println("file: "+name);
				File f = new File(outputDirectory + name);
				File parentDir = f.getParentFile();
				if(parentDir != null && !parentDir.exists()) parentDir.mkdirs();
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1) {
					out.write(b);
				}
				out.close();
			}
		}
		in.close();
	}
}

