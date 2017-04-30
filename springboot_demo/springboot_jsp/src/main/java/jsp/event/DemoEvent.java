package jsp.event;

import org.springframework.context.ApplicationEvent;

/**
 * Author: Johnny
 * Date: 2017/4/27
 * Time: 13:17
 */
public class DemoEvent extends ApplicationEvent {
	private static final long serialVersionUID = -8050245124927369971L;
	private String msg;

    DemoEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
