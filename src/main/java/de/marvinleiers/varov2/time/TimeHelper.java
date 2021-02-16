package de.marvinleiers.varov2.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper
{
    public static boolean canJoin()
    {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
        String time = simpleDateFormat.format(date);

        return !time.contains("23:3") && !time.contains("23:4") && !time.contains("23:5");
    }

    public static long calculate0Uhr()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-M-yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);

        Date tomorrow = calendar.getTime();
        String time = simpleDateFormat.format(tomorrow);

        String dateString = time +  " 00:00:00";

        try
        {
            Date date = sdf.parse(dateString);

            calendar = Calendar.getInstance();
            calendar.setTime(date);

            return calendar.getTimeInMillis();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        }
    }
}
