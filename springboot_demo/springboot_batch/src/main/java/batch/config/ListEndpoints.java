package batch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Johnny on 2018/3/3.
 * 列举所有端点 http://localhost:8088/monitor/All_ep
 * Id must only contains letters, numbers and '_'
 */
@Component
public class ListEndpoints extends AbstractEndpoint<List<Endpoint>> {
    private List<Endpoint> endpoints;

    @Autowired
    public ListEndpoints(List<Endpoint> endpoints) {
        super("All_ep");
        this.endpoints = endpoints;
    }

    @Override
    public List<Endpoint> invoke() {
        return this.endpoints;
    }
}
