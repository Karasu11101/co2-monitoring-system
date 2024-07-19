package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.District;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DistrictMapper {
    @Insert("INSERT INTO district (name, id_city_fk) VALUES (#{name}, #{cityId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertDistrict(District district);

    @Select("SELECT COUNT(*) FROM city WHERE id = #{cityId}")
    int checkCityExists(@Param("cityId") Long cityId);

    @Select("SELECT id, name, id_city_fk FROM district")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "cityId", column = "id_city_fk")
    })
    List<District> getAllDistricts();
}
