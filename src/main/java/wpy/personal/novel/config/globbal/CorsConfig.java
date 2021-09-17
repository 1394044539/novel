package wpy.personal.novel.config.globbal;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域问题解决
 * @author pywang
 * @date 2020/11/30
 */
@Configuration
@Data
public class CorsConfig{

    @Bean
    public CorsFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowedMethods(
                Lists.newArrayList("GET","HEAD","POST","DELETE","PUT","OPTIONS"));
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowCredentials(true);

        source.registerCorsConfiguration("/**",corsConfiguration);
        return new CorsFilter(source);
    }
}
