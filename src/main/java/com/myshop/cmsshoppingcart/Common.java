package com.myshop.cmsshoppingcart;

import com.myshop.cmsshoppingcart.models.CategoryRepository;
import com.myshop.cmsshoppingcart.models.PageRepository;
import com.myshop.cmsshoppingcart.models.data.Cart;
import com.myshop.cmsshoppingcart.models.data.Category;
import com.myshop.cmsshoppingcart.models.data.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@ControllerAdvice
@SuppressWarnings("unchecked")
public class Common {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute
    public void sharedData(Model model, HttpSession session, Principal principal) {

        if(principal != null) {
            model.addAttribute("user", principal.getName());
        }
        List<Page> pages = pageRepository.findAllByOrderBySortingAsc();

        List<Category> categories = categoryRepository.findAllByOrderBySortingAsc();

        boolean cartActive = false;

        if (session.getAttribute("cart") != null) {

            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

            int size = 0;
            double total = 0;

            for (Cart value: cart.values()) {
                size += value.getQuantity();
                total += value.getQuantity() * Double.parseDouble(value.getPrice());
            }

            model.addAttribute("csize", size);
            model.addAttribute("ctotal", total);

            cartActive = true;
        }

        model.addAttribute("cpages", pages);
        model.addAttribute("ccategories", categories);
        model.addAttribute("cartActive", cartActive);
    }

}
