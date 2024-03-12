package com.project.shopapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    //show all categories
    @GetMapping("")
    public ResponseEntity<String> getAllCategories() {
        return ResponseEntity.ok("getAllCategories");
    }
    @PostMapping("")
    public ResponseEntity<String> insertCategory() {
        return ResponseEntity.ok("insertCategory");
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id){
        return ResponseEntity.ok("updateCategory id =" + id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("deleteCategory  id =" + id);
    }
}
