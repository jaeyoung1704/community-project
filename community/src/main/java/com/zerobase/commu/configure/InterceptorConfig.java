package com.zerobase.commu.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zerobase.commu.intercepter.LoginInterceptor;

//인터셉터 등록
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(new LoginInterceptor())
	    .addPathPatterns("/board/**")
	    .addPathPatterns("/admin/**")
	    .addPathPatterns("/member/**")
	    // 로그인,회원가입 페이지는 비로그인으로 접근가능
	    .excludePathPatterns("/member/logIn")
	    .excludePathPatterns("/member/signUp");
    }

}
