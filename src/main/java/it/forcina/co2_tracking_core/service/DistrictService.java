package it.forcina.co2_tracking_core.service;

import it.forcina.co2_tracking_core.dto.DistrictDto;
import it.forcina.co2_tracking_core.dto.response.Codes;
import it.forcina.co2_tracking_core.exception.DistrictException;
import it.forcina.co2_tracking_core.persistence.entity.District;
import it.forcina.co2_tracking_core.persistence.mapper.CheckExistsMapper;
import it.forcina.co2_tracking_core.persistence.mapper.DistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DistrictService {
    private final DistrictMapper districtMapper;
    private final CheckExistsMapper checkExistsMapper;

    @Autowired
    public DistrictService(DistrictMapper districtMapper, CheckExistsMapper checkExistsMapper) {
        this.districtMapper = districtMapper;
        this.checkExistsMapper = checkExistsMapper;
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
        if(checkExistsMapper.checkCityExists(districtDto.cityId()) < 0) {
            throw new DistrictException(
                    String.format("Cannot insert record: city with ID {%d} does not exist", districtDto.cityId()),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        District district = District.builder()
                .name(districtDto.name())
                .cityId(districtDto.cityId())
                .build();
        int response = districtMapper.insertDistrict(district);
        if(response == 1) {
            return district.getId();
        }
        throw new DistrictException("Insert was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }

    @Transactional
    public int updateDistrict(DistrictDto dto, Long id) throws DistrictException {
        if(dto == null || dto.name().isBlank() || dto.name().isEmpty() || id == null || id < 0) {
            throw new DistrictException(
                    "Invalid argument: district name must not be null, and ID must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkDistrictExists(id) < 0) {
            throw new DistrictException(
                    String.format("Cannot update record: district with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = districtMapper.updateDistrict(dto.name(), id);
        if(response == 1) {
            return response;
        }
        throw new DistrictException("Update was not successful", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }

    @Transactional
    public int deleteDistrict(Long id) throws DistrictException {
        if(id == null || id < 0) {
            throw new DistrictException(
                    "Invalid argument: ID must not be null and must be a positive number",
                    Codes.INVALID_ARGUMENT.getLabel()
            );
        }
        if(checkExistsMapper.checkDistrictExists(id) < 0) {
            throw new DistrictException(
                    String.format("Cannot delete record: district with ID {%d} does not exist", id),
                    Codes.NO_RESOURCE_FOUND.getLabel());
        }
        int response = districtMapper.deleteDistrict(id);
        if(response == 1) {
            return response;
        }
        throw new DistrictException("Could not delete record", Codes.OPERATION_UNSUCCESSFUL.getLabel());
    }
}
