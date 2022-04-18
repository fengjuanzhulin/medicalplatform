package love.mcfxu.medicalPlatform.mapper.authority;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserRoleMapper {

    @Insert("insert into `user_role` (`role_id`,`user_id`) values (#{roleid},#{userid})")
    @Options(useGeneratedKeys = true,keyColumn = "role_id.id")
    int addUserRole(@Param("roleid")int roleId, @Param("userid")int userId);
}
