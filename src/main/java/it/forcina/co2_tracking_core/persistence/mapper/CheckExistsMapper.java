package it.forcina.co2_tracking_core.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CheckExistsMapper {
    @Select("SELECT COUNT(*) FROM city WHERE id = #{cityId}")
    int checkCityExists(@Param("cityId") Long cityId);

    @Select("SELECT COUNT(*) FROM sensor WHERE id = #{sensorId}")
    int checkSensorExists(@Param("sensorId") Long sensorId);

    @Select("SELECT COUNT(*) FROM district WHERE id = #{districtId}")
    int checkDistrictExists(@Param("districtId") Long districtId);
}
