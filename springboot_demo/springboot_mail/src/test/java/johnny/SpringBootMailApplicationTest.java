package johnny;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Author: Johnny
 * Date: 2017/4/19
 * Time: 11:26
 */
public class SpringBootMailApplicationTest {


    @Test
    public void contextLoads() {

    }

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void sendSimpleMail() throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("wangjianloveblue@163.com");
        message.setTo("wangjianloveblue@163.com");
        message.setSubject("title: simple mail");
        message.setText("test mail content");
        mailSender.send(message);
    }

}