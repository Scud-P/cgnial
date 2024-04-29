package com.cgnial.salesreports.service;

import com.cgnial.salesreports.domain.Product;
import com.cgnial.salesreports.domain.RetailSale;
import com.cgnial.salesreports.repositories.ProductRepository;
import com.cgnial.salesreports.repositories.RetailSalesRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExperimentalCSVLoaderService {

    @Autowired
    private RetailSalesRepository retailSalesRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @PostConstruct
    public void loadDB() {
        populatePuresourceRetailSalesTable();
    }

    @PreDestroy
    public void emptyDB() {
        retailSalesRepository.deleteAll();
        productRepository.deleteAll();
    }

    private void populatePuresourceRetailSalesTable() {

        List<RetailSale> retailSales = new ArrayList<>();
        String distributor = "puresource";
        ClassPathResource resource = new ClassPathResource("puresource_03_24.csv");
        Reader reader = null;

        try {
            reader = new FileReader(resource.getFile());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            assert reader != null;
            try (CSVReader csvReader = new CSVReader(reader)) {
                csvReader.skip(1);
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    Year year = Year.parse(line[0]);
                    Month month = Month.valueOf(line[1]);
                    MonthDay monthDay = MonthDay.parse(line[2]);
                    String customerNumber = (line[3]);
                    String customerName = (line[4]);
                    String address = (line[5]);
                    String city = (line[6]);
                    String province = (line[7]);
                    String zip = (line[8]);
                    String productCode = (line[10]);
                    int quantity = Integer.parseInt((line[12]));
                    double amount = Double.parseDouble((line[13]));
                    String quarter = (line[14]);

                    RetailSale retailSale = new RetailSale();
                    retailSale.setSaleYear(year);
                    retailSale.setSaleMonth(month);
                    retailSale.setSaleMonthDay(monthDay);
                    retailSale.setCustomerNumber(customerNumber);
                    retailSale.setCustomerName(customerName);
                    retailSale.setCustomerAddress(address);
                    retailSale.setCustomerCity(city);
                    retailSale.setCustomerProvince(province);
                    retailSale.setCustomerZip(zip);
                    retailSale.setDistributorProductCode(productCode);
                    retailSale.setSaleQuantity(quantity);
                    retailSale.setSaleAmount(amount);
                    retailSale.setSaleQuarter(quarter);
                    retailSale.setCoutuCode(productService.getCoutuCode(distributor, productCode));
                    retailSales.add(retailSale);
                    retailSalesRepository.save(retailSale);

                }
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Number of retail sales found for PureSource " + retailSales.size());
    }


}
