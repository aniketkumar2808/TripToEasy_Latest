package com.TripToEasy.flight.adapter.roundTrip;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.flight.fragment.TwoWayFragment;
import com.TripToEasy.model.parsingModel.flight.TwoWayList;

import java.util.List;

public class RoundTripMainAdapter extends CommonRecyclerAdapter {

    Activity activity;
    TwoWayFragment twoWayFragment;
    List<TwoWayList> twoWayLists;

    public RoundTripMainAdapter(Activity activity,
                                TwoWayFragment twoWayFragment,
                                List<TwoWayList> twoWayLists) {
        this.activity = activity;
        this.twoWayLists = twoWayLists;
        this.twoWayFragment = twoWayFragment;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.roundtrip_item_main) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        RecyclerView twoWayListView = view.findViewById(R.id.two_way_list);
        twoWayListView.setLayoutManager(new LinearLayoutManager(activity));
        int pos;
        switch (position){
            case 0:
                RoundTripDepartureAdapter roundTripDepartureAdapter =
                        new RoundTripDepartureAdapter(activity, twoWayLists, twoWayFragment);
                twoWayListView.setAdapter(roundTripDepartureAdapter);

                try{
                    pos = getPosition(0);
                    if(pos != -1){
                        twoWayListView.scrollToPosition(pos);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case 1:
                RoundTripReturnAdapter roundTripReturnAdapter =
                        new RoundTripReturnAdapter(activity, twoWayLists, twoWayFragment);
                twoWayListView.setAdapter(roundTripReturnAdapter);
                try{
                    pos = getPosition(1);
                    if(pos != -1){
                        twoWayListView.scrollToPosition(pos);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    private int getPosition(int i) {
        Integer pos = -1;
        int j=0;
        while (j < twoWayLists.get(i).getOneWayLists().size()){
            if(twoWayLists.get(i).getOneWayLists().get(j).getSelected()){
                pos = j;
                break;
            }
            j++;
        }
        return pos;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}