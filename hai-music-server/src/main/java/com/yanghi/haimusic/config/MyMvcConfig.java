package com.yanghi.haimusic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //设置允许跨域的路径 /**表示所有
                .allowedOriginPatterns("*") //设置允许跨域请求的域名 /*表示所有
                .allowCredentials(true) //是否允许Cookie
                .allowedMethods("GET", "POST", "DELETE", "PUT") //设置允许的请求方式
                .allowedHeaders("*") //设置允许的header属性 *所有
                .maxAge(3600); //设置跨域允许时间
    }

    //增加访问路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/song/**").addResourceLocations("classpath:/static/song/");
    }
}
