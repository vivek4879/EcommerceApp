package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //so just by declaring this method over here JPA is going to
    // provide us the implementation without us writing anything.
    // it will also derive the query to trigger to the database based on the naming itself.
    //just by looking at the method name, JPA knows what to do.
    List<Product> findByCategoryOrderByPriceAsc(Category category);
}
