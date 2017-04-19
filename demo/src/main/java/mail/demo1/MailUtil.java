package mail.demo1;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jfree.util.Log;
import org.testng.annotations.Test;

import java.io.Serializable;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/10/31
 * Time: 22:16
 * sending mail through HtmlEmail
 * see http://www.jb51.net/article/85839.htm
 */
public class MailUtil {

    //mailto: 通过该协议可以创建一个指向电子邮件地址的超级链接，通过该链接可以在Internet中发送电子邮件
    @Test
    public void sendMail() {
        Mail mail = new Mail();
        // 设置邮件服务器
        mail.setHost("smtp.163.com");

        // 发件人邮件地址
        mail.setSender("wangjianloveblue@163.com");
        // 发件人名称
        mail.setName("Java.小学生");
        mail.setUsername("wangjianloveblue@163.com");
        mail.setPassword("w**5");
        mail.setReceiver("1224017485@qq.com");
        mail.setReceiverName("我要女票和加薪");
        mail.setSubject("测试");
        String html = "<!DOCTYPE html>";
        html += "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
        html += "<title>Insert title here</title>";
        html += "</head><body>";
        html += "<div style=\"width:600px;height:400px;margin:50px auto;\">";
        html += "<h1>我来看看邮件是否发送成功呢</h1><br/><br/>";
        html += "<p>下面是通过该协议可以创建一个指向电子邮件地址的超级链接，通过该链接可以在Internet中发送电子邮件</p><br/>";
        html += "<a href=\"mailto:huntereagle@foxmail.com?subject=test&cc=huntertochen@163.com&body=use mailto sample\">send mail</a>";
        html += "</div>";
        html += "</body></html>";
        mail.setMessage(html);
        new MailUtil().send(mail);
    }

    public Boolean send(Mail mail) {
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            email.setHostName(mail.getHost());
            // 字符编码集的设置
            email.setCharset(Mail.ENCODING);
            // 发送人的邮箱
            email.setFrom(mail.getSender(), mail.getName());
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            email.setAuthentication(mail.getUsername(), mail.getPassword());

            // 设置收件人信息
            setTo(email, mail);
            // 设置抄送人信息
            setCc(email, mail);
            // 设置密送人信息
            setBcc(email, mail);
            // 要发送的邮件主题
            email.setSubject(mail.getSubject());
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setHtmlMsg(mail.getMessage());
            // 发送
            email.send();
            if (Log.isDebugEnabled()) {
                Log.info(mail.getSender() + " 发送邮件到 " + mail.getReceiver());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.info(mail.getSender() + " 发送邮件到 " + mail.getReceiver() + " 失败");
            return false;
        }
    }

    /**
     * 设置收件人信息
     */
    private void setTo(HtmlEmail email, Mail mail) throws EmailException {
        if (StringUtils.isNotEmpty(mail.getReceiver())) {
            if (StringUtils.isNotEmpty(mail.getReceiverName())) {
                email.addTo(mail.getReceiver(), mail.getReceiverName());
            } else {
                email.addTo(mail.getReceiver());
            }
        }
        if (mail.getTo() != null) {
            for (Map.Entry<String, String> entry : mail.getTo().entrySet()) {
                if (StringUtils.isNotEmpty(entry.getValue())) {
                    email.addTo(entry.getKey(), entry.getValue());
                } else {
                    email.addTo(entry.getKey());
                }
            }
        }
    }

    /**
     * 设置抄送人信息
     */
    private void setCc(HtmlEmail email, Mail mail) throws EmailException {
        // 抄送人 Map 不为空
        if (mail.getCc() != null) {
            for (Map.Entry<String, String> entry : mail.getCc().entrySet()) {
                // 抄送人名称不为空
                if (StringUtils.isNotEmpty(entry.getValue())) {
                    email.addCc(entry.getKey(), entry.getValue());
                } else {
                    email.addCc(entry.getKey());
                }
            }
        }
    }

    /**
     * 设置密送人信息
     */
    private void setBcc(HtmlEmail email, Mail mail) throws EmailException {
        if (mail.getBcc() != null) {
            for (Map.Entry<String, String> entry : mail.getBcc().entrySet()) {
                if (StringUtils.isNotEmpty(entry.getValue())) {
                    email.addBcc(entry.getKey(), entry.getValue());
                } else {
                    email.addBcc(entry.getKey());
                }
            }
        }
    }
}

class Mail implements Serializable {
    private static final long serialVersionUID = -6390720891150157552L;
    static final String ENCODING = "UTF-8";

    // 服务器地址
    private String host;
    // 发件人的邮箱
    private String sender;
    // 发件人昵称
    private String name;
    private String username;
    private String password;
    private String receiver;
    private String receiverName;
    // 收件人的邮箱(key)和名称(value)
    private Map<String, String> to;
    // 抄送人的邮箱(key)和名称(value)
    private Map<String, String> cc;
    // 秘密抄送人的邮箱(key)和名称(value)
    private Map<String, String> bcc;
    private String subject;
    // 信息(支持HTML)
    private String message;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    String getSender() {
        return sender;
    }

    void setSender(String sender) {
        this.sender = sender;
    }

    String getReceiver() {
        return receiver;
    }

    void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    String getSubject() {
        return subject;
    }

    void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String getReceiverName() {
        return receiverName;
    }

    void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public Map<String, String> getTo() {
        return to;
    }

    public void setTo(Map<String, String> to) {
        this.to = to;
    }

    public Map<String, String> getCc() {
        return cc;
    }

    public void setCc(Map<String, String> cc) {
        this.cc = cc;
    }

    Map<String, String> getBcc() {
        return bcc;
    }

    public void setBcc(Map<String, String> bcc) {
        this.bcc = bcc;
    }
}
