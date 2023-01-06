package com.myshop.cmsshoppingcart.controllers;

import com.myshop.cmsshoppingcart.models.CategoryRepository;
import com.myshop.cmsshoppingcart.models.ProductRepository;
import com.myshop.cmsshoppingcart.models.data.Category;
import com.myshop.cmsshoppingcart.models.data.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductsController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public String index(Model model, @RequestParam(value = "page", required = false) Integer p) {

        int perPage = 6;
        int page = (p != null) ? p : 0;

        Pageable pageable = PageRequest.of(page, perPage);

        Page<Product> products = productRepository.findAll(pageable);
        List<Category> categories = categoryRepository.findAll();

        HashMap<Integer, String> cats = new HashMap<>();
        for (Category category : categories) {
            cats.put(category.getId(), category.getName());
        }

        model.addAttribute("cats", cats);
        model.addAttribute("products", products);

        Long count = productRepository.count();
        double pageCount = Math.ceil((double) count / (double) perPage);

        model.addAttribute("pageCount", (int) pageCount);
        model.addAttribute("perPage", perPage);
        model.addAttribute("count", count);
        model.addAttribute("page", page);

        return "admin/products/index";
    }

    @GetMapping("/add")
    public String add(Product product, Model model) {

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/products/add";
    }

    @PostMapping("/add")
    public String add(@Valid Product product,
                      BindingResult bindingResult,
                      MultipartFile file,
                      RedirectAttributes redirectAttributes,
                      Model model) throws IOException {

        List<Category> categories = categoryRepository.findAll();

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categories);
            return "admin/products/add";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (filename.endsWith("jpg") || filename.endsWith("png")) {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExists = productRepository.findBySlug(slug);

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else if (productExists != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else {
            product.setSlug(slug);
            product.setImage(filename);
            productRepository.save(product);

            Files.write(path, bytes);
        }
        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {

        Optional<Product> product = productRepository.findById(id);
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("product", product.get());
        model.addAttribute("categories", categories);


        return "admin/products/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Product product,
                       BindingResult bindingResult,
                       MultipartFile file,
                       RedirectAttributes redirectAttributes,
                       Model model) throws IOException {

        Optional<Product> currentProduct = productRepository.findById(product.getId());

        List<Category> categories = categoryRepository.findAll();

        if (bindingResult.hasErrors()) {
            model.addAttribute("productName", currentProduct.get().getName());
            model.addAttribute("categories", categories);
            return "admin/products/edit";
        }

        boolean fileOK = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (!file.isEmpty()) {
            if (filename.endsWith("jpg") || filename.endsWith("png")) {
                fileOK = true;
            }
        } else {
            fileOK = true;
        }

        redirectAttributes.addFlashAttribute("message", "Product edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        String slug = product.getName().toLowerCase().replace(" ", "-");

        Product productExists = productRepository.findBySlugAndIdNot(slug, product.getId());

        if (!fileOK) {
            redirectAttributes.addFlashAttribute("message", "Slug exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else if (productExists != null) {
            redirectAttributes.addFlashAttribute("message", "Product exists, choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", product);
        } else {
            product.setSlug(slug);

            if (!file.isEmpty()) {
                Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.get().getImage());
                Files.delete(path2);
                product.setImage(filename);
                Files.write(path, bytes);
            } else {
                product.setImage(currentProduct.get().getImage());
            }

            productRepository.save(product);

        }
        return "redirect:/admin/products/edit/" + product.getId();
    }

    @GetMapping("/delete/{id}")
    public String del(@PathVariable int id, RedirectAttributes redirectAttributes) throws IOException {

        Optional<Product> product = productRepository.findById(id);
        Optional<Product> currentProduct = productRepository.findById(product.get().getId());

        Path path2 = Paths.get("src/main/resources/static/media/" + currentProduct.get().getImage());
        Files.delete(path2);
        productRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "Product deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/admin/products";
    }

}
