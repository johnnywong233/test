package johnny;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Johnny
 * Date: 2017/3/11
 * Time: 15:50
 */
@RestController
public class MailController {
    @Autowired
    @Qualifier("javaMailSender")
    JavaMailSender javaMailSender;
    @Autowired
    MailUtil mailUtil;

    @RequestMapping(value = "/mail", method = RequestMethod.POST)
    public void sendSimpleMail(User user) throws Exception {
        user.setEmail("wangjianloveblue@163.com");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("user login prompt");
        message.setText("hi, johnny, this is a mail sent through java code to you");
        mailUtil.sendSimpleMail(javaMailSender, message);//OK
        mailUtil.sendAttachmentsMail(javaMailSender);//OK
        mailUtil.sendInlineMail(javaMailSender);//OK
        mailUtil.sendTemplateMail(javaMailSender, user.getEmail(), user.getUsername());//OK
    }
}
