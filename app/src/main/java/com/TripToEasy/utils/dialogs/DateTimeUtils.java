package com.TripToEasy.utils.dialogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaurav.0012 on 2/8/2017.
 */

public class DateTimeUtils {

    SimpleDateFormat defaultformatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat defaultformatterwithtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public String getCurrentDate() {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        return defaultformatter.format(date);
    }

    public Date getCurrentDateTime() {
        long timestamp = System.currentTimeMillis();
        Date date = new Date(timestamp);
        return date;
    }

    public String convertToBasicFormat(String input) {

        try {
            Date date = defaultformatter.parse(input);
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            return myFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return input;
    }


    public String convertToFormat(String input) {

        try {
            Date date = defaultformatter.parse(input);
            SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM");
            return myFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return input;
    }

    public int getNoOfDays(String date1, String date2) {

        int days = 0;
        try {
            Date d1 = defaultformatter.parse(date1);
            Date d2 = defaultformatter.parse(date2);

            days = (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

    public boolean compareDate(String startDate) {

        try {
            Date d1 = getCurrentDateTime();
            Date d2 = defaultformatterwithtime.parse(startDate);

            if (d1.before(d2)) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;

    }

    //Added by Aniket on 12 Dec. 2020
    public static String convertToddmmyyyyt(String input) {
        SimpleDateFormat defaultformatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = defaultformatter.parse(input);
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
            return myFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return input;
    }

}
