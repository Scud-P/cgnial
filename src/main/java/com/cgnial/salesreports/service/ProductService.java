package com.cgnial.salesreports.service;

import com.cgnial.salesreports.controllers.ProductController;
import com.cgnial.salesreports.domain.DTO.ProductDetailsDTO;
import com.cgnial.salesreports.domain.DTO.ProductSummaryDTO;
import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.domain.parameter.ProductDetailsParameter;
import com.cgnial.salesreports.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ExcelReaderService excelReaderService;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);


    public List<ProductSummaryDTO> getAllProductSummaries() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductSummaryDTO(product))
                .sorted(Comparator.comparingInt(ProductSummaryDTO::getCoutuCode))
                .toList();
    }

    public ProductDetailsDTO getProductById(int id) {
        return productRepository.findById(id)
                .map(product -> new ProductDetailsDTO(product))
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
    }

    @Transactional
    public Product saveProduct(ProductDetailsParameter product) {
        Product productToAdd = new Product(product);
        return productRepository.save(productToAdd);
    }


    @Transactional
    public Product updateProduct(ProductDetailsParameter product, Integer id) {


        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));

        logger.info("Service received a request to update product with productId {}", id);
        logger.info("Service received following payload in parameter: {}", product);
        logger.info("Repository found following product to update in DB: {}", productToUpdate);


        productToUpdate.setCoutuCode(product.getCoutuCode());
        productToUpdate.setFrenchDescription(product.getFrenchDescription());
        productToUpdate.setEnglishDescription(product.getEnglishDescription());
        productToUpdate.setSize(product.getSize());
        productToUpdate.setUom(product.getUom());
        productToUpdate.setUnitsPerCase(product.getUnitsPerCase());
        productToUpdate.setCaseUpc(product.getCaseUpc());
        productToUpdate.setUnitUpc(product.getUnitUpc());
        productToUpdate.setSatauCode(product.getSatauCode());
        productToUpdate.setUnfiCode(product.getUnfiCode());
        productToUpdate.setPuresourceCode(product.getPuresourceCode());

        logger.info("Product is supposed to be updated to: {}", productToUpdate);

        return productRepository.save(productToUpdate);
    }

    @Transactional
    public void deleteProductById(int id) {
        Product productToDelete = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
        productRepository.delete(productToDelete);
    }

    @Transactional
    public List<Product> saveAllProducts() throws IOException {
        List<Product> products = excelReaderService.readProductsExcelFile();
        return productRepository.saveAll(products);
    }

    @Transactional
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Transactional
    public void resetAutoIncrement() {
        productRepository.resetAutoIncrement();
    }
}
