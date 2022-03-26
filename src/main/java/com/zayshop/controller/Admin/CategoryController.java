package com.zayshop.controller.Admin;

import com.zayshop.entity.Category;
import com.zayshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity getAllCategory(){
        return ResponseEntity.ok(categoryService.findAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id){
        Optional<Category> optional = categoryService.getCategoryById(id);
        if (optional.isEmpty()){
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.ok(optional.get());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Optional<Integer> id){
        if (id.isEmpty()) return ResponseEntity.badRequest().build();
        Optional<Category> optional = categoryService.getCategoryById(id.get());
        if (optional.isEmpty())
            return ResponseEntity.badRequest().build();
        else {
            optional.get().setActivated(false);
            categoryService.saveCategory(optional.get());
            return ResponseEntity.ok().build();
        }
    }
}
