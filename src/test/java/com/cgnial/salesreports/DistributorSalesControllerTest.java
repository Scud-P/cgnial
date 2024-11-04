package com.cgnial.salesreports;

import com.cgnial.salesreports.controllers.toDistributors.DistributorSalesController;
import com.cgnial.salesreports.domain.DTO.toDistributors.SalesByDistributorByYearDTO;
import com.cgnial.salesreports.domain.DTO.toDistributors.SalesByYearDTO;
import com.cgnial.salesreports.service.toDistributors.SalesToDistributorsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DistributorSalesControllerTest {

    @Mock
    private SalesToDistributorsService distributorSalesService;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private DistributorSalesController distributorSalesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(distributorSalesController).build();
    }

    @Test
    public void testGetSalesByDistributorByYear() throws Exception {
        SalesByYearDTO firstSalesByYearDTO = new SalesByYearDTO(2024, 1000.0);
        SalesByYearDTO secondSalesByYearDTO = new SalesByYearDTO(2024, 1000.0);

        List<SalesByYearDTO> salesByYear = List.of(firstSalesByYearDTO, secondSalesByYearDTO);

        SalesByDistributorByYearDTO sales = new SalesByDistributorByYearDTO("Satau", salesByYear);

        List<SalesByDistributorByYearDTO> salesList = List.of(sales);

        when(distributorSalesService.getSalesByDistributorByYear()).thenReturn(salesList);

        mockMvc.perform(get("/distributorSales/byDistributorByYear"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSalesByDistributorByQuarter() {

        //TODO test case when method is implemented

    }

}
