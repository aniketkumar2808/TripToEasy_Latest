package com.TripToEasy.sight_seeing.sightseeing_adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.TripToEasy.R;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdditionalInfoAdapter extends RecyclerView.Adapter<AdditionalInfoAdapter.ViewHolder> {


    JSONArray mAdditionInfo;

    public AdditionalInfoAdapter(JSONArray mAdditionInfo) {
        this.mAdditionInfo = mAdditionInfo;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_additional_info, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.txtAddInfo.setText(mAdditionInfo.get(position).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return ((mAdditionInfo == null) ? 0 : mAdditionInfo.length());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtAddInfo)
        TextView txtAddInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);


        }
    }

}
