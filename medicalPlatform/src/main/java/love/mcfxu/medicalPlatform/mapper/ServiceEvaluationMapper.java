package love.mcfxu.medicalPlatform.mapper;

import love.mcfxu.medicalPlatform.domain.entity.ServiceEvaluation;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ServiceEvaluationMapper {

    /**
     * 查找所有医疗服务评价(管理员)
     * @return
     */
    @Select("select * from service_evaluation")
    List<ServiceEvaluation> findAllServiceEvaluationforadm();

    /**
     * 查找所有医疗服务评价(游客)
     * @return
     */
    @Select("select service_evaluation_id, register_time, service_evaluation_details from service_evaluation")
    List<ServiceEvaluation> findAllServiceEvaluationforvis();

    /**
     * 根据患者id查找医疗服务评价
     * @param patientId
     * @return
     */
    @Select("select * from service_evaluation where patient_id=#{patientId}")
    Object findServiceEvaluationById(int patientId);

    /**
     * 根据评价id和患者id删除医疗服务评价
     * @param serviceEvaluationId
     * @return
     */
    @Delete("delete from service_evaluation where service_evaluation_id=#{serviceEvaluationId}")
    int deleteServiceEvaluation(int serviceEvaluationId);

    /**
     * 根据患者id更新医疗服务评价
     * @param serviceEvaluationDetails
     * @param serviceEvaluationId
     * @return
     */
    @Update("update service_evaluation set service_evaluation_details=#{serviceEvaluationDetails} where" +
            "service_evaluation_id=#{serviceEvaluationId}")
    int updateServiceEvaluationDetailsById(String serviceEvaluationDetails,int serviceEvaluationId);

    /**
     * 增添医疗服务评价
     * @param serviceEvaluation
     * @return
     */
    @Insert("insert into `service_evaluation` (`registration_sheet_id`,`register_time`,`doctor_id`," +
            "`patient_id`,`service_evaluation_details`) values (#{registrationSheetId},#{registerTime}," +
            "#{doctorId},#{patientId},#{serviceEvaluationDetails})")
    @Options(useGeneratedKeys = true,keyColumn = "service_department_id",keyProperty = "serviceDepartmentId")
    int addServiceEvaluation(ServiceEvaluation serviceEvaluation);
}
