package mybatis.service;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import mybatis.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

/**
 * Author: Johnny
 * Date: 2017/9/17
 * Time: 15:55
 * 一般情况下，数据库的更新操作如update，具备事务属性，应该放在service包下面
 */
@Service
public class EmployeeService {
    @Resource
    private EmployeeRepository employeeRepository;

    @Transactional
    public void update(Integer id, Integer age) {
        employeeRepository.update(id, age);
    }
}
