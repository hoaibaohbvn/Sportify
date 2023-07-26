package duan.sportify;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import duan.sportify.interceptor.GlobalInterceptor;


/**
 * Cấu hình Interceptor toàn cầu
 * @author USER
 * @Configuration đánh dấu cấu hình
 * 
 */

@SuppressWarnings("unused")
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	@Autowired
	GlobalInterceptor globalInterceptor;
	/**
	 * excludePathPatterns("/rest/**", "/admin/**", "/assets/**") chỉ định các URL mà user ko được áp dụng
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(globalInterceptor)
		.addPathPatterns("/**")
		.excludePathPatterns("/rest/**", "/admin/**", "/assets/**");
	}
}
