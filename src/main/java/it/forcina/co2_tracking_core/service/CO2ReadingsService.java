package it.forcina.co2_tracking_core.service;

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
    public List<Map<String,Object>> getAllReadingsBySensorId(Long sensorId, String username) {
        return mapper.getCO2ReadingsBySensorId(sensorId, username);
    }
}
