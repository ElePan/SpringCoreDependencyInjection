package controllers;

import services.ProductNotAComponentService;


public class ProductNotAComponentController {
    private final ProductNotAComponentService productService;

    public ProductNotAComponentController(ProductNotAComponentService productNotAComponentService) {
        productService = productNotAComponentService;
    }

    public ProductNotAComponentService getProductService() {
        return productService;
    }

    public void addProduct() {
        productService.addProduct();
    }
}
