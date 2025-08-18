package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // this will tell Spring that you need to manage this component as a bean,
// and you need to inject it when needed.
public class CategoryServiceImpl implements CategoryService {

//    private List<Category> categories = new ArrayList<>();
    @Autowired
    private  CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        //first we create a  pageable object
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize, sortByAndOrder);
        //categoryPage is a paginated object that JPA is getting us  based on the
        // page number and size that the user has provided
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No Categories Added yet");
        }

        List<CategoryDTO> categoryDTOS = categories.stream().map(category -> modelMapper
                .map(category, CategoryDTO.class)).toList();
        //this will set all the pagination metadata
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setLastPage(categoryPage.isLast());
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if(categoryFromDb != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");
        }
        //save returns the saved object so we are storing it here.
        Category savedCategory = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO =  modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    //we use stream here because a normal traversal to delete on the go will result in concurrentModificationException
    public CategoryDTO deleteCategory(Long CategoryId){

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
        CategoryDTO deletedCategoryDTO =  modelMapper.map(categoryToDelete, CategoryDTO.class);
        return deletedCategoryDTO;
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId){
        //Optional is a container object that may or may not contain a non-null value
        //used to safely handle missing values without null checks. helps avoid nullPointerException
        Optional<Category> savedCategoryOptional = categoryRepository.findById(categoryId);

        Category categoryToUpdate = savedCategoryOptional
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category",  categoryId));
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryToUpdate.setCategoryName(category.getCategoryName());
        categoryToUpdate = categoryRepository.save(categoryToUpdate);
        return modelMapper.map(categoryToUpdate, CategoryDTO.class);

    }
}
