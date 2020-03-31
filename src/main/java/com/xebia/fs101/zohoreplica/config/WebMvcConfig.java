package com.xebia.fs101.zohoreplica.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    public WebMvcConfig() {
        super();
    }



    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login")
      //  /Users/codeMars/Downloads/GIT/Repositories/zoho-replica/front-end/build/index.html
        registry.addViewController("/").setViewName("login");
registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("signup");
    }

 //   @Override
//    public void addResourceHandlers(
//            ResourceHandlerRegistry registry) {
////
////        registry.addResourceHandler("/static/**")
////                .addResourceLocations("/front-end/build/static/");
////        registry.addResourceHandler("/*.js")
////                .addResourceLocations("/WEB-INF/view/react/build/");
////        registry.addResourceHandler("/*.json")
////                .addResourceLocations("/WEB-INF/view/react/build/");
////        registry.addResourceHandler("/*.ico")
////                .addResourceLocations("/WEB-INF/view/react/build/");
//        registry.addResourceHandler("/index.html")
//                .addResourceLocations("/front-end/build/index.html");
//    }
}
