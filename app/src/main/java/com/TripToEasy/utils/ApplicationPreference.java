package com.TripToEasy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by manoj on 13-02-2018.
 */

public class ApplicationPreference {

    public final String userId = "user_id";
    public final String userName = "user_fname";
    public final String userLastName = "user_lname";
    public final String userEmail = "user_email";
    public final String userMobile = "user_mobile";
    public final String login_flag = "login_flag";
    public final String login_type = "login_type";
    public final String user_dp = "user_dp";
    public final String user_title = "user_title";
    public final String user_cc = "user_cc";
    public final String user_addr = "user_addr";

    public final String depFlightCode = "dep_f_code";
    public final String depAirportName = "dep_f_name";
    public final String arriFlightCode = "arri_f_code";
    public final String arriAirportName = "arri_f_name";

    public final String hotelCityName = "hotel_city_name";
    public final String hotelCityCode = "hotel_city_code";

    public final String busFromCityName = "bus_from_city_name";
    public final String busToCityName = "bus_to_city_name";
    public final String busFromId = "bus_from_id";
    public final String busToId = "bus_to_id";

    public final String holidayCountryName = "h_country_name";
    public final String holidayCountryId = "h_country_id";
    public final String holidayPackageName = "h_package_name";
    public final String holidayPackageId = "h_package_id";
    public final String holidayDuration = "h_duration";

    public final String transferCityName = "t_city_name";
    public final String transferCityId = "t_city_id";


    public final String activityCityName = "a_city_name";
    public final String activityCityId = "a_city_id";

    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ApplicationPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("mytripbazar", 0);
    }

    public void setData(String key, String value) {
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getData(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void clearAll() {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}