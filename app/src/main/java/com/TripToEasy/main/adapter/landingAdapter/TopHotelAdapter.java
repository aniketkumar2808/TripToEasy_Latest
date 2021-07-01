package com.TripToEasy.main.adapter.landingAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TripToEasy.main.activity.MainActivity;
import com.TripToEasy.utils.IntentAndFragmentService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.landingPage.TopHotelModel;

import java.util.List;

/**
 * Created by ptblr-1195 on 20/3/18.
 */

public class TopHotelAdapter extends CommonRecyclerAdapter {
    IntentAndFragmentService intentAndFragmentService;
    Activity activity;
    List<TopHotelModel> topHotelModels;

    public TopHotelAdapter(Activity activity,
                           List<TopHotelModel> topHotelModels) {
        this.activity = activity;
        this.topHotelModels = topHotelModels;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.top_hotel_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        ImageView hotelImage = view.findViewById(R.id.package_image);
        TextView cityName = view.findViewById(R.id.city_name);
        TextView hotelCount = view.findViewById(R.id.hotel_count);

        Picasso.get()
                .load(topHotelModels.get(position).getDestinationImage())
                .placeholder(R.drawable.hotel_placeholder)
                .error(R.drawable.hotel_placeholder)
                .into(hotelImage);
        cityName.setText(topHotelModels.get(position).getDestinationName());
        hotelCount.setText(topHotelModels.get(position).getHotelCount()+" hotels");

        hotelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAndFragmentService = new IntentAndFragmentService();
                Bundle bundle = new Bundle();
                bundle.putInt("calling_fragment",6);
                bundle.putString("cityname",topHotelModels.get(position).getDestinationName());
                bundle.putString("cityid",topHotelModels.get(position).getCityID());
                intentAndFragmentService.intentDisplay(activity, MainActivity.class, bundle);
            }
        });

    }

    final Callback loaderCallBack = new Callback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Exception e) {

        }
    };

    @Override
    public int getItemCount() {
        return topHotelModels.size();
    }
}
