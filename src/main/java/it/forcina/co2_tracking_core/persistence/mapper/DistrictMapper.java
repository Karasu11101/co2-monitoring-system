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
    @Insert("INSERT INTO district (name, id_city_fk) VALUES (#{name}, #{city.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertDistrict(District district);

    @Select("SELECT COUNT(*) FROM city WHERE id = #{cityId}")
    int checkCityExists(@Param("cityId") Long cityId);

//    @Select("SELECT * FROM district")
//    List<District> getAllDistricts();

    @Select("SELECT d.id AS district_id, d.name AS district_name, d.id_city_fk, c.id AS city_id, c.name AS city_name " +
            "FROM district d " +
            "JOIN city c ON d.id_city_fk = c.id")
    @Results(value = {
            @Result(property = "id", column = "district_id"),
            @Result(property = "name", column = "district_name"),
            @Result(property = "city.id", column = "city_id"),
            @Result(property = "city.name", column = "city_name")
    })
    List<District> getAllDistricts();
}
