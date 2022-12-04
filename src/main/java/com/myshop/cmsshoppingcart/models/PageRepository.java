package com.myshop.cmsshoppingcart.models;

import com.myshop.cmsshoppingcart.models.data.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

    Page findBySlug(String slug);
}
