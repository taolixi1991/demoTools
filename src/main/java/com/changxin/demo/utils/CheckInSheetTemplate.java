package com.changxin.demo.utils;

import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.poi.ss.usermodel.IndexedColors.BRIGHT_GREEN;

public class CheckInSheetTemplate {

    public static Table buildCheckInSheetTemplate(int num) {
        Table table = buildTable(num);
        return table;
    }

    private static Table buildTable(int num) {
        List<List<String>> headList = new ArrayList<>();

        for(int i = 0 ; i < num; i++) {
            String columnName = "Day " + i + "th";
            List<String> tmp = Arrays.asList(columnName);
            headList.add(tmp);
        }

        Table table = new Table(1);
        table.setHead(headList);
        TableStyle style = new TableStyle();
        style.setTableHeadBackGroundColor(BRIGHT_GREEN);
        table.setTableStyle(style);

        return table;
    }

}
