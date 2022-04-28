package com.zayshop.controller.User;

import com.zayshop.service.CategoryService;
import com.zayshop.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity getAllProduct(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(required = false) String productName,
                                        @RequestParam(required = false) Integer categoryID) throws Exception {
        return ResponseEntity.ok(productService.getViewShop(page, size, productName, categoryID));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findProduct(id));
    }

    @GetMapping("/categories")
    public ResponseEntity getAllCategories(){
        return ResponseEntity.ok(categoryService.findAllCategory());
    }
}
