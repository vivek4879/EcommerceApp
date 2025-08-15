package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//this is the request Object
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long CategoryId;
    private String categoryName;
}
