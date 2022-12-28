package com.myshop.cmsshoppingcart.models;

import com.myshop.cmsshoppingcart.models.data.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
