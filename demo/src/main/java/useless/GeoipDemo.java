package useless;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import java.io.IOException;

/**
 * Author: Johnny
 * Date: 2017/2/19
 * Time: 18:58
 * convert ip address to infos down to latitude and longitude
 */
public class GeoipDemo {
    public static void main(String[] args) {
        try {
            LookupService cl = new LookupService("C:\\work\\test_git\\test\\demo\\src\\main\\resources\\GeoLiteCity-2013-01-18.dat", LookupService.GEOIP_MEMORY_CACHE);
            Location l2 = cl.getLocation("144.0.9.29");
            System.out.println(
                    "countryCode: " + l2.countryCode + "\n" +
                            "countryName: " + l2.countryName + "\n" +
                            "region: " + l2.region + "\n" +
                            "city: " + l2.city + "\n" +
                            "latitude: " + l2.latitude + "\n" +
                            "longitude: " + l2.longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
