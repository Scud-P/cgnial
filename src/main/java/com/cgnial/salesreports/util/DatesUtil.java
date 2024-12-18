package com.cgnial.salesreports.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DatesUtil {

    public int convertMonthToIntValue(String month) {
        if (month == null || month.length() != 3) {
            return -1;
        }

        return switch (month.toUpperCase()) {
            case "JAN" -> 1;
            case "FEB" -> 2;
            case "MAR" -> 3;
            case "APR" -> 4;
            case "MAY" -> 5;
            case "JUN" -> 6;
            case "JUL" -> 7;
            case "AUG" -> 8;
            case "SEP" -> 9;
            case "OCT" -> 10;
            case "NOV" -> 11;
            case "DEC" -> 12;
            default -> -1;
        };
    }


    public int determineQuarter(int month) {
        return (month - 1) / 3 + 1;
    }

    public int determineMonth(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(dateString, formatter).getMonthValue();
    }

    public int determineYear(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return LocalDate.parse(dateString, formatter).getYear();
    }

    public LocalDate toLocalDate(String dateString) {
        if(dateString != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            return LocalDate.parse(dateString, formatter);
        }
        return LocalDate.of(2000, 1, 1);
    }
}
