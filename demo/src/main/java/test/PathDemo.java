package test;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by wajian on 2016/9/14.
 */
public class PathDemo {
    //http://www.jb51.net/article/35668.htm
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        System.out.println(System.getProperty("file.separator") + System.getProperty("path.separator") + System.getProperty("line.separator"));
    }
    
    /*
     * System.getProperty()参数大全
		# java.version                                Java Runtime Environment version 
		# java.vendor                                Java Runtime Environment vendor 
		# java.vendor.url                           Java vendor URL 
		# java.home                                Java installation directory 
		# java.vm.specification.version   Java Virtual Machine specification version 
		# java.vm.specification.vendor    Java Virtual Machine specification vendor 
		# java.vm.specification.name      Java Virtual Machine specification name 
		# java.vm.version                        Java Virtual Machine implementation version 
		# java.vm.vendor                        Java Virtual Machine implementation vendor 
		# java.vm.name                        Java Virtual Machine implementation name 
		# java.specification.version        Java Runtime Environment specification version 
		# java.specification.vendor         Java Runtime Environment specification vendor 
		# java.specification.name           Java Runtime Environment specification name 
		# java.class.version                    Java class format version number 
		# java.class.path                      Java class path 
		# java.library.path                 List of paths to search when loading libraries 
		# java.io.tmpdir                       Default temp file path 
		# java.compiler                       Name of JIT compiler to use 
		# java.ext.dirs                       Path of extension directory or directories 
		# os.name                              Operating system name 
		# os.arch                                  Operating system architecture 
		# os.version                       Operating system version 
		# file.separator                         File separator ("/" on UNIX) 
		# path.separator                  Path separator (":" on UNIX) 
		# line.separator                       Line separator ("\n" on UNIX) 
		# user.name                        User's account name 
		# user.home                              User's home directory 
		# user.dir                               User's current working directory
     */

    public static Integer getTomcatPortFromConfigXml(File serverXml) {
        Integer port;
        try {
            DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setNamespaceAware(true); // never forget this!
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(serverXml);
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile
                    ("/Server/Service[@name='Catalina']/Connector[count(@scheme)=0]/@port[1]");
            String result = (String) expr.evaluate(doc, XPathConstants.STRING);
            port = result != null && result.length() > 0 ? Integer.valueOf(result) : null;
        } catch (Exception e) {
            port = null;
        }
        return port;
    }

    @Test
    private String path() {
        //对于getCanonicalPath()函数，“."就表示当前的文件夹，而”..“则表示当前文件夹的上一级文件夹
        //对于getAbsolutePath()函数，则不管”.”、“..”，返回当前的路径加上你在new File()时设定的路径
        //至于getPath()函数，得到的只是你在new File()时设定的路径
        //比如当前的路径为 C:\test ：
        File directory = new File("abc");
        try {
//            direcotry.getPath();          //得到的是abc
            directory.getCanonicalPath(); //得到的是C:\test\abc
            directory.getAbsolutePath();  //得到的是C:\test\abc
        } catch (IOException e) {
            e.printStackTrace();
        }

        File directory1 = new File(".");
        try {
            directory1.getCanonicalPath(); //得到的是C:\test
            directory1.getAbsolutePath();  //得到的是C:\test\.
//            direcotry1.getPath();          //得到的是.
        } catch (IOException e) {
            e.printStackTrace();
        }

        File directory2 = new File("..");
        try {
            directory2.getCanonicalPath(); //得到的是C:\
            directory2.getAbsolutePath();  //得到的是C:\test\..
//            direcotry2.getPath();          //得到的是..
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
