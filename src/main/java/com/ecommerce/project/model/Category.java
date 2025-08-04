package com.ecommerce.project.model;

public class Category {
    Long CategoryId;
    String CategoryName;

    public Category(Long CategoryId, String CategoryName) {
        this.CategoryId = CategoryId;
        this.CategoryName = CategoryName;
    }

    public Long getCategoryId() {
        return CategoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.CategoryId = categoryId;
    }

    public String getCategoryName(){
        return CategoryName;
    }

    public void setCategoryName(String categoryName){
        this.CategoryName = categoryName;

    }
}
