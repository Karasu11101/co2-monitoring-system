package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.SensorDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.SensorException;
import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.persistence.entity.District;
import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import it.forcina.co2_tracking_core.persistence.mapper.SensorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorService {
    private final SensorMapper sensorMapper;

    @Autowired
    public SensorService(SensorMapper sensorMapper) {
        this.sensorMapper = sensorMapper;
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
        if(sensorMapper.checkDistrictExists(sensorDto.districtId()) < 0) {
            throw new SensorException(
                    String.format("Cannot insert record: district with ID {%d} does not exist", sensorDto.districtId()),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        Sensor sensor = Sensor.builder()
                .name(sensorDto.name())
                .district(new District(
                        sensorDto.districtId(),
                        sensorDto.districtName(),
                        new City(
                                sensorDto.cityId(),
                                sensorDto.cityName())))
                .build();
        int response = sensorMapper.insertSensor(sensor);
        if(response == 1) {
            return sensor.getId();
        }
        throw new SensorException("Insert was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }
}
