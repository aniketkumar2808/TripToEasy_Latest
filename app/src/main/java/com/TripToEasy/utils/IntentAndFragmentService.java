package com.TripToEasy.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

/**
 * Created by Manoj DB on 11/7/2015.
 */

public class IntentAndFragmentService {

    private FragmentManager fragmentManager = null;

    public void fragmentDisplay(FragmentActivity context, int viewID, Fragment fragmentName, Bundle values, boolean forBackPress) {
        try {
            if (fragmentName != null) {
                if (values != null && values.size() > 0) {
                    fragmentName.setArguments(values);
                }
                fragmentManager = context.getSupportFragmentManager();
                if (forBackPress)
                {
                    fragmentManager.beginTransaction()
                            .add(viewID, fragmentName).addToBackStack(null).commitAllowingStateLoss();
                } else {
                        fragmentManager.beginTransaction()
                                .replace(viewID, fragmentName).commitAllowingStateLoss();
                }
            } else {
            }
        } catch (NullPointerException e) {
        }
    }

    public void intentDisplay(Context currentActivity, Class callingActivity, Bundle values) {
        try {
            Intent intent = new Intent(currentActivity, callingActivity);
            if (values != null && values.size() > 0 && !values.isEmpty()) {
                intent.putExtra("bundleWithValues", values);
            }
            currentActivity.startActivity(intent);
        } catch (NullPointerException e) {
        }
    }

}