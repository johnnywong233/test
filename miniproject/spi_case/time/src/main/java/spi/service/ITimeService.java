package spi.service;

import lombok.Data;

/**
 * Created by Johnny on 2018/3/3.
 */
@Data
public abstract class ITimeService {
    private String serviceName;

    public abstract String getCurrentTime();
}
