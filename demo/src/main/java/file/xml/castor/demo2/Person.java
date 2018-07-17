package file.xml.castor.demo2;

/**
 * Author: Johnny
 * Date: 2016/11/20
 * Time: 20:38
 */
public class Person {
    private String name;
    private Address address;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + "," + address + "," + type;
    }
}
