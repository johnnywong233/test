package file.xml.jaxb.demo1;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: Johnny
 * Date: 2017/1/4
 * Time: 20:36
 */
public class ShopTest {
    //http://www.voidcn.com/blog/huxu981598436/article/p-6005921.html
    public static void main(String[] args) throws JAXBException, IOException {
        Set<Order> orders = new HashSet<>();

        Address address1 = new Address("China", "ShangHai", "ShangHai", "Huang", "200000");
        Customer customer1 = new Customer("Jim", "male", "13699990000", address1);
        Order order1 = new Order("Mart", "LH59900", new Date(), new BigDecimal(60), 1);
        order1.setCustomer(customer1);

        Address address2 = new Address("China", "JiangSu", "NanJing", "ZhongYangLu", "210000");
        Customer customer2 = new Customer("David", "male", "13699991000", address2);
        Order order2 = new Order("Mart", "LH59800", new Date(), new BigDecimal(80), 1);
        order2.setCustomer(customer2);

        orders.add(order1);
        orders.add(order2);

        Address address3 = new Address("China", "ZheJiang", "HangZhou", "XiHuRoad", "310000");
        Shop shop = new Shop("CHMart", "100000", "EveryThing", address3);
        shop.setOrders(orders);


        FileWriter writer;
        JAXBContext context = JAXBContext.newInstance(Shop.class);
        try {
            Marshaller marshal = context.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshal.marshal(shop, System.out);

            writer = new FileWriter("shop.xml");
            marshal.marshal(shop, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Unmarshaller unmarshal = context.createUnmarshaller();
        FileReader reader = new FileReader("shop.xml");
        Shop shop1 = (Shop) unmarshal.unmarshal(reader);

        Set<Order> orders1 = shop1.getOrders();
        for (Order order : orders1) {
            System.out.println("***************************");
            System.out.println(order.getOrderNumber());
            System.out.println(order.getCustomer().getName());
            System.out.println("***************************");
        }
    }
}
