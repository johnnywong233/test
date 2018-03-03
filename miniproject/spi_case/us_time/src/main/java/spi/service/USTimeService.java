package spi.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Johnny on 2018/3/3.
 */
public class USTimeService extends ITimeService {
    public USTimeService() {
        super.setServiceName("US-Time-Service");
    }

    @Override
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return getServiceName() + ":" + sdf.format(new Date());
    }
}