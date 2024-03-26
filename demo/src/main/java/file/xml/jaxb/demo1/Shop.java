package file.xml.jaxb.demo1;

import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Author: Johnny
 * Date: 2017/1/4
 * Time: 20:54
 */
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "shop", propOrder = {"name", "number", "describer", "address", "orders"})
@XmlRootElement(name = "CHMart")
@Data
@NoArgsConstructor
public class Shop {
    @XmlAttribute
    private String name;

    // @XmlElement
    private String number;

    @XmlElement
    private String describer;

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    private Set<Order> orders;

    @XmlElement
    private Address address;

    Shop(String name, String number, String describer, Address address) {
        this.name = name;
        this.number = number;
        this.describer = describer;
        this.address = address;
    }
}
