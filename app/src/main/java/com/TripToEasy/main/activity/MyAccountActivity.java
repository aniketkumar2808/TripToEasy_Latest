package com.TripToEasy.main.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.database.DBAdapter;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.ProgressLoader;
import com.TripToEasy.utils.retrofit.ApiClient;
import com.TripToEasy.utils.retrofit.ApiInterface;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MyAccountActivity extends BaseActivity implements WebInterface
{
    WebServiceController webServiceController;
    ProgressLoader progressLoader;
    File imageFile;
    DBAdapter dbAdapter;

    @BindView(R.id.spinn_countrycode)
    Spinner spinn_countrycode;

    @BindView(R.id.rb_mr)
    RadioButton rb_mr;

    @BindView(R.id.rb_mrs)
    RadioButton rb_mrs;

    @BindView(R.id.rb_ms)
    RadioButton rb_ms;

    @BindView(R.id.ivProfilePhoto)
    ImageView ivProfilePhoto;

    @BindView(R.id.input_fname)
    EditText input_fname;

    @BindView(R.id.input_lname)
    EditText input_lname;

    @BindView(R.id.input_email)
    EditText input_email;

    @BindView(R.id.input_number)
    EditText input_number;

    @BindView(R.id.input_addr)
    EditText input_addr;

    @BindView(R.id.iv_edit)
    ImageView iv_edit;

    @BindView(R.id.iv_camera)
    ImageView iv_camera;

    @BindView(R.id.change_password)
    TextView tv_change_password;

    @BindView(R.id.bt_save)
    Button bt_save;

    @OnClick(R.id.iv_camera)
    void setProfileImage(){
        selectImage();
    }

    @OnClick(R.id.iv_edit)
    void setEdit(){
        input_fname.setEnabled(true);
        input_lname.setEnabled(true);

        input_number.setEnabled(true);
        input_addr.setEnabled(true);
        iv_camera.setVisibility(View.VISIBLE);
        bt_save.setVisibility(View.VISIBLE);
    }
    String fname,lname,mobile,email,address,title;
    @OnClick(R.id.bt_save)
    void saveProfile()
    {
         fname = input_fname.getText().toString().trim();
         lname = input_lname.getText().toString().trim();
         mobile = input_number.getText().toString().trim();
         email = input_email.getText().toString().trim();
         address = input_addr.getText().toString().trim();

        if (TextUtils.isEmpty(fname)) {
            input_fname.setError("Enter first name");
            input_fname.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(lname)) {
            input_lname.setError("Enter last name");
            input_lname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobile)) {
            input_number.setError("Enter mobile number");
            input_number.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            input_addr.setError("Enter address");
            input_addr.requestFocus();
            return;
        }

        if(rb_mr.isChecked()){
            title="1";
        }
        if(rb_ms.isChecked()){
            title="2";
        }
        if(rb_mrs.isChecked()){
            title="5";
        }
        RequestBody reqFile = null;
        MultipartBody.Part lFileBody = null;

        if (imageFile != null) {

            // imageFile = sessionManager.compressImage(imageFile, profile_image);
            String lStrMediaType = getMimeType(imageFile);
            reqFile = RequestBody.create(MediaType.parse(lStrMediaType), imageFile);
            lFileBody = MultipartBody.Part.createFormData("image", imageFile.getName(), reqFile);
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("title", title);
        requestParams.put("first_name", fname);
        requestParams.put("last_name", lname);
        requestParams.put("country_code", "91");
        requestParams.put("phone", mobile);
        requestParams.put("address", address);
        requestParams.put("user_id", applicationPreference.getData(applicationPreference.userId));
        requestParams.put("image", lFileBody);
        Log.e("profile params are :",requestParams.toString());

        progressLoader.ShowProgress(MyAccountActivity.this);
        RequestBody titleData = RequestBody.create(okhttp3.MultipartBody.FORM, title);
        RequestBody fnameData = RequestBody.create(okhttp3.MultipartBody.FORM, fname);
        RequestBody lnameData = RequestBody.create(okhttp3.MultipartBody.FORM, lname);
        RequestBody countryCodeVal = RequestBody.create(okhttp3.MultipartBody.FORM, "91");
        RequestBody mobileData = RequestBody.create(okhttp3.MultipartBody.FORM, mobile);
        RequestBody addrData = RequestBody.create(okhttp3.MultipartBody.FORM, address);
        RequestBody userIdVal = RequestBody.create(okhttp3.MultipartBody.FORM, applicationPreference.getData(applicationPreference.userId));

        ApiInterface apiInterface= null;
        try {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        Call<ResponseBody> signInCall = apiInterface.updateProfile(titleData, fnameData, lnameData,countryCodeVal, mobileData,addrData,userIdVal,lFileBody);
        ApiClient.getApiResponse(signInCall, new ApiClient.ApiCallBack() {
            @Override
            public void Success(Response<ResponseBody> response) throws IOException {
                progressLoader.DismissProgress();
                String responseData= response.body().string();
                Log.e("Api url ",response.raw().request().url().toString()+"  blank ");
                Log.e("Api response",responseData+"  blank ");
                try {
                    JSONObject jsonObject=new JSONObject(responseData);
                    JSONObject dataObj=jsonObject.getJSONObject("data");
                    if(jsonObject.getInt("status")==1){
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
                        finish();

                    }else {

                    }
                    commonUtils.toastShort(jsonObject.getString("msg"),getApplicationContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                    commonUtils.toastShort(e.toString(),getApplicationContext());
                }

            }

            @Override
            public void Failure(Response<ResponseBody> response) throws IOException
            {
                progressLoader.DismissProgress();
                commonUtils.toastShort(response.toString(),getApplicationContext());
            }

            @Override
            public void Error(Throwable t)
            {
                progressLoader.DismissProgress();
                commonUtils.toastShort(t.toString(),getApplicationContext());

            }
        });


    }

    @OnClick(R.id.change_password)
    void setChangePassword(){
        Intent intent=new Intent(getApplicationContext(),ChangePasswordActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_account);
        webServiceController = new WebServiceController(this, MyAccountActivity.this);
        dbAdapter = new DBAdapter(this);
        progressLoader=new ProgressLoader();
       // loadCountries();
        input_fname.setText(applicationPreference.getData("user_fname"));
        input_lname.setText(applicationPreference.getData("user_lname"));
        input_email.setText(applicationPreference.getData("user_email"));
        input_number.setText(applicationPreference.getData("user_mobile"));
        if(applicationPreference.getData("user_title").equals("1")){
            rb_mr.setChecked(true);
        }else if(applicationPreference.getData("user_title").equals("2")){
            rb_ms.setChecked(true);
        }else if(applicationPreference.getData("user_title").equals("5")) {
            rb_mrs.setChecked(true);
        }
        input_addr.setText(applicationPreference.getData("user_addr"));

        if(applicationPreference.getData(applicationPreference.login_type).equalsIgnoreCase(getString(R.string.app_login_type))){
            iv_edit.setVisibility(View.VISIBLE);
            tv_change_password.setVisibility(View.VISIBLE);

        }else {
            iv_edit.setVisibility(View.VISIBLE);
            tv_change_password.setVisibility(View.GONE);
            iv_camera.setVisibility(View.GONE);
        }

        if(!applicationPreference.getData(applicationPreference.user_dp).equals("") && applicationPreference.getData(applicationPreference.user_dp)!=null) {
            Picasso.get()
                    .load(applicationPreference.getData(applicationPreference.user_dp)).placeholder(R.drawable.user_dummy)
                    .into(ivProfilePhoto);
        }


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

            if(jsonObject.getString("msg").equalsIgnoreCase("No changes.")){
                commonUtils.toastShort("Updated Successfully",getApplicationContext());
            }else {
                commonUtils.toastShort(jsonObject.getString("msg"),getApplicationContext());
            }
            applicationPreference.setData(
                    applicationPreference.userMobile,
                    mobile);
            applicationPreference.setData(
                    applicationPreference.userName,
                    fname);
            applicationPreference.setData(
                    applicationPreference.userLastName,
                    lname);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
            commonUtils.toastShort(e.toString(),getApplicationContext());
        }
    }

    public String getMimeType(File lFile) {
        Uri selectedUri = Uri.fromFile(lFile);
        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension);
        return mimeType;
    }

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    Bitmap thumbnail = null;
    String message, encodedImage;
    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallary",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
               // boolean result = Utility.checkPermission(MainActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    //if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Gallary")) {
                    userChoosenTask = "Choose from Gallary";
                    //if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageFile=persistImage(thumbnail);
        ivProfilePhoto.setImageBitmap(thumbnail);
    }

    private File persistImage(Bitmap bitmap) {

        File imageFile  = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {

        }
        return imageFile;
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(MyAccountActivity.this.getContentResolver(), data.getData());
                imageFile=persistImage(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivProfilePhoto.setImageBitmap(thumbnail);
    }

    public String getStringImage(Bitmap bmp){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,60, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }catch (Exception e){

        }
        return encodedImage;
    }

    private void showDialog() {

    }

    private void hideDialog() {

    }
    public void loadCountries(){
        Thread loadCountries = new Thread(){
            @Override
            public void run() {
                // dbAdapter.open();

                if (Global.arrCountry.size()==0){
                    for (int i=0;i<dbAdapter.getCountryList().size();i++){
                        Global.arrCountry.add(dbAdapter.getCountryList().get(i).getPhonecode());
                    }
                }

                //  dbAdapter.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinn_countrycode.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item,R.id.spinn_text, Global.arrCountry));

                        spinn_countrycode.setSelection(89);

                    }
                });
            }
        };
        loadCountries.start();
    }
}
