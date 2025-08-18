package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

//can make this as class too, but using Interface to promote loose coupling and modularity in codebase.
public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO category);
    String deleteCategory(Long CategoryId);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long CategoryId);
}
