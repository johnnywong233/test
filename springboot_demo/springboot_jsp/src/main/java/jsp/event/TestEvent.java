package jsp.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/4/27
 * Time: 18:53
 */
@Component
public class TestEvent {
    //http://wiselyman.iteye.com/blog/2216498
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("jsp.event");
        TestEvent main = context.getBean(TestEvent.class);
        main.publish(context);
        context.close();
    }

    private void publish(AnnotationConfigApplicationContext context) {
        context.publishEvent(new DemoEvent(this, "22"));
    }
}
