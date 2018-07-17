package mail;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by wajian on 2016/8/19.
 * demo of send mail by javax mail api
 */
public class MailDemo1 {
    //http://www.jb51.net/article/61005.htm
    public static void main(String[] args) throws Exception {
        // 连接pop3服务器的主机名、协议、用户名、密码
        String pop3Server = "pop.163.com";
        String protocol = "pop3";
        String user = "wangjianloveblue@163.com";
        String pwd = "wjlb38745";

        // 创建一个有具体连接信息的Properties对象
        Properties props = new Properties();
        props.setProperty("mail.store.protocol", protocol);
        props.setProperty("mail.pop3.host", pop3Server);

        // 使用Properties对象获得Session对象
        Session session = Session.getInstance(props);
        session.setDebug(true);

        // 利用Session对象获得Store对象，并连接pop3服务器
        Store store = session.getStore();
        store.connect(pop3Server, user, pwd);

        // 获得邮箱内的邮件夹Folder对象，以"只读"打开
        Folder folder = store.getFolder("inbox");
        folder.open(Folder.READ_ONLY);

        // 获得邮件夹Folder内的所有邮件Message对象
        Message[] messages = folder.getMessages();
        ReciveMail rm;
        for (int i = 0; i < messages.length; i++) {
            rm = new ReciveMail((MimeMessage) messages[i]);
            rm.receive(messages[i], i);
        }
        folder.close(false);
        store.close();
    }
}

class ReciveMail {
    private MimeMessage msg = null;
    private String saveAttchPath = "";
    private StringBuffer bodytext = new StringBuffer();
    private String dateFormate = "yy-MM-dd HH:mm";

    ReciveMail(MimeMessage msg) {
        this.msg = msg;
    }

    public void setMsg(MimeMessage msg) {
        this.msg = msg;
    }

    /**
     * 获取发送邮件者信息
     */
    public String getFrom() throws MessagingException {
        InternetAddress[] address = (InternetAddress[]) msg.getFrom();
        String from = address[0].getAddress();
        if (from == null) {
            from = "";
        }
        String personal = address[0].getPersonal();
        if (personal == null) {
            personal = "";
        }
        return personal + "<" + from + ">";
    }

    /**
     * 获取邮件收件人，抄送，密送的地址和信息。根据所传递的参数不同 "to"-->收件人,"cc"-->抄送人地址,"bcc"-->密送地址
     */
    public String getMailAddress(String type) throws MessagingException, UnsupportedEncodingException {
        String mailaddr = "";
        String addrType = type.toUpperCase();
        InternetAddress[] address = null;

        if ("TO".equals(addrType) || "CC".equals(addrType) || "BCC".equals(addrType)) {
            if ("TO".equals(addrType)) {
                address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.TO);
            }
            if ("CC".equals(addrType)) {
                address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.CC);
            }
            if ("BCC".equals(addrType)) {
                address = (InternetAddress[]) msg.getRecipients(Message.RecipientType.BCC);
            }
            if (address != null) {
                for (InternetAddress addres : address) {
                    String mail = addres.getAddress();
                    if (mail == null) {
                        mail = "";
                    } else {
                        mail = MimeUtility.decodeText(mail);
                    }
                    String personal = addres.getPersonal();
                    if (personal == null) {
                        personal = "";
                    } else {
                        personal = MimeUtility.decodeText(personal);
                    }
                    String compositeto = personal + "<" + mail + ">";
                    mailaddr += "," + compositeto;
                }
                mailaddr = mailaddr.substring(1);
            }
        } else {
            throw new RuntimeException("Error email Type!");
        }
        return mailaddr;
    }

    /**
     * 获取邮件主题
     */
    private String getSubject() throws UnsupportedEncodingException, MessagingException {
        String subject;
        subject = MimeUtility.decodeText(msg.getSubject());
        return subject;
    }

    /**
     * 获取邮件发送日期
     */
    public String getSendDate() throws MessagingException {
        Date sendDate = msg.getSentDate();
        SimpleDateFormat smd = new SimpleDateFormat(dateFormate);
        return smd.format(sendDate);
    }

    /**
     * 获取邮件正文内容
     */
    private String getBodyText() {
        return bodytext.toString();
    }

    /**
     * 解析邮件，将得到的邮件内容保存到一个stringBuffer对象中，
     * 解析邮件 主要根据MimeType的不同执行不同的操作，一步一步的解析
     */
    private void getMailContent(Part part) throws MessagingException, IOException {

        String contentType = part.getContentType();
        int nameindex = contentType.indexOf("name");
        boolean conname = false;
        if (nameindex != -1) {
            conname = true;
        }
        System.out.println("CONTENTTYPE:" + contentType);
        if (part.isMimeType("text/plain") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("text/html") && !conname) {
            bodytext.append((String) part.getContent());
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        }
    }

    /**
     * 判断邮件是否需要回执，如需回执返回true，否则返回false
     */
    private boolean getReplySign() throws MessagingException {
        boolean replySign = false;
        String needreply[] = msg.getHeader("Disposition-Notification-TO");
        if (needreply != null) {
            replySign = true;
        }
        return replySign;
    }

    /**
     * 获取此邮件的message-id
     */
    public String getMessageId() throws MessagingException {
        return msg.getMessageID();
    }

    /**
     * 判断此邮件是否已读，如果未读则返回false，已读返回true
     */
    public boolean isNew() throws MessagingException {
        boolean isNew = false;
        Flags flags = msg.getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        System.out.println("flags's length:" + flag.length);
        for (Flags.Flag aFlag : flag) {
            if (aFlag == Flags.Flag.SEEN) {
                isNew = true;
                System.out.println("seen message .......");
                break;
            }
        }
        return isNew;
    }

    /**
     * 判断是是否包含附件
     */
    private boolean isContainAttch(Part part) throws MessagingException, IOException {
        boolean flag = false;

        //String contentType = part.getContentType();
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodypart = multipart.getBodyPart(i);
                String dispostion = bodypart.getDisposition();
                if ((dispostion != null) && (dispostion.equals(Part.ATTACHMENT) || dispostion.equals(Part.INLINE))) {
                    flag = true;
                } else if (bodypart.isMimeType("multipart/*")) {
                    flag = isContainAttch(bodypart);
                } else {
                    String conType = bodypart.getContentType();
                    if (conType.toLowerCase().contains("appliaction")) {
                        flag = true;
                    }
                    if (conType.toLowerCase().contains("name")) {
                        flag = true;
                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttch((Part) part.getContent());
        }
        return flag;
    }

    /**
     * 保存附件
     */
    private void saveAttchMent(Part part) throws MessagingException, IOException {
        String filename;
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String dispostion = mpart.getDisposition();
                if ((dispostion != null) && (dispostion.equals(Part.ATTACHMENT) || dispostion.equals(Part.INLINE))) {
                    filename = mpart.getFileName();
                    if (filename.toLowerCase().contains("gb2312")) {
                        filename = MimeUtility.decodeText(filename);
                    }
                    saveFile(filename, mpart.getInputStream());
                } else if (mpart.isMimeType("multipart/*")) {
                    saveAttchMent(mpart);
                } else {
                    filename = mpart.getFileName();
                    if (filename != null && (filename.toLowerCase().contains("gb2312"))) {
                        filename = MimeUtility.decodeText(filename);
                    }
                    saveFile(filename, mpart.getInputStream());
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            saveAttchMent((Part) part.getContent());
        }
    }

    /**
     * 获得保存附件的地址
     */
    private String getSaveAttchPath() {
        return saveAttchPath;
    }

    /**
     * 设置保存附件地址
     */
    public void setSaveAttchPath(String saveAttchPath) {
        this.saveAttchPath = saveAttchPath;
    }

    /**
     * 设置日期格式
     */
    public void setDateformate(String dateFormate) {
        this.dateFormate = dateFormate;
    }

    /**
     * 保存文件内容
     */
    private void saveFile(String filename, InputStream inputStream) throws IOException {
        String osName = System.getProperty("os.name");
        String storeDir = getSaveAttchPath();
        String sepatror;
        if (osName == null) {
            osName = "";
        }
        if (osName.toLowerCase().contains("win")) {
            sepatror = "//";
            if (storeDir == null || "".equals(storeDir)) {
                storeDir = "C:\\temp";
            }
        } else {
            sepatror = "/";
            storeDir = "/temp";
        }

        File storeFile = new File(storeDir + sepatror + filename);
        System.out.println("store file's path:" + storeFile.toString());

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(storeFile));
             BufferedInputStream bis = new BufferedInputStream(inputStream)) {
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void receive(Part part, int i) throws MessagingException, IOException {
        System.out.println("------------------START-----------------------");
        System.out.println("Message" + i + " subject:" + getSubject());
        System.out.println("Message" + i + " from:" + getFrom());
        System.out.println("Message" + i + " isNew:" + isNew());
        boolean flag = isContainAttch(part);
        System.out.println("Message" + i + " isContainAttch:" + flag);
        System.out.println("Message" + i + " replySign:" + getReplySign());
        getMailContent(part);
        System.out.println("Message" + i + " content:" + getBodyText());
        setSaveAttchPath("c://temp//" + i);
        if (flag) {
            saveAttchMent(part);
        }
        System.out.println("------------------END-----------------------");
    }
}