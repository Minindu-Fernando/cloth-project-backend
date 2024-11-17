package edu.icet.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map URL path /images/** to the local folder src/main/resources/image/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:src/main/resources/image/");
    }
}
