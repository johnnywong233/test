package fm.service.impl;

import fm.mapper.ds1.CityMapper;
import fm.domain.City;
import fm.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: Johnny
 * Date: 2017/7/15
 * Time: 23:34
 */
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;

    public List<City> findAllCity() {
        return cityMapper.findAllCity();
    }

    public City findCityById(Long id) {
        return cityMapper.findById(id);
    }

    public City findCityByName(String name) {
        return cityMapper.findByName(name);
    }

    @Override
    public Long saveCity(City city) {
        return cityMapper.saveCity(city);
    }

    @Override
    public Long updateCity(City city) {
        return cityMapper.updateCity(city);
    }

    @Override
    public Long deleteCity(Long id) {
        return cityMapper.deleteCity(id);
    }

}
