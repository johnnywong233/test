package file.xml.castor.demo1;

import java.io.InputStream;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;

/**
 * Author: Johnny
 * Date: 2016/11/20
 * Time: 11:36
 */
public class CastorDemo {
    public static void main(String[] args) throws MarshalException, ValidationException {
        InputStream xmlInputStream = ClassLoader.getSystemResourceAsStream("person.xml");
        Unmarshaller unmarshaller = new Unmarshaller(Person.class);
        InputSource inputSource = new InputSource(xmlInputStream);
        Person person = (Person)unmarshaller.unmarshal(inputSource);
        System.out.println(person.getBirthDay());
    }
}

