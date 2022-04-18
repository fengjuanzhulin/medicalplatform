package love.mcfxu.medicalPlatform.domain.entity;


import java.io.Serializable;

public class ServiceInformation implements Serializable {

  /**
   * 服务科室id
   */
  private Integer serviceDepartmentId;

  /**
   * 服务科室
   */
  private String serviceDepartment;

  /**
   * 服务科室细化
   */
  private String serviceFurtherDepartment;

  /**
   * 医生id
   */
  private Integer doctorId;


  public Integer getServiceDepartmentId() {
    return serviceDepartmentId;
  }

  public void setServiceDepartmentId(Integer serviceDepartmentId) {
    this.serviceDepartmentId = serviceDepartmentId;
  }


  public String getServiceDepartment() {
    return serviceDepartment;
  }

  public void setServiceDepartment(String serviceDepartment) {
    this.serviceDepartment = serviceDepartment;
  }


  public String getServiceFurtherDepartment() {
    return serviceFurtherDepartment;
  }

  public void setServiceFurtherDepartment(String serviceFurtherDepartment) {
    this.serviceFurtherDepartment = serviceFurtherDepartment;
  }


  public Integer getDoctorId() {
    return this.doctorId;
  }

  public void setDoctorId(Integer doctorId) {this.doctorId=doctorId;}

}
