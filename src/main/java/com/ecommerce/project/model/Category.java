package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "categories")
//JPA requires every entity class to have a public or protected no args constructor
//create it yourself of use Lombok to create it for you
public class Category {
    @Id // marks the filed as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //tells JPA that let DB handle auto incrementing this column
    //IDENTITY = DB auto increment
    //AUTO = JPA picks best strategy based on DB dialect. other strategies available
    Long CategoryId;
    String CategoryName;

    public Category() {}
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
