package com.myshop.cmsshoppingcart.models;

import com.myshop.cmsshoppingcart.models.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySlug(String slug);

    Product findBySlugAndIdNot(String slug, int id);
}
