package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.ProductRepository;
import com.myshop.cmsshoppingcart.models.data.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    private ProductRepository productRepository;

    public AdminProductsController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String index(Model model){

        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);

        return "admin/products/index";
    }
}
