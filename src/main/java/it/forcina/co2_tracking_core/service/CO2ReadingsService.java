package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.CO2ReadingDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.CO2ReadingException;
import it.forcina.co2_tracking_core.persistence.entity.CO2Reading;
import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.persistence.entity.District;
import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import it.forcina.co2_tracking_core.persistence.entity.User;
import it.forcina.co2_tracking_core.persistence.mapper.CO2ReadingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class CO2ReadingsService {

    private final CO2ReadingsMapper mapper;

    @Autowired
    public CO2ReadingsService(CO2ReadingsMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllReadingsBySensorId(Long sensorId, String username) throws CO2ReadingException {
        if ((sensorId != null && sensorId > 0) && (!username.isEmpty() && !username.isBlank())) {
            List<Map<String, Object>> readings = mapper.getAllCO2ReadingsBySensorId(sensorId, username);
            if (!readings.isEmpty()) {
                return readings;
            }
            throw new CO2ReadingException("No readings were found", Codes.NO_RESOURCE_FOUND.getLabel());
        }
        throw new CO2ReadingException("Invalid argument: sensor ID must be a positive integer", Codes.INVALID_ARGUMENT.getLabel());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllReadingsByCityId(Long cityId, String username) throws CO2ReadingException {
        if ((cityId != null && cityId > 0) && (!username.isEmpty() && !username.isBlank())) {
            List<Map<String, Object>> readings = mapper.getAllCO2ReadingsByCityId(cityId, username);
            if (!readings.isEmpty()) {
                return readings;
            }
            throw new CO2ReadingException("No readings were foung", Codes.NO_RESOURCE_FOUND.getLabel());
        }
        throw new CO2ReadingException("Invalid argument: city ID must be a positive integer", Codes.INVALID_ARGUMENT.getLabel());
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllReadingsByDistrictId(Long districtId, String username) throws CO2ReadingException {
        if ((districtId != null && districtId > 0) && (!username.isEmpty() && !username.isBlank())) {
            List<Map<String, Object>> readings = mapper.getAllCO2ReadingsByDistrictId(districtId, username);
            if (!readings.isEmpty()) {
                return readings;
            }
            throw new CO2ReadingException("No readings were found", Codes.NO_RESOURCE_FOUND.getLabel());
        }
        throw new CO2ReadingException("Invalid argument: district ID must be a positive integer", Codes.INVALID_ARGUMENT.getLabel());
    }

    @Transactional
    public long insertRecording(CO2ReadingDto co2ReadingDto, User user) throws CO2ReadingException {
        if (co2ReadingDto == null) {
            throw new CO2ReadingException(
                    "Invalid argument: CO2 readings must not be null",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(mapper.checkSensorExists(co2ReadingDto.sensorId()) < 0) {
            throw new CO2ReadingException(
                    String.format("Cannot insert record: sensor with ID {%d} does not exist", co2ReadingDto.sensorId()),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        CO2Reading co2Reading = CO2Reading.builder()
                .sensor(new Sensor(
                        co2ReadingDto.sensorId(),
                        co2ReadingDto.sensorName(),
                        new District(
                                co2ReadingDto.districtId(),
                                co2ReadingDto.districtName(),
                                new City(
                                        co2ReadingDto.cityId(),
                                        co2ReadingDto.cityName()))))
                .ppm(co2ReadingDto.ppm())
                .recordDate(co2ReadingDto.recordDate())
                .user(user)
                .build();

        int response = mapper.insertRecording(co2Reading);
        if (response == 1) {
            return co2Reading.getId();
        }
        throw new CO2ReadingException("Insert was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }
}

