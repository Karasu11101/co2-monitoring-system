package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.SensorException;
import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import it.forcina.co2_tracking_core.persistence.mapper.SensorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SensorService {
    private final SensorMapper sensorMapper;

    @Autowired
    public SensorService(SensorMapper sensorMapper) {
        this.sensorMapper = sensorMapper;
    }

    @Transactional
    public int insertSensor(Sensor sensor) throws SensorException {
        if(sensor != null) {
            return sensorMapper.insertSensor(sensor);
        }
        throw new SensorException("Invalid argument: sensor must not be null", Codes.INVALID_ARGUMENT.getLabel());
    }
}
