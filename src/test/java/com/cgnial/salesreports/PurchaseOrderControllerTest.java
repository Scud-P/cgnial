package com.cgnial.salesreports;

import com.cgnial.salesreports.controllers.DistributorCasesController;
import com.cgnial.salesreports.controllers.PurchaseOrderController;
import com.cgnial.salesreports.service.DistributorCasesService;
import com.cgnial.salesreports.service.PurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PurchaseOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PurchaseOrderService service;

    @InjectMocks
    private PurchaseOrderController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testDeleteAllPurchaseOrders() throws Exception {
        mockMvc.perform(delete("/po/batchDelete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("POs deleted from DB"));

        verify(service, times(1)).deleteAllPurchaseOrders();
    }

    @Test
    public void testSaveAllPurchaseOrders() throws Exception {
        mockMvc.perform(post("/po/batchSave")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("POs saved to DB"));

        verify(service, times(1)).saveAllPurchaseOrders();
    }

}
