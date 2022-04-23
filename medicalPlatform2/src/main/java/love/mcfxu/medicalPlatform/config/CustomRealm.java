package love.mcfxu.medicalPlatform.config;

import love.mcfxu.medicalPlatform.domain.model.Permission;
import love.mcfxu.medicalPlatform.domain.model.Role;
import love.mcfxu.medicalPlatform.domain.model.User;
import love.mcfxu.medicalPlatform.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义realm
 */

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 进行权限校验的时候回调用
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权 doGetAuthorizationInfo");


        Object primaryPrincipal = principals.getPrimaryPrincipal();
        User user = userService.findAllUserInfoByUsername(primaryPrincipal.toString());


        User user0 = userService.findAllUserInfoByUsername(user.getUsername());


        List<String> stringRoleList = new ArrayList<>();
        List<String> stringPermissionList = new ArrayList<>();


        List<Role> roleList = user0.getRoleList();

        for(Role role : roleList){
            stringRoleList.add(role.getName());

            List<Permission> permissionList = role.getPermissionList();

            for(Permission p: permissionList){
                if(p!=null){
                    stringPermissionList.add(p.getName());
                }
            }

        }



        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(stringRoleList);
        simpleAuthorizationInfo.addStringPermissions(stringPermissionList);

        return simpleAuthorizationInfo;
    }



    /**
     * 用户登录的时候会调用
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {


        System.out.println("认证 doGetAuthenticationInfo");

        UsernamePasswordToken  token1 = (UsernamePasswordToken)token;

        String username = token1.getUsername();

//        String username = (String)token.getPrincipal();

        System.out.println("==================--------------"+token1);

        User user =  userService.findAllUserInfoByUsername(username);


        String pwd = user.getPassword();
        if(pwd == null || "".equals(pwd)){
            return null;
        }

        return new SimpleAuthenticationInfo(username, user.getPassword(), getName());
    }
}
