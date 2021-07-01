package com.TripToEasy.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements WebInterface {
    WebServiceController webServiceController;
    @OnClick(R.id.back_button)
    void setGoBack(){
        finish();
    }

    @BindView(R.id.text_toolbar)
    TextView toolbarText;

    @BindView(R.id.et_email_id)
    EditText et_email;

    @BindView(R.id.et_mobile)
    EditText et_mobile;

    @OnClick(R.id.bt_get_otp)
    void getOtp(){
        final String mobile = et_mobile.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            et_mobile.setError("Enter mobile number");
            et_mobile.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            et_email.setError("Enter your email");
            et_email.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter a valid email");
            et_email.requestFocus();
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("email_id", email);
        requestParams.put("phone", mobile);
        webServiceController.postRequest(
                apiConstants.FORGOT_PASSWORD_OTP,
                requestParams,
                1);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_forgot_password);
        webServiceController = new WebServiceController(this, ForgotPasswordActivity.this);

        toolbarText.setText(getResources().getString(R.string.forgot_password).replace("?",""));
        et_email.setText(applicationPreference.getData(applicationPreference.userEmail));
        et_mobile.setText(applicationPreference.getData(applicationPreference.userMobile));
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void getResponse(String response, int flag) {

        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getBoolean("status")){
                commonUtils.toastShort(jsonObject.getString("data"),getApplicationContext());
                finish();
            }else {
                commonUtils.toastShort(jsonObject.getString("data"),getApplicationContext());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
