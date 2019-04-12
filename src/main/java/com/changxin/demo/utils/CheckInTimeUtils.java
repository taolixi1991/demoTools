package com.changxin.demo.utils;

import com.changxin.demo.common.CheckInStatus;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckInTimeUtils {

    private static Date LATE_CHECK_IN_TIME;
    private static Date EARLY_CHECK_OUT_TIME;
    private static Date MID_OF_WORK_TIME;
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    static {
        try {
            LATE_CHECK_IN_TIME = TIME_FORMAT.parse("08:30:999");
            EARLY_CHECK_OUT_TIME = TIME_FORMAT.parse("05:30:000");
            MID_OF_WORK_TIME = TIME_FORMAT.parse("13:00:000");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static boolean isCheckIn(String time) throws ParseException {
        return TIME_FORMAT.parse(time).before(MID_OF_WORK_TIME);
    }

    public static boolean isLate(String time) throws ParseException {
        return TIME_FORMAT.parse(time).after(LATE_CHECK_IN_TIME);
    }

    public static boolean isEarly(String time) throws ParseException {
        return TIME_FORMAT.parse(time).before(EARLY_CHECK_OUT_TIME);
    }

    public static int compare(String date1, String date2) throws ParseException {
        return DATE_FORMAT.parse(date1).compareTo(DATE_FORMAT.parse(date2));
    }

    public static String getCheckInStatus(String time, boolean isForCheckIn) {
        if(StringUtils.isBlank(time)) {
            return null;
        }
        try {
            if(isForCheckIn) {
                if(CheckInTimeUtils.isLate(time)) {
                    return String.valueOf(CheckInStatus.OVERDUE);
                } else {
                    return String.valueOf(CheckInStatus.ONTIME);
                }
            } else {
                if(CheckInTimeUtils.isEarly(time)) {
                    return String.valueOf(CheckInStatus.OVERDUE);
                } else {
                    return String.valueOf(CheckInStatus.ONTIME);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
