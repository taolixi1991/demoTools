package com.changxin.demo.utils;

import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.poi.ss.usermodel.IndexedColors.BRIGHT_GREEN;

public class CheckInSheetTemplate {

    public static Table buildCheckInSheetTemplate() {
        Table table = buildTable();
        return table;
    }

    private static Table buildTable() {
        List<List<String>> headList = new ArrayList<>();

        headList.add(Collections.singletonList("日期"));
        headList.add(Collections.singletonList("上班"));
        headList.add(Collections.singletonList("下班"));
        headList.add(Collections.singletonList("图1"));
        headList.add(Collections.singletonList("图2"));
        headList.add(Collections.singletonList("图3"));

        Table table = new Table(1);
        table.setHead(headList);
        TableStyle style = new TableStyle();
        style.setTableHeadBackGroundColor(BRIGHT_GREEN);
        table.setTableStyle(style);

        return table;
    }

}
