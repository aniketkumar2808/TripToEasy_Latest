package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.TripToEasy.R;
import com.TripToEasy.interfaces.DatePickerNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.utils.ProgressLoader;
import com.TripToEasy.utils.dialogs.DateTimeUtils;
import com.TripToEasy.utils.webservice.ApiConstants;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ptblr-1195 on 2/5/18.
 */

public class FairCalendarFragment extends BaseFragment implements WebInterface{

    Calendar mCalendar;
    ProgressLoader progressLoader;
    ApiConstants apiConstants;
    WebServiceController webServiceController;
    int currentFlag = 1;
    String searchDate=null;
    String depart,origin;
    String startDate="",sel_startDate="";
    DatePickerNotify datePickerNotify;
    Integer datePickerValue;
    String monthToday="";
    public FairCalendarFragment() {
        /**
         * Empty Constructor
         * */
    }

    @SuppressLint("ValidFragment")
    public FairCalendarFragment(String from, String to,Object object,Integer datePickerValue) {
        depart=from;
        origin =to;
        this.datePickerValue = datePickerValue;
        datePickerNotify = (DatePickerNotify) object;
    }

    @SuppressLint("ValidFragment")
    public FairCalendarFragment(String from, String to,Object object,Integer datePickerValue,String startDate) {
        depart=from;
        origin =to;
        this.datePickerValue = datePickerValue;
        this.sel_startDate = startDate;
        datePickerNotify = (DatePickerNotify) object;
    }

    @BindView(R.id.month_value)
    TextView currentMonth;

    @BindView(R.id.calendar_view)
    CompactCalendarView calendar_view;

    @OnClick(R.id.bt_done)
    void selectDate(){
        if(startDate.equals("")){
            commonUtils.toastShort("Select a date",getActivity().getApplicationContext());
            return;
        }
        startDate = new DateTimeUtils().convertToBasicFormat(startDate);
        if(datePickerValue==2)
        {
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sel_startDate);
                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
                datePickerNotify.notifyDate(startDate, datePickerValue);
                getActivity().onBackPressed();
                /*if(date2.after(date1))
                {
                    datePickerNotify.notifyDate(startDate, datePickerValue);
                    getActivity().onBackPressed();
                }else
                {
                    commonUtils.toastShort("Return Date cannot be less than Departure Date",getActivity().getApplicationContext());
                    return;
                }*/
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else
        {
            datePickerNotify.notifyDate(startDate, datePickerValue);
            getActivity().onBackPressed();
        }

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fare_calendar_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCalendar = Calendar.getInstance();
        progressLoader = new ProgressLoader();
        apiConstants=new ApiConstants();
        webServiceController=new WebServiceController(getActivity(),FairCalendarFragment.this);

        searchDate = mCalendar.get(Calendar.YEAR)+"-"+
                (mCalendar.get(Calendar.MONTH)+1)+"-"+mCalendar.get(Calendar.DAY_OF_MONTH);

        monthToday=convertDateFormat(searchDate);

        currentMonth.setText(monthToday);

        calendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(java.util.Date dateClicked) {
               /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate=sdf.format(dateClicked);*/
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String currentdate = df.format(mCalendar.getTime());
                String startdate1=df.format(dateClicked);
                if(Integer.parseInt(startdate1)<Integer.parseInt(currentdate)){
                    commonUtils.toastShort("Select proper date",getActivity().getApplicationContext());
                    startDate="";
                }else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    startDate=sdf.format(dateClicked);
                    //commonUtils.toastShort("Selected Date : "+startDate,getActivity().getApplicationContext());
                }
            }

            @Override
            public void onMonthScroll(java.util.Date firstDayOfNewMonth) {
                int month= firstDayOfNewMonth.getMonth();
                String data = firstDayOfNewMonth.toString();
                searchDate = data.split(" ")[5]+"-"
                        +(firstDayOfNewMonth.getMonth()+1)+"-"+data.split(" ")[2];
                currentMonth.setText(data.split(" ")[1]+" "+data.split(" ")[5]);
                currentFlag = firstDayOfNewMonth.getDay();
                if(month>=mCalendar.get(Calendar.MONTH)){
                    if (!((data.split(" ")[1] + " " + data.split(" ")[5]).equals(monthToday))) {
                        try {
                            if (calendar_view.getEvents(firstDayOfNewMonth).get(0).getEventName().equals("")) {
                                loadData(firstDayOfNewMonth.getDay());
                            }
                        } catch (IndexOutOfBoundsException e) {
                            loadData(firstDayOfNewMonth.getDay());
                        }
                    }
                }
            }

        });

        loadData(1);
    }

    private void loadData(int flag){
        String requestUrl = apiConstants.FAIR_CALENDAR+"from="+depart
                +"&to="+origin+"&depature="+searchDate+"&adult="+"1"
                +"&carrier="+"";
        webServiceController.getRequest(requestUrl, flag, true);
    }

    @Override
    public void getResponse(String response, int flag) {
        //parse data if both match
        if(currentFlag == flag){
            // calendar_view.removeAllEvents();
            try{
                JSONObject lResponse = new JSONObject(response);
                JSONObject lData = lResponse.getJSONObject("data");
                JSONArray lJsonArray = lData.getJSONArray("day_fare_list");
                List<Event> eventList = new ArrayList<>();
                eventList.clear();
                Event lEvent;
                SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < lJsonArray.length(); i++) {
                    JSONObject lObject = lJsonArray.getJSONObject(i);
                    Date lDate = lSimpleDateFormat.parse(lObject.getString("start"));
                    lEvent = new Event(getResources().getColor(R.color.color_absent), lDate.getTime(), lObject.getString("title"));
                    eventList.add(lEvent);

                }

                calendar_view.addEvents(eventList);


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String convertDateFormat(String date_yyyymmdd){
        String date_mmmdd=null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(date_yyyymmdd);
            SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy",Locale.ENGLISH);
            date_mmmdd = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date_mmmdd;
    }

}