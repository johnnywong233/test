package johnny;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/3/11
 * Time: 15:50
 */
@RestController
public class MailController {
    @Resource
    @Qualifier("javaMailSender")
    private JavaMailSender javaMailSender;
    @Resource
    private MailUtil mailUtil;

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public void sendSimpleMail(User user) throws Exception {
        user.setEmail("wangjianloveblue@163.com");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("user login prompt");
        message.setText("hi, johnny, this is a mail sent through java code to you");
        // all ok
        mailUtil.sendSimpleMail(javaMailSender, message);
        mailUtil.sendAttachmentsMail(javaMailSender);
        mailUtil.sendInlineMail(javaMailSender);
        mailUtil.sendTemplateMail(javaMailSender, user.getEmail(), user.getUsername());
    }
}
