package io.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

public class FileUtil {
	public static int BUFFER_SIZE = 2048;

    private static List<String> unTar(InputStream inputStream, String destDir) throws Exception {
        List<String> fileNames = new ArrayList<String>();
        TarArchiveInputStream tarIn = new TarArchiveInputStream(inputStream, BUFFER_SIZE);
        TarArchiveEntry entry = null;
        try {
            while ((entry = tarIn.getNextTarEntry()) != null) {
                fileNames.add(entry.getName());
                if (entry.isDirectory()) {//��Ŀ¼  
                    createDirectory(destDir, entry.getName());//������Ŀ¼  
                } else {//���ļ�  
                    File tmpFile = new File(destDir + File.separator + entry.getName());
                    createDirectory(tmpFile.getParent() + File.separator, null);//�������Ŀ¼  
                    OutputStream out = null;
                    try { 
                        out = new FileOutputStream(tmpFile);
                        int length = 0;
                        byte[] b = new byte[2048];
                        while ((length = tarIn.read(b)) != -1) {
                            out.write(b, 0, length);
                        }
                    } finally {
                        IOUtils.closeQuietly(out);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(tarIn);
        }

        return fileNames;
    }

    public static List<String> unTar(String tarFile, String destDir) throws Exception {
        File file = new File(tarFile);
        return unTar(file, destDir);
    }

    public static List<String> unTar(File tarFile, String destDir) throws Exception {
        if(StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new FileInputStream(tarFile), destDir);
    }

    public static List<String> unTarBZip2(File tarFile,String destDir) throws Exception{ 
        if(StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new BZip2CompressorInputStream(new FileInputStream(tarFile)), destDir);
    }  

    public static List<String> unTarBZip2(String file,String destDir) throws Exception{ 
        File tarFile = new File(file);
        return unTarBZip2(tarFile, destDir);
    }  

    public static List<String> unBZip2(String bzip2File, String destDir) throws IOException {
        File file = new File(bzip2File);
        return unBZip2(file, destDir);
    }

    public static List<String> unBZip2(File srcFile, String destDir) throws IOException {
        if(StringUtils.isBlank(destDir)) {
            destDir = srcFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        List<String> fileNames = new ArrayList<String>();
        InputStream is = null;
        OutputStream os = null;
        try {
            File destFile = new File(destDir, FilenameUtils.getBaseName(srcFile.toString()));
            fileNames.add(FilenameUtils.getBaseName(srcFile.toString()));
            is = new BZip2CompressorInputStream(new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE));
            os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
            IOUtils.copy(is, os);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
        return fileNames;
    }

    public static List<String> unGZ(String gzFile, String destDir) throws IOException {
        File file = new File(gzFile);
        return unGZ(file, destDir);
    }

    public static List<String> unGZ(File srcFile, String destDir) throws IOException {
        if(StringUtils.isBlank(destDir)) {
            destDir = srcFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        List<String> fileNames = new ArrayList<String>();
        InputStream is = null;
        OutputStream os = null;
        try {
            File destFile = new File(destDir, FilenameUtils.getBaseName(srcFile.toString()));
            fileNames.add(FilenameUtils.getBaseName(srcFile.toString()));
            is = new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE));
            os = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER_SIZE);
            IOUtils.copy(is, os);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
        return fileNames;
    }

    public static List<String> unTarGZ(File tarFile,String destDir) throws Exception{ 
        if(StringUtils.isBlank(destDir)) {
            destDir = tarFile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        return unTar(new GzipCompressorInputStream(new FileInputStream(tarFile)), destDir);
    }  

    public static List<String> unTarGZ(String file,String destDir) throws Exception{ 
        File tarFile = new File(file);
        return unTarGZ(tarFile, destDir);
    }  

    public static void createDirectory(String outputDir,String subDir){  
        File file = new File(outputDir);  
        if(!(subDir == null || subDir.trim().equals(""))){//��Ŀ¼��Ϊ��  
            file = new File(outputDir + File.separator + subDir);  
        }  
        if(!file.exists()){  
            file.mkdirs();  
        }  
    }  

    public static List<String> unZip(File zipfile, String destDir) throws Exception {
        if(StringUtils.isBlank(destDir)) {
            destDir = zipfile.getParent();
        }
        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<String>();

        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipfile), BUFFER_SIZE));
            ZipArchiveEntry entry = null;
            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());
                if (entry.isDirectory()) {
                    File directory = new File(destDir, entry.getName());
                    directory.mkdirs();
                } else {
                    OutputStream os = null;
                    try {
                        os = new BufferedOutputStream(new FileOutputStream(new File(destDir, entry.getName())), BUFFER_SIZE);
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }

        return fileNames;
    }

    public static List<String> unZip(String zipfile, String destDir) throws Exception {
        File zipFile = new File(zipfile);
        return unZip(zipFile, destDir);
    }

    public static List<String> unCompress(String compressFile, String destDir) throws Exception {
        String upperName= compressFile.toUpperCase();
        List<String> ret = null;
        if(upperName.endsWith(".ZIP")) {
            ret = unZip(compressFile, destDir);
        } else if(upperName.endsWith(".TAR")) {
            ret = unTar(compressFile, destDir);
        } else if(upperName.endsWith(".TAR.BZ2")) {
            ret = unTarBZip2(compressFile, destDir);
        } else if(upperName.endsWith(".BZ2")) {
            ret = unBZip2(compressFile, destDir);
        } else if(upperName.endsWith(".TAR.GZ")) {
            ret = unTarGZ(compressFile, destDir);
        } else if(upperName.endsWith(".GZ")) {
            ret = unGZ(compressFile, destDir);
        }
        return ret;
    }

    public static void main(String[] args) throws Exception {

    	//C:\Users\wajian\Documents\Test
//        System.out.println(unZip("C:\\Users\\wajian\\Documents\\Test\\test.zip", "C:\\Users\\wajian\\Documents\\Test\\"));
//        System.out.println(unTar("C:\\Users\\wajian\\Documents\\Test\\test.tar", "C:\\Users\\wajian\\Documents\\Test\\"));

    	//not ok
//        System.out.println(unBZip2("C:\\Users\\wajian\\Documents\\Test\\test.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));
//        System.out.println(unTarBZip2("C:\\Users\\wajian\\Documents\\Test\\test.tar.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));
        
        //
//        System.out.println(unBZip2("C:\\Users\\wajian\\Documents\\Test\\test1.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));
        System.out.println(unBZip2("C:\\Users\\wajian\\Documents\\Test\\test1.tar.bz2", "C:\\Users\\wajian\\Documents\\Test\\"));
        
        
//        System.out.println(unGZ("C:\\Users\\wajian\\Documents\\Test\\test.gz", "C:\\Users\\wajian\\Documents\\Test\\"));
//        System.out.println(unTarGZ("C:\\Users\\wajian\\Documents\\Test\\test.tar.gz", "C:\\Users\\wajian\\Documents\\Test\\"));
    }

}
