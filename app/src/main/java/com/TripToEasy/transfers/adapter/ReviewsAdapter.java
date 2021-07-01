package com.TripToEasy.transfers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.model.parsingModel.transfers.ProductReviewsData;
import com.TripToEasy.utils.CommonUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    List<ProductReviewsData> mReviewsData;
    Context mContext;

    public ReviewsAdapter(List<ProductReviewsData> mReviewsData, Context mContext) {
        this.mReviewsData = mReviewsData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cust_review, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtUserName.setText(mReviewsData.get(position).getUserName());
        holder.txtUserComment.setText(Html.fromHtml(mReviewsData.get(position).getReview()));
        holder.ratingBar.setRating(mReviewsData.get(position).getRating());

        if (mReviewsData.get(position).getUserImage() != null) {
            Picasso.get().load(mReviewsData.get(position).getUserImage()).placeholder(R.drawable.ic_hotel_bg).into(holder.ivUserPic);        }
            else {
            Picasso.get().load(R.drawable.ic_my_account_not_selected).error(R.drawable.ic_my_account_not_selected).into(holder.ivUserPic);
        }

        Date lPubDate = CommonUtils.convertStrToDate(mReviewsData.get(position).getPublishedDate(), "yyyy-MM-dd");

        String strPubDate = CommonUtils.conDateToStr(lPubDate, "EEEE, dd MMM yyyy");

        holder.txtPubDate.setText(strPubDate);


    }


    @Override
    public int getItemCount() {
        return mReviewsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivUserPic)
        ImageView ivUserPic;

        @BindView(R.id.ratingBar)
        RatingBar ratingBar;

        @BindView(R.id.txtUserName)
        TextView txtUserName;

        @BindView(R.id.txtPubDate)
        TextView txtPubDate;

        @BindView(R.id.txtUserComment)
        TextView txtUserComment;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
