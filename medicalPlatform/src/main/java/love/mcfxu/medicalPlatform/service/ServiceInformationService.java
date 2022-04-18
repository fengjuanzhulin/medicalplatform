package love.mcfxu.medicalPlatform.service;

import love.mcfxu.medicalPlatform.domain.entity.ServiceInformation;

import java.util.List;

public interface ServiceInformationService {

    List<ServiceInformation> findAllServiceInformation();
    int updateServiceInformation(ServiceInformation serviceInformation);
    int deleteServiceInformationById(int serviceDepartmentId);
    int addServiceInformation(ServiceInformation serviceInformation);
    ServiceInformation findServiceInformationByDoctorId(int doctorId);

}
