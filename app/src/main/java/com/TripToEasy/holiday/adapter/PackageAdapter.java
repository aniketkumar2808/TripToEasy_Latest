package com.TripToEasy.holiday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.holiday.fragment.HolidaySearchFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.holiday.PackageListModel;

import java.util.List;

/**
 * Created by ptblr-1195 on 11/4/18.
 */

public class PackageAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HolidaySearchFragment holidaySearchFragment;
    List<PackageListModel> packageListModels;

    public PackageAdapter(Activity activity,
                          HolidaySearchFragment holidaySearchFragment,
                          List<PackageListModel> packageListModels) {
        this.activity = activity;
        this.holidaySearchFragment = holidaySearchFragment;
        this.packageListModels = packageListModels;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.city_list_item) {
            @Override
            public void onItemSelected(int position) {
                holidaySearchFragment.updatePackageDetails(
                        packageListModels.get(position).getPackageName(),
                        packageListModels.get(position).getPackageTypeId(),
                        packageListModels.get(position).getDuration());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, int position) {
        View view = viewHolder.getView();
        TextView packageName = view.findViewById(R.id.place_name);
        packageName.setText(packageListModels.get(position).getPackageName());
    }

    @Override
    public int getItemCount() {
        return packageListModels.size();
    }
}