package com.TripToEasy.flight.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.flight.FlightSegment;
import com.TripToEasy.utils.dialogs.DateTimeUtils;
import com.TripToEasy.utils.webservice.ApiConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ptblr-1195 on 15/3/18.
 */

public class FlightSegmentAdapter extends CommonRecyclerAdapter {

    Activity activity;
    List<FlightSegment> flightSegments;
    ApiConstants apiConstants;

    public FlightSegmentAdapter(Activity activity,
                                List<FlightSegment> flightSegments) {
        this.activity = activity;
        this.flightSegments = flightSegments;
        apiConstants = new ApiConstants();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.flight_detail_segment_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        ImageView flightImage = view.findViewById(R.id.airline_image);
        TextView departureAirport = view.findViewById(R.id.place_name_departure);
        TextView arrivalAirport = view.findViewById(R.id.place_name_arrival);
        TextView departureTime = view.findViewById(R.id.departure_time);
        TextView arrivalTime = view.findViewById(R.id.arrival_time);
        TextView airlineName = view.findViewById(R.id.airline_name);
        TextView segmentDetails = view.findViewById(R.id.segment_details);
        TextView travelTime = view.findViewById(R.id.travel_time);
        TextView flightNumber = view.findViewById(R.id.flight_number);
        LinearLayout layOffLayout = view.findViewById(R.id.lay_off_info);

        Picasso.get().load(apiConstants.IMAGE_BASE+flightSegments
                .get(position).getAirlineCode()).into(flightImage);
        departureAirport.setText(flightSegments.get(position).getDepartureAirportCode()
                +", "+flightSegments.get(position).getDepartureAirportName());
        arrivalAirport.setText(flightSegments.get(position).getArrivalAirportCode()
                +", "+flightSegments.get(position).getArrivalAirportName());
        departureTime.setText(chageDateFormat(flightSegments.get(position).getOriginDateTime())+"   "+flightSegments.get(position).getDepartureTime());
        arrivalTime.setText(chageDateFormat(flightSegments.get(position).getDestinationDateTime())+"   "+flightSegments.get(position).getArrivalTime());
        airlineName.setText(flightSegments.get(position).getAirlineName());
        travelTime.setText(flightSegments.get(position).getTravelTime());
        flightNumber.setText(flightSegments.get(position).getAirlineId());

        if(position == (flightSegments.size()-1)){
            layOffLayout.setVisibility(View.GONE);
        }else {
            layOffLayout.setVisibility(View.VISIBLE);
            DateTimeUtils obj = new DateTimeUtils();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String layTime = "";
            Date date1, date2;
            try{
                date1 = simpleDateFormat.parse(flightSegments.get(position).getDestinationDateTime());
                date2 = simpleDateFormat.parse(flightSegments.get(position+1).getOriginDateTime());

                long different = date2.getTime() - date1.getTime();
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;

                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;

                long elapsedHours = different / hoursInMilli;
                different = different % hoursInMilli;

                long elapsedMinutes = different / minutesInMilli;
                different = different % minutesInMilli;

                long elapsedSeconds = different / secondsInMilli;

                layTime = elapsedDays+"Day "+elapsedHours+"hour "+elapsedMinutes+"min "+elapsedSeconds+"sec";

            }catch (Exception e){
                e.printStackTrace();
            }
            segmentDetails.setText(flightSegments.get(position).getArrivalAirportCode()
                    +", "+flightSegments.get(position).getArrivalAirportName()
                    +"\n"+layTime);
        }

    }

    @Override
    public int getItemCount() {
        return flightSegments.size();
    }

    String chageDateFormat(String departureDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            Date endDate = simpleDateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return departureDate;
    }
}