package com.changxin.demo.module;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.changxin.demo.consumer.DingDingCheckInConsumer;
import com.changxin.demo.consumer.FPICheckInConsumer;
import com.changxin.demo.common.CheckInInfo;
import com.changxin.demo.extractor.ExcelInputStreamExtractor;
import com.changxin.demo.extractor.ExcelInputStreamMapper;
import com.changxin.demo.extractor.ExcelPOIInputStreamMapper;
import com.changxin.demo.loader.ExcelLoader;
import com.changxin.demo.utils.CheckInSheetTemplate;
import com.changxin.demo.utils.CheckInTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Hyperlink;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.function.Consumer;
import java.util.prefs.BackingStoreException;

@SuppressWarnings("unchecked")
public class CheckInJob {

    private HashMap<String, Map<String, CheckInInfo>> checkIns;


    public static CheckInJob newJob(String excelFromFPI, String excelFromDingDing) {
        return new CheckInJob(excelFromFPI, excelFromDingDing);
    }


    private void execute(String excelFromFPI, String excelFromDingDing) {
        analysis(excelFromFPI, new Sheet(1, 1), false, FPICheckInConsumer.class);
        analysis(excelFromDingDing, new Sheet(1, 3),false, DingDingCheckInConsumer.class);


    }


    public void generateResult() {
        Table table = CheckInSheetTemplate.buildCheckInSheetTemplate();
        List<Sheet> sheets = getSheetList();

        try {
            ExcelLoader loader = ExcelLoader.newLoader("C:\\Users\\Zacks\\Desktop\\CheckinInfo.xlsx");

            System.out.println("load is prepared");
            for(Sheet sheet : sheets) {
                loader = loader.of(sheet, table);
                System.out.println("applied sheet and table");

                Map<String, CheckInInfo> map = checkIns.get(sheet.getSheetName());
                List<List<Object>> res = new ArrayList();
                for(String key : map.keySet()) {
                    res.add(map.get(key).toList());
                }
                res.sort((o1, o2) -> {
                    try {
                        return CheckInTimeUtils.compare((String)o1.get(0), (String)o2.get(0));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                });
                loader.load(res);
            }

            loader.commit();
            loader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======Finisheddddd=======");
    }

    //=========================================
    public void analysis(String excelPath, Sheet sheet, boolean isPOIBased, Class consumerClass) {
        ExcelInputStreamExtractor extractor = getExtractor(excelPath, sheet, isPOIBased);

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
                CheckInInfo info;
                if("HyperLinkConsumer".equals(consumerClass.getName())) {
                    info = new CheckInInfo((String)records.get(1),null, null, (Hyperlink)records.get(2), (Hyperlink)records.get(3));
                } else {
                    info = new CheckInInfo((String)records.get(1), (String)records.get(2), (String)records.get(3), null, null);
                }

                if(!checkIns.containsKey(records.get(0))) {
                    checkIns.put((String)records.get(0), new HashMap<>());
                }
                //
                if(!checkIns.get(records.get(0)).containsKey(info.getDate())) {
                    checkIns.get(records.get(0)).put(info.getDate(), info);
                } else {
                    update((String)records.get(0), info);
                }
            }
            //======
            extractor.close();
        } catch (BackingStoreException | IOException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private ExcelInputStreamExtractor getExtractor(String excelPath, Sheet sheet, boolean isPOIBaesd) {
        BufferedInputStream inputStream;
        ExcelInputStreamExtractor extractor = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(excelPath));

            if(isPOIBaesd) {
                extractor = ExcelInputStreamExtractor.of(
                        inputStream,
                        ExcelPOIInputStreamMapper.newMapper()
                );
            } else {
                extractor = ExcelInputStreamExtractor.of(
                        inputStream,
                        ExcelInputStreamMapper.newMapper(sheet)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractor;
    }

    private void update(String name, CheckInInfo info) {
        CheckInInfo record = checkIns.get(name).get(info.getDate());

        if(StringUtils.isNotBlank(info.getArrive()) && StringUtils.isBlank(record.getArrive())) {
            record.setArrive(info.getArrive());
        }

        if(StringUtils.isNotBlank(info.getDepart()) && StringUtils.isBlank(record.getDepart())) {
            record.setArrive(info.getDepart());
        }
        record.addHyperLink(info.getArrivePic());
        record.addHyperLink(info.getDepartPic());

        checkIns.get(name).put(info.getDate(), record);
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
        execute(excelFromFPI, excelFromDingDing);
    }

    public static void main(String[] args) {

        String excel1 = "D:\\workspace\\tmpDemo\\src\\main\\resources\\指纹机.xlsx";
        String excel2 = "D:\\workspace\\tmpDemo\\src\\main\\resources\\钉钉签到报表.xls";

        //CheckInJob job = new CheckInJob(null, null);
        CheckInJob job = CheckInJob.newJob(excel1, excel2);
        //job.analysis(excel2,  new Sheet(1, 3), DingDingCheckInConsumer.class);

        job.generateResult();
        System.out.println();
    }
}
