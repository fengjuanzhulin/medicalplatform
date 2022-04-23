package love.mcfxu.medicalPlatform.service;

import love.mcfxu.medicalPlatform.domain.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserEntityService {

    List<UserEntity> findAllUsers();
    UserEntity findUserById(int userId);
    int addUser(UserEntity user);
    int deleteUserById(int userID);
    int updateUser(UserEntity user);

    UserEntity saveWeChatUser(String code);
    List<UserEntity> findByUserPhone(String userPhone);

    UserEntity findoneByUserPhone(String userPhone);
    UserEntity findByOpenId(String openId);
    int findByUserImportInformation(String userPhone,String userPwd,String userAccountNumber);
    int save(Map<String, String> userInfo);
    UserEntity findTheUser(String userPhone,String userPwd,String userAccountNumber);
    UserEntity findByUserName(String userName);

    UserEntity showUserInfo(int userId);

    UserEntity findUserInfo(String name,String pwd);

    String showUserName(int userId);
}
