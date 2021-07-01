package com.TripToEasy.holiday.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.flight.fragment.PaymentFlight;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class HolidayBookingFragment extends BaseFragment implements WebInterface {
    int adultCount,childCount,totalTraveller;
    String packageId,holidayName,totalPrice;

    @BindView(R.id.tv_adults) TextView  tv_adults;
    @BindView(R.id.tv_child) TextView  tv_child;
    @BindView(R.id.tv_total) TextView  tv_total;
    @BindView(R.id.et_firstName) EditText et_firstName;
    @BindView(R.id.et_lastname) EditText  et_lastname;
    @BindView(R.id.s_gender) Spinner s_gender;
    List<String> genderList=new ArrayList<String>();
    @BindView(R.id.s_coluntryList) Spinner s_coluntryList;
    @BindView(R.id.et_dateOfTravel) EditText  et_dateOfTravel;
    @BindView(R.id.et_email) EditText  et_email;
    @BindView(R.id.et_userAddress) EditText  et_userAddress;
    @BindView(R.id.et_contactNo) EditText  et_contactNo;
    ArrayList<String> countryList = new ArrayList<>();
    WebServiceController webServiceController;
    Calendar myCalendar;



    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_holiday_booking;
    }

    public HolidayBookingFragment()
    {}

    @SuppressLint("ValidFragment")
    public HolidayBookingFragment(int adultCount, int childCount, String totalPrice,String packageId, String holidayName)
    {
        this.adultCount=adultCount;
        this.childCount=childCount;
        this.totalTraveller=adultCount+childCount;
        this.totalPrice=String.valueOf(Float.valueOf(totalPrice)*totalTraveller);
        this.packageId=packageId;
        this.holidayName=holidayName;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(), HolidayBookingFragment.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_adults.setText(String.valueOf(adultCount));
        tv_child.setText(String.valueOf(childCount));
        tv_total.setText(totalPrice);
        genderList.add("Male");
        genderList.add("Female");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, genderList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_gender.setAdapter(dataAdapter);
        RequestParams params = new RequestParams();
        webServiceController.postRequest(apiConstants.URL_COUNTRY_LIST,params,1);




        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        et_dateOfTravel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        }


    private void updateLabel()
    {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        et_dateOfTravel.setText(sdf.format(myCalendar.getTime()));
    }



        @OnClick(R.id.tv_confirmBooking)
        public  void bookHoliday()
        {
            if(et_firstName.getText().toString().length() == 0){
                commonUtils.toastShort("Please enter first name.", getActivity());
            }else  if(et_lastname.getText().toString().length() == 0){
                commonUtils.toastShort("Please enter last name.", getActivity());
            }
            else if(et_dateOfTravel.getText().toString().length() == 0){
                commonUtils.toastShort("Please select travel date", getActivity());
            }
            else if(et_contactNo.getText().toString().length() == 0){
                commonUtils.toastShort("Please enter Contact Number.", getActivity());
            }else if(et_contactNo.getText().toString().length() < 10){
                commonUtils.toastShort("Please enter Valid Contact Number.", getActivity());
            }else if(!isValidEmail(et_email.getText().toString())){
                commonUtils.toastShort("Please enter valid email.", getActivity());
            }
            else if(et_userAddress.getText().toString().length()==0){
                commonUtils.toastShort("Please enter address", getActivity());
            }else {

                try{
                JSONObject holidayBooking = new JSONObject();
                holidayBooking.put("first_name", et_firstName.getText().toString());
                holidayBooking.put("last_name", et_lastname.getText().toString());
                holidayBooking.put("gender", s_gender.getSelectedItem());
                holidayBooking.put("date_of_travel", et_dateOfTravel.getText().toString());
                holidayBooking.put("phone", et_contactNo.getText().toString());
                holidayBooking.put("email", et_email.getText().toString());
                holidayBooking.put("country", s_coluntryList.getSelectedItem());
                holidayBooking.put("pack_id", packageId);
                holidayBooking.put("no_adults",String.valueOf(adultCount));
                holidayBooking.put("no_child",String.valueOf(childCount));
                holidayBooking.put("total", totalPrice);
                holidayBooking.put("booking_source","PTBSID0000000004");
                holidayBooking.put("payment_method", "PNHB1");
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("passenger_data", holidayBooking.toString());

                    webServiceController.postRequest(
                            apiConstants.PRE_BOOKING_HOLIDAY,
                            requestParams,
                            2);
            }
            catch (JSONException e) {
            e.printStackTrace();
        }

            }
        }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    Integer status = 0;
    String url = null;

    @Override
    public void getResponse(String response, int flag) {


        switch (flag) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("country_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        countryList.add(jsonArray.getJSONObject(i).getString("name"));

                    }
                    s_coluntryList.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, R.id.spinn_text, countryList));
                    s_coluntryList.setSelection(89);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1)
                    {
                        status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url =dataobj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0){
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.main_frame, new PaymentFlight(url), null, false);
                }
                break;
        }

    }
}
