package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.District;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DistrictMapper {
    @Insert("INSERT INTO district (name, id_city_fk) VALUES (#{name}, #{cityId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertDistrict(District district);

    @Select("SELECT id, name, id_city_fk FROM district")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "cityId", column = "id_city_fk")
    })
    List<District> getAllDistricts();

    @Update("UPDATE district SET name = #{name} WHERE id = #{id}")
    int updateDistrict(@Param("name") String name, @Param("id") long id);

    @Delete("DELETE FROM district WHERE id = #{id}")
    int deleteDistrict(@Param("id") long id);
}
