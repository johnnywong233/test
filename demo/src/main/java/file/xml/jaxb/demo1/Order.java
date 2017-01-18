package file.xml.jaxb.demo1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getPurDate() {
        return purDate;
    }

    public void setPurDate(Date purDate) {
        this.purDate = purDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Order() {

    }

    Order(String shopName, String orderNumber, Date purDate,
          BigDecimal price, int amount) {
        this.shopName = shopName;
        this.orderNumber = orderNumber;
        this.purDate = purDate;
        this.price = price;
        this.amount = amount;
    }

}
