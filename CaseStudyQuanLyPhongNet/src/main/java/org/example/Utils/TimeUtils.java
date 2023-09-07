package org.example.Utils;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeUtils {
    private static DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm");
    public static LocalTime parseTime(String strDate) {
        try {
            return LocalTime.parse(strDate, dateTimeFormatter);
        } catch (DateTimeParseException dateTimeParseException) {
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
    public static String formatTime(LocalTime localTime) {
        try{
            return dateTimeFormatter.format(localTime);
        }catch (DateTimeParseException dateTimeParseException) {
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
}
