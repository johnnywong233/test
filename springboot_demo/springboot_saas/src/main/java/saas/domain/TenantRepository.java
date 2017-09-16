package saas.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * Author: Johnny
 * Date: 2017/8/17
 * Time: 0:08
 */
public interface TenantRepository extends CrudRepository<Tenant, Long> {
}
