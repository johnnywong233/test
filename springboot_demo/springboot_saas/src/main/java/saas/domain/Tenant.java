package saas.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:07
 */
@Data
@Entity
public class Tenant {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String dbu;

    @Column
    private String edbpwd;

    private String businessName;
}
