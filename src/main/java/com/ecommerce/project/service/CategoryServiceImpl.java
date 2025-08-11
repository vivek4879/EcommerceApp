package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // this will tell Spring that you need to manage this component as a bean,
// and you need to inject it when needed.
public class CategoryServiceImpl implements CategoryService {

//    private List<Category> categories = new ArrayList<>();
    @Autowired
    private  CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category){
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");
        }
        categoryRepository.save(category);
    }

    @Override
    //we use stream here because a normal traversal to delete on the go will result in concurrentModificationException
    public String deleteCategory(Long CategoryId){

        Optional<Category> categoryToDeleteOptional = categoryRepository.findById(CategoryId);

        Category categoryToDelete = categoryToDeleteOptional
                .orElseThrow(()->new ResourceNotFoundException("Category", "Category",  CategoryId));
        categoryRepository.delete(categoryToDelete);
//        using a local variable for now
//        List<Category> categories = categoryRepository.findAll();
//        Category category = categories.stream().filter(c -> c.getCategoryId()
//                .equals(CategoryId))
//                .findFirst()
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
//        categoryRepository.delete(category);
        return "Category with categoryId: " + CategoryId + " deleted";
    }

    public Category updateCategory(Category category, Long categoryId){
        //Optional is a container object that may or may not contain a non-null value
        //used to safely handle missing values without null checks. helps avoid nullPointerException
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);

        Category categoryToUpdate = savedCategoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category",  categoryId));
        categoryToUpdate.setCategoryName(category.getCategoryName());
        categoryToUpdate = categoryRepository.save(categoryToUpdate);
        return categoryToUpdate;

    }
}
