package com.TripToEasy.utils.dialogs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.TripToEasy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;
    int datePickValue;
    String checkIn,checkout;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public DatePickerFragment(Integer datePickerValue) {

    }
    @SuppressLint("ValidFragment")
    public DatePickerFragment(int datePickValue,String checkIn,String checkout) {
        this.datePickValue=datePickValue;
        this.checkIn = checkIn;
        this.checkout = checkout;
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),R.style.DialogTheme ,ondateSet, year, month, day);

        Calendar cal= Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
        if(datePickValue==2)
        {
            String maxDate = "" + day + "-" + (month+1) + "-" + year;
            try {
                if(!checkIn.equals(""))
                {
                    String minDate = "" + Integer.valueOf(checkIn.split("-")[2]) + "-" + Integer.valueOf(checkIn.split("-")[1]) + "-" + Integer.valueOf(checkIn.split("-")[0]);
                    datePickerDialog.getDatePicker().setMinDate(dateFormat.parse(minDate).getTime());
                }else
                {
                    datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                }
                datePickerDialog.getDatePicker().setMaxDate(dateFormat.parse(maxDate).getTime()+864000000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else
        {
            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        }


        return datePickerDialog ;
    }
}  