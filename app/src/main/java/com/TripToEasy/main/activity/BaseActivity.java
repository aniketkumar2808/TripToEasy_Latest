package com.TripToEasy.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.TripToEasy.utils.ApplicationPreference;
import com.TripToEasy.utils.CommonUtils;
import com.TripToEasy.utils.IntentAndFragmentService;
import com.TripToEasy.utils.webservice.ApiConstants;

import butterknife.ButterKnife;

/**
 * Created by manoj on 09-02-2018.
 */

public abstract class BaseActivity extends PermissionActivity {

    protected abstract int getLayoutResource();
    public ApplicationPreference applicationPreference;
    public IntentAndFragmentService intentAndFragmentService;
    public ApiConstants apiConstants;
    public CommonUtils commonUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(getLayoutResource());
        }catch (Exception e){
            e.printStackTrace();
        }
        ButterKnife.bind(this);

        applicationPreference = new ApplicationPreference(this);
        intentAndFragmentService = new IntentAndFragmentService();
        apiConstants = new ApiConstants();
        commonUtils = new CommonUtils();
    }

}