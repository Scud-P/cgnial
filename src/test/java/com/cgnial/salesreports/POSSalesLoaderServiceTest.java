package com.cgnial.salesreports;

import com.cgnial.salesreports.domain.POSSale;
import com.cgnial.salesreports.domain.parameter.distributorLoading.PuresourcePOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.SatauPOSParameter;
import com.cgnial.salesreports.domain.parameter.distributorLoading.UnfiPOSParameter;
import com.cgnial.salesreports.repositories.POSSalesRepository;
import com.cgnial.salesreports.service.loading.ItemNumberMatchingService;
import com.cgnial.salesreports.service.loading.POSSalesLoaderService;
import com.cgnial.salesreports.util.DatesUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class POSSalesLoaderServiceTest {

    @Mock
    private POSSalesRepository posSalesRepository;

    @Mock
    private ItemNumberMatchingService itemNumberMatchingService;

    @Mock
    private DatesUtil datesUtil;

    @InjectMocks
    private POSSalesLoaderService posSalesLoaderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadAllPuresourceSales() {
        PuresourcePOSParameter param1 = new PuresourcePOSParameter();
        param1.setPuresourceItemNumber("P1234");

        PuresourcePOSParameter param2 = new PuresourcePOSParameter();
        param2.setPuresourceItemNumber("P5678");

        List<PuresourcePOSParameter> sales = List.of(param1, param2);

        when(itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber("P1234")).thenReturn(100);
        when(itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber("P5678")).thenReturn(200);

        posSalesLoaderService.loadAllPuresourceSales(sales);

        verify(posSalesRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testLoadAllPuresourceSales_DiscontinuedProducts() {
        PuresourcePOSParameter param1 = new PuresourcePOSParameter();
        param1.setPuresourceItemNumber("P1234");
        PuresourcePOSParameter param2 = new PuresourcePOSParameter();
        param1.setPuresourceItemNumber("P2345");

        List<PuresourcePOSParameter> sales = List.of(param1);

        when(itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber("P1234")).thenReturn(0);
        when(itemNumberMatchingService.determineProductCodeFromOldPuresourceItemNumber("P1234")).thenReturn(0);
        when(itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber("P2345")).thenReturn(100);


        List<String> discontinuedProducts = List.of("P1234");

        POSSalesLoaderService spyService = spy(posSalesLoaderService);

        doReturn(discontinuedProducts).when(spyService).getDiscontinuedPuresourceCodes();

        spyService.loadAllPuresourceSales(sales);

        ArgumentCaptor<List<POSSale>> captor = ArgumentCaptor.forClass(List.class);
        verify(posSalesRepository).saveAll(captor.capture());

        List<POSSale> capturedSales = captor.getValue();
        assertEquals(1, capturedSales.size());
        assertEquals(100, capturedSales.get(0).getItemNumber());
    }

    @Test
    public void testLoadAllPuresourceSales_OldProductCode() {
        PuresourcePOSParameter param = new PuresourcePOSParameter();
        param.setPuresourceItemNumber("P1234");

        List<PuresourcePOSParameter> sales = List.of(param);

        when(itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber("P1234")).thenReturn(0);
        when(itemNumberMatchingService.determineProductCodeFromOldPuresourceItemNumber("P1234")).thenReturn(100);

        posSalesLoaderService.loadAllPuresourceSales(sales);

        verify(posSalesRepository, times(1)).saveAll(anyList());
        verify(itemNumberMatchingService, times(1)).determineProductCodeFromOldPuresourceItemNumber("P1234");
    }

    @Test
    public void testLoadAllPuresourceSales_MissingProductCodes() {
        PuresourcePOSParameter param = new PuresourcePOSParameter();
        param.setPuresourceItemNumber("P1234");

        List<PuresourcePOSParameter> sales = List.of(param);

        when(itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber("P1234")).thenReturn(0);
        when(itemNumberMatchingService.determineProductCodeFromOldPuresourceItemNumber("P1234")).thenReturn(0);

        posSalesLoaderService.loadAllPuresourceSales(sales);

        verify(posSalesRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void testLoadAllSatauSales_ExcludedCodes() {
        SatauPOSParameter param1 = new SatauPOSParameter();
        param1.setSatauItemNumber("S1234");

        SatauPOSParameter param2 = new SatauPOSParameter();
        param2.setSatauItemNumber("S1235");


        List<SatauPOSParameter> sales = List.of(param1, param2);

        List<String> excludedCodes = List.of("S1234");

        POSSalesLoaderService spyService = spy(posSalesLoaderService);

        when(itemNumberMatchingService.determineProductCodeFromSatauItemNumber("S1234")).thenReturn(123);
        doReturn(excludedCodes).when(spyService).getSatauExcludedCodes();
        when(itemNumberMatchingService.determineProductCodeFromSatauItemNumber("S1235")).thenReturn(234);

        spyService.loadAllSatauSales(sales);

        ArgumentCaptor<List<POSSale>> captor = ArgumentCaptor.forClass(List.class);
        verify(posSalesRepository).saveAll(captor.capture());

        List<POSSale> capturedSales = captor.getValue();
        assertEquals(1, capturedSales.size());
        assertEquals(234, capturedSales.get(0).getItemNumber());
    }

    @Test
    public void testLoadAllSatauSales_ValidProduct() {
        SatauPOSParameter param1 = new SatauPOSParameter();
        param1.setSatauItemNumber("S1234");

        List<SatauPOSParameter> sales = List.of(param1);

        POSSalesLoaderService spyService = spy(posSalesLoaderService);

        when(itemNumberMatchingService.determineProductCodeFromSatauItemNumber("S1234")).thenReturn(123);
        doReturn(Collections.emptyList()).when(spyService).getSatauExcludedCodes();

        spyService.loadAllSatauSales(sales);

        ArgumentCaptor<List<POSSale>> captor = ArgumentCaptor.forClass(List.class);
        verify(posSalesRepository, times(1)).saveAll(captor.capture());

        List<POSSale> savedSales = captor.getValue();
        assertEquals(1, savedSales.size());
        assertEquals(123, savedSales.get(0).getItemNumber());
    }

    @Test
    public void testLoadAllUnfiSales_ValidProduct() {
        UnfiPOSParameter param1 = new UnfiPOSParameter();
        param1.setUnfiItemNumber("U1234");
        param1.setMonth("March");

        List<UnfiPOSParameter> sales = List.of(param1);

        when(itemNumberMatchingService.determineProductCodeFromUnfiItemNumber("U1234")).thenReturn(123);
        when(datesUtil.convertMonthToIntValue("March")).thenReturn(3);
        when(datesUtil.determineQuarter(3)).thenReturn(1);

        posSalesLoaderService.loadAllUnfiSales(sales);

        ArgumentCaptor<List<POSSale>> captor = ArgumentCaptor.forClass(List.class);
        verify(posSalesRepository, times(1)).saveAll(captor.capture());

        List<POSSale> savedSales = captor.getValue();
        assertEquals(1, savedSales.size());
        assertEquals(123, savedSales.get(0).getItemNumber());
        assertEquals(3, savedSales.get(0).getMonth());
        assertEquals(1, savedSales.get(0).getQuarter());
    }

    @Test
    public void testClearAllSatauSales() {
        posSalesLoaderService.clearAllSatauSales();
        verify(posSalesRepository, times(1)).deleteAllByDistributorIgnoreCase("Satau");
    }

    @Test
    public void testClearAllUnfiSales() {
        posSalesLoaderService.clearAllUnfiSales();
        verify(posSalesRepository, times(1)).deleteAllByDistributorIgnoreCase("UNFI");
    }

    @Test
    public void testClearAllSales() {
        posSalesLoaderService.clearAllSales();
        verify(posSalesRepository, times(1)).deleteAll();
    }

    @Test
    public void testResetAutoIncrement() {
        posSalesLoaderService.resetAutoIncrement();
        verify(posSalesRepository, times(1)).resetAutoIncrement();
    }

}
