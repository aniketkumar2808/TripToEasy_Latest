package com.TripToEasy.sight_seeing.sightseeing_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.TripToEasy.R;
import com.TripToEasy.model.parsingModel.sightSeeing.TripData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {

    List<TripData> mTripList;
    onBookNow mOnBookNow;
    public TripListAdapter(List<TripData> mTripList, onBookNow mOnBookNow) {
        this.mTripList = mTripList;
        this.mOnBookNow=mOnBookNow;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.txtTrName.setText(mTripList.get(position).getGradeTitle());
        holder.txtTrCode.setText(mTripList.get(position).getGradeCode());
        holder.txtDescription.setText("Description: " + mTripList.get(position).getGradeDescription());
        holder.txtDeparture.setText("Departure Time: " + mTripList.get(position).getGradeDepartureTime());
        holder.txtTotalTra.setText("Total Traveller(s): " + mTripList.get(position).getTotalPax());
        holder.txtLanguage.setText("Language Service: ");
        holder.txtPrice.setText(mTripList.get(position).getTotalDisplayFare());

        holder.btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOnBookNow.onBookClicked(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTrName)
        TextView txtTrName;

        @BindView(R.id.txtTrCode)
        TextView txtTrCode;

        @BindView(R.id.txtDescription)
        TextView txtDescription;

        @BindView(R.id.txtDeparture)
        TextView txtDeparture;

        @BindView(R.id.txtTotalTra)
        TextView txtTotalTra;

        @BindView(R.id.txtLanguage)
        TextView txtLanguage;

        @BindView(R.id.txtPrice)
        TextView txtPrice;

        @BindView(R.id.btnBookNow)
        Button btnBookNow;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface onBookNow{
        void onBookClicked(int position);

    }


}
