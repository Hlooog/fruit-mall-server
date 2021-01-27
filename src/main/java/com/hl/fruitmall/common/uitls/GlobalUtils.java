package com.hl.fruitmall.common.uitls;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Hl
 * @create 2020/12/28 17:19
 */
public class GlobalUtils {

    public static final int[] PUNISHMENT = {1, 3, 7, 30, 365, -1};

    public static final int MAX_VIOLATION = 5;

    public static Date changeTime(Date create, Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (day == -1) {
            cal.setTime(create);
        } else {
            Date now = new Date();
            if (now.before(date)) {
                cal.setTime(date);
            } else {
                cal.setTime(now);
            }
        }
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }
}
