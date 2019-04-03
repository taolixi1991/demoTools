package com.changxin.demo;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.changxin.demo.utils.CheckInSheetTemplate;
import com.changxin.demo.utils.Month;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ceshi {
    public static void main(String[] args) {
        Sheet sheet = new Sheet(1,2);
        sheet.setAutoWidth(true);

        List<List<String>> object = new ArrayList<>();
        List<String> ob1 = Arrays.asList("a", "aa", "aaa", "aaaa");
        List<String> ob2 = Arrays.asList("b", "bb", "bbb", "bbbb");
        List<String> ob3 = Arrays.asList("c", "cc", "ccc", "cccc");
        object.add(ob1);
        object.add(ob2);
        object.add(ob3);

        Table table = CheckInSheetTemplate.buildCheckInSheetTemplate(Month.AUGUST.value());

        ExcelLoader excel = ExcelLoader.of(sheet, table);
        try {
            excel.open("C:\\Users\\Zacks\\Desktop\\CheckinInfo.xlsx");
            excel.load(object);
            excel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======Finisheddddd=======");
    }
}
