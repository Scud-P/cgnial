package com.cgnial.salesreports;

import com.cgnial.salesreports.controllers.toDistributors.DistributorCasesController;
import com.cgnial.salesreports.domain.DTO.CasesByDistributorByYearDTO;
import com.cgnial.salesreports.domain.DTO.CasesByYearDTO;
import com.cgnial.salesreports.service.DistributorCasesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DistributorCasesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DistributorCasesService distributorCasesService;

    @InjectMocks
    private DistributorCasesController distributorCasesController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(distributorCasesController).build();
    }

    @Test
    public void testSalesByDistributorByYear() throws Exception {

        CasesByYearDTO cases = new CasesByYearDTO();
        cases.setDistributor("firstDistributor");
        cases.setYear(2024);
        cases.setOneHundredQty(1);

        CasesByYearDTO casesBis = new CasesByYearDTO();
        casesBis.setDistributor("firstDistributor");
        casesBis.setYear(2024);
        cases.setOneHundredQty(1);

        List<CasesByYearDTO> caseList = List.of(cases, casesBis);

        CasesByDistributorByYearDTO firstDTO = new CasesByDistributorByYearDTO();
        firstDTO.setDistributor("firstDistributor");
        firstDTO.setCasesByYear(caseList);

        List<CasesByDistributorByYearDTO> casesByYear = List.of(firstDTO);

        when(distributorCasesService.getCasesByDistributorByYearDTO()).thenReturn(casesByYear);

        mockMvc.perform(get("/distributorCases/byDistributorByYear")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"));

        verify(distributorCasesService, times(1)).getCasesByDistributorByYearDTO();
    }

}
