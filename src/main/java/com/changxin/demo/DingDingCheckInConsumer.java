package com.changxin.demo;

import com.changxin.demo.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class DingDingCheckInConsumer implements Consumer {

    private static List<String> result = new ArrayList<>();

    @Override
    public void accept(Object o) {
        List<String> record = (List<String>)o;
        record.add(record.get(0));
        record.add(record.get(5));

        boolean res = false;
        try {
            res = TimeUtils.isCheckIn(record.get(6));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(res) {
            result.add(TimeUtils.getCheckInStatus(record.get(9), Boolean.TRUE));
            result.add(null);
        } else {
            result.add(null);
            result.add(TimeUtils.getCheckInStatus(record.get(9), Boolean.FALSE));
        }
    }

    public List<String> getResult() {
        return result;
    }
}
