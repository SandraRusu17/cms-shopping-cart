package com.myshop.cmsshoppingcart.models;

import com.myshop.cmsshoppingcart.models.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
