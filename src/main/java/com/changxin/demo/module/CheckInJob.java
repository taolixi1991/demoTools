package com.changxin.demo.module;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.changxin.demo.consumer.DingDingCheckInConsumer;
import com.changxin.demo.consumer.FPICheckInConsumer;
import com.changxin.demo.extractor.CheckInInfo;
import com.changxin.demo.extractor.ExcelInputStreamExtractor;
import com.changxin.demo.extractor.ExcelInputStreamMapper;
import com.changxin.demo.loader.ExcelLoader;
import com.changxin.demo.utils.CheckInSheetTemplate;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.prefs.BackingStoreException;

@SuppressWarnings("unchecked")
public class CheckInJob {

    private HashMap<String, List<List<Object>>> checkIns;


    public static CheckInJob newJob(String excelFromFPI, String excelFromDingDing) {
        return new CheckInJob(excelFromFPI, excelFromDingDing);
    }


    private void start(String excelFromFPI, String excelFromDingDing) {
        analysis(excelFromFPI, new Sheet(1, 1), FPICheckInConsumer.class);
        analysis(excelFromDingDing, new Sheet(1, 3), DingDingCheckInConsumer.class);
        System.out.println(checkIns.size());
    }

    public void execute() {
        Table table = CheckInSheetTemplate.buildCheckInSheetTemplate();
        List<Sheet> sheets = getSheetList();

        try {
            ExcelLoader loader = ExcelLoader.newLoader("C:\\Users\\Zacks\\Desktop\\CheckinInfo.xlsx");

            System.out.println("load is prepared");
            for(Sheet sheet : sheets) {
                loader = loader.of(sheet, table);
                System.out.println("applied sheet and table");

                loader.load(checkIns.get(sheet.getSheetName()));
            }

            loader.commit();
            loader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======Finisheddddd=======");
    }





    //=========================================
    public void analysis(String excelPath, Sheet sheet, Class consumerClass) {
        ExcelInputStreamExtractor extractor = getExtractor(excelPath, sheet);

        System.out.println("we got extractor" + extractor.toString());

        try {
            extractor.open();
            System.out.println(extractor.toString() + " is opened");

            //======
            while(extractor.hasNext()){
                Object consumer = consumerClass.newInstance();
                extractor.next().ifPresent((Consumer)consumer);
                Method method = consumerClass.getDeclaredMethod("result");
                method.setAccessible(true);
                List<Object> records = (List<Object>)method.invoke(consumer);
                CheckInInfo info = new CheckInInfo((String)records.get(1), (String)records.get(2), (String)records.get(3),
                        records.get(4), records.get(5), records.get(6));
                System.out.println(info.toList());

                if(!checkIns.containsKey(records.get(0))) {
                    checkIns.put((String)records.get(0), new ArrayList<>());
                }
                checkIns.get(records.get(0)).add(info.toList());
//                checkIns.getOrDefault(records.get(0), new ArrayList<>()).add(info);
            }
            //======
            extractor.close();
        } catch (BackingStoreException | IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private ExcelInputStreamExtractor getExtractor(String excelPath, Sheet sheet) {
        BufferedInputStream inputStream;
        ExcelInputStreamExtractor extractor = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(excelPath));

            extractor = ExcelInputStreamExtractor.of(
                    inputStream,
                    ExcelInputStreamMapper.newMapper(sheet)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractor;
    }

    private List<Sheet> getSheetList() {
        List<Sheet> sheets = new ArrayList<>();
        int count = 1;
        for(String key : checkIns.keySet()) {
            Sheet sheet = new Sheet(count++, 1);
            sheet.setSheetName(key);
            sheet.setAutoWidth(true);
            sheets.add(sheet);
        }
        return sheets;
    }


    public CheckInJob(String excelFromFPI, String excelFromDingDing) {
        checkIns = new HashMap<>();
        start(excelFromFPI, excelFromDingDing);
    }

    public static void main(String[] args) {

        String excel1 = "D:\\workspace\\tmpDemo\\src\\main\\resources\\指纹机.xlsx";
        String excel2 = "D:\\workspace\\tmpDemo\\src\\main\\resources\\钉钉签到报表.xls";

        //CheckInJob job = new CheckInJob(null, null);
        CheckInJob job = CheckInJob.newJob(excel1, excel2);
        //job.analysis(excel2,  new Sheet(1, 3), DingDingCheckInConsumer.class);

        job.execute();
        System.out.println();
    }
}
