package com.TripToEasy.holiday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.holiday.ItineraryModel;

import java.util.List;

/**
 * Created by ptblr-1195 on 17/4/18.
 */

public class ItineraryAdapter extends CommonRecyclerAdapter {

    Activity activity;
    List<ItineraryModel> itineraryModels;

    public ItineraryAdapter(Activity activity,
                            List<ItineraryModel> itineraryModels) {
        this.activity = activity;
        this.itineraryModels = itineraryModels;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.itinerary_list_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, int i) {
        View view = viewHolder.getView();
        TextView dayValue, description;
        View line;

        dayValue = view.findViewById(R.id.day_title);
        description = view.findViewById(R.id.description);
        line = view.findViewById(R.id.view_line);

        dayValue.setText("DAY "+itineraryModels.get(i).getDayText()+" - "
                +itineraryModels.get(i).getPlace());
        description.setText(itineraryModels.get(i).getDescriptionText());

        if(i == (itineraryModels.size() -1)){
            line.setVisibility(View.GONE);
        }else {
            line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return itineraryModels.size();
    }

}