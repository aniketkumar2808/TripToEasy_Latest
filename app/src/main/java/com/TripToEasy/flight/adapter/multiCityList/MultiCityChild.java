package com.TripToEasy.flight.adapter.multiCityList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.flight.multiCity.FlightDetailList;
import com.TripToEasy.utils.webservice.ApiConstants;

import java.util.List;

/**
 * Created by ptblr-1195 on 6/3/18.
 */

public class MultiCityChild extends CommonRecyclerAdapter {

    Activity activity;
    List<FlightDetailList> flightDetailLists;
    ApiConstants apiConstants;
    MultiCityMain multiCityMain;

    public MultiCityChild(Activity activity,
                          List<FlightDetailList> flightDetailLists,
                          MultiCityMain multiCityMain) {
        this.activity = activity;
        this.flightDetailLists = flightDetailLists;
        apiConstants = new ApiConstants();
        this.multiCityMain = multiCityMain;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.multi_city_item_two){
            @Override
            public void onItemSelected(int position) {
                multiCityMain.clickAction(flightDetailLists.get(position).getToken());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
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
        ImageView flightImage = view.findViewById(R.id.flight_image);

        airlineName.setText(flightDetailLists.get(position).getFlightName());
        airlineCode.setText(flightDetailLists.get(position).getFlightCode());
        airlineDepartureTime.setText(flightDetailLists.get(position).getFlightDepartureTime());
        airlineDepartureDate.setText(flightDetailLists.get(position).getFlightDepartureDate());
        airlineDepartureCity.setText(flightDetailLists.get(position).getFlightDeparturePlace());
        airlineArrivalTime.setText(flightDetailLists.get(position).getFlightArrivalTime());
        airlineArrivalDate.setText(flightDetailLists.get(position).getFlightArrivalDate());
        airlineArrivalCity.setText(flightDetailLists.get(position).getFlightArrivalPlace());
        airlineTravelTime.setText(flightDetailLists.get(position).getFlightTravelTime());
        airlineStops.setText(flightDetailLists.get(position).getFlightStopCount());

        Picasso.get().load(apiConstants.URL+flightDetailLists.get(position).getFlightImage()).into(flightImage);
    }

    @Override
    public int getItemCount() {
        return flightDetailLists.size();
    }
}