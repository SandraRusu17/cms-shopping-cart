package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.CategoryRepository;
import com.myshop.cmsshoppingcart.models.data.Category;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController {

    private CategoryRepository categoryRepository;

    public AdminCategoriesController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String index(Model model){
        List<Category>  categories = categoryRepository.findAll();

        model.addAttribute("categories", categories);

        return "admin/categories/index";

    }
    // this model attribute "category" will be available for all the methods in this controller
//    @ModelAttribute("category")
//    public Category getCategory() {
//        return new Category();
//    }

    @GetMapping("/add")
    public String add(Category category){
        return "admin/categories/add";
    }
}
