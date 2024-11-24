package com.cos.fluxtest;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class MyFilterConfig {

	@Bean
	public FilterRegistrationBean<Filter> addFilter() {
		System.out.println("필터 등록됨");
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter());
		bean.addUrlPatterns("/*");
		return bean;
	}
}
