package com.TripToEasy.transfers.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.TripToEasy.R;
import com.TripToEasy.model.parsingModel.transfers.TransfersTravellerInfo;

import java.util.List;

/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class TransfersBookingTravellerAdapter extends RecyclerView.Adapter<TransfersBookingTravellorVH> {

    Activity activity;
    TransfersBookingTravellorVH transfersBookingTravellorVH;
    List<TransfersTravellerInfo> transfersTravellerInfos;
    TransfersBookingTravellorVH viewHolder;

    public TransfersBookingTravellerAdapter(Activity activity,
                                            List<TransfersTravellerInfo> transfersTravellerInfos) {
        this.activity = activity;
        this.transfersTravellerInfos = transfersTravellerInfos;
    }

    @Override
    public TransfersBookingTravellorVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.transfers_add_traveller_item,parent,false);
        transfersBookingTravellorVH = new TransfersBookingTravellorVH(view,activity);
        return transfersBookingTravellorVH;
    }

    @Override
    public void onBindViewHolder(final TransfersBookingTravellorVH holder, final int position) {

        viewHolder = holder;

        if(transfersTravellerInfos.get(position).getFirstName() != null){
            holder.firstName.setText(transfersTravellerInfos.get(position).getFirstName());
        }

        if(transfersTravellerInfos.get(position).getLastName() != null){
            holder.lastName.setText(transfersTravellerInfos.get(position).getLastName());
        }

        holder.lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(holder.lastName.getText().toString() != null &&
                        holder.lastName.getText().toString().length() != 0){
                    transfersTravellerInfos.get(position).setLastName(holder.lastName.getText().toString());
                }else {
                    if(holder.lastName.getText().toString().length() == 0){
                        transfersTravellerInfos.get(position).setLastName(null);
                    }
                }
            }
        });

        holder.firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(holder.firstName.getText().toString() != null &&
                        holder.firstName.getText().toString().length() != 0){
                    transfersTravellerInfos.get(position).setFirstName(holder.firstName.getText().toString());
                }else {
                    if( holder.firstName.getText().toString().length() == 0){
                        transfersTravellerInfos.get(position).setFirstName(null);
                    }
                }
            }
        });

        holder.titleSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(holder.titleSelection.getSelectedItem() != null){
                    transfersTravellerInfos.get(position).setTitleValue(activity.getResources().getStringArray(R.array.array_name)[holder.titleSelection.getSelectedItemPosition()]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.typeSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(holder.typeSelection.getSelectedItem() != null){
                    transfersTravellerInfos.get(position).setTypeValue(activity.getResources().getStringArray(R.array.type_array_name)[holder.typeSelection.getSelectedItemPosition()]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return transfersTravellerInfos.size();
    }

}
