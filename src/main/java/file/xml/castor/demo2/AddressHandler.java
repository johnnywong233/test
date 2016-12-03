package file.xml.castor.demo2;

import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.mapping.ValidityException;

/**
 * Author: Johnny
 * Date: 2016/11/20
 * Time: 20:40
 */
public class AddressHandler implements FieldHandler {
    public Object getValue(Object object) throws IllegalStateException {
        Person root = (Person) object;
        if (root.getAddress() == null) {
            return null;
        }
        return root.getAddress().getStreet1() + "|" + root.getAddress().getStreet2();
    }

    public void setValue(Object object, Object value) throws IllegalStateException, IllegalArgumentException {
        Person root = (Person) object;
        Address address = new Address();
        String[] streets = ((String) value).split("\\|");
        address.setStreet1(streets[0]);
        address.setStreet2(streets[1]);

        root.setAddress(address);
    }

    public void resetValue(Object object) throws IllegalStateException, IllegalArgumentException {
    }

    public void checkValidity(Object object) throws ValidityException, IllegalStateException {
    }

    public Object newInstance(Object parent) throws IllegalStateException {
        return null;
    }
}
