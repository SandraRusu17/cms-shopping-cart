package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.PageRepository;
import com.myshop.cmsshoppingcart.models.data.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/pages")
public class AdminController {

    @Autowired
    PageRepository pageRepository;

    @GetMapping
    public String index(Model model) {
        List<Page> pages = pageRepository.findAll();
        model.addAttribute("pages", pages);
        return "admin/pages/index";
    }
}
