package file.xml.jaxb.demo1;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Author: Johnny
 * Date: 2017/1/4
 * Time: 20:45
 */
@XmlType(propOrder = {"state", "province", "city", "street", "zip"})
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Address {

    @XmlAttribute
    private String state;

    @XmlElement
    private String province;

    @XmlElement
    private String city;

    @XmlElement
    private String street;

    @XmlElement
    private String zip;

    private String getState() {
        return state;
    }

    private void setState(String state) {
        this.state = state;
    }

    private String getProvince() {
        return province;
    }

    private void setProvince(String province) {
        this.province = province;
    }

    private String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    private String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public Address() {
        super();
    }

    public Address(String state, String province, String city, String street,
                   String zip) {
        super();
        this.state = state;
        this.province = province;
        this.city = city;
        this.street = street;
        this.zip = zip;
    }
}
