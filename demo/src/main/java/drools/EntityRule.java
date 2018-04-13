package drools;

import lombok.Data;

import java.util.UUID;

/**
 * Created by Johnny on 2018/4/6.
 */
@Data
public class EntityRule {
    private String username;

    /**
     * Whether for new account.
     */
    private boolean account;

    /**
     * The number of add.
     */
    private int addTime;

    /**
     * The sum of the current account.
     */
    private double currentMoney;

    /**
     * The total amount added.
     */
    private double totalAddMoney;

    /**
     * Record the serial number of the operation.
     */
    public void getSerialNumber(String username, double currentMoney) {
        System.out.println("Account：" + username + " Balance：￥" + currentMoney);
        System.out.println("Serial Number：" + UUID.randomUUID().toString());
    }
}
