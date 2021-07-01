package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;

import butterknife.BindView;


public class PaymentFlight extends BaseFragment {

    @BindView(R.id.main_web)
    WebView paymentView;

    String paymentUrl;
    Boolean isSuccess = false;
    @SuppressLint("ValidFragment")
    public PaymentFlight(String url) {
        paymentUrl = url;
    }

    public PaymentFlight() {

    }
    //ProgressLoader progressLoader;
    @Override
    protected int getLayoutResource() {
        return R.layout.payment_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //progressLoader=new ProgressLoader();
        //progressLoader.ShowProgress(getActivity());

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
                //progressLoader.DismissProgress();
                if(url.contains("BOOKING_HOLD/show_voucher")){
                    isSuccess = true;
                }
                else if (url.contains("http://www.travojet.in/"))
                {
                    isSuccess = true;
                    getActivity().finish();
                }
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(isSuccess){
            getActivity().finish();
        }
    }
}