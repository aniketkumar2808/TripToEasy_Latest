package com.TripToEasy;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.TripToEasy.main.activity.BaseActivity;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {
    String webUrl;
    Boolean isSuccess = false;
    @BindView(R.id.main_web)
    WebView webView;
    @Override
    protected int getLayoutResource() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_web_view);
         webUrl=getIntent().getExtras().getString("weburl");
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        if(webUrl != null){
            webView.postUrl(webUrl, null);
        }

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }
}
