package com.TripToEasy.transfers.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.TripToEasy.flight.fragment.PaymentFlight;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.CountryInfo;
import com.TripToEasy.model.parsingModel.transfers.TransfersHotelInfo;
import com.TripToEasy.model.parsingModel.transfers.TransfersQuestionsInfo;
import com.TripToEasy.model.parsingModel.transfers.TransfersTravellerInfo;
import com.TripToEasy.transfers.adapter.TransfersBookingTravellerAdapter;
import com.TripToEasy.transfers.adapter.TransfersQuestionAdapter;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.ProgressLoader;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class BookTranfersFragment extends BaseFragment implements WebInterface {
    ProgressLoader progressLoader;
    WebServiceController webServiceController;
    ArrayList<CountryInfo> countryList = new ArrayList<>();
    String walletSelectType="off";
    @BindView(R.id.email_id_prim)
    EditText emailPrime;

    @BindView(R.id.mobile_prime)
    EditText mobilePrime;

    @BindView(R.id.spinn_countrycode)
    Spinner spinn_countrycode;

    @BindView(R.id.rv_travellers)
    RecyclerView rv_travellers;

    @BindView(R.id.rv_questions)
    RecyclerView rv_questions;

    @BindView(R.id.llayout3)
    LinearLayout quesLayout;

    @BindView(R.id.llayout6)
    LinearLayout hotelListLayout;

    @BindView(R.id.spinn_hotels)
    Spinner spinn_hotels;

    @BindView(R.id.total_fare)
    TextView total_fare;

    @BindView(R.id.tax_price)
    TextView tax_price;

    @BindView(R.id.convenience_fee)
    TextView convenience_fee;

    @BindView(R.id.grand_total_price)
    TextView grand_total_price;

    @BindView(R.id.transfer_bkg)
    ImageView productImg;

    @BindView(R.id.product_name)
    TextView product_name;

    @BindView(R.id.tour_code)
    TextView tour_code;

    @BindView(R.id.tour_name)
    TextView tour_name;

    @BindView(R.id.description)
    TextView description;

    @BindView(R.id.final_price)
    TextView final_price;

    @BindView(R.id.booking_date)
    TextView tv_booking_date;

    @BindView(R.id.traveller_count)
    TextView traveller_count;

    @BindView(R.id.tour_rating)
    RatingBar tour_rating;


    @OnClick(R.id.book_now)
    void doBooking(){

            walletSelectType="off";
            createBookingParam();
    }


    String response=null,bookingQuestion=null;
    int travellerCount=0;
    public BookTranfersFragment() {
        // Required empty public constructor
    }

    TransfersBookingTravellerAdapter transfersTravellerAdapter;
    TransfersQuestionAdapter transfersQuestionAdapter;

    @SuppressLint("ValidFragment")
    public BookTranfersFragment(String response,String bookingQuestion) {
        this.response=response;
        this.bookingQuestion=bookingQuestion;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_book_tranfers;
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_tranfers, container, false);
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        progressLoader = new ProgressLoader();
        webServiceController = new WebServiceController(getActivity(),BookTranfersFragment.this);
        commonUtils.linearLayout(rv_questions,getActivity());
        commonUtils.linearLayout(rv_travellers,getActivity());

        loadCountries();
        loadData();
    }
    String grade_code,product_title,product_code,grade_name,grade_desc,ProductImage,bookingDate,totalAmount,
            taxAmt,convienceFee,token,tokenKey,BlockTourId,booking_source,moduleType;
    int StarRating;
    List<TransfersTravellerInfo> travellerList = new ArrayList<TransfersTravellerInfo>();
    List<TransfersQuestionsInfo> quesList = new ArrayList<TransfersQuestionsInfo>();
    List<TransfersHotelInfo> hotelsList = new ArrayList<TransfersHotelInfo>();
    ArrayList<String> arrHotelNames=new ArrayList<String>();

    public void loadData(){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject dataObj=jsonObject.getJSONObject("data");
            JSONObject bookObj=dataObj.getJSONObject("pre_booking_params");
            grade_code=bookObj.getString("grade_code");
            product_title=bookObj.getString("product_title");
            product_code=bookObj.getString("product_code");
            booking_source=bookObj.getString("booking_source");
            grade_name=bookObj.getString("GradeDescription");
            grade_desc=bookObj.getString("grade_desc");
            BlockTourId=bookObj.getString("BlockTourId");
            ProductImage=bookObj.getString("ProductImage");
            bookingDate=bookObj.getString("booking_date");
            totalAmount=dataObj.getString("total_price");
            taxAmt=dataObj.getString("tax_service_sum");
            convienceFee=dataObj.getString("convenience_fees");
            token=dataObj.getString("token");
            tokenKey=dataObj.getString("token_key");
            moduleType=dataObj.getString("module_value");
            StarRating=bookObj.getInt("StarRating");
            JSONArray ageBandArr=bookObj.getJSONArray("AgeBands");
            for (int i=0;i<ageBandArr.length();i++){
                travellerCount=travellerCount+ageBandArr.getJSONObject(i).getInt("count");
            }
            JSONArray quesArr = bookObj.getJSONArray("BookingQuestions");
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
            if(quesList.size()>0){
                quesLayout.setVisibility(View.VISIBLE);
            }

            JSONArray hotelListArr=bookObj.getJSONArray("HotelList");
            for(int k=0;k<hotelListArr.length();k++){
                hotelsList.add(new TransfersHotelInfo(hotelListArr.getJSONObject(k).getString("hotel_name"),
                        hotelListArr.getJSONObject(k).getString("hotel_id") ));
                arrHotelNames.add(hotelListArr.getJSONObject(k).getString("hotel_name"));
            }
            if(bookObj.getBoolean("HotelPickup")){
                hotelListLayout.setVisibility(View.VISIBLE);
            }

            if(hotelsList.size()==0){
                JSONObject hotelOptionObj=bookObj.getJSONObject("Hotel_Pickup_Option");
                arrHotelNames.add(hotelOptionObj.getString("local"));
                arrHotelNames.add(hotelOptionObj.getString("notBooked"));
                arrHotelNames.add(hotelOptionObj.getString("notListed"));
            }

            spinn_hotels.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item,R.id.spinn_text, arrHotelNames));

            Picasso.get().load(ProductImage).placeholder(R.drawable.ic_hotel_bg).into(productImg);
            product_name.setText(product_title);
            tour_code.setText(grade_code);
            tour_name.setText(grade_name);
            description.setText(grade_desc);
            traveller_count.setText(travellerCount+" Travellers");
            tv_booking_date.setText(bookingDate);
            final_price.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(totalAmount)/Double.parseDouble(Global.currencyValue))));
            total_fare.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(totalAmount)/Double.parseDouble(Global.currencyValue))));
            convenience_fee.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(convienceFee)/Double.parseDouble(Global.currencyValue))));
            grand_total_price.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(String.valueOf(Float.valueOf(totalAmount)+Float.valueOf(convienceFee)))/Double.parseDouble(Global.currencyValue))));
            tax_price.setText(taxAmt+"");
            tour_rating.setRating(Float.parseFloat(StarRating+""));

            for (int i = 0; i < travellerCount; i++) {
                travellerList.add(new TransfersTravellerInfo("Mr","Adult", null, null));
            }

            transfersTravellerAdapter = new TransfersBookingTravellerAdapter(getActivity(), travellerList);
            rv_travellers.setAdapter(transfersTravellerAdapter);

            transfersQuestionAdapter = new TransfersQuestionAdapter(getActivity(), quesList);
            rv_questions.setAdapter(transfersQuestionAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void createBookingParam(){
        if(emailPrime.getText().toString().trim().equals("")){
            commonUtils.toastShort("Please enter email id",getActivity());
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailPrime.getText().toString().trim()).matches()){
            commonUtils.toastShort("enter valid email",getActivity());
        }
        else  if(mobilePrime.getText().toString().trim().equals("")){
            commonUtils.toastShort("Please enter mobile number",getActivity());
        }
        else  if(mobilePrime.getText().toString().trim().length()!=10){
            commonUtils.toastShort("enter valid mobile number",getActivity());
        }else {
            try {
            JSONObject paramObj=new JSONObject();
            JSONArray passTypeArr=new JSONArray();
            for(int i=0;i<travellerList.size();i++){
                switch (travellerList.get(i).getTypeValue()){
                    case "Adult" :
                        passTypeArr.put("1");
                        break;
                    case "Child" :
                        passTypeArr.put("2");
                        break;
                    case "Infant" :
                        passTypeArr.put("3");
                        break;
                }

            }

                JSONArray leadPassArr=new JSONArray();
                leadPassArr.put("");

                JSONArray passTitleArr=new JSONArray();
                for(int i=0;i<travellerList.size();i++){
                    switch (travellerList.get(i).getTitleValue()){
                        case "Mr":
                            passTitleArr.put("1");
                            break;
                        case "Ms":
                            passTitleArr.put("2");
                            break;
                        case "Miss":
                            passTitleArr.put("3");
                            break;
                        case "Master":
                            passTitleArr.put("5");
                            break;
                        case "Mrs":
                            passTitleArr.put("4");
                            break;
                    }

                }

                JSONArray fNameArr=new JSONArray();
                for(int i=0;i<travellerList.size();i++){
                    fNameArr.put(travellerList.get(i).getFirstName());
                }

                JSONArray lNameArr=new JSONArray();
                for(int i=0;i<travellerList.size();i++){
                    lNameArr.put(travellerList.get(i).getLastName());
                }

                JSONArray quesIDArr=new JSONArray();
                for(int i=0;i<quesList.size();i++){
                    quesIDArr.put(new JSONArray().put(quesList.get(i).getQuesId()));
                }

                JSONArray ansArr=new JSONArray();
                for(int i=0;i<quesList.size();i++){
                    ansArr.put(new JSONArray().put(quesList.get(i).getAnswer()));
                }

                paramObj.put("BlockTourId",BlockTourId);
                paramObj.put("token",token);
                paramObj.put("token_key",tokenKey);
                paramObj.put("booking_source",booking_source);
                paramObj.put("op","book_flight");
                paramObj.put("promo_code_discount_val","0.00");
                paramObj.put("promo_code","");
                paramObj.put("promo_actual_value","");
                paramObj.put("code","");
                paramObj.put("module_type",moduleType);
                if (applicationPreference.getData("login_flag").equals("true"))
                {
                    paramObj.put("customer_id", applicationPreference.getData("user_id"));
                } else {
                    paramObj.put("customer_id", "");
                }
                paramObj.put("total_amount_val",String.valueOf(Float.valueOf(totalAmount)+Float.valueOf(convienceFee)));
                paramObj.put("convenience_fee",convienceFee);
                paramObj.put("currency_symbol","Rs");
                paramObj.put("currency","INR");
                paramObj.put("billing_country","92");
                paramObj.put("billing_city","bangalore");
                paramObj.put("billing_zipcode","560037");
                paramObj.put("billing_address_1","bangalore E-city");
                paramObj.put("country_code",Global.countryList.get(spinn_countrycode.getSelectedItemPosition()).getIso());
                paramObj.put("passenger_contact",mobilePrime.getText().toString());
                paramObj.put("billing_email",emailPrime.getText().toString());
                paramObj.put("tc","on");
                paramObj.put("payment_method","PNHB1");
                if(hotelListLayout.getVisibility()==View.VISIBLE)
                {
                    if(hotelsList.size()==0)
                    {
                        paramObj.put("hotel_pickup_list_name",spinn_hotels.getSelectedItem());

                    }else
                        {
                        paramObj.put("hotel_pickup_list_name",hotelsList.get(spinn_hotels.getSelectedItemPosition()).getHotel_id());
                    }
                }else {
                    paramObj.put("hotel_pickup_list_name","");
                }

                paramObj.put("passenger_type",passTypeArr);
                paramObj.put("lead_passenger",leadPassArr);
                paramObj.put("name_title",passTitleArr);
                paramObj.put("first_name",fNameArr);
                paramObj.put("last_name",lNameArr);
                if(quesList.size()>0){
                    paramObj.put("question_Id",quesIDArr);
                    paramObj.put("question",ansArr);
                }

                RequestParams params = new RequestParams();
                params.put("booking_params", paramObj.toString());
                if (applicationPreference.getData("login_flag").equals("true"))
                {
                    params.put("wallet_bal",walletSelectType);
                } else {
                    params.put("wallet_bal", "off");
                }


                Log.i("request", params.toString());
                webServiceController.postRequest(
                        apiConstants.TRANSFERS_PRE_BOOKING,
                        params,
                        2);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void loadCountries()
    {
        if (Global.arrCountry.size()==0)
        {
            progressLoader.ShowProgress(getActivity());
            RequestParams params = new RequestParams();
            webServiceController.postRequest(apiConstants.URL_COUNTRY_LIST,params,1);
        }else
        {
            spinn_countrycode.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item,R.id.spinn_text, Global.arrCountry));
            spinn_countrycode.setSelection(89);
            if(applicationPreference.getData("login_flag").equalsIgnoreCase("true"))
            {
                callWalletAPI();
            }
        }
    }

    @Override
    public void getResponse(String response, int flag) {
        progressLoader.DismissProgress();

        switch (flag){
            case 1:
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray jsonArray=jsonObject.getJSONArray("country_list");
                    for (int i=0;i<jsonArray.length();i++){
                        countryList.add(new CountryInfo(jsonArray.getJSONObject(i).getString("origin"),
                                jsonArray.getJSONObject(i).getString("name"),
                                jsonArray.getJSONObject(i).getString("country_code"),
                                jsonArray.getJSONObject(i).getString("country_code"),
                                jsonArray.getJSONObject(i).getString("iso_country_code")));
                        Global.arrCountry.add(jsonArray.getJSONObject(i).getString("name")+" ("
                                +jsonArray.getJSONObject(i).getString("country_code")+")");
                    }
                    Global.countryList.addAll(countryList);
                    spinn_countrycode.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item,R.id.spinn_text, Global.arrCountry));
                    spinn_countrycode.setSelection(89);

                    if(applicationPreference.getData("login_flag").equalsIgnoreCase("true"))
                    {
                        callWalletAPI();
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                String url=null;
                int status=0;
                Log.e("response", response);

                try{
                    JSONObject dataObj;
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getInt("status") == 1){
                        status = 1;
                        dataObj=jsonObject.getJSONObject("data");
                        url = dataObj.getString("return_url");
                    }else {
                        status = 0;
                        commonUtils.toastShort("Failed to process request", getActivity());
                    }
                }catch (Exception e){
                    commonUtils.toastShort(e.getMessage(), getActivity());
                }

                if(status != 0) {
                    intentAndFragmentService.fragmentDisplay(getActivity(),
                            R.id.main_frame, new PaymentFlight(url), null, false);
                }
                break;
            case 3:

                break;
        }

    }
    public void callWalletAPI()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id",applicationPreference.getData("user_id"));
        webServiceController.postRequest(apiConstants.WALLET_DETAILS,requestParams,3);
    }

}
