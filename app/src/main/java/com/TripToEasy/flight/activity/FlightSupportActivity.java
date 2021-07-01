package com.TripToEasy.flight.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.TripToEasy.R;
import com.TripToEasy.flight.fragment.FlightDetailFragment;
import com.TripToEasy.main.activity.BaseActivity;

import butterknife.OnClick;

public class FlightSupportActivity extends BaseActivity {

    @OnClick(R.id.back_button)
    void backAction(){
        onBackPressed();
    }

    String tax = null, adultPrice = null, childPrice = null,
            infantPrice = null, authKey, bookingSource, token, tokenKey,
            return_authKey="", return_bookingSource="", return_token="",return_tokenKey="",
    source = null, destination = null, returnDate=null,travelDate=null,detailResponse = null,
            totalPrice = null, airlineUrl = null, detailResponseTwo = null,diff_amount=null;
    int adultCount = 0, childCount = 0, infantCount = 0, actionValue = 0;

    @Override
    protected int getLayoutResource() {
        return R.layout.support_activity;
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        switch (extras.getBundle("bundleWithValues").getInt("from_view")){
            case 1:
                tax = extras.getBundle("bundleWithValues").getString("tax");
                adultPrice = extras.getBundle("bundleWithValues").getString("adult_price");
                childPrice = extras.getBundle("bundleWithValues").getString("child_price");
                infantPrice = extras.getBundle("bundleWithValues").getString("infant_price");
                authKey = extras.getBundle("bundleWithValues").getString("auth_key");
                bookingSource = extras.getBundle("bundleWithValues").getString("booking_source");
                token = extras.getBundle("bundleWithValues").getString("token");
                tokenKey = extras.getBundle("bundleWithValues").getString("tokenKey");
                return_authKey = extras.getBundle("bundleWithValues").getString("return_auth_key");
                return_bookingSource = extras.getBundle("bundleWithValues").getString("return_booking_source");
                return_token = extras.getBundle("bundleWithValues").getString("return_token");
                return_tokenKey = extras.getBundle("bundleWithValues").getString("return_tokenKey");
                source = extras.getBundle("bundleWithValues").getString("source");
                destination = extras.getBundle("bundleWithValues").getString("destination");
                travelDate=extras.getBundle("bundleWithValues").getString("depart_date");
                returnDate=extras.getBundle("bundleWithValues").getString("return_date");
                detailResponse = extras.getBundle("bundleWithValues").getString("detail_response");
                totalPrice = extras.getBundle("bundleWithValues").getString("total_price");
                airlineUrl = extras.getBundle("bundleWithValues").getString("airline_url");
                detailResponseTwo = extras.getBundle("bundleWithValues").getString("detail_response_two");
                adultCount = extras.getBundle("bundleWithValues").getInt("adult_count");
                childCount = extras.getBundle("bundleWithValues").getInt("child_count");
                infantCount = extras.getBundle("bundleWithValues").getInt("infant_count");
                diff_amount = extras.getBundle("bundleWithValues").getString("diff_amount");
                intentAndFragmentService.fragmentDisplay(this,R.id.support_frame,
                        new FlightDetailFragment(tax, adultPrice, childPrice, infantPrice,
                                authKey, bookingSource, token, tokenKey,
                                return_authKey, return_bookingSource, return_token, return_tokenKey,
                                source,
                                destination, travelDate,returnDate,adultCount, childCount,
                                infantCount,detailResponse, totalPrice,
                                airlineUrl, detailResponseTwo,diff_amount),
                        null, false);
                break;
            case 2:

                break;
        }
    }
}
