package love.mcfxu.medicalPlatform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import love.mcfxu.medicalPlatform.config.CacheKeyManager;
import love.mcfxu.medicalPlatform.domain.entity.*;
import love.mcfxu.medicalPlatform.service.*;
import love.mcfxu.medicalPlatform.utils.CommonUtils;
import love.mcfxu.medicalPlatform.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Api(value = "管理员模块")
@RestController
@RequestMapping("admin")
public class UserAdminController {

    @Autowired
    UserEntityService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RegistrationSheetService registrationSheetService;

    @Autowired
    ServiceInformationService serviceInformationService;

    @Autowired
    PublicInformationService publicInformationService;

    @Autowired
    HealthCommonSenseService healthCommonSenseService;

    @Autowired
    ServiceEvaluationService serviceEvaluationService;


    /**
     * 用有缓存的方式查找挂号单列表
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "用有缓存的方式查找挂号单列表")
    @GetMapping(value = "list_reg")
    public JsonData listSheetCache(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        PageHelper.startPage(page, size);

        Object cacheObj = redisTemplate.opsForValue().get(CacheKeyManager.REGISTRATION_TABLE_ID);

        if (cacheObj != null) {
            List<RegistrationSheet> list = (ArrayList<RegistrationSheet>) cacheObj;
            PageInfo<RegistrationSheet> sheetPageInfo = new PageInfo<>(list);
            return JsonData.buildSuccess(CommonUtils.sortPage(sheetPageInfo));

        } else {
            List<RegistrationSheet> registrationSheet = registrationSheetService.findAllRegistrationSheet();
            redisTemplate.opsForValue().set(CacheKeyManager.REGISTRATION_TABLE_ID, registrationSheet, 7, TimeUnit.SECONDS);

            PageInfo<RegistrationSheet> sheetPageInfo = new PageInfo<>(registrationSheet);
            return JsonData.buildSuccess(CommonUtils.sortPage(sheetPageInfo));
        }
    }

    /**
     * 分页查询用户
     *
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "分页查询用户")
    @GetMapping("page_all_users")
    public JsonData pageUser(@RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size) {
        PageHelper.startPage(page, size);
        List<UserEntity> userList = userService.findAllUsers();
        PageInfo<UserEntity> pageInfo = new PageInfo<>(userList);
        return JsonData.buildSuccess(CommonUtils.sortPage(pageInfo));
    }

    /**
     * 根据id删除用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据id删除用户")
    @PutMapping("delete_user_by_id")
    public JsonData deleteUserById(@RequestParam(value = "user_id", required = true) int userId) {
        int i = userService.deleteUserById(userId);
        return i==1?JsonData.buildSuccess():JsonData.buildError(""+i);
    }


    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据id查找用户")
    @GetMapping("find_user_by_id")
    public JsonData findUserById(@RequestParam(value = "user_id", required = true) int userId) {
        UserEntity userById = userService.findUserById(userId);
        return  JsonData.buildSuccess(userById);
    }

    /**
     * 根据手机号查找用户登录痕迹
     * @param request
     * @return
     */
    @ApiOperation(value = "根据手机号查找用户登录痕迹")
    @GetMapping("find_by_phone")
    public JsonData findUserInfoByToken(HttpServletRequest request) {
        String userPhone = (String) request.getAttribute("user_phone");
        if (userPhone == null) {
            return JsonData.buildError("查询失败");
        }
        List<UserEntity> user = userService.findByUserPhone(userPhone);
        return JsonData.buildSuccess(user);
    }

    /**
     * 更新医生服务信息
     * @param serviceInformation
     * @return
     */
    @ApiOperation(value = "更新医生服务信息")
    @PutMapping("update_service_info")
    public JsonData updateServiceInformation(@RequestBody ServiceInformation serviceInformation){
        int i = serviceInformationService.updateServiceInformation(serviceInformation);
        if (i==0){
            return JsonData.buildError("更新该医生服务信息失败，您未作改动");
        }
        return JsonData.buildSuccess("更新该医生服务信息成功");
    }

    /**
     * 根据服务信息id删除医生服务
     * @param serviceDepartmentId
     * @return
     */
    @ApiOperation(value = "根据服务信息id删除医生服务")
    @PutMapping("delete_service_info_by_id")
    public JsonData deleteServiceInformationById(@RequestParam(value = "service_department_id",required = true
    ) int serviceDepartmentId){
        int i = serviceInformationService.deleteServiceInformationById(serviceDepartmentId);
        return i==0?JsonData.buildError("删除医生服务失败"):JsonData.buildSuccess();
    }

    /**
     * 添加医生服务信息
     * @param serviceInformation
     * @return
     */
    @ApiOperation(value = "添加医生服务信息")
    @PutMapping("add_service_info")
    public JsonData addServiceInformation(@RequestBody ServiceInformation serviceInformation){
        int i = serviceInformationService.addServiceInformation(serviceInformation);
        return i==0?JsonData.buildError("添加医生服务信息失败"):JsonData.buildSuccess();
    }

    /**
     * 更新平台简介
     * @param publicInformation
     * @return
     */
    @ApiOperation(value = "更新平台简介")
    @PutMapping("update_plat_info")
    public JsonData updatePlatInformation(@RequestBody PublicInformation publicInformation){
        int i = publicInformationService.updatePublicInformation(publicInformation);
        return i==0?JsonData.buildError("更新平台简介失败"):JsonData.buildSuccess();
    }

    /**
     * 更新健康小常识
     * @param healthCommonSense
     * @return
     */
    @ApiOperation(value = "更新健康小常识")
    @PutMapping("update_health_info")
    public JsonData updateHealthInformation(@RequestBody HealthCommonSense healthCommonSense){
        int i = healthCommonSenseService.updateHealthCommonSense(healthCommonSense);
        return  i==1?JsonData.buildSuccess("更新健康小常识更新成功"):JsonData.buildError("更新健康小常识更新失败");
    }

    /**
     * 删除健康小常识
     * @param healthCommonSenseId
     * @return
     */
    @ApiOperation(value = "删除健康小常识")
    @PutMapping("delete_health_info")
    public JsonData deleteHealthInformation(@RequestParam(value = "health_common_sense_id") int healthCommonSenseId){
        int i = healthCommonSenseService.delectHealthCommonSenseById(healthCommonSenseId);
        return i==0?JsonData.buildError("删除健康小常识失败"):JsonData.buildSuccess();
    }


    /**
     * 添加健康小常识
     * @param healthCommonSense
     * @return
     */
    @ApiOperation(value = "添加健康小常识")
    @PutMapping("add_health_info")
    public JsonData addHealthInformation(@RequestBody HealthCommonSense healthCommonSense){
        int i = healthCommonSenseService.addHealthCommonSense(healthCommonSense);
        return i==0?JsonData.buildError("添加健康小常识失败"):JsonData.buildSuccess();
    }

    /**
     * 添加医生信息
     * @param doctorInfo
     * @return
     */
    @ApiOperation(value = "添加医生信息")
    @PutMapping("add_doctor")
    public JsonData addDoctor(@RequestBody Map<String,String> doctorInfo){
        String user_name = doctorInfo.get("user_name");
        if ((doctorInfo.get("user_role").equals("1"))&&(userService.findByUserName(user_name)==null)){
            int rows = userService.save(doctorInfo);
            return rows == 0 ? JsonData.buildSuccess(): JsonData.buildError("注册失败，请重试");
        }
        return JsonData.buildError("管理员只能添加医生信息");
    }


    @ApiOperation(value = "查询咨询评价详情")
    @GetMapping("find_all_evaluation_foradm")
    public JsonData findAllEvaluationForadm(@RequestParam(value = "page",defaultValue = "1") int page,
                                            @RequestParam(value = "size",defaultValue = "10") int size){

        PageHelper.startPage(page, size);
        List<ServiceEvaluation> allServiceEvaluationforvis = serviceEvaluationService.findAllServiceEvaluationforadm();
        PageInfo<ServiceEvaluation> PageInfo = new PageInfo<>(allServiceEvaluationforvis);
        return JsonData.buildSuccess(CommonUtils.sortPage(PageInfo));

    }


    @ApiOperation(value = "删除指定咨询评价")
    @PutMapping("delete_evaluation")
    public JsonData deleteEvaluation(@RequestParam(value = "service_evaluation_id") int serviceEvaluationId){

        int i = serviceEvaluationService.deleteServiceEvaluation(serviceEvaluationId);
        return i==1?JsonData.buildSuccess():JsonData.buildError("删除失败", i);

    }

}
