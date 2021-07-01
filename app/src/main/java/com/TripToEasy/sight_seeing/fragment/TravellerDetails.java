package com.TripToEasy.sight_seeing.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.WebViewActivity;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.CountryInfo;
import com.TripToEasy.model.parsingModel.sightSeeing.TRData;
import com.TripToEasy.model.parsingModel.sightSeeing.TripData;
import com.TripToEasy.model.parsingModel.transfers.TransfersQuestionsInfo;
import com.TripToEasy.model.requestModel.FriendsData;
import com.TripToEasy.sight_seeing.adapter.TagFriendsListAdp;
import com.TripToEasy.sight_seeing.sightseeing_adapters.TrDetailsAdapter;
import com.TripToEasy.transfers.adapter.TransfersQuestionAdapter;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.ApiConstants;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TravellerDetails extends BaseFragment implements WebInterface, TagFriendsListAdp.onCheckBoxClicked
{

    @BindView(R.id.rvTrDetails) RecyclerView rvTrDetails;
    @BindView(R.id.spinn_countrycode) Spinner spinn_countrycode;
    @BindView(R.id.rv_questions) RecyclerView rv_questions;
    @BindView(R.id.llayout3) LinearLayout quesLayout;
    @BindView(R.id.email_id_prim) EditText emailPrime;
    @BindView(R.id.mobile_prime) EditText mobilePrime;
    @BindView(R.id.spinnerHotelPickup) Spinner spinnerHotelPickup;
    @BindView(R.id.txtHotelPackage) TextView txtHotelPackage;
    @BindView(R.id.txtGrandTotal) TextView txtGrandTotal;
    @BindView(R.id.llHotelPackage) LinearLayout llHotelPackage;
    @BindView(R.id.llHotelName) LinearLayout llHotelName;
    @BindView(R.id.edHotelName) EditText edHotelName;
    @BindView(R.id.llPromCode) LinearLayout llPromCode;
    @BindView(R.id.edPromoCode) EditText edPromoCode;
    @BindView(R.id.cbTerms) CheckBox cbTerms;
    @BindView(R.id.txtTag) TextView txtTag;
    String Hotelpickupname = "", HotelPickupId = "",TourUniqId,PaymentMethod = "";
    JSONObject tokenData;
    JSONArray mBookingQuestions;
    TripData mTripData;



    /*  @OnClick(R.id.back_button)
    public void goBack()
    {
        getActivity().onBackPressed();
    }
*/
    int AdultCount = 0, ChildCount = 0, Infant = 0;
    List<TRData> mTrDataList = new ArrayList<>();
    ArrayList<CountryInfo> countryList = new ArrayList<CountryInfo>();
    List<TransfersQuestionsInfo> quesList = new ArrayList<TransfersQuestionsInfo>();
    TransfersQuestionAdapter transfersQuestionAdapter;



    @OnClick(R.id.llMain)
    public void mainClick() {
        return;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragemet_tr_details;
    }


    TrDetailsAdapter trDetailsAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mTripData = (TripData) getArguments().getSerializable("sel_data");
        String data = getArguments().getString("tokenData");
        try
        {
            tokenData=new JSONObject(data);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        commonUtils.linearLayout(rvTrDetails, getContext());
        commonUtils.linearLayout(rv_questions,getContext());

        if (applicationPreference.getData(applicationPreference.login_flag).equals("true"))
        {
            llPromCode.setVisibility(View.VISIBLE);
            txtTag.setVisibility(View.VISIBLE);
        }
        else
        {
            llPromCode.setVisibility(View.GONE);
            txtTag.setVisibility(View.GONE);
        }

        emailPrime.setText(applicationPreference.getData("user_email"));
        mobilePrime.setText(applicationPreference.getData("user_mobile"));
        txtHotelPackage.setText(Html.fromHtml("Hotel Package <sup>*<sup>"));
        spinnerHotelPickup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Hotelpickupname = mHotelList.get(i);
                HotelPickupId = mHotelIDs.get(i);

                if (HotelPickupId.equalsIgnoreCase("notListed"))
                {
                    llHotelName.setVisibility(View.VISIBLE);
                }
                else
                {
                    llHotelName.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            { }
        });

        loadCountries();
        getDetails();


    }

    @OnClick(R.id.txtApplyPromoCode)
    public void getPromoCode() {
        try {


            WebServiceController lController = new WebServiceController(getContext(), this);

            if (TextUtils.isEmpty(edPromoCode.getText().toString()))
            {
                commonUtils.toastShort("Please Enter Promocode", getActivity());
                return;
            }
            RequestParams params = new RequestParams();
            JSONObject lJsonObject = new JSONObject();
            lJsonObject.put("promo_code", edPromoCode.getText().toString());
            lJsonObject.put("module", "flight");
            lJsonObject.put("total_amount_val", mPrice);
            lJsonObject.put("user_id", applicationPreference.getData(applicationPreference.userId));
            lJsonObject.put("email", applicationPreference.getData(applicationPreference.userEmail));
            lJsonObject.put("convenience_fee", "0");
            lJsonObject.put("currency", Global.currencySymbol);
            params.put("get_promo", lJsonObject);
            lController.postRequest(ApiConstants.URL_PROMOCODE_LIST, params, 3);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void getDetails() {

        try {
            WebServiceController lController = new WebServiceController(getContext(), this);
            RequestParams params = new RequestParams();
            params.put("booking_source", apiConstants.BOOKING_SOURCE);
            params.put("search_id", getArguments().getInt("search_id"));
            params.put("product_code", mTripData.getProductCode());
            params.put("booking_date", mTripData.getBookingDate());
            params.put("tour_uniq_id", mTripData.getTourUniqueId());
            params.put("op", "block_trip");
            params.put("grade_code", mTripData.getGradeCode());
            params.put("grade_title", mTripData.getGradeTitle());

            params.put("additional_info", tokenData.getString("AdditionalInfo"));
            params.put("inclusions",tokenData.getString("Inclusions"));
            params.put("exclusions",tokenData.getString("Exclusions") );
            params.put("short_desc",tokenData.getString("ShortDescription"));
            params.put("voucher_req",tokenData.getString("voucher_req"));
            params.put("API_Price",tokenData.getString("API_Price") );


            JSONArray lJsonArray = mTripData.getAgeBands();
            for (int i = 0; i < lJsonArray.length(); i++) {
                JSONObject lJsonObject = lJsonArray.getJSONObject(i);

                if (lJsonObject.getInt("bandId") == 1) {
                    params.put("Adult_Band_ID", "1");
                    params.put("no_of_Adult", lJsonObject.getInt("count"));
                    AdultCount = lJsonObject.getInt("count");
                } else if (lJsonObject.getInt("bandId") == 2) {
                    params.put("Child_Band_ID", "2");
                    params.put("no_of_Child", lJsonObject.getInt("count"));
                    ChildCount = lJsonObject.getInt("count");
                } else if (lJsonObject.getInt("bandId") == 3) {
                    params.put("Infant_Band_ID", "2");
                    params.put("no_of_Infant", lJsonObject.getInt("count"));
                    Infant = lJsonObject.getInt("count");
                }
            }

           /* params.put("Adult_Band_ID", "1");
            params.put("Child_Band_ID", "2");
            params.put("no_of_Child", mSelChildCount);
            params.put("Infant_Band_ID", "3");
            params.put("no_of_Infant", mSelInInfant);*/
            Log.e("request", params.toString());

            lController.postRequest(ApiConstants.BOOKING, params, 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    List<String> mHotelList = new ArrayList<>();
    List<String> mHotelIDs = new ArrayList<>();

    String mPrice = "";
    String mToken = "", mTokenKey = "";

    @Override
    public void getResponse(String response, int flag)
    {
        Log.e("response", response);

        if (flag == 1) {
            try {
                JSONObject lMainObj = new JSONObject(response);
                JSONObject preBookingObj = lMainObj.getJSONObject("pre_booking_params");
                mBookingQuestions = preBookingObj.getJSONArray("BookingQuestions");
                JSONArray quesArr = preBookingObj.getJSONArray("BookingQuestions");
                // JSONArray quesArr=new JSONArray(bookingQuestion);
                for (int j=0;j<quesArr.length();j++){
                    quesList.add(new TransfersQuestionsInfo(
                            quesArr.getJSONObject(j).getString("questionId"),
                            quesArr.getJSONObject(j).getString("stringQuestionId"),
                            quesArr.getJSONObject(j).getString("message"),
                            quesArr.getJSONObject(j).getString("subTitle"),
                            null
                    ));
                }
                if(quesList.size()>0)
                {
                    quesLayout.setVisibility(View.VISIBLE);
                }
                TourUniqId = preBookingObj.getString("tour_uniq_id");
                PaymentMethod = lMainObj.getJSONArray("active_payment_options").getString(0);
                mToken = lMainObj.getString("token");
                mTokenKey = lMainObj.getString("token_key");
                mPrice = preBookingObj.getJSONObject("Price").getString("TotalDisplayFare");
                txtGrandTotal.setText("Grand Total\n" + String.format("%.2f",(Double.parseDouble(mPrice)/Double.parseDouble(Global.currencyValue))));
                if (preBookingObj.getBoolean("HotelPickup"))
                {
                    llHotelPackage.setVisibility(View.VISIBLE);
                    JSONArray HotelList = preBookingObj.getJSONArray("HotelList");
                    for (int i = 0; i < HotelList.length(); i++) {
                        JSONObject object = HotelList.getJSONObject(i);
                        mHotelList.add(object.getString("hotel_name"));
                        mHotelIDs.add(object.getString("hotel_id"));
                    }
                    ArrayAdapter<String> hotelAdp = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1, android.R.id.text1, mHotelList);
                    spinnerHotelPickup.setAdapter(hotelAdp);

                    int j = 0;


                    TRData lData;

                    while (j < AdultCount)
                    {
                        lData = new TRData();
                        j++;
                        lData.setHeader("Adult " + j);
                        mTrDataList.add(lData);
                    }
                    j = 0;
                    while (j < ChildCount) {
                        lData = new TRData();
                        j++;
                        lData.setHeader("Child " + j);
                        mTrDataList.add(lData);
                    }
                    j = 0;
                    while (j < Infant) {
                        lData = new TRData();
                        j++;
                        lData.setHeader("Infant " + j);
                        mTrDataList.add(lData);
                    }
                    trDetailsAdapter = new TrDetailsAdapter(mTrDataList, getContext(), mBookingQuestions);
                    rvTrDetails.setAdapter(trDetailsAdapter);

                    transfersQuestionAdapter = new TransfersQuestionAdapter(getActivity(), quesList);
                    rv_questions.setAdapter(transfersQuestionAdapter);
                } else {
                    llHotelPackage.setVisibility(View.GONE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (flag == 2)
        {
            try {

                JSONObject lJsonObject = new JSONObject(response);

                if (lJsonObject.getInt("status") == 1) {
                    JSONObject lData = lJsonObject.getJSONObject("data");
                    String Url = lData.getString("payment_url");
                    Intent intent;
                    intent = new Intent(getContext().getApplicationContext(), WebViewActivity.class);
                    intent.putExtra("weburl", Url);
                    getActivity().startActivity(intent);
                    getActivity().finish();

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (flag == 3) {
            try {
                JSONObject lJsonObject = new JSONObject(response);
                llPromCode.setVisibility(View.GONE);
                commonUtils.toastShort("Promo Code Applied", getActivity());
                mPrice = lJsonObject.getString("total_amount_val");
                txtGrandTotal.setText("Grand Total\n" + String.format("%.2f",(Double.parseDouble(mPrice)/Double.parseDouble(Global.currencyValue))));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (flag == 4) {
            try {
                JSONObject lJsonObject = new JSONObject(response);
                JSONArray lJsonArray = lJsonObject.getJSONArray("friends_list");
                mFriendsList.clear();
                FriendsData lFriendsData;
                for (int i = 0; i < lJsonArray.length(); i++) {
                    lFriendsData = new FriendsData();
                    JSONObject lObject = lJsonArray.getJSONObject(i);
                    lFriendsData.setUser_id(lObject.getString("user_id"));
                    lFriendsData.setImage(lObject.getString("image"));
                    lFriendsData.setFirst_name(lObject.getString("first_name"));
                    lFriendsData.setLast_name(lObject.getString("last_name"));
                    mFriendsList.add(lFriendsData);
                }

                selTagFriends();

            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }


    @OnClick(R.id.txtConBooking)
    public void confirmBooking() {

        JSONArray lPassengers = new JSONArray();


        JSONArray Answer1 = new JSONArray();
        JSONArray Answer2 = new JSONArray();
        JSONArray Answer3 = new JSONArray();
        JSONArray Answer4 = new JSONArray();
        JSONArray questions = new JSONArray();
       JSONObject pax_question = new JSONObject();
       JSONObject BookingQuestions = new JSONObject();

        String heightType = "", weightType = "";

        for (int i = 0; i < mTrDataList.size(); i++)
        {
            if (TextUtils.isEmpty(mTrDataList.get(i).getFirstName())) {
                commonUtils.toastShort("Please Enter all the details", getActivity());
                return;
            } else if (TextUtils.isEmpty(mTrDataList.get(i).getLastName())) {
                commonUtils.toastShort("Please Enter all the details", getActivity());
                return;

            } else if (mTrDataList.get(i).getLastName().length()<3) {
                commonUtils.toastShort("Last name should contain more then 2 letter", getActivity());
                return;

            } else if (mTrDataList.get(i).isShowWeight() && TextUtils.isEmpty(mTrDataList.get(i).getWeight())) {
                commonUtils.toastShort("Please Enter all the details", getActivity());
                return;

            } else if (mTrDataList.get(i).isShowHeight() && TextUtils.isEmpty(mTrDataList.get(i).getHeight())) {
                commonUtils.toastShort("Please Enter all the details", getActivity());
                return;

            } else if (mTrDataList.get(i).isShowDOB() && TextUtils.isEmpty(mTrDataList.get(i).getDOB())) {
                commonUtils.toastShort("Please Enter all the details", getActivity());
                return;

            } else if (mTrDataList.get(i).isShowPassportExpire() && TextUtils.isEmpty(mTrDataList.get(i).getPassportExpiryDate())) {
                commonUtils.toastShort("Please Enter all the details", getActivity());
                return;


            } else if (llHotelPackage.getVisibility() == View.VISIBLE && llHotelName.getVisibility() == View.VISIBLE && TextUtils.isEmpty(edHotelName.getText().toString())) {
                commonUtils.toastShort("Please Hotel Name", getActivity());
                return;

            } else if (TextUtils.isEmpty(emailPrime.getText().toString())) {
                commonUtils.toastShort("Please Enter email address", getActivity());
                return;

            } else if (TextUtils.isEmpty(mobilePrime.getText().toString())) {
                commonUtils.toastShort("Please Enter mobile number", getActivity());
                return;

            } else if (!cbTerms.isChecked()) {
                commonUtils.toastShort("Please accept the Terms and Conditions", getActivity());
                return;

            } else {
                try {
                    JSONObject lAnswer;
                    JSONObject lData = new JSONObject();
                    lData.put("FirstName", mTrDataList.get(i).getFirstName());
                    lData.put("LastName", mTrDataList.get(i).getLastName());
                    lData.put("Pax_Type", "1");
                    lData.put("Title", mTrDataList.get(i).getTitle());

                    if (mTrDataList.get(i).isShowWeight())
                    {
                        if (i == 0) weightType = mTrDataList.get(i).getWeightType();
                        Answer1.put(mTrDataList.get(i).getWeight());
                    }
                    if (mTrDataList.get(i).isShowHeight())
                    {
                        if (i == 0) heightType = mTrDataList.get(i).getHeightType();
                        Answer2.put(mTrDataList.get(i).getHeight());
                    }
                    if (mTrDataList.get(i).isShowDOB())
                    {
                        Answer3.put(mTrDataList.get(i).getDOB());
                    }
                    if (mTrDataList.get(i).isShowPassportExpire())
                    {
                        Answer4.put(mTrDataList.get(i).getPassportExpiryDate());
                    }


                    lPassengers.put(lData);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }
        try {

            JSONArray question = new JSONArray();
            JSONArray answer = new JSONArray();
            for (int j = 0; j < mBookingQuestions.length(); j++)
            {
                JSONObject lJsonObject = mBookingQuestions.getJSONObject(j);
                question.put(lJsonObject.getInt("questionId"));

                if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("weights_passengerWeights")) {
                    answer.put(Answer1);
                } else if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("heights_passengerHeights")) {
                    answer.put(Answer2);
                } else if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("dateOfBirth_dob")) {
                    answer.put(Answer3);
                } else if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("passport_expiry")) {
                    answer.put(Answer4);
                }
            }
            for(int j = 0; j < question.length(); j++)
            {
                JSONObject lJsonObject = question.getJSONObject(j);
                pax_question.put( lJsonObject.getString("questionId"),answer.get(j));

            }

            for (int j = 0; j < mBookingQuestions.length(); j++)
            {
                JSONObject lJsonObject = mBookingQuestions.getJSONObject(j);
                JSONObject lBookingAns = new JSONObject();
                if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("weights_passengerWeights")) {
                    lBookingAns.put("Question_id", lJsonObject.getInt("questionId"));
                    lBookingAns.put("Answer", quesList.get(j).getAnswer());
                } else if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("heights_passengerHeights")) {
                    lBookingAns.put("Question_id", lJsonObject.getInt("questionId"));
                    lBookingAns.put("Answer", quesList.get(j).getAnswer());
                } else if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("dateOfBirth_dob")) {
                    lBookingAns.put("Question_id", lJsonObject.getInt("questionId"));
                    lBookingAns.put("Answer", quesList.get(j).getAnswer());
                } else if (lJsonObject.getString("stringQuestionId").equalsIgnoreCase("passport_expiry")) {
                    lBookingAns.put("Question_id", lJsonObject.getInt("questionId"));
                    lBookingAns.put("Answer", quesList.get(j).getAnswer());
                }
                questions.put(lBookingAns);
            }

            BookingQuestions.put("pax_question",pax_question);
            BookingQuestions.put("Questions",questions);




        } catch (Exception ex)
        {
            ex.printStackTrace();
        }


        WebServiceController lController = new WebServiceController(getContext(), this);
        RequestParams params = new RequestParams();
        params.put("booking_source", apiConstants.BOOKING_SOURCE);
        params.put("search_id", getArguments().getInt("search_id"));
        params.put("product_code", mTripData.getProductCode());
        params.put("booking_date", mTripData.getBookingDate());
        params.put("BlockTourId", TourUniqId);
        params.put("age_band", mTripData.getAgeBands());
        params.put("op", "block_trip");
        params.put("grade_code", mTripData.getGradeCode());
        params.put("grade_title", mTripData.getGradeTitle());
        params.put("Email", emailPrime.getText().toString());
        params.put("ContactNo", mobilePrime.getText().toString());
        params.put("final_fare", mPrice);
        params.put("Passengers", lPassengers);
        params.put("height", heightType);
        params.put("weight", weightType);
        params.put("HotelPickupId", HotelPickupId);
        if(quesList.size()>0)
        {
            params.put("BookingQuestions", BookingQuestions);
        }

        if (llHotelPackage.getVisibility() == View.VISIBLE && llHotelName.getVisibility() == View.VISIBLE) {
            params.put("Hotelpickupname", edHotelName.getText().toString());
        } else {
            params.put("Hotelpickupname", Hotelpickupname);
        }
        JSONArray lTaggedData = new JSONArray();
        for (int j = 0; j < mFriendsList.size(); j++) {
            if (mFriendsList.get(j).isTagged()) {
                lTaggedData.put(mFriendsList.get(j).getUser_id());
            }
        }
        params.put("tageduser", lTaggedData);

        params.put("payment_method", PaymentMethod);
        params.put("token", mToken);
        params.put("token_key", mTokenKey);
        params.put("customer_id", applicationPreference.getData(applicationPreference.userId));
        Log.e("pre booking params is ",params.toString());
        lController.postRequest(ApiConstants.PRE_BOOKING, params, 2);

    }


    public void loadCountries() {
        Thread loadCountries = new Thread() {
            @Override
            public void run() {
                countryList.clear();
                dbAdapter.open();

//                if (Global.arrCountry.size() == 0) {
                countryList.addAll(dbAdapter.getCountryList());

                for (int i = 0; i < countryList.size(); i++) {
                    Global.arrCountry.add(countryList.get(i).getName() + "  ( +" + countryList.get(i).getPhonecode() + " )");
//                        countryList.add(dbAdapter.getCountryList().get(i));
                }
//                }

                dbAdapter.close();
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spinn_countrycode.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, R.id.spinn_text, Global.arrCountry));
                            spinn_countrycode.setSelection(89);
                        }
                    });
                } catch (NullPointerException e) {
                    Log.e("Exception", e.toString());
                }

            }
        };
        loadCountries.start();
    }

    List<FriendsData> mFriendsList = new ArrayList<>();


    @OnClick(R.id.txtTag)
    public void getUserList() {
        if (mFriendsList.size() == 0) {
            RequestParams params = new RequestParams();
            params.put("user_id", applicationPreference.getData(applicationPreference.userId));
            params.put("tab", "friends_list");
            WebServiceController wsc = new WebServiceController(getActivity(), this);
            wsc.postRequest(ApiConstants.LOGIN_STORY, params, 4);

        } else {
            selTagFriends();
        }

    }

    Dialog dialog;

    private void selTagFriends() {
        SearchView searchView;
        final ListView cityListView;
        TextView txtTag;

        dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.friends_tag_list);
        searchView = (SearchView) dialog.findViewById(R.id.searchView);
        cityListView = (ListView) dialog.findViewById(R.id.friendListView);
        txtTag = dialog.findViewById(R.id.txtTag);
        final TagFriendsListAdp mSearchAdapter = new TagFriendsListAdp(getContext(), mFriendsList, this);
        cityListView.setAdapter(mSearchAdapter);
        cityListView.setTextFilterEnabled(true);
        searchView.setQueryHint("Find Friends");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mSearchAdapter.getFilter().filter(newText);
                return false;
            }
        });
        txtTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }


    @Override
    public void onTagged(String Userid, boolean state) {
        for (int i = 0; i < mFriendsList.size(); i++) {
            if (mFriendsList.get(i).getUser_id().equalsIgnoreCase(Userid)) {
                mFriendsList.get(i).setTagged(state);
                break;
            }
        }
    }




}
