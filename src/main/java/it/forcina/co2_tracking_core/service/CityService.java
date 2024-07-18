package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.exception.CityException;
import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.persistence.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {
    private final CityMapper cityMapper;

    @Autowired
    public CityService(CityMapper cityMapper) {
        this.cityMapper = cityMapper;
    }

    @Transactional
    public long insertCity(City city) throws CityException {
        cityMapper.insertCity(city);
        return city.getId();
    }

    @Transactional(readOnly = true)
    public List<City> getAllCities() {
        return cityMapper.getAllCities();
    }
}
