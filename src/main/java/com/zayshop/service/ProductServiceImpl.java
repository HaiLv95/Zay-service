package com.zayshop.service;

import com.zayshop.entity.Product;
import com.zayshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl{
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product deleteProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findByID(Integer id) {
        return productRepository.findById(id);
    }
}
