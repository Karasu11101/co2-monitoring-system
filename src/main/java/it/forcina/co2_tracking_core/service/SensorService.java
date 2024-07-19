package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.SensorDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.SensorException;
import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import it.forcina.co2_tracking_core.persistence.mapper.CheckExistsMapper;
import it.forcina.co2_tracking_core.persistence.mapper.SensorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorService {
    private final SensorMapper sensorMapper;
    private final CheckExistsMapper checkExistsMapper;

    @Autowired
    public SensorService(SensorMapper sensorMapper, CheckExistsMapper checkExistsMapper) {
        this.sensorMapper = sensorMapper;
        this.checkExistsMapper = checkExistsMapper;
    }

    @Transactional(readOnly = true)
    public List<Sensor> getAllSensors() throws SensorException {
        List<Sensor> sensors = sensorMapper.getAllSensors();
        if(!sensors.isEmpty()) {
            return sensors;
        }
        throw new SensorException("No sensors found", Codes.NO_RESOURCE_FOUND.getLabel());
    }

    @Transactional
    public long insertSensor(SensorDto sensorDto) throws SensorException {
        if(sensorDto == null) {
            throw new SensorException("Invalid argument: sensor must not be null", Codes.INVALID_ARGUMENT.getLabel());
        }
        if(checkExistsMapper.checkDistrictExists(sensorDto.districtId()) < 0) {
            throw new SensorException(
                    String.format("Cannot insert record: district with ID {%d} does not exist", sensorDto.districtId()),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        Sensor sensor = Sensor.builder()
                .name(sensorDto.name())
                .districtId(sensorDto.districtId())
                .build();
        int response = sensorMapper.insertSensor(sensor);
        if(response == 1) {
            return sensor.getId();
        }
        throw new SensorException("Insert was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }

    @Transactional
    public int updateSensor(SensorDto dto, Long id) throws SensorException {
        if(dto == null || dto.name().isBlank() || dto.name().isEmpty() || id == null || id < 0) {
            throw new SensorException(
                    "Invalid argument: sensor name must not be null, and ID must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkSensorExists(id) < 0) {
            throw new SensorException(
                    String.format("Cannot update record: sensor with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = sensorMapper.updateSensor(dto.name(), id);
        if(response == 1) {
            return response;
        }
        throw new SensorException("Update was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }

    @Transactional
    public int deleteSensor(Long id) throws SensorException {
        if(id == null || id < 0) {
            throw new SensorException(
                    "Invalid argument: ID must not be null and must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkSensorExists(id) < 0) {
            throw new SensorException(
                    String.format("Cannot delete record: sensor with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = sensorMapper.deleteSensor(id);
        if(response == 1) {
            return response;
        }
        throw new SensorException("Could not delete record", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }
}
