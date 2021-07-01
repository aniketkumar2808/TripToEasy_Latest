package com.TripToEasy.main.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.WalletTransactionAdapter;
import com.TripToEasy.model.parsingModel.WalletModel;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.loopj.android.http.RequestParams;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.OnClick;

public class WalletActivity extends BaseActivity implements WebInterface
{
    @BindView(R.id.tv_walletAmount) TextView tv_walletAmount;
    @BindView(R.id.rv_transactionList) RecyclerView rv_transactionList;
    WebServiceController webServiceController;
    ArrayList<WalletModel> transactionList=new ArrayList<>();
    WalletTransactionAdapter adapter;

    @OnClick(R.id.back_button)
    void goBack(){
        finish();
    }


    @OnClick(R.id.ll_addAmount)
    void addMoney(){
       final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_amount_dialog);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
        final EditText et_amount=dialog.findViewById(R.id.et_amount);
        Button bt_addAmount=dialog.findViewById(R.id.bt_addAmount);

        bt_addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams requestParams = new RequestParams();
                requestParams.put("amount_wallet",et_amount.getText().toString().trim());
                requestParams.put("user_id",applicationPreference.getData("user_id"));
                webServiceController.postRequest(apiConstants.ADD_WALLET_AMOUNT,requestParams,2);
                dialog.dismiss();

            }
        });

    }


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        commonUtils.linearLayout(rv_transactionList,this);
        webServiceController=new WebServiceController(this,WalletActivity.this);
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id",applicationPreference.getData("user_id"));
        webServiceController.postRequest(apiConstants.WALLET_DETAILS,requestParams,1);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void getResponse(String response, int flag)
    {
        switch (flag) {
            case 1:

                Log.e("response",response);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array=object.getJSONArray("trnsaction_log");
                    for (int i=0;i<array.length();i++)
                    {
                        JSONObject transObj =array.getJSONObject(i);
                        transactionList.add(new WalletModel(transObj.getString("app_reference"),
                                transObj.getString("amount"),
                                transObj.getString("credit"),
                                transObj.getString("debit"),
                                transObj.getString("opening_balance"),
                                transObj.getString("closing_balance"),
                                transObj.getString("date"),
                                transObj.getString("status")));
                    }
                    adapter=new WalletTransactionAdapter(this,transactionList);
                    rv_transactionList.setAdapter(adapter);
                    tv_walletAmount.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(object.getString("wallet_balance"))/Double.parseDouble(Global.currencyValue))));


                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
            case 2:
                Integer status = 0;
                String url = null;
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1)
                    {
                        status = 1;
                        JSONObject dataobj=jsonObject.getJSONObject("data");
                        url =dataobj.getString("return_url");
                    }
                    else
                        {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", this);
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), this);
                }

                if(status != 0)
                {
                    Bundle bundle=new Bundle();
                    bundle.putString("url",url);
                    intentAndFragmentService.intentDisplay(this,WalletPaymentActivity.class,bundle);
                }
                break;
        }
    }


    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


}
