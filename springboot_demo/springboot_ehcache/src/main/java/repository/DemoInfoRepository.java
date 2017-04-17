package repository;

import bean.DemoInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 23:38
 */
public interface DemoInfoRepository extends CrudRepository<DemoInfo, Long> {
}
