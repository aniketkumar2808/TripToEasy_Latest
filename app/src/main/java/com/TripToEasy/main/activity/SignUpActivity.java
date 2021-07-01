package com.TripToEasy.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.model.requestModel.login.UserSignUp;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements WebInterface {

    WebServiceController webServiceController;
    Gson gson;

    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_lastname)
    EditText et_lastname;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confpassword)
    EditText et_confpassword;

    @OnClick(R.id.bt_signup)
    void doSignUp(){
        final String fname = et_name.getText().toString().trim();
        final String lname = et_lastname.getText().toString().trim();
        final String mobile = et_mobile.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        final String conf_password = et_confpassword.getText().toString().trim();
        final String terms ="on";

        //first we will do the validations

        if (TextUtils.isEmpty(fname)) {
            et_name.setError("Enter first name");
            et_name.requestFocus();
            return;
        }

       else if (TextUtils.isEmpty(lname)) {
            et_lastname.setError("Enter last name");
            et_lastname.requestFocus();
            return;
        }

       else if (TextUtils.isEmpty(mobile)) {
            et_mobile.setError("Enter mobile number");
            et_mobile.requestFocus();
            return;
        }

       else if (TextUtils.isEmpty(email)) {
            et_email.setError("Enter your email");
            et_email.requestFocus();
            return;
        }

      else  if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Enter a valid email");
            et_email.requestFocus();
            return;
        }

      else  if (TextUtils.isEmpty(password)) {
            et_password.setError("Enter a password");
            et_password.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(conf_password)) {
            et_confpassword.setError("Enter a confirm password");
            et_confpassword.requestFocus();
            return;
        }
       else if(!password.equalsIgnoreCase(conf_password))
        {
            et_confpassword.setError("Password and confirm password did not match");

        }
        else
        {

            UserSignUp usersignup = new UserSignUp(
                    fname,lname,mobile,email,password,conf_password,terms
            );
            RequestParams requestParams = new RequestParams();
            requestParams.put("user_register", gson.toJson(usersignup));
            webServiceController.postRequest(
                    apiConstants.USER_SIGN_UP,
                    requestParams,
                    1);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_sign_up);
        gson = new Gson();
        webServiceController = new WebServiceController(this, SignUpActivity.this);

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void getResponse(String response, int flag) {


        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getInt("status")==0){
                commonUtils.toastShort(jsonObject.getString("message"),getApplicationContext());
                return;
            }
            commonUtils.toastShort(jsonObject.getString("message"),getApplicationContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
