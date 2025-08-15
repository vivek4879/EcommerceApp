package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import com.ecommerce.project.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

//Controller will only be restricted to handling and routing requests.

@RestController // tells Spring that this class is a web Controller, i.e. it will handle HTTP requests.
// And the return value should be directly written to response body. Anything returned is automatically serialized to JSON.
@RequestMapping("/api")
public class CategoryController {


    private CategoryService categoryService;//created object of service

    //expecting Spring to inject on runtime and for spring to inject, it has to manage the categoryService bean.
    // For that need to add some annotation for spring to know that it is supposed to manage it in the categoryServiceImpl class
    //this is constructor injection
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getCategories() {
        CategoryResponse categoryResponse =  categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }
    @PostMapping("/public/categories")
    //the @Valid will do data validation in the request body itself so that the error is user friendly.
    // We still have to put validation in the Category class. This Valid here is a Spring thing which  will check if the incoming
    // request body is fulfilling the constraint defined in the model.
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>( "Category added successfully",  HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{CategoryId}")
    //ResponseEntity is a spring class that represents the entire HTTP response.Gives full control over HTTP response.
    //Helps return custom status codes and error messages
    public ResponseEntity<String> deleteCategory(@PathVariable Long CategoryId){
        //try block contains code which might throw an exception
        //commented try cath as we have implemented app vide exception handling
//        try{
            String status = categoryService.deleteCategory(CategoryId);
            return new ResponseEntity<>(status, HttpStatus.OK);
            // catch executes only if an exception occurs in the try block
//        } catch(ResponseStatusException e){
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
    }
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId){
//        try{
//        commented try cath as we have implemented app vide exception handling
            Category savedCategory = categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<>("Updated Category with CategoryId: " + categoryId, HttpStatus.OK);
//        }catch(ResponseStatusException e){
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
    }

}
