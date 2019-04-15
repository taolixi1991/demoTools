package com.changxin.demo.utils;

import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.util.*;

import static org.apache.poi.ss.usermodel.IndexedColors.BRIGHT_GREEN;

public class CheckInSheetTemplate {


    private static final ImmutableList<String> titles = ImmutableList.of("日期", "上班", "下班", "图1", "图2");

    //======easyexcel
    public static Table buildCheckInSheetTemplate() {
        Table table = buildTable();
        return table;
    }

    private static Table buildTable() {
        List<List<String>> headList = new ArrayList<>();

        titles.forEach(title -> headList.add(Collections.singletonList(title)));

        Table table = new Table(1);
        table.setHead(headList);
        TableStyle style = new TableStyle();
        style.setTableHeadBackGroundColor(BRIGHT_GREEN);
        table.setTableStyle(style);

        return table;
    }

    //======APACHE POI
    public static ImmutableMap<String, CellStyle> buildStyles(Workbook workbook) {
        ImmutableMap.Builder<String, CellStyle> list = ImmutableMap.builder();
        //title
        HSSFCellStyle titleStyle = (HSSFCellStyle) workbook.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleStyle.setLocked(true);
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        Font titleFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short)16);
        titleFont.setBold(true);
        titleFont.setFontName("微软雅黑");
        titleStyle.setFont(titleFont);
        list.put("title", titleStyle);

        //header
        HSSFCellStyle headerStyle = cellStyleStub(workbook);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        titleFont.setFontName("微软雅黑");
        headerStyle.setFont(headerFont);

        list.put("header", headerStyle);

        //body

        HSSFCellStyle bodyStyle = cellStyleStub(workbook);
        bodyStyle.setAlignment(HorizontalAlignment.CENTER); // 居中设置
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font cellStyleFont = workbook.createFont();
        cellStyleFont.setFontHeightInPoints((short) 12);
        cellStyleFont.setColor(IndexedColors.BLUE_GREY.getIndex());
        cellStyleFont.setFontName("微软雅黑");
        bodyStyle.setFont(cellStyleFont);

        list.put("body", bodyStyle);

         return list.build();
    }


    private static HSSFCellStyle cellStyleStub(Workbook workbook) {
        HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        return style;
    }



}
