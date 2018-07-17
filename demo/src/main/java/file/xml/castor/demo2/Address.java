package file.xml.castor.demo2;

/**
 * Author: Johnny
 * Date: 2016/11/20
 * Time: 20:39
 */
public class Address {
    private String street1;
    private String street2;


    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    @Override
    public String toString() {
        return street1 + "|" + street2;
    }
}
