package com.TripToEasy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.facebook.FacebookSdk;
import com.TripToEasy.database.DBAdapter;
import com.TripToEasy.main.activity.BaseActivity;
import com.TripToEasy.main.activity.LoginActivity;
import com.TripToEasy.model.CountryInfo;
import com.TripToEasy.service.CityParsingService;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashScreen extends BaseActivity implements WebInterface{

    DBAdapter dbAdapter;
    WebServiceController webServiceController;
    ArrayList<CountryInfo> countryList;

    @Override
    protected int getLayoutResource() {
        return R.layout.splash_screen;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        countryList=new ArrayList<CountryInfo>();
        dbAdapter = new DBAdapter(this);
        dbAdapter.open();
        final Integer countValue = dbAdapter.flightCityCount();
        final Integer countrycountValue = dbAdapter.countryCount();
        dbAdapter.close();

        webServiceController=new WebServiceController(SplashScreen.this,SplashScreen.this);

        if(applicationPreference.getData(applicationPreference.depFlightCode).length() == 0){
            applicationPreference.setData(
                    applicationPreference.depFlightCode, "BLR");
            applicationPreference.setData(
                    applicationPreference.depAirportName,
                    "Bangalore");
            applicationPreference.setData(
                    applicationPreference.arriFlightCode, "MAA");
            applicationPreference.setData(
                    applicationPreference.arriAirportName,
                    "Chennai");
        }

        if(applicationPreference.getData(applicationPreference.hotelCityCode).length() == 0){
            applicationPreference.setData(
                    applicationPreference.hotelCityCode, "6743");
            applicationPreference.setData(
                    applicationPreference.hotelCityName,
                    "Bangalore India");
        }

        if(applicationPreference.getData(applicationPreference.busFromId).length() == 0){
            applicationPreference.setData(
                    applicationPreference.busFromCityName,
                    "Bangalore");
            applicationPreference.setData(
                    applicationPreference.busToCityName,
                    "Chennai");
            applicationPreference.setData(
                    applicationPreference.busFromId,
                    "4292");
            applicationPreference.setData(
                    applicationPreference.busToId,
                    "4562");
        }

        if(countValue == 0){
            Thread thread = new Thread(){
                @Override
                public void run() {
                    startService(new Intent(SplashScreen.this,CityParsingService.class));
                }
            };
            thread.start();
        }

        if(countrycountValue==0){
            RequestParams requestParams = new RequestParams();
            webServiceController.postRequest(apiConstants.URL_COUNTRY_LIST,requestParams,1);

        }else {
            requestPermissionAction();
        }

    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if(requestCode == 1){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(applicationPreference.getData(applicationPreference.login_flag).equals("true"))
                    {
                        intentAndFragmentService.intentDisplay(SplashScreen.this,LandingActivityNew.class,null);
                        finish();
                    }else {
                        intentAndFragmentService.intentDisplay(SplashScreen.this,LoginActivity.class,null);
                        finish();
                    }

                }
            },4000);
        }
    }

    @Override
    public void getResponse(String response, int flag) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("country_list");
            for (int i=0;i<jsonArray.length();i++){
                countryList.add(new CountryInfo(jsonArray.getJSONObject(i).getString("origin"),
                        jsonArray.getJSONObject(i).getString("name"),
                        jsonArray.getJSONObject(i).getString("country_code"),
                        jsonArray.getJSONObject(i).getString("country_code"),
                        jsonArray.getJSONObject(i).getString("iso_country_code")));

            }
            loadCountryList();
        } catch (JSONException e) {
            e.printStackTrace();
            requestPermissionAction();
        }
    }

    private void loadCountryList(){
        dbAdapter.open();
        dbAdapter.insertCountryList(countryList);
        dbAdapter.close();

        requestPermissionAction();
    }
}