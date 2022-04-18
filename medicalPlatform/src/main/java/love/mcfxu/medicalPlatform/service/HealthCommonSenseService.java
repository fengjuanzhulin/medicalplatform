package love.mcfxu.medicalPlatform.service;

import love.mcfxu.medicalPlatform.domain.entity.HealthCommonSense;

import java.util.List;

public interface HealthCommonSenseService {

    List<HealthCommonSense> findAllHealthCommonSense();
    int updateHealthCommonSense(HealthCommonSense healthCommonSense);
    int delectHealthCommonSenseById(int healthCommonSenseId);
    int addHealthCommonSense(HealthCommonSense healthCommonSense);
}
