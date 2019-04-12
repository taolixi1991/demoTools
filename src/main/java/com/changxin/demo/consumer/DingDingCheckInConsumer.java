package com.changxin.demo.consumer;

import com.alibaba.excel.util.ObjectUtils;
import com.changxin.demo.utils.CheckInTimeUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class DingDingCheckInConsumer implements Consumer {

    private static List<Object> result = new ArrayList<>(4);

    @Override
    public void accept(Object o) {
        result.clear();

        List<String> record = (List<String>)o;
        result.add(record.get(0));
        result.add(record.get(5));

        boolean res = false;
        try {
            res = CheckInTimeUtils.isCheckIn(record.get(6));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(res) {
            result.add(CheckInTimeUtils.getCheckInStatus(record.get(6), Boolean.TRUE));
            result.add(null);
        } else {
            result.add(null);
            result.add(CheckInTimeUtils.getCheckInStatus(record.get(6), Boolean.FALSE));
        }
    }

    private List<Object> result() {
        return result;
    }
}
