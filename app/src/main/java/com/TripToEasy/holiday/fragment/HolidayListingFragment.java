package com.TripToEasy.holiday.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.holiday.adapter.PackageListingAdapter;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.holiday.PackageMainModel;
import com.TripToEasy.utils.Global;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ptblr-1195 on 11/4/18.
 */

public class HolidayListingFragment extends BaseFragment {

    @BindView(R.id.package_listing)
    RecyclerView packageListing;

    @BindView(R.id.country_name)
    TextView countryNameText;

    @BindView(R.id.duration)
    TextView packageDurationText;

    @BindView(R.id.holiday_type)
    TextView holiday_type;

    String response, holidayType, tripDuration;
    List<PackageMainModel> packageMainModels = new ArrayList<PackageMainModel>();
    PackageListingAdapter packageListingAdapter;

    @SuppressLint("ValidFragment")
    public HolidayListingFragment(String response, String holidayType,
                                  String tripDuration) {
        this.response = response;
        this.holidayType = holidayType;
        this.tripDuration = tripDuration;
    }

    public HolidayListingFragment() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.holiday_listing_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(packageListing, getActivity());
        holiday_type.setText(holidayType);
        //packageDurationText.setText(tripDuration);

        packageListingAdapter = new PackageListingAdapter(
                getActivity(),
                HolidayListingFragment.this,
                packageMainModels);
        packageListing.setAdapter(packageListingAdapter);

        lodeDataToList();
    }
    String fare;
    private void lodeDataToList() {
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getInt("status") == 1){
                JSONArray dataArray = jsonObject.getJSONArray("data");
                int i=0;
                while (i<dataArray.length()){
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    fare = String.format("%.0f",Double.parseDouble(dataObject.getString("price"))/Double.parseDouble(Global.currencyValue));

                    packageMainModels.add(new PackageMainModel(
                            dataObject.getString("package_id"),
                            dataObject.getString("package_code"),
                            dataObject.getString("supplier_id"),
                            dataObject.getString("tour_types"),
                            dataObject.getString("package_name"),
                            dataObject.getString("package_tour_code"),
                            dataObject.getString("duration"),
                            dataObject.getString("package_description"),
                            "http://"+dataObject.getString("image"),
                            dataObject.getString("package_country"),
                            dataObject.getString("package_state"),
                            dataObject.getString("package_city"),
                            dataObject.getString("package_location"),
                            dataObject.getString("package_type"),
                            dataObject.getString("price_includes"),
                            dataObject.getString("deals"),
                            dataObject.getString("no_que"),
                            dataObject.getString("home_page"),
                            dataObject.getString("rating"),
                            dataObject.getString("status"),
                            fare,
                            dataObject.getString("display"),
                            dataObject.getString("domain_list_fk"),
                            dataObject.getString("top_destination")));
                    i++;
                }
            }else {
                commonUtils.toastShort(jsonObject.getString("message"), getActivity());
            }

            /**
             * Notify Adapter
             * */
            if(packageMainModels.size() != 0){
                packageListingAdapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void navigateToDetail(String packageId) {
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new HolidayDetailFragment(packageId), null, true);
    }

}