package config;


import controllers.ProductNotAComponentController;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import services.ProductNotAComponentService;


@Configuration
//Annotation to tell spring that class is a config one
@ComponentScan(basePackages = {"controllers", "services"})
//Annotations to tell spring we have components that he needs to add to the config
public class AppConfig {

    @Bean
    //@Autowires implicit here
    ProductNotAComponentController productNotAComponentController(ProductNotAComponentService productNotAComponentService){
        return new ProductNotAComponentController(productNotAComponentService);
    }

    @Bean
    //@Autowires implicit here
    ProductNotAComponentController productNotAComponentControllerSecondOption(){
        return new ProductNotAComponentController(productNotAComponentService());
        //you can take directly the instance from the context, it is not a method call
    }

    @Bean
    ProductNotAComponentService productNotAComponentService(){
        return new ProductNotAComponentService();
    }
}
//You are declaring a config class in this file
