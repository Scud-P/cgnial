package com.cgnial.salesreports;

import com.cgnial.salesreports.controllers.dev.DevController;
import com.cgnial.salesreports.service.loading.POSSalesLoaderService;
import com.cgnial.salesreports.service.products.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DevControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private POSSalesLoaderService posSalesLoaderService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private DevController devController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(devController).build();
    }

    @Test
    public void testClearEverything() throws Exception {

        doNothing().when(posSalesLoaderService).clearAllSales();
        doNothing().when(posSalesLoaderService).resetAutoIncrement();
        doNothing().when(productService).deleteAll();
        doNothing().when(productService).resetAutoIncrement();

        mockMvc.perform(delete("/dev/clear"))
                .andExpect(status().isOk())
                .andExpect(content().string("All databases cleared and autoincrement reset"));

        verify(posSalesLoaderService, times(1)).clearAllSales();
        verify(posSalesLoaderService, times(1)).resetAutoIncrement();
        verify(productService, times(1)).deleteAll();
        verify(productService, times(1)).resetAutoIncrement();
    }

}
