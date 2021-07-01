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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.TripToEasy.flight.adapter.multiCityList.MultiCityMain;
import com.TripToEasy.interfaces.FilterNotify;
import com.TripToEasy.model.parsingModel.flight.FlightList;
import com.TripToEasy.model.parsingModel.flight.multiCity.FlightDetailList;
import com.TripToEasy.model.parsingModel.flight.multiCity.MultiCityParsing;
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

/**
 * Created by ptblr-1195 on 1/3/18.
 */

public class MultiCityFragment extends BaseFragment
        implements FilterNotify, WebInterface, ProfileFilterNotify {

    @BindView(R.id.one_way_list)
    RecyclerView multiCityFlight;

    @BindView(R.id.from_station)
    TextView fromStation;

    @BindView(R.id.to_destination)
    TextView toStation;

    @BindView(R.id.traveller_count)
    TextView travellerCount;

    @BindView(R.id.travel_date)
    TextView travelDate;

    @BindView(R.id.return_date)
    TextView travelReturnDate;

    @OnClick(R.id.filter_action)
    void filterAction(){
        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                new FilterFragment(airlineList, priceBreakup, this,
                        flightLists, minPrice, maxPrice, stopList, departureList,
                        arrivalList, true),null, true);
    }

    String response, airlineList, priceBreakup, minPrice = null,
            maxPrice = null, detailResponse, catchFileName,
            departureDate,returnDate, cabinClass,type;
    boolean low_price,high_price,earliest,latest,arr_earliest,arr_latest,
            fastest,slow, refundable, nonRefundable;
    List<MultiCityParsing> multiCityParsing = new ArrayList<MultiCityParsing>();
    List<MultiCityParsing> backupList = new ArrayList<MultiCityParsing>();
    List<MultiCityParsing> tempMultiCityParsing = new ArrayList<MultiCityParsing>();

    List<FlightList> flightLists = new ArrayList<FlightList>();
    List<Integer> stopList = new ArrayList<Integer>();
    List<Integer> departureList = new ArrayList<Integer>();
    List<Integer> arrivalList = new ArrayList<Integer>();
    MultiCityMain multiCityMain;
    ImageView action_type;

    @SuppressLint("ValidFragment")
    public MultiCityFragment(String response, String catchFileName,
                             String departureDate, String cabinClass,
                             String type) {
        this.response = response;
        this.catchFileName = catchFileName;
        this.departureDate = departureDate;
        this.cabinClass = cabinClass;
        this.type = type;
    }

    @SuppressLint("ValidFragment")
    public MultiCityFragment(String response, String catchFileName,
                             String departureDate,String returnDate, String cabinClass,
                             String type) {
        this.response = response;
        this.catchFileName = catchFileName;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.cabinClass = cabinClass;
        this.type = type;
    }

    public MultiCityFragment(){
        /**
         * Empty Constructor
         * */
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.one_way_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(multiCityFlight,getActivity());
        action_type = getActivity().findViewById(R.id.action_type);
        action_type.setVisibility(View.VISIBLE);
        webServiceController = new WebServiceController(getActivity(), MultiCityFragment.this);
        progressLoader = new ProgressLoader();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        travelDate.setText(departureDate);
        if(type.equals("RT"))
        {
            travelReturnDate.setVisibility(View.VISIBLE);
            try{
                Date endDate = dateFormat.parse(returnDate);
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd",Locale.ENGLISH);
                returnDate = sdf.format(endDate);
                travelReturnDate.setText(returnDate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        lodeData();
        lodeDetailData();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).toolbarActionIcon(MultiCityFragment.this, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).toolbarActionIcon(MultiCityFragment.this, 0);
    }

    private void lodeData() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    JSONObject mainObject = new JSONObject(response);

                    airlineList = mainObject.getJSONArray("airline_list").toString();
                    priceBreakup = mainObject.getJSONObject("price").toString();

                    JSONObject journeySummary = mainObject.getJSONObject("JourneySummary");
                    final String from = journeySummary.getString("Origin");
                    final String to = journeySummary.getString("Destination");

                    JSONObject traveller = journeySummary.getJSONObject("PassengerConfig");
                    final String passengerCount = String.valueOf(traveller.getInt("TotalPassenger"))+" Travellers";

                    JSONArray flightArrayOne = mainObject.getJSONArray("Flights");
                    JSONArray flightArrayTwo = flightArrayOne.getJSONArray(0);
                    int i=0;
                    while (i<flightArrayTwo.length()){
                        JSONObject flightObject = flightArrayTwo.getJSONObject(i);
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
                        String flightCode = null;
                        int j=0;
                        List<FlightDetailList> flightDetailList = new ArrayList<FlightDetailList>();
                        while (j<segmentArray.length()){
                            JSONObject segmentObject = segmentArray.getJSONObject(j);
                            flightCode = segmentObject.getString("Airline_AirlineCode");
                            flightDetailList.add(new FlightDetailList(segmentObject.getString("Airline_AirlineName"),
                                    mainObject.getString("airline_img_url")+segmentObject.getString("Airline_AirlineCode")+".gif",
                                    segmentObject.getString("Airline_AirlineCode")+" "
                                            +segmentObject.getString("Airline_FlightNumber"),
                                    segmentObject.getString("Origin_DepartureTime"),
                                    segmentObject.getString("Origin__DepartureDate"),
                                    segmentObject.getString("Origin_CityName"),
                                    segmentObject.getString("Destination_ArrivalTime"),
                                    segmentObject.getString("Destination__ArrivalDate"),

                                    segmentObject.getString("Destination_CityName"),
                                    segmentObject.getString("TotalDuaration"),
                                    flightObject.getString("Token"),
                                    String.valueOf(segmentObject.getInt("TotalStops"))+" Stop"));
                            j++;
                        }

                        multiCityParsing.add(new MultiCityParsing
                                (priceValue,refundable,flightCode,
                                        flightObject.getString("ProvabAuthKey"),
                                        flightObject.getString("booking_source"),
                                        flightObject.getString("Token"),
                                        flightDetailList));
                        i++;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fromStation.setText(from);
                            toStation.setText(to);
                            travellerCount.setText(passengerCount);
                            backupList.addAll(multiCityParsing);

                            multiCityMain = new MultiCityMain(getActivity(),
                                    MultiCityFragment.this,multiCityParsing);
                            multiCityFlight.setAdapter(multiCityMain);
                            sortByPrice(1);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    private void lodeDetailData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("search_key", catchFileName);
        WebServiceController webServiceController = new
                WebServiceController(getActivity(),MultiCityFragment.this);
        webServiceController.postRequest(apiConstants.FLIGHT_DETAIL, requestParams, 9999);
    }

    @Override
    public void filterTypeOne(List<FlightList> flightLists, List<Integer> stopCount, List<Integer> departure, List<Integer> arrival, String minPrice, String maxPrice) {
        /**
         * used in one way and round trip only
         * */
        multiCityParsing.clear();
        multiCityParsing.addAll(backupList);

        this.flightLists .clear();
        this.stopList.clear();
        this.departureList .clear();
        this.arrivalList .clear();

        this.flightLists.addAll(flightLists);
        this.stopList.addAll(stopCount);
        this.departureList.addAll(departure);
        this.arrivalList.addAll(arrival);

        this.minPrice = minPrice;
        this.maxPrice = maxPrice;

        if(this.flightLists.size() == 0 && minPrice == null && maxPrice == null &&
                this.stopList.size()==0 && departure.size() == 0 &&
                arrival.size() == 0){
            sortByPrice(1);
        }else {
            if(this.flightLists.size() != 0){
                sortFlight(flightLists,stopCount,minPrice,maxPrice);
            }else {
                sortPrice(minPrice,maxPrice,multiCityParsing,stopCount);
            }
        }

    }

    @Override
    public void filterTypeTwo(List<FlightList> flightLists,List<Integer> stopCount,
                              String minPrice, String maxPrice) {
        multiCityParsing.clear();
        multiCityParsing.addAll(backupList);

        this.flightLists .clear();
        this.stopList.clear();
        this.flightLists.addAll(flightLists);
        this.stopList.addAll(stopCount);

        this.minPrice = minPrice;
        this.maxPrice = maxPrice;

        if(this.flightLists.size() == 0 && minPrice == null && maxPrice == null && this.stopList.size()==0){
            sortByPrice(1);
        }else {
            if(this.flightLists.size() != 0){
                sortFlight(flightLists,stopCount,minPrice,maxPrice);
            }else {
                sortPrice(minPrice,maxPrice,multiCityParsing,stopCount);
            }
        }
    }

    @Override
    public void sortingNotify(int price, int departure, int arrival, int duration) {

    }

    @Override
    public void filterTypeBus(List<BusOperatorsInfo> busOprList, List<Integer> departure, List<Integer> arrival, String minPrice, String maxPrice,String selectBusType) {

    }


    private void sortPrice(String minPrice, String maxPrice,
                           List<MultiCityParsing> sortedList,List<Integer> stopCount) {
        if(minPrice != null && maxPrice != null){
            tempMultiCityParsing.clear();
            tempMultiCityParsing.addAll(sortedList);
            multiCityParsing.clear();

            int i=0;
            while (i<tempMultiCityParsing.size()){
                String[] flightPrice = tempMultiCityParsing.get(i).getPriceValue().split(" ");
                if(Float.valueOf(flightPrice[1]) >=
                        Float.valueOf(minPrice) &&
                        Float.valueOf(flightPrice[1]) <=
                                Float.valueOf(maxPrice)){
                    multiCityParsing.add(tempMultiCityParsing.get(i));
                }
                i++;
            }
        }

        sortStop(stopCount,multiCityParsing);
    }

    private void sortFlight(List<FlightList> flightLists,List<Integer> stopCount,
                            String minPrice, String maxPrice) {
        tempMultiCityParsing.clear();
        tempMultiCityParsing.addAll(multiCityParsing);
        multiCityParsing.clear();

        int i=0;
        while (i<flightLists.size()){
            int j=0;
            while (j<tempMultiCityParsing.size()){
                if(flightLists.get(i).getAirlineCode().equals(
                        tempMultiCityParsing.get(j).getFlightCode())){
                    multiCityParsing.add(tempMultiCityParsing.get(j));
                }
                j++;
            }
            i++;
        }

        sortPrice(minPrice,maxPrice, multiCityParsing,stopCount);
    }

    private void sortStop(List<Integer> stopCount,List<MultiCityParsing> sortedList) {
        if(stopCount.size() != 0){
            tempMultiCityParsing.clear();
            tempMultiCityParsing.addAll(sortedList);
            multiCityParsing.clear();

            int j=0;
            while (j < stopCount.size()){
                int i=0;
                while (i < tempMultiCityParsing.size()){
                    String[] stop = tempMultiCityParsing.get(i).getFlightDetailLists().get(0).getFlightStopCount().split(" ");
                    String[] stop1 = null;
                    if(type.equals("RT")){
                        stop1 = tempMultiCityParsing.get(i).getFlightDetailLists().get(1).getFlightStopCount().split(" ");
                    }

                    if(type.equals("RT")){
                        if(Integer.valueOf(stop[0]) == stopCount.get(j) &&
                                Integer.parseInt(stop1[0]) == stopCount.get(j)){
                            multiCityParsing.add(tempMultiCityParsing.get(i));
                        }
                    }else {
                        if(Integer.valueOf(stop[0]) == stopCount.get(j)){
                            multiCityParsing.add(tempMultiCityParsing.get(i));
                        }
                    }
                    i++;
                }
                j++;
            }
        }

        sortDepartureTime(departureList, arrivalList, multiCityParsing);
    }

    private void sortDepartureTime(List<Integer> departureList,
                                   List<Integer> arrivalList,
                                   List<MultiCityParsing> multiCityParsing) {
        if(departureList.size() != 0){
            tempMultiCityParsing.clear();
            tempMultiCityParsing.addAll(multiCityParsing);
            this.multiCityParsing.clear();
            String depTime;

            int j=0;
            while (j<departureList.size()){
                depTime = String.valueOf(departureList.get(j));
                int i=0;
                while (i<tempMultiCityParsing.size()){
                    String[] time = tempMultiCityParsing.get(i).getFlightDetailLists().get(0).getFlightDepartureTime().split(":");
                    switch (depTime){
                        case "0":
                            sortDataTime(0,6,Integer.parseInt(time[0]),i);
                            break;
                        case "6":
                            sortDataTime(6,12,Integer.parseInt(time[0]),i);
                            break;
                        case "12":
                            sortDataTime(12,18,Integer.parseInt(time[0]),i);
                            break;
                        case "18":
                            sortDataTime(18,24,Integer.parseInt(time[0]),i);
                            break;
                    }
                    i++;
                }
                j++;
            }
        }


        //TODO : Arrival Time Sort.

        sortArrivalTime(arrivalList, multiCityParsing);

    }

    private void sortArrivalTime(List<Integer> arrivalList,
                                 List<MultiCityParsing> multiCityParsing) {

        if(arrivalList.size() != 0){
            tempMultiCityParsing.clear();
            tempMultiCityParsing.addAll(multiCityParsing);
            this.multiCityParsing.clear();
            String arrivalTime;

            int j=0;
            while (j<arrivalList.size()){
                arrivalTime = String.valueOf(arrivalList.get(j));
                int i=0;
                while (i<tempMultiCityParsing.size()){
                    String[] time = tempMultiCityParsing.get(i).getFlightDetailLists().get(0).getFlightArrivalTime().split(":");
                    switch (arrivalTime){
                        case "0":
                            sortDataTime(0,6,Integer.parseInt(time[0]),i);
                            break;
                        case "6":
                            sortDataTime(6,12,Integer.parseInt(time[0]),i);
                            break;
                        case "12":
                            sortDataTime(12,18,Integer.parseInt(time[0]),i);
                            break;
                        case "18":
                            sortDataTime(18,24,Integer.parseInt(time[0]),i);
                            break;
                    }
                    i++;
                }
                j++;
            }
        }

        if(multiCityParsing.size() == 0){
            commonUtils.toastShort("No Flight Found", getActivity());
        }
        multiCityMain.notifyDataSetChanged();
    }

    private void sortDataTime(int minTime, int maxTime, int value, int position) {
        if(value >= minTime && value <= maxTime){
            multiCityParsing.add(tempMultiCityParsing.get(position));
        }
    }

    public void callDetailFragment(String provabAuthKey, String bookingSource,
                                   String token, int position) {
        String tax = null, adultPrice = null, childPrice = null,
                infantPrice = null, totalPrice = null,diff_amount=null,
                airlineUrl = null, dataArray = null;
        int adultCount = 0, childCount = 0, infantCount = 0;
        try{
            JSONObject jsonObject = new JSONObject(response);

            airlineUrl = jsonObject.getString("airline_img_url");

            JSONArray flightArray = jsonObject.getJSONArray("Flights");
            JSONArray flightNextArray = flightArray.getJSONArray(0);
            int i=0;
            while (i < flightNextArray.length()) {
                JSONObject flightObject = flightNextArray.getJSONObject(i);
                if(flightObject.getString("Token").equals(token)){
                    position = i;
                    break;
                }
                i++;
            }

            JSONObject flightObject = flightNextArray.getJSONObject(position);
            JSONObject fairOne = flightObject.getJSONObject("FareDetails");
            JSONObject fairTwo = fairOne.getJSONObject("b2c_PriceDetails");

            tax = String.valueOf(fairTwo.getDouble("TotalTax"));
            totalPrice = String.valueOf(fairTwo.getDouble("TotalFare"));

            JSONObject passengerFair = flightObject.getJSONObject("PassengerFareBreakdown");
            if(passengerFair.has("ADT")){
                JSONObject adultObj = passengerFair.getJSONObject("ADT");
                adultPrice = String.valueOf(adultObj.getDouble("BaseFare"));
                adultCount = adultObj.getInt("PassengerCount");
            }

            if(passengerFair.has("CHD")){
                JSONObject childObj = passengerFair.getJSONObject("CHD");
                childPrice = String.valueOf(childObj.getDouble("BaseFare"));
                childCount = childObj.getInt("PassengerCount");
            }

            if(passengerFair.has("INF")){
                JSONObject infantObj = passengerFair.getJSONObject("INF");
                infantPrice = String.valueOf(infantObj.getDouble("BaseFare"));
                infantCount = infantObj.getInt("PassengerCount");
            }

            JSONArray dataArrayMain = new JSONArray(detailResponse);
            int k=0;
            while (k < dataArrayMain.length()) {
                JSONObject detailObj = dataArrayMain.getJSONObject(k);
                JSONArray dataArrayOne = detailObj.getJSONArray(token);
                dataArray = dataArrayOne.toString();
                break;
            }

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
        if(type.equals("RT")) {
            bundle.putString("return_date", travelReturnDate.getText().toString());
        }else {
            bundle.putString("return_date", "NA");
        }
        bundle.putString("token",token);
        bundle.putString("auth_key", provabAuthKey);
        bundle.putString("booking_source", bookingSource);
        bundle.putString("return_token","NA");
        bundle.putString("return_tokenKey","NA");
        bundle.putString("return_auth_key", "NA");
        bundle.putString("return_booking_source", "NA");
        bundle.putString("adult_price",adultPrice);
        bundle.putString("child_price", childPrice);
        bundle.putString("infant_price", infantPrice);
        bundle.putInt("adult_count", adultCount);
        bundle.putInt("child_count", childCount);
        bundle.putInt("infant_count", infantCount);
        bundle.putString("tax", tax);
        bundle.putString("detail_response",dataArray);
        bundle.putString("detail_response_two",null);
        bundle.putString("total_price",totalPrice);
        bundle.putString("airline_url",airlineUrl);
        bundle.putString("diff_amount",diff_amount);
        intentAndFragmentService.intentDisplay(getActivity(),
                FlightSupportActivity.class, bundle);
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
                        arrivalList, true),null, true);
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
        dialog.setContentView(R.layout.flight_sort_fragment_multicity);
        dialog.setCancelable(false);

        final RadioButton priceLow, priceHigh, departureEarly, departureLater,
                arrivalEarly, arrivalLater, refundableRadio, nonRefundableRadio;
        final RadioGroup price_group,departure_group,arrival_group,
                duration_group, fareGroup;
        TextView startingFlight, returnFlight;
        LinearLayout depLay, arriLay, durationLay, fareLayout;

        price_group = dialog.findViewById(R.id.price_group);
        departure_group = dialog.findViewById(R.id.departure_group);
        arrival_group = dialog.findViewById(R.id.arrival_group);
        duration_group = dialog.findViewById(R.id.duration_group);
        fareGroup = dialog.findViewById(R.id.fare_group);

        priceLow = dialog.findViewById(R.id.price_low);
        priceHigh = dialog.findViewById(R.id.price_high);
        departureEarly = dialog.findViewById(R.id.dep_earliest);
        departureLater = dialog.findViewById(R.id.dep_latest);
        arrivalEarly = dialog.findViewById(R.id.arri_earliest);
        arrivalLater = dialog.findViewById(R.id.arri_latest);
        startingFlight = dialog.findViewById(R.id.starting_flight);
        returnFlight = dialog.findViewById(R.id.return_flight);
        refundableRadio = dialog.findViewById(R.id.refundable);
        nonRefundableRadio = dialog.findViewById(R.id.non_refundable);

        depLay = dialog.findViewById(R.id.dep_lay);
        arriLay = dialog.findViewById(R.id.arrival_lay);
        durationLay = dialog.findViewById(R.id.duration_lay);
        fareLayout = dialog.findViewById(R.id.fare_layout);

        startingFlight.setText("Onward Flight");
        returnFlight.setText("Return Flight");

        if(type.equals("MC")){
            depLay.setVisibility(View.GONE);
            arriLay.setVisibility(View.GONE);
            durationLay.setVisibility(View.GONE);
        }else {
            durationLay.setVisibility(View.GONE);
        }

        fareLayout.setVisibility(View.VISIBLE);

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
        if(refundable){
            refundableRadio.setChecked(true);
        }
        if(nonRefundable){
            nonRefundableRadio.setChecked(true);
        }
        priceLow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //sortByPrice(1);
                if(!b)
                {
                    low_price=false;
                }
                if(b)
                {
                    departure_group.clearCheck();
                    arrival_group.clearCheck();
                    duration_group.clearCheck();
                    fareGroup.clearCheck();
                }
            }
        });

        priceHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //sortByPrice(2);
                if(!b)
                {
                    high_price=false;
                }
                if(b)
                {
                    departure_group.clearCheck();
                    arrival_group.clearCheck();
                    duration_group.clearCheck();
                    fareGroup.clearCheck();
                }
            }
        });

        departureEarly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //departureSort(1);
                if(!b)
                {
                    earliest=false;
                }
                if(b)
                {
                    price_group.clearCheck();
                    arrival_group.clearCheck();
                    duration_group.clearCheck();
                    fareGroup.clearCheck();
                }
            }
        });

        departureLater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //departureSort(2);
                if(!b)
                {
                    latest=false;
                }
                if(b)
                {
                    price_group.clearCheck();
                    arrival_group.clearCheck();
                    duration_group.clearCheck();
                    fareGroup.clearCheck();
                }
            }
        });

        arrivalEarly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //arrivalSort(1);
                if(!b)
                {
                    arr_earliest=false;
                }
                if(b)
                {
                    price_group.clearCheck();
                    departure_group.clearCheck();
                    duration_group.clearCheck();
                    fareGroup.clearCheck();
                }
            }
        });

        arrivalLater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //arrivalSort(2);
                if(!b)
                {
                    arr_latest=false;
                }
                if(b)
                {
                    price_group.clearCheck();
                    departure_group.clearCheck();
                    duration_group.clearCheck();
                    fareGroup.clearCheck();
                }
            }
        });

        refundableRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    refundable = false;
                }else {
                    price_group.clearCheck();
                    departure_group.clearCheck();
                    duration_group.clearCheck();
                    arrival_group.clearCheck();
                }
            }
        });

        nonRefundableRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    nonRefundable = false;
                }else {
                    price_group.clearCheck();
                    departure_group.clearCheck();
                    duration_group.clearCheck();
                    arrival_group.clearCheck();
                }
            }
        });

        TextView applySort = dialog.findViewById(R.id.apply_sort);
        applySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(priceLow.isChecked()){
                    low_price=true;
                    sortByPrice(1);
                }else if(priceHigh.isChecked()){
                    high_price=true;
                    sortByPrice(2);
                }else if(departureEarly.isChecked()){
                    earliest=true;
                    departureSort(1);
                }else if(departureLater.isChecked()){
                    latest=true;
                    departureSort(2);
                }else if(arrivalEarly.isChecked()){
                    arr_earliest=true;
                    arrivalSort(1);
                }else if(arrivalLater.isChecked()){
                    arr_latest=true;
                    arrivalSort(2);
                }else if(refundableRadio.isChecked()){
                    refundable = true;
                    sortFareGroup(1);
                }else if(nonRefundableRadio.isChecked()){
                    nonRefundable = true;
                    sortFareGroup(2);
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
                refundable = false;
                nonRefundable = false;
                multiCityParsing.clear();
                multiCityParsing.addAll(backupList);
                sortByPrice(1);
                dialog.dismiss();
            }
        });

        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    private void sortFareGroup(final Integer i) {

        tempMultiCityParsing.clear();
        tempMultiCityParsing.addAll(backupList);
        multiCityParsing.clear();

        int m=0;
        while (m < tempMultiCityParsing.size()){
            Integer k=0;
            if(tempMultiCityParsing.get(m).getFlightIsRefundable().equals("Refundable")){
                k = 1;
            }else {
                k =2;
            }

            if(i == k){
                multiCityParsing.add(tempMultiCityParsing.get(m));
            }
            m++;
        }

        if(multiCityParsing.size() == 0){
            commonUtils.toastShort("No Flight's Found", getActivity());
        }
        multiCityMain.notifyDataSetChanged();
    }

    private void sortByPrice(int i) {
        Collections.sort(multiCityParsing, new Comparator<MultiCityParsing>() {
            @Override
            public int compare(MultiCityParsing multiCityParsing, MultiCityParsing t1) {
                Double p1 = Double.parseDouble(multiCityParsing.getPriceValue().split(" ")[1]);
                Double p2 = Double.parseDouble(t1.getPriceValue().split(" ")[1]);
                return p1.compareTo(p2);
            }
        });
        notifyAdapter(i);
    }

    private void notifyAdapter(int i) {
        switch (i){
            case 2:
                Collections.reverse(multiCityParsing);
                break;
        }
        multiCityMain.notifyDataSetChanged();
    }

    private void departureSort(int i) {
        Collections.sort(multiCityParsing, new Comparator<MultiCityParsing>() {
            @Override
            public int compare(MultiCityParsing flightDetailList, MultiCityParsing t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date1 = null, date2 = null;
                try {
                    date1 = sdf.parse(flightDetailList.getFlightDetailLists().get(0).getFlightDepartureTime());
                    date2 = sdf.parse(t1.getFlightDetailLists().get(0).getFlightDepartureTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
        notifyAdapter(i);
    }

    private void arrivalSort(int i) {
        Collections.sort(multiCityParsing, new Comparator<MultiCityParsing>() {
            @Override
            public int compare(MultiCityParsing flightDetailList, MultiCityParsing t1) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date date1 = null, date2 = null;
                try {
                    date1 = sdf.parse(flightDetailList.getFlightDetailLists().get(1).getFlightDepartureTime());
                    date2 = sdf.parse(t1.getFlightDetailLists().get(1).getFlightDepartureTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            }
        });
        notifyAdapter(i);
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