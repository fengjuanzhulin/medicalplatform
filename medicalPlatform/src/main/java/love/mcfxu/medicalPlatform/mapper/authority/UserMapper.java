package love.mcfxu.medicalPlatform.mapper.authority;

import love.mcfxu.medicalPlatform.domain.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User findByUsername(@Param("username")String username);



    @Select("select * from user where id=#{userId}")
    User findById(@Param("userId") int id);



    @Select("select * from user where username = #{username} and password = #{pwd}")
    User findByUsernameAndPwd(@Param("username")String username, @Param("pwd")String pwd);


    @Insert("insert into `user` (`username`,`password`) values (#{userName},#{Pwd})")
    @Options(useGeneratedKeys = true,keyColumn = "username.id")
    int userAdd(@Param("userName")String username, @Param("Pwd")String pwd);

    @Update("update user set password=#{Pwd} where username=#{userName}")
    int updatePwd(@Param("userName")String username,@Param("Pwd")String pwd);
}
