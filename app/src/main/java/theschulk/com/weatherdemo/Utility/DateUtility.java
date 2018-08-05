package theschulk.com.weatherdemo.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtility {
    public static String formatDate(String date){
        //convertDate to long
        Long unixTime = Long.parseLong(date);

        //Create date with milliseconds
        Date currentDate = new Date(unixTime * 1000L);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("EST"));

        String formatDate = simpleDateFormat.format(currentDate);
        return formatDate;
    }
}
