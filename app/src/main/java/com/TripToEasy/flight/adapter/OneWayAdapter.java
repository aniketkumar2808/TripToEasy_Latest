package com.TripToEasy.flight.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.flight.fragment.OneWayFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.flight.OneWayList;
import com.TripToEasy.utils.webservice.ApiConstants;

import java.util.List;

/**
 * Created by ptblr-1195 on 27/2/18.
 */

public class OneWayAdapter extends CommonRecyclerAdapter {

    Activity activity;
    OneWayFragment oneWayFragment;
    List<OneWayList> oneWayLists;
    ApiConstants apiConstants;

    public OneWayAdapter(Activity activity,
                         OneWayFragment oneWayFragment,
                         List<OneWayList> oneWayLists) {
        this.activity = activity;
        this.oneWayFragment = oneWayFragment;
        this.oneWayLists = oneWayLists;
        apiConstants = new ApiConstants();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.one_way_item) {
            @Override
            public void onItemSelected(int position) {
                oneWayFragment.callDetailFragment(
                        oneWayLists.get(position).getProvabAuthKey(),
                        oneWayLists.get(position).getBookingSource(),
                        oneWayLists.get(position).getToken(),
                        oneWayLists.get(position).getTokenKey(),
                        position);
            }
        };
    }

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
        TextView cabinClass = view.findViewById(R.id.cabin_class);
        TextView fairRule = view.findViewById(R.id.fair_rule);
        ImageView flightImage = view.findViewById(R.id.flight_image);

        airlineName.setText(oneWayLists.get(position).getFlightName());
        airlineCode.setText(oneWayLists.get(position).getFlightCode());
        airlineDepartureTime.setText(oneWayLists.get(position).getFlightDepartureTime());
        airlineDepartureDate.setText(oneWayLists.get(position).getFlightDepartureDate());
        airlineDepartureCity.setText(oneWayLists.get(position).getFlightDeparturePlace());
        airlineArrivalTime.setText(oneWayLists.get(position).getFlightArrivalTime());
        airlineArrivalDate.setText(oneWayLists.get(position).getFlightArrivalDate());
        airlineArrivalCity.setText(oneWayLists.get(position).getFlightArrivalPlace());
        airlineTravelTime.setText(oneWayLists.get(position).getFlightTravelTime());
        airlineStops.setText(oneWayLists.get(position).getFlightStopCount());
        airlinePrice.setText(Html.fromHtml(oneWayLists.get(position).getFlightPrice().split("\\.")[0]));
        airlineRefundable.setText(oneWayLists.get(position).getFlightIsRefundable());
        cabinClass.setText(oneWayLists.get(position).getCabinClass());
        Picasso.get().load(apiConstants.URL+oneWayLists.get(position).getFlightImage()).into(flightImage);

        fairRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFairRulePopup(oneWayLists.get(position).getProvabAuthKey(),
                        oneWayLists.get(position).getToken());
            }
        });
    }

    private void callFairRulePopup(String provabAuthKey, String token) {
        oneWayFragment.callFairRule(provabAuthKey, token);
    }

    @Override
    public int getItemCount() {
        return oneWayLists.size();
    }

}