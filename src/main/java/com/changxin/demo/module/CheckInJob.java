package com.changxin.demo.module;

import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.changxin.demo.consumer.DingDingCheckInConsumer;
import com.changxin.demo.consumer.FPICheckInConsumer;
import com.changxin.demo.common.CheckInInfo;
import com.changxin.demo.consumer.HyperLinkConsumer;
import com.changxin.demo.extractor.ExcelInputStreamExtractor;
import com.changxin.demo.extractor.ExcelInputStreamMapper;
import com.changxin.demo.extractor.ExcelPOIInputStreamMapper;
import com.changxin.demo.loader.ExcelLoader;
import com.changxin.demo.loader.ExcelPOILoader;
import com.changxin.demo.utils.CheckInSheetTemplate;
import com.changxin.demo.utils.CheckInTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Hyperlink;

import java.io.*;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class CheckInJob {

    /**
     * Map<name, Map<date, checkinInfo>>
     */
    private HashMap<String, Map<String, CheckInInfo>> checkIns;


    public static CheckInJob newJob(String excelFromFPI, String excelFromDingDing) throws RuntimeException {
        return new CheckInJob(excelFromFPI, excelFromDingDing);
    }


    private void execute(String excelFromFPI, String excelFromDingDing) throws RuntimeException {
        analysis(excelFromFPI, new Sheet(1, 1), false, FPICheckInConsumer.class);
        analysis(excelFromDingDing, new Sheet(1, 3),false, DingDingCheckInConsumer.class);
        analysis(excelFromDingDing, null, true, HyperLinkConsumer.class);
    }


    public void generateResult() {
        Table table = CheckInSheetTemplate.buildCheckInSheetTemplate();
        List<Sheet> sheets = getSheetList();

        try {
            ExcelLoader loader = ExcelLoader.newLoader("C:\\Users\\Zacks\\Desktop\\CheckinInfo.xlsx");

            System.out.println("load is prepared");
            for(Sheet sheet : sheets) {
                loader = loader.of(sheet, table);
                List<List<Object>> res = sortRecord(sheet.getSheetName());
                loader.load(res);
            }

            loader.commit();
            loader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("======Finisheddddd=======");
    }


    public boolean generateResultWithHyperLink(String pathDir) {
        try {
            //String outputFilePath = this.getClass().getClassLoader().getResource("").getPath() + "签到统计结果.xls";
            //System.out.println(outputFilePath);
            ExcelPOILoader loader = ExcelPOILoader.newLoader(pathDir +"\\签到统计结果.xls");
            for(String key : checkIns.keySet()) {
                loader.of(key);
                List<List<Object>> res = sortRecord(key);
                loader.load(res);
            }
            loader.commit();
            System.out.println("======Finisheddddd=======");

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }

    //=========================================
    public void analysis(String excelPath, Sheet sheet, boolean isPOIBased, Class consumerClass) throws RuntimeException {
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
                if("HyperLinkConsumer".equals(consumerClass.getSimpleName())) {
                    info = new CheckInInfo((String)records.get(1),null, null, (Hyperlink)records.get(2), (Hyperlink)records.get(3));
                } else {
                    info = new CheckInInfo((String)records.get(1), (String)records.get(2), (String)records.get(3), null, null);
                }

                if(!checkIns.containsKey(records.get(0))) {
                    checkIns.put((String)records.get(0), new HashMap());
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
        } catch (Exception e) {
            throw  new RuntimeException("执行错误" + e);
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
            record.setDepart(info.getDepart());
        }
        record.addHyperLink(info.getArrivePic());
        record.addHyperLink(info.getDepartPic());

        System.out.println(record.toList().toString());
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

    private List<List<Object>> sortRecord(String keyName) {
        Map<String, CheckInInfo> map = checkIns.get(keyName);
        List<List<Object>> res = new ArrayList<>();
        for(String date : map.keySet()) {
            res.add(map.get(date).toList());
        }
        res.sort((o1, o2) -> {
            try {
                return CheckInTimeUtils.compare((String)o1.get(0), (String)o2.get(0));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;
        });

        return res;
    }


    public CheckInJob(String excelFromFPI, String excelFromDingDing) throws RuntimeException {
        checkIns = new HashMap<>();
        execute(excelFromFPI, excelFromDingDing);
    }

    public static void main(String[] args) {

        String excel1 = "D:\\workspace\\tmpDemo\\src\\main\\resources\\指纹机.xlsx";
        String excel2 = "D:\\workspace\\tmpDemo\\src\\main\\resources\\钉钉签到报表.xls";

        //CheckInJob job = new CheckInJob(null, null);
        CheckInJob job = CheckInJob.newJob(excel1, excel2);
        //job.analysis(excel2,  new Sheet(1, 3), DingDingCheckInConsumer.class);

        String output = "C:\\Users\\Zacks\\Desktop\\CheckinInfo.xls";
        String dir = "C:\\Users\\Zacks\\Desktop";
        job.generateResultWithHyperLink(dir);
        System.out.println();
    }
}
