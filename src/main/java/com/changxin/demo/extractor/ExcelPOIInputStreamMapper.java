package com.changxin.demo.extractor;

import com.alibaba.excel.metadata.Sheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class ExcelPOIInputStreamMapper<T> implements InputStreamMapper<T> {

    public static ExcelPOIInputStreamMapper newMapper(Sheet sheet) {
        return new ExcelPOIInputStreamMapper(sheet);
    }

    private Sheet sheet;

    private ExcelPOIInputStreamMapper(Sheet sheet) {
        this.sheet = sheet;
    }

    @Override
    public Iterator apply(InputStream inputStream) {
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }

        return null;
    }
}
