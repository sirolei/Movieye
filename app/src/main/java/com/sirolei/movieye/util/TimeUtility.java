package com.sirolei.movieye.util;

import android.text.format.Time;

/**
 * Created by siro on 2016/4/20.
 */
public class TimeUtility {

    public static int getJulientDay(long timeMillis){
        Time dayTime = new Time();
//        dayTime.setToNow();
        // we start at the day returned by local time. Otherwise this is a mess.
        int julianStartDay = Time.getJulianDay(timeMillis, dayTime.gmtoff);
        return julianStartDay;
    }

}
