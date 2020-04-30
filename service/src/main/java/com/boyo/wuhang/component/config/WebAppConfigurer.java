package com.boyo.wuhang.component.config;

import com.boyo.wuhang.component.converter.String2DateTimeConverter;
import com.boyo.wuhang.component.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration          //使用注解 实现配置
public class WebAppConfigurer implements WebMvcConfigurer {
	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;
	@Autowired
	private LoginInterceptor loginInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//登录拦截的管理器
		InterceptorRegistration registration = registry.addInterceptor(loginInterceptor);     //拦截的对象会进入这个类中进行判断
		registration.addPathPatterns("/api/**");                    //指定路径被拦截
		registration.excludePathPatterns("/sso/login");
		registration.excludePathPatterns("/sso/no_login");       //添加不拦截路径
		registration.excludePathPatterns("/sso/no_auth");
		registration.excludePathPatterns("/sso/no_uri_auth");
		System.out.println("---------------注册登入拦截器----------------");
	}

	/**
	 * 增加字符串转日期的功能
	 */
	@PostConstruct
	public void initEditableValidation() {

		ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
		if (initializer.getConversionService() != null) {
			GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();

			genericConversionService.addConverter(new String2DateTimeConverter());
			System.out.println("---------------注册全局String转Date转换器----------------");
		}

	}

}