package com.cgnial.salesreports;

import com.cgnial.salesreports.controllers.PurchaseOrderCasesController;
import com.cgnial.salesreports.service.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseOrderCasesControllerTest {
    @Mock
    private PurchaseOrderService purchaseOrderService;

    @InjectMocks
    private PurchaseOrderCasesController purchaseOrderCasesController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderCasesController).build();
    }

    @Test
    public void loadAllCasesTest() throws Exception {
        doNothing().when(purchaseOrderService).saveAllPurchaseOrders();
        mockMvc.perform(post("/cases/batchSave"))
                .andExpect(status().isOk())
                .andExpect(content().string("Case quantities saved to DB"));
    }

    @Test
    public void deleteAllCasesTest() throws Exception {
        doNothing().when(purchaseOrderService).deleteAllPurchaseOrders();
        mockMvc.perform(delete("/cases/batchDelete"))
                .andExpect(status().isOk())
                .andExpect(content().string("Case quantities DB cleared"));
    }

}

