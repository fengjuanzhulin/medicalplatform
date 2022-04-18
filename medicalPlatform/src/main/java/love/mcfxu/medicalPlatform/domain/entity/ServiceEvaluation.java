package love.mcfxu.medicalPlatform.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class ServiceEvaluation implements Serializable {


  /**
   * 服务评价id
   */
  private Integer serviceEvaluationId;

  /**
   * 挂号单id
   */
  private Integer registrationSheetId;

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
   * 患者id
   */
  private Integer patientId;

  /**
   * 服务评价
   */
  private String serviceEvaluationDetails;


  public Integer getServiceEvaluationId() {
    return serviceEvaluationId;
  }

  public void setServiceEvaluationId(Integer serviceEvaluationId) {
    this.serviceEvaluationId = serviceEvaluationId;
  }


  public Integer getRegistrationSheetId() {
    return registrationSheetId;
  }

  public void setRegistrationSheetId(Integer registrationSheetId) {
    this.registrationSheetId = registrationSheetId;
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


  public Integer getPatientId() {
    return patientId;
  }

  public void setPatientId(Integer patientId) {
    this.patientId = patientId;
  }


  public String getServiceEvaluationDetails() {
    return serviceEvaluationDetails;
  }

  public void setServiceEvaluationDetails(String serviceEvaluationDetails) {
    this.serviceEvaluationDetails = serviceEvaluationDetails;
  }

}
