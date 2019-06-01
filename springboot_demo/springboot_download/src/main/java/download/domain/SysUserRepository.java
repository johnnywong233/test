package download.domain;

import download.domain.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Johnny
 * Date: 2017/7/13
 * Time: 13:23
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {
    SysUser findByUsername(String username);

    long deleteById(long id);
}
