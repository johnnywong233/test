package spi.test;

import spi.service.ITimeService;

import java.util.ServiceLoader;

/**
 * Created by Johnny on 2018/3/3.
 */
public class SPIClient {
    public static void main(String[] args) {
        ServiceLoader<ITimeService> loader = ServiceLoader.load(ITimeService.class);
        for (ITimeService iTimeService : loader) {
            System.out.println(iTimeService.getCurrentTime());
        }
    }
}