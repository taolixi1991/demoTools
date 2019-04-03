package com.changxin.demo.module;

import com.changxin.demo.FPICheckInConsumer;
import com.changxin.demo.CheckInInfo;
import com.changxin.demo.ExcelInputStreamExtractor;
import com.changxin.demo.ExcelInputStreamMapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.prefs.BackingStoreException;

@SuppressWarnings("unchecked")
public class CheckInJob {

    private HashMap<String, List<CheckInInfo>> checkIns;


    public CheckInJob newJob(String excelFromFPI, String excelFromDingDing) {
        return new CheckInJob(excelFromFPI, excelFromDingDing);
    }


    public void start(String excelFromFPI, String excelFromDingDing) {
        analysis(excelFromFPI);
        analysis(excelFromDingDing);



    }




    //=========================================
    private void analysis(String excelPath) {
        ExcelInputStreamExtractor extractorForLocal = getExtractor(excelPath);

        try {
            extractorForLocal.open();
            //======
            while(extractorForLocal.hasNext()){
                FPICheckInConsumer consumer = new FPICheckInConsumer();
                extractorForLocal.next().ifPresent(consumer);
                List<String> records = consumer.getResult();
                CheckInInfo info = new CheckInInfo(records.get(1), records.get(2), records.get(3));
                checkIns.getOrDefault(records.get(0), new ArrayList<>()).add(info);
            }
            //======
            extractorForLocal.close();
        } catch (BackingStoreException | IOException e) {
            e.printStackTrace();
        }
    }

    private ExcelInputStreamExtractor getExtractor(String excelPath) {
        FileInputStream inputStream;
        ExcelInputStreamExtractor extractor = null;
        try {
            inputStream = new FileInputStream(excelPath);
            extractor = ExcelInputStreamExtractor.of(
                    inputStream,
                    ExcelInputStreamMapper.newMapper()
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return extractor;
    }


    private CheckInJob(String excelFromFPI, String excelFromDingDing) {
        checkIns = new HashMap<>();
        start(excelFromFPI, excelFromDingDing);
    }

}
