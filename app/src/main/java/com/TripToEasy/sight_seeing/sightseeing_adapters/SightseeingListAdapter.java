package com.TripToEasy.sight_seeing.sightseeing_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.TripToEasy.R;
import com.TripToEasy.utils.Global;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SightseeingListAdapter extends RecyclerView.Adapter<SightseeingListAdapter.ViewHolder> implements Filterable {
    ArrayList<SightseeingListBean> data_al;
    private Context mContext;
    private CustomFilter mCustomFilter;
    onItemClicked onItemClicked;


    public SightseeingListAdapter(Context mContext, ArrayList<SightseeingListBean> data_al, onItemClicked onItemClicked) {
        this.mContext = mContext;
        this.data_al = data_al;
        this.onItemClicked=onItemClicked;

    }


    @Override
    public Filter getFilter() {
        if (mCustomFilter == null) {
            mCustomFilter = new CustomFilter(data_al);
        }
        return mCustomFilter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sight_seeing_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tripName.setText(data_al.get(position).getProductName());
        if (Integer.parseInt(data_al.get(position).getRatings()) > 0)
            holder.ratingBar.setRating(Float.parseFloat(data_al.get(position).getRatings()));
        holder.reviewsTx.setText(data_al.get(position).getReviewCount() + " Reviews");
        holder.durationTx.setText("Duration " + data_al.get(position).getDuration());
        holder.priceTx.setText(Global.currencySymbol +" " +String.format("%.2f",(Double.parseDouble(data_al.get(position).getPrice())/Double.parseDouble(Global.currencyValue))));
        Glide.with(mContext).load(data_al.get(position).getBigImage()).
                into(holder.cityImage);
        if (data_al.get(position).refundable)
            holder.refundableTx.setText("Refundable");
        else
            holder.refundableTx.setText("Non-Refundable");

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked.onSelectSS(data_al.get(position).getProductCode(),data_al.get(position).getResultToken());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_al.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tripName)
        TextView tripName;
        @BindView(R.id.ratingBar)
        RatingBar ratingBar;
        @BindView(R.id.reviewsTx)
        TextView reviewsTx;
        @BindView(R.id.durationTx)
        TextView durationTx;
        @BindView(R.id.priceTx)
        TextView priceTx;
        @BindView(R.id.refundableTx)
        TextView refundableTx;
        @BindView(R.id.cityImage)
        ImageView cityImage;
        @BindView(R.id.llMain)
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CustomFilter extends Filter {


        ArrayList<SightseeingListBean> filterList;

        public CustomFilter(ArrayList<SightseeingListBean> filterList) {

            this.filterList = filterList;

        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();

            //CHECK CONSTRAINT VALIDITY
            if (charSequence != null && charSequence.length() > 0) {
                //CHANGE TO UPPER
                charSequence = charSequence.toString().toUpperCase();
                //STORE OUR FILTERED PLAYERS
                ArrayList<SightseeingListBean> filteredPlayers = new ArrayList<>();

                for (int i = 0; i < filterList.size(); i++) {
                    //CHECK
                    if (filterList.get(i).getProductName().toUpperCase().contains(charSequence)) {
                        //ADD PLAYER TO FILTERED PLAYERS
                        filteredPlayers.add(filterList.get(i));
                    }
                }

                results.count = filteredPlayers.size();
                results.values = filteredPlayers;
            } else {
                results.count = filterList.size();
                results.values = filterList;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            data_al = (ArrayList<SightseeingListBean>) filterResults.values;
            notifyDataSetChanged();
        }
    }


    public interface onItemClicked{

        void onSelectSS(String ID, String Token);
    }


}


