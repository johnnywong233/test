package mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Author: Johnny
 * Date: 2016/10/16
 * Time: 2:08
 * a mail corresponding to a SMTP, such as smtp.qq.com
 */
public class MailUtil {
    //TODO
    //http://blog.csdn.net/xiao__gui/article/details/8108214
    public static void main(String[] args) throws MessagingException {
        sendMail1();
        try {
//            MailUtil.sendMailByJavaMail("smtp.qq.com", 25, "1224017485@qq.com","wyohaethwctghjig","1224017485@qq.com","silly test","shit happen");
//            MailUtil.sendMailByCommonsEmail ("smtp.163.com", 25, "wangjianloveblue@qq.com","wjlb3874596120","1224017485@qq.com","life sucks","fuck day");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMailByJavaMail(String smtpHost, int smtpPort, String username,
                                           String password, String to, String title, String content)
            throws MessagingException {
        //create session
        Session session = Session.getDefaultInstance(new Properties());
        //create message(mail)
        Message mailMessage = new MimeMessage(session);
        //set sender
        mailMessage.setFrom(new InternetAddress(username));
        //set title
        mailMessage.setSubject(title);
        //set content
        mailMessage.setText(content);
        Transport trans = null;
        try {
            trans = session.getTransport("smtp");
            trans.connect(smtpHost, smtpPort, username, password);
            trans.sendMessage(mailMessage, InternetAddress.parse(to));
        } catch (MessagingException e) {
            throw e;
        } finally {
            if (trans !=null) {
                try {
                    trans.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * commons-email is based on JavaMail and mail.jar, more usable。
     */
    private static void sendMailByCommonsEmail(String smtpHost, int smtpPort, String username,
                                               String password, String to, String title, String content)
            throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName(smtpHost);
        email.setSmtpPort(smtpPort);
        email.setAuthentication(username, password);
        email.addTo(to);
        email.setFrom(username);
        email.setSubject(title);
        email.setMsg(content);
        email.send();
    }

    private static void sendMail1() throws MessagingException {
        Properties props = new Properties();
        // 发送邮件的服务器的IP和端口
        String MAIL_SMTP_HOST = "1224017485@qq.com";
        Object MAIL_SMTP_PORT = 25;
        String MAIL_SENDER_MAIL = "1224017485@qq.com";
        String MAIL_SENDER_PASS = "wjlb0174851224";//wjlb3874596120

        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.port", MAIL_SMTP_PORT);
        // 是否需要身份验证
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // 登陆邮件发送服务器的用户名和密码
                return new PasswordAuthentication(MAIL_SENDER_MAIL, MAIL_SENDER_PASS);
            }
        });
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = session.getTransport();
        ts.connect("smtp.qq.com", "1224017485@qq.com", "wjlb0174851224");

        Message message = new Message() {
            @Override
            public Address[] getFrom() throws MessagingException {
                return new Address[0];
            }

            @Override
            public void setFrom() throws MessagingException {

            }

            @Override
            public void setFrom(Address address) throws MessagingException {

            }

            @Override
            public void addFrom(Address[] addresses) throws MessagingException {

            }

            @Override
            public Address[] getRecipients(RecipientType recipientType) throws MessagingException {
                return new Address[0];
            }

            @Override
            public void setRecipients(RecipientType recipientType, Address[] addresses) throws MessagingException {

            }

            @Override
            public void addRecipients(RecipientType recipientType, Address[] addresses) throws MessagingException {

            }

            @Override
            public String getSubject() throws MessagingException {
                return null;
            }

            @Override
            public void setSubject(String s) throws MessagingException {

            }

            @Override
            public Date getSentDate() throws MessagingException {
                return null;
            }

            @Override
            public void setSentDate(Date date) throws MessagingException {

            }

            @Override
            public Date getReceivedDate() throws MessagingException {
                return null;
            }

            @Override
            public Flags getFlags() throws MessagingException {
                return null;
            }

            @Override
            public void setFlags(Flags flags, boolean b) throws MessagingException {

            }

            @Override
            public Message reply(boolean b) throws MessagingException {
                return null;
            }

            @Override
            public void saveChanges() throws MessagingException {

            }

            @Override
            public int getSize() throws MessagingException {
                return 0;
            }

            @Override
            public int getLineCount() throws MessagingException {
                return 0;
            }

            @Override
            public String getContentType() throws MessagingException {
                return null;
            }

            @Override
            public boolean isMimeType(String s) throws MessagingException {
                return false;
            }

            @Override
            public String getDisposition() throws MessagingException {
                return null;
            }

            @Override
            public void setDisposition(String s) throws MessagingException {

            }

            @Override
            public String getDescription() throws MessagingException {
                return null;
            }

            @Override
            public void setDescription(String s) throws MessagingException {

            }

            @Override
            public String getFileName() throws MessagingException {
                return null;
            }

            @Override
            public void setFileName(String s) throws MessagingException {

            }

            @Override
            public InputStream getInputStream() throws IOException, MessagingException {
                return null;
            }

            @Override
            public DataHandler getDataHandler() throws MessagingException {
                return null;
            }

            @Override
            public Object getContent() throws IOException, MessagingException {
                return null;
            }

            @Override
            public void setDataHandler(DataHandler dataHandler) throws MessagingException {

            }

            @Override
            public void setContent(Object o, String s) throws MessagingException {

            }

            @Override
            public void setText(String s) throws MessagingException {

            }

            @Override
            public void setContent(Multipart multipart) throws MessagingException {

            }

            @Override
            public void writeTo(OutputStream outputStream) throws IOException, MessagingException {

            }

            @Override
            public String[] getHeader(String s) throws MessagingException {
                return new String[0];
            }

            @Override
            public void setHeader(String s, String s1) throws MessagingException {

            }

            @Override
            public void addHeader(String s, String s1) throws MessagingException {

            }

            @Override
            public void removeHeader(String s) throws MessagingException {

            }

            @Override
            public Enumeration getAllHeaders() throws MessagingException {
                return null;
            }

            @Override
            public Enumeration getMatchingHeaders(String[] strings) throws MessagingException {
                return null;
            }

            @Override
            public Enumeration getNonMatchingHeaders(String[] strings) throws MessagingException {
                return null;
            }
        };

        ts.sendMessage(message, message.getAllRecipients());
        ts.close();




//        //使用JavaMail发送邮件的5个步骤
//        //1、创建session
//        Session session = Session.getInstance(prop);
//        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
//        ts.connect("smtp.sohu.com", "gacl", "邮箱密码");
//        //4、创建邮件
//        Message message = createSimpleMail(session);
//        //5、发送邮件
//        ts.sendMessage(message, message.getAllRecipients());
//        //关闭连接
//        ts.close();

    }

}