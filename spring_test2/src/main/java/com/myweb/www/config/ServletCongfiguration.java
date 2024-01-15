package com.myweb.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
//@ComponentScan(basePackages = "com.myweb.www")
@ComponentScan(basePackages = {"com.myweb.www.controller","com.myweb.www.handler"})
//	이런식으로 구역을 나눌 수 있다 => 데이터가 많을 경우 용이
public class ServletCongfiguration implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// resource 경로 설정 / 나중에 파일 업로드 경로 설정 추가
		// xml 의 resources mapping
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		
		// 2024 -01 -10
		// 파일 업로드 경로 설정 D:\_myProject\_java\_fileUpload
		registry.addResourceHandler("/fileupload/**").addResourceLocations("file:///D:\\_myProject\\_java\\_fileUpload\\");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// view 경로 설정 , prefix, suffix
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		
		registry.viewResolver(viewResolver);
	}
	
	// 2024 -01 -10
	// multipartResolver 설정
	// Bean 이름이 반드시 multipartResolver 이어야함!!!
	@Bean(name ="multipartResolver") // 생략가능하며 생략할 경우 getMultipartResolver 가 자동으로 들어간다.
	public MultipartResolver getMultipartResolver() {
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return multipartResolver;
	}
	
}