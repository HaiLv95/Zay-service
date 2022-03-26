package com.zayshop.controller.Admin;

import com.zayshop.entity.Product;
import com.zayshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("")
    public ResponseEntity getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Optional<Integer> id){
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        Optional<Product> optional = productService.getProductById(id.get());
        if (optional.isEmpty())
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(optional.get());
    }

    @PostMapping("/add")
    public ResponseEntity saveProduct(@RequestBody Optional<Product> product){
        if (product.isEmpty()) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(productService.saveProduct(product.get()));
    }

    @PutMapping("/update")
    public ResponseEntity updateProduct(@RequestBody Optional<Product> product){
        if (product.isEmpty()) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(productService.saveProduct(product.get()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Optional<Integer> id){
        if (id.isEmpty()) return ResponseEntity.badRequest().build();

        Optional<Product> product = productService.getProductById(id.get());

        if (product.isEmpty()) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok().build();
    }
}
