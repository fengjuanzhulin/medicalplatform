package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.config.CacheKeyManager;
import love.mcfxu.medicalPlatform.domain.entity.PublicInformation;
import love.mcfxu.medicalPlatform.domain.entity.ServiceInformation;
import love.mcfxu.medicalPlatform.mapper.PublicInformationMapper;
import love.mcfxu.medicalPlatform.service.PublicInformationService;
import love.mcfxu.medicalPlatform.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PublicInformationServiceImpl implements PublicInformationService {

    @Autowired
    PublicInformationMapper publicInformationMapper;

    @Autowired
    BaseCache baseCache;

    @Override
    public List<PublicInformation> findAllPublicInformation() {

        try{

            Object cacheObj =  baseCache.getOneHourCache().get(CacheKeyManager.PUB_PLAT_INFO, ()->{

                List<PublicInformation> allPublicInformation = publicInformationMapper.findAllPublicInformation();

                System.out.println("从数据库里面找平台简介信息");

                return allPublicInformation;

            });

            if(cacheObj instanceof List){
                List<PublicInformation> publicInformations = (List<PublicInformation>)cacheObj;
                return publicInformations;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updatePublicInformation(PublicInformation publicInformation) {
        return publicInformationMapper.updatePublicInformation(publicInformation);
    }

    @Override
    public int deletePublicInformationById(int publicInformationId) {
        return publicInformationMapper.deletePublicInformationById(publicInformationId);
    }

    @Override
    public int addPublicInformation(PublicInformation publicInformation) {
        return publicInformationMapper.addPublicInformation(publicInformation);
    }
}
