package at.alex.falexhomes.utils;

import java.util.Date;

public class TimeUtils {
    public static long getCurrentTime() {
        Date date = new Date();
        return Long.valueOf(date.getTime()/1000);
    }
}
