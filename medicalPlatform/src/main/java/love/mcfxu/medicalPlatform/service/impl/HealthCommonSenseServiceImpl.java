package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.config.CacheKeyManager;
import love.mcfxu.medicalPlatform.domain.entity.HealthCommonSense;
import love.mcfxu.medicalPlatform.mapper.HealthCommonSenseMapper;
import love.mcfxu.medicalPlatform.service.HealthCommonSenseService;
import love.mcfxu.medicalPlatform.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class HealthCommonSenseServiceImpl implements HealthCommonSenseService {

    @Autowired
    HealthCommonSenseMapper healthCommonSenseMapper;

    @Autowired
    BaseCache baseCache;

    @Override
    public List<HealthCommonSense> findAllHealthCommonSense() {

        try{

            Object cacheObj =  baseCache.getOneHourCache().get(CacheKeyManager.PUB_HEALTH_INFO, ()->{

                List<HealthCommonSense> allHealthCommonSense = healthCommonSenseMapper.findAllHealthCommonSense();

                System.out.println("从数据库里面找健康常识咨询");

                return allHealthCommonSense;

            });

            if(cacheObj instanceof List){
                List<HealthCommonSense> healthCommonSenses = (List<HealthCommonSense>)cacheObj;
                return healthCommonSenses;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateHealthCommonSense(HealthCommonSense healthCommonSense) {
        return healthCommonSenseMapper.updateHealthCommonSense(healthCommonSense);
    }

    @Override
    public int delectHealthCommonSenseById(int healthCommonSenseId) {
        return healthCommonSenseMapper.delectHealthCommonSenseById(healthCommonSenseId);
    }

    @Override
    public int addHealthCommonSense(HealthCommonSense healthCommonSense) {

        HealthCommonSense healthCommonSense1 = new HealthCommonSense();
        healthCommonSense1.setAuthorName(healthCommonSense.getAuthorName());
        healthCommonSense1.setReleaseDate(new Date(System.currentTimeMillis()));
        healthCommonSense1.setTopic(healthCommonSense.getTopic());
        return healthCommonSenseMapper.addHealthCommonSense(healthCommonSense1);
    }
}
