package jsp.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Author: Johnny
 * Date: 2017/4/27
 * Time: 13:17
 */
@Getter
public class DemoEvent extends ApplicationEvent {
	private final String msg;

    DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }
}
