package util;

import java.time.OffsetDateTime;

public class Time {
    public static String getDateFromOffset(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getDayOfMonth() + "-" + offsetDateTime.getMonthValue() + "-" + offsetDateTime.getYear() + " " + offsetDateTime.getHour() + ":" + offsetDateTime.getMinute();
    }
}
