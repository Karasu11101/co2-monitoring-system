package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.CityDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
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
    public long insertCity(CityDto cityDto) throws CityException {
        City city = City.builder()
                .name(cityDto.name())
                .build();

        if(city != null) {
            int response = cityMapper.insertCity(city);
            if(response == 1) {
                return city.getId();
            }
            throw new CityException("Insert was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
        }
        throw new CityException("Invalid argument: body must not be null", Codes.INVALID_ARGUMENT.getLabel());
    }

    @Transactional(readOnly = true)
    public List<City> getAllCities() throws CityException {
        List<City> cities = cityMapper.getAllCities();
        if(!cities.isEmpty()) {
            return cities;
        }
        throw new CityException("No city found", Codes.NO_RESOURCE_FOUND.getLabel());
    }
}
