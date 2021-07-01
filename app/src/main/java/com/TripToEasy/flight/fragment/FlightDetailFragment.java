package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.flight.adapter.FlightSegmentAdapter;
import com.TripToEasy.model.parsingModel.flight.FlightSegment;
import com.TripToEasy.traveller.TravellerFragment;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class FlightDetailFragment extends BaseFragment implements WebInterface{

    @BindView(R.id.segment_list)
    RecyclerView segmentListView;

    @BindView(R.id.from_station)
    TextView departureText;

    @BindView(R.id.to_destination)
    TextView destinationText;

    @BindView(R.id.traveller_count)
    TextView travellerCount;

    @BindView(R.id.travel_date)
    TextView travel_date;

    @BindView(R.id.return_date)
    TextView travelReturnDate;

    @BindView(R.id.adult_base_fare)
    TextView adultPriceText;

    @BindView(R.id.child_base_fare)
    TextView childPriceText;

    @BindView(R.id.infant_base_fare)
    TextView infantPriceText;

    @BindView(R.id.adult_view)
    RelativeLayout adultView;

    @BindView(R.id.child_view)
    RelativeLayout childView;

    @BindView(R.id.infant_view)
    RelativeLayout infantView;

    @BindView(R.id.tax_price)
    TextView taxPriceText;

    @BindView(R.id.convenience_fee)
    TextView convenienceFeeText;

    @BindView(R.id.grand_total_price)
    TextView grandTotalOne;

    @BindView(R.id.total_price)
    TextView grandTotalTwo;

    @BindView(R.id.adult_count)
    TextView adultCountText;

    @BindView(R.id.child_count)
    TextView childCountText;

    @BindView(R.id.infant_count)
    TextView infantCountText;

    @OnClick(R.id.book_now)
    void bookNow(){
        Gson gson = new Gson();
        RequestParams requestParams = new RequestParams();
        String parsingData;
        String[] arrToken,arrTokenKey,arrProvabAuthKey,promoplantype,arrIsLcc;
        if(return_token.equals("NA")){
            arrToken= new String[]{token};
        }
        else {
            arrToken= new String[]{token, return_token};
        }
        if(return_authKey.equals("NA")){
            arrProvabAuthKey= new String[]{authKey};
        }
        else {
            arrProvabAuthKey= new String[]{authKey, return_authKey};
        }
        if(return_tokenKey.equals("NA")){
            arrTokenKey= new String[]{tokenKey};
        }
        else {
            arrTokenKey= new String[]{tokenKey, return_tokenKey};
        }
        if(return_bookingSource.equals("NA")){
            promoplantype= new String[]{bookingSource};
            arrIsLcc= new String[]{""};
        }
        else {
            promoplantype= new String[]{bookingSource, return_bookingSource};
            arrIsLcc= new String[]{"", ""};
        }

        HashMap param=new HashMap();
        param.put("is_domestic", Global.is_domestic_flag);
        param.put("token",arrToken);
        param.put("token_key",arrTokenKey);
        param.put("search_access_key",arrProvabAuthKey);
        param.put("is_lcc",arrIsLcc);
        param.put("promotional_plan_type",promoplantype);
        param.put("booking_type","process_fare_quote");
        param.put("booking_source",bookingSource);
        param.put("search_id", Global.search_id);

        parsingData = gson.toJson(param);
        requestParams.put("farequote_data",parsingData);

        webServiceController.postRequest(apiConstants.FARE_QUOTE_UPDATE,requestParams,1);
    }

    String tax = null, adultPrice = null, childPrice = null,
            infantPrice = null, authKey, bookingSource, token, tokenKey,
            return_authKey, return_bookingSource, return_token,
            return_tokenKey,returnDate=null,departureDate=null,
            source = null, destination = null, detailResponse = null,
            totalPrice, airlineUrl, detailResponseTwo= null,diff_amount=null;
    int adultCount = 0, childCount = 0, infantCount = 0;
    List<FlightSegment> flightSegments = new ArrayList<FlightSegment>();
    FlightSegmentAdapter flightSegmentAdapter;
    WebServiceController webServiceController;

    @SuppressLint("ValidFragment")
    public FlightDetailFragment(String tax, String adultPrice, String childPrice,
                                String infantPrice, String authKey,
                                String bookingSource, String token, String tokenKey,
                                String return_authKey,String return_bookingSource, String return_token,String return_tokenKey,
                                String source,
                                String destination, String departureDate,String returnDate,int adultCount, int childCount,
                                int infantCount, String detailResponse,
                                String totalPrice, String airlineUrl,
                                String detailResponseTwo,
                                String diff_amount) {
        this.tax = tax;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
        this.authKey = authKey;
        this.bookingSource = bookingSource;
        this.token = token;
        this.tokenKey = tokenKey;
        this.return_authKey = return_authKey;
        this.return_bookingSource = return_bookingSource;
        this.return_token = return_token;
        this.return_tokenKey = return_tokenKey;
        this.source = source;
        this.destination = destination;
        this.departureDate=departureDate;
        this.returnDate=returnDate;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.infantCount = infantCount;
        this.detailResponse = detailResponse;
        this.totalPrice = totalPrice;
        this.airlineUrl = airlineUrl;
        this.detailResponseTwo = detailResponseTwo;
        this.diff_amount = diff_amount;
    }

    public FlightDetailFragment(){
        /**
         * Empty Constructor
         * */
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.flight_detail_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(), FlightDetailFragment.this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(adultCount != 0){
            adultView.setVisibility(View.VISIBLE);
            adultCountText.setText(String.valueOf(adultCount)+" AD");
            //adultPriceText.setText("Rs. "+String.format("%.2f",Double.parseDouble(adultPrice)));
            adultPriceText.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(adultPrice)/Double.parseDouble(Global.currencyValue))));

        }else {
            adultView.setVisibility(View.GONE);
        }

        if(childCount != 0){
            childView.setVisibility(View.VISIBLE);
            childCountText.setText(String.valueOf(childCount)+" CH");
           // childPriceText.setText("Rs. "+String.format("%.2f",Double.parseDouble(childPrice)));
            childPriceText.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(childPrice)/Double.parseDouble(Global.currencyValue))));

        }else {
            childView.setVisibility(View.GONE);
        }

        if(infantCount != 0){
            infantView.setVisibility(View.VISIBLE);
            infantCountText.setText(String.valueOf(infantCount)+" IF");
            //infantPriceText.setText("Rs. "+String.format("%.2f",Double.parseDouble(infantPrice)));
            infantPriceText.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(infantPrice)/Double.parseDouble(Global.currencyValue))));

        }else {
            infantView.setVisibility(View.GONE);
        }

        travel_date.setText(departureDate);
        if(!returnDate.equals("NA")){
            travelReturnDate.setVisibility(View.VISIBLE);
            travelReturnDate.setText(returnDate);
        }

        departureText.setText(source);
        destinationText.setText(destination);
        travellerCount.setText(String.valueOf(adultCount+childCount+infantCount)+" Travellers");

        /*convenienceFeeText.setText("Rs. "+String.format("%.2f",Double.parseDouble(diff_amount)));
        taxPriceText.setText("Rs. "+String.format("%.2f",Double.parseDouble(tax)));
        grandTotalOne.setText("Rs. "+String.format("%.2f",Double.parseDouble(totalPrice)));
        grandTotalTwo.setText("Rs. "+String.format("%.2f",Double.parseDouble(totalPrice)));*/
        //convenienceFeeText.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(diff_amount)/Double.parseDouble(Global.currencyValue))));
        taxPriceText.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(tax)/Double.parseDouble(Global.currencyValue))));
        grandTotalOne.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(totalPrice)/Double.parseDouble(Global.currencyValue))));
        grandTotalTwo.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(totalPrice)/Double.parseDouble(Global.currencyValue))));

        commonUtils.linearLayout(segmentListView,getActivity());
        flightSegmentAdapter = new FlightSegmentAdapter(getActivity(), flightSegments);
        segmentListView.setAdapter(flightSegmentAdapter);

        lodeSegmentDetails();
    }

    private void lodeSegmentDetails() {
        Thread segmentDetails = new Thread(){
            @Override
            public void run() {
                try{
                    JSONArray dataArrayMain = new JSONArray(detailResponse);
                    int k=0;
                    while (k < dataArrayMain.length()){
                        JSONArray dataArray = dataArrayMain.getJSONArray(k);
                        int i=0;
                        while (i < dataArray.length()){
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            flightSegments.add(new FlightSegment(
                                    airlineUrl+dataObject.getString("Airline_AirlineCode")+".gif",
                                    dataObject.getString("Airline_AirlineName"),
                                    dataObject.getString("Origin_AirportCode"),
                                    dataObject.getString("Origin_AirportName"),
                                    dataObject.getString("Destination_AirportCode"),
                                    dataObject.getString("Destination_AirportName"),
                                    dataObject.getString("Origin_DepartureTime"),
                                    dataObject.getString("Destination_ArrivalTime"),
                                    dataObject.getString("SegmentDuration"),
                                    dataObject.getString("Origin_DateTime"),
                                    dataObject.getString("Destination_DateTime"),
                                    dataObject.getString("Airline_AirlineCode")
                                            +" "+ dataObject.getString("Airline_FlightNumber")));
                            i++;
                        }
                        k++;
                    }

                    //FOR ONLY ROUND TRIP DOMESTIC FLIGHT
                    if(detailResponseTwo != null){
                        JSONArray dataArrayMain2 = new JSONArray(detailResponseTwo);
                        int m=0;
                        while (m < dataArrayMain2.length()){
                            JSONArray dataArray = dataArrayMain2.getJSONArray(m);
                            int i=0;
                            while (i < dataArray.length()){
                                JSONObject dataObject = dataArray.getJSONObject(i);
                                flightSegments.add(new FlightSegment(
                                        airlineUrl+dataObject.getString("Airline_AirlineCode")+".gif",
                                        dataObject.getString("Airline_AirlineName"),
                                        dataObject.getString("Origin_AirportCode"),
                                        dataObject.getString("Origin_AirportName"),
                                        dataObject.getString("Destination_AirportCode"),
                                        dataObject.getString("Destination_AirportName"),
                                        dataObject.getString("Origin_DepartureTime"),
                                        dataObject.getString("Destination_ArrivalTime"),
                                        dataObject.getString("SegmentDuration"),
                                        dataObject.getString("Origin_DateTime"),
                                        dataObject.getString("Destination_DateTime"),
                                        dataObject.getString("Airline_AirlineCode")
                                                +" "+ dataObject.getString("Airline_FlightNumber")));
                                i++;
                            }
                            m++;
                        }
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            flightSegmentAdapter.notifyDataSetChanged();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        segmentDetails.start();
    }

    @Override
    public void getResponse(String response, int flag) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getInt("status")==1)
            {
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.support_frame,
                        new TravellerFragment(adultCount, childCount, infantCount,
                                totalPrice, response,1), null, true);

            }
            else {
                commonUtils.toastShort("Unable to proceed, please try again",getActivity());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
