package com.TripToEasy.transfers.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.TripToEasy.utils.Global;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.transfers.TransfersResults;
import com.TripToEasy.transfers.fragment.TransfersResultFragment;

import java.util.List;


public class TransfersListAdapter extends CommonRecyclerAdapter {

    Activity activity;
    TransfersResultFragment transfersResultFragment;
    List<TransfersResults> transfersList;

    public TransfersListAdapter(Activity activity,
                                TransfersResultFragment transfersResultFragment,
                                List<TransfersResults> transfersList) {
        this.activity = activity;
        this.transfersResultFragment = transfersResultFragment;
        this.transfersList = transfersList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        return new CommonViewHolder(parent, R.layout.transfers_item_layout) {
            @Override
            public void onItemSelected(int position) {
                transfersResultFragment.callDetailPage(transfersList.get(position).getProductCode(),transfersList.get(position).getResultToken());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int i) {
        View view = holder.getView();
        ImageView iv_transfers=view.findViewById(R.id.iv_transfer);
        TextView transferName = view.findViewById(R.id.tv_name);
        TextView transferReview = view.findViewById(R.id.tv_reviews);
        TextView transferDuration = view.findViewById(R.id.tv_duration);
        TextView tv_amount = view.findViewById(R.id.tv_amount);
        TextView tv_refundable = view.findViewById(R.id.tv_refundable);
        RatingBar transfers_rating = view.findViewById(R.id.transfers_rating);

        transferName.setText(transfersList.get(i).getProductName());
        transferReview.setText(transfersList.get(i).getReviewCount()+" Reviews");
        transferDuration.setText( "Duration :"+transfersList.get(i).getDuration());
        tv_amount.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(transfersList.get(i).getNetFare())/Double.parseDouble(Global.currencyValue))));

        tv_refundable.setText("-");

        transfers_rating.setRating(Float.parseFloat(transfersList.get(i).getStarRating()));
        if(transfersList.get(i).getImageUrl().equals("")){
            Picasso.get().load("-").placeholder(R.drawable.app_logo).into(iv_transfers);

        }else {
            Picasso.get().load(transfersList.get(i).getImageUrl()).placeholder(R.drawable.app_logo).into(iv_transfers);

        }
    }

    @Override
    public int getItemCount() {
        return transfersList.size();
    }

}
