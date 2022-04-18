package love.mcfxu.medicalPlatform.service;

import love.mcfxu.medicalPlatform.domain.entity.RegistrationSheet;



import java.util.List;


public interface RegistrationSheetService {

    List<RegistrationSheet> findAllRegistrationSheet();

    List<RegistrationSheet> findAllRegistrationSheetByPatientId(int patientId);

    int deleteRegistrationSheetById(int registrationCardId);

    int addRegistrationSheet(RegistrationSheet registrationSheet);

    RegistrationSheet sheetDuplicateCheck(int patienrId, int doctorId);

    String getUrl(RegistrationSheet registrationSheet) throws Exception;

    RegistrationSheet findByOutTradeNo(String outTradeNo);

    int updateRegistrationSheetByOutTradeNo(RegistrationSheet registrationSheet);
}
