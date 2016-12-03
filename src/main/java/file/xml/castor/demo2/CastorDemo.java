package file.xml.castor.demo2;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.net.URL;

/**
 * Author: Johnny
 * Date: 2016/11/20
 * Time: 20:46
 */
public class CastorDemo {
    //http://unmi.cc/castor-xml-to-javabean-2/
    public static void main(String[] args) throws Exception {
        Mapping mapping = new Mapping();
        URL urlMapping = ClassLoader.getSystemResource("mapping.xml");
        mapping.loadMapping(urlMapping);
        Unmarshaller unmarshaller = new Unmarshaller(PersonList.class);
        unmarshaller.setMapping(mapping);

        InputStream xmlInputStream = ClassLoader.getSystemResourceAsStream("persons.xml");
        InputSource inputSource = new InputSource(xmlInputStream);
        PersonList personList = (PersonList) unmarshaller.unmarshal(inputSource);
        System.out.println(personList.getPersons());
    }
}
