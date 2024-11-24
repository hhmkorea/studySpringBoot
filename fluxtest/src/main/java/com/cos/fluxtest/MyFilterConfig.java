package com.cos.fluxtest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class MyFilterConfig {

	@Autowired
	private EventNotify eventNotify;
	
	@Bean
	public FilterRegistrationBean<Filter> addFilter1() {
		System.out.println("필터 등록됨");
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter(eventNotify));
		bean.addUrlPatterns("/sse");
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<Filter> addFilter2() {
		System.out.println("필터 등록됨");
		FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>(new MyFilter2(eventNotify));
		bean.addUrlPatterns("/add");
		return bean;
	}
}
