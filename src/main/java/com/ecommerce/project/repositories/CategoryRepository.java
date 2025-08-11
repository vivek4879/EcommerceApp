package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA already extends CRUD Repository interface and has some more methods.
//we won't have to write any implementation code for the CategoryRepository we created as
// spring data jpa will automatically generate the implementation at runtime, and we can
// use it to perform all the basic CRUD operations on the Category table.
//JpaRepository<t, id>  t is type of the entity and id is the type of the primary key of the entity
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //this here has been created by Spring Data JPA. name has a convention. findBy is needed to find by which entity,
    // CategoryName should match a variable in the category entity. JPA then picks it up and sees that you want to find by
    // categoryName which is a field defined in the entity. So it will generate an implementation for that query.
    Category findByCategoryName(String categoryName);
}
