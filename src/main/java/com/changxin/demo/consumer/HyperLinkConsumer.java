package com.changxin.demo.consumer;

import com.changxin.demo.utils.CheckInTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Hyperlink;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class HyperLinkConsumer implements Consumer {
    private static List<Object> result = new ArrayList<>(4);

    @Override
    public void accept(Object o) {
        try {
            result.clear();
            //name date time
            List<Cell> record = (List<Cell>)o;

            String name = record.get(0).getStringCellValue();
            String date = record.get(1).getStringCellValue();
            String time = record.get(2).getStringCellValue();

            if(StringUtils.isBlank(name) || StringUtils.isBlank(date) || StringUtils.isBlank(time)) {
                return;
            }
            result.add(name);
            result.add(date);

            boolean isCheckIn = true;
            try {
                isCheckIn = CheckInTimeUtils.isCheckIn(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Hyperlink link = record.get(3).getHyperlink();
            if(link != null) {
                link.setLabel(time);
            }

            if(isCheckIn) {
                result.add(link);
                result.add(null);
            } else {
                result.add(null);
                result.add(link);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private List<Object> result() {
        return result;
    }
}
