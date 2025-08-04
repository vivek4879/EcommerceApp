package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import com.ecommerce.project.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//Controller will only be restricted to handling and routing requests.

@RestController // tells Spring that this class is a web Controller, i.e. it will handle HTTP requests.
// And the return value should be directly written to response body. Anything returned is automatically serialized to JSON.
public class CategoryController {


    private CategoryService categoryService;//created object of service

    //expecting Spring to inject on runtime and for spring to inject, it has to manage the categoryService bean.
    // For that need to add some annotation for spring to know that it is supposed to manage it in the categoryServiceImpl class
    //this is constructor injection
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/api/public/Categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }
    @PostMapping("/api/public/Categories")
    public String addCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return "Category added successfully";
    }

    @DeleteMapping("/api/admin/Categories/{CategoryId}")
    public String deleteCategory(@PathVariable Long CategoryId){
        String status = categoryService.deleteCategory(CategoryId);
        return status;
    }
}
