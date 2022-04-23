package love.mcfxu.medicalPlatform.service;

import love.mcfxu.medicalPlatform.domain.entity.PublicInformation;

import java.util.List;

public interface PublicInformationService {

    List<PublicInformation> findAllPublicInformation();
    int updatePublicInformation(PublicInformation publicInformation);
    int deletePublicInformationById(int publicInformationId);
    int addPublicInformation(PublicInformation publicInformation);
}
