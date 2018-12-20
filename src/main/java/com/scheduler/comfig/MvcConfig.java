package com.scheduler.comfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This class contains tho configuration of our web layer(слоя)
 * here we can add pages just by using their templates
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");//"/login" - page "login" - template logic
    }

    /**
     * Наступний метод буде направляти ф
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:/" + uploadPath + "/");//файл в файловій системі + місце зберігання
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");//файл в структурі проекту + місце зберігання
    }
}