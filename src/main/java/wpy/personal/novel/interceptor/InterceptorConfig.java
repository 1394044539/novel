package wpy.personal.novel.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 自定义拦截器配置文件
 * @author pywang
 * @date
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

//    @Value("${upload.img.rootpath}")
//    private String rootPath;

    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(getMyInterceptor());
//        interceptorRegistration.addPathPatterns("/**");
        interceptorRegistration.excludePathPatterns("/error");
        interceptorRegistration.excludePathPatterns("/sysUser/loginByAccount");
        interceptorRegistration.excludePathPatterns("/sysUser/loginByPhone");
        interceptorRegistration.excludePathPatterns("/sysUser/getVerifyCode");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**").addResourceLocations("file:"+rootPath+ CharConstant.FILE_SEPARATOR);
    }
}
