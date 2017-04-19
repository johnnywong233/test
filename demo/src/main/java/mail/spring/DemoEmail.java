package mail.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Johnny
 * Date: 2016/12/9
 * Time: 20:50
 */
@Component("demoEmail")
public class DemoEmail {
    @Autowired
    private TemplateEmail templateEmail;

    @Resource(name = "templateEmail")
    public void setTemplateEmail(TemplateEmail templateEmail) {
        this.templateEmail = templateEmail;
    }

    //http://blog.csdn.net/ajun_studio/article/details/7347644
    @Test
    public void send() {
        Map<String, Object> root = new HashMap<>();
        root.put("username", "johnny");
        //TODO: NPE
        templateEmail.sendTemplateMail(root, "wangjianloveblue@163.com", "topic", "demo.ftl");
    }
}
