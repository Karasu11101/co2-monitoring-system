package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.District;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DistrictMapper {
    @Insert("INSERT INTO district (name, id_city_fk) VALUES (#{name}, #{city.Id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertDistrict(District district);

    @Select("SELECT id, name FROM district")
    List<District> getAllDistrict();
}