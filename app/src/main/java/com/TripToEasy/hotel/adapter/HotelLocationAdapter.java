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
import com.TripToEasy.model.parsingModel.LocationModel;

import java.util.List;

public class HotelLocationAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HotelFilterFragment filterFragment;
    List<LocationModel> hotelLocation;

    public HotelLocationAdapter(Activity activity,
                                HotelFilterFragment filterFragment,
                                List<LocationModel> hotelLocation) {
        this.activity = activity;
        this.filterFragment = filterFragment;
        this.hotelLocation = hotelLocation;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.flight_filter_item) {
            @Override
            public void onItemSelected(int position) {
                filterFragment.notifyLocationAction(position,
                        hotelLocation.get(position).getSelected(),
                        hotelLocation.get(position).getLocationName());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView airlineName = view.findViewById(R.id.flight_name);
        CheckBox isSelected = view.findViewById(R.id.is_selected);

        if(hotelLocation.get(position).getSelected()){
            isSelected.setChecked(true);
        }else {
            isSelected.setChecked(false);
        }

        airlineName.setText(hotelLocation.get(position).getLocationName());
    }

    @Override
    public int getItemCount() {
        return hotelLocation.size();
    }

}
