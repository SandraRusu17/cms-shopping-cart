package com.myshop.cmsshoppingcart;

import com.myshop.cmsshoppingcart.models.PageRepository;
import com.myshop.cmsshoppingcart.models.data.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class Common {

    private PageRepository pageRepository;

    @ModelAttribute
    public void sharedData(Model model) {
        List<Page> pages = pageRepository.findAllByOrderBySortingAsc();

        model.addAttribute("cpages", pages);
    }

}
