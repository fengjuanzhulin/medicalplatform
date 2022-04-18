package love.mcfxu.medicalPlatform.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import love.mcfxu.medicalPlatform.utils.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * logout, 映射shiro自带的过滤器
 */
@Api(value = "登出模块")
@RestController
public class LogoutController {

    @ApiOperation(value = "登出界面")
    @RequestMapping("/logout")
    public JsonData findMyPlayRecord(){

        Subject subject = SecurityUtils.getSubject();

        if(subject.getPrincipals() != null ){

        }

        SecurityUtils.getSubject().logout();

        return JsonData.buildSuccess("logout成功");

    }

}
