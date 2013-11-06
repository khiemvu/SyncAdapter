package com.qsoft.onlinedio.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: khiemvx
 * Date: 11/5/13
 */
public class DateTime
{
    private static Date currentDate = Calendar.getInstance().getTime();
    public long getTimeBefore(String updated_time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        try {
            Date updated_date = dateFormat.parse(updated_time);
            long time = (currentDate.getTime() - updated_date.getTime())
                    / (1000 * 60 * 60 * 24);
            if (time > 0)
                return time;
            else
                return time *= -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
