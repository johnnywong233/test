package demo.el;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@Service
public class DemoService1 {
    //注入普通字符串
    @Value("王健")
    private String author;
}
