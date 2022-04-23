package love.mcfxu.medicalPlatform.utils;

import love.mcfxu.medicalPlatform.domain.entity.HealthCommonSense;
import love.mcfxu.medicalPlatform.domain.entity.PublicInformation;
import love.mcfxu.medicalPlatform.domain.entity.ServiceInformation;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import org.apache.ibatis.jdbc.SQL;

public class SqlProvider {

    /**
     * 动态更新公共信息
     * @param publicInformation
     * @return
     */
    public String updatePublicInformation(PublicInformation publicInformation){
        return new SQL(){
            {
                UPDATE("public_information");
                if(publicInformation.getContactNumber()!=null){
                    SET("contact_number=#{contactNumber}");
                }
                if(publicInformation.getSummary()!=null){
                    SET("summary=#{summary}");
                }
                if(publicInformation.getUrl()!=null){
                    SET("url=#{url}");
                }
                WHERE("public_information_id=#{publicInformationId}");
            }
        }.toString();
    }

    /**
     * 动态更新健康资讯
     * @param healthCommonSense
     * @return
     */
    public String updateHealthCommonSense(HealthCommonSense healthCommonSense){
        return new SQL(){
            {
                UPDATE("health_common_sense");
                if(healthCommonSense.getAuthorName()!=null){
                    SET("author_name=#{authorName}");
                }
                if(healthCommonSense.getReleaseDate()!=null){
                    SET("release_date=#{releaseDate}");
                }
                if(healthCommonSense.getTopic()!=null){
                    SET("topic=#{topic}");
                }
                WHERE("health_common_sense_id=#{healthCommonSenseId}");
            }
        }.toString();
    }

    /**
     * 动态更新医疗服务信息
     * @param serviceInformation
     * @return
     */
    public String updateServiceInformation(ServiceInformation serviceInformation){
        return new SQL(){
            {
                UPDATE("service_information");
                if(serviceInformation.getServiceDepartment()!=null){
                    SET("service_department=#{serviceDepartment}");
                }
                if(serviceInformation.getServiceFurtherDepartment()!=null){
                    SET("service_further_department=#{serviceFurtherDepartment}");
                }
                if(serviceInformation.getDoctorId()!=null){
                    SET("doctor_id=#{doctorId}");
                }
                WHERE("service_department_id=#{serviceDepartmentId}");
            }
        }.toString();
    }

    /**
     * 动态更新用户
     * @param user
     * @return
     */
    public String updateUser(UserEntity user){
        return new SQL(){
            {
                UPDATE("user_entity");
                if(user.getUserAccountNumber()!=null){
                    SET("user_account_number=#{userAccountNumber}");
                }
                if(user.getUserName()!=null){
                    SET("user_name=#{userName}");
                }
                if(user.getUserPhone()!=null){
                    SET("user_phone=#{userPhone}");
                }
                if (user.getUserPwd()!=null){
                    SET("user_pwd=#{userPwd}");
                }
                if (user.getUserSex()!=null){
                    SET("user_sex=#{userSex}");
                }
                if (user.getUserRole()!=null){
                    SET("user_role=#{userRole}");
                }
                WHERE("user_id=#{userId}");
            }
        }.toString();
    }
}
