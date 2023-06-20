package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;
import com.example.demo.services.ProductNotFoundException;
import com.example.demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {
    @Autowired private ProductService service;

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @GetMapping("/productsList")
//    public String showProductList(Model model){
//        List<Product> listProducts = service.listAll();
//        model.addAttribute("listProducts", listProducts);
//
//        return "productsList";
//    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/addProduct")
    public String showNewForm(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Add new user");
        return "productForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/saveProduct")
    public String saveProduct(Product product, RedirectAttributes ra,
                              @RequestParam(value="multipartFile", required=false) MultipartFile multipartFile) {

        if (!multipartFile.isEmpty()) {
            try {
                service.addImage(product, multipartFile);
            }
            catch(IOException e){}
        }

        service.save(product);
        ra.addFlashAttribute("message", "The product has been saved successfully. ");

        return "redirect:/productsList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/editProduct/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try{
            Product product = service.get(id);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", "Edit user (ID: " + id + ")");
            return "productForm";
        }
        catch (ProductNotFoundException e){
            ra.addFlashAttribute("message", "The product has been saved successfully. ");
            return "redirect:/productsList";
        }

    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, RedirectAttributes ra){
        try{
            service.delete(id);
            ra.addFlashAttribute("message", "The product ID " + id + " has been deleted");

        }
        catch (ProductNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/productsList";

    }


    @RequestMapping(value = "/productsList", method = RequestMethod.GET)
    public String listProducts(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);


        List<Product> listProducts = service.listAll();
        model.addAttribute("listProducts", listProducts);

        Page<Product> productPage = service.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("productPage", productPage);

        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "productsList";
    }


}