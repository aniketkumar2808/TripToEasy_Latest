package com.TripToEasy.flight.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.flight.fragment.FlightFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.requestModel.multiCity.MultiCitySegment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ptblr-1195 on 1/3/18.
 */

public class MultiCityViewAdapter extends CommonRecyclerAdapter {

    FlightFragment flightFragment;
    List<MultiCitySegment> multiCitySegments;

    public MultiCityViewAdapter(FlightFragment flightFragment,
                                List<MultiCitySegment> multiCitySegments) {
        this.flightFragment = flightFragment;
        this.multiCitySegments = multiCitySegments;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.multi_city_view_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        final ImageView closeLayout = view.findViewById(R.id.remove_item);
        final TextView fromCity = view.findViewById(R.id.from_city);
        final TextView toCity = view.findViewById(R.id.to_city);
        final TextView dateValue = view.findViewById(R.id.select_date);

        if(position == 0 || position == 1){
            closeLayout.setVisibility(View.INVISIBLE);
        }

        fromCity.setText(multiCitySegments.get(position).getOrigin());
        toCity.setText(multiCitySegments.get(position).getDestination());
        if(multiCitySegments.get(position).getDepartureDate().length() != 0){
            String[] date = multiCitySegments.get(position).getDepartureDate().split("T");

            String endDate_str;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                Date endDate = dateFormat.parse(date[0]);
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMM-dd EEEE",Locale.ENGLISH);
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy EEEE", Locale.ENGLISH);
                endDate_str = sdf.format(endDate);
                String[] splitDate = endDate_str.split(" ");
                dateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }else {
            dateValue.setText("");
        }

        closeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(closeLayout.getVisibility() != View.INVISIBLE){
                    //TODO : remove that position view.
                    flightFragment.removeView(position);
                }
            }
        });

        fromCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flightFragment.addCity(position,3);
            }
        });

        toCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flightFragment.addCity(position,4);
            }
        });

        dateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flightFragment.addDate(position, fromCity.getText().toString(),toCity.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return multiCitySegments.size();
    }

}