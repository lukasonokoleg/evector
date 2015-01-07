package lt.jmsys.spark.gwt.application.client.helper;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.CalendarUtil;

@SuppressWarnings("deprecation")
public class ExtCalendarUtil {

    private static final int DAYS_IN_WEEK = 7;

    /**
     * system locale week start (from Monday) 
     * @param date
     * @return
     */
    public static Date getWeekStart(Date date) {
        Date weekStart = CalendarUtil.copyDate(date);
        int day = getDayOfWeek(weekStart);
        if (day != 1) {
            CalendarUtil.addDaysToDate(weekStart, -day + 1);
        }
        return weekStart;

    }

    public static Date truncateDate(Date date) {
        return date;
    }

    public static Date getNextWeekStart(Date date) {
        Date nextWeekStart = getWeekStart(date);
        CalendarUtil.addDaysToDate(nextWeekStart, DAYS_IN_WEEK);
        return nextWeekStart;
    }

    public static int getWeekNumber(Date date) {
        Date firstWeekMonday = getFirstWeekOfYear(date);
        Date thisMonday = getWeekStart(date);
        if (thisMonday.getYear() < firstWeekMonday.getYear()) {
            firstWeekMonday = getFirstWeekOfYear(thisMonday);
        } else {
            Date thisWednesday = CalendarUtil.copyDate(thisMonday);
            CalendarUtil.addDaysToDate(thisWednesday, 2);
            if (thisWednesday.getYear() > thisMonday.getYear()) {
                firstWeekMonday = getFirstWeekOfYear(thisWednesday);
            }
        }
        int daysBetween = CalendarUtil.getDaysBetween(firstWeekMonday, thisMonday);
        int weekNo = daysBetween / DAYS_IN_WEEK + 1;
        return weekNo;
    }

    /**
     * system locale first weak the year (minimal days in week = 4 / from Thursday) 
     * 
     * @param date
     * @return
     */
    public static Date getFirstWeekOfYear(Date date) {
        Date jan1 = getFirstDayOfYear(date);
        int dayOfWeek = getDayOfWeek(jan1);
        Date firstWeek = dayOfWeek <= 4 ? getWeekStart(jan1) : getNextWeekStart(jan1);
        return firstWeek;
    }

    /**
     * System locale day of week 
     * (1 = Monday, 2 = Tuesday, 3 = Wednesday, 4 = Thursday, 5 = Friday, 6 = Saturday, 7 = Sunday)
     * 
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        if (date != null) {
            return date.getDay() == 0 ? 7 : date.getDay();
        } else {
            return 1;
        }
    }

    public static Date getFirstDayOfYear(Date date) {
        Date dayOne = CalendarUtil.copyDate(date);
        String dateFormt = DateTimeFormat.getFormat("yyyy").format(dayOne) + "-01-01";
        dayOne = DateTimeFormat.getFormat("yyyy-MM-dd").parse(dateFormt);
        return dayOne;
    }

    public static final int getWeeksBetween(Date start, Date finish) {
        return (CalendarUtil.getDaysBetween(getWeekStart(start), getWeekStart(finish)) / DAYS_IN_WEEK);
    }

    public static int compareDateOnly(Date date0, Date date1) {
        assert date0 != null : "date0 cannot be null";
        assert date1 != null : "date1 cannot be null";
        if (date0.getYear() > date1.getYear()) {
            return 1;
        } else if (date0.getYear() < date1.getYear()) {
            return -1;
        } else if (date0.getMonth() > date1.getMonth()) {
            return 1;
        } else if (date0.getMonth() < date1.getMonth()) {
            return -1;
        } else if (date0.getDate() > date1.getDate()) {
            return 1;
        } else if (date0.getDate() < date1.getDate()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static Date addDaysToDate(Date monday, int days) {
        Date d = new Date(monday.getTime());
        CalendarUtil.addDaysToDate(d, days);
        return d;
    }

    public static Date[] getFirstAndLastDate(Date monday, int day) {
        Date[] dates = new Date[2];
        Date firstDate = CalendarUtil.copyDate(monday);
        if (day != 1) {
            CalendarUtil.addDaysToDate(firstDate, day - 1);
        }
        Date lastDate = CalendarUtil.copyDate(firstDate);
        CalendarUtil.addDaysToDate(lastDate, 5);

        dates[0] = firstDate;
        dates[1] = lastDate;
        return dates;
    }

    public static Date earliestDate(Date param1, Date param2) {
        if (param1 != null && param2 != null) {
            return param1.after(param2) ? param1 : param2;
        } else if (param1 == null && param2 != null) {
            return param2;
        } else if (param1 != null && param2 == null) {
            return param1;
        }
        return null;
    }

    public static Date latestDate(Date param1, Date param2) {
        if (param1 != null && param2 != null) {
            return param1.after(param2) ? param2 : param1;
        } else if (param1 == null && param2 != null) {
            return param2;
        } else if (param1 != null && param2 == null) {
            return param1;
        }
        return null;
    }

}
