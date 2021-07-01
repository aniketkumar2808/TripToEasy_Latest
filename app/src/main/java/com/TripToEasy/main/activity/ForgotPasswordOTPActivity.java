package com.TripToEasy.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgotPasswordOTPActivity extends BaseActivity implements WebInterface {
    WebServiceController webServiceController;
    String st_email="";
    @OnClick(R.id.back_button)
    void setGoBack(){
        finish();
    }

    @BindView(R.id.text_toolbar)
    TextView toolbarText;
    @BindView(R.id.et_otp)
    EditText et_otp;

    @BindView(R.id.et_new_pw)
    EditText et_new_pw;

    @OnClick(R.id.bt_reset_pw)
    void resetPassword(){
        final String otp = et_otp.getText().toString().trim();
        final String password = et_new_pw.getText().toString().trim();
        if (TextUtils.isEmpty(otp)) {
            et_otp.setError("Enter mobile number");
            et_otp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            et_new_pw.setError("Enter your email");
            et_new_pw.requestFocus();
            return;
        }

        RequestParams requestParams = new RequestParams();
        requestParams.put("otp", otp);
        requestParams.put("email_id", st_email);
        requestParams.put("password", password);
        webServiceController.postRequest(
                apiConstants.FORGOT_PASSWORD_CONFIRM,
                requestParams,
                1);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_forgot_password_otp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_forgot_password_otp);
        webServiceController = new WebServiceController(this, ForgotPasswordOTPActivity.this);
        toolbarText.setText(getResources().getString(R.string.get_otp));
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag) {
        commonUtils.toastShort(response,getApplicationContext());
    }
}
