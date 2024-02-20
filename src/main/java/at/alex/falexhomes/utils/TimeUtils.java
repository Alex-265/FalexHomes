package at.alex.falexhomes.utils;

import java.util.Date;

public class TimeUtils {
    public static int getCurrentTime() {
        // Ab 2038 gibt es ein problem
        Date date = new Date();
        Long longTime = new Long(date.getTime()/1000);
        return longTime.intValue();
    }
}
