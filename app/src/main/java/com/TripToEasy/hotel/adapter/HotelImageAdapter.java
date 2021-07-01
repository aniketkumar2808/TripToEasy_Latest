package com.TripToEasy.hotel.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.hotel.fragment.HotelDetailFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;

import java.util.List;

/**
 * Created by ptblr-1174 on 18/4/18.
 */

public class HotelImageAdapter extends CommonRecyclerAdapter {
    List<String> imgLists;
    Activity activity;
    HotelDetailFragment detailFragment;
    public HotelImageAdapter(
            Activity activity,
            HotelDetailFragment detailFragment, List<String> imgLists) {
        this.activity=activity;
        this.detailFragment=detailFragment;
        this.imgLists = imgLists;
    }
    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();

        ImageView imageView=view.findViewById(R.id.iv_hotel_img);

        Picasso.get().load(imgLists.get(position)).placeholder(R.drawable.hotel_placeholder).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

    }
    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.image_hotel_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }
    @Override
    public int getItemCount() {
        return imgLists.size();
    }
}
