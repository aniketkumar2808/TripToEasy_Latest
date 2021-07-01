package com.TripToEasy.main.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.PromoCodeInfo;
import com.TripToEasy.traveller.TravellerFragment;

import java.util.List;

public class PromocodeAdapter extends CommonRecyclerAdapter {

    TravellerFragment fragment;
    Activity activity;
    List<PromoCodeInfo> promocodeLists;

    public PromocodeAdapter(TravellerFragment fragment,
                            Activity activity,
                            List<PromoCodeInfo> promocodeLists) {
        this.fragment = fragment;
        this.activity = activity;
        this.promocodeLists = promocodeLists;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.promocode_list_item) {
            @Override
            public void onItemSelected(int position) {
                fragment.notifyPromocode(promocodeLists.get(position).getPromo_code());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView tv_promocode = view.findViewById(R.id.tv_promocode);
        TextView tv_value = view.findViewById(R.id.tv_value);
        TextView tv_description = view.findViewById(R.id.tv_description);
        tv_promocode.setText(promocodeLists.get(position).getPromo_code());
        tv_description.setText(promocodeLists.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return promocodeLists.size();
    }

}