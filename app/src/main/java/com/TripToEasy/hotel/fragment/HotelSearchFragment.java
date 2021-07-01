package com.TripToEasy.hotel.fragment;

import android.annotation.SuppressLint;
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
import com.TripToEasy.model.parsingModel.CurrencyConverter;
import com.TripToEasy.model.parsingModel.hotel.search.RoomGuest;
import com.TripToEasy.model.parsingModel.hotel.search.SearchMain;
import com.TripToEasy.model.parsingModel.hotel.travellerSelection.TravellerSelectionMain;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ptblr-1195 on 21/3/18.
 */

public class HotelSearchFragment extends BaseFragment implements
        DatePickerNotify, CitySearch, WebInterface {

    public HotelSearchFragment(){

    }
    String hotelCityName;
    int callingFragment=0;
    ArrayList<CurrencyConverter> currencyList=new ArrayList<CurrencyConverter>();
    @SuppressLint("ValidFragment")
    public HotelSearchFragment(String hotelCityID, String hotelCityName,int callingFragment){
        cityId=hotelCityID;
        this.hotelCityName=hotelCityName;
        this.callingFragment=callingFragment;
    }

    @BindView(R.id.week_name)
    TextView ciWeekName;

    @BindView(R.id.date_value)
    TextView ciDateValue;

    /*@BindView(R.id.month_value)
    TextView ciMonthValue;*/

    @BindView(R.id.to_week_name)
    TextView coWeekValue;

    @BindView(R.id.to_date_value)
    TextView coDateValue;

  /*  @BindView(R.id.to_month_value)
    TextView coMonthValue;*/

/*
    @BindView(R.id.search_city_name)
    TextView searchCityName;
*/

    @BindView(R.id.night_count)
    TextView nightCountText;

    @BindView(R.id.city_name)
    TextView cityNameText;

    @BindView(R.id.passenger_count)
    TextView passengerCountText;

    @BindView(R.id.passenger_detail_count)
    TextView passengerDetailCount;

    @BindView(R.id.room_count)
    TextView roomCountText;


    @OnClick(R.id.check_in)
    void checkIn(){
        commonUtils.callDatePicker(this,HotelSearchFragment.this,checkInDate,
                1);
    }



    @OnClick(R.id.check_out)
    void checkOut(){
        if(checkOutDate.equals(""))
        {
            checkOutDate = checkInDate;
        }
        commonUtils.callDatePicker(this,HotelSearchFragment.this,checkInDate,checkOutDate,
                2);
    }

    @OnClick(R.id.city_name)
    void cityName(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new CitySearchFragment(HotelSearchFragment.this, 5),
                null, true);
    }

    @OnClick(R.id.guest_layout)
    void guestLayoutClick(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new GuestRoomFragment(HotelSearchFragment.this, travellerSelectionMains), null, true);
    }

    @OnClick(R.id.room_layout)
    void roomLayoutClick(){
        guestLayoutClick();
    }

    @OnClick(R.id.search_hotel)
    void searchHotel(){
        if(cityId != null){
            applicationPreference.setData(
                    applicationPreference.hotelCityCode, cityId);
            applicationPreference.setData(
                    applicationPreference.hotelCityName,
                    cityNameText.getText().toString());

            List<RoomGuest> roomGuests = new ArrayList<RoomGuest>();
            if(travellerSelectionMains.size() != 0){
                int i =0;
                while (i < travellerSelectionMains.size()){
                    List<String> childAge = new ArrayList<String>();
                    switch (travellerSelectionMains.get(i).getChildValue()){
                        case 0:

                            break;
                        case 1:
                            childAge.add(travellerSelectionMains.get(i).getChildAgeOne());
                            break;
                        case 2:
                            childAge.add(travellerSelectionMains.get(i).getChildAgeOne());
                            childAge.add(travellerSelectionMains.get(i).getChildAgeTwo());
                            break;
                    }
                    RoomGuest roomGuest = new RoomGuest(
                            String.valueOf(travellerSelectionMains.get(i).getAdultValue()),
                            String.valueOf(travellerSelectionMains.get(i).getChildValue()),
                            childAge);
                    roomGuests.add(roomGuest);
                    i++;
                }
            }else {
                List<String> childAge = new ArrayList<String>();
                RoomGuest roomGuest = new RoomGuest(
                        adultCount, childCount, childAge);
                roomGuests.add(roomGuest);
            }

            SearchMain searchMain = new SearchMain(roomGuests, roomCount,
                    nightCount, cityId, checkInDate, checkOutDate);

            Gson gson = new Gson();
            String searchData = gson.toJson(searchMain);
            RequestParams requestParams = new RequestParams();
            requestParams.put("hotel_search", searchData);
            webServiceController.postRequest(apiConstants.HOTEL_SEARCH, requestParams, 1);
        }else {
            commonUtils.toastShort("Please select the city name.", getActivity());
        }
    }

    String checkInDate="", checkOutDate="", startDate, adultCount = "1",
            childCount = "0", roomCount = "1", nightCount = "1",
            cityId = null;
    WebServiceController webServiceController;
    List<TravellerSelectionMain> travellerSelectionMains =
            new ArrayList<TravellerSelectionMain>();

    @Override
    protected int getLayoutResource() {
        return R.layout.hotel_search_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        startDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)
                +"-"+calendar.get(Calendar.DAY_OF_MONTH);
        notifyDate(startDate, 1);

        webServiceController = new WebServiceController(getActivity(),HotelSearchFragment.this);
        if(callingFragment==5){
          //  searchCityName.setText(hotelCityName);
            cityNameText.setText(hotelCityName);
        }else {
            if (applicationPreference.getData(
                    applicationPreference.hotelCityCode).length() != 0) {
                cityId = applicationPreference.getData(applicationPreference.hotelCityCode);
                cityNameText.setText(applicationPreference.getData(
                        applicationPreference.hotelCityName));
             //   searchCityName.setText(applicationPreference.getData(
                     //   applicationPreference.hotelCityName));
            }
        }


        }

    @Override
    public void notifyDate(String dateValue, Integer datePickerValue)
    {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date endDate = dateFormat.parse(dateValue);
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMM-dd EEEE",Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");
            //String[] dateValueArray = splitDate[0].split("-");

            switch (datePickerValue){
                case 1:
                    ciWeekName.setText(splitDate[3]);
                    ciDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);

                    checkInDate = dateValue;

                    setCheckOutDate(checkInDate);

                    getDateDifference();
                    break;
                case 2:
                    coWeekValue.setText(splitDate[3]);
                    coDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);

                    checkOutDate = dateValue;
                    getDateDifference();
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setCheckOutDate(String dateValue)
    {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateFormat.parse(dateValue));
            calendar.add(Calendar.DATE, 1);
            dateValue = dateFormat.format(calendar.getTime());

            Date endDate = dateFormat.parse(dateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");


            coWeekValue.setText(splitDate[3]);
            coDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);

            checkOutDate = dateFormat.format(calendar.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void getDateDifference() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;
        try{
            date1 = format.parse(checkInDate);
            date2 = format.parse(checkOutDate);

            int diffInDays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));

            if(diffInDays >= 1){
                nightCountText.setText(String.valueOf(diffInDays)+ " Night's");
                nightCount = String.valueOf(diffInDays);
            }else {
                setCheckOutDate(checkInDate);
                switch (diffInDays){
                    case 0:
                        date1 = format.parse(checkInDate);
                        date2 = format.parse(checkOutDate);

                        diffInDays = (int) ((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24));
                        nightCountText.setText(String.valueOf(diffInDays)+ " Night's");
                        nightCount = String.valueOf(diffInDays);
                        commonUtils.toastShort(
                                "Check in and Check out date cannot be same.", getActivity());
                        break;
                    default:
                        commonUtils.toastShort(
                                "Check out date cannot be less than Check In date.", getActivity());
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void citySearchResult(Integer type, String cityName, String cityCode) {
       // searchCityName.setText(cityName);
        cityNameText.setText(cityName);
        cityId = cityCode;
    }

    @Override
    public void getResponse(String response, int flag)
    {

        switch (flag)
        {
                default:
                    Boolean isSuccess = false;
                    JSONObject jsonObject;
                    String message = null;
                    try{
                        jsonObject = new JSONObject(response);
                        if(jsonObject.optInt("status") == 1 || jsonObject.optBoolean("status")){
                            isSuccess = true;
                        }else {
                            message = jsonObject.getString("message");
                            isSuccess = false;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    if(isSuccess){
                        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                                new HotelListFragment(cityNameText.getText().toString(),
                                        checkInDate, checkOutDate, adultCount, roomCount, response,
                                        nightCount, childCount),
                                null, true);
                    }else {
                        commonUtils.toastShort(message, getActivity());
                    }
                    break;
        }

    }

    public void updateTravellerCount(Integer finalAdultCount, Integer finalChildCount, Integer finalRoomCount,
                                     List<TravellerSelectionMain> travellerSelectionMains) {
        adultCount = String.valueOf(finalAdultCount);
        childCount = String.valueOf(finalChildCount);
        roomCount = String.valueOf(finalRoomCount);

        passengerCountText.setText(String.valueOf(finalAdultCount+finalChildCount));
        passengerDetailCount.setText(adultCount+" AD "+childCount+" CH");
        roomCountText.setText(String.valueOf(finalRoomCount));

        this.travellerSelectionMains.clear();
        this.travellerSelectionMains = travellerSelectionMains;
    }

}