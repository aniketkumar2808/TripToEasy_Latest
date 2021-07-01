package com.TripToEasy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.TripToEasy.database.DBAdapter;
import com.TripToEasy.model.parsingModel.CityList;
import com.TripToEasy.model.parsingModel.holiday.HolidayCity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptblr-1195 on 23/2/18.
 */

public class CityParsingService extends Service {

    DBAdapter dbAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        lodeCityListData(1);
    }

    private void lodeCityListData(int i) {
        dbAdapter = new DBAdapter(this);

        List<CityList> flightCityLists = new ArrayList<CityList>();
        List<CityList> hotelCityLists = new ArrayList<CityList>();
        List<CityList> busCityLists = new ArrayList<CityList>();
        List<HolidayCity> holidayCities = new ArrayList<HolidayCity>();

        BufferedReader br = null;

        try {
            InputStreamReader is;
            String sCurrentLine;

            switch (i){
                case 1:
                    is = new InputStreamReader(getAssets()
                            .open("flight_airport_list.csv"));
                    br = new BufferedReader(is);
                    while ((sCurrentLine = br.readLine()) != null) {
                        String[] lineData = sCurrentLine.split(",");
                        flightCityLists.add(new CityList(
                                lineData[3].replace("\"",""),
                                lineData[1].replace("\"",""),
                                Integer.parseInt(lineData[lineData.length-1].replace("\"",""))));
                    }

                    dbAdapter.open();
                    dbAdapter.insertFlightCityList(flightCityLists);
                    dbAdapter.close();

                    lodeCityListData(2);

                    break;

                case 2:

                    is = new InputStreamReader(getAssets()
                            .open("all_api_city_master.csv"));
                    br = new BufferedReader(is);
                    while ((sCurrentLine = br.readLine()) != null) {
                        String[] lineData = sCurrentLine.split(",");
                        hotelCityLists.add(new CityList(lineData[1].replace("\"","")+" "
                                +lineData[2].replace("\"",""),
                                lineData[0].replace("\"","")));
                    }

                    dbAdapter.open();
                    dbAdapter.insertHotelCityList(hotelCityLists);
                    dbAdapter.close();

                    lodeCityListData(3);
                    break;
                case 3:
                    is = new InputStreamReader(getAssets()
                            .open("bus_stations.csv"));
                    br = new BufferedReader(is);
                    while ((sCurrentLine = br.readLine()) != null) {
                        String[] lineData = sCurrentLine.split(",");
                        busCityLists.add(new CityList(lineData[2].replace("\"",""),
                                lineData[1].replace("\"","")));
                    }
                    dbAdapter.open();
                    dbAdapter.insertBusCityList(busCityLists);
                    dbAdapter.close();

                    lodeCityListData(4);
                    break;
                case 4:
                    is = new InputStreamReader(getAssets()
                            .open("api_sightseeing_destination_list.csv"));
                    br = new BufferedReader(is);
                    while ((sCurrentLine = br.readLine()) != null) {
                        String[] lineData = sCurrentLine.split(",");
                        holidayCities.add(new HolidayCity(lineData[1].replace("\"",""),
                                lineData[2].replace("\"","")));
                    }

                    dbAdapter.open();
                    dbAdapter.insertTransfersCityList(holidayCities);
                    dbAdapter.close();

                    stopSelf();
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
