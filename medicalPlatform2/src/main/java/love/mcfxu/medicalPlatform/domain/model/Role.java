package love.mcfxu.medicalPlatform.domain.model;


import java.io.Serializable;
import java.util.List;

public class Role implements Serializable {

  private int id;
  private String name;
  private String description;
  private List<Permission> permissionList;

  public long getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Permission> getPermissionList() {
    return permissionList;
  }

  public void setPermissionList(List<Permission> permissionList) {
    this.permissionList = permissionList;
  }
}
