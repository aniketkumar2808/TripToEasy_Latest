package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.TripToEasy.R;
import com.TripToEasy.flight.adapter.CityListAdapter;
import com.TripToEasy.interfaces.CitySearch;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.CityList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ptblr-1195 on 27/2/18.
 */

public class CitySearchFragment extends BaseFragment {

    @BindView(R.id.city_list)
    RecyclerView cityList;

    @BindView(R.id.city_name)
    EditText cityNameSearch;

    @BindView(R.id.flight_direction)
    ImageView flightDirection;

    Integer requestType;
    CitySearch citySearch;
    List<CityList> cityLists = new ArrayList<CityList>();
    CityListAdapter cityListAdapter;

    @SuppressLint("ValidFragment")
    public CitySearchFragment(Object object, Integer i) {
        citySearch = (CitySearch)object;
        requestType = i;
    }

    public CitySearchFragment(){
        /**
         * Empty Constructor
         * */
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.city_list_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        dbAdapter.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dbAdapter!=null)
        {
            dbAdapter.close();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        commonUtils.linearLayout(cityList,getActivity());

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(cityNameSearch, InputMethodManager.SHOW_IMPLICIT);

        cityNameSearch.setFocusableInTouchMode(true);
        cityNameSearch.setFocusable(true);
        cityNameSearch.requestFocus();

        callImageLoader();

        if(requestType == 1 || requestType == 2 || requestType == 3 || requestType == 4){
            dbAdapter.open();
            cityLists = dbAdapter.getFlightTopCity(5,6);
            notifyAdapter();
        }

        if(requestType==5)
        {
            dbAdapter.open();
            cityLists = dbAdapter.getHotelCityData("India");
        }

        cityNameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cityLists.clear();
                if(cityNameSearch.getText().toString().length() != 0)
                {
                    if(requestType == 1 || requestType == 2 || requestType == 3 || requestType == 4){
                        cityLists = dbAdapter.getFlightCityData(cityNameSearch.getText().toString());
                    }else if(requestType == 5){
                        cityLists = dbAdapter.getHotelCityData(cityNameSearch.getText().toString());
                    }else if(requestType == 6 || requestType == 7){
                        cityLists = dbAdapter.getBusCityData(cityNameSearch.getText().toString());
                    }
                    if(cityLists.size() != 0){
                        // lode adapter
                        notifyAdapter();
                    }else {
                        commonUtils.toastShort("No Data Found",getActivity());
                        notifyAdapter();
                    }
                }else {
                    if(requestType == 1 || requestType == 2 || requestType == 3 || requestType == 4){
                        cityLists = dbAdapter.getFlightTopCity(5,6);
                    }
                    notifyAdapter();
                }
            }
        });
    }

    private void notifyAdapter() {
        cityListAdapter = new CityListAdapter(CitySearchFragment.this,
                getActivity(),cityLists);
        cityList.setAdapter(cityListAdapter);
    }

    private void callImageLoader() {
        switch (requestType){
            case 1:
                flightDirection.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_flight_from));
                break;
            case 2:
                flightDirection.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_flight_to));
                break;
            case 3:
                //TODO : need to add location icon
                break;
 	    default:
                    flightDirection.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_location));
                    break;
        }
    }

    public void notifySelection(String cityId, String searchCityName)
    {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        citySearch.citySearchResult(requestType, searchCityName, cityId);
        getActivity().onBackPressed();
    }

}
