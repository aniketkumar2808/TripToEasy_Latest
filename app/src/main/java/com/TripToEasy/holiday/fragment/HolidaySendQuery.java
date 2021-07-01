package com.TripToEasy.holiday.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.requestModel.holiday.SendQuery;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ptblr-1195 on 18/4/18.
 */

public class HolidaySendQuery extends BaseFragment implements WebInterface{

    @BindView(R.id.package_name)
    TextView packageName;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.contact_number)
    EditText contactNumber;

    @BindView(R.id.email_id)
    EditText emailId;

    @BindView(R.id.departure_place)
    EditText departurePlace;

    @BindView(R.id.message)
    EditText message;

    @OnClick(R.id.send_query)
    void sendQuery(){
        if(name.getText().toString().length() == 0){
            commonUtils.toastShort("Please enter name.", getActivity());
        }else if(contactNumber.getText().toString().length() == 0){
            commonUtils.toastShort("Please enter Contact Number.", getActivity());
        }else if(contactNumber.getText().toString().length() < 10){
            commonUtils.toastShort("Please enter Valid Contact Number.", getActivity());
        }else if(!isValidEmail(emailId.getText().toString())){
            commonUtils.toastShort("Please enter valid email.", getActivity());
        }else if(departurePlace.getText().toString().length() == 0){
            commonUtils.toastShort("Please enter departure location.", getActivity());
        }else {
            SendQuery sendQuery = new SendQuery(packageId, name.getText().toString(),
                    contactNumber.getText().toString(),
                    emailId.getText().toString(),
                    departurePlace.getText().toString(),
                    message.getText().toString());
            RequestParams requestParams = new RequestParams();
            requestParams.put("enquiry", gson.toJson(sendQuery));
            webServiceController.postRequest(
                    apiConstants.SEND_HOLIDAY_QUERY,
                    requestParams,
                    1);
        }
    }

    String packageId, holidayName;
    Gson gson;
    WebServiceController webServiceController;

    @SuppressLint("ValidFragment")
    public HolidaySendQuery(String packageId, String holidayName) {
        this.packageId = packageId;
        this.holidayName = holidayName;
    }

    public HolidaySendQuery() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.holiday_send_query_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
        webServiceController = new WebServiceController(getActivity(),HolidaySendQuery.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        packageName.setText(holidayName);
    }

    /**
     * email text validator
     * */
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void getResponse(String response, int flag) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getInt("status") == 1){
                commonUtils.toastShort(jsonObject.getString("message"), getActivity());
                getActivity().finish();
            }else {
                commonUtils.toastShort(jsonObject.getString("message"), getActivity());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}