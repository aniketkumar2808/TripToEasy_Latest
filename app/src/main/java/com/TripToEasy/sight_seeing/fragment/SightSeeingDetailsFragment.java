package com.TripToEasy.sight_seeing.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.sightSeeing.ProductReviewsData;
import com.TripToEasy.model.parsingModel.sightSeeing.TripData;
import com.TripToEasy.sight_seeing.sightseeing_adapters.AdditionalInfoAdapter;
import com.TripToEasy.sight_seeing.sightseeing_adapters.ReviewsAdapter;
import com.TripToEasy.utils.CommonUtils;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.ApiConstants;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SightSeeingDetailsFragment extends BaseFragment implements WebInterface
{
    @BindView(R.id.ivPropPic)
    ImageView ivPropPic;

    @BindView(R.id.txtPLName)
    TextView txtPLName;

    @BindView(R.id.txtPLLocation)
    TextView txtPLLocation;

    @BindView(R.id.txtDuration)
    TextView txtDuration;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.txtViewReviews)
    TextView txtViewReviews;

    @BindView(R.id.txtReviewsCount)
    TextView txtReviewsCount;

    @BindView(R.id.txtBestPrice)
    TextView txtBestPrice;
    @BindView(R.id.txtImpInfo)
    TextView txtImpInfo;

    @BindView(R.id.txtOverView)
    TextView txtOverView;

    @BindView(R.id.txtDetails)
    TextView txtDetails;

    @BindView(R.id.view1)
    View view1;

    @BindView(R.id.view2)
    View view2;

    @BindView(R.id.view3)
    View view3;

    int selTab = 0;

    @BindView(R.id.rvImpInfo)
    RecyclerView rvImpInfo;

    @BindView(R.id.txtOVDetails)
    TextView txtOVDetails;

    @BindView(R.id.txtDetailsVal)
    TextView txtDetailsVal;

    @BindView(R.id.txtInstant)
    TextView txtInstant;

    @BindView(R.id.txtTabType)
    TextView txtTabType;

    @BindView(R.id.txtAvlDate)
    TextView txtAvlDate;

    @BindView(R.id.txtCancelStatus)
    TextView txtCancelStatus;

    @BindView(R.id.rvReviews)
    RecyclerView rvReviews;

    @BindView(R.id.txtReviewTitle)
    TextView txtReviewTitle;

    @BindView(R.id.txtPriceHeader)
    TextView txtPriceHeader;

    JSONArray CalAvlDate;
    int mSelAdultCount = 1;
    int mSelChildCount = 0;
    int mSelInInfant = 0;
    JSONArray ProductAgeBands;
    JSONObject tokenData;

    @OnClick(R.id.txtCheckAvl)
    public void showAval() {
        showOptionDialog();
    }

    private void checkAvl(String date)
    {
        String[] dates = date.split("-");//30-6-2018

        WebServiceController lController = new WebServiceController(getContext(), this);
        RequestParams params = new RequestParams();
        params.put("productID", "PRODUCT");
        params.put("product_code", getArguments().getString("product_code"));
        params.put("ResultToken", ResultToken);
        params.put("search_id", getArguments().getInt("search_id"));
        params.put("booking_engine", "FreesaleBE");
        params.put("get_date", dates[0]);
        params.put("get_month", dates[1]);
        params.put("get_year", dates[2]);
        params.put("op", "check_tourgrade");
        params.put("booking_source", apiConstants.BOOKING_SOURCE);
        params.put("max_count", "9");
        params.put("Adult_Band_ID", "1");
        params.put("no_of_Adult", mSelAdultCount);
        params.put("Child_Band_ID", "2");
        params.put("no_of_Child", mSelChildCount);
        params.put("Infant_Band_ID", "3");
        params.put("no_of_Infant", mSelInInfant);
        Log.i("request", params.toString());
        lController.postRequest(ApiConstants.SELECT_TOURGRADE, params, 2);
    }


    Dialog mDialog;
    String ResultToken;

    private void showOptionDialog()
    {
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

                if (ProductAgeBands.getJSONObject(i).getBoolean("adult")) {
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
                } else if (ProductAgeBands.getJSONObject(i).getBoolean("adult")) {
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
                } else if (ProductAgeBands.getJSONObject(i).getBoolean("adult")) {
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


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_sight_seeing_details;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(rvImpInfo, getContext());
        commonUtils.linearLayout(rvReviews, getContext());
        txtViewReviews.setVisibility(View.INVISIBLE);
        txtReviewTitle.setVisibility(View.INVISIBLE);
        txtPriceHeader.setVisibility(View.INVISIBLE);
        callWebservice();

    }

    private void callWebservice() {
        WebServiceController lController = new WebServiceController(getContext(), this);
        String param = "?booking_source="+apiConstants.BOOKING_SOURCE+"&search_id=" + getArguments().getInt("search_id") + "&result_token=" + getArguments().getString("token") + "&product_code=" + getArguments().getString("product_code") + "&op=get_details";
        lController.getRequest(ApiConstants.SIGHT_SEEING_DETAILS + param, 1, true);

    }

    @Override
    public void getResponse(String response, int flag) {

        Log.i("response", response);
        if (flag == 1) {
            try {
                JSONObject lObject = new JSONObject(response);

                if (lObject.getInt("status") == 1) {
                    JSONObject lDataObj = lObject.getJSONObject("data");
                    txtPLName.setText(lDataObj.getString("ProductName"));
                    txtDuration.setText("Duration : " + lDataObj.getString("Duration"));
                    txtPLLocation.setText("Location : " + lDataObj.getString("Location"));
                    txtReviewsCount.setText(lDataObj.getString("ReviewCount") + " Review(s)");
                    txtOVDetails.setText(Html.fromHtml(lDataObj.getString("ShortDescription")));
                    txtDetailsVal.setText(Html.fromHtml(lDataObj.getString("Description")));
                    txtBestPrice.setText(Global.currencySymbol +" " +String.format("%.2f",(Double.parseDouble(lDataObj.getJSONObject("Price").getString("TotalDisplayFare"))/Double.parseDouble(Global.currencyValue))));
                    txtInstant.setText("Instant Confirmation");
                    CalAvlDate = lDataObj.getJSONArray("Calendar_available_date");
                    ProductAgeBands = lDataObj.getJSONArray("Product_AgeBands");
                    Date lAvlDate = CommonUtils.convertStrToDate(CalAvlDate.getString(0).toString(), "dd-MM-yyyy");
                    txtAvlDate.setText("Earliest available date : " + CommonUtils.conDateToStr(lAvlDate, "dd MMM yyyy"));
                    ResultToken = lDataObj.getString("ResultToken");

                    if (lDataObj.getBoolean("Cancellation_available")) {
                        txtCancelStatus.setText("Free Cancellation - " + lDataObj.getInt("Cancellation_day") + "  day(s) prior");
                    } else {
                        txtCancelStatus.setText(lDataObj.getString("Cancellation_Policy"));
                    }

                    JSONArray lProductReview = lDataObj.getJSONArray("Product_Reviews");
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

                    AdditionalInfoAdapter lAdditionalInfoAdapter = new AdditionalInfoAdapter(lDataObj.getJSONArray("AdditionalInfo"));
                    rvImpInfo.setAdapter(lAdditionalInfoAdapter);


                    Glide.with(getContext()).load(lDataObj.getString("product_image")).
                            into(ivPropPic);

                    txtViewReviews.setVisibility(View.VISIBLE);
                    txtReviewTitle.setVisibility(View.VISIBLE);
                    txtPriceHeader.setVisibility(View.VISIBLE);

                    if (lDataObj.getInt("StarRating") > 0)
                        ratingBar.setRating(lDataObj.getInt("StarRating"));
                    tokenData = lDataObj.getJSONObject("tokens_data");

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else if (flag == 2) {

            List<TripData> mTourgradeData = new ArrayList<>();
            TripData mTripData;
            try {
                JSONObject lJsonObject = new JSONObject(response);
                if (lJsonObject.getInt("status") == 1) {
                    JSONArray lTripList = lJsonObject.getJSONObject("data").getJSONArray("Trip_list");
                    for (int i = 0; i < lTripList.length(); i++) {
                        JSONObject lTripObj = lTripList.getJSONObject(i);
                        if (!lTripObj.getBoolean("available")) {
                            continue;
                        }

                        mTripData = new TripData();
                        mTripData.setGradeTitle(lTripObj.getString("gradeTitle"));
                        mTripData.setGradeCode(lTripObj.getString("gradeCode"));
                        mTripData.setGradeDepartureTime(lTripObj.getString("gradeDepartureTime"));
                        mTripData.setGradeDescription(lTripObj.getString("gradeDescription"));
                        mTripData.setTotalPax(String.valueOf(lTripObj.getInt("TotalPax")));
                        mTripData.setTotalDisplayFare(lTripObj.getJSONObject("Price").getString("TotalDisplayFare"));
                        mTripData.setTourUniqueId(lTripObj.getString("TourUniqueId"));
                        mTripData.setBookingDate(lTripObj.getString("bookingDate"));
                        mTripData.setProductCode(lTripObj.getString("productCode"));
                        mTripData.setAgeBands(lTripObj.getJSONArray("AgeBands"));


                        mTourgradeData.add(mTripData);
                    }

                    if (mTourgradeData.size() == 0) {
                        commonUtils.toastShort("Trip List Not Foud", getContext());
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt("search_id", getArguments().getInt("search_id"));
                    bundle.putString("tokenData", tokenData.toString());
                    bundle.putSerializable("trip_list", (Serializable) mTourgradeData);

                    intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame, new TripListFragment(), bundle, true);

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    @OnClick(R.id.back_button)
    public void goBack() {
        getActivity().onBackPressed();
    }


    @OnClick(R.id.txtDetails)
    public void showDetails() {
        if (selTab == 2) return;
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.VISIBLE);
        txtImpInfo.setTextColor(getContext().getResources().getColor(R.color.transparent));
        txtOverView.setTextColor(getContext().getResources().getColor(R.color.transparent));
        txtDetails.setTextColor(getContext().getResources().getColor(R.color.white));
        rvImpInfo.setVisibility(View.GONE);
        txtOVDetails.setVisibility(View.GONE);
        txtDetailsVal.setVisibility(View.VISIBLE);
        txtTabType.setText("Details");
        selTab = 2;
    }

    @OnClick(R.id.txtOverView)
    public void showOverView() {
        if (selTab == 1) return;
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.GONE);
        txtImpInfo.setTextColor(getContext().getResources().getColor(R.color.transparent));
        txtOverView.setTextColor(getContext().getResources().getColor(R.color.white));
        txtDetails.setTextColor(getContext().getResources().getColor(R.color.transparent));
        rvImpInfo.setVisibility(View.GONE);
        txtOVDetails.setVisibility(View.VISIBLE);
        txtDetailsVal.setVisibility(View.GONE);
        txtTabType.setText("Over view");
        selTab = 1;
    }

    @OnClick(R.id.txtImpInfo)
    public void showInfoTab() {
        if (selTab == 0) return;
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        txtImpInfo.setTextColor(getContext().getResources().getColor(R.color.white));
        txtOverView.setTextColor(getContext().getResources().getColor(R.color.transparent));
        txtDetails.setTextColor(getContext().getResources().getColor(R.color.transparent));
        rvImpInfo.setVisibility(View.VISIBLE);
        txtOVDetails.setVisibility(View.GONE);
        txtDetailsVal.setVisibility(View.GONE);
        txtTabType.setText("Important Information");
        selTab = 0;
    }


}
