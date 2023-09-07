package org.example.Utils;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM-yyyy");
    private static DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
    public static Year parseYear(String strYear){
        try {
            return Year.parse(strYear, yearFormatter);
        }catch (DateTimeParseException dateTimeParseException){
            return null;
        }
    }
    public static YearMonth parseMonth(String strMonth){
        try {
            return YearMonth.parse(strMonth, monthFormatter);
        }catch (DateTimeParseException dateTimeParseException){
            return null;
        }
    }

    public static LocalDate parseDate(String strDate) {
        try {
            return LocalDate.parse(strDate, dateTimeFormatter);
        } catch (DateTimeParseException dateTimeParseException) {
            return null;
        }
    }

    public static String formatDate(LocalDate localDate) {
        try{
            return dateTimeFormatter.format(localDate);
        }catch (DateTimeParseException dateTimeParseException) {
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
    public static String formatYear(Year year) {
        try{
            return yearFormatter.format(year);
        }catch (DateTimeParseException dateTimeParseException) {
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
    public static String formatMonth(YearMonth month) {
        try{
            return monthFormatter.format(month);
        }catch (DateTimeParseException dateTimeParseException) {
            dateTimeParseException.printStackTrace();
        }
        return null;
    }
    public static LocalDate getValidDateFromMessage(String  strDate, String strMessage){
        LocalDate date = parseDate(strDate);
        if(date == null){
            System.out.println(strMessage);
        }
        return date;
    }
    public static YearMonth getValidMonthFromMessage(String strMonth, String strMessage){
        YearMonth month = parseMonth(strMonth);
        if(month == null){
            System.out.println(strMessage);
        }
        return month;
    }
    public static Year getValidYearFromMessage(String strYear, String strMessage){
        Year year = parseYear(strYear);
        if(year == null){
            System.out.println(strMessage);
        }
        return year;
    }
}
