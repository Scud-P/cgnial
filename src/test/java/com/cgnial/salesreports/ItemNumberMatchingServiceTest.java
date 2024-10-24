package com.cgnial.salesreports;

import com.cgnial.salesreports.repositories.ProductRepository;
import com.cgnial.salesreports.service.ItemNumberMatchingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ItemNumberMatchingServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ItemNumberMatchingService itemNumberMatchingService;

    private String puresourceCode;
    private String oldPuresourceCode;
    private String satauCode;
    private String oldSatauCode;
    private String unfiCode;
    private int coutuCode;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        puresourceCode = "pCode";
        oldPuresourceCode = "opCode";
        satauCode = "sCode";
        oldSatauCode = "osCode";
        unfiCode = "uCode";
        coutuCode = 100;
    }

    @Test
    public void testDetermineProductCodeFromPuresourceItemNumber() {
        when(productRepository.findCoutuCodeByPuresourceCode(puresourceCode)).thenReturn(coutuCode);
        int result = itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber(puresourceCode);
        assertEquals(coutuCode, result);
    }

    @Test
    public void testDetermineProductCodeFromPuresourceItemNumber_codeNull() {
        when(productRepository.findCoutuCodeByPuresourceCode(puresourceCode)).thenReturn(null);
        int result = itemNumberMatchingService.determineProductCodeFromPuresourceItemNumber(puresourceCode);
        assertEquals(0, result);
    }

    @Test
    public void testDetermineProductCodeFromOldPuresourceItemNumber() {
        when(productRepository.findCoutuCodeByOldPuresourceCode(oldPuresourceCode)).thenReturn(coutuCode);
        int result = itemNumberMatchingService.determineProductCodeFromOldPuresourceItemNumber(oldPuresourceCode);
        assertEquals(coutuCode, result);
    }

    @Test
    public void testDetermineProductCodeFromOldPuresourceItemNumber_codeNull() {
        when(productRepository.findCoutuCodeByOldPuresourceCode(oldPuresourceCode)).thenReturn(null);
        int result = itemNumberMatchingService.determineProductCodeFromOldPuresourceItemNumber(oldPuresourceCode);
        assertEquals(0, result);
    }

    @Test
    public void testDetermineProductCodeFromUnfiItemNumber() {
        when(productRepository.findCoutuCodeByUnfiCode(unfiCode)).thenReturn(coutuCode);
        int result = itemNumberMatchingService.determineProductCodeFromUnfiItemNumber(unfiCode);
        assertEquals(coutuCode, result);
    }

    @Test
    public void testDetermineProductCodeFromUnfiItemNumber_codeNull() {
        when(productRepository.findCoutuCodeByUnfiCode(unfiCode)).thenReturn(null);
        int result = itemNumberMatchingService.determineProductCodeFromUnfiItemNumber(unfiCode);
        assertEquals(0, result);
    }

    @Test
    public void testDetermineProductCodeFromSatauItemNumber() {
        when(productRepository.findCoutuCodeBySatauCode(satauCode)).thenReturn(coutuCode);
        int result = itemNumberMatchingService.determineProductCodeFromSatauItemNumber(satauCode);
        assertEquals(coutuCode, result);
    }

    @Test
    public void testDetermineProductCodeFromSatauItemNumber_codeNull() {
        when(productRepository.findCoutuCodeBySatauCode(satauCode)).thenReturn(null);
        int result = itemNumberMatchingService.determineProductCodeFromSatauItemNumber(satauCode);
        assertEquals(0, result);
    }

    @Test
    public void testDetermineProductCodeFromOldSatauItemNumber() {
        when(productRepository.findCoutuCodeByOldSatauCode(oldSatauCode)).thenReturn(coutuCode);
        int result = itemNumberMatchingService.determineProductCodeFromOldSatauItemNumber(oldSatauCode);
        assertEquals(coutuCode, result);
    }

    @Test
    public void testDetermineProductCodeFromOldSatauItemNumber_codeNull() {
        when(productRepository.findCoutuCodeByOldSatauCode(oldSatauCode)).thenReturn(null);
        int result = itemNumberMatchingService.determineProductCodeFromOldSatauItemNumber(oldSatauCode);
        assertEquals(0, result);
    }

    @Test
    public void testDetermineProductCodeFromOldSatauItemNumber_itemNumberNull() {
        when(productRepository.findCoutuCodeByOldSatauCode(null)).thenReturn(null);
        int result = itemNumberMatchingService.determineProductCodeFromOldSatauItemNumber(null);
        assertEquals(0, result);
    }

    @Test
    public void testDetermineProductCodeFromOldSatauItemNumber_itemNumberEmpty() {
        when(productRepository.findCoutuCodeByOldSatauCode("   ")).thenReturn(null);
        int result = itemNumberMatchingService.determineProductCodeFromOldSatauItemNumber("   ");
        assertEquals(0, result);
    }

}
