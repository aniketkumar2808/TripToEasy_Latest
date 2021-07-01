package com.TripToEasy.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.TripToEasy.R;
import com.TripToEasy.bus.fragment.BusSearchFragment;
import com.TripToEasy.car.CarSearchFragment;
import com.TripToEasy.flight.fragment.FlightFragment;
import com.TripToEasy.holiday.fragment.HolidaySearchFragment;
import com.TripToEasy.hotel.fragment.HotelSearchFragment;
import com.TripToEasy.interfaces.ProfileFilterNotify;
import com.TripToEasy.model.parsingModel.PromoCodeInfo;
import com.TripToEasy.sight_seeing.fragment.SightSeeingSelectionFragment;
import com.TripToEasy.transfers.fragment.TransfersSearchFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @OnClick(R.id.iv_back)
    void backAction(){
        onBackPressed();
    }

    @BindView(R.id.tool_bar)
    Toolbar toolBar;

    @BindView(R.id.action_type)
    ImageView actionType;

    @BindView(R.id.sort_action)
    ImageView sortList;

    @OnClick(R.id.action_type)
    void actionType(){
        switch (actionValue){
            case 0:
                profileFilterNotify.notifyProfileView();
                break;
            case 1:
                profileFilterNotify.notifyFilter();
                break;
        }
    }

    @OnClick(R.id.sort_action)
    void sortAction(){
        profileFilterNotify.notifySort();
    }

    Integer actionValue = 0;
    ProfileFilterNotify profileFilterNotify;
    Bundle extras;
    ArrayList<PromoCodeInfo> promoList;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         extras = getIntent().getExtras();
        fragmentCallingAction(extras.getBundle("bundleWithValues")
                .getInt("calling_fragment"));
        }

    private void fragmentCallingAction(int i) {
        switch (i){
            case 1:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new FlightFragment(),null,false);
                break;
            case 2:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new HotelSearchFragment(), null, false);
                break;
            case 3:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new BusSearchFragment(), null, false);
                break;
            case 4:
                intentAndFragmentService.fragmentDisplay(this, R.id.main_frame,
                        new HolidaySearchFragment(), null, false);
                break;
            case 5:
                intentAndFragmentService.fragmentDisplay(this, R.id.main_frame,
                        new TransfersSearchFragment(), null, false);
               break;
            case 6:
                String hotelCityID,hotelCityName;
                hotelCityName=extras.getBundle("bundleWithValues").getString("cityname");
                hotelCityID=extras.getBundle("bundleWithValues").getString("cityid");
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,
                        new HotelSearchFragment(hotelCityID, hotelCityName,5), null, false);
                break;
            case 7:
                intentAndFragmentService.fragmentDisplay(this,R.id.main_frame,new SightSeeingSelectionFragment(),
                        null,false);

                break;
            case 8:
                intentAndFragmentService.fragmentDisplay(this, R.id.main_frame,
                        new CarSearchFragment(), null, false);
                break;
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    public void hideToolBar(int i)
    {
        switch (i){
            case 1:
                toolBar.setVisibility(View.VISIBLE);
                break;
            case 2:
                toolBar.setVisibility(View.GONE);
                break;
        }
    }

    public void toolbarActionIcon(Object object, Integer actionValue){
        profileFilterNotify = (ProfileFilterNotify) object;
        switch (actionValue){
            case 0:
                sortList.setVisibility(View.GONE);
                this.actionValue = actionValue;
                break;
            case 1:
                sortList.setVisibility(View.VISIBLE);
                this.actionValue = actionValue;
                break;

        }
    }

}