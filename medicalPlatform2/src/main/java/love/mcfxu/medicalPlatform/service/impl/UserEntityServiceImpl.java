package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.config.CacheKeyManager;
import love.mcfxu.medicalPlatform.config.WeChatConfig;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import love.mcfxu.medicalPlatform.domain.model.User;
import love.mcfxu.medicalPlatform.mapper.UserEntityMapper;
import love.mcfxu.medicalPlatform.mapper.authority.UserMapper;
import love.mcfxu.medicalPlatform.mapper.authority.UserRoleMapper;
import love.mcfxu.medicalPlatform.service.UserEntityService;
import love.mcfxu.medicalPlatform.utils.BaseCache;
import love.mcfxu.medicalPlatform.utils.HttpUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    UserEntityMapper userMapper;

    @Autowired
    WeChatConfig weChatConfig;

    @Autowired
    UserMapper userMapper0;

    @Autowired
    UserRoleMapper userRoleMapper0;

    @Autowired
    BaseCache baseCache;

    @Override
    public List<UserEntity> findAllUsers() {
        try{

            Object cacheObj =  baseCache.getTwentySecondCache().get(CacheKeyManager.USER_LIST_KEY, ()->{

                List<UserEntity> userEntityList =  userMapper.findAllUsers();

                System.out.println("从数据库里面找用户列表");

                return userEntityList;

            });

            if(cacheObj instanceof List){
                List<UserEntity> userList = (List<UserEntity>)cacheObj;
                return userList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserEntity findUserById(int userId) {
       return  userMapper.findUserById(userId);
    }

    @Override
    public int addUser(UserEntity user) {
        return userMapper.addUser(user);
    }

    @Override
    public int deleteUserById(int userID) {
        return userMapper.deleteUserById(userID);
    }

    @Override
    public int updateUser(UserEntity user) {
        String userPwd = user.getUserPwd();
        if (userPwd != null && userPwd != "") {
            user.setUserPwd((String) doubleMD5(userPwd));
            String userName = user.getUserName();
            String userPwd1 = user.getUserPwd();
            int i = userMapper0.updatePwd(userName, userPwd1);
            int j = userMapper.updateUser(user);
            if (i==1&&j==1) {
                return 0;
            }
            else {
                return -1;
            }

        }
        else {
            int i = userMapper.updateUser(user);
            return i==1?0:-1;
        }
    }

    @Override
    public List<UserEntity> findByUserPhone(String userPhone) {
        return userMapper.findByUserPhone(userPhone);
    }

    @Override
    public UserEntity findoneByUserPhone(String userPhone) {
        return userMapper.findoneByUserPhone(userPhone);
    }

    @Override
    public UserEntity findByOpenId(String openId) {
        return userMapper.findByOpenId(openId);
    }

    /**
     * 保存支付的用户信息
     * @param code
     * @return
     */
    @Override
    public UserEntity saveWeChatUser(String code) {

        String accessTokenUrl = String.format(WeChatConfig.getOpenAccessTokenUrl(),
                weChatConfig.getOpenAppId(),weChatConfig.getOpenAppSecret(),code);

        Map<String ,Object> baseMap =  HttpUtils.doGet(accessTokenUrl);

        if(baseMap == null || baseMap.isEmpty()){ return  null; }
        String accessToken = (String)baseMap.get("access_token");
        String openId  = (String) baseMap.get("openid");

        UserEntity dbUser = userMapper.findByOpenId(openId);

        if(dbUser!=null) {
            return dbUser;
        }

        String userInfoUrl = String.format(WeChatConfig.getOpenUserInfoUrl(),accessToken,openId);

        Map<String ,Object> baseUserMap =  HttpUtils.doGet(userInfoUrl);

        if(baseUserMap == null || baseUserMap.isEmpty()){ return  null; }
        String nickName = (String)baseUserMap.get("nickname");

        Double sexTemp  = (Double) baseUserMap.get("sex");
        int sex = sexTemp.intValue();
        String province = (String)baseUserMap.get("province");
        String city = (String)baseUserMap.get("city");
        String country = (String)baseUserMap.get("country");
        String headImgUrl = (String)baseUserMap.get("headimgurl");
        StringBuilder sb = new StringBuilder(country).append("||").append(province).append("||").append(city);
        String finalAddress = sb.toString();
        try {
            nickName = new String(nickName.getBytes("ISO-8859-1"), "UTF-8");
            finalAddress = new String(finalAddress.getBytes("ISO-8859-1"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        UserEntity user = new UserEntity();
        user.setUserName(nickName);
        user.setHeadImg(headImgUrl);
        user.setCity(finalAddress);
        user.setOpenId(openId);
        user.setUserSex(sex);
        user.setCreateTime(new Date());
        userMapper.saveWeChatUser(user);
        return user;
    }

    /**
     * 保存用户信息
     * @param userInfo
     * @return
     */
    @Override
    public int save(Map<String, String> userInfo) {

        UserEntity user = parseToUser(userInfo);
        if( user != null){
            userMapper.addUser(user);
            userMapper0.userAdd(user.getUserName(),user.getUserPwd());
            User byUsername = userMapper0.findByUsername(user.getUserName());
            int id = byUsername.getId();
            userRoleMapper0.addUserRole(user.getUserRole(),id);
            return 0;

        }else {
            return -1;
        }

    }

    @Override
    public UserEntity findTheUser(String userPhone, String userPwd, String userAccountNumber) {
        return userMapper.findTheUser(userPhone, userPwd, userAccountNumber);
    }

    @Override
    public UserEntity findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    @Override
    public UserEntity showUserInfo(int userId) {
        return userMapper.showUserInfo(userId);
    }

    @Override
    public UserEntity findUserInfo(String name,String pwd) {
        return userMapper.findUserInfo(name,pwd);
    }

    @Override
    public String showUserName(int userId) {
        return userMapper.showUserName(userId);
    }

    /**
     * 更具特定信息查找用户
     * @param userPhone
     * @param userPwd
     * @param userAccountNumber
     * @return
     */
    @Override
    public int findByUserImportInformation(String userPhone,String userPwd,String userAccountNumber) {

        UserEntity user = userMapper.findTheUser(userPhone,doubleMD5(userPwd).toString(),userAccountNumber);

        if(user == null){
            return -1;

        }else {
            return user.getUserId();
        }

    }

    /**
     * 添加用户
     * @param userInfo
     * @return
     */
    private UserEntity parseToUser(Map<String,String> userInfo) {

        if(userInfo.containsKey("user_phone") && userInfo.containsKey("user_pwd") && userInfo.containsKey("user_name")
                &&userInfo.containsKey("user_role")&&userInfo.containsKey("user_account_number")){
            UserEntity user = new UserEntity();
            user.setUserAccountNumber(userInfo.get("user_account_number"));
            user.setUserName(userInfo.get("user_name"));
            user.setHeadImg(getRandomImg());
            user.setUserPhone(userInfo.get("user_phone"));
            String pwd = userInfo.get("user_pwd");
            user.setUserPwd(doubleMD5(pwd).toString());
            user.setUserRole(Integer.valueOf(userInfo.get("user_role")));
            user.setCreateTime(new Date());

            return user;

        }else {
            return null;
        }

    }


    private static final String [] headImg = {
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/12.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/11.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/13.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/14.jpeg",
            "https://xd-video-pc-img.oss-cn-beijing.aliyuncs.com/xdclass_pro/default/head_img/15.jpeg"
    };

    private String getRandomImg(){
        int size =  headImg.length;
        Random random = new Random();
        int index = random.nextInt(size);
        return headImg[index];
    }

    private Object doubleMD5(String pwd){
        String hashName = "md5";
        Object result = new SimpleHash(hashName, pwd, null, 2);
        return result;
    }



}
