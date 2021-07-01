package com.TripToEasy.hotel.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.hotel.fragment.HotelFilterFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.hotel.AmenitiesModel;

import java.util.List;

/**
 * Created by ptblr-1195 on 23/3/18.
 */

public class AmenitiesAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HotelFilterFragment hotelFilterFragment;
    List<AmenitiesModel> amenitiesList;

    public AmenitiesAdapter(Activity activity,
                            HotelFilterFragment hotelFilterFragment,
                            List<AmenitiesModel> amenitiesList) {
        this.activity = activity;
        this.hotelFilterFragment = hotelFilterFragment;
        this.amenitiesList = amenitiesList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.flight_filter_item) {
            @Override
            public void onItemSelected(int position) {
                hotelFilterFragment.notifyAction(position,
                        amenitiesList.get(position).getSelected(),
                        amenitiesList.get(position).getAmenityName());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView amenityName = view.findViewById(R.id.flight_name);
        CheckBox isSelected = view.findViewById(R.id.is_selected);

        amenityName.setText(amenitiesList.get(position).getAmenityName());

        if(amenitiesList.get(position).getSelected()){
            isSelected.setChecked(true);
        }else {
            isSelected.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return amenitiesList.size();
    }
}
