package com.zayshop.controller.Admin;

import com.zayshop.dto.ProductDTO;
import com.zayshop.entity.Category;
import com.zayshop.entity.Product;
import com.zayshop.service.CategoryService;
import com.zayshop.service.ProductServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CategoryService categoryService;

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
    public ResponseEntity updateProduct(@RequestBody Optional<ProductDTO> dto){
        if (dto.isEmpty()) return ResponseEntity.badRequest().build();
        Optional<Category> category = categoryService.getCategoryById(dto.get().getCategoryID());
        Product product = new Product();
        BeanUtils.copyProperties(dto.get(), product);
        product.setCreateDate(LocalDate.now());
        product.setDesc("a");
        product.setCategoryID(category.get());
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Optional<Integer> id){
        Optional<Product> product = productService.getProductById(id.get());
        if (product.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            product.get().setAvailable(!product.get().getAvailable());
            Product productDelete = productService.deleteProduct(product.get());
            return ResponseEntity.ok(productDelete);
        }
    }
}
