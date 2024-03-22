package mail;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Author: Johnny
 * Date: 2016/10/17
 * Time: 23:44
 */
@Service
public class MailService {
    private final String MAIL_SENDER_MAIL = "wangjianloveblue@163.com";
    private final String MAIL_SENDER_PASS = "w**5";

    public static void main(String[] args) {
        MailService m = new MailService();
        List<String> recipients = new ArrayList<>();
        recipients.add("wangjianloveblue@163.com");

        //this is cc(carbon copy) function
        List<String> copyToRecipients = new ArrayList<>();
        copyToRecipients.add("wangjianloveblue@163.com");
        try {
            m.sendMail("title", "content", recipients, copyToRecipients, null);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * init Session
     */
    private Session getMailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.163.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MAIL_SENDER_MAIL, MAIL_SENDER_PASS);
            }
        });
    }

    /**
     * @param title                  mail title
     * @param content                mail content
     * @param recipients             收件人邮箱列表
     * @param copyToRecipients       抄送人邮箱列表
     * @param secretCopyToRecipients 密送人邮箱列表
     * @throws MessagingException           exception
     * @throws UnsupportedEncodingException exception
     */
    private boolean sendMail(String title, String content, Collection<String> recipients,
                             Collection<String> copyToRecipients, Collection<String> secretCopyToRecipients) throws MessagingException, UnsupportedEncodingException {
        List<InternetAddress> toAddresses = parseStringToAddress(recipients);
        List<InternetAddress> ccAddresses = parseStringToAddress(copyToRecipients);
        List<InternetAddress> bccAddresses = parseStringToAddress(secretCopyToRecipients);
        //init email content
        Message message = new MimeMessage(getMailSession());
        message.setFrom(new InternetAddress(MAIL_SENDER_MAIL, "johnny"));
        String subject = MimeUtility.encodeWord(title, "UTF-8", "Q");//set title encoding
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=utf-8");

        message.setRecipients(Message.RecipientType.TO, toAddresses.toArray(new InternetAddress[0]));
        message.setRecipients(Message.RecipientType.CC, ccAddresses.toArray(new InternetAddress[0]));
        message.setRecipients(Message.RecipientType.BCC, bccAddresses.toArray(new InternetAddress[0]));

        message.saveChanges();
        Transport.send(message);
        //不报异常表示邮件发送成功
        return true;
    }

    /**
     * 将字符串类型的邮箱地址转成InternetAddress类型的邮箱地址
     */
    private List<InternetAddress> parseStringToAddress(Collection<String> mailStrings) throws AddressException {
        if (CollectionUtils.isEmpty(mailStrings)) {
            return Collections.emptyList();
        }
        List<InternetAddress> addressList = new ArrayList<>();
        for (String mailString : mailStrings) {
            InternetAddress internetAddress = new InternetAddress(mailString);
            addressList.add(internetAddress);
        }
        return addressList;
    }
}