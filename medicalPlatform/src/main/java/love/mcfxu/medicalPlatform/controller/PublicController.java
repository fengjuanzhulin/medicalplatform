package love.mcfxu.medicalPlatform.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import love.mcfxu.medicalPlatform.domain.entity.HealthCommonSense;
import love.mcfxu.medicalPlatform.domain.entity.PublicInformation;
import love.mcfxu.medicalPlatform.domain.entity.ServiceEvaluation;
import love.mcfxu.medicalPlatform.domain.entity.ServiceInformation;
import love.mcfxu.medicalPlatform.domain.model.UserQuery;
import love.mcfxu.medicalPlatform.service.*;
import love.mcfxu.medicalPlatform.utils.CommonUtils;
import love.mcfxu.medicalPlatform.utils.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "公共页面")
@RestController
@RequestMapping("pub")
public class PublicController {

    @Autowired
    UserEntityService userService;

    @Autowired
    ServiceInformationService serviceInformationService;

    @Autowired
    PublicInformationService publicInformationService;

    @Autowired
    HealthCommonSenseService healthCommonSenseService;

    @Autowired
    ServiceEvaluationService serviceEvaluationService;

    @ApiOperation(value = "需要登陆")
    @RequestMapping("need_login")
    public JsonData needLogin(){

        return JsonData.buildSuccess("请使用相应的账号登录",-1);

    }

    @ApiOperation(value = "没有权限")
    @RequestMapping("not_permit")
    public JsonData notPermit(){

        return JsonData.buildSuccess("拒绝访问，没权限",-1);
    }



    /**
     * 注册用户(患者)
     * @param patientInfo
     * @return
     */
    @ApiOperation(value = "注册用户(患者)")
    @PostMapping("patient_register")
    public JsonData register(@RequestBody Map<String,String> patientInfo ){
        String user_name = patientInfo.get("user_name");
        if ((patientInfo.get("user_role").equals("2"))&&(userService.findByUserName(user_name)==null)){
            int rows = userService.save(patientInfo);

            return rows == 0 ? JsonData.buildSuccess(): JsonData.buildError("注册失败，请重试");}
        else {
            return JsonData.buildError("账号注册不合规，请更换角色定位");
        }
    }


    /**
     * 登录接口
     * @param userQuery
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "登录接口")
    @PostMapping("login")
    public JsonData login(@RequestBody UserQuery userQuery, HttpServletRequest request, HttpServletResponse response){

        Subject subject = SecurityUtils.getSubject();
        Map<String,Object> info = new HashMap<>();
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userQuery.getName(), userQuery.getPwd());

            subject.login(usernamePasswordToken);

            info.put("msg","登录成功");
            info.put("session_id", subject.getSession().getId());

            return JsonData.buildSuccess(info);

        }catch (Exception e){
            e.printStackTrace();

            return JsonData.buildError("账号或者密码错误");

        }
    }

    /**
     * 查看医生服务信息
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "查看医生服务信息")
    @GetMapping("find_all_service_info")
    public JsonData findAllServiceInformation(@RequestParam(value = "page",defaultValue = "1") int page,
                                              @RequestParam(value = "size",defaultValue = "10") int size){

        PageHelper.startPage(page, size);
        List<ServiceInformation> allServiceInformation = serviceInformationService.findAllServiceInformation();
        PageInfo<ServiceInformation> PageInfo = new PageInfo<>(allServiceInformation);
        return JsonData.buildSuccess(CommonUtils.sortPage(PageInfo));
    }


    /**
     * 查看平台简单介绍
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "查看平台简单介绍")
    @GetMapping("find_all_plat_info")
    public JsonData findAllPlatformInformation(@RequestParam(value = "page",defaultValue = "1") int page,
                                              @RequestParam(value = "size",defaultValue = "10") int size){

        PageHelper.startPage(page, size);
        List<PublicInformation> allPublicInformation = publicInformationService.findAllPublicInformation();
        PageInfo<PublicInformation> PageInfo = new PageInfo<>(allPublicInformation);
        return JsonData.buildSuccess(CommonUtils.sortPage(PageInfo));
    }


    /**
     * 查询健康小常识
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "查询健康小常识")
    @GetMapping("find_all_health_info")
    public JsonData findAllHealthCommonSenseInformation(@RequestParam(value = "page",defaultValue = "1") int page,
                                                       @RequestParam(value = "size",defaultValue = "10") int size){

        PageHelper.startPage(page, size);
        List<HealthCommonSense> allHealthCommonSense = healthCommonSenseService.findAllHealthCommonSense();
        PageInfo<HealthCommonSense> PageInfo = new PageInfo<>(allHealthCommonSense);
        return JsonData.buildSuccess(CommonUtils.sortPage(PageInfo));
    }


    @ApiOperation(value = "查看大致的咨询评价")
    @GetMapping("find_all_evaluation_forvis")
    public JsonData findAllEvaluationForvis(@RequestParam(value = "page",defaultValue = "1") int page,
                                      @RequestParam(value = "size",defaultValue = "10") int size){

        PageHelper.startPage(page, size);
        List<ServiceEvaluation> allServiceEvaluationforvis = serviceEvaluationService.findAllServiceEvaluationforvis();
        PageInfo<ServiceEvaluation> PageInfo = new PageInfo<>(allServiceEvaluationforvis);
        return JsonData.buildSuccess(CommonUtils.sortPage(PageInfo));

    }

}
