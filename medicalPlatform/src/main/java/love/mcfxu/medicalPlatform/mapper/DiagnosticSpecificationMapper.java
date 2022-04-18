package love.mcfxu.medicalPlatform.mapper;

import love.mcfxu.medicalPlatform.domain.entity.DiagnosticSpecification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface DiagnosticSpecificationMapper {

    @Delete("delete from diagnostic_specification where diagnostic_specification_id" +
            "=#{diagnosticSpecificationId}")
    int deleteDiagnosticSpecificationById(int diagnosticSpecificationId);

    @Select("select * from diagnostic_specification")
    List<DiagnosticSpecification> findAllDiagnosticSpecification();

    @Select("select diagnostic_specification_id from diagnostic_specification where patient_id=#{patientId} and doctor_id=#{doctorId}")
    int findId(int patientId,int doctorId);

    @Select("select * from diagnostic_specification where patient_id=#{patientId} and doctor_id=#{doctorId}")
    List<DiagnosticSpecification> findDiagnosticSpecificationById(int patientId,int doctorId);

    @Update("update diagnostic_specification set advice=#{advice} where diagnostic_specification_id" +
            "=#{diagnosticSpecificationId}")
    int updateDiagnosticSpecificationByDoctor(String advice,int diagnosticSpecificationId);


    @Select("select doctor_id from diagnostic_specification where diagnostic_specification_id=#{diagnosticSpecificationId}")
    int findDoctorById(int diagnosticSpecificationId);


    @Select("select patient_id from diagnostic_specification where diagnostic_specification_id=#{diagnosticSpecificationId}")
    int findPatientById(int diagnosticSpecificationId);


    @Update("update diagnostic_specification set consulting_content=#{consultingContent} where" +
            "diagnostic_specification_id=#{diagnosticSpecificationId")
    int updateDiagnosticSpecificationByPatient(String consultingContent,int diagnosticSpecificationId);

    @Insert("insert into diagnostic_specification (`doctor_id`,`patient_id`,`registration_card_id`," +
            "`consulting_content`,`advice`) values (#{doctorId},#{patientId},#{registrationCardId}," +
            "#{consultingContent},#{advice})")
    @Options(useGeneratedKeys = true,keyProperty = "diagnosticSpecificationId",
            keyColumn = "diagnostic_specification_id")
    int addDiagnosticSpecification(DiagnosticSpecification diagnosticSpecification);
}
