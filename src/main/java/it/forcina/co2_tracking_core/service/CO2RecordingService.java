package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.exception.CO2ReadingException;
import it.forcina.co2_tracking_core.persistence.entity.CO2Reading;
import it.forcina.co2_tracking_core.persistence.mapper.CO2ReadingsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CO2RecordingService {
    private final CO2ReadingsMapper co2ReadingsMapper;

    @Autowired
    public CO2RecordingService(CO2ReadingsMapper co2ReadingsMapper) {
        this.co2ReadingsMapper = co2ReadingsMapper;
    }

    @Transactional
    public int insertRecording(CO2Reading co2Reading) throws CO2ReadingException {
        if(co2Reading != null) {
            return co2ReadingsMapper.insertRecording(co2Reading);
        }
        throw new CO2ReadingException("Invalid argument: CO2 readings must not be null", "ERROR_CODE 001");
    }
}
