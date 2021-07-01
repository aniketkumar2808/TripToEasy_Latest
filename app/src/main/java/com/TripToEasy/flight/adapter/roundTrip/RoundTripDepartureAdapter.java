package com.TripToEasy.flight.adapter.roundTrip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.flight.fragment.TwoWayFragment;
import com.TripToEasy.model.parsingModel.flight.TwoWayList;
import com.TripToEasy.utils.webservice.ApiConstants;

import java.util.List;

public class RoundTripDepartureAdapter extends CommonRecyclerAdapter {
    Activity activity;
    List<TwoWayList> twoWayLists;
    ApiConstants apiConstants;
    TwoWayFragment twoWayFragment;

    public RoundTripDepartureAdapter(Activity activity,
                                     List<TwoWayList> twoWayLists,
                                     TwoWayFragment twoWayFragment) {
        this.activity = activity;
        this.twoWayLists = twoWayLists;
        this.twoWayFragment = twoWayFragment;
        apiConstants = new ApiConstants();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.round_tip_list_item_one) {
            @Override
            public void onItemSelected(int position) {


            }
        };
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        TextView airlineName = view.findViewById(R.id.airline_name);
        TextView airlineCode = view.findViewById(R.id.airline_code);
        TextView airlineDepartureTime = view.findViewById(R.id.departure_time);
        TextView airlineDepartureDate = view.findViewById(R.id.departure_date);
        TextView airlineDepartureCity = view.findViewById(R.id.from_city);
        TextView airlineArrivalTime = view.findViewById(R.id.arrival_time);
        TextView airlineArrivalDate = view.findViewById(R.id.arrival_date);
        TextView airlineArrivalCity = view.findViewById(R.id.to_city);
        TextView airlineTravelTime = view.findViewById(R.id.travel_time);
        TextView airlineStops = view.findViewById(R.id.stop_count);
        TextView airlinePrice = view.findViewById(R.id.price);
        TextView airlineRefundable = view.findViewById(R.id.refundable);
        TextView fareRule = view.findViewById(R.id.farerule);
        LinearLayout viewTwo = view.findViewById(R.id.view_two);
        CardView main_layout = view.findViewById(R.id.card_view);
        ImageView flightImage = view.findViewById(R.id.flight_image);

        ImageView returnImage = view.findViewById(R.id.return_flight_image);
        TextView returnDepartureTime = view.findViewById(R.id.return_time_one);
        TextView returnArrivalTime = view.findViewById(R.id.return_time_two);

        airlineName.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightName());
        airlineCode.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightCode());
        airlineDepartureTime.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightDepartureTime());
        airlineDepartureDate.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightDepartureDate());

        airlineDepartureCity.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightDeparturePlace());
        airlineArrivalTime.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightArrivalTime());
        airlineArrivalDate.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightArrivalDate());
        airlineArrivalCity.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightArrivalPlace());
        airlineTravelTime.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightTravelTime());
        airlineStops.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightStopCount());
        //airlinePrice.setText(Html.fromHtml(twoWayLists.get(0).getOneWayLists().get(position).getFlightPrice()));
        airlinePrice.setText(Html.fromHtml(twoWayLists.get(0).getOneWayLists().get(position).getFlightPrice().split("\\.")[0]));
        airlineRefundable.setText(twoWayLists.get(0).getOneWayLists().get(position).getFlightIsRefundable());
        Picasso.get().load(apiConstants.URL+twoWayLists.get(0).getOneWayLists().get(position).getFlightImage()).into(flightImage);
        if(twoWayLists.get(0).getOneWayLists().get(position).getSelected()){
            main_layout.setBackgroundColor(activity.getResources().getColor(R.color.light_blue));
            main_layout.setRadius(5.0f);
        }else {
            main_layout.setBackgroundColor(activity.getResources().getColor(R.color.white));
            main_layout.setRadius(5.0f);
        }

        try{
            Log.d("TIME DATA",twoWayLists.get(1).getOneWayLists().get(position).getFlightDepartureTime());
            returnDepartureTime.setText(twoWayLists.get(1).getOneWayLists().get(position).getFlightDepartureTime());
            returnArrivalTime.setText(twoWayLists.get(1).getOneWayLists().get(position).getFlightArrivalTime());
            Picasso.get().load(apiConstants.URL+twoWayLists.get(1).getOneWayLists().get(position).getFlightImage()).into(returnImage);
            if(twoWayLists.get(1).getOneWayLists().get(position).getSelected()){
                viewTwo.setBackground(activity.getResources().getDrawable(R.drawable.light_blue_curve_one));
            }else {
                viewTwo.setBackground(activity.getResources().getDrawable(R.drawable.top_botom_curve));
            }
            viewTwo.setVisibility(View.VISIBLE);
        }catch (Exception e){
            e.printStackTrace();
            viewTwo.setVisibility(View.GONE);
        }

        fareRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twoWayFragment.callFairRule(twoWayLists.get(0).getOneWayLists().get(position).getProvabAuthKey(),
                                            twoWayLists.get(0).getOneWayLists().get(position).getToken());
            }
        });

        main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twoWayFragment.DepartureDetails(position,
                        twoWayLists.get(0).getOneWayLists().get(position).getProvabAuthKey(),
                        twoWayLists.get(0).getOneWayLists().get(position).getBookingSource(),
                        twoWayLists.get(0).getOneWayLists().get(position).getToken(),
                        twoWayLists.get(0).getOneWayLists().get(position).getTokenKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(twoWayLists.size() != 0){
            return twoWayLists.get(0).getOneWayLists().size();
        }else {
            return 0;
        }
    }
}