package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;

import java.util.List;

//can make this as class too, but using Interface to promote loose coupling and modularity in codebase.
public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    String deleteCategory(Long CategoryId);
}
