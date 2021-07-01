package com.TripToEasy.flight.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.flight.fragment.CitySearchFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.CityList;

import java.util.List;

/**
 * Created by ptblr-1195 on 27/2/18.
 */

public class CityListAdapter extends CommonRecyclerAdapter {

    CitySearchFragment citySearchFragment;
    Activity activity;
    List<CityList> cityLists;

    public CityListAdapter(CitySearchFragment citySearchFragment,
                           Activity activity,
                           List<CityList> cityLists) {
        this.citySearchFragment = citySearchFragment;
        this.activity = activity;
        this.cityLists = cityLists;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.city_list_item) {
            @Override
            public void onItemSelected(int position) {
                citySearchFragment.notifySelection(
                        cityLists.get(position).getCityId(),
                        cityLists.get(position).getSearchCityName());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView cityName = view.findViewById(R.id.place_name);
        cityName.setText(cityLists.get(position).getSearchCityName());
    }

    @Override
    public int getItemCount() {
        return cityLists.size();
    }

}