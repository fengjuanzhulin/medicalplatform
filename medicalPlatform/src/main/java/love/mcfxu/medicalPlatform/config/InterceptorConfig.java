//package love.mcfxu.medicalPlatform.config;
//
//import love.mcfxu.medicalPlatform.interceptor.LoginIntercepter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//
//    /**
//     * 增加拦截器
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/user/*/**")
//                .excludePathPatterns("/user/login","/user/register");
//        WebMvcConfigurer.super.addInterceptors(registry);
//    }
//}
