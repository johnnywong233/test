package saas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:09
 */
@Data
@Entity
@Table(name = "inventory_vw")
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;
}
