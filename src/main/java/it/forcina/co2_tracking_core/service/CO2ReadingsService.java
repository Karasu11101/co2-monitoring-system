package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.CO2ReadingDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.CO2ReadingException;
import it.forcina.co2_tracking_core.persistence.entity.CO2Reading;
import it.forcina.co2_tracking_core.persistence.entity.User;
import it.forcina.co2_tracking_core.persistence.mapper.CO2ReadingsMapper;
import it.forcina.co2_tracking_core.persistence.mapper.CheckExistsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.time.ZonedDateTime.now;

@Service
public class CO2ReadingsService {

    private final CO2ReadingsMapper mapper;
    private final CheckExistsMapper checkExistsMapper;

    @Autowired
    public CO2ReadingsService(CO2ReadingsMapper mapper, CheckExistsMapper checkExistsMapper) {
        this.mapper = mapper;
        this.checkExistsMapper = checkExistsMapper;
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
        if(checkExistsMapper.checkSensorExists(co2ReadingDto.sensorId()) < 0) {
            throw new CO2ReadingException(
                    String.format("Cannot insert record: sensor with ID {%d} does not exist", co2ReadingDto.sensorId()),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        CO2Reading co2Reading = CO2Reading.builder()
                .sensorId(co2ReadingDto.sensorId())
                .ppm(co2ReadingDto.ppm())
                .recordDate(co2ReadingDto.recordDate())
                .username(user.getUsername())
                .build();

        int response = mapper.insertRecording(co2Reading);
        if (response == 1) {
            return co2Reading.getId();
        }
        throw new CO2ReadingException("Insert was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }

    @Transactional
    public int updateReading(CO2ReadingDto dto, Long id) throws CO2ReadingException {
        if(dto == null || dto.recordDate().isAfter(now()) || dto.ppm() < 0 || id == null || id < 0) {
            throw new CO2ReadingException(
                    "Invalid argument: CO2 reading must not be null, record date must not be a future date, " +
                            "ppm levels must not be negative, and ID must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkReadingExists(id) < 0) {
            throw new CO2ReadingException(
                    String.format("Cannot update record: reading with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = mapper.updateReading(dto.ppm(), dto.recordDate(), id);
        if(response == 1) {
            return response;
        }
        throw new CO2ReadingException("Update was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }

    @Transactional
    public int deleteReading(Long id) throws CO2ReadingException {
        if(id == null || id < 0) {
            throw new CO2ReadingException(
                    "Invalid argument: ID must not be null and must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkReadingExists(id) < 0) {
            throw new CO2ReadingException(
                    String.format("Cannot delete record: reading with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = mapper.deleteReading(id);
        if(response == 1) {
            return response;
        }
        throw new CO2ReadingException("Could not delete record", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }
}

