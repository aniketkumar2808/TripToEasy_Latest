package com.TripToEasy.flight.adapter.multiCityList;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.flight.fragment.MultiCityFragment;
import com.TripToEasy.model.parsingModel.flight.multiCity.MultiCityParsing;

import java.util.List;

/**
 * Created by ptblr-1195 on 6/3/18.
 */

public class MultiCityMain extends CommonRecyclerAdapter {

    Activity activity;
    MultiCityFragment multiCityFragment;
    List<MultiCityParsing> multiCityParsing;

    public MultiCityMain(Activity activity,
                         MultiCityFragment multiCityFragment,
                         List<MultiCityParsing> multiCityParsing) {
        this.activity = activity;
        this.multiCityFragment = multiCityFragment;
        this.multiCityParsing = multiCityParsing;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.multi_city_item_one) {
            @Override
            public void onItemSelected(int position) {
                navigateToDetail(position);
            }
        };
    }

    private void navigateToDetail(int position) {
        multiCityFragment.callDetailFragment(
                multiCityParsing.get(position).getProvabAuthKey(),
                multiCityParsing.get(position).getBookingSource(),
                multiCityParsing.get(position).getToken(),
                position);
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        View view = holder.getView();
        TextView priceText = view.findViewById(R.id.price);
        TextView airlineRefundable = view.findViewById(R.id.refundable);
        TextView fareRule = view.findViewById(R.id.farerule);
        RecyclerView multiCityList = view.findViewById(R.id.multi_city_list);
        multiCityList.setLayoutManager(new LinearLayoutManager(activity));

        airlineRefundable.setText(multiCityParsing.get(position).getFlightIsRefundable());
       // priceText.setText(multiCityParsing.get(position).getPriceValue());
        priceText.setText(multiCityParsing.get(position).getPriceValue().split("\\.")[0]);

        MultiCityChild multiCityChild = new MultiCityChild(activity,
                multiCityParsing.get(position).getFlightDetailLists(), MultiCityMain.this);
        multiCityList.setAdapter(multiCityChild);

        fareRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiCityFragment.callFairRule( multiCityParsing.get(position).getProvabAuthKey(),
                        multiCityParsing.get(position).getToken());
            }
        });
    }

    @Override
    public int getItemCount() {
        return multiCityParsing.size();
    }

    public void clickAction(String token) {
        int i=0;
        while (i<multiCityParsing.size()){
            if(token.equals(multiCityParsing.get(i).getToken())){
                navigateToDetail(i);
                break;
            }
            i++;
        }
    }
}