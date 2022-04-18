package love.mcfxu.medicalPlatform.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class HealthCommonSense implements Serializable {

  /**
   * 健康小常识id
   */
  private Integer healthCommonSenseId;

  /**
   * 作者姓名
   */
  private String authorName;

  /**
   * 标题
   */
  private String topic;

  /**
   * 发布日期
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  private Date releaseDate;


  public Integer getHealthCommonSenseId() {
    return healthCommonSenseId;
  }

  public void setHealthCommonSenseId(Integer healthCommonSenseId) {
    this.healthCommonSenseId = healthCommonSenseId;
  }


  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }


  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }


  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

}
