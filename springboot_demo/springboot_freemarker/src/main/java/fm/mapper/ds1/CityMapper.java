package fm.mapper.ds1;

import fm.domain.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 23:31
 */
public interface CityMapper {
    /**
     * 获取城市信息列表
     */
    List<City> findAllCity();

    /**
     * 根据城市 ID，获取城市信息
     * 注意这里的定义，添加requestParam来避免这种报错
     * MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long';
     */
    City findById(@Param("id") Long id);

    City findByName(@Param("cityName") String cityName);

    Long saveCity(City city);

    Long updateCity(City city);

    Long deleteCity(Long id);
}
