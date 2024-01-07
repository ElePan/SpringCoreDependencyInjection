package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.ProductService;

@Component
public class ProductController {
    @Autowired
    private ProductService productService; //add the service as an attribute

    public void addProduct() {
        productService.addProduct();
    }
}
