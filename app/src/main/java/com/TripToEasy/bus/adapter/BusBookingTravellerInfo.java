package com.TripToEasy.bus.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.TripToEasy.R;
import com.TripToEasy.model.parsingModel.bus.BusTravellerInfo;

import java.util.List;

/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class BusBookingTravellerInfo extends RecyclerView.Adapter<BusBookingTravellorVH> {

    Activity activity;
    BusBookingTravellorVH busBookingTravellorVH;
    List<BusTravellerInfo> busTravellerInfos;
    BusBookingTravellorVH viewHolder;

    public BusBookingTravellerInfo(Activity activity,
                                   List<BusTravellerInfo> busTravellerInfos) {
        this.activity = activity;
        this.busTravellerInfos = busTravellerInfos;
    }

    @Override
    public BusBookingTravellorVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.bus_booking_user_info_item,parent,false);
        busBookingTravellorVH = new BusBookingTravellorVH(view,activity);
        return busBookingTravellorVH;
    }

    @Override
    public void onBindViewHolder(final BusBookingTravellorVH holder, final int position) {

        viewHolder = holder;

        if(busTravellerInfos.get(position).getContactName() != null){
            holder.travellerName.setText(busTravellerInfos.get(position).getContactName());
        }

        if(busTravellerInfos.get(position).getAgeValue() != null){
            holder.travellerAge.setText(busTravellerInfos.get(position).getAgeValue());
        }

        holder.travellerAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(holder.travellerAge.getText().toString() != null &&
                        holder.travellerAge.getText().toString().length() != 0){
                    busTravellerInfos.get(position).setAgeValue(holder.travellerAge.getText().toString());
                }else {
                    if(holder.travellerAge.getText().toString().length() == 0){
                        busTravellerInfos.get(position).setAgeValue(null);
                    }
                }
            }
        });

        holder.travellerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(holder.travellerName.getText().toString() != null &&
                        holder.travellerName.getText().toString().length() != 0){
                    busTravellerInfos.get(position).setContactName(holder.travellerName.getText().toString());
                }else {
                    if( holder.travellerName.getText().toString().length() == 0){
                        busTravellerInfos.get(position).setContactName(null);
                    }
                }
            }
        });

        holder.titleSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(holder.titleSelection.getSelectedItem() != null){
                    busTravellerInfos.get(position).setTitleValue(activity.getResources().getStringArray(R.array.array_name)[holder.titleSelection.getSelectedItemPosition()]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return busTravellerInfos.size();
    }

}
