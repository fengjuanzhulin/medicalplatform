package love.mcfxu.medicalPlatform.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

public class UserEntity implements Serializable {


  public UserEntity() {
  }


  /**
   * 用户主键id
   */
  private Integer userId;

  /**
   * 用户账号名
   */
  private String userAccountNumber;

  /**
   * 用户密码
   */
  @JsonIgnore
  private String userPwd;

  /**
   * 用户手机号
   */
  private String userPhone;

  /**
   * 0是女，1是男，2未知
   */
  private Integer userSex;

  /**
   * 0是医生，1是患者，2是管理员
   */
  private Integer userRole;

  /**
   * 用户真实名字
   */
  private String userName;

  /**
   * 联网中用户主键openid
   */
  private String openId;

  /**
   * 联网中用户的头像
   */
  private String headImg;

  /**
   * 联网中用户所在城市
   */
  private String city;

  /**
   * 用户挂号时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date createTime;

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getHeadImg() {
    return headImg;
  }

  public void setHeadImg(String headImg) {
    this.headImg = headImg;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }


  public String getUserAccountNumber() {
    return userAccountNumber;
  }

  public void setUserAccountNumber(String userAccountNumber) {
    this.userAccountNumber = userAccountNumber;
  }


  public String getUserPwd() {
    return userPwd;
  }

  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd;
  }


  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }


  public Integer getUserSex() {
    return userSex;
  }

  public void setUserSex(Integer userSex) {
    this.userSex = userSex;
  }


  public Integer getUserRole() {
    return userRole;
  }

  public void setUserRole(Integer userRole) {
    this.userRole = userRole;
  }


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
