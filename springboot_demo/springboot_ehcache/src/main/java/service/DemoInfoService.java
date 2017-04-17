package service;

import bean.DemoInfo;
import javassist.NotFoundException;

/**
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 23:36
 */
public interface DemoInfoService {

    void delete(Long id);

    DemoInfo update(DemoInfo updated) throws NotFoundException;

    DemoInfo findById(Long id);

    DemoInfo save(DemoInfo demoInfo);
}
