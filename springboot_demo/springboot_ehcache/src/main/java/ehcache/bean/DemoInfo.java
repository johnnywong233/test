package ehcache.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 23:30
 */
@Data
@Entity
public class DemoInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String pwd;
    private int state;
}
