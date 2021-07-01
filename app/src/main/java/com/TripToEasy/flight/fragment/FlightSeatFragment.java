package com.TripToEasy.flight.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.main.fragment.TicketPreviewFragment;
import com.TripToEasy.utils.ProgressLoader;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlightSeatFragment extends BaseFragment {

    View view;
    String flightResponse,passengers;
    Bundle bundle;
    ProgressLoader progressLoader;

    public FlightSeatFragment() {
        // Required empty public constructor
    }
    String discountValue="0.00";
    @SuppressLint("ValidFragment")
    public FlightSeatFragment(String flightResponse,String passengers,String discountValue) {
        this.flightResponse=flightResponse;
        this.passengers=passengers;
        this.discountValue=discountValue;
    }

    @BindView(R.id.webview)
    WebView webView;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_flight_seat;
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_seat, container, false);
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //progressLoader = new ProgressLoader();
        //progressLoader.ShowProgress(getActivity());
        init();
    }

    private void init(){
        bundle = getArguments();
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

        Log.e("web_post",bundle.getString("postData"));

        webView.postUrl(apiConstants.FLIGHT_BOOKING_URL,
                            bundle.getString("postData").getBytes());

        Log.e("web view url is ",apiConstants.FLIGHT_BOOKING_URL+
                bundle.getString("postData").getBytes());


        /*webView.loadUrl(UrlConstants.PASSENGER_DETAILS+
                bundle.getBundle("old_bundle").getString("search_id")+"?"+
                bundle.getString("postData"));*/

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.e("overriding_url",url);
               // progressLoader.ShowProgress(getActivity());
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("seat_start",url);
               // progressLoader.ShowProgress(getActivity());
                /*if(url.contains("flight/error_response?msg=Issue_with_Token") ||
                        url.contains("flight/error_response?msg=Issue_with_Token"))
                {
                    progressLoader.DismissProgress();
                    ShowPrompt("Your Session has been expired. Please Try again");

                }*/
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("seat_fin",url);
                //progressLoader.DismissProgress();
                view.clearHistory();

                if(url.contains("process_additional_ssr"))
                {

                    view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('body')[0].innerHTML,1);");

                }
            }
        });
    }

    String return_url;
    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html, String status)
        {
            int extra_service_amt=0;
            try {
                JSONObject jsonObject=new JSONObject(html);
                JSONObject dataObj=jsonObject.getJSONObject("data");
                if(jsonObject.getInt("status")==1){
                    return_url=jsonObject.getString("retun_url");
                    if(dataObj.has("extra_services_total_price"))
                    {
                        extra_service_amt = dataObj.getInt("extra_services_total_price");
                    }
                   /* intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.support_frame, new PaymentFlight(return_url), null, false);
                    */
                    intentAndFragmentService.fragmentDisplay(getActivity(),R.id.support_frame,
                            new TicketPreviewFragment(extra_service_amt,return_url,flightResponse,passengers,discountValue,1),null,false);


                }else {
                    commonUtils.toastShort("Failed to process request", getActivity());
                    getActivity().finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                commonUtils.toastShort(e.toString(), getActivity());
                getActivity().finish();
            }
        }

    }
}
