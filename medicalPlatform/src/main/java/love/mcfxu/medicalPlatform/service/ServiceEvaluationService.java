package love.mcfxu.medicalPlatform.service;

import love.mcfxu.medicalPlatform.domain.entity.ServiceEvaluation;

import java.util.List;

public interface ServiceEvaluationService {

    List<ServiceEvaluation> findAllServiceEvaluationforadm();

    List<ServiceEvaluation> findAllServiceEvaluationforvis();

    Object findServiceEvaluationById(int patientId);
    int deleteServiceEvaluation(int serviceEvaluationId);
    int updateServiceEvaluationDetailsById(String serviceEvaluationDetails,int serviceEvaluationId);
    int addServiceEvaluation(ServiceEvaluation serviceEvaluation);
}
