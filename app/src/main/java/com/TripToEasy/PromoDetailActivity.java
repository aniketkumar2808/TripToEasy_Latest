package com.TripToEasy;

import android.os.Bundle;
import android.text.Html;

import android.widget.TextView;
import android.widget.Toast;

import com.TripToEasy.main.activity.BaseActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class PromoDetailActivity extends BaseActivity {


    @BindView(R.id.tv_description) TextView tv_description;
    @BindView(R.id.tv_couponCode) TextView tv_couponCode;
    @BindView(R.id.tv_validDate) TextView tv_validDate;
    @BindView(R.id.tv_howGet) TextView tv_howGet;
    @BindView(R.id.tv_whatElse) TextView tv_whatElse;
    @BindView(R.id.tv_tc) TextView tv_tc;

    @OnClick(R.id.iv_close)
    public void back()
    {
        this.finish();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_promo_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detail(getIntent().getExtras().getString("response"));
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void detail(String response)
    {
        try{
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getInt("status") == 1){
                JSONObject dataObject = jsonObject.getJSONObject("data");

                tv_description.setText(dataObject.getString("description"));
                tv_couponCode.setText(dataObject.getString("promo_code"));
                tv_validDate.setText(dataObject.getString("expiry_date"));
                tv_howGet.setText(Html.fromHtml(dataObject.getString("how_get_it")));
                tv_whatElse.setText(Html.fromHtml(dataObject.getString("you_need_to_know")));
                tv_tc.setText(Html.fromHtml(dataObject.getString("terms_conditions")));

            }else {
                Toast.makeText(this,jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }



    }


}
