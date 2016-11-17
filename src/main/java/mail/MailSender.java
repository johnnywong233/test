package mail;

import org.testng.annotations.Test;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.security.Security;
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
    public void sender() throws Exception {
        send("1224017485@qq.com", "final test", "silly");

    }

    private static final String MAIL_SMTP_HOST = "smtp.exmail.qq.com";
    private static final Integer MAIL_SMTP_PORT = 465;//587;//465
    private static final Boolean MAIL_SMTP_AUTH = true;
    private static final String MAIL_SMTP_USER = "1224017485@qq.com";
    private static final String MAIL_SMTP_PASSWORD = "wjlb0174851224";

    private static Properties props = new Properties();

    static {
    	//http://bbs.csdn.net/topics/390680630
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");

        props.put("exmail.smtp.host", MAIL_SMTP_HOST);
//        props.put("mail.smtp.port", MAIL_SMTP_PORT);//add this, 长时间得不到任何响应
        props.put("exmail.smtp.auth", MAIL_SMTP_AUTH);
        props.put("exmail.smtp.user", MAIL_SMTP_USER);
        props.put("exmail.smtp.password", MAIL_SMTP_PASSWORD);
        props.put("exmail.smtp.starttls.enable", true);
        
//        props.put("mail.smtp.host", MAIL_SMTP_HOST);
////      props.put("mail.smtp.port", MAIL_SMTP_PORT);//add this, 长时间得不到任何响应
//      props.put("mail.smtp.auth", MAIL_SMTP_AUTH);
//      props.put("mail.smtp.user", MAIL_SMTP_USER);
//      props.put("mail.smtp.password", MAIL_SMTP_PASSWORD);
//      props.put("mail.smtp.starttls.enable", true);
        
    }


    /**
     * 发送邮件
     */
    public static void send(String to, String title, String content) {
        try {
            Session session = Session.getInstance(props);//创建邮件会话
            MimeMessage message = new MimeMessage(session);//由邮件会话新建一个消息对象

            message.setFrom(new InternetAddress(MAIL_SMTP_PASSWORD));//设置发件人的地址
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//设置收件人,并设置其接收类型为TO
            //设置信件内容
            //message.setText(mailContent); //发送 纯文本 邮件 TODO
            message.setSubject(title);//设置标题
            message.setContent(content, "text/html;charset=gbk"); //发送HTML邮件，内容样式比较丰富
            message.setSentDate(new Date());//设置发信时间
            message.saveChanges();//存储邮件信息

            //发送邮件
            Transport transport = session.getTransport("smtp");
            transport.connect(MAIL_SMTP_USER, MAIL_SMTP_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());//发送邮件,其中第二个参数是所有已设好的收件人地址
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
