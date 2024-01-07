package controllers;

import org.springframework.stereotype.Component;
import services.ProductService;

@Component
public class ProductWithImplicitAutowiredController {
    private final ProductService productService;

    //@Autowired is implicit (you must have only ONE constructor)
    public ProductWithImplicitAutowiredController(ProductService productService) {
        this.productService = productService;
    }

    public void addProduct() {
        productService.addProduct();
    }
}
