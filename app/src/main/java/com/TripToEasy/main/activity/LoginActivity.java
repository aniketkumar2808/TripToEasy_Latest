package com.TripToEasy.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.EditText;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.TripToEasy.LandingActivityNew;
import com.TripToEasy.R;
import com.TripToEasy.model.requestModel.login.SocialLogin;
import com.TripToEasy.model.requestModel.login.UserLogin;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity
        implements GoogleApiClient.OnConnectionFailedListener,
        WebInterface {
    private LoginManager fbLoginManager;

    @BindView(R.id.user_name)
    EditText userNameText;

    @BindView(R.id.password)
    EditText passwordText;

    @OnClick(R.id.tv_signup)
    void doSignUp()
    {
        Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.skip_login)
    void skipLogin(){
        callLandingPage();
    }

    private void callLandingPage() {
        intentAndFragmentService.intentDisplay(LoginActivity.this, LandingActivityNew.class, null);
        finish();
    }

    @OnClick(R.id.google_login)
    void googleLogin(){
        googleSignIn();
    }

    @OnClick(R.id.facebook_login)
    void facebookLogin(){
        fbLoginManager.logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
    }

    @OnClick(R.id.login_action)
    void loginAction(){
        if(userNameText.getText().toString().length() != 0
                && passwordText.getText().toString().length() != 0)
        {
            UserLogin userLogin = new UserLogin(
                    userNameText.getText().toString(),
                    passwordText.getText().toString());
            RequestParams requestParams = new RequestParams();
            requestParams.put("user_login", gson.toJson(userLogin));
            webServiceController.postRequest(apiConstants.USER_LOGIN, requestParams, 3);
        }
        else
            {
            commonUtils.toastShort("Please enter user name and password to login", LoginActivity.this);
        }
    }

    @OnClick(R.id.forgotpassword)
    void doForgotPassword(){
        Intent intent=new Intent(getApplicationContext(),ForgotPasswordActivity.class);
        startActivity(intent);
    }

    WebServiceController webServiceController;

    GoogleApiClient mGoogleApiClient;
    private CallbackManager callbackManager;
    Integer RC_SIGN_IN = 1;
    Gson gson;

    @Override
    protected int getLayoutResource() {
        return R.layout.login_activity;
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        fbLoginManager = com.facebook.login.LoginManager.getInstance();

        fbLoginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object,
                                                    GraphResponse response) {
                                try {
                                    SocialLogin socialLogin = new SocialLogin(
                                            "0",
                                            object.optString("email"),
                                            object.getString("first_name"),
                                            "");
                                    Log.e("TAG", "onCompleted: "+gson.toJson(socialLogin));
                                    RequestParams requestParams = new RequestParams();
                                    requestParams.put("get_fb_detail", gson.toJson(socialLogin));
                                    webServiceController.postRequest(
                                            apiConstants.FACEBOOK_LOGIN,
                                            requestParams, 2);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id, first_name, last_name, email, gender, birthday, location");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                commonUtils.toastShort("Cancelled", LoginActivity.this);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("TAG", "onError: "+error.getMessage());
                if (error instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }

                commonUtils.toastShort(error.getMessage(), LoginActivity.this);
            }
        });

        gson = new Gson();
        webServiceController = new WebServiceController(this, LoginActivity.this);
        configureGoogleSignIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager.onActivityResult(requestCode, resultCode, data))
        {
            return;
        }




        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                SocialLogin socialLogin = new SocialLogin(
                        "0",
                        account.getEmail(),
                        account.getDisplayName(),
                        "");

                try{
                    applicationPreference.setData(
                            applicationPreference.user_dp,
                            account.getPhotoUrl().toString());

                }catch (Exception e){
                    applicationPreference.setData(
                            applicationPreference.user_dp,
                           "none");
                }

                RequestParams requestParams = new RequestParams();
                requestParams.put("get_google_detail", gson.toJson(socialLogin));
                webServiceController.postRequest(
                        apiConstants.GOOLE_LOGIN,
                        requestParams, 1);
            }else {
                commonUtils.toastShort("Login Failure", this);
            }
        }
    }

    public void configureGoogleSignIn(){
        GoogleSignInOptions options =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();
    }

    public void googleSignIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void getResponse(String response, int flag) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            Log.e("Login Response is: ",response);
            switch (flag)
            {
                case 1:
                    applicationPreference.setData(
                            applicationPreference.userId,
                            jsonObject.getString("user_id"));
                    applicationPreference.setData(
                            applicationPreference.userEmail,
                            jsonObject.getString("email"));
                    applicationPreference.setData(
                            applicationPreference.userName,
                            jsonObject.getString("first_name"));
                    applicationPreference.setData(
                            applicationPreference.login_flag,
                            "true");
                    applicationPreference.setData(
                            applicationPreference.login_type,
                            "google");

                    callLandingPage();
                    break;
                case 2:
                    applicationPreference.setData(
                            applicationPreference.userId,
                            jsonObject.getString("user_id"));
                    applicationPreference.setData(
                            applicationPreference.userEmail,
                            jsonObject.getString("email"));
                    applicationPreference.setData(
                            applicationPreference.userName,
                            jsonObject.getString("first_name"));
                    applicationPreference.setData(
                            applicationPreference.login_flag,
                            "true");
                    applicationPreference.setData(
                            applicationPreference.login_type,
                            "facebook");
                    callLandingPage();
                    break;
                case 3:
                    if(jsonObject.getInt("status") == 1)
                    {
                        JSONObject dataObj = jsonObject.getJSONObject("data");
                        applicationPreference.setData(
                                applicationPreference.userId,
                                dataObj.getString("user_id"));
                        applicationPreference.setData(
                                applicationPreference.userEmail,
                                dataObj.getString("email_id"));
                        applicationPreference.setData(
                                applicationPreference.userMobile,
                                dataObj.getString("phone"));
                        applicationPreference.setData(
                                applicationPreference.userName,
                                dataObj.getString("first_name"));
                        applicationPreference.setData(
                                applicationPreference.userLastName,
                                dataObj.getString("last_name"));
                        applicationPreference.setData(
                                applicationPreference.user_title,
                                dataObj.getString("title"));
                        applicationPreference.setData(
                                applicationPreference.user_addr,
                                dataObj.getString("address"));
                        applicationPreference.setData(
                                applicationPreference.user_cc,
                                dataObj.getString("country_code"));
                        try{
                            applicationPreference.setData(
                                    applicationPreference.user_dp,
                                    dataObj.getString("image"));
                        }catch (Exception e){
                            applicationPreference.setData(
                                    applicationPreference.user_dp,
                                    "none");
                        }

                        applicationPreference.setData(
                                applicationPreference.login_flag,
                                "true");
                        applicationPreference.setData(
                                applicationPreference.login_type,
                                getString(R.string.app_login_type));
                        callLandingPage();
                    }
                    else {
                        commonUtils.toastShort(jsonObject.getString("message"), LoginActivity.this);
                    }
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

}