package love.mcfxu.medicalPlatform.service.impl;

import love.mcfxu.medicalPlatform.config.CacheKeyManager;
import love.mcfxu.medicalPlatform.domain.entity.ServiceInformation;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import love.mcfxu.medicalPlatform.mapper.ServiceInformationMapper;
import love.mcfxu.medicalPlatform.service.ServiceInformationService;
import love.mcfxu.medicalPlatform.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ServiceInformationServiceImpl implements ServiceInformationService {

    @Autowired
    ServiceInformationMapper serviceInformationMapper;

    @Autowired
    BaseCache baseCache;

    @Override
    public List<ServiceInformation> findAllServiceInformation() {

        try{

            Object cacheObj =  baseCache.getOneHourCache().get(CacheKeyManager.USER_LIST_KEY, ()->{

                List<ServiceInformation> allServiceInformation = serviceInformationMapper.findAllServiceInformation();

                System.out.println("从数据库里面找医务服务列表");

                return allServiceInformation;

            });

            if(cacheObj instanceof List){
                List<ServiceInformation> serviceList = (List<ServiceInformation>)cacheObj;
                return serviceList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateServiceInformation(ServiceInformation serviceInformation) {
        return serviceInformationMapper.updateServiceInformation(serviceInformation);
    }

    @Override
    public int deleteServiceInformationById(int serviceDepartmentId) {
        return serviceInformationMapper.deleteServiceInformationById(serviceDepartmentId);
    }

    @Override
    public int addServiceInformation(ServiceInformation serviceInformation) {
        return serviceInformationMapper.addServiceInformation(serviceInformation);
    }

    @Override
    public ServiceInformation findServiceInformationByDoctorId(int doctorId) {
        return serviceInformationMapper.findServiceInformationByDoctorId(doctorId);
    }
}
