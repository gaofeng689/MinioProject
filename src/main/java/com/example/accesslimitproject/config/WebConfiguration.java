package com.example.accesslimitproject.config;

import com.example.accesslimitproject.interceptor.AccessLimitInteceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public AccessLimitInteceptor createAccessLimintInterceptor(){
        return new AccessLimitInteceptor();
    }

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(createAccessLimintInterceptor())
                .addPathPatterns("/pass/get")
                .addPathPatterns("/refuse/get");
    }
}
