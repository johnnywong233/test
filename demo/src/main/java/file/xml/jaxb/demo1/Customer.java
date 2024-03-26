package file.xml.jaxb.demo1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Author: Johnny
 * Date: 2017/1/4
 * Time: 20:48
 */
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Data
@NoArgsConstructor
public class Customer {
    @XmlAttribute
    private String name;

    private String gender;

    private String phoneNo;

    private Address address;

    private Set<Order> orders;

    public Customer(String name, String gender, String phoneNo, Address address) {
        this.name = name;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.address = address;
    }
}
