//package com.changxin.demo;
//
//
//import org.apache.poi.ss.usermodel.Hyperlink;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class Ceshi {
//
//    public static void main(String[] args) throws IOException {
//
//        String filePath = "D:\\workspace\\tmpDemo\\src\\main\\resources\\钉钉签到报表.xls";
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("钉钉签到报表.xls");
//
//        Workbook wb = ExcelHyperLinkUtils.getWorkbok(inputStream, new File(filePath));
//        Sheet sheet = wb.getSheetAt(0);
//
//        Row row = sheet.getRow(5);
//        Hyperlink link = row.getCell(15).getHyperlink();
//        System.out.println(link.getAddress());
//
////        Iterator<Row> rows = sheet.rowIterator();
////
////        Row
////        while(rows.hasNext()) {
////            System.out.println("11111");
////            for(int i = 0; i < 3; i++) {
////                rows.next();
////            }
////            System.out.println("2222");
////
////            Row row = rows.next();
////            System.out.println(row.toString());
////
////            Hyperlink link = row.getCell(14).getHyperlink();
////
////            System.out.println(link);
////            System.out.println("3333");
////
////            System.out.println(link.getAddress());
////        }
//
//
//
//
//        System.out.println("======Finisheddddd=======");
//    }
//}
