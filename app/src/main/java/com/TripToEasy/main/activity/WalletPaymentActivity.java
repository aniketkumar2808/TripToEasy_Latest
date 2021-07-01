package com.TripToEasy.main.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.TripToEasy.R;
import butterknife.BindView;
import butterknife.OnClick;

public class WalletPaymentActivity extends BaseActivity {


    @OnClick(R.id.back_button)
    void goBack(){
        finish();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    @BindView(R.id.main_web)
    WebView paymentView;

    String paymentUrl;
    Boolean isSuccess = false;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wallet_payment;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentUrl=getIntent().getExtras().getBundle("bundleWithValues").getString("url");
        paymentView.getSettings().setAppCacheEnabled(false);
        paymentView.getSettings().setJavaScriptEnabled(true);
        paymentView.getSettings().setLoadWithOverviewMode(true);
        paymentView.getSettings().setUseWideViewPort(true);
        paymentView.getSettings().setBuiltInZoomControls(true);
        if(paymentUrl != null){
            if(paymentUrl.contains("https")||paymentUrl.contains("http")){
                paymentView.postUrl(paymentUrl, null);
            }else {
                paymentView.postUrl("https://"+paymentUrl, null);
            }

        }

        paymentView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("URL CONTAINS ",url);
                if (url.contains("http://http//travojet.in/"))
                {
                    isSuccess = true;
                    finish();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isSuccess){
            this.finish();
        }
    }

}
