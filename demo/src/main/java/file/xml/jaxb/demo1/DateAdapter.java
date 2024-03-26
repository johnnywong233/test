package file.xml.jaxb.demo1;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/1/4
 * Time: 21:01
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    private final String pattern = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat fmt = new SimpleDateFormat(pattern);

    @Override
    public Date unmarshal(String dateStr) throws Exception {
        return fmt.parse(dateStr);
    }

    @Override
    public String marshal(Date date) {
        return fmt.format(date);
    }
}
