package com.TripToEasy.bus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.flight.fragment.CitySearchFragment;
import com.TripToEasy.interfaces.CitySearch;
import com.TripToEasy.interfaces.DatePickerNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.requestModel.bus.BusSearchModel;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class BusSearchFragment extends BaseFragment implements
        CitySearch, DatePickerNotify, WebInterface {

    @BindView(R.id.from_city_display)
    TextView fromCityDisplay;

    @BindView(R.id.to_city_display)
    TextView toCityDisplay;

    @BindView(R.id.from_city_name)
    TextView fromCityName;

    @BindView(R.id.to_city_name)
    TextView toCityName;

    @BindView(R.id.travel_date)
    TextView travelDateText;

    @OnClick(R.id.from_city_name)
    void fromStation(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new CitySearchFragment(BusSearchFragment.this, 6),
                null, true);
    }

    @OnClick(R.id.to_city_name)
    void toStation(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new CitySearchFragment(BusSearchFragment.this, 7),
                null, true);
    }

    @OnClick(R.id.travel_date)
    void travelDate(){
        commonUtils.callDatePicker(BusSearchFragment.this,
                BusSearchFragment.this,travelDate, 1);
    }

    @OnClick(R.id.iv_swap)
    void swapCity()
    {
        String temp="";
        String fromCity=fromCityName.getText().toString();
        String toCity=toCityName.getText().toString();

        temp=fromCityId;
        fromCityId=toCityId;
        toCityId=temp;
        temp=fromCity;
        fromCity=toCity;
        toCity=temp;
        fromCityName.setText(fromCity);
        toCityName.setText(toCity);
    }


    @OnClick(R.id.search_bus)
    void searchBus(){
        if(fromCityId == null){
            commonUtils.toastShort("Please select from location", getActivity());
        }else if(toCityId == null){
            commonUtils.toastShort("Please select to location", getActivity());
        }else if(travelDate == null){
            commonUtils.toastShort("Please select travel date", getActivity());
        }else {
            applicationPreference.setData(
                    applicationPreference.busFromCityName,
                    fromCityName.getText().toString());
            applicationPreference.setData(
                    applicationPreference.busToCityName,
                    toCityName.getText().toString());
            applicationPreference.setData(
                    applicationPreference.busFromId,
                    fromCityId);
            applicationPreference.setData(
                    applicationPreference.busToId,
                    toCityId);
            BusSearchModel busSearchModel = new BusSearchModel(
                    fromCityName.getText().toString(),
                    fromCityId,
                    toCityName.getText().toString(),
                    toCityId,
                    travelDate);

            Gson gson = new Gson();
            RequestParams requestParams = new RequestParams();
            requestParams.put("bus_search", gson.toJson(busSearchModel));
            webServiceController.postRequest(apiConstants.BUS_SEARCH, requestParams,1);
        }
    }

    public BusSearchFragment()
    {}


    String fromCityId = null, toCityId = null, travelDate = "";
    WebServiceController webServiceController;

    @Override
    protected int getLayoutResource() {
        return R.layout.bus_search_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                BusSearchFragment.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        String startDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)
                +"-"+calendar.get(Calendar.DAY_OF_MONTH);
        notifyDate(startDate, 1);


        if(applicationPreference.getData(applicationPreference.busFromId)
                .length() != 0){
            fromCityName.setText(applicationPreference.getData(
                    applicationPreference.busFromCityName));
            fromCityDisplay.setText(applicationPreference.getData(
                    applicationPreference.busFromCityName));
            toCityName.setText(applicationPreference.getData(
                    applicationPreference.busToCityName));
            toCityDisplay.setText(applicationPreference.getData(
                    applicationPreference.busToCityName));
            fromCityId = applicationPreference.getData(
                    applicationPreference.busFromId);
            toCityId = applicationPreference.getData(
                    applicationPreference.busToId);
        }
    }

    @Override
    public void citySearchResult(Integer type, String cityName, String cityCode) {
        switch (type){
            case 6:
                fromCityName.setText(cityName);
                fromCityDisplay.setText(cityName);
                fromCityId = cityCode;
                break;
            case 7:
                toCityName.setText(cityName);
                toCityDisplay.setText(cityName);
                toCityId = cityCode;
                break;
        }
    }

    @Override
    public void notifyDate(String dateValue, Integer datePickerValue) {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date endDate = dateFormat.parse(dateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");
            travelDateText.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        travelDate = dateValue;
    }

    @Override
    public void getResponse(String response, int flag) {
        switch (flag)
        {
            case 1:
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getInt("status")==1){
                        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                                new BusListingFragment(fromCityName.getText().toString(),fromCityId,
                                        toCityName.getText().toString(),toCityId, travelDate, response), null, true);
                    }else {
                        commonUtils.toastShort(jsonObject.getString("message"),getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }

    }
}