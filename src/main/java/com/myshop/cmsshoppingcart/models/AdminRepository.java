package com.myshop.cmsshoppingcart.models;

import com.myshop.cmsshoppingcart.models.data.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
