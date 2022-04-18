package love.mcfxu.medicalPlatform.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import love.mcfxu.medicalPlatform.domain.entity.UserEntity;
import love.mcfxu.medicalPlatform.utils.JsonData;

import love.mcfxu.medicalPlatform.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "用户模块")
@RestController
@RequestMapping("authe/ordinary")
public class UserEntityController {


    @Autowired
    UserEntityService userService;


    /**
     * 查看个人信息
     * @param request
     * @return
     */
    @GetMapping("show_info")
    public JsonData showInfo(HttpServletRequest request){

        Object userId = request.getAttribute("user_id");
        int i = Integer.parseInt(userId.toString());

        UserEntity userEntity = userService.showUserInfo(i);
        return JsonData.buildSuccess(userEntity);
    }



    /**
     * 更新用户信息
     * @param user
     * @param userPhone
     * @param userPwd
     * @param userAccountNumber
     * @return
     */
    @ApiOperation(value = "更新用户信息")
    @PutMapping("update_user")
    public JsonData updateUser(@ApiParam(value = "需要去替换旧信息的新信息") @RequestBody UserEntity user,
                               @ApiParam(value = "原电话")@RequestParam(value = "user_phone",required = true) String userPhone,
                               @ApiParam(value = "原密码")@RequestParam(value = "user_pwd",required = true) String userPwd,
                               @ApiParam(value = "原账号")@RequestParam(value = "user_accountNumber",required = true) String userAccountNumber,
                               HttpServletRequest request){

        int id = userService.findByUserImportInformation(userPhone,userPwd,userAccountNumber);
        Object user_id = request.getAttribute("user_id");
        int userId = Integer.parseInt(user_id.toString());

        if (id!=-1){
            if (id!=userId){
                return JsonData.buildError("无权修改别人的信息");
            }

            UserEntity byUserPhone = userService.findoneByUserPhone(userPhone);
            String userName = user.getUserName();
            Integer userRole = user.getUserRole();
            if ((byUserPhone.getUserName().equals(userName))&&(byUserPhone.getUserRole().equals(userRole))){

                user.setUserId(userId);

                int i1 = userService.updateUser(user);
                if (i1==0){
                    return JsonData.buildSuccess();
                }
                else {
                    return JsonData.buildError("更新账户信息失败");
                }
            }
        }
        return JsonData.buildError("旧账号信息填写有误，修改账号信息失败");
    }







//    /**
//     * 登录
//     * @param userInfo
//     * @return
//     */
//    @PostMapping("login")
//    public JsonData login(@RequestBody Map<String,String> userInfo){
//
//        String token = userService.findByUserImportInformation(userInfo.get("user_phone"),
//                userInfo.get("user_pwd"),userInfo.get("user_account_number"));
//
//        return token == null ?JsonData.buildError("登录失败，账号密码错误"): JsonData.buildSuccess(token);
//
//    }


}
