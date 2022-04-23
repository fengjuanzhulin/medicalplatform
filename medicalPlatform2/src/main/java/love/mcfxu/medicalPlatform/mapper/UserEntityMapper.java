package love.mcfxu.medicalPlatform.mapper;

import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import love.mcfxu.medicalPlatform.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public interface UserEntityMapper {

    /**
     * 查找所有用户
     * @return
     */
    @Select("select * from user_entity")
    List<UserEntity> findAllUsers();

    /**
     * 根据用户id查找用户
     * @param userId
     * @return
     */
    @Select("select * from user_entity where user_id=#{userId}")
    UserEntity findUserById(int userId);

    /**
     * 用户查看自己的信息
     * @param userId
     * @return
     */
    @Select("select user_phone,user_name, user_account_number,user_sex, open_id from user_entity where user_id=#{userId}")
    UserEntity showUserInfo(int userId);

    @Select("select user_name from user_entity where user_id=#{userId}")
    String showUserName(int userId);

    /**
     * 锁定用户
     * @param userName
     * @param userPwd
     * @return
     */
    @Select("select *  from user_entity where user_name=#{userName} and user_pwd=#{userPwd}")
    UserEntity findUserInfo(String userName,String userPwd);


    /**
     * 增加用户
     * @param user
     * @return
     */
    @Insert("insert into `user_entity` (`user_account_number`,`user_pwd`,`user_phone`,"+
            "`user_role`,`user_name`,`head_img`)"+
            "values" +
            "(#{userAccountNumber},#{userPwd},#{userPhone},#{userRole},#{userName},#{headImg})")
    @Options(useGeneratedKeys = true,keyColumn = "user_id",keyProperty = "userId")
    int addUser(UserEntity user);

    /**
     * 根据用户id删除用户
     * @param userID
     * @return
     */
    @Delete("delete from user_entity where user_id=#{userId}")
    int deleteUserById(int userID);

    /**
     * 更新用户
     * @param user
     * @return
     */
    @UpdateProvider(type = SqlProvider.class,method = "updateUser")
    int updateUser(UserEntity user);

    /**
     * 保存微信支付的用户
     * @param user
     * @return
     */
    @Insert("INSERT INTO `user_entity` ( `open_id`,`user_name`,`head_img`,`user_phone`,`user_sex`,`city`,`create_time`)" +
            "VALUES" +
            "(#{openId},#{userName},#{headImg},#{userPhone},#{userSex},#{city},#{createTime})")
    @Options(useGeneratedKeys=true, keyProperty="userId", keyColumn="user_id")
    int saveWeChatUser(UserEntity user);

    /**
     * 根据手机查多个用户
     * @param userPhone
     * @return
     */
    @Select("select * from `user_entity` where `user_phone` = #{userPhone}")
    List<UserEntity> findByUserPhone(String userPhone);

    /**
     * 根据手机查单个用户
     * @param userPhone
     * @return
     */
    @Select("select * from `user_entity` where `user_phone` = #{userPhone}")
    UserEntity findoneByUserPhone(String userPhone);

    /**
     * 根据名字查用户
     * @param userName
     * @return
     */
    @Select("select * from user_entity where user_name = #{userName}")
    UserEntity findByUserName(String userName);


    /**
     * 根据特定条件筛选用户
     * @param userPhone
     * @param userPwd
     * @param userAccountNumber
     * @return
     */
    @Select("select * from user_entity where user_phone = #{userPhone} and user_account_number=#{userAccountNumber} " +
            " and user_pwd=#{userPwd}")
    UserEntity findTheUser(String userPhone,String userPwd,String userAccountNumber);

    /**
     * 根据openid查找支付用户
     * @param openId
     * @return
     */
    @Select("select * from user_entity where open_id = #{openId}")
    UserEntity findByOpenId(String openId);

}
