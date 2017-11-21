package provider;

import contract.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Johnny
 * Date: 2017/11/21
 * Time: 11:33
 */
@Repository
public interface SingerRepository extends JpaRepository<Singer, Long> {
}
