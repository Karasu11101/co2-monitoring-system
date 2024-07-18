package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CityMapper {
    @Insert("INSERT INTO city (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCity(City city);

    @Select("SELECT * FROM city")
    List<City> getAllCities();
}
