package johnny;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2017/3/11
 * Time: 15:46
 */
@Service
class MailUtil {
    private static final Logger LOG = LoggerFactory.getLogger(MailUtil.class);
    private static String[] sendTo = {"wangjianloveblue@163.com"};

    @Async
    void sendSimpleMail(JavaMailSender javaMailSender, SimpleMailMessage message) {
        if (javaMailSender != null) {
            message.setFrom("wangjianloveblue@163.com");
            javaMailSender.send(message);
            LOG.info("send success");
        } else {
            LOG.info("javaMailSender is null");
        }
    }

    void sendAttachmentsMail(JavaMailSender javaMailSender) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("wangjianloveblue@163.com");
        helper.setTo(sendTo);
        helper.setSubject("title: with attachment");
        helper.setText("mail with attachment ");
        FileSystemResource file = new FileSystemResource(new File("README.md"));
        FileSystemResource file1 = new FileSystemResource(new File("README.md"));
        helper.addAttachment("attachment 1", file);
        helper.addAttachment("attachment 2", file1);
        javaMailSender.send(mimeMessage);
    }

    /**
     * embed static resources, directly show attachment in mail
     */
    void sendInlineMail(JavaMailSender javaMailSender) throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("wangjianloveblue@163.com");
        helper.setTo(sendTo);
        helper.setSubject("title: embed static resources");
        helper.setText("<html><body><img src=\"cid:doubi\" ></body></html>", true);
        FileSystemResource file = new FileSystemResource(new File("D:\\S60728-200903.jpg"));
        // addInline函数中资源名称doubi需要与正文中cid:doubi对应起来
        helper.addInline("static resources", file);
        javaMailSender.send(mimeMessage);
    }

    @Async
    void sendTemplateMail(JavaMailSender javaMailSender, String sendTo, String username) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Map<String, Object> model = new HashMap<>();
        model.put("user", username);
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom("wangjianloveblue@163.com");
            helper.setTo(sendTo);
            helper.setSubject("title:template mail");
            String html = HtmlProcessor.getHtml("mail.html", new Context(Locale.CHINA, model));
            helper.setText(html, true);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        javaMailSender.send(mimeMessage);
    }
}
