package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.domain.entity.RegistrationSheet;
import love.mcfxu.medicalPlatform.domain.entity.ServiceEvaluation;
import love.mcfxu.medicalPlatform.mapper.RegistrationSheetMapper;
import love.mcfxu.medicalPlatform.mapper.ServiceEvaluationMapper;
import love.mcfxu.medicalPlatform.service.ServiceEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ServiceEvaluationServiceImpl implements ServiceEvaluationService {

    @Autowired
    ServiceEvaluationMapper serviceEvaluationMapper;

    @Autowired
    RegistrationSheetMapper registrationSheetMapper;

    @Override
    public List<ServiceEvaluation> findAllServiceEvaluationforadm() {
        return serviceEvaluationMapper.findAllServiceEvaluationforadm();
    }

    @Override
    public List<ServiceEvaluation> findAllServiceEvaluationforvis() {
        return serviceEvaluationMapper.findAllServiceEvaluationforvis();
    }

    @Override
    public Object findServiceEvaluationById(int patientId) {
        return serviceEvaluationMapper.findServiceEvaluationById(patientId);
    }

    @Override
    public int deleteServiceEvaluation(int serviceEvaluationId) {
        return serviceEvaluationMapper.deleteServiceEvaluation(serviceEvaluationId);
    }

    @Override
    public int updateServiceEvaluationDetailsById(String serviceEvaluationDetails, int serviceEvaluationId) {
        return serviceEvaluationMapper.updateServiceEvaluationDetailsById(
                serviceEvaluationDetails,serviceEvaluationId);
    }

    @Override
    public int addServiceEvaluation(ServiceEvaluation serviceEvaluation) {

        ServiceEvaluation serviceEvaluation1 = new ServiceEvaluation();
        int doctorId = serviceEvaluation.getDoctorId();
        serviceEvaluation1.setDoctorId(doctorId);
        int patientId = serviceEvaluation.getPatientId();
        serviceEvaluation1.setPatientId(patientId);
        RegistrationSheet sheet = registrationSheetMapper.sheetDuplicateCheck(patientId, doctorId);
        serviceEvaluation1.setRegistrationSheetId(sheet.getRegistrationCardId());
        serviceEvaluation1.setRegisterTime(new Date(System.currentTimeMillis()));
        serviceEvaluation1.setServiceEvaluationDetails(null);
        int row =serviceEvaluationMapper.addServiceEvaluation(serviceEvaluation1);
        return row;
    }

}
