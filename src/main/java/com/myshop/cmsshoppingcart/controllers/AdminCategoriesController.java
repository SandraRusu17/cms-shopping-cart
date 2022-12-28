package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.CategoryRepository;
import com.myshop.cmsshoppingcart.models.data.Category;
import com.myshop.cmsshoppingcart.models.data.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/add")
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/categories/add";
        }
        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = category.getName().toLowerCase().replace(" ", "-");
        Category categoryExists = categoryRepository.findByName(category.getName());
        if (categoryExists != null) {
            redirectAttributes.addFlashAttribute("message", "Category exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("categoryInfo",category);
        } else {
            category.setSlug(slug);
            category.setSorting(100);

            categoryRepository.save(category);
        }
        return "redirect:/admin/categories/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Optional<Category> category = categoryRepository.findById(id);

        model.addAttribute("category", category.get());

        return "admin/categories/edit";
    }

}
