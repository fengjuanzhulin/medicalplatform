package love.mcfxu.medicalPlatform.mapper;

import love.mcfxu.medicalPlatform.domain.entity.ServiceInformation;
import love.mcfxu.medicalPlatform.utils.SqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ServiceInformationMapper {

    /**
     * 查找所有医疗服务信息
     * @return
     */
    @Select("select * from service_information")
    @Options(useGeneratedKeys = true,keyColumn = "service_department_id",keyProperty = "serviceDepartmentId")
    List<ServiceInformation> findAllServiceInformation();

    /**
     * 动态更新医疗服务信息
     * @param serviceInformation
     * @return
     */
    @UpdateProvider(type = SqlProvider.class,method = "updateServiceInformation")
    int updateServiceInformation(ServiceInformation serviceInformation);

    /**
     * 根据服务部门id删除医疗服务信息
     * @param serviceDepartmentId
     * @return
     */
    @Delete("delete from service_information where service_department_id=#{serviceDepartmentId}")
    int deleteServiceInformationById(int serviceDepartmentId);

    /**
     * 增添医疗服务信息
     * @param serviceInformation
     * @return
     */
    @Insert("insert into `service_information` (`service_department`,`service_further_department`," +
            "`doctor_id`) values(#{serviceDepartment},#{serviceFurtherDepartment},#{doctorId})")
    @Options(useGeneratedKeys = true,keyProperty ="serviceDepartmentId",keyColumn = "service_department_id")
    int addServiceInformation(ServiceInformation serviceInformation);

    /**
     * 根据医生id查找医疗服务信息
     * @param doctorId
     * @return
     */
    @Select("select * from service_information where doctor_id=#{doctorId}")
    ServiceInformation findServiceInformationByDoctorId(int doctorId);

}
