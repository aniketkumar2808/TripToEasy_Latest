package com.TripToEasy.transfers.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.TripToEasy.utils.Global;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.transfers.ProductReviewsData;
import com.TripToEasy.transfers.adapter.ReviewsAdapter;
import com.TripToEasy.utils.CommonUtils;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransfersDetailFragment extends BaseFragment implements WebInterface {

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

    @BindView(R.id.earliest_avail_date)
    TextView earliest_avail_date;

    @BindView(R.id.free_cancel)
    TextView free_cancel;

    @BindView(R.id.over_view_description)
    TextView over_view_description;

    @BindView(R.id.detail_description)
    TextView detail_description;

    @BindView(R.id.imp_info_description)
    TextView imp_info_description;

    @BindView(R.id.over_view_main)
    LinearLayout over_view_main;

    @BindView(R.id.detail_main)
    LinearLayout detail_main;

    @BindView(R.id.imp_info_main)
    LinearLayout imp_info_main;

    @BindView(R.id.over_view)
    TextView over_view;

    @BindView(R.id.details)
    TextView details;

    @BindView(R.id.imp_info)
    TextView imp_info;

    @BindView(R.id.rvReviews)
    RecyclerView rvReviews;

    @BindView(R.id.txtReviewTitle)
    TextView txtReviewTitle;

    @OnClick(R.id.check_avail)
    void checkAvailability(){
        showAvailableDialog();
    }

    @OnClick(R.id.over_view)
    void onClickOverview(){
        over_view_main.setVisibility(View.VISIBLE);
        detail_main.setVisibility(View.GONE);
        imp_info_main.setVisibility(View.GONE);
        over_view.setTextColor(getActivity().getResources().getColor(R.color.white));
        details.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        imp_info.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));

    }

    @OnClick(R.id.details)
    void onClickDetail(){
        over_view_main.setVisibility(View.GONE);
        detail_main.setVisibility(View.VISIBLE);
        imp_info_main.setVisibility(View.GONE);

        over_view.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        details.setTextColor(getActivity().getResources().getColor(R.color.white));
        imp_info.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
    }

    @OnClick(R.id.imp_info)
    void onClickImpInfo(){
        over_view_main.setVisibility(View.GONE);
        detail_main.setVisibility(View.GONE);
        imp_info_main.setVisibility(View.VISIBLE);

        over_view.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        details.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        imp_info.setTextColor(getActivity().getResources().getColor(R.color.white));
    }

    String detailResponse=null;

    public TransfersDetailFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public TransfersDetailFragment(String detailResponse) {
        this.detailResponse=detailResponse;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transfers_detail;
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transfers_detail, container, false);
    }*/

    WebServiceController webServiceController;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                TransfersDetailFragment.this);
        commonUtils.linearLayout(rvReviews, getActivity());
        loadDetail();
    }

    JSONArray ProductAgeBands,CalAvlDate;
    int mSelAdultCount = 1;
    int mSelChildCount = 0;
    int mSelInInfant = 0;
    String productName,productCode,imgUrl,rating,duration,price,currency,impInfo,overview,detail,
            BookingEngineId,ResultToken,search_id,booking_source,booking_question;
    String tokenData=null;
    private void loadDetail(){
        try {
            JSONObject jsonObject=new JSONObject(detailResponse);
            JSONObject dataObj=jsonObject.getJSONObject("data");
            JSONObject detailObj=dataObj.getJSONObject("product_details");
            productName=detailObj.getString("ProductName");
            productCode=detailObj.getString("ProductCode");
            imgUrl=detailObj.getString("product_image");
            rating=detailObj.getInt("StarRating")+"";
            duration=detailObj.getString("Duration");
            price=detailObj.getJSONObject("Price").getString("TotalDisplayFare");
            currency=detailObj.getJSONObject("Price").getString("Currency");
            //impInfo=detailObj.getJSONArray("AdditionalInfo").getString(0);
            overview=detailObj.getString("ShortDescription");
            detail=detailObj.getString("Description");
            BookingEngineId=detailObj.getString("BookingEngineId");
            ProductAgeBands = detailObj.getJSONArray("Product_AgeBands");
            CalAvlDate = detailObj.getJSONArray("Calendar_available_date");
            booking_question = detailObj.getJSONArray("BookingQuestions").toString();
            ResultToken=detailObj.getString("ResultToken");
            search_id=dataObj.getJSONObject("params").getString("search_id");
            booking_source=dataObj.getJSONObject("params").getString("booking_source");
            Date lAvlDate = CommonUtils.convertStrToDate(CalAvlDate.getString(0).toString(), "dd-MM-yyyy");

            if (detailObj.getBoolean("Cancellation_available")) {
                free_cancel.setText("Free Cancellation - " + detailObj.getInt("Cancellation_day") + "  day(s) prior");
            } else {
                free_cancel.setText(detailObj.getString("Cancellation_Policy"));
            }
            StringBuilder sb=new StringBuilder();
            for(int m=0;m<detailObj.getJSONArray("AdditionalInfo").length();m++){
                sb.append(detailObj.getJSONArray("AdditionalInfo").getString(m));
                sb.append("\n\n");
            }
            tokenData=dataObj.getJSONObject("tokens_data").toString();
            transfers_name.setText(productName);
            transfers_rating.setRating(Float.parseFloat(rating));
            earliest_avail_date.setText("Earliest available date : " + CommonUtils.conDateToStr(lAvlDate, "dd MMM yyyy"));
            tv_duration.setText("Duration : "+duration);
            final_price.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(price)/Double.parseDouble(Global.currencyValue))));
            over_view_description.setText(Html.fromHtml(overview));
            detail_description.setText(Html.fromHtml(detail));
            imp_info_description.setText(sb.toString());


            Picasso.get().load(imgUrl).placeholder(R.drawable.ic_hotel_bg).into(transfer_bkg);

            JSONArray lProductReview = detailObj.getJSONArray("Product_Reviews");
            List<ProductReviewsData> reviewsDataList = new ArrayList<>();
            ProductReviewsData mData;
            for (int i = 0; i < lProductReview.length(); i++) {
                mData = new ProductReviewsData();
                JSONObject lReviewData = lProductReview.getJSONObject(i);
                mData.setPublishedDate(lReviewData.getString("Published_Date"));
                mData.setReview(lReviewData.getString("Review"));
                mData.setRating(lReviewData.getInt("Rating"));
                mData.setUserImage(lReviewData.getString("UserImage"));
                mData.setUserName(lReviewData.getString("UserName"));
                reviewsDataList.add(mData);
            }

            ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsDataList, getContext());
            rvReviews.setAdapter(reviewsAdapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    Dialog mDialog;
    public void showAvailableDialog(){
        final TextView txtTotalTra, txtTravCount, txtAdultDis, txtChild, txtInfant, txtTRInfantCount, txtChildTRCount;
        final Spinner spinnerAvlDate;
        final LinearLayout llCheckAvl, llAddMembers;
        Button btnCheckAvl, btnDone;
        ImageView ivPlus, ivMinus, ivChildMinus, ivChildPlus, ivInfantMinus, ivInfantPlus;
        LinearLayout llAdult, llChild, llInfant;
        try {
            mSelAdultCount=1;
            mSelChildCount=0;
            mSelInInfant=0;

            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(getContext());
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.check_avail_dialog);
            txtTotalTra = mDialog.findViewById(R.id.txtTotalTra);
            txtTravCount = mDialog.findViewById(R.id.txtTravCount);
            spinnerAvlDate = mDialog.findViewById(R.id.spinnerAvlDate);
            llCheckAvl = mDialog.findViewById(R.id.llCheckAvl);
            llAddMembers = mDialog.findViewById(R.id.llAddMembers);
            btnCheckAvl = mDialog.findViewById(R.id.btnCheckAvl);
            btnDone = mDialog.findViewById(R.id.btnDone);
            ivPlus = mDialog.findViewById(R.id.ivPlus);
            ivMinus = mDialog.findViewById(R.id.ivMinus);
            llAdult = mDialog.findViewById(R.id.llAdult);
            llChild = mDialog.findViewById(R.id.llChild);
            llInfant = mDialog.findViewById(R.id.llInfant);

            ivChildMinus = mDialog.findViewById(R.id.ivChildMinus);
            ivChildPlus = mDialog.findViewById(R.id.ivChildPlus);
            ivInfantMinus = mDialog.findViewById(R.id.ivInfantMinus);
            ivInfantPlus = mDialog.findViewById(R.id.ivInfantPlus);

            txtAdultDis = mDialog.findViewById(R.id.txtAdultDis);
            txtChild = mDialog.findViewById(R.id.txtChild);
            txtInfant = mDialog.findViewById(R.id.txtInfant);
            txtTRInfantCount = mDialog.findViewById(R.id.txtTRInfantCount);
            txtChildTRCount = mDialog.findViewById(R.id.txtChildTRCount);

            llAdult.setVisibility(View.GONE);
            llChild.setVisibility(View.GONE);
            llInfant.setVisibility(View.GONE);


            for (int i = 0; i < ProductAgeBands.length(); i++) {

                if (ProductAgeBands.getJSONObject(i).getString("description").equals("Adult")) {
                    llAdult.setVisibility(View.VISIBLE);
                    ivPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int i = Integer.parseInt(txtTravCount.getText().toString());

                            if (i >= 9) {
                                return;
                            }
                            i++;
                            txtTravCount.setText("" + i);
                            mSelAdultCount = i;

                        }
                    });
                    ivMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int i = Integer.parseInt(txtTravCount.getText().toString());
                            if (i <= 0) {
                                return;
                            }
                            i--;
                            txtTravCount.setText("" + i);
                            mSelAdultCount = i;

                        }
                    });
                } else if (ProductAgeBands.getJSONObject(i).getString("description").equals("Child")) {
                    llChild.setVisibility(View.VISIBLE);
                    ivChildPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int i = Integer.parseInt(txtChildTRCount.getText().toString());

                            if (i >= 9) {
                                return;
                            }
                            i++;
                            txtChildTRCount.setText("" + i);
                            mSelChildCount = i;

                        }
                    });
                    ivChildMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int i = Integer.parseInt(txtChildTRCount.getText().toString());
                            if (i <= 1) {
                                return;
                            }
                            i--;
                            txtChildTRCount.setText("" + i);
                            mSelChildCount = i;

                        }
                    });
                } else if (ProductAgeBands.getJSONObject(i).getString("description").equals("Infant")) {
                    llInfant.setVisibility(View.VISIBLE);
                    ivInfantPlus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int i = Integer.parseInt(txtTRInfantCount.getText().toString());

                            if (i >= 9) {
                                return;
                            }
                            i++;
                            txtTRInfantCount.setText("" + i);
                            mSelInInfant = i;

                        }
                    });
                    ivInfantMinus.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            int i = Integer.parseInt(txtTRInfantCount.getText().toString());
                            if (i <= 0) {
                                return;
                            }
                            i--;
                            txtTRInfantCount.setText("" + i);
                            mSelInInfant = i;

                        }
                    });
                }
            }


            final List<String> items = new ArrayList<>();

            items.add("Select Date");

            for (int i = 0; i < CalAvlDate.length(); i++) {
                items.add(CalAvlDate.getString(i));


            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerAvlDate.setAdapter(adapter);


            btnCheckAvl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spinnerAvlDate.getSelectedItemPosition() == 0) {
                        commonUtils.toastShort("Please Select Date", getContext());
                        return;
                    } else {
                        mDialog.dismiss();
                        checkAvl(items.get(spinnerAvlDate.getSelectedItemPosition()));
                    }
                }
            });

            txtTotalTra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (llAddMembers.getVisibility() == View.GONE) {
                        llAddMembers.setVisibility(View.VISIBLE);
                        llCheckAvl.setVisibility(View.GONE);
                    } else {

                    }

                }

            });


            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    llAddMembers.setVisibility(View.GONE);
                    llCheckAvl.setVisibility(View.VISIBLE);
                    txtTotalTra.setText("Total Travellers " + (mSelAdultCount + mSelChildCount + mSelInInfant));
                }
            });


            mDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            mDialog.show();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void checkAvl(String date) {

        String[] dates = date.split("-");//30-6-2018
        JSONArray ageArr=new JSONArray();

        try {
            JSONObject jobj=new JSONObject();
            jobj.put("bandId",1);
            jobj.put("count",mSelAdultCount);
            ageArr.put(jobj);
            JSONObject jobj1=new JSONObject();
            jobj1.put("bandId",2);
            jobj1.put("count",mSelChildCount);
            ageArr.put(jobj1);
            JSONObject jobj2=new JSONObject();
            jobj2.put("bandId",3);
            jobj2.put("count",mSelInInfant);
            ageArr.put(jobj2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject reqObj=new JSONObject();
        try {
            reqObj.put("productID", "PRODUCT");
            reqObj.put("product_code", productCode);
            reqObj.put("ResultToken", ResultToken);
            reqObj.put("search_id", search_id);
            reqObj.put("booking_engine", BookingEngineId);
            reqObj.put("booking_date", dates[2]+"-"+dates[1]+"-"+dates[0]);
            reqObj.put("op", "check_tourgrade");
            reqObj.put("booking_source", booking_source);
            reqObj.put("ageBands", ageArr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestParams params = new RequestParams();
        params.put("select_tourgrade", reqObj.toString());


        Log.i("request", params.toString());
        webServiceController.postRequest(
                apiConstants.TRANSFERS_TOUR_GRADE,
                params,
                1);

    }


    @Override
    public void getResponse(String response, int flag) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getInt("status")==1){
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                        new SelectTourgradeFragment(response,tokenData,booking_question), null, true);
            }else {
                commonUtils.toastShort("Please Try Again",getActivity());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
