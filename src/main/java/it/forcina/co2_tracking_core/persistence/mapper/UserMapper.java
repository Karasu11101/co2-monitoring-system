package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM user_data WHERE username = #{username}")
    Optional<User> getUserByUsername(@Param("username") String username);

    @Insert("INSERT INTO user_data (fullName, username, password, email) VALUES (#{fullName}, #{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertNewUser(User user);

    @Insert("INSERT INTO authorities(role, id_user_fk) VALUES(#{role}, #{id_user_fk})")
    int insertUserRole(@Param("role") String role, @Param("id_user_fk") Long idUserFk);
}
