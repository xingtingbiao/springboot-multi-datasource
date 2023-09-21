package com.cfex.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        WebMvcConfigurer.super.configurePathMatch(configurer);
//        configurer.addPathPrefix("/api/v1", c -> c.isAnnotationPresent(RestController.class));
//    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowedMethods("*")
//                .allowCredentials(true)
//                .maxAge(3600L)
//                .allowedHeaders("*")
//                .exposedHeaders("*");
//    }

}
