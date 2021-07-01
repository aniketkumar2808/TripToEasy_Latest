package com.TripToEasy.database;

/**
 * Created by Vikneswaran on 17-May-16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static com.TripToEasy.database.DBAdapter.BUS_CITY_TABLE;
import static com.TripToEasy.database.DBAdapter.COUNTRY_LIST_MOBILE_TABLE;
import static com.TripToEasy.database.DBAdapter.COUNTRY_TABLE;
import static com.TripToEasy.database.DBAdapter.DATABASE_NAME;
import static com.TripToEasy.database.DBAdapter.FLIGHT_CITY_TABLE;
import static com.TripToEasy.database.DBAdapter.HOTEL_CITY_TABLE;
import static com.TripToEasy.database.DBAdapter.TRANSFER_CITY_TABLE;
import static com.TripToEasy.database.DBAdapter.TRAVELLER_TABLE;



public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = "DataBaseHelper";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        exportDB();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(LOG_TAG, "new create 1");
        try {
               createDataBaseTable(db);
        } catch (Exception exception) {
            Log.e(LOG_TAG, "Exception onCreate() exception");
        }
    }

    @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e(LOG_TAG, "Upgrading database from version" + oldVersion
                + "to" + newVersion + "...");

        onCreate(db);
    }

    private void createDataBaseTable(SQLiteDatabase db){
        /**
         * add table name
         * */
        db.execSQL(HOTEL_CITY_TABLE);
        db.execSQL(FLIGHT_CITY_TABLE);
        db.execSQL(BUS_CITY_TABLE);
        db.execSQL(TRAVELLER_TABLE);
        db.execSQL(COUNTRY_TABLE);
        db.execSQL(TRANSFER_CITY_TABLE);
        db.execSQL(COUNTRY_LIST_MOBILE_TABLE);
    }

    private void exportDB() {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "/data/" + "com.TripToEasy" + "/databases/" + DATABASE_NAME;
        String backupDBPath = "travel.sqlite";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

