package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.PageRepository;
import com.myshop.cmsshoppingcart.models.data.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {

    private PageRepository pageRepository;

    @GetMapping
    public String home(Model model){

        Page page = pageRepository.findBySlug("home");
        model.addAttribute("page", page);

        return "page";
    }
}
