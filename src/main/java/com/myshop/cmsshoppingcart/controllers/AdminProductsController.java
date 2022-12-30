package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.CategoryRepository;
import com.myshop.cmsshoppingcart.models.ProductRepository;
import com.myshop.cmsshoppingcart.models.data.Category;
import com.myshop.cmsshoppingcart.models.data.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductsController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model){

        List<Product> products = productRepository.findAll();

        model.addAttribute("products", products);

        return "admin/products/index";
    }

    @GetMapping("/add")
    public String add(Product product, Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/products/add";
    }

}
