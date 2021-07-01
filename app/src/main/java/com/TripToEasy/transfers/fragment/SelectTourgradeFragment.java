package com.TripToEasy.transfers.fragment;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.TripToEasy.utils.Global;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.transfers.TourGradeTrips;
import com.TripToEasy.transfers.adapter.TourGradeAdapter;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectTourgradeFragment extends BaseFragment implements WebInterface{
    WebServiceController webServiceController;
    String transfersResponse=null;
    List<TourGradeTrips> transfersList=new ArrayList<TourGradeTrips>();
    TourGradeAdapter tourGradeAdapter;

    @BindView(R.id.rv_select_transfer)
    RecyclerView rv_select_transfer;

    @BindView(R.id.transfer_bkg)
    ImageView transfer_bkg;

    @BindView(R.id.transfers_name)
    TextView transfers_name;

    @BindView(R.id.transfers_rating)
    RatingBar transfers_rating;

    @BindView(R.id.duration)
    TextView tv_duration;

    @BindView(R.id.final_price)
    TextView final_price;

    @OnClick(R.id.next)
    void doTourBlock(){
        callTourBlockApi();
    }

    public SelectTourgradeFragment() {
        // Required empty public constructor
    }

    String resultToken=null,bookingQuestions=null;
    @SuppressLint("ValidFragment")
    public SelectTourgradeFragment(String transfersResponse,String resultToken,String bookingQuestions) {
        this.transfersResponse=transfersResponse;
        this.resultToken=resultToken;
        this.bookingQuestions=bookingQuestions;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_select_tourgrade;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_tourgrade, container, false);
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                SelectTourgradeFragment.this);
        commonUtils.linearLayout(rv_select_transfer, getActivity());
        tourGradeAdapter = new TourGradeAdapter(getActivity(),
                SelectTourgradeFragment.this, transfersList);
        rv_select_transfer.setAdapter(tourGradeAdapter);

        loadData();
    }
    String productName,productCode,imgUrl,rating,duration,ageBandToken,grade_desc,grade_code,tour_uniq_id,grade_title;
    String searchParams=null;
    private void loadData(){
        try {
            JSONObject jsonObject=new JSONObject(transfersResponse);
            JSONObject dataObj=jsonObject.getJSONObject("data");
            searchParams=dataObj.getJSONObject("search_params").toString();
            JSONObject tourgradeObj=dataObj.getJSONObject("tourgrade_list");
            JSONArray tripArr=tourgradeObj.getJSONArray("Trip_list");
            ageBandToken=dataObj.getString("ageBand_token");
            productName=tourgradeObj.getJSONObject("ProductDetails").getString("ProductName");
            productCode=tourgradeObj.getJSONObject("ProductDetails").getString("ProductCode");
            imgUrl=tourgradeObj.getJSONObject("ProductDetails").getString("ProductImage");
            rating=tourgradeObj.getJSONObject("ProductDetails").getInt("StarRating")+"";
            duration=tourgradeObj.getJSONObject("ProductDetails").getString("Duration");

            for(int i=0;i<tripArr.length();i++){
                transfersList.add(new TourGradeTrips(
                        tripArr.getJSONObject(i).getString("bookingDate"),
                        tripArr.getJSONObject(i).getString("langServices"),
                        tripArr.getJSONObject(i).getString("gradeCode"),
                        tripArr.getJSONObject(i).getString("gradeTitle"),
                        tripArr.getJSONObject(i).getString("gradeDescription"),
                        tripArr.getJSONObject(i).getJSONObject("Price").getString("Currency"),
                        tripArr.getJSONObject(i).getJSONObject("Price").getString("TotalDisplayFare"),
                        tripArr.getJSONObject(i).getString("TourUniqueId"),
                        tripArr.getJSONObject(i).getString("TotalPax")
                ));
            }

            tour_uniq_id=transfersList.get(0).getTourUniqueId();
            grade_title=transfersList.get(0).getGradeTitle();
            grade_code=transfersList.get(0).getGradeCode();
            grade_desc=transfersList.get(0).getGradeDescription();

            transfers_name.setText(productName);
            transfers_rating.setRating(Float.parseFloat(rating));
            tv_duration.setText("Duration : "+duration);
            final_price.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(transfersList.get(0).getNetFare())/Double.parseDouble(Global.currencyValue))));

            Picasso.get().load(imgUrl).placeholder(R.drawable.ic_hotel_bg).into(transfer_bkg);

            tourGradeAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateTourGrade(String tour_uniq_id,String grade_title,String grade_code,String grade_desc,String grade_amt){
            this.tour_uniq_id=tour_uniq_id;
            this.grade_title=grade_title;
            this.grade_code=grade_code;
            this.grade_desc=grade_desc;
        final_price.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(grade_amt)/Double.parseDouble(Global.currencyValue))));


    }

    public void callTourBlockApi(){
        try {
            JSONObject searcParamObj=new JSONObject(searchParams);
            JSONObject tokenObj=new JSONObject(resultToken);


            JSONObject reqObj=new JSONObject();
            try {
                reqObj.put("booking_source", searcParamObj.getString("booking_source"));
                reqObj.put("search_id", searcParamObj.getString("search_id"));
                reqObj.put("product_code", searcParamObj.getString("product_code"));
                reqObj.put("product_title",productName);
                reqObj.put("age_band",ageBandToken);
                reqObj.put("op", "block_trip");
                reqObj.put("booking_date", searcParamObj.getString("booking_date"));
                reqObj.put("additional_info", tokenObj.getString("AdditionalInfo"));
                reqObj.put("inclusions", tokenObj.getString("Inclusions"));
                reqObj.put("exclusions", tokenObj.getString("Exclusions"));
                reqObj.put("short_desc", tokenObj.getString("ShortDescription"));
                reqObj.put("voucher_req", tokenObj.getString("voucher_req"));

                reqObj.put("tour_uniq_id", tour_uniq_id);
                reqObj.put("grade_title", grade_title);
                reqObj.put("grade_code", grade_code);
                reqObj.put("grade_desc", grade_desc);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestParams params = new RequestParams();
            params.put("block_params", reqObj.toString());


            Log.i("request", params.toString());
            webServiceController.postRequest(
                    apiConstants.TRANSFERS_TOUR_BLOCK,
                    params,
                    1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getResponse(String response, int flag) {
        Log.i("response", response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getInt("status")==1){
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                        new BookTranfersFragment(response,bookingQuestions), null, true);
            }else {
                commonUtils.toastShort("Please Try Again",getActivity());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
