package com.TripToEasy.holiday.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TripToEasy.utils.webservice.ApiConstants;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.holiday.fragment.HolidayListingFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.holiday.PackageMainModel;
import com.TripToEasy.utils.Global;

import java.util.List;

/**
 * Created by ptblr-1195 on 11/4/18.
 */

public class PackageListingAdapter extends CommonRecyclerAdapter {
    ApiConstants apiConstants;
    Activity activity;
    HolidayListingFragment holidayListingFragment;
    List<PackageMainModel> packageMainModels;

    public PackageListingAdapter(Activity activity,
                                 HolidayListingFragment holidayListingFragment,
                                 List<PackageMainModel> packageMainModels) {
        this.activity = activity;
        this.holidayListingFragment = holidayListingFragment;
        this.packageMainModels = packageMainModels;
        apiConstants=new ApiConstants();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.holiday_listing_item) {
            @Override
            public void onItemSelected(int position) {
                holidayListingFragment.navigateToDetail(
                        packageMainModels.get(position).getPackageId());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, int i) {
        View view = viewHolder.getView();
        TextView packageName, packageCode, packageAmount, packageDuration,
                packageLocation, packageType, viewMore;
        ImageView packageImage;

        packageName = view.findViewById(R.id.package_name);
        packageCode = view.findViewById(R.id.package_code);
        packageAmount = view.findViewById(R.id.package_price);
        packageDuration = view.findViewById(R.id.package_duration);
        packageLocation = view.findViewById(R.id.location_name);
        packageType = view.findViewById(R.id.package_type);
        viewMore = view.findViewById(R.id.view_details);

        packageImage = view.findViewById(R.id.package_image);

        Picasso.get()
                .load(packageMainModels.get(i).getImage())
                .into(packageImage);

        packageName.setText(packageMainModels.get(i).getPackageName());
        packageCode.setText(packageMainModels.get(i).getPackageCode());
        packageAmount.setText(Global.currencySymbol+" "
                +packageMainModels.get(i).getPrice());
        packageDuration.setText(packageMainModels.get(i).getDuration()+ " Days");
        packageLocation.setText(packageMainModels.get(i).getPackageLocation());
        packageType.setText(packageMainModels.get(i).getPackageType());

    }

    @Override
    public int getItemCount() {
        return packageMainModels.size();
    }
}
