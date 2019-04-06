package com.changxin.demo;

import com.changxin.demo.utils.CheckInStatus;
import com.changxin.demo.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class FPICheckInConsumer implements Consumer {

    private static List<String> result = new ArrayList<>();

    @Override
    public void accept(Object o) {

        //3th name, 5th date, 9th checkinTime， 10th checkoutTime
        List<String> record = (List<String>)o;
        result.add(record.get(3));
        result.add(record.get(5));
        //判断值
        result.add(TimeUtils.getCheckInStatus(record.get(9), Boolean.TRUE));
        result.add(TimeUtils.getCheckInStatus(record.get(10), Boolean.FALSE));
    }

    public List<String> getResult() {
        return result;
    }

//    private String getCheckInStatus(String time, boolean isForCheckIn) {
//        if(StringUtils.isBlank(time)) {
//            return null;
//        }
//        try {
//            if(isForCheckIn) {
//                if(TimeUtils.isLate(time)) {
//                    return String.valueOf(CheckInStatus.OVERDUE);
//                } else {
//                    return String.valueOf(CheckInStatus.ONTIME);
//                }
//            } else {
//                if(TimeUtils.isEarly(time)) {
//                    return String.valueOf(CheckInStatus.OVERDUE);
//                } else {
//                    return String.valueOf(CheckInStatus.ONTIME);
//                }
//            }
//        } catch (ParseException e) {
//            //e.printStackTrace();
//            return null;
//        }
//    }

//    public static void main(String[] args) {
//        //false
//        String time1 = "08:30";
//        String time2 = "08:30:000";
//        String time3= "08:30:001";
//        String time4 = "08:30:000";
//        //true;
//        String time5 = "08:31:00";
//        String time6 = "08:35:0";
//        FPICheckInConsumer consumer = new FPICheckInConsumer();
//
//        System.out.println(consumer.getCheckInStatus(time1, true));
//        System.out.println(consumer.getCheckInStatus(time2, true));
//        System.out.println(consumer.getCheckInStatus(time3, true));
//        System.out.println(consumer.getCheckInStatus(time4, true));
//        System.out.println(consumer.getCheckInStatus(time5, true));
//        System.out.println(consumer.getCheckInStatus(time6, true));
//
//
//        System.out.println(consumer.getCheckInStatus("", true));
//        System.out.println(consumer.getCheckInStatus(" ", true));
//        System.out.println(consumer.getCheckInStatus("109430", true));
//    }
}
