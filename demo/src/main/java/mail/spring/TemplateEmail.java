package mail.spring;

import freemarker.template.Template;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/12/9
 * Time: 20:38
 */
@Slf4j
@Component
public class TemplateEmail {

    @Resource
    private JavaMailSender sender;
    private FreeMarkerConfigurer freeMarkerConfigurer = null;

    public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    public void setSender(JavaMailSender sender) {
        this.sender = sender;
    }

    /**
     * 生成html模板字符串
     *
     * @param root 存储动态数据的map
     */
    private String getMailText(Map<String, Object> root, String templateName) {
        String htmlText = "";
        try {
            //通过指定模板名获取FreeMarker模板实例
            Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
        } catch (Exception e) {
            log.error("getMailText fail", e);
        }
        return htmlText;
    }

    /**
     * send mail
     *
     * @param root    存储动态数据的map
     * @param toEmail mail address
     * @param subject mail title
     */
    public void sendTemplateMail(Map<String, Object> root, String toEmail, String subject, String templateName) {
        try {
            MimeMessage msg = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");//由于是html邮件，不是multipart类型
            helper.setFrom("wangjianloveblue@163.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            String htmlText = getMailText(root, templateName);//使用模板生成html邮件内容
            helper.setText(htmlText, true);
            sender.send(msg);
        } catch (MailException | MessagingException e) {
            log.error("send template mail fail.", e);
        }
    }
}
