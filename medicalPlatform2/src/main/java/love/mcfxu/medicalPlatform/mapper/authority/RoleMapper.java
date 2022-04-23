package love.mcfxu.medicalPlatform.mapper.authority;

import love.mcfxu.medicalPlatform.domain.model.Role;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleMapper {


    @Select("select ur.role_id as id, " +
            "r.name as name, " +
            "r.description as description " +
            " from  user_role ur left join role r on ur.role_id = r.id " +
            "where  ur.user_id = #{userId}")
    @Results(
            value = {
                    @Result(id=true, property = "id",column = "id"),
                    @Result(property = "name",column = "name"),
                    @Result(property = "description",column = "description"),
                    @Result(property = "permissionList",column = "id",
                            many = @Many(select = "love.mcfxu.medicalPlatform.mapper.authority.PermissionMapper.findPermissionListByRoleId", fetchType = FetchType.DEFAULT)
                    )
            }
    )
    List<Role> findRoleListByUserId(@Param("userId")int userId);



}
