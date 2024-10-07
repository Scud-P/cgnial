package com.cgnial.salesreports.controllers;

import com.cgnial.salesreports.domain.DTO.ProductSummaryDTO;
import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @GetMapping("/list")
    public String getAllProducts(Model model) {
        List<ProductSummaryDTO> productSummaries = productService.getAllProductSummaries();
        logger.info("List of products found by controller: {}", productSummaries);
        model.addAttribute("productSummaries", productSummaries);
        return "products/list";
    }

    @GetMapping("/details/{id}")
    public String getAllProducts(Model model, @PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/details";
    }

    @GetMapping("/add")
    public String getNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "products/add";
    }

    @PostMapping("/add")
    public String validateProduct(Model model,
                                  @ModelAttribute Product product) {
        try {
            productService.saveProduct(product);
            return "products/list";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/update/{id}")
    public String getUpdateProductForm(Model model, @PathVariable("id") int id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "products/update";
    }

    @PutMapping("/update/{id}")
    public String updateProduct(Model model, @ModelAttribute Product product, @PathVariable("id") int id) {
        productService.updateProduct(product);
        List<ProductSummaryDTO> productSummaries = productService.getAllProductSummaries();
        model.addAttribute("productSummaries", productSummaries);
        return "products/list";
    }

    @PostMapping("/batchAdd")
    public String batchAddProducts(Model model) {
        try {
            productService.saveAllProducts();
            return "products/list";

        } catch (RuntimeException | IOException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
