package mail;

import org.testng.annotations.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Date;
import java.util.Properties;

/**
 * Author: Johnny
 * Date: 2016/10/31
 * Time: 22:42
 */
public class MailSender {

    //http://www.jb51.net/article/92431.htm
    @Test
    public void sender() {
        send("wangjianloveblue@163.com", "final test", "silly");
    }

    private static final String MAIL_SMTP_HOST = "smtp.163.com";
    private static final Integer MAIL_SMTP_PORT = 25;
    private static final Boolean MAIL_SMTP_AUTH = true;
    private static final String MAIL_SMTP_USER = "wangjianloveblue@163.com";
    private static final String MAIL_SMTP_PASSWORD = "w**5";

    private static final Properties props = new Properties();

    static {
        //http://bbs.csdn.net/topics/390680630
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        props.put("mail.smtp.socketFactory.fallback", "false");

        props.put("mail.smtp.host", MAIL_SMTP_HOST);
        props.put("mail.smtp.port", MAIL_SMTP_PORT);
        props.put("mail.smtp.auth", MAIL_SMTP_AUTH);
        props.put("mail.smtp.user", MAIL_SMTP_USER);
        props.put("mail.smtp.password", MAIL_SMTP_PASSWORD);
        props.put("mail.smtp.starttls.enable", true);
    }

    public static void send(String to, String title, String content) {
        try {
            Session session = Session.getInstance(props);//创建邮件会话
            MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象

            message.setFrom(new InternetAddress(MAIL_SMTP_USER));//设置发件人的地址
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//设置收件人,并设置其接收类型为TO
            //设置信件内容
            message.setText(content, "UTF-8");
            message.setSubject(title);//set title
            message.setContent(content, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
            message.setSentDate(new Date());
            message.saveChanges();

            //send
            Transport transport = session.getTransport("smtp");
            transport.connect(MAIL_SMTP_USER, MAIL_SMTP_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());//第二个参数是所有已设好的收件人地址
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
