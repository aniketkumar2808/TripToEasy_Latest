package com.travojet.flight.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.travojet.R;
import com.travojet.flight.fragment.FilterFragment;
import com.travojet.main.adapter.common.CommonRecyclerAdapter;
import com.travojet.main.adapter.common.CommonViewHolder;
import com.travojet.model.parsingModel.flight.FlightList;

import java.util.List;

/**
 * Created by ptblr-1195 on 13/3/18.
 */

public class FlightListAdapter extends CommonRecyclerAdapter {

    Activity activity;
    FilterFragment filterFragment;
    List<FlightList> flightLists;

    public FlightListAdapter(Activity activity,
                             FilterFragment filterFragment,
                             List<FlightList> flightLists) {
        this.activity = activity;
        this.filterFragment = filterFragment;
        this.flightLists = flightLists;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.flight_filter_item) {
            @Override
            public void onItemSelected(int position) {
                filterFragment.notifyFlight(position,flightLists.get(position).getSelected());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView airlineName = view.findViewById(R.id.flight_name);
        CheckBox isSelected = view.findViewById(R.id.is_selected);

        if(flightLists.get(position).getSelected()){
            isSelected.setChecked(true);
        }else {
            isSelected.setChecked(false);
        }

        airlineName.setText(flightLists.get(position).getAirlineName());
    }

    @Override
    public int getItemCount() {
        return flightLists.size();
    }

}
