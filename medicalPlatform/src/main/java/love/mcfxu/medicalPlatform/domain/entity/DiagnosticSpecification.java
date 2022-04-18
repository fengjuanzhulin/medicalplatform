package love.mcfxu.medicalPlatform.domain.entity;


import java.io.Serializable;

public class DiagnosticSpecification implements Serializable {

  /**
   * 诊断书id
   */
  private Integer diagnosticSpecificationId;

  /**
   * 医生id
   */
  private Integer doctorId;

  /**
   * 患者id
   */
  private Integer patientId;

  /**
   * 挂号单单号
   */
  private Integer registrationCardId;

  /**
   * 患者病情描述
   */
  private String consultingContent;

  /**
   * 医嘱
   */
  private String advice;


  public Integer getDiagnosticSpecificationId() {
    return diagnosticSpecificationId;
  }

  public void setDiagnosticSpecificationId(Integer diagnosticSpecificationId) {
    this.diagnosticSpecificationId = diagnosticSpecificationId;
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


  public Integer getRegistrationCardId() {
    return registrationCardId;
  }

  public void setRegistrationCardId(Integer registrationCardId) {
    this.registrationCardId = registrationCardId;
  }


  public String getConsultingContent() {
    return consultingContent;
  }

  public void setConsultingContent(String consultingContent) {
    this.consultingContent = consultingContent;
  }


  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

}
