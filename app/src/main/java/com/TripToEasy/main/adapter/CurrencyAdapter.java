package com.TripToEasy.main.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TripToEasy.LandingActivityNew;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.CurrencyConverter;
import com.TripToEasy.utils.Global;

import java.util.List;

/**
 * Created by ptblr-1195 on 20/3/18.
 */

public class CurrencyAdapter extends CommonRecyclerAdapter {
    Activity activity;
    List<CurrencyConverter> currencyList;
    int frame;
    LandingActivityNew fragment;


    public CurrencyAdapter(Activity activity,
                           LandingActivityNew fragment,
                           List<CurrencyConverter> currencyList, int frame) {
        this.activity = activity;
        this.currencyList = currencyList;
        this.fragment=fragment;
        this.frame=frame;
        Log.e("CurrenctList","insideCurrencyAdapter- "+currencyList);
    }




    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.spinner_item_curr) {
            @Override
            public void onItemSelected(int position) {
                Global.currencyIcon=currencyList.get(position).getCurrency_symbol();
                if(frame==1){
                    fragment.setCurrency(currencyList.get(position).getCurrency_symbol(),
                            currencyList.get(position).getCountry(),
                            currencyList.get(position).getValue());
                }
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        TextView currencyName = view.findViewById(R.id.spinn_text);
        ImageView iv_flag=view.findViewById(R.id.iv_flag);
        currencyName.setText(currencyList.get(position).getCountry());
        switch (currencyList.get(position).getCountry()){
            case "AUD" : iv_flag.setImageResource(R.drawable.flag_australia);
                break;
            case "SAR" : iv_flag.setImageResource(R.drawable.flag_saudi_arabia);
                break;
            case "GBP" : iv_flag.setImageResource(R.drawable.flag_united_kingdom);
                break;
            case "INR" : iv_flag.setImageResource(R.drawable.flag_india);
                break;
            case "USD" : iv_flag.setImageResource(R.drawable.flag_united_states_of_america);
                break;
            case "CAD" : iv_flag.setImageResource(R.drawable.cad);
                break;
            case "BDT" : iv_flag.setImageResource(R.drawable.bdt);
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
