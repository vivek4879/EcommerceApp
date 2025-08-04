package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service // this will tell Spring that you need to manage this component as a bean,
// and you need to inject it when needed.
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private Long curIndex = 1L;
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category){
        category.setCategoryId(curIndex++);
        categories.add(category);
    }

    @Override
    //we use stream here because a normal traversal to delete on the go will result in concurrentModificationException
    public String deleteCategory(Long CategoryId){
        Category category = categories.stream().filter(c -> c.getCategoryId()
                .equals(CategoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        categories.remove(category);
        return "Category with categoryId: " + CategoryId + " deleted";
    }
}
