package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.config.CacheKeyManager;
import love.mcfxu.medicalPlatform.domain.entity.DiagnosticSpecification;
import love.mcfxu.medicalPlatform.domain.entity.RegistrationSheet;
import love.mcfxu.medicalPlatform.mapper.DiagnosticSpecificationMapper;
import love.mcfxu.medicalPlatform.mapper.RegistrationSheetMapper;
import love.mcfxu.medicalPlatform.service.DiagnosticSpecificationServive;
import love.mcfxu.medicalPlatform.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DiagnosticSpecificationServiceImpl implements DiagnosticSpecificationServive {

    @Autowired
    DiagnosticSpecificationMapper diagnosticSpecificationMapper;

    @Autowired
    RegistrationSheetMapper registrationSheetMapper;

    @Autowired
    BaseCache baseCache;

    /**
     * 通过主键查找诊断书
     * @param diagnosticSpecificationId
     * @return
     */
    @Override
    public int deleteDiagnosticSpecificationById(int diagnosticSpecificationId) {
        return diagnosticSpecificationMapper.deleteDiagnosticSpecificationById(diagnosticSpecificationId);
    }

    /**
     * 查找所有诊断书
     * @return
     */
    @Override
    public List<DiagnosticSpecification> findAllDiagnosticSpecification() {
        return diagnosticSpecificationMapper.findAllDiagnosticSpecification();
    }

    /**
     * 根据id定位诊断书
     * @param patientId
     * @param doctorId
     * @return
     */
    @Override
    public List<DiagnosticSpecification> findDiagnosticSpecificationById(int patientId, int doctorId) {

        int id = diagnosticSpecificationMapper.findId(patientId, doctorId);

        String diaCacheKey = String.format(CacheKeyManager.DIA_DETAIL,id);

        try{
            Object cacheObject = baseCache.getTwentySecondCache().get( diaCacheKey, ()->{

                List<DiagnosticSpecification> aDiagnosticSpecificationById = diagnosticSpecificationMapper.
                        findDiagnosticSpecificationById(patientId, doctorId);
                return aDiagnosticSpecificationById;
            });

            if(cacheObject instanceof List){

                List<DiagnosticSpecification> diagnosticSpecifications = (List<DiagnosticSpecification>)cacheObject;
                return diagnosticSpecifications;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 更新诊断书里的医嘱
     * @param advice
     * @param diagnosticSpecificationId
     * @return
     */
    @Override
    public int updateDiagnosticSpecificationByDoctor(String advice, int diagnosticSpecificationId) {
        return diagnosticSpecificationMapper.updateDiagnosticSpecificationByDoctor(advice, diagnosticSpecificationId);
    }


    /**
     * 更新诊断书里的病情描述
     * @param consultingContent
     * @param diagnosticSpecificationId
     * @return
     */
    @Override
    public int updateDiagnosticSpecificationByPatient(String consultingContent, int diagnosticSpecificationId) {

        return diagnosticSpecificationMapper.updateDiagnosticSpecificationByPatient(consultingContent,
                diagnosticSpecificationId);
    }

    /**
     * 添加诊断书
     * @param diagnosticSpecification
     * @return
     */
    @Override
    public int addDiagnosticSpecification(DiagnosticSpecification diagnosticSpecification) {

        DiagnosticSpecification aDiagnosticSpecification=new DiagnosticSpecification();
        int doctorId = diagnosticSpecification.getDoctorId();
        aDiagnosticSpecification.setDoctorId(doctorId);
        int patientId = diagnosticSpecification.getPatientId();
        aDiagnosticSpecification.setPatientId(patientId);
        RegistrationSheet sheet = registrationSheetMapper.sheetDuplicateCheck(patientId, doctorId);
        aDiagnosticSpecification.setRegistrationCardId(sheet.getRegistrationCardId());
        int row =diagnosticSpecificationMapper.addDiagnosticSpecification(aDiagnosticSpecification);
        return row;
    }

    @Override
    public int findPatientById(int diagnosticSpecificationId) {
        return diagnosticSpecificationMapper.findPatientById(diagnosticSpecificationId);
    }

    @Override
    public int findDoctorById(int diagnosticSpecificationId) {
        return diagnosticSpecificationMapper.findDoctorById(diagnosticSpecificationId);
    }

    @Override
    public List<DiagnosticSpecification> findDiagnosticSpecificationforpat(int patientId) {
        return diagnosticSpecificationMapper.findDiagnosticSpecificationforpat(patientId);
    }

    @Override
    public List<DiagnosticSpecification> findDiagnosticSpecificationfordoc(int doctorId) {
        return diagnosticSpecificationMapper.findDiagnosticSpecificationfordoc(doctorId);
    }


}
