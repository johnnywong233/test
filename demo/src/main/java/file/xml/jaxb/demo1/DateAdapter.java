package file.xml.jaxb.demo1;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/1/4
 * Time: 21:01
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    private String pattern = "yyyy-MM-dd HH:mm:ss";
    private SimpleDateFormat fmt = new SimpleDateFormat(pattern);

    @Override
    public Date unmarshal(String dateStr) throws Exception {
        return fmt.parse(dateStr);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return fmt.format(date);
    }
}
