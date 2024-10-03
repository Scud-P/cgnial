package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.DTO.ProductSummaryDTO;
import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.repositories.ProductRepository;
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

    public List<ProductSummaryDTO> getAllProductSummaries() {
        return productRepository.findAll()
                .stream()
                .map(ProductSummaryDTO::new)
                .sorted(Comparator.comparingInt(ProductSummaryDTO::getCoutuCode))
                .toList();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }


    @Transactional
    public Product updateProduct(Product product) {

        Product productToUpdate = productRepository.findById(product.getProductId())
                .orElseThrow(() -> new RuntimeException("Product with ID " + product.getProductId() + " not found"));

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

        return productRepository.save(productToUpdate);
    }

    @Transactional
    public void deleteProductById(String id) {
        Product productToDelete = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
        productRepository.delete(productToDelete);
    }

    @Transactional
    public List<Product> saveAllProducts() throws IOException {
        List<Product> products = excelReaderService.readProductsExcelFile();
        return productRepository.saveAll(products);
    }
}
