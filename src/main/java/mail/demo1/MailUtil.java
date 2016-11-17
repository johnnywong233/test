package mail.demo1;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.jfree.util.Log;
import org.testng.annotations.Test;

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
        mail.setHost("smtp.exmail.qq.com");
        
        // 发件人邮件地址
        mail.setSender("1224017485@qq.com");
        // 发件人名称
        mail.setName("Java.小学生");
        // 登录账号,一般都是和邮箱名一样吧
        mail.setUsername("1224017485@qq.com");
        // 发件人邮箱的登录密码
        mail.setPassword("wjlb0174851224");
        // 接收人
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
            email.setCharset(Mail.ENCODEING);
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
     *
     * @param email
     * @param mail
     * @throws EmailException
     */
    private void setTo(HtmlEmail email, Mail mail) throws EmailException {
        // 收件人不为空
        if (StringUtils.isNotEmpty(mail.getReceiver())) {
            // 收件人名称不为空
            if (StringUtils.isNotEmpty(mail.getReceiverName())) {
                email.addTo(mail.getReceiver(), mail.getReceiverName());
            } else {
                email.addTo(mail.getReceiver());
            }
        }
        // 收件人 Map 不为空
        if (mail.getTo() != null) {
            for (Map.Entry<String, String> entry : mail.getTo().entrySet()) {
                // 收件人名称不为空
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
     *
     * @param email
     * @param mail
     * @throws EmailException
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
     *
     * @param email
     * @param mail
     * @throws EmailException
     */
    private void setBcc(HtmlEmail email, Mail mail) throws EmailException {
        // 密送人 Map 不为空
        if (mail.getBcc() != null) {
            for (Map.Entry<String, String> entry : mail.getBcc().entrySet()) {
                // 密送人名称不为空
                if (StringUtils.isNotEmpty(entry.getValue())) {
                    email.addBcc(entry.getKey(), entry.getValue());
                } else {
                    email.addBcc(entry.getKey());
                }
            }
        }
    }
}
