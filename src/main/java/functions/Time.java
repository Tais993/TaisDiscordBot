package functions;

import java.util.Calendar;
import java.util.Date;

public class Time {
    Calendar calendar = Calendar.getInstance();
    Date date = new Date();

    public void prepareCalendar() {
        calendar.setTime(date);
    }

    public String getFullDateString() {
        return getDayString() + " " + getMonthString() + " " + getYear();
    }

    public String getFullDate() {
        return getDayNumber() + "-" + getMonthNumber() + "-" + getYear();
    }

    public int getDayNumber() {
        prepareCalendar();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getDayString() {
        prepareCalendar();
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1: return "Monday";
            case 2: return "Tuesday";
            case 3: return "Wednesday";
            case 4: return "Thursday";
            case 5: return "Friday";
            case 6: return "Saturday";
            case 7: return "Sunday";
        }
        return "ERROR: day of week is above 7";
    }

    public int getMonthNumber() {
        prepareCalendar();
        return calendar.get(Calendar.MONTH);
    }

    public String getMonthString() {
        prepareCalendar();
        switch (calendar.get(Calendar.MONTH)){
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
        }
        return "ERROR: day of week is above 7";
    }

    public int getYear() {
        prepareCalendar();
        return calendar.get(Calendar.YEAR);
    }
}
