package com.myweb.www.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// RootConfig 의 경로 설정
		return new Class[] {RootConfig.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// ServletConfig 의 경로 설정
		return new Class[] {ServletCongfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		
		return new String[] {"/"};
	}

	// EncodingFilter 설정
	@Override
	protected Filter[] getServletFilters() {
		// Filter 설정
		CharacterEncodingFilter encoding = new CharacterEncodingFilter();
		encoding.setEncoding("UTF-8");
		encoding.setForceEncoding(true); // 외부로 나가는 데이터도 인코딩 설정 => 외부로 나가는 데이터
		
		return new Filter[] {encoding};
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		// 그외 기타 사용자 설정
		// 사용자 지정 익셉션 처리 지정 : 4xx , 5xx ... error 페이지 설정(custom)이라고 보면 될듯
		
		// 2024 -01 -10 
		// 파일 업로드 설정
		String uploadLocation = "D:\\_myProject\\_java\\_fileUpload";
		int maxFileSize = 1024*1024*20; // 20mb
		int maxReqSize = maxFileSize*2; // 40mb 보통 maxFileSize의 2배로 설정함
		int fileSizeThreshold = maxFileSize; // 보통 maxFileSize와 동일함 
		// multipartConfing 설정
		MultipartConfigElement multipartConfig =
				new MultipartConfigElement(uploadLocation, maxFileSize,maxReqSize,fileSizeThreshold);
		registration.setMultipartConfig(multipartConfig);
	}
	
	
	
}
