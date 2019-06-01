package johnny;

import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/4/19
 * Time: 11:26
 */
public class SpringBootMailApplicationTest {


    @Test
    public void contextLoads() {

    }

    @Resource
    private JavaMailSender mailSender;

    @Test
    // TODO
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wangjianloveblue@163.com");
        message.setTo("wangjianloveblue@163.com");
        message.setSubject("title: simple mail");
        message.setText("test mail content");
        mailSender.send(message);
    }

}