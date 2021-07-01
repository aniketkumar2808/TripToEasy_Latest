package com.TripToEasy.bus.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.bus.fragment.BusFilterFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.bus.BusOperatorsInfo;

import java.util.List;

/**
 * Created by ptblr-1174 on 13/6/18.
 */

public class BusOperatorAdapter extends CommonRecyclerAdapter {

    Activity activity;
    BusFilterFragment filterFragment;
    List<BusOperatorsInfo> busOprLists;

    public BusOperatorAdapter(Activity activity,
                              BusFilterFragment filterFragment,
                             List<BusOperatorsInfo> busOprLists) {
        this.activity = activity;
        this.filterFragment = filterFragment;
        this.busOprLists = busOprLists;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.flight_filter_item) {
            @Override
            public void onItemSelected(int position) {
                filterFragment.notifyFlight(position,busOprLists.get(position).getSelected());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView airlineName = view.findViewById(R.id.flight_name);
        CheckBox isSelected = view.findViewById(R.id.is_selected);

        if(busOprLists.get(position).getSelected()){
            isSelected.setChecked(true);
        }else {
            isSelected.setChecked(false);
        }

        airlineName.setText(busOprLists.get(position).getOperatorName());
    }

    @Override
    public int getItemCount() {
        return busOprLists.size();
    }

}
