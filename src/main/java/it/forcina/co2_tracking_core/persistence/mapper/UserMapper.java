package it.forcina.co2_tracking_core.persistence.mapper;

import it.forcina.co2_tracking_core.persistence.entity.Role;
import it.forcina.co2_tracking_core.persistence.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;
import java.util.Set;

@Mapper
public interface UserMapper {
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "roles", column = "id", javaType = Set.class, many = @Many(select = "getRolesByUserId"))})
    @Select("SELECT ud.*, a.role FROM user_data ud " +
            "INNER JOIN authorities a ON ud.id = a.id_user_fk " +
            "WHERE username = #{username}")
    Optional<User> getUserByUsername(@Param("username") String username);

    @Select("SELECT role FROM authorities WHERE id_user_fk = #{userId}")
    Set<Role> getRolesByUserId(@Param("userId") Long userId);

    @Insert("INSERT INTO user_data (fullName, username, password, email) VALUES (#{fullName}, #{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertNewUser(User user);

    @Insert("INSERT INTO authorities(role, id_user_fk) VALUES(#{role}, #{id_user_fk})")
    int insertUserRole(@Param("role") String role, @Param("id_user_fk") Long idUserFk);
}
