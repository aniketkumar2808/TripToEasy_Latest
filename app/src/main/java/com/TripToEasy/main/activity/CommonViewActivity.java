package com.TripToEasy.main.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class CommonViewActivity extends BaseActivity implements WebInterface
{
    int page_type=0;
    WebServiceController webServiceController;
    @BindView(R.id.text_toolbar) TextView toolbarText;
    @BindView(R.id.content_text) TextView content_text;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_common_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page_type=getIntent().getExtras().getInt("page_type");
        RequestParams requestParams = new RequestParams();
        webServiceController=new WebServiceController(this,CommonViewActivity.this);

        if (page_type==1)
        {
            toolbarText.setText("About Us");
            webServiceController.postRequest(apiConstants.ABOUNT_US,requestParams,1);


        }
        else  if (page_type==2)
        {
            toolbarText.setText("Privacy Policy");
            webServiceController.postRequest(apiConstants.PRIVACY_POLICY,requestParams,2);


        }
        else  if (page_type==3)
        {
            toolbarText.setText("Terms and Conditions");
            webServiceController.postRequest(apiConstants.TERMS_AND_CONDITION,requestParams,3);

        }
        else
        {
            toolbarText.setText("Contact Us");
            webServiceController.postRequest(apiConstants.CONTACT_US,requestParams,4);


        }


    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            String pageContent=jsonObject.getString("data");

        switch (flag){
            case 1:

                content_text.setText(Html.fromHtml(pageContent));
                break;
            case 2:
                content_text.setText(Html.fromHtml(pageContent));

                break;
            case 3:
                content_text.setText(Html.fromHtml(pageContent));

                break;
            case 4:
                content_text.setText(Html.fromHtml(pageContent));

                break;
        }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.back_button)
    void setGoBack(){
        finish();
    }
}
