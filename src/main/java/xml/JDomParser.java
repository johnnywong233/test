package xml;

import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.List;

/**
 * Created by wajian on 2016/9/5.
 */
public class JDomParser {

    public void buildXml01(){
        Element rootElement = new Element("Document");
        Element element = new Element("Element");

        Element nameElement = new Element("name");
        nameElement.setText("<名称>");
        Element valueElement = new Element("value");
        valueElement.setText("<值 >\"\\");
        Element descriptionElement = new Element("description");
        descriptionElement.setText("<描述><![CDATA[<查看是否转义保存>]]>");
        //添加子节点
        element.addContent(nameElement);
        element.addContent(valueElement);
        element.addContent(descriptionElement);

        rootElement.addContent(element);

        Document document = new Document(rootElement);
        DocType docType = new DocType("Doctype");
        document.setDocType(docType);

        XMLOutputter xmloutputter = new XMLOutputter();
        OutputStream outputStream;
        try {
            outputStream = new FileOutputStream("c:\\document.xml");
            xmloutputter.output(document, outputStream);
            System.out.println("generate xml doc success!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //parse
    public void parseXml01(String xmlPath){
        try {
            //create a SAXBuilder instance, true/false to validate xml document, deprecated in org.jdom2
            SAXBuilder saxBuilder = new SAXBuilder(false);            
            //set true is not allowed, if the XML file is not complete
//            SAXBuilder saxBuilder = new SAXBuilder(true);
            
            //1、直接指定绝对路径获取文件输入流对象
            //InputStream inputStream = new FileInputStream(xmlPath);
            //2、使用类的相对路径查找xml路径
            //InputStream inputStream = this.getClass().getResourceAsStream(xmlName);
            //3、也可以指定路径完成InputStream输入流的实例化操作
            InputStream inputStream = new FileInputStream(new File(xmlPath));
            //4、使用InputSource输入源作为参数也可以转换xml
            InputSource inputSource = new InputSource(inputStream);
            //解析xml文档，返回document文档对象
            Document document = saxBuilder.build(inputSource);
            Element rootElement = document.getRootElement();//根节点

            System.out.println("根节点名称：" + rootElement.getName());//获取节点的名称
            System.out.println("根节点有多少属性：" + rootElement.getAttributes().size());//获取节点属性数目
            System.out.println("根节点id属性的值：" + rootElement.getAttributeValue("id"));//获取节点的属性id的值
            System.out.println("根节点内文本：" + rootElement.getText());//如果元素有子节点则返回空字符串，否则返回节点内的文本
            //rootElement.getText() 之所以会换行是因为 标签与标签之间使用了tab键和换行符布局，这个也算是文本所以显示出来换行的效果。
            System.out.println("根节点内文本(1)：" + rootElement.getTextTrim());//去掉的是标签与标签之间的tab键和换行符等等，不是内容前后的空格
            System.out.println("根节点内文本(2)：" + rootElement.getTextNormalize()); //目前发现和getTextTrim()方法效果一样
            System.out.println("根节点内文本(3)：" + rootElement.getValue());  //返回节点下所有内容

            Element element = rootElement.getChild("Element"); //获取子节点,如果有多个Element节点，那么返回最先读取到的element
            if(element != null){
                System.out.println("子节点的文本：" + element.getText());//因为子节点和根节点都是Element对象所以它们的操作方式都是相同的
            }
            //但是有些情况xml比较复杂，规范不统一，某个节点不存在直接java.lang.NullPointerException，所以获取到element对象之后要先判断一下是否为空
            List<Element> list = rootElement.getChildren("Element");//返回的是List集合
            for (Element ele : list) {
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseXmlAslist(String xmlPath){
        try {
            SAXBuilder saxBuilder = new SAXBuilder(false);
            InputStream inputStream = new FileInputStream(new File(xmlPath));
            Document document = saxBuilder.build(inputStream);
            Element rootElement = document.getRootElement();

            List<Element> elementList = rootElement.getChildren("Element");
            for (Element element : elementList) {
                System.out.println("【" + element.getName() + "】：" + element.getTextTrim());//如果有子节点就返回空字符串
                Element nameElement = element.getChild("name");
                if(nameElement != null){
                    System.out.println("   " + nameElement.getName() + "：" + nameElement.getTextTrim());
                }
                Element valueElement = element.getChild("value");
                if(valueElement != null){
                    System.out.println("   " + valueElement.getName() + "：" + valueElement.getTextTrim());
                }
                Element descriptElement = element.getChild("descript");
                if(descriptElement != null){
                    System.out.println("   " + descriptElement.getName() + "：" + descriptElement.getTextTrim());
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void operateXml01(String xmlPath){
        try {
            SAXBuilder saxBuilder = new SAXBuilder(false);
            InputStream inputStream = new FileInputStream(new File(xmlPath));
            InputSource inputSource = new InputSource(inputStream);
            Document document = saxBuilder.build(inputSource);
            Element rootElement = document.getRootElement();
        /*
        rootElement.setName("root");//支持修改节点名称
        System.out.println("根节点修改之后的名称：" + rootElement.getName());
        rootElement.setText("text"); //同样修改标签内的文本也一样
        System.out.println("根节点修改之后的文本：" + rootElement.getText());
        */
            //接下来根据id获取元素 添加子元素或者删除子节点
            List<Element> elementList = rootElement.getChildren();
            System.out.println("删除节点前的集合个数："+elementList.size());
            for (Element element : elementList) {
                if(element.getAttributeValue("id")!=null){
                    if(element.getAttributeValue("id").equals("ele01")){
                        Element element01 = new Element("new_name");
                        element01.setText("新添加的名称");
                        Element element02 = new Element("new_value");
                        element02.setText("新添加的名称");
                        Element element03 = new Element("new_descript");
                        element03.setText("新添加的名称");
                        element.addContent(element01);
                        element.addContent(element02);
                        element.addContent(element03);
                    }else if(element.getAttributeValue("id").equals("ele02")){
                    /*
                    rootElement.removeContent(element);
                    break;
                    */
                        element.removeContent();
                        System.out.println("删除节点后的集合个数："+elementList.size());
                    }
                }
            }
            elementList = rootElement.getChildren("Element");
            for (Element element : elementList) {
                System.out.println("【" + element.getName() + "】：" + element.getTextTrim());//如果有子节点就返回空字符串
                Element nameElement = element.getChild("new_name");
                if(nameElement != null){
                    System.out.println("   " + nameElement.getName() + "：" + nameElement.getTextTrim());
                }
                Element valueElement = element.getChild("new_value");
                if(valueElement != null){
                    System.out.println("   " + valueElement.getName() + "：" + valueElement.getTextTrim());
                }
                Element descriptElement = element.getChild("new_descript");
                if(descriptElement != null){
                    System.out.println("   " + descriptElement.getName() + "：" + descriptElement.getTextTrim());
                }
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //http://blog.csdn.net/chenghui0317/article/details/12137845
    public static void main(String[] args) {
        String xmlPath = "C:\\work\\Demo\\johnny\\src\\test\\resources\\ErrorXML.xml";
//        String xmlName = xmlPath.substring(xmlPath.lastIndexOf("\\"));
        JDomParser demo = new JDomParser();
        demo.parseXml01(xmlPath);
        System.out.println("***********");
        demo.parseXmlAslist(xmlPath);
    }
}
