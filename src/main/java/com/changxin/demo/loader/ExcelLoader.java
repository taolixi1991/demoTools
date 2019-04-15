package com.changxin.demo.loader;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import lombok.Setter;

import java.io.*;
import java.net.URL;
import java.util.*;

@SuppressWarnings("unchecked")
public class ExcelLoader {
    private static OutputStream outputStream;
    private static ExcelWriter writer;

    private Sheet sheet;
    private Table table;

    public ExcelLoader of(Sheet newSheet, Table newTable) {
        sheet = newSheet;
        table = newTable;
        return this;
    }

//    public static ExcelLoader of(List<Sheet> sheets, Table table) {
//        return new ExcelLoader(sheets, table);
//    }

    public static ExcelLoader newLoader(String destination) throws IOException {
        outputStream = new BufferedOutputStream(new FileOutputStream(destination));
        writer = EasyExcelFactory.getWriter(outputStream);
        return new ExcelLoader();
    }

    public void load(List objectToLoad) {
        if(writer == null || sheet == null) {
            throw new RuntimeException("Unable to load");
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        System.out.println(objectToLoad);
        writer.write1(objectToLoad, sheet, table);
    }

    public void commit() {
        writer.finish();
    }

    public void close() throws IOException {
        if(outputStream != null) {
            outputStream.close();
        }
    }

    private ExcelLoader() {
    }
}
