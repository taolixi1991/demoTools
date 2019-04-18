package com.changxin.demo.consumer;

import com.changxin.demo.utils.CheckInTimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class FPICheckInConsumer implements Consumer {

    private static List<String> result = new ArrayList<>(4);

    @Override
    public void accept(Object o) {
        try {
            result.clear();
            //3th name, 5th date, 9th checkinTime， 10th checkoutTime
            List<String> record = (List<String>)o;
            result.add(record.get(3));
            result.add(record.get(5));
            //判断值
            if(StringUtils.isNotBlank(record.get(9))) {
                result.add(record.get(9) + " " + CheckInTimeUtils.getCheckInStatus(record.get(9), Boolean.TRUE));
            } else {
                result.add(null);
            }

            if(StringUtils.isNotBlank(record.get(10))) {
                result.add(record.get(10) + " " + CheckInTimeUtils.getCheckInStatus(record.get(10), Boolean.TRUE));
            } else {
                result.add(null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private List<String> result() {
        return result;
    }
}
