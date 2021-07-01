package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.TripToEasy.model.parsingModel.bus.BusOperatorsInfo;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.interfaces.ProfileFilterNotify;
import com.TripToEasy.main.activity.MainActivity;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.flight.activity.FlightSupportActivity;
import com.TripToEasy.flight.adapter.OneWayAdapter;
import com.TripToEasy.interfaces.FilterNotify;
import com.TripToEasy.model.parsingModel.flight.FlightList;
import com.TripToEasy.model.parsingModel.flight.OneWayList;
import com.TripToEasy.model.requestModel.FairRule;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.ProgressLoader;
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


public class OneWayFragment extends BaseFragment implements FilterNotify,
        WebInterface, ProfileFilterNotify {

    @BindView(R.id.one_way_list)
    RecyclerView oneWayFlight;

    @BindView(R.id.from_station)
    TextView fromStation;

    @BindView(R.id.to_destination)
    TextView toStation;

    @BindView(R.id.traveller_count)
    TextView travellerCount;

    @BindView(R.id.travel_date)
    TextView travelDate;

    @OnClick(R.id.filter_action)
    void filterAction(){
        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                new FilterFragment(airlineList, priceBreakup, this,
                        flightLists, minPrice, maxPrice, stopList, departureList,
                        arrivalList, false),null, true);
    }

    String response, airlineList, priceBreakup, minPrice = null,
            maxPrice = null, departureTime = null,
            arrivalTime = null, catchFileName = null, detailResponse = null,
            departureDate, cabinClass;
    boolean low_price,high_price,earliest,latest,arr_earliest,arr_latest,fastest,
            slow, refundable, nonRefundable;
    List<OneWayList> oneWayLists = new ArrayList<OneWayList>();
    List<OneWayList> backupList = new ArrayList<OneWayList>();
    List<OneWayList> tempOneWayLists = new ArrayList<OneWayList>();
    List<FlightList> flightLists = new ArrayList<FlightList>();
    List<Integer> stopList = new ArrayList<Integer>();
    List<Integer> departureList = new ArrayList<Integer>();
    List<Integer> arrivalList = new ArrayList<Integer>();

    OneWayAdapter oneWayAdapter;
    WebServiceController webServiceController;
    ProgressLoader progressLoader;

    ImageView action_type;

    @SuppressLint("ValidFragment")
    public OneWayFragment(String response, String catchFileName,
                          String departureDate, String cabinClass) {
        this.response = response;
        this.catchFileName = catchFileName;
        this.departureDate = departureDate;
        this.cabinClass = cabinClass;
    }

    public OneWayFragment()
    {
        }

    @Override
    protected int getLayoutResource() {
        return R.layout.one_way_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(), OneWayFragment.this);
        progressLoader = new ProgressLoader();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(oneWayFlight,getActivity());
        action_type = getActivity().findViewById(R.id.action_type);
        action_type.setVisibility(View.VISIBLE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        travelDate.setText(departureDate);

        lodeData();
        lodeDetailData();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).toolbarActionIcon(OneWayFragment.this, 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).toolbarActionIcon(OneWayFragment.this, 0);
    }

    private void lodeDetailData() {

        RequestParams requestParams = new RequestParams();
        requestParams.put("search_key", catchFileName);
        WebServiceController webServiceController = new
                WebServiceController(getActivity(),OneWayFragment.this);
        webServiceController.postRequest(apiConstants.FLIGHT_DETAIL, requestParams, 9999);

    }

    private void lodeData() {
        final Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("Flight Response is : ",response);

                    airlineList = jsonObject.getJSONArray("airline_list").toString();
                    priceBreakup = jsonObject.getJSONObject("price").toString();

                    JSONObject journeySummary = jsonObject.getJSONObject("JourneySummary");
                    final String from = journeySummary.getString("Origin");
                    final String to = journeySummary.getString("Destination");

                    JSONObject traveller = journeySummary.getJSONObject("PassengerConfig");
                    final String passengerCount = String.valueOf(traveller.getInt("TotalPassenger"))+" Travellers";

                    JSONArray flightArray = jsonObject.getJSONArray("Flights");
                    JSONArray flightNextArray = flightArray.getJSONArray(0);
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
                                cabinClass
                        ));
                        i++;
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fromStation.setText(from);
                            toStation.setText(to);
                            travellerCount.setText(passengerCount);

                            backupList.clear();
                            backupList.addAll(oneWayLists);

                            oneWayAdapter = new OneWayAdapter(getActivity(),
                                    OneWayFragment.this, oneWayLists);
                            oneWayFlight.setAdapter(oneWayAdapter);
                            sortingPrice(1);
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void filterTypeOne(List<FlightList> flightLists,
                              List<Integer> stopCount,
                              List<Integer> departure,
                              List<Integer> arrival,
                              String minPrice, String maxPrice) {
        oneWayLists.clear();
        oneWayLists.addAll(backupList);

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
            sortingPrice(1);
        }else {
            if(this.flightLists.size() != 0){
                sortFlight(flightLists,minPrice,maxPrice,this.stopList,
                        this.departureList,this.arrivalList);
            }else {
                sortPrice(minPrice,maxPrice,this.stopList,
                        this.departureList,this.arrivalList, oneWayLists);
            }
        }
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


    private void sortPrice(String minPrice, String maxPrice,
                           List<Integer> stopCount,List<Integer> departure,
                           List<Integer> arrival, List<OneWayList> sortedList) {
        if(minPrice != null && maxPrice != null){
            tempOneWayLists.clear();
            tempOneWayLists.addAll(sortedList);
            oneWayLists.clear();
            int i=0;
            while (i < tempOneWayLists.size()){
                String[] flightPrice = tempOneWayLists.get(i).getFlightPrice().split(" ");
                if(Float.valueOf(flightPrice[1]) >=
                        Float.valueOf(minPrice) &&
                        Float.valueOf(flightPrice[1]) <=
                                Float.valueOf(maxPrice)){
                    oneWayLists.add(tempOneWayLists.get(i));
                }
                i++;
            }
        }else if(minPrice != null){
            tempOneWayLists.clear();
            tempOneWayLists.addAll(sortedList);
            oneWayLists.clear();
            int i=0;
            while (i < tempOneWayLists.size()){
                String[] flightPrice = tempOneWayLists.get(i).getFlightPrice().split(" ");
                if(Float.parseFloat(flightPrice[1]) >=
                        Float.parseFloat(minPrice)){
                    oneWayLists.add(tempOneWayLists.get(i));
                }
                i++;
            }
        }else if(maxPrice != null){
            tempOneWayLists.clear();
            tempOneWayLists.addAll(sortedList);
            oneWayLists.clear();
            int i=0;
            while (i < tempOneWayLists.size()){
                String[] flightPrice = tempOneWayLists.get(i).getFlightPrice().split(" ");
                if(Float.parseFloat(flightPrice[1]) <=
                                Float.parseFloat(maxPrice)){
                    oneWayLists.add(tempOneWayLists.get(i));
                }
                i++;
            }
        }

        sortStop(stopCount, departure, arrival, oneWayLists);
    }

    private void sortFlight(List<FlightList> flightLists, String minPrice,
                            String maxPrice, List<Integer> stopCount,
                            List<Integer> departure,
                            List<Integer> arrival) {
        tempOneWayLists.clear();
        tempOneWayLists.addAll(oneWayLists);
        oneWayLists.clear();

        int i=0;
        while (i < flightLists.size()){
            int j=0;
            while (j < tempOneWayLists.size()){
                String[] flightCode = tempOneWayLists.get(j).getFlightCode().split(" ");
                if(flightCode[0].equals(flightLists.get(i).getAirlineCode())){
                    oneWayLists.add(tempOneWayLists.get(j));
                }
                j++;
            }
            i++;
        }

        sortPrice(minPrice,maxPrice,stopCount,
                departure,arrival,oneWayLists);
    }

    private void sortStop(List<Integer> stopCount, List<Integer> departure,
                          List<Integer> arrival, List<OneWayList> sortedList) {
        if(stopCount.size() != 0){
            tempOneWayLists.clear();
            tempOneWayLists.addAll(sortedList);
            oneWayLists.clear();

            int j=0;
            while (j < stopCount.size()){
                int i=0;
                while (i < tempOneWayLists.size()){
                    String[] stop = tempOneWayLists.get(i).getFlightStopCount().split(" ");
                    if(Integer.valueOf(stop[0]) == stopCount.get(j)){
                        oneWayLists.add(tempOneWayLists.get(i));
                    }
                    i++;
                }
                j++;
            }
        }

        sortDepartureTime(departure, arrival, oneWayLists);
    }

    private void sortDepartureTime(List<Integer> departure,
                                   List<Integer> arrival,
                                   List<OneWayList> sortedList) {
        if(departure.size() != 0){
            tempOneWayLists.clear();
            tempOneWayLists.addAll(sortedList);
            oneWayLists.clear();
            int j=0;
            while (j < departure.size()){
                departureTime = String.valueOf(departure.get(j));
                int i=0;
                while (i < tempOneWayLists.size()){
                    String[] time = tempOneWayLists.get(i).getFlightDepartureTime().split(":");
                    switch (departureTime){
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

        sortArrivalTime(arrival, oneWayLists);
    }

    private void sortArrivalTime(List<Integer> arrival,
                                 List<OneWayList> sortedList) {
        if(arrival.size() != 0){
            tempOneWayLists.clear();
            tempOneWayLists.addAll(sortedList);
            oneWayLists.clear();

            int j=0;
            while (j < arrival.size()){
                arrivalTime = String.valueOf(arrival.get(j));
                int i=0;
                while (i < tempOneWayLists.size()){
                    String[] time = tempOneWayLists.get(i).getFlightArrivalTime().split(":");
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

        if(oneWayLists.size() != 0){
            oneWayAdapter.notifyDataSetChanged();
        }else {
            commonUtils.toastShort("No Flight's Found.",getActivity());
            oneWayAdapter.notifyDataSetChanged();
        }
    }

    private void sortDataTime(int minTime, int maxTime, int value, int position) {
        if(value >= minTime && value <= maxTime){
            oneWayLists.add(tempOneWayLists.get(position));
        }
    }

    public void callDetailFragment(String provabAuthKey,
                                   String bookingSource,
                                   String token,
                                   String tokenKey,
                                   int position) {

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
            /*    if(adultObj.getDouble("BaseFare")<adultObj.getDouble("Tax"))
                {
                    adultPrice = String.valueOf(adultObj.getDouble("Tax"));
                }else
                {*/
                    adultPrice = String.valueOf(adultObj.getDouble("BaseFare"));
              //  }
                adultCount = adultObj.getInt("PassengerCount");
            }

            if(passengerFair.has("CHD")){
                JSONObject childObj = passengerFair.getJSONObject("CHD");
               /* if(childObj.getDouble("BaseFare")<childObj.getDouble("Tax"))
                {
                    childPrice = String.valueOf(childObj.getDouble("Tax"));
                }else
                {*/
                    childPrice = String.valueOf(childObj.getDouble("BaseFare"));
              //  }
                childCount = childObj.getInt("PassengerCount");
            }

            if(passengerFair.has("INF")){
                JSONObject infantObj = passengerFair.getJSONObject("INF");
             /*   if(infantObj.getDouble("BaseFare")<infantObj.getDouble("Tax"))
                {
                    infantPrice = String.valueOf(infantObj.getDouble("Tax"));
                }else
                {*/
                    infantPrice = String.valueOf(infantObj.getDouble("BaseFare"));
              //  }
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
        bundle.putString("return_date", "NA");
        bundle.putString("token",token);
        bundle.putString("tokenKey",tokenKey);
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
    public void notifyFilter() {
        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                new FilterFragment(airlineList, priceBreakup, this,
                        flightLists, minPrice, maxPrice, stopList, departureList,
                        arrivalList, false),null, true);
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
        //asdsdad
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.flight_sort_fragment);
        dialog.setCancelable(true);

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
        TextView applySort = dialog.findViewById(R.id.apply_sort);
        applySort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(priceLow.isChecked()){
                    low_price=true;
                    sortingPrice(1);
                }else if(priceHigh.isChecked()){
                    high_price=true;
                    sortingPrice(2);
                }else if(departureEarly.isChecked()){
                    earliest=true;
                    sortingTimeEar(1);
                }else if(departureLater.isChecked()){
                    latest=true;
                    sortingTimeEar(2);
                }else if(arrivalEarly.isChecked()){
                    arr_earliest=true;
                    sortTimeArri(1);
                }else if(arrivalLater.isChecked()){
                    arr_latest=true;
                    sortTimeArri(2);
                }else if(fastFlight.isChecked()){
                    fastest=true;
                    sortFastest(1);
                }else if(slowFlight.isChecked()){
                    slow=true;
                    sortFastest(2);
                }else if(refundableFlight.isChecked()){
                    refundable = true;
                    sortFareGroup(1);
                }else if(nonRefundableFlight.isChecked()){
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
                refundable=false;
                nonRefundable=false;
                oneWayLists.clear();
                oneWayLists.addAll(backupList);
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


                //sortingPrice(1);
                //dialog.dismiss();
            }
        });

        priceHigh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b)
                {
                    high_price=false;
                }

                //sortingPrice(2);
                //dialog.dismiss();
            }
        });


        departureEarly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    earliest=false;
                }

                //sortingTimeEar(1);
                //dialog.dismiss();
            }
        });


        departureLater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    latest=false;
                }

                //sortingTimeEar(2);
                //dialog.dismiss();
            }
        });

        arrivalEarly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    arr_earliest=false;
                }

                //sortTimeArri(1);
                //dialog.dismiss();
            }
        });

        arrivalLater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    arr_latest=false;
                }

                //sortTimeArri(2);
               // dialog.dismiss();
            }
        });

        fastFlight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(!b)
                {
                    fastest=false;
                }

                //sortFastest(1);
                //dialog.dismiss();
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

    private void sortFareGroup(Integer i) {
        tempOneWayLists.clear();
        tempOneWayLists.addAll(backupList);
        oneWayLists.clear();

        int m=0;
        while (m<tempOneWayLists.size()){
            Integer k=0;
            if(tempOneWayLists.get(m).getFlightIsRefundable().equals("Refundable")){
                k = 1;
            }else {
                k =2;
            }

            if(i == k){
                oneWayLists.add(tempOneWayLists.get(m));
            }
            m++;
        }

        if(oneWayLists.size() == 0){
            commonUtils.toastShort("No Flight's Found", getActivity());
        }

        oneWayAdapter.notifyDataSetChanged();
    }

    /**
     * soring action and methods
     * */

    /**
     * 1 - low to high
     * 2 - high to low
     * */
    private void sortingPrice(int i) {
        Collections.sort(oneWayLists, new Comparator<OneWayList>() {
            @Override
            public int compare(OneWayList oneWayList, OneWayList t1) {
                Double p1 = Double.parseDouble(oneWayList.getFlightPrice().split(" ")[1]);
                Double p2 = Double.parseDouble(t1.getFlightPrice().split(" ")[1]);
                return p1.compareTo(p2);
            }
        });
        switch (i){
            case 2:
                Collections.reverse(oneWayLists);
                break;
        }
        oneWayAdapter.notifyDataSetChanged();
    }

    private void sortingTimeEar(int i) {
        Collections.sort(oneWayLists, new Comparator<OneWayList>() {
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
                Collections.reverse(oneWayLists);
                break;
        }
        oneWayAdapter.notifyDataSetChanged();
    }

    private void sortTimeArri(int i) {
        Collections.sort(oneWayLists, new Comparator<OneWayList>() {
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
                Collections.reverse(oneWayLists);
                break;
        }
        oneWayAdapter.notifyDataSetChanged();
    }

    private void sortFastest(int i) {
        Collections.sort(oneWayLists, new Comparator<OneWayList>() {
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
                Collections.reverse(oneWayLists);
                break;
        }
        oneWayAdapter.notifyDataSetChanged();
    }

    public void callFairRule(String provabAuthKey, String token) {
        progressLoader.ShowProgress(getActivity());
        FairRule fairRule = new FairRule(token,
                "PTBSID0000000002", provabAuthKey);
        String param = gson.toJson(fairRule);
        RequestParams requestParams = new RequestParams();
        requestParams.put("farerules", param);
        webServiceController.postRequest(apiConstants.FAIR_RULE, requestParams, 2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        action_type.setVisibility(View.GONE);
    }
}