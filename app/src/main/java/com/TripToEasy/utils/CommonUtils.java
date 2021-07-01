package com.TripToEasy.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.TripToEasy.R;
import com.TripToEasy.interfaces.AdvanceOptionNotify;
import com.TripToEasy.interfaces.DatePickerNotify;
import com.TripToEasy.utils.dialogs.DatePickerFragment;
import com.TripToEasy.utils.dialogs.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ptblr-1195 on 21/2/18.
 */

public class CommonUtils {

    DatePickerNotify datePickerNotify;
    Integer datePickerValue;

    public void linearLayout(RecyclerView recyclerViewId, Context context){
        recyclerViewId.setLayoutManager(new LinearLayoutManager(context));
    }

    public void horizontalLayout(RecyclerView recyclerViewId, Context context){
        recyclerViewId.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
    }

    public void toastLong(String data, Context context){
        Toast.makeText(context,data, Toast.LENGTH_LONG).show();
    }

    public void toastShort(String data, Context context){
        Toast.makeText(context,data, Toast.LENGTH_SHORT).show();
    }

    public void gridLayout(RecyclerView recyclerView, Context context, Integer gridCount){
        recyclerView.setLayoutManager(new GridLayoutManager(context, gridCount ));
    }

    public void callDatePicker(Fragment context, Object object,String date,
                               Integer datePickerValue) {

        datePickerNotify = (DatePickerNotify) object;
        this.datePickerValue = datePickerValue;

        DatePickerFragment datePickerFragment1 = new DatePickerFragment(datePickerValue);
        Calendar calender1 = Calendar.getInstance();
        Bundle args1 = new Bundle();
        if(!date.equals(""))
        {
            args1.putInt("year", Integer.valueOf(date.split("-")[0]));
            args1.putInt("month", Integer.valueOf(date.split("-")[1])-1);
            args1.putInt("day", Integer.valueOf(date.split("-")[2]));
        }else
        {
            args1.putInt("year", calender1.get(Calendar.YEAR));
            args1.putInt("month", calender1.get(Calendar.MONTH));
            args1.putInt("day", calender1.get(Calendar.DAY_OF_MONTH));
        }
        datePickerFragment1.setArguments(args1);
        datePickerFragment1.setCallBack(onDateSetListener1);
        datePickerFragment1.show(context.getFragmentManager(), "date picker");
    }

    public void callDatePicker(Fragment context, Object object,String date,String checkoutDate,
                                Integer datePickerValue) {

        datePickerNotify = (DatePickerNotify) object;
        this.datePickerValue = datePickerValue;

        DatePickerFragment datePickerFragment1 = new DatePickerFragment(datePickerValue,date,checkoutDate);
        Calendar calender1 = Calendar.getInstance();
        Bundle args1 = new Bundle();
        if(!date.equals(""))
        {
            args1.putInt("year", Integer.valueOf(checkoutDate.split("-")[0]));
            args1.putInt("month", Integer.valueOf(checkoutDate.split("-")[1])-1);
            args1.putInt("day", Integer.valueOf(checkoutDate.split("-")[2]));
        }else
        {
            args1.putInt("year", calender1.get(Calendar.YEAR));
            args1.putInt("month", calender1.get(Calendar.MONTH));
            args1.putInt("day", calender1.get(Calendar.DAY_OF_MONTH));
        }
        datePickerFragment1.setArguments(args1);
        datePickerFragment1.setCallBack(onDateSetListener1);
        datePickerFragment1.show(context.getFragmentManager(), "date picker");
    }

    DatePickerDialog.OnDateSetListener onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            monthOfYear++;
            String startDate = year + "-" + monthOfYear + "-" + dayOfMonth;
            startDate = new DateTimeUtils().convertToBasicFormat(startDate);

            datePickerNotify.notifyDate(startDate, datePickerValue);
        }
    };

    public void showAdvanceOptionDialog(Activity activity,
                                        String previousSelected,
                                        Object object){
        final AdvanceOptionNotify advanceOptionNotify = (AdvanceOptionNotify) object;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.advance_option);

        TextView economy, business, first,all;
        all = dialog.findViewById(R.id.text_all);
        economy = dialog.findViewById(R.id.text_economy);
        business = dialog.findViewById(R.id.text_business);
        first = dialog.findViewById(R.id.text_first);

        switch (previousSelected){
            case "All":
                all.setTextColor(activity.getResources().getColor(R.color.orange_text));
                break;
            case "Economy":
                economy.setTextColor(activity.getResources().getColor(R.color.orange_text));
                break;
            case "Business":
                business.setTextColor(activity.getResources().getColor(R.color.orange_text));
                break;
            case "First":
                first.setTextColor(activity.getResources().getColor(R.color.orange_text));
                break;
        }

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                advanceOptionNotify.notifyAdvance("All");
            }
        });


        economy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                advanceOptionNotify.notifyAdvance("Economy");
            }
        });

        business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                advanceOptionNotify.notifyAdvance("Business");
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                advanceOptionNotify.notifyAdvance("First");
            }
        });
        dialog.show();
    }

    public static String conDateToStr(Date lDate, String format) {
        String lRetVal = "";
        try {
            SimpleDateFormat lDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            lRetVal = lDateFormat.format(lDate.getTime());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return lRetVal;
        }

    }


    public static Date convertStrToDate(String strDate, String format) {
        Date lRetVal = new Date();
        try {
            SimpleDateFormat lDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
            lRetVal = lDateFormat.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return lRetVal;
        }
    }

}