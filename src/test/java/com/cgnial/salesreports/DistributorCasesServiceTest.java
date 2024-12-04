//package com.cgnial.salesreports;
//
//import com.cgnial.salesreports.domain.DTO.cases.CaseOrderDTO;
//import com.cgnial.salesreports.domain.DTO.cases.CasesByDistributorByYearDTO;
//import com.cgnial.salesreports.domain.DTO.cases.CasesByYearDTO;
//import com.cgnial.salesreports.domain.PurchaseOrderProduct;
//import com.cgnial.salesreports.repositories.PurchaseOrderProductRepository;
//import com.cgnial.salesreports.service.toDistributors.DistributorCasesService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class DistributorCasesServiceTest {
//
//    @Autowired
//    private DistributorCasesService distributorCasesService;
//
//    @MockBean
//    private PurchaseOrderProductRepository purchaseOrderProductRepository;
//
//    private static List<PurchaseOrderProduct> poProducts;
//    private static PurchaseOrderProduct firstPoProduct;
//    private static PurchaseOrderProduct secondPoProduct;
//    private static PurchaseOrderProduct thirdPoProduct;
//
//    private static List<CaseOrderDTO> poProductsDTO;
//    private static CaseOrderDTO firstPoProductDTO;
//    private static CaseOrderDTO secondPoProductDTO;
//    private static CaseOrderDTO thirdPoProductDTO;
//
//
//    @BeforeAll
//    public static void setUp() {
//        firstPoProduct = new PurchaseOrderProduct(0L, "2022/08/12", "Satau", 1,
//                1, 1,1,1,1,
//                1,1,1,1,1,
//                1,1,1,1,1,
//                1,1,1,1,1,1,
//                1,1);
//
//        secondPoProduct = new PurchaseOrderProduct(2L, "2022/08/13", "UNFI", 2,
//                2, 2,2,2,2,
//                2,2,2,2,2,
//                2,2,2,2,2,
//                2,2,2,2,2,2,
//                2,2);
//
//        thirdPoProduct = new PurchaseOrderProduct(0L, "2022/09/12", "Satau", 3,
//                3, 3,3,3,3,
//                3,3,3,3,3,
//                3,3,3,3,3,
//                3,3,3,3,3,3,
//                3,3);
//
//
//        poProducts= List.of(firstPoProduct, secondPoProduct, thirdPoProduct);
//
//        firstPoProductDTO = new CaseOrderDTO(firstPoProduct);
//        secondPoProductDTO = new CaseOrderDTO(secondPoProduct);
//        thirdPoProductDTO = new CaseOrderDTO(thirdPoProduct);
//        poProductsDTO = List.of(firstPoProductDTO, secondPoProductDTO, thirdPoProductDTO);
//    }
//
//    @Test
//    public void testGetAllCases() {
//        when(purchaseOrderProductRepository.findAll()).thenReturn(poProducts);
//        List<CaseOrderDTO> result = distributorCasesService.getAllCases();
//        assertEquals(poProductsDTO, result);
//    }
//
//    @Test
//    public void testGetSalesByDistributorByYear() {
//        //SATAU capitalized because of toUpperCase in method
//
//        CasesByYearDTO expectedDTO = new CasesByYearDTO(2022, "SATAU", 4,
//                4, 4,4,4,4,
//                4,4,4,4,4,
//                4,4,4,4,4,
//                4,4,4,4,4,4,
//                4,4);
//
//        when(purchaseOrderProductRepository.findAll()).thenReturn(poProducts);
//        List<CasesByYearDTO> result = distributorCasesService.getSalesByDistributorByYear();
//
//        assertEquals(2, result.size());
//        assertEquals(expectedDTO, result.get(0));
//    }
//
//    @Test
//    public void testGetCasesByDistributorByYearDTO() {
//
//        CasesByYearDTO firstCasesDTO = new CasesByYearDTO(2022, "SATAU", 4,
//                4, 4,4,4,4,
//                4,4,4,4,4,
//                4,4,4,4,4,
//                4,4,4,4,4,4,
//                4,4);
//
//        CasesByYearDTO secondCasesDTO = new CasesByYearDTO(2022, "UNFI", 2,
//                2, 2,2,2,2,
//                2,2,2,2,2,
//                2,2,2,2,2,
//                2,2,2,2,2,2,
//                2,2);
//
//        List<CasesByYearDTO> firstExpectedList = List.of(firstCasesDTO);
//        List<CasesByYearDTO> secondExpectedList = List.of(secondCasesDTO);
//
//        System.out.println(firstCasesDTO);
//        System.out.println(secondCasesDTO);
//
//        CasesByDistributorByYearDTO firstExpectedDTO = new CasesByDistributorByYearDTO("SATAU", firstExpectedList);
//        CasesByDistributorByYearDTO secondExpectedDTO = new CasesByDistributorByYearDTO("UNFI", secondExpectedList);
//
//        List<CasesByDistributorByYearDTO> expectedListDTO = List.of(firstExpectedDTO, secondExpectedDTO);
//
//        when(purchaseOrderProductRepository.findAll()).thenReturn(poProducts);
//        List<CasesByDistributorByYearDTO> result = distributorCasesService.getCasesByDistributorByYearDTO();
//
//        assertEquals(expectedListDTO, result);
//    }
//}
