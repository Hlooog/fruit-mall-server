package com.hl.fruitmall.common.uitls;

import com.hl.fruitmall.entity.bean.User;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Hl
 * @create 2020/12/28 17:19
 */
public class GlobalUtils {

    public static final int[] PUNISHMENT = {1, 3, 7, 30, 365, -1};

    public static final int MAX_VIOLATION = 5;

    public static void changeTime(User user, int day) {
        Calendar cal = Calendar.getInstance();
        if (day == -1) {
            cal.setTime(user.getCreateTime());
        } else {
            Integer violation = user.getViolation();
            if (violation == PUNISHMENT.length * MAX_VIOLATION) {
                cal.setTime(user.getCreateTime());
                day = PUNISHMENT[MAX_VIOLATION];
            } else {
                cal.setTime(new Date());
                day = PUNISHMENT[day] + PUNISHMENT[violation / MAX_VIOLATION];
            }
        }
        cal.add(Calendar.DATE, day);
        user.setBanTime(cal.getTime());
    }
}
