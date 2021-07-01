package com.TripToEasy.hotel.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;

import java.util.List;

/**
 * Created by ptblr-1195 on 28/3/18.
 */

public class FacilityAdapter extends CommonRecyclerAdapter {

    Activity activity;
    List<String> hotelFacilities;
    int i;

    public FacilityAdapter(Activity activity,
                           List<String> hotelFacilities,
                           int i) {
        this.activity = activity;
        this.hotelFacilities = hotelFacilities;
        this.i = i;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.hotel_facility_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, int position) {
        View view = viewHolder.getView();
        TextView facilityName = view.findViewById(R.id.facility_name);
        facilityName.setText(hotelFacilities.get(position));
    }

    @Override
    public int getItemCount() {
        if(hotelFacilities.size() != 0){
            if(i == 1){
                return 6;
            }else {
                return hotelFacilities.size();
            }
        }else {
            return 0;
        }
    }

}