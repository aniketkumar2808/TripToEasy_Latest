package com.TripToEasy.main.fragment;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.flight.adapter.ReviewSegmentAdapter;
import com.TripToEasy.flight.fragment.PaymentFlight;
import com.TripToEasy.main.adapter.PassangerListAdapter;
import com.TripToEasy.model.parsingModel.flight.TicketPreviewSegment;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketPreviewFragment extends BaseFragment implements WebInterface{

    String flightResponse;
    int frameType,extra_services_amount=0;
    String payment_gateway_url="";
    WebServiceController wsc;
    RequestParams params=new RequestParams();

    @BindView(R.id.frame_flight)
    RelativeLayout flight_layout;


    @BindView(R.id.frame_hotel)
    RelativeLayout hotel_layout;


    @BindView(R.id.frame_bus)
    RelativeLayout bus_layout;

    @BindView(R.id.tv_email)
    TextView tv_email;

    @BindView(R.id.tv_mobile)
    TextView tv_mobile;

    @BindView(R.id.tv_basefare)
    TextView tv_flightBaseFare;

    @BindView(R.id.tv_taxes)
    TextView tv_flightTaxes;

    @BindView(R.id.tv_confee)
    TextView tv_flightConfee;

    @BindView(R.id.tv_meals)
    TextView tv_flightMeals;

    @BindView(R.id.tv_gst) TextView tv_gst;


/*    @BindView(R.id.tv_extra_bag)
    TextView tv_extra_bag;

    @BindView(R.id.tv_seat_selection)
    TextView tv_flightseat_selection;*/

    @BindView(R.id.tv_total)
    TextView tv_flight_total;

    @BindView(R.id.check_in_date)
    TextView tv_check_in_date;

    @BindView(R.id.check_out_date)
    TextView tv_check_out_date;

    @BindView(R.id.tv_no_of_rooms)
    TextView tv_no_of_rooms;

    @BindView(R.id.tv_hotel_name)
    TextView tv_hotel_name;

    @BindView(R.id.tv_hotel_addr)
    TextView tv_hotel_addr;

    @BindView(R.id.tv_room_type)
    TextView tv_room_type;

    @BindView(R.id.tv_room_total)
    TextView tv_room_total;

    @BindView(R.id.tv_room_tax)
    TextView tv_room_tax;

    @BindView(R.id.tv_room_con_fee)
    TextView tv_room_con_fee;

    @BindView(R.id.tv_hotel_grand_total)
    TextView tv_hotel_grand_total;

    @BindView(R.id.bus_from_city)
    TextView bus_from_city;

    @BindView(R.id.bus_departure_date)
    TextView bus_departure_date;

    @BindView(R.id.bus_departure_time)
    TextView bus_departure_time;

    @BindView(R.id.bus_to_city)
    TextView bus_to_city;

    @BindView(R.id.bus_arrival_date)
    TextView bus_arrival_date;

    @BindView(R.id.bus_arrival_time)
    TextView bus_arrival_time;

    @BindView(R.id.bus_operator)
    TextView bus_operator;

    @BindView(R.id.total_fare)
    TextView total_fare;

    @BindView(R.id.seats_number)
    TextView seats_number;

    @BindView(R.id.tv_no_of_seats)
    TextView tv_no_of_seats;

    @BindView(R.id.tv_hotelDiscount)
    TextView tv_hotelDiscount;

    @BindView(R.id.tv_discount)
    TextView tv_discount;

    @BindView(R.id.rv_passangers)
    RecyclerView rv_passangers;

    @BindView(R.id.rv_segments)
    RecyclerView rv_segments;

    @OnClick(R.id.proceed)
    void proceedToPay()
    {
       // commonUtils.toastShort("Cooming Soon",getActivity());
        if(frameType==1)
        {
            if(payment_gateway_url.equals(""))
            {
                Log.e("fl_pg_params",params.toString());
                wsc.postRequest(apiConstants.FLIGHT_BOOKING_URL,params,1);
            }else {
                   intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.support_frame, new PaymentFlight(payment_gateway_url), null, false);
            }

        }else if(frameType==2){
            Log.e("fl_pg_params",params.toString());

            wsc.postRequest(apiConstants.HOTEL_ROOM_PRE_BOOK,params,2);
        }
        else if(frameType==3){
            Log.e("fl_pg_params",params.toString());
            wsc.postRequest(apiConstants.BUS_PRE_BOOK,params,3);
        }
    }


    public TicketPreviewFragment() {
        // Required empty public constructor
    }
    String passengers,mobile,email;
    String discountValue="0.00";
    @SuppressLint("ValidFragment")
    public TicketPreviewFragment(String flightRespnse,String passengers, RequestParams params,String discountValue,int frameType) {
        // Required empty public constructor

        this.flightResponse=flightRespnse;
        this.params=params;
        this.frameType=frameType;
        this.passengers=passengers;
        this.discountValue=discountValue;
    }

    @SuppressLint("ValidFragment")
    public TicketPreviewFragment(int extra_services_amount,String payment_gateway_url,String flightRespnse,String passengers,String discountValue,int frameType) {
        // Required empty public constructor
        this.extra_services_amount=extra_services_amount;
        this.payment_gateway_url=payment_gateway_url;
        this.flightResponse=flightRespnse;
        this.passengers=passengers;
        this.frameType=frameType;
        this.discountValue=discountValue;
    }

    int seat_count=0;
    String bus_seatValue="",arrival_date="";
    List<String> passangerList=new ArrayList<String>();
    List<TicketPreviewSegment> segmentList=new ArrayList<TicketPreviewSegment>();
    ReviewSegmentAdapter segmentAdapter;
    @SuppressLint("ValidFragment")
    public TicketPreviewFragment(String flightRespnse, RequestParams params,int seat_count,String bus_seatValue,
                                 String arrival_date,String email,String mobile,List<String> passangerList,
                                 int frameType) {
        //for bus booking
        this.arrival_date=arrival_date;
        this.bus_seatValue=bus_seatValue;
        this.seat_count=seat_count;
        this.flightResponse=flightRespnse;
        this.params=params;
        this.email=email;
        this.mobile=mobile;
        this.frameType=frameType;
        this.passangerList.clear();
        this.passangerList.addAll(passangerList);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_ticket_preview;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_preview, container, false);
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wsc = new WebServiceController(getActivity(),TicketPreviewFragment.this);
        commonUtils.linearLayout(rv_passangers,getActivity());
        commonUtils.linearLayout(rv_segments,getActivity());

        if(frameType==1||frameType==2) {
            passangerList.clear();
            try {
                JSONObject jsonObject = new JSONObject(passengers);
                email = jsonObject.getString("Email");
                mobile = jsonObject.getString("ContactNo");
                JSONArray passArr = jsonObject.getJSONArray("Passengers");
                for (int i = 0; i < passArr.length(); i++) {
                    passangerList.add(passArr.getJSONObject(i).getString("FirstName") + " " +
                            passArr.getJSONObject(i).getString("LastName"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        PassangerListAdapter pAdapter = new PassangerListAdapter(
                getActivity(), passangerList);
        rv_passangers.setAdapter(pAdapter);


        tv_email.setText(email);
        tv_mobile.setText(mobile);
        if(frameType==1){
            flight_layout.setVisibility(View.VISIBLE);
            segmentAdapter = new ReviewSegmentAdapter(
                    getActivity(), segmentList);
            rv_segments.setAdapter(segmentAdapter);

            loadFlightDetails();
        }
        if(frameType==2){
            hotel_layout.setVisibility(View.VISIBLE);
            loadHotelDetails();
        }
        if(frameType==3){
            bus_layout.setVisibility(View.VISIBLE);
            loadBusDetails();
        }

        //Remove KeyListener. Implement this when changing to other fragment.
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(null);
    }

    String booking_token,bookingTokenKey,bookingSearchId,flight_token_table_id,search_hash_ssr,convienceFee,
            basefare,tax,totPrice,curr_symbol,mealsPrice="0",extraBagPrice="0",flightSeatPrice="0",GST="0.00";
    public void loadFlightDetails(){
        try {
            JSONObject mainjsonObj = new JSONObject(flightResponse);
            JSONObject dataObj = mainjsonObj.getJSONObject("data");
            booking_token = dataObj.getString("token");
            bookingTokenKey = dataObj.getString("token_key");
            bookingSearchId = dataObj.getString("search_id");
            flight_token_table_id = dataObj.getString("flight_token_table_id");
            search_hash_ssr = dataObj.getString("search_hash_ssr");
            convienceFee = dataObj.getString("convenience_fees");

            JSONObject preBookObj = dataObj.getJSONObject("pre_booking_summery");
            JSONObject fareDetailObj = preBookObj.getJSONObject("FareDetails");
            JSONObject priceDetailObj = fareDetailObj.getJSONObject("b2c_PriceDetails");
            basefare = priceDetailObj.getString("BaseFare");
            tax = priceDetailObj.getString("TotalTax");
            GST = priceDetailObj.getString("GST");

            totPrice = priceDetailObj.getString("TotalFare");
            curr_symbol = priceDetailObj.getString("CurrencySymbol");
            segmentList.clear();
            JSONArray segmentArr=preBookObj.getJSONArray("SegmentSummary");
            for (int i=0;i<segmentArr.length();i++){
                segmentList.add(new TicketPreviewSegment(
                    segmentArr.getJSONObject(i).getJSONObject("AirlineDetails").getString("AirlineName"),
                        segmentArr.getJSONObject(i).getJSONObject("OriginDetails").getString("AirportCode"),
                        segmentArr.getJSONObject(i).getJSONObject("OriginDetails").getString("CityName"),
                        segmentArr.getJSONObject(i).getJSONObject("DestinationDetails").getString("AirportCode"),
                        segmentArr.getJSONObject(i).getJSONObject("DestinationDetails").getString("CityName"),
                        segmentArr.getJSONObject(i).getJSONObject("OriginDetails").getString("DepartureTime"),
                        segmentArr.getJSONObject(i).getJSONObject("OriginDetails").getString("DepartureDate"),
                        segmentArr.getJSONObject(i).getJSONObject("DestinationDetails").getString("ArrivalTime"),
                        segmentArr.getJSONObject(i).getJSONObject("DestinationDetails").getString("ArrivalDate")
                ));
            }



            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                 /*   tv_flightBaseFare.setText(curr_symbol + " " + basefare);
                    tv_flightTaxes.setText(curr_symbol + " " + tax);
                    tv_flightConfee.setText(curr_symbol + " " + convienceFee);
                    tv_flightMeals.setText(curr_symbol + " " + extra_services_amount);
                    tv_flight_total.setText(curr_symbol + " " + (Double.parseDouble(totPrice) + Double.parseDouble(convienceFee)+extra_services_amount));
*/
                    tv_flightBaseFare.setText(Global.currencySymbol + " " + String.format("%.2f",(Double.parseDouble(basefare)/Double.parseDouble(Global.currencyValue))));
                    tv_flightTaxes.setText(Global.currencySymbol + " " + String.format("%.2f",(Double.parseDouble(tax)/Double.parseDouble(Global.currencyValue))));
                    tv_flightConfee.setText(Global.currencySymbol + " " + String.format("%.2f",(Double.parseDouble(convienceFee)/Double.parseDouble(Global.currencyValue))));
                    tv_gst.setText(Global.currencySymbol + " " + String.format("%.2f", (Double.parseDouble(GST) / Double.parseDouble(Global.currencyValue))));
                    tv_flightMeals.setText(Global.currencySymbol + " " + String.format("%.2f",(Double.parseDouble(extra_services_amount+"")/Double.parseDouble(Global.currencyValue))));
                    tv_discount.setText(Global.currencySymbol + " " +discountValue);
                    Float newPrice=Float.valueOf(totPrice)-Float.valueOf(discountValue);
                    tv_flight_total.setText(Global.currencySymbol + " " +(String.format("%.2f",(newPrice + Double.parseDouble(convienceFee)+extra_services_amount)/Double.parseDouble(Global.currencyValue))));

                    segmentAdapter.notifyDataSetChanged();
                   /* tv_extrabag_detail.setText("No Added Baggage");
                    tv_extrabag_amt.setText(curr_symbol+" "+baggageAmt);
                    tv_meal_detail.setText("No Added Meal");
                    tv_meal_amt.setText(curr_symbol+" "+mealAmount);*/
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    String hotel_checkin_date,hotel_checkout_date,hotel_room_count,hotelName,hotelAddress,roomType,currencySymbol;
    String hotel_grandtotal_price,hotel_total_price,hotel_tax,hotel_convi_fee;
    public void loadHotelDetails(){
        //loading hotel detail values
        try {
            JSONObject mainhoteljsonObj = new JSONObject(flightResponse);
            JSONObject dataObj=mainhoteljsonObj.getJSONObject("data");
            JSONObject searchdataObj=dataObj.getJSONObject("search_data");
            hotel_checkin_date=searchdataObj.getString("from_date");
            hotel_checkout_date=searchdataObj.getString("to_date");
            hotel_room_count=searchdataObj.getString("room_count");

            JSONObject prebookingObj=dataObj.getJSONObject("pre_booking_params");
            hotelName=prebookingObj.getString("HotelName");
            hotelAddress=prebookingObj.getString("HotelAddress");
            roomType=prebookingObj.getString("RoomTypeName");
            currencySymbol=prebookingObj.getString("default_currency");
             JSONArray priceTokenArr=prebookingObj.getJSONArray("price_token");
          //  hotel_total_price=priceTokenArr.getJSONObject(0).getString("RoomPrice");

            hotel_total_price=dataObj.getString("total_price");
            hotel_grandtotal_price=dataObj.getString("total_price");
            hotel_tax=dataObj.getString("tax_service_sum");
            hotel_convi_fee=dataObj.getString("convenience_fees");


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_check_in_date.setText(hotel_checkin_date);
                    tv_check_out_date.setText(hotel_checkout_date);
                    tv_no_of_rooms.setText(hotel_room_count);
                    tv_hotel_name.setText(hotelName);
                    tv_hotel_addr.setText(hotelAddress);
                    tv_room_type.setText(roomType);
                   /* tv_room_total.setText(currencySymbol+" "+(Integer.parseInt(hotel_total_price)*Integer.parseInt(hotel_room_count)));
                    tv_room_tax.setText(currencySymbol+" "+hotel_tax);
                    tv_room_con_fee.setText(currencySymbol+" "+hotel_convi_fee);
                    tv_hotel_grand_total.setText(currencySymbol+" "+(Double.parseDouble(hotel_grandtotal_price)));
           */       tv_hotelDiscount.setText(Global.currencySymbol+" "+discountValue);
                    tv_room_total.setText(Global.currencySymbol+" "+String.format("%.2f",((Double.parseDouble(hotel_total_price)/Double.parseDouble(Global.currencyValue))*Integer.parseInt(hotel_room_count))));

                    tv_room_tax.setVisibility(View.GONE);
                    tv_room_tax.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(hotel_tax)/Double.parseDouble(Global.currencyValue))));
                    tv_room_con_fee.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(hotel_convi_fee)/Double.parseDouble(Global.currencyValue))));
                    //tv_hotel_grand_total.setText(Global.currencySymbol+" "+((Double.parseDouble(String.format("%.2f",(Double.parseDouble(hotel_grandtotal_price)/Double.parseDouble(Global.currencyValue)))))));
                    tv_hotel_grand_total.setText(Global.currencySymbol+" "+String.format("%.2f",(((Double.parseDouble(hotel_grandtotal_price)+Double.parseDouble(hotel_convi_fee))/Double.parseDouble(Global.currencyValue))-Float.valueOf(discountValue))));

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    String departure_from,DepartureTime,JourneyDate,arrival_to,ArrivalTime,operator,default_currency,total_price_summary;
    public void loadBusDetails(){
        try {
            JSONObject mainjsonObj = new JSONObject(flightResponse);
            default_currency=mainjsonObj.getJSONObject("seat_attr").getString("default_currency");
            total_price_summary=mainjsonObj.getJSONObject("seat_attr").getString("total_price_summary");
            departure_from=mainjsonObj.getString("departure_from");
            DepartureTime=mainjsonObj.getString("DepartureTime");
            arrival_to=mainjsonObj.getString("arrival_to");
            ArrivalTime=mainjsonObj.getString("ArrivalTime");
            operator=mainjsonObj.getString("operator");
            JourneyDate=mainjsonObj.getString("JourneyDate");
           //ArrivalDate=mainjsonObj.getString("ArrTime");


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bus_from_city.setText(departure_from.toUpperCase());
                    bus_departure_time.setText(DepartureTime);
                    bus_departure_date.setText(Global.changeDateFormat(JourneyDate));
                    bus_to_city.setText(arrival_to.toUpperCase());
                    bus_arrival_time.setText(ArrivalTime);
                    bus_arrival_date.setText(Global.changeDateFormat(arrival_date));
                    bus_operator.setText(operator);
                    seats_number.setText(bus_seatValue);
                    tv_no_of_seats.setText(seat_count+"");
                   // total_fare.setText(Global.currencySymbol+" "+total_price_summary);
                    total_fare.setText(Global.currencySymbol+" "+((Double.parseDouble(String.format("%.2f",(Double.parseDouble(total_price_summary)/Double.parseDouble(Global.currencyValue)))))));


                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Integer status = 0;
    String url = null;

    @Override
    public void getResponse(String response, int flag) {

        Log.e("payment page response",response);
        switch (flag){
            case 1:

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1)
                    {    status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url =dataobj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0){
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.support_frame, new PaymentFlight(url), null, false);
                }
                break;
            case 2:
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1){
                        status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url =dataobj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0){
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.main_frame, new PaymentFlight(url), null, false);
                }
                break;

            case 3:
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1){
                        status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url = dataobj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0){
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.main_frame, new PaymentFlight(url), null, true);
                }
                break;

        }
    }

    public void setKeyListenerOnView(View v)
    {

       // Log.i(TAG, "setKeyListenerOnView");

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;

                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK :
                     //   Log.i("Menu", "onKey Back listener is working!!!");
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Alert")
                                .setMessage("Do you wish to exit from payments session?")
                                .setNegativeButton("No", null)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                getActivity().finish();
                                                //((NavigationDrawer)getActivity()).selectItem(0, true);
                                            }
                                        }).create().show();
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.i(TAG, "onResume");
        setKeyListenerOnView(getView());
    }



}
