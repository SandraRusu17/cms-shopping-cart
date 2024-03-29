package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.ProductRepository;
import com.myshop.cmsshoppingcart.models.data.Cart;
import com.myshop.cmsshoppingcart.models.data.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/add/{id}")
    public String add(@PathVariable int id,
                      HttpSession session,
                      Model model,
                      @RequestParam(value = "cartPage", required = false) String cartPage) {

        Optional<Product> product = productRepository.findById(id);

        if (session.getAttribute("cart") == null) {

            HashMap<Integer, Cart> cart = new HashMap<>();
            cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), 1, product.get().getImage()));
            session.setAttribute("cart", cart);
        } else {
            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
            if (cart.containsKey(id)) {
                int qty = cart.get(id).getQuantity();
                cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), ++qty, product.get().getImage()));
            } else {
                cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), 1, product.get().getImage()));
                session.setAttribute("cart", cart);
            }
        }

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

        int size = 0;
        double total = 0;

        for (Cart value : cart.values()) {
            size += value.getQuantity();
            total += value.getQuantity() * Double.parseDouble(value.getPrice());
        }

        model.addAttribute("size", size);
        model.addAttribute("total", total);

        if (cartPage != null) {
            return "redirect:/cart/view";
        }

        return "cart_view";
    }

    @GetMapping("/subtract/{id}")
    public String subtract(@PathVariable int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {

        Optional<Product> product = productRepository.findById(id);

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

        int qty = cart.get(id).getQuantity();
        if (qty == 1) {
            cart.remove(id);
            if (cart.size() == 0) {
                session.removeAttribute("cart");
            }
        } else {
            cart.put(id, new Cart(id, product.get().getName(), product.get().getPrice(), --qty, product.get().getImage()));
        }

        String refererLink = httpServletRequest.getHeader("referer");

        return "redirect:" + refererLink;

    }

    @RequestMapping("/view")
    public String view(HttpSession session, Model model) {

        if (session.getAttribute("cart") == null) {
            return "redirect:/";
        }

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
        model.addAttribute("cart", cart);
        model.addAttribute("notCartViewPage", true);

        return "cart";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable int id, HttpSession session, Model model, HttpServletRequest httpServletRequest) {

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");

        cart.remove(id);
        if (cart.size() == 0) {
            session.removeAttribute("cart");
        }

        String refererLink = httpServletRequest.getHeader("referer");

        return "redirect:" + refererLink;
    }

    @GetMapping("/clear")
    public String clear(HttpSession session, HttpServletRequest httpServletRequest) {

        session.removeAttribute("cart");

        String refererLink = httpServletRequest.getHeader("referer");

        return "redirect:" + refererLink;
    }

}
