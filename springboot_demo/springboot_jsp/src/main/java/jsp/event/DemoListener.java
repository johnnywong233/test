package jsp.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Author: Johnny
 * Date: 2017/4/27
 * Time: 13:17
 */
@Component
public class DemoListener {
    @EventListener
    public void handleDemoEvent(DemoEvent event) {
        System.out.println("我监听到publisher发布的message为: " + event.getMsg());
    }
}
