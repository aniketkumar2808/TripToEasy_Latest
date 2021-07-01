package com.TripToEasy.main.adapter.landingAdapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.TripToEasy.model.parsingModel.landingPage.TopAirlinesModel;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;

import java.util.List;

public class TopAirlineAdapter extends CommonRecyclerAdapter
{

    Activity activity;
    List<TopAirlinesModel> topAirlinesList;

    public TopAirlineAdapter(Activity activity,
                             List<TopAirlinesModel> topAirlinesList) {
        this.activity = activity;
        this.topAirlinesList = topAirlinesList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.top_airline_list_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        ImageView iv_airlineImage = view.findViewById(R.id.iv_airlineImage);
        Picasso.get()
                .load(topAirlinesList.get(position).getLogo())
                .placeholder(R.drawable.final_logo)
                .error(R.drawable.final_logo)
                .into(iv_airlineImage);

        Log.e("airline image url ",topAirlinesList.get(position).getLogo());
    }

    @Override
    public int getItemCount() {
        return topAirlinesList.size();
    }



}