package com.changxin.demo.consumer;

import com.changxin.demo.utils.CheckInTimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class FPICheckInConsumer implements Consumer {

    private static List<String> result = new ArrayList<>(7);

    @Override
    public void accept(Object o) {
        result.clear();
        //3th name, 5th date, 9th checkinTime， 10th checkoutTime
        List<String> record = (List<String>)o;
        result.add(record.get(3));
        result.add(record.get(5));
        //判断值
        result.add(CheckInTimeUtils.getCheckInStatus(record.get(9), Boolean.TRUE));
        result.add(CheckInTimeUtils.getCheckInStatus(record.get(10), Boolean.FALSE));
        for(int i = 0 ; i < 3; i++) {
            result.add(null);
        }
    }

    private List<String> result() {
        return result;
    }
}
