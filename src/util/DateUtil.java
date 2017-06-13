package util;

import dao.RecordsDAO;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by KundaLin on 17/6/6.
 */
public class DateUtil {
    static final long MILLISECOND_OF_ONE_DAY = 1000 * 60 * 60 * 24;


    public static Date today() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

    public static Date monthBegin() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        return c.getTime();
    }

    public static Date monthEnd() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        c.set(Calendar.DATE, 1);
        c.add(Calendar.MONDAY, 1);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public static int daysToMonthEnd() {
        long lastDayMilliSeconds = monthEnd().getTime();
        long toDayMilliSeconds = today().getTime();
        return (int)((lastDayMilliSeconds-toDayMilliSeconds)/MILLISECOND_OF_ONE_DAY)+1;
    }

    public static int daysFromMonthBegin() {
        long toDayMilliSeconds = today().getTime();
        long firstDayMilliSeconds = monthBegin().getTime();
        return (int) ((toDayMilliSeconds - firstDayMilliSeconds) / MILLISECOND_OF_ONE_DAY) + 1;
    }

    public static void main(String[] args) {
        System.out.println(today());
        System.out.println(monthBegin());
        System.out.println(monthEnd());
        System.out.println(daysFromMonthBegin());
        System.out.println(daysToMonthEnd());
    }

    public static java.sql.Date utilToSQL(Date d) {
        return new java.sql.Date(d.getTime());
    }

    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);

    }
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);

    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

}
