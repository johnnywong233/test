package petshop.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author: Johnny
 * Date: 2017/7/6
 * Time: 12:35
 */
//@Service("clientService")
@Transactional
public class ClienteServiceImpl implements ClienteService {
    @PersistenceContext
    EntityManager entityManager;

    public void save() {
        System.out.println("save..");
    }
}
