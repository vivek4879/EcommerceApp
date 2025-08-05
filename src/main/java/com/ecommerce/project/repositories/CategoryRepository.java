package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

//JPA already extends CRUD Repository interface and has some more methods.
//we wont have to write any implementation code for the CategoryRepository we created as
// spring data jpa will automatically generate the implementation at runtime and we can
// use it to perform all the basic CRUD operations on the Category table.
//JpaRepository<t, id>  t is type of the entity and id is the type of the primary key of the entity
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
