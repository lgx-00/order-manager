package plus.lgx.ordermanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import plus.lgx.ordermanager.interceptor.LoginInterceptor;
import plus.lgx.ordermanager.interceptor.PermissionInterceptor;

import java.util.Arrays;
import java.util.List;

/**
 * 类型：SpringMVCConfig
 *
 * @author lgx
 * @since 2024/8/29
 */
@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {

    //不做拦截的路径
    //上面一行是静态资源路径
    //下面一行是接口访问路径
    private static final List<String> EXCLUDE_PATH = Arrays.asList(
            "/", "css/**", "js/**", "img/**",
            "json/**", "fonts/**", "/**/*.html", "/webjars/**", "/swagger-resources/**",
            "/login", "/error"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置了一个order 其中值越小代表拦截器优先级越高
        registry.addInterceptor(new LoginInterceptor()).
                excludePathPatterns(EXCLUDE_PATH).order(0);

        // 权限拦截器
//        registry.addInterceptor(new PermissionInterceptor())
//                .excludePathPatterns(EXCLUDE_PATH).order(1);
    }

}
