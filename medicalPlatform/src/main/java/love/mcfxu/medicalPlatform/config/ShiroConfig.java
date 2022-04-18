package love.mcfxu.medicalPlatform.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){

        System.out.println("执行 ShiroFilterFactoryBean.shiroFilter()");

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //必须设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        //需要登录的接口，如果访问某个接口，需要登录却没登录，则调用此接口(如果不是前后端分离，则跳转页面)
        shiroFilterFactoryBean.setLoginUrl("/pub/need_login");

//        //登录成功，跳转url，如果前后端分离，则没这个调用
//        shiroFilterFactoryBean.setSuccessUrl("/");

        //没有权限，未授权就会调用此方法， 先验证登录-》再验证是否有权限
        shiroFilterFactoryBean.setUnauthorizedUrl("/pub/not_permit");

        //设置自定义filter
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("roleOrFilter",new CustomRolesOrAuthorizationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);


        //拦截器路径，坑一，部分路径无法进行拦截，时有时无；因为使用的是hashmap, 无序的，应该改为LinkedHashMap
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        //退出过滤器
        filterChainDefinitionMap.put("/logout","logout");

        //匿名可以访问，也是就游客模式
        filterChainDefinitionMap.put("/pub/**","anon");

        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v3/**", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");

        //登录用户才可以访问
        filterChainDefinitionMap.put("/authc/**","authc");

        //管理员角色才可以访问
        filterChainDefinitionMap.put("/admin/**","roles[admin]");

//        filterChainDefinitionMap.put("/authe/**","roles[admin]");


        //有编辑权限才可以访问
        filterChainDefinitionMap.put("/pub/captcha/get_captcha","perms[get_captcha]");
        filterChainDefinitionMap.put("/pub/captcha/check_captcha","perms[check_captcha]");
        filterChainDefinitionMap.put("/authe/dia/update","perms[dia_update]");
        filterChainDefinitionMap.put("/pub/patient_register","perms[patient_register]");
        filterChainDefinitionMap.put("/authe/dia/find","perms[dia_find]");
        filterChainDefinitionMap.put("/pub/find_all_service_info","perms[find_allserinfo]");
        filterChainDefinitionMap.put("/pub/find_all_plat_info","perms[find_allplainfo]");
        filterChainDefinitionMap.put("/pub/find_all_health_info","perms[find_allheainfo]");
        filterChainDefinitionMap.put("/authe/reg/list_allsheetsforone","perms[reg_listall_forone]");
        filterChainDefinitionMap.put("/authe/reg/add","perms[reg_add]");
        filterChainDefinitionMap.put("/authe/ordinary/update_user","perms[ordinary_upduser]");
        filterChainDefinitionMap.put("/admin/list_reg","perms[all_reg]");
        filterChainDefinitionMap.put("/admin/page_all_users","perms[all_user]");
        filterChainDefinitionMap.put("/admin/delete_user_by_id","perms[delete_user]");
        filterChainDefinitionMap.put("/admin/find_user_by_id","perms[find_user_byid]");
        filterChainDefinitionMap.put("/admin/find_by_phone","perms[find_user_byphone]");
        filterChainDefinitionMap.put("/admin/update_service_info","perms[update_serinfo]");
        filterChainDefinitionMap.put("/admin/delete_service_info_by_id","perms[delete_serviceinfo]");
        filterChainDefinitionMap.put("/admin/add_service_info","perms[add_serinfo]");
        filterChainDefinitionMap.put("/admin/update_plat_info","perms[update_plainfo]");
        filterChainDefinitionMap.put("/admin/update_health_info","perms[update_heainfo]");
        filterChainDefinitionMap.put("/admin/delete_health_info","perms[delete_heainfo]");
        filterChainDefinitionMap.put("/admin/add_health_info","perms[add_heainfo]");
        filterChainDefinitionMap.put("/admin/add_doctor","perms[add_doctor]");
        filterChainDefinitionMap.put("/authe/ordinary/show_info","perms[show_info]");
        filterChainDefinitionMap.put("/pub/find_all_evaluation_forvis","perms[find_eva_forvis]");
        filterChainDefinitionMap.put("/admin/delete_evaluation","perms[delete_eva]");
        filterChainDefinitionMap.put("/admin/find_all_evaluation_foradm","perms[find_eva_foradm]");
        filterChainDefinitionMap.put("/authe/reg/evaluate","perms[evaluate]");


        //坑二: 过滤链是顺序执行，从上而下，一般讲/** 放到最下面

        //authc : url定义必须通过认证才可以访问
        //anon  : url可以匿名访问
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //不是前后端分离的话，不用自定义sessionManager
        securityManager.setSessionManager(sessionManager());

        //使用自定义的cacheManager
        securityManager.setCacheManager(cacheManager());

        //设置realm(最好放到setSessionManager方法后)
        securityManager.setRealm(customRealm());

        return securityManager;
    }


    /**
     * 自定义realm
     * @return
     */
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm = new CustomRealm();

        customRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return customRealm;
    }

    /**
     * 密码加解密规则
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();

        //设置散列算法：这里使用的MD5算法
        credentialsMatcher.setHashAlgorithmName("md5");

        //散列次数，好比散列2次，相当于md5(md5(xxxx))
        credentialsMatcher.setHashIterations(2);

        return credentialsMatcher;
    }


    /**
     * 自定义sessionManager
     * @return
     */
    @Bean
    public SessionManager sessionManager(){

        CustomSessionManager customSessionManager = new CustomSessionManager();

        //会话超时时间,单位毫秒
        customSessionManager.setGlobalSessionTimeout(120000);

        //配置session持久化
        customSessionManager.setSessionDAO(redisSessionDAO());

        return customSessionManager;
    }

    /**
     * 配置redisManager
     *
     */
    public RedisManager getRedisManager(){
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("123.56.122.10:6379");
        redisManager.setPassword("youshuai~youyouqian~~");
        return redisManager;
    }

    /**
     * 配置具体cache实现类
     * @return
     */
    public RedisCacheManager cacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(getRedisManager());

        redisCacheManager.setExpire(120);

        return redisCacheManager;
    }

    /**
     * 自定义session持久化
     * @return
     */
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(getRedisManager());

        //设置sessionid生成器
        redisSessionDAO.setSessionIdGenerator(new CustomSessionIdGenerator());

        return redisSessionDAO;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor(); }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        return defaultAdvisorAutoProxyCreator; }

}