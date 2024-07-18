package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface SensorMapper {
    @Insert("INSERT INTO sensor (name, id_district_fk) VALUES (#{name}, #{district.id})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertSensor(Sensor sensor);
}
