package com.changxin.demo.consumer;

import com.alibaba.excel.util.ObjectUtils;
import com.changxin.demo.utils.CheckInTimeUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class DingDingCheckInConsumer implements Consumer {

    private static List<Object> result = new ArrayList<>(7);

    @Override
    public void accept(Object o) {
        result.clear();

        List<Object> record = (List<Object>)o;
        result.add(record.get(0));
        result.add(record.get(5));

        boolean res = false;
        try {
            res = CheckInTimeUtils.isCheckIn((String)record.get(6));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(res) {
            result.add(CheckInTimeUtils.getCheckInStatus((String)record.get(6), Boolean.TRUE));
            result.add(null);
        } else {
            result.add(null);
            result.add(CheckInTimeUtils.getCheckInStatus((String)record.get(6), Boolean.FALSE));

        }

        if(!ObjectUtils.isEmpty(record.get(15))) {
            result.add(record.get(15));
        } else {
            result.add(null);
        }

        if(!ObjectUtils.isEmpty(record.get(16))) {
            result.add(record.get(16));
        } else {
            result.add(null);
        }

        if(!ObjectUtils.isEmpty(record.get(17))) {
            result.add(record.get(17));
        } else {
            result.add(null);
        }
    }

    private List<Object> result() {
        return result;
    }
}
