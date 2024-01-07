package main;

import config.AppConfig;
import controllers.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        var productController = context.getBean(ProductController.class);
        productController.addProduct();

        var productWithImmutableController = context.getBean(ProductWithImmutableController.class);
        productWithImmutableController.addProduct();

        var productWithImplicitAutowiredController = context.getBean(ProductWithImplicitAutowiredController.class);
        productWithImplicitAutowiredController.addProduct();
        //productWithImplicitAutowiredController is exactly the same of productWithImmutableController

        var productNotAComponentController = context.getBean("productNotAComponentController", ProductNotAComponentController.class);
        productNotAComponentController.addProduct();

        var productNotAComponentControllerSecondOption = context.getBean("productNotAComponentControllerSecondOption", ProductNotAComponentController.class);
        productNotAComponentController.addProduct();

        assert productNotAComponentController.getProductService()
                .equals(productNotAComponentControllerSecondOption.getProductService());
        //as both components are using the same service instance we expect a true here

        //All the controllers are doing the same, but they are implemented in different way
        //productWithImmutableController is the recommended way to use @Autowired
    }
}