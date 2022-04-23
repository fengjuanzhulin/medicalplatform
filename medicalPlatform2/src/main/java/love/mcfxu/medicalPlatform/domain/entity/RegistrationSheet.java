package love.mcfxu.medicalPlatform.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class RegistrationSheet implements Serializable {

  /**
   * 挂号单主键id
   */
  private Integer registrationCardId;

  /**
   * 挂号时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date registerTime;

  /**
   * 医生id
   */
  private Integer doctorId;

  /**
   * 服务科室
   */
  private String department;

  /**
   * 细化的服务科室
   */
  private String furtherDepartment;

  /**
   * 患者id
   */
  private Integer patientId;

  /**
   * 挂号单状态，0表示未支付，1表示已支付
   */
  private  Integer state;

  /**
   * 联网中支付用户主键id
   */
  private String openId;

  /**
   * 订单唯一标识
   */
  private String outTradeNo;

  /**
   * ip地址
   */
  private String ip;

  /**
   * 订单金额
   */
  private Integer totalFee;

  public Integer getTotalFee() {
    return totalFee;
  }

  public void setTotalFee(Integer totalFee) {
    this.totalFee = totalFee;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

  public String getOpenId() {
    return openId;
  }
  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Integer getRegistrationCardId() {
    return registrationCardId;
  }

  public void setRegistrationCardId(Integer registrationCardId) {
    this.registrationCardId = registrationCardId;
  }


  public Date getRegisterTime() {
    return registerTime;
  }

  public void setRegisterTime(Date registerTime) {
    this.registerTime = registerTime;
  }


  public Integer getDoctorId() {
    return doctorId;
  }

  public void setDoctorId(Integer doctorId) {
    this.doctorId = doctorId;
  }


  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }


  public String getFurtherDepartment() {
    return furtherDepartment;
  }

  public void setFurtherDepartment(String furtherDepartment) {
    this.furtherDepartment = furtherDepartment;
  }


  public Integer getPatientId() {
    return patientId;
  }

  public void setPatientId(Integer patientId) {
    this.patientId = patientId;
  }

}
