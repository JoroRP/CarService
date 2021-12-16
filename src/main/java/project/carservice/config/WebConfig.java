package project.carservice.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/**")
               .addResourceLocations("classpath:/static/","classpath:/images/")
                .setCachePeriod(0);
       // registry.addResourceHandler("/videos/**").addResourceLocations("file:videos/");
    }
}
