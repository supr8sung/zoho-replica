package com.xebia.fs101.zohoreplica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    public WebMvcConfig() {
        super();
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {


        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("signup");

    }

}
