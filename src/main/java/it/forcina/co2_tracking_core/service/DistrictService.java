package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.DistrictDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.CO2ReadingException;
import it.forcina.co2_tracking_core.exception.DistrictException;
import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.persistence.entity.District;
import it.forcina.co2_tracking_core.persistence.mapper.DistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistrictService {
    private final DistrictMapper districtMapper;

    @Autowired
    public DistrictService(DistrictMapper districtMapper) {
        this.districtMapper = districtMapper;
    }

    @Transactional(readOnly = true)
    public List<District> getAllDistricts() throws DistrictException {
        List<District> districts = districtMapper.getAllDistricts();
        if(!districts.isEmpty()) {
            return districts;
        }
        throw new DistrictException("No districts found", Codes.NO_RESOURCE_FOUND.getLabel());
    }

    @Transactional
    public long insertDistrict(DistrictDto districtDto) throws DistrictException {
        if(districtDto == null) {
            throw new DistrictException(
                    "Invalid argument: district must not be null",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(districtMapper.checkCityExists(districtDto.cityId()) < 0) {
            throw new DistrictException(
                    String.format("Cannot insert record: city with ID {%d} does not exist", districtDto.cityId()),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        District district = District.builder()
                .name(districtDto.name())
                .city(City.builder()
                        .id(districtDto.cityId())
                        .name(districtDto.cityName())
                        .build())
                .build();
        int response = districtMapper.insertDistrict(district);
        if(response == 1) {
            return district.getId();
        }
        throw new DistrictException("Insert was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }
}
