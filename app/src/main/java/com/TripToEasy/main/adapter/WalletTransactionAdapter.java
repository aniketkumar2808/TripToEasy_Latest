package com.TripToEasy.main.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.main.activity.WalletActivity;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.WalletModel;
import com.TripToEasy.utils.Global;

import java.util.List;

public class WalletTransactionAdapter extends CommonRecyclerAdapter {

    WalletActivity activity;
    List<WalletModel> walletModelList;

    public WalletTransactionAdapter(WalletActivity activity, List<WalletModel> walletModelList) {
        this.activity = activity;
        this.walletModelList = walletModelList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new CommonViewHolder(parent, R.layout.wallet_transaction_item)
        {
            @Override
            public void onItemSelected(int position)
            {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView tv_transactionType = view.findViewById(R.id.tv_transactionType);
        TextView tv_amount = view.findViewById(R.id.tv_amount);
        TextView tv_reference = view.findViewById(R.id.tv_reference);
        TextView tv_dateTime = view.findViewById(R.id.tv_dateTime);
        TextView tv_status = view.findViewById(R.id.tv_status);
        TextView tv_closingBalance = view.findViewById(R.id.tv_closingBalance);

        if(walletModelList.get(position).getStatus().equalsIgnoreCase("1"))
        {
            tv_status.setText(activity.getResources().getString(R.string.status)+" "+"Processed");

            if (walletModelList.get(position).getCredit().equalsIgnoreCase("0"))
            {
                    tv_transactionType.setText(activity.getString(R.string.amount_paid));
            }
            else
            {
                tv_transactionType.setText(activity.getString(R.string.amount_added));

            }
        }
        else
        {
            tv_transactionType.setText(activity.getString(R.string.transaction_cancelled));
            tv_status.setText(activity.getResources().getString(R.string.status)+" "+"Payment not processed!!");
        }
        tv_amount.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(walletModelList.get(position).getAmount())/Double.parseDouble(Global.currencyValue))));

        tv_reference.setText(activity.getResources().getString(R.string.reference_id)+" "+walletModelList.get(position).getApp_reference());
        tv_dateTime.setText(activity.getResources().getString(R.string.date)+" "+walletModelList.get(position).getDate());
        tv_closingBalance.setText(activity.getResources().getString(R.string.closing_balance)+" "+String.format("%.2f",(Double.parseDouble(walletModelList.get(position).getClosing_balance())/Double.parseDouble(Global.currencyValue))));


    }

    @Override
    public int getItemCount() {
        return walletModelList.size();
    }

}