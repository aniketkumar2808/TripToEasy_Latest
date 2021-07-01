package com.TripToEasy.flight.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.flight.TicketPreviewSegment;

import java.util.List;

/**
 * Created by ptblr-1195 on 15/3/18.
 */

public class ReviewSegmentAdapter extends CommonRecyclerAdapter {

    Activity activity;
    List<TicketPreviewSegment> flightSegments;

    public ReviewSegmentAdapter(Activity activity,
                                List<TicketPreviewSegment> flightSegments) {
        this.activity = activity;
        this.flightSegments = flightSegments;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.flight_review_segment_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView departureAirport = view.findViewById(R.id.place_name_departure);
        TextView arrivalAirport = view.findViewById(R.id.place_name_arrival);
        TextView departureTime = view.findViewById(R.id.departure_time);
        TextView arrivalTime = view.findViewById(R.id.arrival_time);
        TextView airlineName = view.findViewById(R.id.airline_name);

        departureAirport.setText(flightSegments.get(position).getOriginCode()
                +", "+flightSegments.get(position).getOriginName());
        arrivalAirport.setText(flightSegments.get(position).getDestinationCode()
                +", "+flightSegments.get(position).getDestinationName());
        departureTime.setText((flightSegments.get(position).getDepartureDate())+"   "+flightSegments.get(position).getDepartureTime());
        arrivalTime.setText((flightSegments.get(position).getArrivalDate())+"   "+flightSegments.get(position).getArrivalTime());
        airlineName.setText(flightSegments.get(position).getAirlineName());


    }

    @Override
    public int getItemCount() {
        return flightSegments.size();
    }
}