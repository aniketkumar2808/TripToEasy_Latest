package com.TripToEasy.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity implements WebInterface {
    WebServiceController webServiceController;
    @OnClick(R.id.back_button)
    void setGoBack(){
        Intent intent=new Intent(getApplicationContext(),MyAccountActivity.class);
        startActivity(intent);
        finish();
    }

    @BindView(R.id.text_toolbar)
    TextView toolbarText;

    @BindView(R.id.old_password)
    EditText old_password;

    @BindView(R.id.new_password)
    EditText new_password;

    @BindView(R.id.re_new_password)
    EditText re_new_password;

    @OnClick(R.id.bt_save)
    void changePassword(){
        final String current_password = old_password.getText().toString().trim();
        final String password = new_password.getText().toString().trim();
        final String confirm_password = re_new_password.getText().toString().trim();

        if (TextUtils.isEmpty(current_password)) {
            old_password.setError("Enter current password");
            old_password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            new_password.setError("Enter new password");
            new_password.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirm_password)) {
            re_new_password.setError("Enter confirm password");
            re_new_password.requestFocus();
            return;
        }
        if(password.equals(confirm_password)){
            RequestParams requestParams = new RequestParams();
            requestParams.put("current_password", current_password);
            requestParams.put("new_password", password);
            requestParams.put("user_id", applicationPreference.getData("user_id"));
            webServiceController.postRequest(
                    apiConstants.CHANGE_PASSWORD,
                    requestParams,
                    1);
        }else {
            commonUtils.toastShort("Enter Password are not same",ChangePasswordActivity.this);
        }

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_change_password);
        toolbarText.setText(getResources().getString(R.string.change_password));
        webServiceController=new WebServiceController(ChangePasswordActivity.this,ChangePasswordActivity.this);

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getInt("status")==1){
                commonUtils.toastShort(jsonObject.getString("message"),ChangePasswordActivity.this);
                applicationPreference.clearAll();
                applicationPreference.setData(
                        applicationPreference.login_flag,
                        "false");
                intentAndFragmentService.intentDisplay(ChangePasswordActivity.this,
                        LoginActivity.class, null);
                finish();
            }else {
                commonUtils.toastShort(jsonObject.getString("message"),ChangePasswordActivity.this);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(getApplicationContext(),MyAccountActivity.class);
        startActivity(intent);
        finish();
    }
}
