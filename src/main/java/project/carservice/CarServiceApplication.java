package project.carservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarServiceApplication {



    //public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
    //    registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/statics/")
   //             .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
   // }


    public static void main(String[] args) {
        SpringApplication.run(CarServiceApplication.class, args);
    }

}
