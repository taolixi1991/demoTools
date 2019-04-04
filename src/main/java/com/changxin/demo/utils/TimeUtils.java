package com.changxin.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    private static Date LATE_CHECK_IN_TIME;
    private static Date EARLY_CHECK_OUT_TIME;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    static {
        try {
            LATE_CHECK_IN_TIME = TIME_FORMAT.parse("08:30:999");
            EARLY_CHECK_OUT_TIME = TIME_FORMAT.parse("05:30:000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static boolean isLate(String time) throws ParseException {
        return TIME_FORMAT.parse(time).after(LATE_CHECK_IN_TIME);
    }

    public static boolean isEarly(String time) throws ParseException {
        return TIME_FORMAT.parse(time).before(EARLY_CHECK_OUT_TIME);
    }
}
