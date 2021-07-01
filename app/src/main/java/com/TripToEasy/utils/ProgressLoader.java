package com.TripToEasy.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.TripToEasy.R;

/**
 * Created by user on 8/8/2016.
 */

public class ProgressLoader {

    Dialog pDialog;

    public void ShowProgress(Context context) {
        // TODO Auto-generated method stub
        pDialog = new Dialog(context, android.R.style.Theme_Translucent);
        pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //here we set layout of progress dialog
        pDialog.setContentView(R.layout.dialog_common_progress);
//        pDialog.setCancelable(false);
        if(pDialog!=null)
        {
            pDialog.show();
        }

    }

    public void DismissProgress(){
        if(pDialog!=null)
        {
            pDialog.dismiss();
        }
    }

}
