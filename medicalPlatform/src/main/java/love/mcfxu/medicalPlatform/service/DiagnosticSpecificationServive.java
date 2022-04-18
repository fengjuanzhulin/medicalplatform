package love.mcfxu.medicalPlatform.service;

import love.mcfxu.medicalPlatform.domain.entity.DiagnosticSpecification;

import java.util.List;

public interface DiagnosticSpecificationServive {

    int deleteDiagnosticSpecificationById(int diagnosticSpecificationId);
    List<DiagnosticSpecification> findAllDiagnosticSpecification();
    List<DiagnosticSpecification> findDiagnosticSpecificationById(int patientId,int doctorId);
    int updateDiagnosticSpecificationByDoctor(String advice,int diagnosticSpecificationId);
    int updateDiagnosticSpecificationByPatient(String consultingContent,int diagnosticSpecificationId);
    int addDiagnosticSpecification(DiagnosticSpecification diagnosticSpecification);

    int findPatientById(int diagnosticSpecificationId);

    int findDoctorById(int diagnosticSpecificationId);
}
