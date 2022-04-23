package love.mcfxu.medicalPlatform.domain.model;


import java.io.Serializable;

public class UserRole implements Serializable {

  private int id;
  private int roleId;
  private int userId;
  private String remarks;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }


  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }


  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

}
