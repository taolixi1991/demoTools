package com.changxin.demo.extractor;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelPOIInputStreamMapper<T> implements InputStreamMapper<T> {

    private static int DATA_START_NUM = 3;

    public static ExcelPOIInputStreamMapper newMapper() {
        return new ExcelPOIInputStreamMapper();
    }

    private ExcelPOIInputStreamMapper() {
    }

    @Override
    public Iterator apply(InputStream inputStream) {
        List<Cell> excel = new ArrayList<>();
        try {
            Workbook workbook = new HSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            if(sheet != null) {
                excel = readExcelSheet(sheet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return excel.iterator();
    }

    private List<Cell> readExcelSheet(Sheet sheet) {
        List<Cell> data = new ArrayList<>();
        int rowNums = sheet.getLastRowNum();
        for(int i = 0; i < rowNums; i++) {
            Row row = sheet.getRow(i);
            if(row != null) {
                //0 : name, 5: date, 6: time, 15-20 pic
                data.add(row.getCell(0));
                data.add(row.getCell(5));
                data.add(row.getCell(6));
                if(row.getCell(15) != null) {
                    data.add(row.getCell(15));
                }
            }
        }
        return data;
    }
}
