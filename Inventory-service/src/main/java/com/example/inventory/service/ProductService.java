package com.example.inventory.service;

import com.example.common.InventoryRequest;
import com.example.common.InventoryResponse;
import com.example.inventory.model.Product;
import com.example.inventory.repo.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;
    @Transactional
    public InventoryResponse add(InventoryRequest request) {
        Product product = repository.getReferenceById(request.getProductId());
        Integer availableQuantity = product.getQuantity();
        product.setQuantity(availableQuantity + request.getQuantity());
        repository.save(product);
        return  new InventoryResponse(request.getProductId(),request.getQuantity(),"SUCCESS");
    }

    @Transactional
    public InventoryResponse deduct(InventoryRequest request) {
        Product product = repository.getReferenceById(request.getProductId());
        Integer availableQuantity = product.getQuantity();
        if(availableQuantity < request.getQuantity()) {
            return new InventoryResponse(request.getProductId(), request.getQuantity(), "FAILURE");
        }
        product.setQuantity(availableQuantity - request.getQuantity());
        repository.save(product);
        return  new InventoryResponse(request.getProductId(),request.getQuantity(),"SUCCESS");
    }
}
