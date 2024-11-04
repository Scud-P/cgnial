package com.cgnial.salesreports;

import com.cgnial.salesreports.controllers.products.ProductController;
import com.cgnial.salesreports.domain.DTO.ProductDetailsDTO;
import com.cgnial.salesreports.domain.DTO.ProductSummaryDTO;
import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.domain.parameter.ProductDetailsParameter;
import com.cgnial.salesreports.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product firstProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        firstProduct = new Product(0, 100, "frDescription1", "enDescription1",
                1, "kg", "small", 1, "someUPC", "someUnitUPC", "UNFI1",
                "SATAU1", "OLDSATAU1", "PURESOURCE1", "OLDPURESOURCE1");
    }

    @Test
    public void testGetAllProducts() throws Exception {
        ProductSummaryDTO productSummary = new ProductSummaryDTO(firstProduct);
        List<ProductSummaryDTO> productSummaries = Collections.singletonList(productSummary);

        when(productService.getAllProductSummaries()).thenReturn(productSummaries);

        mockMvc.perform(get("/products/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(productService, times(1)).getAllProductSummaries();
    }

    @Test
    public void testGetProductDetails() throws Exception {
        ProductDetailsDTO productDetails = new ProductDetailsDTO(firstProduct); // Populate DTO fields as needed

        when(productService.getProductById(firstProduct.getId())).thenReturn(productDetails);

        mockMvc.perform(get("/products/details/{id}", firstProduct.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(firstProduct.getId())); // Adjust field checks as needed

        verify(productService, times(1)).getProductById(firstProduct.getId());
    }

    @Test
    public void testValidateProduct() throws Exception {
        ProductDetailsParameter productParam = new ProductDetailsParameter();
        productParam.setId(1);

        when(productService.saveProduct(any(ProductDetailsParameter.class))).thenReturn(firstProduct);

        mockMvc.perform(post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1 }"))
                .andExpect(status().isOk());

        verify(productService, times(1)).saveProduct(any(ProductDetailsParameter.class));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        int productId = 1;
        ProductDetailsParameter productParam = new ProductDetailsParameter();
        productParam.setId(productId);
        ProductSummaryDTO summary = new ProductSummaryDTO(firstProduct);
        List<ProductSummaryDTO> summaries = Collections.singletonList(summary);

        when(productService.updateProduct(any(ProductDetailsParameter.class), anyInt())).thenReturn(firstProduct);
        when(productService.getAllProductSummaries()).thenReturn(summaries);

        mockMvc.perform(put("/products/update/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(productService, times(1)).updateProduct(any(ProductDetailsParameter.class), eq(productId));
        verify(productService, times(1)).getAllProductSummaries();
    }

    @Test
    public void testBatchDeleteProducts() throws Exception {
        doNothing().when(productService).deleteAll();

        mockMvc.perform(delete("/products/batchDelete"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product repository emptied"));

        verify(productService, times(1)).deleteAll();
    }

    @Test
    public void testBatchAddProducts() throws Exception {
        List<Product> products = List.of(firstProduct);
        when(productService.saveAllProducts()).thenReturn(products);

        mockMvc.perform(post("/products/batchAdd"))
                .andExpect(status().isOk());

        verify(productService, times(1)).saveAllProducts();
    }

    @Test
    public void testResetAutoIncrement() throws Exception {
        doNothing().when(productService).resetAutoIncrement();

        mockMvc.perform(post("/products/resetAutoIncrement"))
                .andExpect(status().isOk());

        verify(productService, times(1)).resetAutoIncrement();
    }

    @Test
    public void testGetUpdateProductForm() throws Exception {
        mockMvc.perform(get("/products/update/{id}", 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetNewProductForm() throws Exception {
        mockMvc.perform(get("/products/add"))
                .andExpect(status().isOk());
    }



}
