package ehcache.bean;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
