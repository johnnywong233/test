package mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Author: Johnny
 * Date: 2016/10/16
 * Time: 2:08
 * three ways to send a mail
 * a mail corresponding to a SMTP, such as smtp.163.com
 */
public class MailUtil {
    //http://blog.csdn.net/xiao__gui/article/details/8108214
    public static void main(String[] args) throws MessagingException {
        try {
            //TODO
            sendMail();
            MailUtil.sendMailByJavaMail("smtp.163.com", 25, "wangjianloveblue@163.com", "w**5", "1224017485@qq.com", "silly test", "shit happen");
            MailUtil.sendMailByCommonsEmail("smtp.163.com", 25, "wangjianloveblue@163.com", "w**5", "1224017485@qq.com", "life sucks", "fuck day");
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
            e.printStackTrace();
            throw e;
        } finally {
            if (trans != null) {
                try {
                    trans.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
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

    private static void sendMail() throws MessagingException {
        Properties props = new Properties();
        // 发送邮件的服务器的IP和端口
        String MAIL_SMTP_HOST = "smtp.163.com";
        Object MAIL_SMTP_PORT = 25;
        String MAIL_SENDER_MAIL = "wangjianloveblue@163.com";
        String MAIL_RECEIVER_MAIL = "1224017485@qq.com";
        String MAIL_SENDER_PASS = "wjlb38745";

        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.port", MAIL_SMTP_PORT);
        //是否需要身份验证
//        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        //ERROR: 553 Mail from must equal authorized user
        Session session = Session.getInstance(props);/*, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // 登陆邮件发送服务器的用户名和密码
                return new PasswordAuthentication(MAIL_SENDER_MAIL, MAIL_SENDER_PASS);
            }
        });*/
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //通过session得到transport对象
        Transport ts = session.getTransport();
        ts.connect("smtp.163.com", "wangjianloveblue@163.com", "wjlb38745");

        MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象
        session.getTransport("smtp");
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(MAIL_SENDER_MAIL));

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