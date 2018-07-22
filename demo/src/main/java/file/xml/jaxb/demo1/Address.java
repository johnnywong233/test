package file.xml.jaxb.demo1;

import lombok.AllArgsConstructor;
import lombok.Data;

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
@Data
@AllArgsConstructor
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

    public Address() {
        super();
    }
}
