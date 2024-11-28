package com.cgnial.salesreports.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlTableParser {

    public static List<List<String>> parseHtmlTable(InputStream inputStream) throws Exception {
        Document doc = Jsoup.parse(inputStream, "UTF-8", "");

        List<List<String>> tableData = new ArrayList<>();

        Element table = doc.select("table").first();
        if (table != null) {
            Elements rows = table.select("tr");
            for (Element row : rows) {
                List<String> rowData = new ArrayList<>();
                Elements cells = row.select("th, td");
                for (Element cell : cells) {
                    rowData.add(cell.text());
                }
                tableData.add(rowData);
            }
        } else {
            throw new IllegalArgumentException("No table found in the uploaded HTML file.");
        }

        return tableData;
    }

}

