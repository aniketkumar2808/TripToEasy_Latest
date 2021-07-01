package com.TripToEasy.main.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;

import java.util.List;

/**
 * Created by ptblr-1195 on 27/2/18.
 */

public class PassangerListAdapter extends CommonRecyclerAdapter {

    Activity activity;
    List<String> passangerList;

    public PassangerListAdapter(
            Activity activity,
            List<String> passangerList) {

        this.activity = activity;
        this.passangerList = passangerList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.psngr_list_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView psngrName = view.findViewById(R.id.psngr_name);
        psngrName.setText((position+1)+".  "+passangerList.get(position));
    }

    @Override
    public int getItemCount() {
        return passangerList.size();
    }

}