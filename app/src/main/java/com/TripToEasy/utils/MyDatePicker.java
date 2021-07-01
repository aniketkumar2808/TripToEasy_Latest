package com.TripToEasy.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

import com.TripToEasy.R;

import java.util.Calendar;

/**
 * Created by ptblr-1174 on 17/5/18.
 */


public  class MyDatePicker extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    Context context;
    int action=0;
    Calendar c;
    @SuppressLint("ValidFragment")
    public MyDatePicker(Context context,int action){
        this.context=context;
        this.action=action;
    }

    @SuppressLint("ValidFragment")
    public MyDatePicker(Context context){
        this.context=context;
    }

    public MyDatePicker(){

    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        if(action!=0)
        {
            if(action==1)
            {
                c.set(Calendar.YEAR,year-12);
            }
            if(action==2)
            {
                c.set(Calendar.YEAR,year-2);
            }
        }
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        if(action!=0)
        {
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        }

        if(action==2)
        {
            c.set(Calendar.YEAR,year-12);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        }

        if(action==3)
        {
            c.set(Calendar.YEAR,year-2);
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        }

        if (Global.dateset_flag==2) {
            dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        }
        return  dialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        if(Global.dateset_flag==1){
            EditText txtView = (EditText) ((Activity)context).findViewById(R.id.et_dob);
            txtView.setText(day+"-"+(month+1)+"-"+year);
        }
        if (Global.dateset_flag==2) {
            EditText txtView = (EditText) ((Activity)context).findViewById(R.id.et_expiry);
            txtView.setText(day+"-"+(month+1)+"-"+year);
        }
        //btnDate.setText(ConverterDate.ConvertDate(year, month + 1, day));
    }
}
