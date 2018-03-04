package fm.service;

import fm.domain.City;

import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 23:33
 */
public interface CityService {
    /**
     * 获取城市信息列表
     */
    List<City> findAllCity();

    /**
     * 根据城市 ID,查询城市信息
     */
    City findCityById(Long id);

    City findCityByName(String name);

    /**
     * 新增城市信息
     */
    Long saveCity(City city);

    /**
     * 更新城市信息
     */
    Long updateCity(City city);

    /**
     * 根据城市 ID,删除城市信息
     */
    Long deleteCity(Long id);
}
