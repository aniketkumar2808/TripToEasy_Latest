package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import com.TripToEasy.model.parsingModel.bus.BusOperatorsInfo;
import com.TripToEasy.model.requestModel.FairRule;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.ProgressLoader;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.interfaces.ProfileFilterNotify;
import com.TripToEasy.main.activity.MainActivity;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.flight.activity.FlightSupportActivity;
import com.TripToEasy.flight.adapter.roundTrip.RoundTripMainAdapter;
import com.TripToEasy.interfaces.FilterNotify;
import com.TripToEasy.model.parsingModel.flight.FlightList;
import com.TripToEasy.model.parsingModel.flight.OneWayList;
import com.TripToEasy.model.parsingModel.flight.TwoWayList;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.OnClick;

public class TwoWayFragment extends BaseFragment
        implements FilterNotify, WebInterface, ProfileFilterNotify
{

    @BindView(R.id.trip_list)
    RecyclerView twoWayList;

    @BindView(R.id.from_station)
    TextView fromStation;

    @BindView(R.id.to_destination)
    TextView toStation;

    @BindView(R.id.traveller_count)
    TextView travellerCount;

    @BindView(R.id.price_value_dep)
    TextView departurePrice;

    @BindView(R.id.price_value_return)
    TextView returnPrice;

    @BindView(R.id.total_price)
    TextView totalPrice;

    @BindView(R.id.travel_date)
    TextView travelDate;

    @BindView(R.id.return_date)
    TextView travelReturnDate;


    @OnClick(R.id.filter_action)
    void filterAction(){
        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                new FilterFragment(airlineList, priceBreakup, this,
                        flightLists, minPrice, maxPrice, stopList, departureList,
                        arrivalList,false),null, true);
    }

    @OnClick(R.id.book_ticket)
    void navigateToDetail(){
        if(departurePrice.getText().toString().equalsIgnoreCase(Global.currencySymbol+" 0")){
            commonUtils.toastShort("Please add departure flight",getActivity());
            return;
        }
        if(returnPrice.getText().toString().equalsIgnoreCase(Global.currencySymbol+" 0")){
            commonUtils.toastShort("Please add return flight",getActivity());
            return;
        }
        String tax = null, adultPrice = null, childPrice = null,
                infantPrice = null, totalPrice = null,diff_amount=null,
                airlineUrl = null, dataArray = null, dataArrayTwo = null;
        int adultCount = 0, childCount = 0, infantCount = 0, position = 0;
        try{
            JSONObject jsonObject = new JSONObject(response);

            airlineUrl = jsonObject.getString("airline_img_url");

            JSONArray flightArray = jsonObject.getJSONArray("Flights");
            int m=0;
            while (m < flightArray.length()){

                JSONArray flightNextArray = flightArray.getJSONArray(m);

                int i=0;
                while (i < flightNextArray.length()) {
                    JSONObject flightObject = flightNextArray.getJSONObject(i);
                    if(m == 0){
                        if(flightObject.getString("Token").equals(departureToken)){
                            position = i;
                            break;
                        }
                    }else {
                        if(flightObject.getString("Token").equals(returnToken)){
                            position = i;
                            break;
                        }
                    }
                    i++;
                }

                JSONObject flightObject = flightNextArray.getJSONObject(position);
                JSONObject fairOne = flightObject.getJSONObject("FareDetails");
                JSONObject fairTwo = fairOne.getJSONObject("b2c_PriceDetails");

                double price;

                if(tax != null){
                    price = (double) Math.round(Double.parseDouble(tax) + fairTwo.getDouble("TotalTax"));
                    tax = String.valueOf(price);
                }else {
                    tax = String.valueOf(fairTwo.getDouble("TotalTax"));
                }

                if(totalPrice != null){
                    price = (double) Math.round(Double.parseDouble(totalPrice) + fairTwo.getDouble("TotalFare"));
                    totalPrice = String.valueOf(price);
                }else {
                    totalPrice = String.valueOf(fairTwo.getDouble("TotalFare"));
                }

                JSONObject passengerFair = flightObject.getJSONObject("PassengerFareBreakdown");
                if(passengerFair.has("ADT")){
                    JSONObject adultObj = passengerFair.getJSONObject("ADT");
                    if(adultPrice != null){
                        price = (double) Math.round(Double.parseDouble(adultPrice) + adultObj.getDouble("BaseFare"));
                        adultPrice = String.valueOf(price);
                    }else {
                        adultPrice = String.valueOf(adultObj.getDouble("BaseFare"));
                    }
                    adultCount = adultObj.getInt("PassengerCount");
                }

                if(passengerFair.has("CHD")){
                    JSONObject childObj = passengerFair.getJSONObject("CHD");
                    if(childPrice != null){
                        price = (double) Math.round(Double.parseDouble(childPrice) + childObj.getDouble("BaseFare"));
                        childPrice = String.valueOf(price);
                    }else {
                        childPrice = String.valueOf(childObj.getDouble("BaseFare"));
                    }
                    childCount = childObj.getInt("PassengerCount");
                }

                if(passengerFair.has("INF")){
                    JSONObject infantObj = passengerFair.getJSONObject("INF");
                    if(infantPrice != null){
                        price = (double) Math.round(Double.parseDouble(infantPrice) + infantObj.getDouble("BaseFare"));
                        infantPrice = String.valueOf(price);
                    }else {
                        infantPrice = String.valueOf(infantObj.getDouble("BaseFare"));
                    }
                    infantCount = infantObj.getInt("PassengerCount");
                }

                m++;
            }

            JSONArray dataArrayMain = new JSONArray(detailResponse);

            JSONObject detailObj = dataArrayMain.getJSONObject(0);
            JSONArray dataArrayOne = detailObj.getJSONArray(departureToken);
            dataArray = dataArrayOne.toString();

            JSONObject detailObj2 = dataArrayMain.getJSONObject(1);
            JSONArray dataArrayOne2 = detailObj2.getJSONArray(returnToken);
            dataArrayTwo = dataArrayOne2.toString();

            if(adultPrice!=null)
            {
                diff_amount = String.valueOf(Float.parseFloat(adultPrice));
            }
            if(childPrice!=null)
            {
                diff_amount = String.valueOf(Float.parseFloat(adultPrice)+Float.parseFloat(childPrice));
            }
            if(infantPrice!=null)
            {
                diff_amount = String.valueOf(Float.parseFloat(adultPrice)+Float.parseFloat(childPrice)+Float.parseFloat(infantPrice));
            }

            diff_amount = String.valueOf(Math.round(Float.parseFloat(totalPrice)-(Float.parseFloat(diff_amount)+Float.parseFloat(tax))));

            tax = String.valueOf(Float.parseFloat(tax)+Float.parseFloat(diff_amount));

        }catch (Exception e){
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();
        bundle.putInt("from_view",1);
        bundle.putString("source",fromStation.getText().toString());
        bundle.putString("destination", toStation.getText().toString());
        bundle.putString("depart_date", travelDate.getText().toString());
        bundle.putString("return_date", travelReturnDate.getText().toString());
        bundle.putString("token",departureToken);
        bundle.putString("tokenKey",departureTokenKey);
        bundle.putString("auth_key", departureKey);
        bundle.putString("booking_source", departureBookingSource);
        bundle.putString("return_token",returnToken);
        bundle.putString("return_tokenKey",returnTokenKey);
        bundle.putString("return_auth_key", returnKey);
        bundle.putString("return_booking_source", returnBookingSource);
        bundle.putString("adult_price",adultPrice);
        bundle.putString("child_price", childPrice);
        bundle.putString("infant_price", infantPrice);
        bundle.putInt("adult_count", adultCount);
        bundle.putInt("child_count", childCount);
        bundle.putInt("infant_count", infantCount);
        bundle.putString("tax", tax);
        bundle.putString("detail_response",dataArray);
        bundle.putString("detail_response_two",dataArrayTwo);
        bundle.putString("total_price",totalPrice);
        bundle.putString("diff_amount",diff_amount);
        bundle.putString("airline_url",airlineUrl);
        intentAndFragmentService.intentDisplay(getActivity(),
                FlightSupportActivity.class, bundle);

    }

    @SuppressLint("ValidFragment")
    public TwoWayFragment(String response, String catchFileName,
                          String departureDate,String returnDate, String cabinClass) {
        this.response = response;
        this.catchFileName = catchFileName;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.cabinClass= cabinClass;
    }

    public TwoWayFragment(){
        /*
        * Empty Constructor
        * */
    }

    String response, airlineList, priceBreakup,minPrice = null,
            maxPrice = null, catchFileName = null,
            departureKey = null, returnKey = null,
            departureBookingSource = null,
            returnBookingSource = null,
            departureToken = null,departureTokenKey = null,
            returnToken = null, returnTokenKey=null,detailResponse,
            departureDate,returnDate,cabinClass;
    boolean low_price,high_price,earliest,latest,arr_earliest,arr_latest,fastest,
            slow, refundable, nonRefundable;
    List<TwoWayList> twoWayLists = new ArrayList<TwoWayList>();
    List<TwoWayList> backupList = new ArrayList<TwoWayList>();
    List<TwoWayList> tempTwoWayLists = new ArrayList<TwoWayList>();

    List<FlightList> flightLists = new ArrayList<FlightList>();
    List<Integer> stopList = new ArrayList<Integer>();
    List<Integer> departureList = new ArrayList<Integer>();
    List<Integer> arrivalList = new ArrayList<Integer>();
    RoundTripMainAdapter roundTripMainAdapter;
    ImageView action_type;

    @Override
    protected int getLayoutResource() {
        return R.layout.round_trip_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        action_type = getActivity().findViewById(R.id.action_type);
        action_type.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        commonUtils.horizontalLayout(twoWayList,getActivity());
        webServiceController = new WebServiceController(getActivity(), TwoWayFragment.this);
        progressLoader = new ProgressLoader();

        roundTripMainAdapter = new RoundTripMainAdapter(getActivity(),
                TwoWayFragment.this,twoWayLists);
        twoWayList.setAdapter(roundTripMainAdapter);
        departurePrice.setText(Global.currencySymbol+" 0");
        returnPrice.setText(Global.currencySymbol+" 0");
        totalPrice.setText(Global.currencySymbol+" 0");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            Date endDate = dateFormat.parse(returnDate);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd",Locale.ENGLISH);
            returnDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }

        travelDate.setText(departureDate);
        travelReturnDate.setText(returnDate);

        Thread thread = new Thread(){
            @Override
            public void run() {
                parseData(response, false);
            }
        };
        thread.start();

        lodeDetailData();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).toolbarActionIcon(TwoWayFragment.this, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).toolbarActionIcon(TwoWayFragment.this, 0);
    }

    private void lodeDetailData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("search_key", catchFileName);
        WebServiceController webServiceController = new
                WebServiceController(getActivity(),TwoWayFragment.this);
        webServiceController.postRequest(apiConstants.FLIGHT_DETAIL, requestParams, 9999);
    }

    private void parseData(String response, final Boolean isFilter) {
        try{

            twoWayLists.clear();
            backupList.clear();

            JSONObject jsonObject = new JSONObject(response);

            airlineList = jsonObject.getJSONArray("airline_list").toString();
            priceBreakup = jsonObject.getJSONObject("price").toString();

            JSONObject journeySummary = jsonObject.getJSONObject("JourneySummary");
            final String from = journeySummary.getString("Origin");
            final String to = journeySummary.getString("Destination");

            JSONObject traveller = journeySummary.getJSONObject("PassengerConfig");
            final String passengerCount = String.valueOf(traveller.getInt("TotalPassenger"))+" Travellers";

            JSONArray flightArray = jsonObject.getJSONArray("Flights");

            /**
             * if its domestic then get data from two diff array
             *
             * else get the data from zero index
             * */
            if(jsonObject.getBoolean("IsDomestic"))
            {
                int j=0;
                while (j < flightArray.length()){
                    JSONArray flightNextArray = flightArray.getJSONArray(j);
                    List<OneWayList> oneWayLists = new ArrayList<OneWayList>();
                    int i=0;
                    while (i < flightNextArray.length()){
                        JSONObject flightObject = flightNextArray.getJSONObject(i);
                        JSONObject fairOne = flightObject.getJSONObject("FareDetails");
                        JSONObject fairTwo = fairOne.getJSONObject("b2c_PriceDetails");
                       /* String priceValue = fairTwo.getString("CurrencySymbol")+" "
                                +String.valueOf(fairTwo.getDouble("TotalFare"));*/
                        String priceValue =  Global.currencySymbol+" "
                                +String.valueOf((fairTwo.getDouble("TotalFare")/Double.parseDouble(Global.currencyValue)));


                        JSONObject attrObj = flightObject.getJSONObject("Attr");
                        String refundable;
                        if(attrObj.getBoolean("IsRefundable")){
                            refundable = "Refundable";
                        }else {
                            refundable = "Not Refundable";
                        }

                        JSONArray segmentArray = flightObject.getJSONArray("SegmentSummary");
                        JSONObject segmentObject = segmentArray.getJSONObject(0);

                        //Date currentTime = Calendar.getInstance().getTime();
                        //Log.d("TIME VALUE",String.valueOf(currentTime.getHours())+" "+String.valueOf(currentTime.getMinutes()));

                        oneWayLists.add(new OneWayList(segmentObject.getString("Airline_AirlineName"),
                                jsonObject.getString("airline_img_url")+segmentObject.getString("Airline_AirlineCode")+".gif",
                                segmentObject.getString("Airline_AirlineCode")+" "
                                        +segmentObject.getString("Airline_FlightNumber"),
                                segmentObject.getString("Origin_DepartureTime"),
                                segmentObject.getString("Origin__DepartureDate"),
                                segmentObject.getString("Origin_CityName"),
                                segmentObject.getString("Destination_ArrivalTime"),
                                segmentObject.getString("Destination__ArrivalDate"),
                                segmentObject.getString("Destination_CityName"),
                                segmentObject.getString("TotalDuaration"),
                                String.valueOf(segmentObject.getInt("TotalStops"))+" Stop",
                                priceValue, refundable,
                                flightObject.getString("ProvabAuthKey"),
                                flightObject.getString("booking_source"),
                                flightObject.getString("Token"),
                                flightObject.getString("TokenKey"),
                                false));
                        i++;
                    }

                    twoWayLists.add(new TwoWayList(oneWayLists));
                    j++;
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fromStation.setText(from);
                        toStation.setText(to);
                        travellerCount.setText(passengerCount);
                        backupList.addAll(twoWayLists);

                        if(isFilter){
                            twoWayLists.clear();
                            twoWayLists.addAll(backupList);
                            if(flightLists.size() != 0){
                                sortFlight(flightLists,minPrice,maxPrice,stopList,
                                        departureList,arrivalList);
                            }else {
                                sortPrice(minPrice,maxPrice,stopList,
                                        departureList,arrivalList, twoWayLists);
                            }
                        }else {
                            sortingPrice(1);
                            roundTripMainAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }else {

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void filterTypeOne(List<FlightList> flightLists,
                              List<Integer> stopCount,
                              List<Integer> departure,
                              List<Integer> arrival,
                              String minPrice, String maxPrice) {

        this.flightLists .clear();
        this.stopList.clear();
        this.arrivalList.clear();
        this.departureList.clear();

        this.flightLists.addAll(flightLists);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.stopList.addAll(stopCount);
        this.departureList.addAll(departure);
        this.arrivalList.addAll(arrival);

        if(this.flightLists.size() == 0 && minPrice == null && maxPrice == null &&
                stopCount.size() == 0 && departure.size() == 0 &&
                arrival.size() == 0){
            Thread thread = new Thread(){
                @Override
                public void run() {
                    parseData(response, false);
                }
            };
            thread.start();
        }else {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    parseData(response, true);
                }
            };
            thread.start();
        }
    }

    private void sortPrice(String minPrice, String maxPrice, List<Integer> stopList,
                           List<Integer> departureList, List<Integer> arrivalList,
                           List<TwoWayList> sortedList) {
        if(minPrice != null && maxPrice != null){
            tempTwoWayLists.clear();
            tempTwoWayLists.addAll(sortedList);

            List<OneWayList> listOne = new ArrayList<>();
            List<OneWayList> listTwo = new ArrayList<>();

            listOne.addAll(tempTwoWayLists.get(0).getOneWayLists());
            listTwo.addAll(tempTwoWayLists.get(1).getOneWayLists());

            twoWayLists.get(0).getOneWayLists().clear();
            int j=0;
            while (j < listOne.size()){
                String[] flightPrice = listOne.get(j).getFlightPrice().split(" ");
                if(Float.valueOf(flightPrice[1]) >=
                        Float.valueOf(minPrice) &&
                        Float.valueOf(flightPrice[1]) <=
                                Float.valueOf(maxPrice)){
                    twoWayLists.get(0).getOneWayLists().add(
                            listOne.get(j));
                }
                j++;
            }

            twoWayLists.get(1).getOneWayLists().clear();
            int i=0;
            while (i < listTwo.size()){
                String[] flightPrice = listTwo.get(i).getFlightPrice().split(" ");
                if(Float.valueOf(flightPrice[1]) >=
                        Float.valueOf(minPrice) &&
                        Float.valueOf(flightPrice[1]) <=
                                Float.valueOf(maxPrice)){
                    twoWayLists.get(1).getOneWayLists().add(
                            listTwo.get(i));
                }
                i++;
            }

        }

        sortStop(stopList, departureList, arrivalList, twoWayLists);

    }

    private void sortStop(List<Integer> stopList,
                          List<Integer> departureList,
                          List<Integer> arrivalList,
                          List<TwoWayList> sortedList) {
        if(stopList.size() != 0){
            tempTwoWayLists.clear();
            tempTwoWayLists.addAll(sortedList);

            List<OneWayList> listOne = new ArrayList<>();
            List<OneWayList> listTwo = new ArrayList<>();

            listOne.addAll(tempTwoWayLists.get(0).getOneWayLists());
            listTwo.addAll(tempTwoWayLists.get(1).getOneWayLists());

            twoWayLists.get(0).getOneWayLists().clear();
            int i=0;
            while (i < stopList.size()){
                int j=0;
                while (j < listOne.size()){
                    String[] stop = listOne.get(j).getFlightStopCount().split(" ");
                    if(Integer.valueOf(stop[0]) == stopList.get(i)){
                        twoWayLists.get(0).getOneWayLists().add(listOne.get(j));
                    }
                    j++;
                }
                i++;
            }

            twoWayLists.get(1).getOneWayLists().clear();
            int k=0;
            while (k < stopList.size()){
                int m=0;
                while (m < listTwo.size()){
                    String[] stop = listTwo.get(m).getFlightStopCount().split(" ");
                    if(Integer.valueOf(stop[0]) == stopList.get(k)){
                        twoWayLists.get(1).getOneWayLists().add(listTwo.get(m));
                    }
                    m++;
                }
                k++;
            }
        }

        sortDepartureTime(departureList, arrivalList, twoWayLists);

    }

    private void sortDepartureTime(List<Integer> departureList,
                                   List<Integer> arrivalList,
                                   List<TwoWayList> sortedList) {
        if(departureList.size() != 0){
            tempTwoWayLists.clear();
            tempTwoWayLists.addAll(sortedList);

            List<OneWayList> listOne = new ArrayList<>();
            List<OneWayList> listTwo = new ArrayList<>();

            listOne.addAll(tempTwoWayLists.get(0).getOneWayLists());
            listTwo.addAll(tempTwoWayLists.get(1).getOneWayLists());

            twoWayLists.get(0).getOneWayLists().clear();
            int i=0;
            while (i < departureList.size()){
                String departureTime = String.valueOf(departureList.get(i));
                int j=0;
                while (j < listOne.size()){
                    String[] time = listOne.get(j).getFlightDepartureTime().split(":");
                    Boolean isTrue = false;
                    switch (departureTime){
                        case "0":
                            isTrue = sortDataTime(0,6,
                                    Integer.parseInt(time[0]));
                            break;
                        case "6":
                            isTrue = sortDataTime(6,12,
                                    Integer.parseInt(time[0]));
                            break;
                        case "12":
                            isTrue = sortDataTime(12,18,
                                    Integer.parseInt(time[0]));
                            break;
                        case "18":
                            isTrue = sortDataTime(18,24,
                                    Integer.parseInt(time[0]));
                            break;
                    }

                    if(isTrue){
                        twoWayLists.get(0).getOneWayLists().add(listOne.get(j));
                    }

                    j++;
                }
                i++;
            }

            twoWayLists.get(1).getOneWayLists().clear();
            int k=0;
            while (k < departureList.size()){
                String departureTime = String.valueOf(departureList.get(k));
                int m=0;
                while (m < listTwo.size()){
                    String[] time = listTwo.get(m).getFlightDepartureTime().split(":");
                    Boolean isTrue = false;
                    switch (departureTime){
                        case "0":
                            isTrue = sortDataTime(0,6,
                                    Integer.parseInt(time[0]));
                            break;
                        case "6":
                            isTrue = sortDataTime(6,12,
                                    Integer.parseInt(time[0]));
                            break;
                        case "12":
                            isTrue = sortDataTime(12,18,
                                    Integer.parseInt(time[0]));
                            break;
                        case "18":
                            isTrue = sortDataTime(18,24,
                                    Integer.parseInt(time[0]));
                            break;
                    }

                    if(isTrue){
                        twoWayLists.get(1).getOneWayLists().add(listTwo.get(m));
                    }

                    m++;
                }
                k++;
            }
        }

        sortArrivalTime(arrivalList, twoWayLists);

    }

    private void sortArrivalTime(List<Integer> arrivalList,
                                 List<TwoWayList> sortedList) {

        if(arrivalList.size() != 0){
            tempTwoWayLists.clear();
            tempTwoWayLists.addAll(sortedList);

            List<OneWayList> listOne = new ArrayList<>();
            List<OneWayList> listTwo = new ArrayList<>();

            listOne.addAll(tempTwoWayLists.get(0).getOneWayLists());
            listTwo.addAll(tempTwoWayLists.get(1).getOneWayLists());

            twoWayLists.get(0).getOneWayLists().clear();
            int i=0;
            while (i < arrivalList.size()){
                String arrivalTime = String.valueOf(arrivalList.get(i));
                int j=0;
                while (j < listOne.size()){
                    String[] time = listOne.get(j).getFlightArrivalTime().split(":");
                    Boolean isTrue = false;
                    switch (arrivalTime){
                        case "0":
                            isTrue = sortDataTime(0,6,
                                    Integer.parseInt(time[0]));
                            break;
                        case "6":
                            isTrue = sortDataTime(6,12,
                                    Integer.parseInt(time[0]));
                            break;
                        case "12":
                            isTrue = sortDataTime(12,18,
                                    Integer.parseInt(time[0]));
                            break;
                        case "18":
                            isTrue = sortDataTime(18,24,
                                    Integer.parseInt(time[0]));
                            break;
                    }

                    if(isTrue){
                        twoWayLists.get(0).getOneWayLists().add(listOne.get(j));
                    }

                    j++;
                }
                i++;
            }

            twoWayLists.get(1).getOneWayLists().clear();
            int k=0;
            while (k < arrivalList.size()){
                String arrivalTime = String.valueOf(arrivalList.get(k));
                int m=0;
                while (m < listTwo.size()){
                    String[] time = listTwo.get(m).getFlightArrivalTime().split(":");
                    Boolean isTrue = false;
                    switch (arrivalTime){
                        case "0":
                            isTrue = sortDataTime(0,6,
                                    Integer.parseInt(time[0]));
                            break;
                        case "6":
                            isTrue = sortDataTime(6,12,
                                    Integer.parseInt(time[0]));
                            break;
                        case "12":
                            isTrue = sortDataTime(12,18,
                                    Integer.parseInt(time[0]));
                            break;
                        case "18":
                            isTrue = sortDataTime(18,24,
                                    Integer.parseInt(time[0]));
                            break;
                    }

                    if(isTrue){
                        twoWayLists.get(1).getOneWayLists().add(listTwo.get(m));
                    }

                    m++;
                }
                k++;
            }
        }
        if(twoWayLists.get(0).getOneWayLists().size() != 0 && twoWayLists.get(0).getOneWayLists().size() != 0){
            roundTripMainAdapter.notifyDataSetChanged();
        }else {
            commonUtils.toastShort("No Flight's Found.",getActivity());
            roundTripMainAdapter.notifyDataSetChanged();
        }

    }

    private Boolean sortDataTime(int minTime, int maxTime,
                                 int value) {
        Boolean isMatching = false;
        if(value >= minTime && value <= maxTime){
            isMatching = true;
        }
        return isMatching;
    }

    private void sortFlight(List<FlightList> flightLists, String minPrice,
                            String maxPrice, List<Integer> stopList,
                            List<Integer> departureList, List<Integer> arrivalList) {
        tempTwoWayLists.clear();
        tempTwoWayLists.addAll(backupList);

        List<OneWayList> listOne = new ArrayList<>();
        List<OneWayList> listTwo = new ArrayList<>();

        listOne.addAll(tempTwoWayLists.get(0).getOneWayLists());
        listTwo.addAll(tempTwoWayLists.get(1).getOneWayLists());

        twoWayLists.get(0).getOneWayLists().clear();
        int k=0;
        while (k < flightLists.size()){
            int j=0;
            while (j < listOne.size()){
                String[] flightCode = listOne.get(j).getFlightCode().split(" ");
                if(flightCode[0].equals(flightLists.get(k).getAirlineCode())){
                    twoWayLists.get(0).getOneWayLists().add(
                            listOne.get(j));
                }
                j++;
            }
            k++;
        }

        twoWayLists.get(1).getOneWayLists().clear();
        int m=0;
        while (m < flightLists.size()){
            int i=0;
            while (i < listTwo.size()){
                String[] flightCode = listTwo.get(i).getFlightCode().split(" ");
                if(flightCode[0].equals(flightLists.get(m).getAirlineCode())){
                    twoWayLists.get(1).getOneWayLists().add(
                            listTwo.get(i));
                }
                i++;
            }
            m++;
        }

        sortPrice(minPrice,maxPrice,this.stopList,
                this.departureList,this.arrivalList, twoWayLists);
    }

    @Override
    public void filterTypeTwo(List<FlightList> flightLists,List<Integer> stopCount,
                              String minPrice, String maxPrice) {
        /*
        * used only in Multi City
        * */
    }

    @Override
    public void sortingNotify(int price, int departure, int arrival, int duration) {

    }

    @Override
    public void filterTypeBus(List<BusOperatorsInfo> busOprList, List<Integer> departure, List<Integer> arrival, String minPrice, String maxPrice,String selectBusType) {

    }


    public void DepartureDetails(int position, String provabAuthKey,
                                 String bookingSource, String token,String tokenKey) {

        departureKey = provabAuthKey;
        departureBookingSource = bookingSource;
        departureToken = token;
        departureTokenKey = tokenKey;

        int i=0;
        while (i<twoWayLists.get(0).getOneWayLists().size()){
            if(i == position){
                twoWayLists.get(0).getOneWayLists().get(i).setSelected(true);
            }else {
                twoWayLists.get(0).getOneWayLists().get(i).setSelected(false);
            }
            i++;
        }

        roundTripMainAdapter.notifyDataSetChanged();

        Double departurePriceTicket = 0.0,
                returnPriceTicket = 0.0,
                total;

        departurePriceTicket = Double.parseDouble(
                twoWayLists.get(0).getOneWayLists().get(position).getFlightPrice().split(" ")[1]);
        departurePrice.setText(Global.currencySymbol+" "
                +String.valueOf(Math.round(departurePriceTicket)));

        if(returnPrice.getText().toString().length() != 0){
            returnPriceTicket = Double.parseDouble(
                    returnPrice.getText().toString().split(" ")[1]);
        }

        total = departurePriceTicket + returnPriceTicket;
        totalPrice.setText(Global.currencySymbol+" "
                +String.valueOf(Math.round(total)));
    }

    public void ReturnDetails(int position, String provabAuthKey,
                              String bookingSource, String token,String tokenKey) {

        returnKey = provabAuthKey;
        returnBookingSource = bookingSource;
        returnToken = token;
        returnTokenKey = tokenKey;

        int i=0;
        while (i<twoWayLists.get(1).getOneWayLists().size()){
            if(i == position){
                twoWayLists.get(1).getOneWayLists().get(i).setSelected(true);
            }else {
                twoWayLists.get(1).getOneWayLists().get(i).setSelected(false);
            }
            i++;
        }

        roundTripMainAdapter.notifyDataSetChanged();

        Double departurePriceTicket = 0.0,
                returnPriceTicket = 0.0,
                total;

        returnPriceTicket = Double.parseDouble(
                twoWayLists.get(1).getOneWayLists().get(position).getFlightPrice().split(" ")[1]);
        returnPrice.setText(Global.currencySymbol+" "
                +String.valueOf(Math.round(returnPriceTicket)));

        if(departurePrice.getText().toString().length() != 0){
            departurePriceTicket = Double.parseDouble(
                    departurePrice.getText().toString().split(" ")[1]);
        }

        total = departurePriceTicket + returnPriceTicket;
        totalPrice.setText(Global.currencySymbol+" "
                +String.valueOf(Math.round(total)));

    }

    @Override
    public void getResponse(String response, int flag) {
        switch (flag){
            case 2:
                progressLoader.DismissProgress();
                callFairRuleDialog(response);
                break;
            default:
                detailResponse = response;
                break;
        }
    }

    @Override
    public void notifyFilter() {
        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                new FilterFragment(airlineList, priceBreakup, this,
                        flightLists, minPrice, maxPrice, stopList, departureList,
                        arrivalList,false),null, true);
    }

    /**
     * Below Method there is no use in this Fragment
     * */
    @Override
    public void notifyProfileView() {

    }

    /**
     * Sorting
     * */
    @Override
    public void notifySort() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.flight_sort_fragment);
        dialog.setCancelable(false);

       final RadioButton priceLow, priceHigh, departureEarly, departureLater,
               arrivalEarly, arrivalLater, fastFlight, slowFlight,
               refundableFlight, nonRefundableFlight;

        priceLow = dialog.findViewById(R.id.price_low);
        priceHigh = dialog.findViewById(R.id.price_high);
        departureEarly = dialog.findViewById(R.id.dep_earliest);
        departureLater = dialog.findViewById(R.id.dep_latest);
        arrivalEarly = dialog.findViewById(R.id.arri_earliest);
        arrivalLater = dialog.findViewById(R.id.arri_latest);
        fastFlight = dialog.findViewById(R.id.fastest);
        slowFlight = dialog.findViewById(R.id.slow);
        refundableFlight = dialog.findViewById(R.id.refundable);
        nonRefundableFlight = dialog.findViewById(R.id.non_refundable);

        TextView applySort = dialog.findViewById(R.id.apply_sort);

        if(low_price)
        {
            priceLow.setChecked(true);
        }
        if(high_price)
        {
            priceHigh.setChecked(true);
        }
        if(earliest)
        {
            departureEarly.setChecked(true);
        }
        if(latest)
        {
            departureLater.setChecked(true);
        }
        if(arr_earliest)
        {
            arrivalEarly.setChecked(true);
        }
        if(arr_latest)
        {
            arrivalLater.setChecked(true);
        }
        if(fastest)
        {
            fastFlight.setChecked(true);
        }
        if(slow)
        {
            slowFlight.setChecked(true);
        }
        if(refundable){
            refundableFlight.setChecked(true);
        }
        if(nonRefundable){
            nonRefundableFlight.setChecked(true);
        }
        applySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(priceLow.isChecked()){
                    sortingPrice(1);
                }else if(priceHigh.isChecked()){
                    sortingPrice(2);
                }else if(departureEarly.isChecked()){
                    sortEarlyFlight(1);
                }else if(departureLater.isChecked()){
                    sortEarlyFlight(2);
                }else if(arrivalEarly.isChecked()){
                    sortArrivalEarly(1);
                }else if(arrivalLater.isChecked()){
                    sortArrivalEarly(2);
                }else if(fastFlight.isChecked()){
                    sortFlightTime(1);
                }else if(slowFlight.isChecked()){
                    sortFlightTime(2);
                }else if(refundableFlight.isChecked()){
                    refundable = true;
                    sortFareGroup(1, backupList);
                }else if(nonRefundableFlight.isChecked()){
                    nonRefundable = true;
                    sortFareGroup(2, backupList);
                }
                dialog.dismiss();
            }
        });
        TextView cancelDialog = dialog.findViewById(R.id.exit_sort);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                low_price=true;
                high_price=false;
                earliest=false;
                latest=false;
                arr_earliest=false;
                arr_latest=false;
                fastest=false;
                slow=false;
                refundable=false;
                nonRefundable=false;
                twoWayLists.clear();
                twoWayLists.addAll(backupList);
                sortingPrice(1);
                dialog.dismiss();
            }
        });

        priceLow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    low_price=false;
                }

            }
        });

        priceHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    high_price=false;
                }
            }
        });


        departureEarly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    earliest=false;
                }

            }
        });


        departureLater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    latest=false;
                }
            }
        });

        arrivalEarly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    arr_earliest=false;
                }
            }
        });

        arrivalLater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    arr_latest=false;
                }

            }
        });

        fastFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    fastest=false;
                }

            }
        });

        slowFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    slow=false;
                }
            }
        });

        refundableFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    refundable = false;
                }
            }
        });

        nonRefundableFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    nonRefundable = false;
                }
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void sortFareGroup(Integer i, List<TwoWayList> sortedArray) {
        tempTwoWayLists.clear();
        tempTwoWayLists.addAll(sortedArray);

        List<OneWayList> listOne = new ArrayList<OneWayList>();
        List<OneWayList> listTwo = new ArrayList<OneWayList>();

        listOne.addAll(tempTwoWayLists.get(0).getOneWayLists());
        listTwo.addAll(tempTwoWayLists.get(1).getOneWayLists());

        twoWayLists.clear();
        List<OneWayList> oneTemp = new ArrayList<OneWayList>();

        int m=0;
        while (m < listOne.size()){
            Integer k=0;
            if(listOne.get(m).getFlightIsRefundable().equals("Refundable")){
                k = 1;
            }else {
                k =2;
            }

            if(i == k){
                oneTemp.add(listOne.get(m));
            }
            m++;
        }
        twoWayLists.add(new TwoWayList(oneTemp));

        List<OneWayList> twoTemp = new ArrayList<OneWayList>();
        int n=0;
        while (n < listTwo.size()){
            Integer k=0;
            if(listTwo.get(n).getFlightIsRefundable().equals("Refundable")){
                k = 1;
            }else {
                k =2;
            }

            if(i == k){
                twoTemp.add(listTwo.get(n));
            }
            n++;
        }
        twoWayLists.add(new TwoWayList(twoTemp));

        if(twoWayLists.get(0).oneWayLists.size() == 0 &&
                this.twoWayLists.get(1).oneWayLists.size() == 0){
            commonUtils.toastShort("No Flight's Found", getActivity());
        }

        roundTripMainAdapter.notifyDataSetChanged();
    }

    private void sortingPrice(int i) {
        Collections.sort(twoWayLists.get(0).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                Double p1 = Double.parseDouble(oneWayList.getFlightPrice().split(" ")[1]);
                Double p2 = Double.parseDouble(t1.getFlightPrice().split(" ")[1]);
                return p1.compareTo(p2);
            }
        });

        Collections.sort(twoWayLists.get(1).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                Double p1 = Double.parseDouble(oneWayList.getFlightPrice().split(" ")[1]);
                Double p2 = Double.parseDouble(t1.getFlightPrice().split(" ")[1]);
                return p1.compareTo(p2);
            }
        });

        switch (i){
            case 2:
                Collections.reverse(twoWayLists.get(0).getOneWayLists());
                Collections.reverse(twoWayLists.get(1).getOneWayLists());
                break;
        }
        roundTripMainAdapter.notifyDataSetChanged();
    }

    private void sortEarlyFlight(int i) {
        Collections.sort(twoWayLists.get(0).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date1 = null, date2 = null;
                try {
                    date1 = sdf.parse(oneWayList.getFlightDepartureTime());
                    date2 = sdf.parse(t1.getFlightDepartureTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
        Collections.sort(twoWayLists.get(1).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date1 = null, date2 = null;
                try {
                    date1 = sdf.parse(oneWayList.getFlightDepartureTime());
                    date2 = sdf.parse(t1.getFlightDepartureTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
        switch (i){
            case 2:
                Collections.reverse(twoWayLists.get(0).getOneWayLists());
                Collections.reverse(twoWayLists.get(1).getOneWayLists());
                break;
        }
        roundTripMainAdapter.notifyDataSetChanged();
    }

    private void sortArrivalEarly(int i) {
        Collections.sort(twoWayLists.get(0).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date1 = null, date2 = null;
                try {
                    date1 = sdf.parse(oneWayList.getFlightArrivalTime());
                    date2 = sdf.parse(t1.getFlightArrivalTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
        Collections.sort(twoWayLists.get(1).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date1 = null, date2 = null;
                try {
                    date1 = sdf.parse(oneWayList.getFlightArrivalTime());
                    date2 = sdf.parse(t1.getFlightArrivalTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
        switch (i){
            case 2:
                Collections.reverse(twoWayLists.get(0).getOneWayLists());
                Collections.reverse(twoWayLists.get(1).getOneWayLists());
                break;
        }
        roundTripMainAdapter.notifyDataSetChanged();
    }

    private void sortFlightTime(int i) {
        Collections.sort(twoWayLists.get(0).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                Integer timeOne = 0, timeTwo = 0;
                if(oneWayList.getFlightTravelTime().contains("h")){
                    String[] data = oneWayList.getFlightTravelTime().split("h");
                    Integer h1 = Integer.parseInt(data[0]) * 60;
                    String minutes = "0";
                    if(data[1].contains("m")){
                        minutes = oneWayList.getFlightTravelTime().split("h")[1].split(" ")[1].split("m")[0];
                    }
                    timeOne = h1+Integer.parseInt(minutes);
                }else {
                    String[] data = oneWayList.getFlightTravelTime().split("m");
                    timeOne = Integer.parseInt(data[0]);
                }
                if(t1.getFlightTravelTime().contains("h")){
                    String[] data1 = t1.getFlightTravelTime().split("h");
                    Integer h1 = Integer.parseInt(data1[0]) * 60;
                    String minutes = "0";
                    if(data1[1].contains("m")){
                        minutes = t1.getFlightTravelTime().split("h")[1].split(" ")[1].split("m")[0];
                    }
                    timeTwo = h1+Integer.parseInt(minutes);
                }else {
                    String[] data = t1.getFlightTravelTime().split("m");
                    timeTwo = Integer.parseInt(data[0]);
                }
                return timeOne.compareTo(timeTwo);
            }
        });
        Collections.sort(twoWayLists.get(1).oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                Integer timeOne = 0, timeTwo = 0;
                if(oneWayList.getFlightTravelTime().contains("h")){
                    String[] data = oneWayList.getFlightTravelTime().split("h");
                    Integer h1 = Integer.parseInt(data[0]) * 60;
                    String minutes = "0";
                    if(data[1].contains("m")){
                        minutes = oneWayList.getFlightTravelTime().split("h")[1].split(" ")[1].split("m")[0];
                    }
                    timeOne = h1+Integer.parseInt(minutes);
                }else {
                    String[] data = oneWayList.getFlightTravelTime().split("m");
                    timeOne = Integer.parseInt(data[0]);
                }
                if(t1.getFlightTravelTime().contains("h")){
                    String[] data1 = t1.getFlightTravelTime().split("h");
                    Integer h1 = Integer.parseInt(data1[0]) * 60;
                    String minutes = "0";
                    if(data1[1].contains("m")){
                        minutes = t1.getFlightTravelTime().split("h")[1].split(" ")[1].split("m")[0];
                    }
                    timeTwo = h1+Integer.parseInt(minutes);
                }else {
                    String[] data = t1.getFlightTravelTime().split("m");
                    timeTwo = Integer.parseInt(data[0]);
                }
                return timeOne.compareTo(timeTwo);
            }
        });
        switch (i){
            case 2:
                Collections.reverse(twoWayLists.get(0).getOneWayLists());
                Collections.reverse(twoWayLists.get(1).getOneWayLists());
                break;
        }
        roundTripMainAdapter.notifyDataSetChanged();
    }


    WebServiceController webServiceController;
    ProgressLoader progressLoader;
    public void callFairRule(String provabAuthKey, String token) {
        progressLoader.ShowProgress(getActivity());
        FairRule fairRule = new FairRule(token,
                "PTBSID0000000002", provabAuthKey);
        String param = gson.toJson(fairRule);
        RequestParams requestParams = new RequestParams();
        requestParams.put("farerules", param);
        webServiceController.postRequest(apiConstants.FAIR_RULE, requestParams, 2);
    }

    private void callFairRuleDialog(String response) {
        String contentBody = null;
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getInt("status") == 1){
                JSONArray dataArray = jsonObject.getJSONArray("data");
                JSONObject dataObject = dataArray.getJSONObject(0);
                contentBody = dataObject.getString("FareRules");
            }else {
                commonUtils.toastShort(jsonObject.getString("msg"), getActivity());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(contentBody != null){
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.fair_rule_dialog);
            dialog.setCancelable(false);

            TextView contentText, closeDialog;

            contentText = dialog.findViewById(R.id.content_text);
            closeDialog = dialog.findViewById(R.id.close);

            contentText.setText(Html.fromHtml(contentBody));

            closeDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.show();
        }else {
            commonUtils.toastShort("No data found", getActivity());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        action_type.setVisibility(View.GONE);
    }


}