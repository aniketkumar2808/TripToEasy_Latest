package com.TripToEasy.hotel.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.TripToEasy.main.activity.BaseActivity;

/**
 * Created by ptblr-1195 on 21/3/18.
 */

public class HotelSupportActivity extends BaseActivity {
    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
