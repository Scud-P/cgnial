package com.cgnial.salesreports;

import com.cgnial.salesreports.controllers.dev.POSSalesController;
import com.cgnial.salesreports.domain.parameter.distributorLoading.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.SatauPOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.UnfiPOSParameter;
import com.cgnial.salesreports.service.loading.ExcelReaderService;
import com.cgnial.salesreports.service.loading.POSSalesLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class POSSalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private POSSalesLoaderService posSalesLoaderService;

    @Mock
    private ExcelReaderService excelReaderService;

    @InjectMocks
    private POSSalesController posSalesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(posSalesController).build();
    }

    @Test
    public void testResetAutoIncrement() throws Exception {
        doNothing().when(posSalesLoaderService).resetAutoIncrement();

        mockMvc.perform(get("/pos/resetAutoIncrement"))
                .andExpect(status().isOk())
                .andExpect(content().string("AutoIncrement has been reset"));

        verify(posSalesLoaderService, times(1)).resetAutoIncrement();
    }

    @Test
    public void testLoadAllPuresourceSales() throws Exception {
        List<PuresourcePOSParameter> rawSales = Collections.singletonList(new PuresourcePOSParameter());
        when(excelReaderService.readPuresourcePOSParameters()).thenReturn(rawSales);
        doNothing().when(posSalesLoaderService).loadAllPuresourceSales(rawSales);

        mockMvc.perform(get("/pos/loadAllPuresourceSales"))
                .andExpect(status().isOk())
                .andExpect(content().string("Puresource sales loaded to database. 1 transactions found"));

        verify(excelReaderService, times(1)).readPuresourcePOSParameters();
        verify(posSalesLoaderService, times(1)).loadAllPuresourceSales(rawSales);
    }

    @Test
    public void testClearAllPuresourceSales() throws Exception {
        doNothing().when(posSalesLoaderService).clearAllPuresourceSales();

        mockMvc.perform(get("/pos/clearAllPuresourceSales"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sales database emptied from Puresource Sales"));

        verify(posSalesLoaderService, times(1)).clearAllPuresourceSales();
    }

    @Test
    public void testLoadAllSatauSales() throws Exception {
        List<SatauPOSParameter> rawSales = Collections.singletonList(new SatauPOSParameter());
        when(excelReaderService.readSatauPOSParameters()).thenReturn(rawSales);
        doNothing().when(posSalesLoaderService).loadAllSatauSales(rawSales);

        mockMvc.perform(get("/pos/loadAllSatauSales"))
                .andExpect(status().isOk())
                .andExpect(content().string("1 Satau sales loaded to database"));

        verify(excelReaderService, times(1)).readSatauPOSParameters();
        verify(posSalesLoaderService, times(1)).loadAllSatauSales(rawSales);
    }

    @Test
    public void testClearAllSatauSales() throws Exception {
        doNothing().when(posSalesLoaderService).clearAllSatauSales();

        mockMvc.perform(get("/pos/clearAllSatauSales"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sales database emptied from Satau Sales"));

        verify(posSalesLoaderService, times(1)).clearAllSatauSales();
    }

    @Test
    public void testLoadAllUnfiSales() throws Exception {
        List<UnfiPOSParameter> rawSales = Collections.singletonList(new UnfiPOSParameter());
        when(excelReaderService.readUNFIPOSParameters()).thenReturn(rawSales);
        doNothing().when(posSalesLoaderService).loadAllUnfiSales(rawSales);

        mockMvc.perform(get("/pos/loadAllUnfiSales"))
                .andExpect(status().isOk())
                .andExpect(content().string("1 UNFI sales loaded to database"));

        verify(excelReaderService, times(1)).readUNFIPOSParameters();
        verify(posSalesLoaderService, times(1)).loadAllUnfiSales(rawSales);
    }

    @Test
    public void testClearAllUnfiSales() throws Exception {
        doNothing().when(posSalesLoaderService).clearAllUnfiSales();

        mockMvc.perform(get("/pos/clearAllUnfiSales"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sales database emptied from UNFI Sales"));

        verify(posSalesLoaderService, times(1)).clearAllUnfiSales();
    }
}

