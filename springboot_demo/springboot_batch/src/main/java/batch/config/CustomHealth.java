package batch.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by Johnny on 2018/3/3.
 * 自定义已有端点, 自定义期望的监控信息
 */
@Component
public class CustomHealth implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().build();
    }
}