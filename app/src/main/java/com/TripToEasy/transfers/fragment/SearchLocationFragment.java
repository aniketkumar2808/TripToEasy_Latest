package com.TripToEasy.transfers.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.holiday.interfaces.HolidayNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.holiday.HolidayCity;
import com.TripToEasy.sight_seeing.NotifySearchTermResponse;
import com.TripToEasy.transfers.adapter.TransfersCityAdapter;
import com.TripToEasy.utils.webservice.ApiConstants;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class SearchLocationFragment extends BaseFragment implements WebInterface
{
    @BindView(R.id.loc_name) EditText locationName;
    @BindView(R.id.loc_list) RecyclerView locationList;
    WebServiceController webServiceController;
    List<HolidayCity> holidayCities = new ArrayList<HolidayCity>();
    TransfersCityAdapter transferCityAdapter;
    HolidayNotify holidayNotify;
    NotifySearchTermResponse searchTermResponse;
    String from="transfer";

    public SearchLocationFragment()
    {
    }

    @SuppressLint("ValidFragment")
    public SearchLocationFragment(Object object)
    {
        holidayNotify = (HolidayNotify) object;
    }

    @SuppressLint("ValidFragment")
    public SearchLocationFragment(Object object,String from)
    {
        this.from=from;
        if(from.equalsIgnoreCase("transfer"))
        {
            holidayNotify = (HolidayNotify) object;

        }
        else {
            searchTermResponse = (NotifySearchTermResponse) object;

        }
    }


    @Override
    protected int getLayoutResource()
    {
        return R.layout.fragment_search_location;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                SearchLocationFragment.this);

        commonUtils.linearLayout(locationList, getActivity());
        transferCityAdapter = new TransfersCityAdapter(getActivity(),
                SearchLocationFragment.this, holidayCities);
        locationList.setAdapter(transferCityAdapter);

        locationName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(from.equalsIgnoreCase("transfer"))
                {
                    webServiceController.cancelPreviousRequest();
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("char", locationName.getText().toString());
                    webServiceController.postRequest(apiConstants.TRANSFER_LOC_SEARCH, requestParams, 1);
                }
                else
                {
                    webServiceController.cancelPreviousRequest();
                    webServiceController.getRequest(ApiConstants.SIGHT_SEEING_CITY_SEARCH + "?term=" + locationName.getText().toString(), 2, false);

                }
            }
        });
    }

    @Override
    public void getResponse(String response, int flag) {
        switch (flag)
        {
            case 1:
                try {
                    holidayCities.clear();

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            holidayCities.add(new HolidayCity(
                                    jsonArray.getJSONObject(i).getString("city_name"),
                                    jsonArray.getJSONObject(i).getString("id")));
                        }
                    } else {
                        commonUtils.toastShort("Please Try Again", getActivity());
                    }
                    transferCityAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    transferCityAdapter.notifyDataSetChanged();
                }
                break;
            case 2:
                try
                {
                    holidayCities.clear();

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            holidayCities.add(new HolidayCity(
                                    jsonArray.getJSONObject(i).getString("value"),
                                    jsonArray.getJSONObject(i).getString("id")));
                        }
                    } else {
                        commonUtils.toastShort("Please Try Again", getActivity());
                    }
                    transferCityAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    transferCityAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void notifyCountryId(String cityId, String countryName) {
        if(from.equalsIgnoreCase("transfer"))
        {
            holidayNotify.countryId(cityId, countryName);

        }
        else {
            searchTermResponse.countryId(cityId, countryName);


        }
        getActivity().onBackPressed();
    }
}
