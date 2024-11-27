package com.cgnial.salesreports.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlTableParser {

    public static List<List<String>> parseHtmlTable(String filePath) throws Exception {
        File file = new File(filePath);
        Document doc = Jsoup.parse(file, "UTF-8");

        List<List<String>> tableData = new ArrayList<>();

        Element table = doc.select("table").first();
        Elements rows = table.select("tr");

        for (Element row : rows) {
            List<String> rowData = new ArrayList<>();
            Elements cells = row.select("th, td");
            for (Element cell : cells) {
                rowData.add(cell.text());
            }
            tableData.add(rowData);
        }
        return tableData;
    }
}

