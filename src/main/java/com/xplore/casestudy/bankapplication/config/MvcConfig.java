package com.xplore.casestudy.bankapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**", "/static/**","/webjars/**","/js/**",
                        "/css/**",
                        "/img/**")
                .addResourceLocations("/", "/static/", "/webjars/","/js/",
                        "/css/**",
                        "/img/**",
                        "classpath:/webjars/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(dateFormatter());
    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }


}
