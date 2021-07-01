package com.TripToEasy.sight_seeing.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.sightSeeing.HistoryData;

import java.util.List;

public class SightSeeingHistory extends CommonRecyclerAdapter {


    List<HistoryData> mHistoryData;
    Context mContext;


    public SightSeeingHistory(List<HistoryData> mHistoryData, Context mContext) {
        this.mHistoryData = mHistoryData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.item_sight_seeing_history) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder holder, int position) {

        View lView = holder.getView();

        TextView txtName, txtDate, bookingId, txtAddress, totalFare, bookingStatus;

        txtName = lView.findViewById(R.id.txtName);
        txtDate = lView.findViewById(R.id.txtDate);
        bookingId = lView.findViewById(R.id.bookingId);
        txtAddress = lView.findViewById(R.id.txtAddress);
        totalFare = lView.findViewById(R.id.totalFare);
        bookingStatus = lView.findViewById(R.id.bookingStatus);

        txtName.setText(mHistoryData.get(position).getName());
        txtDate.setText(mHistoryData.get(position).getDate());
        bookingId.setText(mHistoryData.get(position).getBookingId());
        txtAddress.setText(mHistoryData.get(position).getTxtAddress());
        totalFare.setText(mHistoryData.get(position).getCurrency() + " " + mHistoryData.get(position).getTotalFare());

        bookingStatus.setText(mHistoryData.get(position).getBookingStatus());


    }

    @Override
    public int getItemCount() {
        return mHistoryData.size();
    }
}
