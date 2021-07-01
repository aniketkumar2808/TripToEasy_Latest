package com.TripToEasy.utils.webservice;

import android.content.Context;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.utils.CommonUtils;
import com.TripToEasy.utils.NetworkCheck;
import com.TripToEasy.utils.ProgressLoader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;

public class WebServiceController {

    Context context;
    WebInterface myInterface;
    ProgressLoader progressLoader;
    NetworkCheck networkCheck;
    CommonUtils commonUtils;
    AsyncHttpClient client;

    public WebServiceController(Context context, Object obj) {
        this.context = context;
        this.myInterface = (WebInterface) obj;
        progressLoader = new ProgressLoader();
        networkCheck = new NetworkCheck(context);
        commonUtils = new CommonUtils();
    }

    public void getRequest(final String url, final int flag, final boolean progressFlag)
    {
        if(networkCheck.isNetworkAvailable()){
            if(!url.contains("ajax/fare_list?")){
                progressLoader.ShowProgress(context);
            }

            client = new AsyncHttpClient();

            client.setSSLSocketFactory(
                    new SSLSocketFactory(getSslContext(),
                            SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));

            client.setTimeout(60000);
            Log.e("url", url);

            client.get(url, new AsyncHttpResponseHandler()
            {

                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
                {
                    String response = "";
                    try {
                        response = new String(arg2, "UTF-8");

                    } catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                        progressLoader.DismissProgress();


                    }
                    if(!url.contains("ajax/fare_list?"))
                    {
                        progressLoader.DismissProgress();
                    }
                    Log.e("API model response success: ",response);

                    myInterface.getResponse(response, flag);
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                      Throwable arg3) {
                    if(!url.contains("ajax/fare_list?")){
                        progressLoader.DismissProgress();
                    }
                    Log.e("API model response error: ",arg3.toString());
                    myInterface.getResponse("failed_to_get_data", flag);
                }

            });
        }else {
            commonUtils.toastShort("Please turn on your internet connection", context);
            progressLoader.DismissProgress();

        }
    }

    public void postRequest(String url, RequestParams params, final int flag) {
        if(networkCheck.isNetworkAvailable()){
            if(flag != 9999){
                if(flag != 8888)
                {
                    progressLoader.ShowProgress(context);
                }
            }

            client = new AsyncHttpClient();
            client.setSSLSocketFactory(
                    new SSLSocketFactory(getSslContext(),
                            SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));


            client.setTimeout(60000);

            Log.e("url", "InsideWebServiceController"+url +" "+params.toString());

            client.post(url, params, new AsyncHttpResponseHandler()
            {

                @Override
                public void onSuccess(int arg0, Header[] arg1, byte[] arg2)
                {
                    String response = "";

                    try {
                        response = new String(arg2, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        progressLoader.DismissProgress();

                    }
                    if(flag != 9999){
                        if(flag != 8888){
                            progressLoader.DismissProgress();
                        }
                    }
                    Log.e("API model response success: ",response);


                    myInterface.getResponse(response, flag);
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3)
                {
                    if(flag != 9999)
                    {
                        if(flag != 8888){
                            progressLoader.DismissProgress();
                        }
                    }
                    myInterface.getResponse("error_response", flag);

                    Log.e("API model response error: ",arg3.toString());


                }


            });
        }else {
            commonUtils.toastShort("Please turn on your internet connection", context);
            progressLoader.DismissProgress();

        }
    }

    public void cancelPreviousRequest(){
        try{
            client.getHttpClient().getConnectionManager().shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public SSLContext getSslContext() {

        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        } };

        SSLContext sslContext=null;

        try {
            sslContext = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            sslContext.init(null, byPassTrustManagers, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return sslContext;
    }



}