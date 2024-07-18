package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.CO2Reading;
import it.forcina.co2_tracking_core.persistence.entity.City;
import it.forcina.co2_tracking_core.persistence.entity.District;
import it.forcina.co2_tracking_core.persistence.entity.Sensor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Map;

@Mapper
public interface CO2ReadingsMapper {

    @Results(id = "sensorCo2ReadingResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "ppm", column = "ppm"),
            @Result(property = "recordDate", column = "recordDate"),
            @Result(property = "sensor", column = "sensor_id",
                    one = @One(select = "getSensorById", fetchType = FetchType.LAZY)),
            @Result(property = "district", column = "district_id",
                    one = @One(select = "getDistrictById", fetchType = FetchType.LAZY)),
            @Result(property = "city", column = "city_id",
                    one = @One(select = "getCityById", fetchType = FetchType.LAZY))
    })
    @Select("SELECT r.id as recording_id, r.ppm as ppm_concentration, r.record_date as record_date, s.name as sensor_name, " +
            "d.id as district_id, d.name as district_name, c.id as city_id, c.name as city_name " +
            "FROM co2_reading r " +
            "INNER JOIN sensor s ON r.id_sensor_fk = s.id " +
            "INNER JOIN district d ON s.id_district_fk = d.id " +
            "INNER JOIN city c ON d.id_city_fk = c.id " +
            "INNER JOIN user_data ud ON r.username_fk = ud.username " +
            "WHERE r.id_sensor_fk = #{sensorId} " +
            "AND r.username_fk = #{username}")
    List<Map<String, Object>> getAllCO2ReadingsBySensorId(@Param("sensorId") Long sensorId, @Param("username") String username);

    @Results(id = "cityCo2ReadingResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "ppm", column = "ppm"),
            @Result(property = "recordDate", column = "recordDate"),
            @Result(property = "sensor", column = "sensor_id",
                    one = @One(select = "getSensorById", fetchType = FetchType.LAZY)),
            @Result(property = "district", column = "district_id",
                    one = @One(select = "getDistrictById", fetchType = FetchType.LAZY)),
            @Result(property = "city", column = "city_id",
                    one = @One(select = "getCityById", fetchType = FetchType.LAZY))
    })
    @Select("SELECT r.id as recording_id, r.ppm as ppm_concentration, r.record_date as record_date, s.name as sensor_name, " +
            "d.id as district_id, d.name as district_name, c.id as city_id, c.name as city_name " +
            "FROM co2_reading r " +
            "INNER JOIN sensor s ON r.id_sensor_fk = s.id " +
            "INNER JOIN district d ON s.id_district_fk = d.id " +
            "INNER JOIN city c ON d.id_city_fk = c.id " +
            "INNER JOIN user_data ud ON r.username_fk = ud.username " +
            "WHERE c.id = #{cityId} " +
            "AND r.username_fk = #{username}"
    )
    List<Map<String, Object>> getAllCO2ReadingsByCityId(@Param("cityId") Long cityId, @Param("username") String username);

    @Results(id = "districtCo2ReadingResultMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "ppm", column = "ppm"),
            @Result(property = "recordDate", column = "recordDate"),
            @Result(property = "sensor", column = "sensor_id",
                    one = @One(select = "getSensorById", fetchType = FetchType.LAZY)),
            @Result(property = "district", column = "district_id",
                    one = @One(select = "getDistrictById", fetchType = FetchType.LAZY)),
            @Result(property = "city", column = "city_id",
                    one = @One(select = "getCityById", fetchType = FetchType.LAZY))
    })
    @Select("SELECT r.id as recording_id, r.ppm as ppm_concentration, r.record_date as record_date, s.name as sensor_name, " +
            "d.id as district_id, d.name as district_name, c.id as city_id, c.name as city_name " +
            "FROM co2_reading r " +
            "INNER JOIN sensor s ON r.id_sensor_fk = s.id " +
            "INNER JOIN district d ON s.id_district_fk = d.id " +
            "INNER JOIN city c ON d.id_city_fk = c.id " +
            "INNER JOIN user_data ud ON r.username_fk = ud.username " +
            "WHERE d.id = #{districtId} " +
            "AND r.username_fk = #{username}"
    )
    List<Map<String, Object>> getAllCO2ReadingsByDistrictId(@Param("districtId") Long districtId, @Param("username") String username);

    @Select("SELECT s.name as sensor_name " +
            "FROM sensor s " +
            "WHERE s.id = #{sensorId}")
    Sensor getSensorById(@Param("sensorId") Long sensorId);

    @Select("SELECT d.id as district_id, d.name as district_name " +
            "FROM district d " +
            "WHERE d.id = #{districtId}")
    District getDistrictById(@Param("districtId") Long districtId);

    @Select("SELECT c.id as city_id, c.name as city_name " +
            "FROM city c " +
            "WHERE c.id = #{cityId}")
    City getCityById(@Param("cityId") Long city);

    @Insert("INSERT INTO co2_reading (ppm, record_date, id_sensor_fk, username_fk) VALUES (#{ppm}, #{recordDate}, #{sensor.id}, #{user.username})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertRecording(CO2Reading recording);

    @Select("SELECT COUNT(*) FROM sensor WHERE id = #{sensorId}")
    int checkSensorExists(@Param("sensorId") Long sensorId);
}
