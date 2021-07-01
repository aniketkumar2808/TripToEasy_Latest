package com.TripToEasy.sight_seeing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.sight_seeing.NotifySearchTermResponse;
import com.TripToEasy.transfers.fragment.SearchLocationFragment;
import com.TripToEasy.utils.webservice.ApiConstants;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class SightSeeingSelectionFragment extends BaseFragment implements WebInterface, NotifySearchTermResponse {

    @BindView(R.id.tv_city)
    TextView tv_city;

    WebServiceController webServiceController;

 @OnClick(R.id.searchBtn)
    public void searchActivities() {

     if(tv_city.getText().toString().equals(""))
     {
         commonUtils.toastShort("Please enter locatiom",getActivity());
     }else {
         applicationPreference.setData(
                 applicationPreference.activityCityName,
                 tv_city.getText().toString());
         applicationPreference.setData(applicationPreference.activityCityId,
                 locationId);

         String param = "?from=" + tv_city.getText().toString() + "&destination_id=" + locationId + "&category_id=0&from_date&to_date";
             webServiceController.getRequest(ApiConstants.SIGHT_SEEING_SEARCH + param, 1, true);
             Log.e("Site seen search ",ApiConstants.SIGHT_SEEING_SEARCH + param);
     }    }


    @OnClick(R.id.ll_city)
    public void citySearch() {
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new SearchLocationFragment(SightSeeingSelectionFragment.this,"activities"), null, true);
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.sight_seeing_selection_lay;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webServiceController  = new WebServiceController(getContext(), this);

        if(applicationPreference.getData(applicationPreference.activityCityName).length() != 0)
        {
            tv_city.setText(applicationPreference.getData(applicationPreference.activityCityName));
            locationId = applicationPreference.getData(applicationPreference.activityCityId);
        }

    }

    @Override
    public void getResponse(String response, int flag) {
        try {

            JSONObject lObject = new JSONObject(response);
            Bundle bundle = new Bundle();
            bundle.putInt("search_id", lObject.getInt("search_id"));
            intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame, new SightSeeingListFragment(), bundle, true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    String locationId = null;

    @Override
    public void countryId(String countryId, String countryName) {
        this.locationId = countryId;
        tv_city.setText(countryName);
    }
}