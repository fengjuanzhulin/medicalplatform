package love.mcfxu.medicalPlatform.mapper;

import love.mcfxu.medicalPlatform.domain.entity.RegistrationSheet;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface RegistrationSheetMapper {

    @Select("select * from registration_sheet")
    List<RegistrationSheet> findAllRegistrationSheet();

    @Select("select doctor_id,further_department,register_time from registration_sheet where patient_id=#{patientId}")
    List<RegistrationSheet> findAllRegistrationSheetByPatientId(int patientId);

    @Delete("delete *from registration_sheet where registration_card_id=#{registrationCardId}")
    int deleteRegistrationSheetById(int registrationCardId);

    @Insert("insert into registration_sheet (`register_time`,`doctor_id`,`department`,`further_department`," +
            "`patient_id`,`state`,`open_id`,`out_trade_no`,`ip`,`total_fee`) values (#{registerTime},#{doctorId}," +
            "#{department},#{furtherDepartment},#{patientId},#{state},#{openId},#{outTradeNo},#{ip},#{totalFee})")
    @Options(useGeneratedKeys = true,keyColumn = "registration_card_id",keyProperty = "registrationCardId")
    int addRegistrationSheet(RegistrationSheet registrationSheet);

    @Select("select * from registration_sheet where doctor_id=#{doctorId} and patient_id=#{patientId}")
    RegistrationSheet sheetDuplicateCheck(int patientId,int doctorId);

    @Select("select * from registration_sheet where out_trade_no=#{outTradeNo}")
    RegistrationSheet findByOutTradeNo(String outTradeNo);

    @Update("update registration_sheet set state=#{state}, register_time=#{registerTime},open_id=#{openId}" +
            " where out_trade_no=#{outTradeNo} and state=0 ")
    int updateRegistrationSheetByOutTradeNo(RegistrationSheet registrationSheet);


}
