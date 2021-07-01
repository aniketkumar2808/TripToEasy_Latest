package com.TripToEasy.main.adapter.landingAdapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TripToEasy.utils.webservice.ApiConstants;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.PromoDetailActivity;
import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.PromoCodeInfo;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InternationalPackageLanding extends CommonRecyclerAdapter implements WebInterface
{

    Activity activity;
    List<PromoCodeInfo> packageModels;
    WebServiceController wsc;
    ApiConstants apiConstants;

    public InternationalPackageLanding(Activity activity,
                                       List<PromoCodeInfo> packageModels) {
        this.activity = activity;
        this.packageModels = packageModels;
        wsc=new WebServiceController(activity,this);
        apiConstants=new ApiConstants();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.best_offers_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        ImageView iv_promoImage = view.findViewById(R.id.iv_promoImage);
        TextView tv_promoDescription = view.findViewById(R.id.tv_promoDescription);
        TextView tv_promoCode = view.findViewById(R.id.tv_promoCode);
        tv_promoDescription.setText(packageModels.get(position).getDescription());
        tv_promoCode.setText(packageModels.get(position).getPromo_code());
        Picasso.get()
                .load(packageModels.get(position).getPromo_code_image_path())
                .placeholder(R.drawable.hotel_placeholder)
                .error(R.drawable.hotel_placeholder)
                .into(iv_promoImage);

        iv_promoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams requestParams = new RequestParams();
                requestParams.put("promo_code", packageModels.get(position).getPromo_code());
                wsc.postRequest(apiConstants.PROMOCODE_INFO
                        ,
                        requestParams,
                        1);
             }
        });
    }

    @Override
    public int getItemCount() {
        return packageModels.size();
    }



    @Override
    public void getResponse(String response, int flag) {
        if(flag==1)
        {
            Intent intent=new Intent(activity,PromoDetailActivity.class);
            intent.putExtra("response",response);
            activity.startActivity(intent);
        }
    }
}