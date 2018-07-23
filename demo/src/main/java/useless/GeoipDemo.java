package useless;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * Author: Johnny
 * Date: 2017/2/19
 * Time: 18:58
 * convert ip address to infos down to latitude and longitude
 * http://geolite.maxmind.com/download/geoip/database/GeoLiteCity.dat.gz
 */
public class GeoipDemo {
    public static void main(String[] args) {
        try {
            LookupService cl = new LookupService(new ClassPathResource("GeoLiteCity.dat").getFile().toString(), LookupService.GEOIP_MEMORY_CACHE);
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
