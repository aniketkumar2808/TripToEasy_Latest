package com.TripToEasy.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.TripToEasy.model.CountryInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ptblr-1174 on 10/5/18.
 */

public class Global {
    public static int room_select_flag=0;
    public static String is_domestic_flag="0";
    public static String update_fare_token="";
    public static String update_fare_token_key="";
    public static String search_access_key="";
    public static String booking_type="";
    public static String booking_source="";
    public static String search_id="";
    public static  int dateset_flag=0;
    public static ArrayList<String> arrCountry=new ArrayList<String>();
    public static ArrayList<CountryInfo> countryList=new ArrayList<CountryInfo>();
    public static String currencySymbol="USD";
    public static String currencyValue="1";
    public static String currencyIcon="$";


    public static String changeDateFormat(String departureDate){//2018-05-31 05:00:00
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return departureDate;
    }

    public static String changeTimeFormat(String departureDate){//2018-05-31 05:00:00
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return departureDate;
    }

    public static  void hideKeyboard(Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            Window window = ((Activity) mContext).getWindow();
            if (window != null && window.getCurrentFocus() != null && window.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(window.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
