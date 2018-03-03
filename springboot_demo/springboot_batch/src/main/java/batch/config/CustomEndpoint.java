package batch.config;

import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnny on 2018/3/3.
 * 新增自定义的 endpoint，http://localhost:8088/monitor/myep
 */
@Component
public class CustomEndpoint implements Endpoint<List<String>> {
    public String getId() {
        return "ep";
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isSensitive() {
        return true;
    }

    public List<String> invoke() {
        List<String> messages = new ArrayList<>();
        messages.add("This is message 1");
        messages.add("This is message 2");
        return messages;
    }
}