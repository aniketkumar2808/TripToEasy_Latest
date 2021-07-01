package com.TripToEasy.transfers.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.holiday.HolidayCity;
import com.TripToEasy.transfers.fragment.SearchLocationFragment;

import java.util.List;

/**
 * Created by ptblr-1195 on 10/4/18.
 */

public class TransfersCityAdapter extends CommonRecyclerAdapter {

    Activity activity;
    SearchLocationFragment transferCitySearch;
    List<HolidayCity> holidayCities;

    public TransfersCityAdapter(Activity activity,
                                SearchLocationFragment transferCitySearch,
                                List<HolidayCity> holidayCities) {
        this.activity = activity;
        this.transferCitySearch = transferCitySearch;
        this.holidayCities = holidayCities;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new CommonViewHolder(parent, R.layout.city_list_item) {
            @Override
            public void onItemSelected(int position) {
                transferCitySearch.notifyCountryId(
                        holidayCities.get(position).getCityId(),
                        holidayCities.get(position).getCityName());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int i) {
        View view = holder.getView();
        TextView locName = view.findViewById(R.id.place_name);
        locName.setText(holidayCities.get(i).getCityName());
    }

    @Override
    public int getItemCount() {
        return holidayCities.size();
    }

}