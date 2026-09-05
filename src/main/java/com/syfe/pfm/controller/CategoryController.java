package com.syfe.pfm.controller;

import com.syfe.pfm.model.Category;
import com.syfe.pfm.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Map<String, List<Category>>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        Map<String, List<Category>> response = new HashMap<>();
        response.put("categories", categories);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Category> createCustomCategory(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String type = request.get("type");

        Category category = categoryService.createCustomCategory(name, type);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Map<String, String>> deleteCustomCategory(@PathVariable String name) {
        categoryService.deleteCustomCategory(name);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Category deleted successfully");
        return ResponseEntity.ok(response);
    }
}