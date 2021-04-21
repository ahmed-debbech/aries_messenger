package com.ahmeddebbech.aries_messenger.util;

import androidx.annotation.NonNull;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AriesCalendar {
    private String year;
    private String month;
    private String day;
    private String hour;
    private String min;

    public AriesCalendar(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        long t = timestamp.getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(t);
        year = String.valueOf(cal.get(Calendar.YEAR));
        switch(cal.get(Calendar.MONTH)){
            case 0: month = "JAN"; break;
            case 1: month = "FEB"; break;
            case 2: month = "MAR"; break;
            case 3: month = "APR"; break;
            case 4: month = "MAY"; break;
            case 5: month = "JUN"; break;
            case 6: month = "JUL"; break;
            case 7: month = "AUG"; break;
            case 8: month = "SEP"; break;
            case 9: month = "OCT"; break;
            case 10: month = "NOV"; break;
            case 11: month = "DEC"; break;
        }
        day = String.valueOf(cal.get(Calendar.DATE));
        hour = String.valueOf(cal.get(Calendar.HOUR));
        min = String.valueOf(cal.get(Calendar.MINUTE));
    }

    @NonNull
    @Override
    public String toString() {
        return year + " " + month + " " + day + " • " + hour + ":" + min;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }
}
