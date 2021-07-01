package com.TripToEasy.holiday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.utils.webservice.ApiConstants;

import java.util.List;

/**
 * Created by ptblr-1195 on 17/4/18.
 */

public class GalleryAdapter extends CommonRecyclerAdapter {

    Activity activity;
    List<String> galleryList;
    ApiConstants apiConstants;

    public GalleryAdapter(Activity activity,
                          List<String> galleryList) {
        this.activity = activity;
        this.galleryList = galleryList;
        apiConstants = new ApiConstants();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.gallery_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, int i) {
        View view = viewHolder.getView();
        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.bringToFront();

        Picasso.get()
                .load(galleryList.get(i))
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

}