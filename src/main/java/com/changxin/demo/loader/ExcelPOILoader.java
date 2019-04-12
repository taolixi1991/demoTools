package com.changxin.demo.loader;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelPOILoader {

    private static OutputStream outputStream;

    private Sheet sheet;
    private Table table;

    public ExcelPOILoader of(Sheet newSheet, Table newTable) {
        sheet = newSheet;
        table = newTable;
        return this;
    }

    public static boolean writeExcel(String filepath, String sheetName, List<String> titles,
                                     List<Map<String, Object>> values) throws IOException {
        return false;
    }
}
