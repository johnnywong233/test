package file.xml.jaxb.demo1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: Johnny
 * Date: 2017/1/4
 * Time: 20:52
 */
@XmlType(name = "order", propOrder = {"shopName", "orderNumber", "price", "amount", "purDate", "customer"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Data
@NoArgsConstructor
public class Order {
    //  @XmlElement　　
    private String shopName;

    @XmlAttribute
    private String orderNumber;

    //  @XmlElement
    @XmlJavaTypeAdapter(value = DateAdapter.class)
    private Date purDate;

    //  @XmlElement
    private BigDecimal price;

    //  @XmlElement
    private int amount;

    //  @XmlElement
    private Customer customer;

    Order(String shopName, String orderNumber, Date purDate,
          BigDecimal price, int amount) {
        this.shopName = shopName;
        this.orderNumber = orderNumber;
        this.purDate = purDate;
        this.price = price;
        this.amount = amount;
    }

}
