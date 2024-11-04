package com.cgnial.salesreports.controllers.products;

import com.cgnial.salesreports.domain.DTO.ProductDetailsDTO;
import com.cgnial.salesreports.domain.DTO.ProductSummaryDTO;
import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.domain.parameter.ProductDetailsParameter;
import com.cgnial.salesreports.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @GetMapping("/list")
    public ResponseEntity<List<ProductSummaryDTO>> getAllProducts() {
        List<ProductSummaryDTO> productSummaries = productService.getAllProductSummaries();
        return ResponseEntity.ok(productSummaries);
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable("id") int id) {
        ProductDetailsDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/add")
    public ResponseEntity<Product> getNewProductForm() {
        Product product = new Product();
        return ResponseEntity.ok(product);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDetailsParameter> validateProduct(ProductDetailsParameter product) {
        productService.saveProduct(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<ProductDetailsDTO> getUpdateProductForm(@PathVariable("id") int id) {
        ProductDetailsDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<List<ProductSummaryDTO>> updateProduct(
            @RequestBody ProductDetailsParameter product,
            @PathVariable("id") int id) {
        logger.info("Will try to update product with Id: {}", id);
        logger.info("Received product from frontend: {}", product);
        productService.updateProduct(product, id);
        List<ProductSummaryDTO> productSummaries = productService.getAllProductSummaries();
        return ResponseEntity.ok(productSummaries);
    }

    @PostMapping("/batchAdd")
    public ResponseEntity<String> batchAddProducts() throws IOException {
        productService.saveAllProducts();
        return ResponseEntity.ok("All products added");
    }

    @DeleteMapping("/batchDelete")
    public ResponseEntity<String> batchDeleteProducts() {
        productService.deleteAll();
        return ResponseEntity.ok("Product repository emptied");
    }

    @PostMapping("/resetAutoIncrement")
    public ResponseEntity<String> resetAutoIncrement() {
        productService.resetAutoIncrement();
        return ResponseEntity.ok("AutoIncrement reset");
    }
}
