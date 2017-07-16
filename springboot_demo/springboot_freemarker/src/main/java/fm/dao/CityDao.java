package fm.dao;

import fm.domain.City;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 23:31
 */
public interface CityDao {
    /**
     * 获取城市信息列表
     */
    List<City> findAllCity();

    /**
     * 根据城市 ID，获取城市信息
     */
    City findById(@Param("id") Long id);

    Long saveCity(City city);

    Long updateCity(City city);

    Long deleteCity(Long id);
}
