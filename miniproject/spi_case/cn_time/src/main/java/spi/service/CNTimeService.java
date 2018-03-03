package spi.service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Johnny on 2018/3/3.
 */
public class CNTimeService extends ITimeService {
    public CNTimeService() {
        super.setServiceName("CN-Time-Service");
    }

    @Override
    public String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getServiceName() + ":" + sdf.format(new Date());
    }
}