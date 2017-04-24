package ehcache.service.impl;

import ehcache.bean.DemoInfo;
import javassist.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ehcache.repository.DemoInfoRepository;
import ehcache.service.DemoInfoService;

import javax.annotation.Resource;

/**
 * Author: Johnny
 * Date: 2017/4/17
 * Time: 23:37
 */
@Service
public class DemoInfoServiceImpl implements DemoInfoService {

    //这里的单引号不能少，否则会报错，被识别是一个对象;
    private static final String CACHE_KEY = "'demoInfo'";

    @Resource
    private DemoInfoRepository demoInfoRepository;

    // value属性表示使用哪个缓存策略，缓存策略在ehcache.xml
    private static final String DEMO_CACHE_NAME = "demo";

    @CacheEvict(value = DEMO_CACHE_NAME, key = CACHE_KEY)
    @Override
    public DemoInfo save(DemoInfo demoInfo) {
        return demoInfoRepository.save(demoInfo);
    }

    @Cacheable(value = DEMO_CACHE_NAME, key = "'demoInfo_'+#id")
    @Override
    public DemoInfo findById(Long id) {
        System.err.println("没有走缓存！" + id);
        return demoInfoRepository.findOne(id);
    }

    /**
     * http://www.mincoder.com/article/2096.shtml:
     * <p>
     * 修改数据.
     * <p>
     * 在支持Spring Cache的环境下，对于使用@Cacheable标注的方法，Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，
     * 如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
     * @ CachePut也可以声明一个方法支持缓存功能。与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在
     * 之前执行过的结果, 而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
     * @ CachePut也可以标注在类上和方法上。使用@CachePut时我们可以指定的属性跟@Cacheable是一样的。
     */
    @CachePut(value = DEMO_CACHE_NAME, key = "'demoInfo_'+#updated.getId()")
    //@CacheEvict(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#updated.getId()")//这是清除缓存.
    @Override
    public DemoInfo update(DemoInfo updated) throws NotFoundException {
        DemoInfo demoInfo = demoInfoRepository.findOne(updated.getId());
        if (demoInfo == null) {
            throw new NotFoundException("No find");
        }
        demoInfo.setName(updated.getName());
        demoInfo.setPwd(updated.getPwd());
        return demoInfo;
    }

    @CacheEvict(value = DEMO_CACHE_NAME, key = "'demoInfo_'+#id")//这是清除缓存.
    @Override
    public void delete(Long id) {
        demoInfoRepository.delete(id);
    }
}
