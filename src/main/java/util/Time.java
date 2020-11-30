package util;

import java.time.OffsetDateTime;

public class Time {
    public static String getDateFromOffset(OffsetDateTime offsetDateTime) {
        return offsetDateTime.getDayOfMonth() + "-" + offsetDateTime.getMonthValue() + "-" + offsetDateTime.getYear() + " " + offsetDateTime.getHour() + ":" + offsetDateTime.getMinute();
    }

    public static String getTextDateFromOffset(OffsetDateTime offsetDateTime) {
        return getWeekDay(offsetDateTime.getDayOfWeek().name()) + ", " + offsetDateTime.getDayOfMonth() +  ". " + getMonthDay(offsetDateTime.getMonth().name()) + " " + offsetDateTime.getYear();
    }

    private static String getWeekDay(String weekDay) {
        return switch (weekDay) {
            case "MONDAY" -> "Monday";
            case "TUESDAY" -> "Tuesday";
            case "WEDNESDAY" -> "Wednesday";
            case "THURSDAY" -> "Thursday";
            case "FRIDAY" -> "Friday";
            case "SATURDAY" -> "Saturday";
            case "SUNDAY" -> "Sunday";

            default -> throw new IllegalStateException("Unexpected value: " + weekDay);
        };
    }

    private static String getMonthDay(String monthDay) {
        return switch (monthDay) {
            case "JANUARY" -> "January";
            case "FEBRUARY" -> "February";
            case "MARCH" -> "March";
            case "APRIL" -> "April";
            case "MAY" -> "May";
            case "JUNE" -> "June";
            case "JULY" -> "July";
            case "AUGUST" -> "August";
            case "SEPTEMBER" -> "September";
            case "OCTOBER" -> "October";
            case "NOVEMBER" -> "November";
            case "DECEMBER" -> "December";

            default -> throw new IllegalStateException("Unexpected value: " + monthDay);
        };
    }
}
