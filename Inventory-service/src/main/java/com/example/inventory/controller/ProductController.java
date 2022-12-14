package com.example.inventory.controller;


import com.example.common.InventoryRequest;
import com.example.common.InventoryResponse;
import com.example.inventory.model.Product;
import com.example.inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public InventoryResponse addProduct(@RequestBody InventoryRequest request) {
        return productService.add(request);
    }
    @PostMapping("/deduct")
    public InventoryResponse deductProduct(@RequestBody InventoryRequest request) {
        return productService.deduct(request);
    }
}
