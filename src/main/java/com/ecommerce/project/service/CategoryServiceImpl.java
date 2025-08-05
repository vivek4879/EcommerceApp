package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // this will tell Spring that you need to manage this component as a bean,
// and you need to inject it when needed.
public class CategoryServiceImpl implements CategoryService {

//    private List<Category> categories = new ArrayList<>();
    @Autowired
    private  CategoryRepository categoryRepository;
    private Long curIndex = 1L;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category){
        category.setCategoryId(curIndex++);
        categoryRepository.save(category);
    }

    @Override
    //we use stream here because a normal traversal to delete on the go will result in concurrentModificationException
    public String deleteCategory(Long CategoryId){
        //using a local variable for now
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.stream().filter(c -> c.getCategoryId()
                .equals(CategoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
        categoryRepository.delete(category);
        return "Category with categoryId: " + CategoryId + " deleted";
    }

    public Category updateCategory(Category category, Long categoryId){
        //Optional is a container object that may or may not contain a non-null value
        //used to safely handle missing values without null checks. helps avoid nullPointerException
        List<Category> categories = categoryRepository.findAll();
        Optional<Category> optionalCategory =  categories.stream().filter(c -> c.getCategoryId()
                .equals(categoryId)).findFirst();
        //isPresent returns true if category present
        if(optionalCategory.isPresent()){
            //.get() will return the actual category object
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            Category savedCategory = categoryRepository.save(existingCategory);
            return savedCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }

    }
}
