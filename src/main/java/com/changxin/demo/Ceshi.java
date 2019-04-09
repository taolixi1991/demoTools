package com.changxin.demo;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.changxin.demo.loader.ExcelLoader;
import com.changxin.demo.utils.CheckInSheetTemplate;
import com.changxin.demo.common.Month;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ceshi {
    public static void main(String[] args) {
        Sheet sheet1 = new Sheet(1,1);
        sheet1.setSheetName("表1");
        sheet1.setAutoWidth(true);

        Sheet sheet2 = new Sheet(2,1);
        sheet2.setSheetName("表2");
        sheet2.setAutoWidth(true);

        List<List<Object>> object = new ArrayList<>();
//        List<String> ob1 = Arrays.asList("a", "aa", "aaa", "aaaa");
//        List<String> ob2 = Arrays.asList("b", "bb", "bbb", "bbbb");
//        List<String> ob3 = Arrays.asList("c", "cc", "ccc", "cccc");
//        object.add(ob1);
//        object.add(ob2);
//        object.add(ob3);

        Table table = CheckInSheetTemplate.buildCheckInSheetTemplate();

        List<Object> objectToLoad = new ArrayList<>();
        objectToLoad.add("String");
        objectToLoad.add(123);

        object.add(objectToLoad);
//        ExcelLoader excel = ExcelLoader.of(Arrays.asList(sheet1, sheet2), table);
//        try {
//            excel.open("C:\\Users\\Zacks\\Desktop\\CheckinInfo.xlsx");
//            excel.load(object);
//            excel.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println("======Finisheddddd=======");
    }
}
