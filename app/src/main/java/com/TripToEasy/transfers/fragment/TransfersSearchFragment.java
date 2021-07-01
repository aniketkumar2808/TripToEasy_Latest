package com.TripToEasy.transfers.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.holiday.interfaces.HolidayNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransfersSearchFragment extends BaseFragment implements WebInterface, HolidayNotify {

    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.tv_search)
    TextView tv_search;

    @OnClick(R.id.tv_location)
    void enterLocation(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new SearchLocationFragment(TransfersSearchFragment.this), null, true);
    }


    @OnClick(R.id.tv_search)
    void searchTransfers(){


        if(tv_location.getText().toString().equals("")){
            commonUtils.toastShort("Please enter locatiom",getActivity());
        }else {
            applicationPreference.setData(
                    applicationPreference.transferCityName,
                    tv_location.getText().toString());
            applicationPreference.setData(applicationPreference.transferCityId,
                    locationId);
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("from",tv_location.getText().toString());
                jsonObject.put("destination_id",locationId);
                //jsonObject.put("from","Bangalore");
                //jsonObject.put("destination_id","1262");
                jsonObject.put("from_date","");
                jsonObject.put("to_date","");

                RequestParams requestParams = new RequestParams();
                requestParams.put("transfer_search", jsonObject.toString());
                webServiceController.postRequest(
                        apiConstants.TRANSFERS_SEARCH,
                        requestParams,
                        1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public TransfersSearchFragment() {
        // Required empty public constructor
    }

    WebServiceController webServiceController;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transfers_search;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfers_search, container, false);
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                TransfersSearchFragment.this);

        if(applicationPreference.getData(applicationPreference.transferCityName).length() != 0) {
            tv_location.setText(applicationPreference.getData(applicationPreference.transferCityName));
            locationId = applicationPreference.getData(applicationPreference.transferCityId);
        }
    }

    @Override
    public void getResponse(String response, int flag) {
        switch (flag)
        {
            case 1:
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("status")==1){
                        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                                new TransfersResultFragment(response,tv_location.getText().toString()), null, true);
                    }else {
                        commonUtils.toastShort("Please Try Again",getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 2:

                break;
        }

    }
    String locationId = null;
    @Override
    public void countryId(String countryId, String countryName) {
        this.locationId = countryId;
        tv_location.setText(countryName);
    }


}

