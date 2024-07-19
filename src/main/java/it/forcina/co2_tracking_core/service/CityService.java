package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.CityDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.CityException;
import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.persistence.mapper.CheckExistsMapper;
import it.forcina.co2_tracking_core.persistence.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityService {
    private final CityMapper cityMapper;
    private final CheckExistsMapper checkExistsMapper;

    @Autowired
    public CityService(CityMapper cityMapper, CheckExistsMapper checkExistsMapper) {
        this.cityMapper = cityMapper;
        this.checkExistsMapper = checkExistsMapper;
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

    @Transactional
    public int updateCity(CityDto dto, Long id) throws CityException {
        if(dto == null || dto.name().isBlank() || dto.name().isEmpty() || id == null || id < 0) {
            throw new CityException(
                    "Invalid argument: city name must not be null, and ID must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkCityExists(id) < 0) {
            throw new CityException(
                    String.format("Cannot update record: city with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = cityMapper.updateCity(dto.name(), id);
        if(response == 1) {
            return response;
        }
        throw new CityException("Update was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }

    @Transactional
    public int deleteCity(Long id) throws CityException {
        if(id == null || id < 0) {
            throw new CityException(
                    "Invalid argument: ID must not be null and must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkCityExists(id) < 0) {
            throw new CityException(
                    String.format("Cannot delete record: city with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = cityMapper.deleteCity(id);
        if(response == 1) {
            return response;
        }
        throw new CityException("Could not delete record", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }
}
