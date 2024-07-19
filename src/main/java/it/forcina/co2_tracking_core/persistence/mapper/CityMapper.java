package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CityMapper {
    @Insert("INSERT INTO city (name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertCity(City city);

    @Select("SELECT id, name FROM city")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    List<City> getAllCities();
}
