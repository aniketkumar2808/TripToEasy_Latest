package com.TripToEasy.holiday.fragment;

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
import com.TripToEasy.holiday.adapter.HolidayCityAdapter;
import com.TripToEasy.holiday.interfaces.HolidayNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.holiday.HolidayCity;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ptblr-1195 on 10/4/18.
 */

public class HolidayCitySearch extends BaseFragment implements WebInterface{

    @BindView(R.id.country_name)
    EditText countryName;

    @BindView(R.id.country_list)
    RecyclerView countryList;

    WebServiceController webServiceController;
    List<HolidayCity> holidayCities = new ArrayList<HolidayCity>();
    HolidayCityAdapter holidayCityAdapter;
    HolidayNotify holidayNotify;

    @SuppressLint("ValidFragment")
    public HolidayCitySearch(Object object) {
        holidayNotify = (HolidayNotify) object;
    }

    public HolidayCitySearch() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.holiday_city_search_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                HolidayCitySearch.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(countryList, getActivity());
        holidayCityAdapter = new HolidayCityAdapter(getActivity(),
                HolidayCitySearch.this, holidayCities);
        countryList.setAdapter(holidayCityAdapter);

        webServiceController.cancelPreviousRequest();
        /*RequestParams requestParams = new RequestParams();
        requestParams.put("term", "i");
        webServiceController.postRequest(
                apiConstants.HOLIDAY_COUNTRY_SEARCH,
                requestParams,
                9999);*/

        countryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                webServiceController.cancelPreviousRequest();
                RequestParams requestParams = new RequestParams();
                requestParams.put("term", countryName.getText().toString());
                webServiceController.postRequest(
                        apiConstants.HOLIDAY_COUNTRY_SEARCH,
                        requestParams,
                        9999);
            }
        });
    }

    @Override
    public void getResponse(String response, int flag) {
        holidayCities.clear();
        try{
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getInt("status")==1){
                JSONObject pkgObj=jsonObject.getJSONObject("package");
                JSONArray jsonArray = pkgObj.getJSONArray("package_city");
                int i=0;
                while (i < jsonArray.length()){
                    //JSONObject dataObject = jsonArray.getJSONObject(i);
                    holidayCities.add(new HolidayCity(jsonArray.getString(i)));
                    i++;
                }
            }

            // Notify Adapter
            holidayCityAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            try {
                holidayCityAdapter.notifyDataSetChanged();
                JSONObject jsonObject = new JSONObject(response);
                commonUtils.toastShort(jsonObject.getString("message"), getActivity());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }

    public void notifyCountryId(String cityId, String countryName) {
        holidayNotify.countryId(cityId, countryName);
        Global.hideKeyboard(getActivity());
        getActivity().onBackPressed();
    }

}