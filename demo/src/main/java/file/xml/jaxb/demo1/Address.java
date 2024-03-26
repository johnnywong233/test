package file.xml.jaxb.demo1;

import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;

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
