package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SensorMapper {
    @Insert("INSERT INTO sensor (name, id_district_fk) VALUES (#{name}, #{districtId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertSensor(Sensor sensor);

    @Select("SELECT id, name, id_district_fk FROM sensor")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "districtId", column = "id_district_fk")
    })
    List<Sensor> getAllSensors();


}
