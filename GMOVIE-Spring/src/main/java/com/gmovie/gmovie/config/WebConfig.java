package com.gmovie.gmovie.config;

import com.gmovie.gmovie.interceptor.SampleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //註冊TestInterceptor攔截器
        InterceptorRegistration registration = registry.addInterceptor(new SampleInterceptor());
        registration.addPathPatterns("http://localhost:3000", "/summary");
        // registration.addPathPatterns("/**"); //所有路徑都被攔截
        // registration.excludePathPatterns(    //添加不攔截路徑
        //         "/login",                    //登錄路徑
        //         "/**/*.html",                //html靜態資源
        //         "/**/*.js",                  //js靜態資源
        //         "/**/*.css"                  //css靜態資源
        // );
    }

}
