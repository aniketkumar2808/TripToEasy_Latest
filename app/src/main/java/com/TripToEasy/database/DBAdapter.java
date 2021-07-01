package com.TripToEasy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.TripToEasy.car.CarCityModel;
import com.TripToEasy.model.CountryInfo;
import com.TripToEasy.model.parsingModel.CityList;
import com.TripToEasy.model.parsingModel.TravellerModel;
import com.TripToEasy.model.parsingModel.holiday.HolidayCity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptblr-1174 on 24/1/17.
 */

public class DBAdapter {

    /**********
     * Logcat TAG
     ************/
    public static final String LOG_TAG = "DBAdapter";

    /*************
     * Database Name
     ************/
    public static final String DATABASE_NAME = "TripToEasy.db";

    /****
     * Database Version (Increase one if want to also upgrade your database)
     ****/
    public static final int DATABASE_VERSION = 1;// started at 1

    /**
     * table name
     * */
    public static final String HOTEL_CITY = "hotel_city_list";
    public static final String FLIGHT_CITY = "flight_city_list";
    public static final String BUS_CITY = "bus_city_list";
    public static final String TRAVELLER_LIST = "traveller_list";
    public static final String COUNTRY = "country_list";
    public static final String TRANSFER_CITY = "transfers_city_list";
    public static final String COUNTRY_LIST_MOBILE = "country_list_mobile";

    /**
     * creating table, to hold the search data when user search for flight and hotel
     *
     * NOTE: trip_to_date will have data if user selected the round trip
     * 
     * */
    public static final String HOTEL_CITY_TABLE = "create table hotel_city_list(_id integer primary key autoincrement," +
            "city_id String, city_name String );";

    public static final String FLIGHT_CITY_TABLE = "create table flight_city_list(_id integer primary key autoincrement," +
            "city_name_search String, airline_code String, city_pref Integer );";

    public static final String BUS_CITY_TABLE = "create table bus_city_list(_id integer primary key autoincrement," +
            "city_id String, city_name String );";
/*    public static final String TRAVELLER_TABLE = "create table traveller_list(_id integer primary key autoincrement," +
            "first_name String, last_name String, gender_data Integer, " +
            "type_action Integer );";*/
public static final String TRAVELLER_TABLE = "create table traveller_list(_id integer primary key autoincrement," +
        "first_name String, last_name String, gender_data Integer, " +
        "type_action Integer, dob String, passportNumber String, passportExpiryDate String, country String );";

    public static final String COUNTRY_TABLE = "create table country_list(_id integer primary key autoincrement," +
            "con_id String, con_name String,phonecode String,con_code String,iso String );";

    public static final String TRANSFER_CITY_TABLE = "create table transfers_city_list(_id integer primary key autoincrement," +
            "t_city_name String, t_city_id String );";
    public static final String COUNTRY_LIST_MOBILE_TABLE = "create table country_list_mobile(_id integer primary key autoincrement," +
            "city_name String, city_country String,city_code String);";

    List<CityList> flightCityLists = new ArrayList<CityList>();
    List<CityList> hotelCityLists = new ArrayList<CityList>();
    List<CityList> busCityLists = new ArrayList<CityList>();
    List<TravellerModel> travellerModels = new ArrayList<TravellerModel>();
    List<CountryInfo> countryLists = new ArrayList<CountryInfo>();
    List<HolidayCity> holidayCities = new ArrayList<HolidayCity>();
    List<CarCityModel> countryListMobile = new ArrayList<CarCityModel>();

    /*********
     * Used to open database in syncronized way
     *********/

    DataBaseHelper DBHelper;
    private SQLiteDatabase db;
    private final Context context;

    public DBAdapter(Context context) {
        this.context = context;
        DBHelper = new DataBaseHelper(context);
    }

    // ---opens the database---
    public void open(){
        try {
            db = DBHelper.getWritableDatabase();
            db = DBHelper.getReadableDatabase();
        }catch (Exception se){
            Log.e(LOG_TAG, se.getMessage());
        }
    }

    // ---closes the database---
    public void close() {
        DBHelper.close();
    }

    /**
     * insert hotel list to db
     * @param hotelCityLists
     */
    public void insertHotelCityList(List<CityList> hotelCityLists){

        try {
            db.beginTransaction();
            for(int i=0;i<hotelCityLists.size();i++){
                ContentValues contentValues=new ContentValues();
                contentValues.put("city_id", hotelCityLists.get(i).getCityId());
                contentValues.put("city_name", hotelCityLists.get(i).getSearchCityName());
                db.insert(HOTEL_CITY,null,contentValues);
            }

            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }

    /**
     * insert hotel list to db
     * @param busCityList
     */
    public void insertBusCityList(List<CityList> busCityList){

        try {
            db.beginTransaction();
            for(int i=0;i<busCityList.size();i++){
                ContentValues contentValues=new ContentValues();
                contentValues.put("city_id", busCityList.get(i).getCityId());
                contentValues.put("city_name", busCityList.get(i).getSearchCityName());
                db.insert(BUS_CITY,null,contentValues);
            }

            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }

    public void insertTransfersCityList(List<HolidayCity> holidayCities){

        try {
            db.beginTransaction();
            for(int i=0;i<holidayCities.size();i++){
                ContentValues contentValues=new ContentValues();
                contentValues.put("t_city_name", holidayCities.get(i).getCityName());
                contentValues.put("t_city_id", holidayCities.get(i).getCityId());
                db.insert(TRANSFER_CITY,null,contentValues);
            }

            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }

    }
    /**
     * insert flight list to db
     * @param flightCityLists
     */
    public void insertFlightCityList(List<CityList> flightCityLists){
        try {
            db.beginTransaction();
            for(int i=0;i<flightCityLists.size();i++){
                ContentValues contentValues=new ContentValues();
                contentValues.put("city_name_search", flightCityLists.get(i).getSearchCityName());
                contentValues.put("airline_code", flightCityLists.get(i).getCityId());
                contentValues.put("city_pref", flightCityLists.get(i).getCity_pref());
                db.insert(FLIGHT_CITY,null,contentValues);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    /**
     * insert traveller data
     * @param travellerModel
     * */
    public void insertTraveller(TravellerModel travellerModel){
        try {
            db.beginTransaction();
            ContentValues contentValues=new ContentValues();
            contentValues.put("first_name", travellerModel.getFirstName());
            contentValues.put("last_name", travellerModel.getLastName());
            contentValues.put("gender_data", travellerModel.getGenderType());
            contentValues.put("type_action", travellerModel.getActionType());
            contentValues.put("dob", travellerModel.getDateOfBirth());
            contentValues.put("passportNumber", travellerModel.getPassportNumber());
            contentValues.put("passportExpiryDate", travellerModel.getPassportExpiry());
            contentValues.put("country", travellerModel.getPassportCountry());
            db.insert(TRAVELLER_LIST,null,contentValues);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public Integer flightCityCount(){
        Integer countValue = 0;
        Cursor cursor = db.rawQuery("select count(airline_code) from "
                + FLIGHT_CITY, null);
        try {
            if(cursor.moveToFirst()){
                do {
                    countValue = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            cursor.close();
        }
        return countValue;
    }

    public Integer countryCount(){
        Integer countValue = 0;
        Cursor cursor = db.rawQuery("select count(con_id) from "
                + COUNTRY, null);
        try {
            if(cursor.moveToFirst()){
                do {
                    countValue = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            cursor.close();
        }
        return countValue;
    }

    public String hotelCityIdValue(String cityName){
        String countValue = "0";
        Cursor cursor = db.rawQuery("select city_id from "
                + HOTEL_CITY + " where city_label = " + "'"+cityName+"'", null);
        try {
            if(cursor.moveToFirst()){
                do {
                    countValue = cursor.getString(0);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.getStackTrace();
        }finally {
            cursor.close();
        }
        return countValue;
    }

    public List<CityList> getFlightCityData(String searchTerm){
        flightCityLists.clear();
        Cursor cursor = db.rawQuery("select * from " + FLIGHT_CITY + " where city_name_search like "
                + "'%"+searchTerm+"%'" +" or airline_code like "+ "'%"+searchTerm+"%'", null);
        try {
            if(cursor.moveToFirst()){
                do {
                    CityList flightList = new CityList();
                    flightList.setSearchCityName(cursor.getString(1));
                    flightList.setCityId(cursor.getString(2));
                    flightCityLists.add(flightList);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return flightCityLists;
    }

    public List<CityList> getFlightTopCity(Integer value1, Integer value2){
        flightCityLists.clear();
        Cursor cursor = db.rawQuery("select * from " + FLIGHT_CITY + " where city_pref = "
                + value1 +" or city_pref = "+ value2 +" order by city_pref desc", null);
        try {
            if(cursor.moveToFirst()){
                do {
                    CityList flightList = new CityList();
                    flightList.setSearchCityName(cursor.getString(1));
                    flightList.setCityId(cursor.getString(2));
                    flightCityLists.add(flightList);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return flightCityLists;
    }

    public List<CityList> getHotelCityData(String searchTerm){
         hotelCityLists.clear();
         Cursor cursor = db.rawQuery("select * from " + HOTEL_CITY + " where city_name like "
                + "'%"+searchTerm+"%'", null);
         try {
             if(cursor.moveToFirst()){
                 do {
                     CityList hotelCityData = new CityList();
                     hotelCityData.setCityId(cursor.getString(1));
                     hotelCityData.setSearchCityName(cursor.getString(2));
                     hotelCityLists.add(hotelCityData);
                 }while (cursor.moveToNext());
             }
         }catch (Exception e){
            e.printStackTrace();
         }finally {
            cursor.close();
         }
         return hotelCityLists;
    }

    public List<CityList> getBusCityData(String searchTerm){
        busCityLists.clear();
        Cursor cursor = db.rawQuery("select * from " + BUS_CITY + " where city_name like "
                + "'%"+searchTerm+"%'", null);
        try {
            if(cursor.moveToFirst()){
                do {
                    CityList busCityData = new CityList();
                    busCityData.setCityId(cursor.getString(1));
                    busCityData.setSearchCityName(cursor.getString(2));
                    busCityLists.add(busCityData);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return busCityLists;
    }

    public List<HolidayCity> getTransferCityData(String searchTerm){
        holidayCities.clear();
        Cursor cursor = db.rawQuery("select * from " + TRANSFER_CITY + " where t_city_name like "
                + "'%"+searchTerm+"%'", null);
        try {
            if(cursor.moveToFirst()){
                do {
                    HolidayCity tCityData = new HolidayCity();
                    tCityData.setCityName(cursor.getString(1));
                    tCityData.setCityId(cursor.getString(2));
                    holidayCities.add(tCityData);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return holidayCities;
    }

    public List<TravellerModel> getTravellerList(Integer actionType){
        travellerModels.clear();
        Cursor cursor;
        if(!db.isOpen())
        {
            open();
            cursor = db.rawQuery("select * from " + TRAVELLER_LIST + " where type_action = "
                    + actionType, null);
        }else
        {
            cursor = db.rawQuery("select * from " + TRAVELLER_LIST + " where type_action = "
                    + actionType, null);
        }
        try{
            if(cursor.moveToFirst()){
                do{
                    TravellerModel dataModel = new TravellerModel();
                    dataModel.setId(cursor.getInt(0));
                    dataModel.setFirstName(cursor.getString(1));
                    dataModel.setLastName(cursor.getString(2));
                    dataModel.setGenderType(cursor.getInt(3));
                    dataModel.setActionType(cursor.getInt(4));
                    dataModel.setIsSelected(1);
                    dataModel.setDateOfBirth(cursor.getString(5));
                    dataModel.setPassportNumber(cursor.getString(6));
                    dataModel.setPassportExpiry(cursor.getString(7));
                    dataModel.setPassportCountry(cursor.getString(8));
                    travellerModels.add(dataModel);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return travellerModels;
    }

    public TravellerModel getIndividualTraveller(Integer Id){
        open();
        TravellerModel travellerModel = new TravellerModel();
        Cursor cursor = db.rawQuery("select * from " + TRAVELLER_LIST + " where _id = "
                + Id, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    travellerModel.setId(cursor.getInt(0));
                    travellerModel.setFirstName(cursor.getString(1));
                    travellerModel.setLastName(cursor.getString(2));
                    travellerModel.setGenderType(cursor.getInt(3));
                    travellerModel.setActionType(cursor.getInt(4));
                    travellerModel.setIsSelected(1);
                    travellerModel.setDateOfBirth(cursor.getString(5));
                    travellerModel.setPassportNumber(cursor.getString(6));
                    travellerModel.setPassportExpiry(cursor.getString(7));
                    travellerModel.setPassportCountry(cursor.getString(8));
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        close();
        return travellerModel;
    }

    public void updateTraveller(TravellerModel travellerModel, Integer Id){
        ContentValues contentValues = new ContentValues();
        contentValues.put("first_name", travellerModel.getFirstName());
        contentValues.put("last_name", travellerModel.getLastName());
        contentValues.put("gender_data", travellerModel.getGenderType());
        contentValues.put("type_action", travellerModel.getActionType());
        contentValues.put("dob", travellerModel.getDateOfBirth());
        contentValues.put("passportNumber", travellerModel.getPassportNumber());
        contentValues.put("passportExpiryDate", travellerModel.getPassportExpiry());
        contentValues.put("country", travellerModel.getPassportCountry());
        db.update(TRAVELLER_LIST,contentValues,"_id"+"=?",new String[]{String.valueOf(Id)});
    }

    public int deleteTraveller(Integer Id){
        return db.delete(TRAVELLER_LIST, "_id=?",new String[]{String.valueOf(Id)});
    }
    public void insertCountryList(List<CountryInfo> countryInfoList){
        try {
            db.beginTransaction();
            for(int i=0;i<countryInfoList.size();i++){
                ContentValues contentValues=new ContentValues();
                contentValues.put("con_id", countryInfoList.get(i).getId());
                contentValues.put("con_name", countryInfoList.get(i).getName());
                contentValues.put("phonecode", countryInfoList.get(i).getPhonecode());
                contentValues.put("con_code", countryInfoList.get(i).getCountry_code());
                contentValues.put("iso", countryInfoList.get(i).getIso());
                db.insert(COUNTRY,null,contentValues);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }

    public List<CountryInfo> getCountryList(){
        open();
        countryLists.clear();
        Cursor cursor = db.rawQuery("select * from "+COUNTRY, null);
        try {
            if(cursor.moveToFirst()){
                do {
                    CountryInfo conInfo = new CountryInfo();
                    conInfo.setId(cursor.getString(1));
                    conInfo.setName(cursor.getString(2));
                    conInfo.setPhonecode(cursor.getString(3));
                    conInfo.setCountry_code(cursor.getString(4));
                    conInfo.setIso(cursor.getString(5));
                    countryLists.add(conInfo);
                }while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        close();
        return countryLists;
    }

    //to find country position in the list -- neeed to do
    public int countryPosition(String countryCode){
        Cursor cursor = db.rawQuery("select * from "+COUNTRY + " where phonecode = "+ countryCode, null);
        return 0;
    }

    //Added by Aniket on 12 Dec. 2020
    public List<CarCityModel> getCountryListMobile() {
        countryListMobile.clear();
        /*Cursor cursor = db.rawQuery("select * from " + FLIGHT_CITY + " where city_name_search like "
                + "'%"+searchTerm+"%'" +" or airline_code like "+ "'%"+searchTerm+"%'", null);*/
        Cursor cursor = db.rawQuery("select * from " + COUNTRY_LIST_MOBILE, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    CarCityModel flightList = new CarCityModel();
                    flightList.setCityName(cursor.getString(1));
                    flightList.setCountryName(cursor.getString(2));
                    flightList.setCityCode(cursor.getString(3));
                    countryListMobile.add(flightList);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return countryListMobile;
    }

    public void insertCountryMobileList(List<CarCityModel> carLists) {
        try {
            db.beginTransaction();
            for (int i = 0; i < carLists.size(); i++) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("city_name", carLists.get(i).getCityName());
                contentValues.put("city_country", carLists.get(i).getCountryName());
                contentValues.put("city_code", carLists.get(i).getCityCode());
                db.insert(COUNTRY_LIST_MOBILE, null, contentValues);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public Integer countryListCount() {
        Integer countValue = 0;
        Cursor cursor = db.rawQuery("select count(city_code) from "
                + COUNTRY_LIST_MOBILE, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    countValue = cursor.getInt(0);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            cursor.close();
        }
        return countValue;
    }

}