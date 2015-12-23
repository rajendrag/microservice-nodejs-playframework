package models.util;

import org.joda.time.DateTime;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by RP on 12/15/15.
 */
public class IQueueUtils {
    public static String TIME_24_HOUR_FORMAT = "HH:mm:ss";

    public static String convertDatetoString(Date date, String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        String datestring = "";
        if(null!=date){
            datestring = dateFormat.format(date);
        }
        return datestring;
    }

    public static Time getNurseCoverageEndTime(Time startTime, Time shiftLength) {
        String [] timeArray = {formatTime(startTime, TIME_24_HOUR_FORMAT), formatTime(shiftLength, TIME_24_HOUR_FORMAT)};
        Time endTime = sumTimes(timeArray, new SimpleDateFormat(TIME_24_HOUR_FORMAT));
        return endTime;
    }

    public static String formatTime(Time time, String format){
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.format(time);
    }

    public static Time sumTimes(String[] timeStrings, DateFormat df) {
        int secs=0, mins=0, hrs=0;
        Calendar calendar = Calendar.getInstance();
        for (int i=0; i<timeStrings.length; i++) {
            String dateString = timeStrings[i];
            Date date = new Date();
            try {
                date = (Date) df.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.setTime(date);
            secs += calendar.get(Calendar.SECOND);
            mins += calendar.get(Calendar.MINUTE);
            hrs += calendar.get(Calendar.HOUR_OF_DAY);
        }
        calendar.set(0, 0, 0, hrs, mins, secs);
        Date d = calendar.getTime();
        return Time.valueOf(df.format(d));
    }

    /**
     * Method used in constructing the appointment scheduling py script input.
     * This method converts the given time in to the corresponding decimal equivalent of floating point representation.
     * Ex.  6:30 = 6.5
     *        6:45 = 6.75
     * @param inputTime
     * @return
     */
    public static String formatTimeToFloat(Time inputTime){
        DateTime jodaTime = new DateTime(inputTime);
        int secsTime = jodaTime.getSecondOfDay();
        long beforeRound = Math.round((secsTime/60.0d/60.0d)*10);
        float result = beforeRound/10;
        return String.valueOf(result);
    }
}
