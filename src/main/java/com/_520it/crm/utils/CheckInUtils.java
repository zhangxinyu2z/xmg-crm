package com._520it.crm.utils;

import java.util.Calendar;
import java.util.Date;

public class CheckInUtils {

    /**
     * 判断正常的签到时间
     * 正常的签到时间是8.30
     *
     * @param date
     * @return -1：当前签到时间小于标准时间
     */
    public static int checkSignInTime(Date date) {
        // 设置当前的正常签到时间
        Calendar normalSignInTime = Calendar.getInstance();
        normalSignInTime.setTime(date);
        normalSignInTime.set(Calendar.HOUR_OF_DAY, 8);
        normalSignInTime.set(Calendar.MINUTE, 30);
        normalSignInTime.set(Calendar.SECOND, 0);
        int result = date.compareTo(normalSignInTime.getTime());
        return result;
    }

    /**
     * 判断正常的签退时间
     * 正常的签退时间是 17.30
     *
     * @param date
     * @return -1 当前时间小于签退时间
     */
    public static int checkSignOutTime(Date date) {
        // 设置当前的正常签退时间

        Calendar normalSignOutTime = Calendar.getInstance();
        normalSignOutTime.setTime(date);
        normalSignOutTime.set(Calendar.HOUR_OF_DAY, 17);
        normalSignOutTime.set(Calendar.MINUTE, 30);
        normalSignOutTime.set(Calendar.SECOND, 0);
        return date.compareTo(normalSignOutTime.getTime());

    }
}
