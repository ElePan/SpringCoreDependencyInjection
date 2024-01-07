package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import services.ProductService;

@Component
public class ProductWithImmutableController {
    private final ProductService productService;

    @Autowired
    public ProductWithImmutableController(ProductService productService) {
        this.productService = productService;
    }

    public void addProduct() {
        productService.addProduct();
    }
}
