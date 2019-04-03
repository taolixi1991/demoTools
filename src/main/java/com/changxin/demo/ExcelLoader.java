package com.changxin.demo;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@SuppressWarnings("unchecked")
public class ExcelLoader {
    private OutputStream outputStream;

    private final Sheet sheet;
    private final Table table;

    public static ExcelLoader of(Sheet sheet, Table table) {
        return new ExcelLoader(sheet, table);
    }

    public void open(String destination) throws IOException {
        outputStream = new FileOutputStream(destination);
    }

    public void load(List objectToLoad) {
        if(outputStream == null || sheet == null) {
            throw new RuntimeException("Unable to load");
        }
        ExcelWriter writer = EasyExcelFactory.getWriter(outputStream);
        writer.write1(objectToLoad, sheet, table);

        writer.finish();
    }

    public void close() throws IOException {
        if(outputStream != null) {
            outputStream.close();
        }
    }

    private ExcelLoader(Sheet sheet, Table table) {
        this.sheet = sheet;
        this.table = table;
    }
}
