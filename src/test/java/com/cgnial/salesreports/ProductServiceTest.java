package com.cgnial.salesreports;

import com.cgnial.salesreports.domain.DTO.products.ProductDetailsDTO;
import com.cgnial.salesreports.domain.DTO.products.ProductSummaryDTO;
import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.domain.parameter.products.ProductDetailsParameter;
import com.cgnial.salesreports.repositories.ProductRepository;
import com.cgnial.salesreports.service.loading.ExcelReaderService;
import com.cgnial.salesreports.service.products.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ExcelReaderService excelReaderService;

    @InjectMocks
    private ProductService productService;

    private Product firstProduct;
    private Product secondProduct;
    private List<Product> products;

    private ProductSummaryDTO firstDTO;
    private ProductSummaryDTO secondDTO;
    private List<ProductSummaryDTO> productDTOs;

    private ProductDetailsDTO firstDetailsDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        firstProduct = new Product(0, 100, "frDescription1", "enDescription1",
                1, "kg", "small", 1, "someUPC", "someUnitUPC", "UNFI1",
                "SATAU1", "OLDSATAU1", "PURESOURCE1", "OLDPURESOURCE1");
        secondProduct = new Product(1, 200, "frDescription2", "enDescription2",
                2, "kg", "small", 1, "someUPC2", "someUnitUPC2", "UNFI2",
                "SATAU2", "OLDSATAU2", "PURESOURCE2", "OLDPURESOURCE2");
        products = List.of(firstProduct, secondProduct);

        firstDTO = new ProductSummaryDTO(firstProduct);
        secondDTO = new ProductSummaryDTO(secondProduct);
        productDTOs = List.of(firstDTO, secondDTO);

        firstDetailsDTO = new ProductDetailsDTO(firstProduct);
    }

    @Test
    public void testGetAllProductSummaries() {
        when(productRepository.findAll()).thenReturn(products);
        List<ProductSummaryDTO> result = productService.getAllProductSummaries();
        assertEquals(productDTOs, result);
    }

    @Test
    public void testGetProductById_found() {
        when(productRepository.findById(firstProduct.getId())).thenReturn(Optional.ofNullable(firstProduct));
        ProductDetailsDTO result = productService.getProductById(firstProduct.getId());
        assertEquals(firstDetailsDTO, result);
    }

    @Test
    public void testGetProductById_notFound() {
        when(productRepository.findById(firstProduct.getId())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.getProductById(firstProduct.getId()));
    }

    @Test
    public void testSaveProduct() {
        ProductDetailsParameter product = new ProductDetailsParameter();
        Product savedProduct = new Product();
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        Product result = productService.saveProduct(product);
        verify(productRepository, times(1)).save(result);
    }

    @Test
    public void testUpdateProduct_notFound() {
        ProductDetailsParameter product = new ProductDetailsParameter();
        product.setId(1);

        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.updateProduct(product, 1));
    }

    @Test
    public void testUpdateProduct_success() {
        String updatedDescription = "Changed description";
        ProductDetailsParameter product = new ProductDetailsParameter();
        product.setFrenchDescription(updatedDescription);
        Product updatedProduct = firstProduct;
        firstProduct.setFrenchDescription(updatedDescription);

        when(productRepository.findById(anyInt())).thenReturn(Optional.ofNullable(firstProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(product, 1);

        assertEquals(result.getFrenchDescription(), updatedDescription);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct_notFound() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.deleteProductById(1));
        verify(productRepository, never()).deleteById(anyInt());
    }

    @Test
    public void testDeleteProduct_success() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.ofNullable(firstProduct));
        productService.deleteProductById(firstProduct.getId());
        verify(productRepository, times(1)).delete(firstProduct);
    }

    @Test
    public void testSaveAllProducts() throws IOException {
        when(excelReaderService.readProductsExcelFile()).thenReturn(products);
        when(productRepository.saveAll(products)).thenReturn(products);

        List<Product> savedProducts = productService.saveAllProducts();

        assertEquals(products, savedProducts);
        verify(productRepository, times(1)).saveAll(products);
        verify(excelReaderService, times(1)).readProductsExcelFile();
    }

    @Test
    public void testDeleteAll() {
        productService.deleteAll();
        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    public void testResetAutoIncrement() {
        productService.resetAutoIncrement();
        verify(productRepository, times(1)).resetAutoIncrement();
    }




}
