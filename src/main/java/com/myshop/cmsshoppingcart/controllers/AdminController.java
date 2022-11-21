package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.PageRepository;
import com.myshop.cmsshoppingcart.models.data.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/pages")
public class AdminController {

    PageRepository pageRepository;

    public AdminController(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    @GetMapping
    public String index(Model model) {
        List<Page> pages = pageRepository.findAll();
        model.addAttribute("pages", pages);
        return "admin/pages/index";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute Page page) {
//        model.addAttribute("page", new Page());
        return "admin/pages/add";
    }

}
