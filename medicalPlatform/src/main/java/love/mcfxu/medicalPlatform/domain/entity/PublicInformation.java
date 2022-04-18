package love.mcfxu.medicalPlatform.domain.entity;


import java.io.Serializable;

public class PublicInformation implements Serializable {


  /**
   * 公共信息id
   */
  private Integer publicInformationId;

  /**
   * 平台联系电话
   */
  private String contactNumber;

  /**
   * 平台名誉简介
   */
  private String summary;

  /**
   * 平台网址
   */
  private String url;


  public Integer getPublicInformationId() {
    return publicInformationId;
  }

  public void setPublicInformationId(Integer publicInformationId) {
    this.publicInformationId = publicInformationId;
  }


  public String getContactNumber() {
    return contactNumber;
  }

  public void setContactNumber(String contactNumber) {
    this.contactNumber = contactNumber;
  }


  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
