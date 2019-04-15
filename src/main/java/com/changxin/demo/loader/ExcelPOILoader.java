package com.changxin.demo.loader;

import com.changxin.demo.utils.CheckInSheetTemplate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class ExcelPOILoader {

    private static final ImmutableList<String> TITLES = ImmutableList.of("日期", "上班", "下班", "图1", "图2");
    private static OutputStream outputStream;
    private static Workbook workbook;
    private static ImmutableMap<String, CellStyle> styles;
    private Sheet sheet;


    public ExcelPOILoader of(String sheetName) {
        sheet = workbook.createSheet(sheetName);
        sheet.setDefaultColumnWidth((short)15);
        return this;
    }

    public static ExcelPOILoader newLoader(String destination) throws IOException {
        outputStream = new BufferedOutputStream(new FileOutputStream(destination));
        workbook = new HSSFWorkbook();
        styles = CheckInSheetTemplate.buildStyles(workbook);
        return new ExcelPOILoader();
    }

    public void load(List<Object> objectToLoad) {
        for(int i = 1; i <= objectToLoad.size(); i++) {
            Row row = sheet.createRow(i);
            for(int cellNum = 0; cellNum < objectToLoad.size(); cellNum++) {
                Cell cell = row.createCell(cellNum);
                cell.setCellStyle(styles.get("body"));
                Object object = objectToLoad.get(cellNum);
                cellHelper(object, cell);
            }
        }
    }

    public void commit() throws IOException {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream != null) {
                outputStream.close();
            }
            if(workbook != null) {
                workbook.close();
            }
        }
    }

    private void cellHelper(Object o, Cell cell) {
        if(o instanceof Hyperlink) {
            cell.setHyperlink((Hyperlink) o);
        } else {
            if(o != null) {
                cell.setCellValue(o.toString());
            }
        }
    }

    private void writeHeader() {
        Row row = sheet.createRow(0);
        for(int i = 0; i < TITLES.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(styles.get("header"));
            cell.setCellValue(TITLES.get(i));
        }
    }

    private ExcelPOILoader() {
    }
}
