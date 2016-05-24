package com.euro.typer.data.source.criteria;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by miras108 on 2016-05-21.
 */
public class DateHelper {

    public static Date getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getTomorrowDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static Date getDateFromStringWithoutTime(String stringDate) throws ParseException {
        stringDate += ".2016";
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        Date date = null;
        return format.parse(stringDate);
    }

    public static Date getDateWithTime(String time) throws ParseException {
        String timeFormat = "dd.MM.yyyy_HH:mm";
        DateFormat format = new SimpleDateFormat(timeFormat, Locale.US);
        return format.parse(time);
    }
}
