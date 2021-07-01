package com.TripToEasy.traveller;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.TripToEasy.flight.fragment.FlightSeatFragment;
import com.TripToEasy.main.adapter.PromocodeAdapter;
import com.TripToEasy.main.fragment.TicketPreviewFragment;
import com.TripToEasy.model.CountryInfo;
import com.TripToEasy.model.parsingModel.PromoCodeInfo;
import com.TripToEasy.utils.ProgressLoader;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.bus.adapter.BusBookingTravellerInfo;
import com.TripToEasy.database.DBAdapter;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.TravellerModel;
import com.TripToEasy.model.parsingModel.bus.BusTravellerInfo;
import com.TripToEasy.model.requestModel.bus.confirmSeat.ConfirmSeatMain;
import com.TripToEasy.traveller.adapter.AdultAdapter;
import com.TripToEasy.traveller.adapter.ChildAdapter;
import com.TripToEasy.traveller.adapter.InfantAdapter;
import com.TripToEasy.traveller.interfaces.NotifyTraveller;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

public class TravellerFragment extends BaseFragment implements NotifyTraveller,
        WebInterface {

    @BindView(R.id.iv_removePromoCode) ImageView iv_removePromoCode;
    @BindView(R.id.tv_select_promo) TextView tv_select_promo;
    @BindView(R.id.tv_discount) TextView tv_discount;
    @BindView(R.id.cb_ssr) CheckBox cb_extraServices;
    @BindView(R.id.spinn_countrycode) Spinner spinn_countrycode;
    @BindView(R.id.tv_seat_num) TextView tv_seat_num;
    @BindView(R.id.adult_count) TextView adultCountText;
    @BindView(R.id.child_count) TextView childCountText;
    @BindView(R.id.infant_count) TextView infantCountText;
    @BindView(R.id.total_price) TextView totalPriceText;
    @BindView(R.id.tv_handbag) TextView tv_handbag;
    @BindView(R.id.tv_chekinbag) TextView tv_chekinbag;
    @BindView(R.id.tv_basefare) TextView tv_basefare;
    @BindView(R.id.tv_taxes) TextView tv_taxes;
    @BindView(R.id.tv_confee) TextView tv_confee;
    @BindView(R.id.tv_gst) TextView tv_gst;
    @BindView(R.id.tv_total) TextView tv_total;
    @BindView(R.id.top_layout) LinearLayout top_layout;
    @BindView(R.id.adult_layout) LinearLayout adultLayout;
    @BindView(R.id.child_layout) LinearLayout childLayout;
    @BindView(R.id.infant_layout) LinearLayout infantLayout;
    @BindView(R.id.booking_detail) LinearLayout bookingDetail;
    @BindView(R.id.adult_list_view) RecyclerView adultListView;
    @BindView(R.id.child_list_view) RecyclerView childListView;
    @BindView(R.id.infants_list_view) RecyclerView infantsListView;
    @BindView(R.id.hotel_booking_detail) LinearLayout hotel_booking_detail;
    @BindView(R.id.bus_booking_detail) LinearLayout bus_booking_detail;
    @BindView(R.id.check_in_date) TextView tv_check_in_date;
    @BindView(R.id.check_out_date) TextView tv_check_out_date;
    @BindView(R.id.tv_no_of_rooms) TextView tv_no_of_rooms;
    @BindView(R.id.tv_hotel_name) TextView tv_hotel_name;
    @BindView(R.id.tv_hotel_addr) TextView tv_hotel_addr;
    @BindView(R.id.tv_room_type) TextView tv_room_type;
    @BindView(R.id.tv_room_total) TextView tv_room_total;
    @BindView(R.id.tv_room_tax) TextView tv_room_tax;
    @BindView(R.id.tv_room_con_fee) TextView tv_room_con_fee;
    @BindView(R.id.user_info_list) RecyclerView travellerInfoLayout;
    @BindView(R.id.email_id_prim) EditText emailPrime;
    @BindView(R.id.mobile_prime) EditText mobilePrime;
    @BindView(R.id.cb_walletPaymentType) CheckBox cb_walletPaymentType;


    DBAdapter dbAdapter;
    Integer adultCount, childCount, infantCount, seatCount, frameType,
            addedAdult = 0, addedChild = 0, addedInfant = 0;
    String totalPrice, fareUpdateResponse = null,totalAmtAfterDiscount="0.00",GST="0.00";
    AdultAdapter adultAdapter;
    ChildAdapter childAdapter;
    InfantAdapter infantAdapter;
    WebServiceController webServiceController;
    Bundle bundle, bundle1;
    String bookingOutPut = "";
    String st_promo_code="",bus_searchId, bus_seatValue, bus_token, bus_ResultToken, bus_tokenValue, bus_scheduler_id, bus_journey_date, bus_pickup_id;
    Integer seatCountValue;
    String discountValue="0.00";
    String[] count;
    ProgressLoader progressLoader;
    String basefare, tax, totPrice, convienceFee, curr_symbol;
    String booking_token, bookingTokenKey, bookingSearchId;
    String  hotel_search_id,currencySymbol;
    String hotel_total_price="", hotel_tax="", hotel_convi_fee="", hotelToken="", hotelTokenKey="";
    String flight_token_table_id="", search_hash_ssr="";
    String bus_arrival_date = "",walletSelectType="off";
    Dialog promocode_dialog;
    BusBookingTravellerInfo busBookingTravellerInfo;
    List<BusTravellerInfo> busTravellerInfos = new ArrayList<BusTravellerInfo>();
    ArrayList<CountryInfo> countryList = new ArrayList<>();
    List<TravellerModel> adultList = new ArrayList<TravellerModel>();
    List<TravellerModel> childList = new ArrayList<TravellerModel>();
    List<TravellerModel> infantList = new ArrayList<TravellerModel>();
    ArrayList<PromoCodeInfo> promocodeList = new ArrayList<PromoCodeInfo>();




    @OnClick(R.id.bt_appy_promo)
    void applyPromoCode() {
        if(st_promo_code.equals("")){
            commonUtils.toastShort("Select Promocode",getActivity());
            return;
        }
        progressLoader.ShowProgress(getActivity());
        JSONObject jsonObject=new JSONObject();
        if(frameType==1)
        {
            try {
                jsonObject.put("promo_code",st_promo_code);
                jsonObject.put("module","flight");
                jsonObject.put("total_amount_val",String.format("%.2f", (Double.parseDouble(totPrice) / Double.parseDouble(Global.currencyValue))));
                jsonObject.put("user_id","");
                jsonObject.put("email","");
                jsonObject.put("convenience_fee",String.format("%.2f", (Double.parseDouble(convienceFee) / Double.parseDouble(Global.currencyValue))));
                jsonObject.put("currency",Global.currencySymbol);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(frameType==2){
            try {
                jsonObject.put("promo_code",st_promo_code);
                jsonObject.put("module","hotel");
                jsonObject.put("total_amount_val",String.format("%.2f", (Double.parseDouble(hotel_total_price) / Double.parseDouble(Global.currencyValue))));
                jsonObject.put("user_id","");
                jsonObject.put("email","");
                jsonObject.put("convenience_fee",String.format("%.2f", (Double.parseDouble(hotel_convi_fee) / Double.parseDouble(Global.currencyValue))));
                jsonObject.put("currency",Global.currencySymbol);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            if(frameType==3){
            try {
                jsonObject.put("promo_code",st_promo_code);
                jsonObject.put("module","bus");
                jsonObject.put("total_amount_val",String.format("%.2f", (Double.parseDouble(totalPrice) / Double.parseDouble(Global.currencyValue))));
                jsonObject.put("user_id","");
                jsonObject.put("email","");
                jsonObject.put("convenience_fee","");
                jsonObject.put("currency",Global.currencySymbol);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RequestParams params = new RequestParams();
        params.put("get_promo",jsonObject.toString());
        webServiceController.postRequest(apiConstants.URL_APPLY_PROMO, params, 3);
    }

    @OnClick(R.id.tv_select_promo)
    void choosePromoCode() {
        progressLoader.ShowProgress(getActivity());
        RequestParams params = new RequestParams();
        webServiceController.postRequest(apiConstants.URL_PROMOCODE_LIST, params, 2);
    }

    @OnClick(R.id.add_adult)
    void addAdult() {
        callAddView(1, null);
    }

    @OnClick(R.id.add_child)
    void addChild() {
        callAddView(2, null);
    }

    @OnClick(R.id.add_infants)
    void addInfants() {
        callAddView(3, null);
    }

    private void callAddView(int actionValue, Integer dbId) {
        int frameView = 0;
        switch (frameType) {
            case 1:
                frameView = R.id.support_frame;
                break;
            case 2:
                frameView = R.id.main_frame;
                break;
        }

        intentAndFragmentService.fragmentDisplay(getActivity(),frameView, new AddTravellerFragment(actionValue,
                        TravellerFragment.this,dbId, frameType), null, true);
    }
    @OnClick(R.id.final_booking)
    void finalBooking() {
        if(cb_walletPaymentType.isChecked())
        {
            walletSelectType="on";
        }
        else
        {
            walletSelectType="off";

        }


        if (frameType == 1) {
            if (Global.countryList.size() == 0)
            {
                commonUtils.toastShort("Country List is loading, Please wait...", getActivity());
            }
            else
            {
                createPreBookParamsFlight();
            }
            createPreBookParamsFlight();
        } else if (frameType == 2) {
            if (Global.countryList.size() == 0) {
                commonUtils.toastShort("Country List is loading, Please wait...", getActivity());
            } else {
                createPreBookParamsHotel();
            }
            createPreBookParamsHotel();
        } else if (frameType == 3) {
            createPreBookParamsBus();
        }

    }

    public void createPreBookParamsFlight() {
        RequestParams params = new RequestParams();
        JSONObject flightBook = new JSONObject();
        JSONArray passArray = new JSONArray();
        JSONObject tokenObj = new JSONObject();


        if (emailPrime.getText().toString().trim().equals(""))
        {
            commonUtils.toastShort("Please enter email id", getActivity());
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailPrime.getText().toString().trim()).matches())
        {
            commonUtils.toastShort("enter valid email", getActivity());
        } else if (mobilePrime.getText().toString().trim().equals(""))
        {
            commonUtils.toastShort("Please enter mobile number", getActivity());
        } else if (mobilePrime.getText().toString().trim().length() < 10) {
            commonUtils.toastShort("enter valid mobile number", getActivity());
        } else {
            try {
                JSONObject Obj = new JSONObject(fareUpdateResponse);
                JSONObject dataObj = Obj.getJSONObject("data");
                flightBook.put("token_key", dataObj.getString("token_key"));
                flightBook.put("Email", emailPrime.getText().toString());
                flightBook.put("ContactNo", mobilePrime.getText().toString());
                flightBook.put("AddressLine1", "E-city");
                flightBook.put("City", "banglore");
                flightBook.put("PinCode", "4456666");
                flightBook.put("CountryCode", Global.countryList.get(spinn_countrycode.getSelectedItemPosition()).getIso());
                flightBook.put("CountryName", Global.countryList.get(spinn_countrycode.getSelectedItemPosition()).getName());
                flightBook.put("search_id", dataObj.getString("search_id"));
                flightBook.put("total_amount_val",String.format("%.2f", ((Double.parseDouble(totPrice)) / Double.parseDouble(Global.currencyValue))));
                flightBook.put("currency", Global.currencySymbol);
                flightBook.put("currency_symbol", Global.currencyIcon);
                flightBook.put("convenience_fee", String.format("%.2f", (Double.parseDouble(convienceFee) / Double.parseDouble(Global.currencyValue))));
                flightBook.put("promo_code_discount_val", discountValue);
                flightBook.put("promo_code", st_promo_code);
                if (applicationPreference.getData("login_flag").equals("true"))
                {
                    flightBook.put("customer_id", applicationPreference.getData("user_id"));
                } else {
                    flightBook.put("customer_id", "");
                }

                flightBook.put("payment_method", "PNHB1");
                for (int i = 0; i < adultList.size(); i++) {
                    if (adultList.size() > 0 && adultList.get(i).getIsSelected() != 1)
                    {
                        if (Global.is_domestic_flag.equals("0") && adultList.get(i).getPassportNumber().equals("NA"))
                        {
                            commonUtils.toastShort("Please Go to Add Passenger Page and Add Pasport Details", getActivity());
                            return;
                        }
                        JSONObject object = new JSONObject();
                        object.put("passenger_type", "Adult");
                        object.put("lead_passenger", "1");
                        object.put("Title", String.valueOf(adultList.get(i).getId()));
                        object.put("FirstName", adultList.get(i).getFirstName());
                        object.put("LastName", adultList.get(i).getLastName());
                        object.put("DateOfBirth", adultList.get(i).getDateOfBirth());

                        if (Global.is_domestic_flag.equals("0"))
                        {
                            object.put("PassportIssueCountry", adultList.get(i).getPassportCountry());
                            object.put("PassportNumber", adultList.get(i).getPassportNumber());
                            object.put("PassportExpiry", adultList.get(i).getPassportExpiry());
                        }
                        else {
                            object.put("PassportIssueCountry", "91");
                            object.put("PassportNumber", "1271623115");
                            object.put("PassportExpiry", "24-01-2025");
                        }

                        object.put("Gender", String.valueOf(adultList.get(i).getGenderType()));
                        passArray.put(object);
                    }
                }
                for (int i = 0; i < childList.size(); i++)
                {
                    if (childList.size() > 0 && childList.get(i).getIsSelected() != 1)
                    {
                        JSONObject object = new JSONObject();
                        object.put("passenger_type", "Child");
                        object.put("lead_passenger", "1");
                        object.put("Title", String.valueOf(childList.get(i).getId()));
                        object.put("FirstName", childList.get(i).getFirstName());
                        object.put("LastName", childList.get(i).getLastName());
                        object.put("DateOfBirth", childList.get(i).getDateOfBirth());

                        if (Global.is_domestic_flag.equals("0"))
                        {
                            object.put("PassportIssueCountry", childList.get(i).getPassportCountry());
                            object.put("PassportNumber", childList.get(i).getPassportNumber());
                            object.put("PassportExpiry", childList.get(i).getPassportExpiry());
                        }
                        else
                        {
                            object.put("PassportIssueCountry", "91");
                            object.put("PassportNumber", "1271623115");
                            object.put("PassportExpiry", "24-01-2025");
                        }
                        object.put("Gender", String.valueOf(childList.get(i).getGenderType()));
                        passArray.put(object);
                    }
                }
                for (int i = 0; i < infantList.size(); i++) {
                    if (infantList.size() > 0 && infantList.get(i).getIsSelected() != 1)
                    {
                        JSONObject object = new JSONObject();
                        object.put("passenger_type", "Infant");
                        object.put("lead_passenger", "1");
                        object.put("Title", String.valueOf(infantList.get(i).getId()));
                        object.put("FirstName", infantList.get(i).getFirstName());
                        object.put("LastName", infantList.get(i).getLastName());
                        object.put("DateOfBirth", infantList.get(i).getDateOfBirth());

                        if (Global.is_domestic_flag.equals("0")) {

                            object.put("PassportIssueCountry", infantList.get(i).getPassportCountry());
                            object.put("PassportNumber", infantList.get(i).getPassportNumber());
                            object.put("PassportExpiry", infantList.get(i).getPassportExpiry());
                        } else {
                            object.put("PassportIssueCountry", "91");
                            object.put("PassportNumber", "1271623115");
                            object.put("PassportExpiry", "24-01-2025");
                        }
                        object.put("Gender", String.valueOf(infantList.get(i).getGenderType()));
                        passArray.put(object);
                    }
                }
                flightBook.put("Passengers", passArray);
                tokenObj.put("flight_token_table_id", flight_token_table_id);
                params.put("flight_book", flightBook.toString());
                params.put("token", tokenObj.toString());
                params.put("search_ssr_hash", search_hash_ssr);
                if (applicationPreference.getData("login_flag").equals("true"))
                {
                    params.put("wallet_bal",walletSelectType);
                } else {
                    params.put("wallet_bal", "off");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (passArray.length() != adultCount + childCount + infantCount)
            {
                commonUtils.toastShort("Please add all Passangers properly", getActivity());
            } else {
                if (cb_extraServices.isChecked())
                {
                    params.put("booking_step", "additional_ssr");
                    bundle1 = new Bundle();
                    bundle1.putString("postData", params.toString());
                    Log.e("seat params",params.toString());
                    intentAndFragmentService.fragmentDisplay(getActivity(), R.id.support_frame,
                            new FlightSeatFragment(fareUpdateResponse, flightBook.toString(),discountValue), bundle1, false);
                }
                else
                    {
                    params.put("booking_step","book");
                    Log.e("seat no booking params",params.toString());

                    intentAndFragmentService.fragmentDisplay(getActivity(), R.id.support_frame,
                            new TicketPreviewFragment(fareUpdateResponse,
                                    flightBook.toString(),
                                    params,discountValue, 1), null, true);
                }

            }

        }

    }

    public void createPreBookParamsHotel() {
        RequestParams params = new RequestParams();
        JSONArray passArray = new JSONArray();
        JSONObject hotelBook = new JSONObject();
        if (emailPrime.getText().toString().trim().equals("")) {
            commonUtils.toastShort("Please enter email id", getActivity());
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailPrime.getText().toString().trim()).matches()) {
            commonUtils.toastShort("enter valid email", getActivity());
        } else if (mobilePrime.getText().toString().trim().equals("")) {
            commonUtils.toastShort("Please enter mobile number", getActivity());
        } else if (mobilePrime.getText().toString().trim().length() != 10) {
            commonUtils.toastShort("enter valid mobile number", getActivity());
        } else {
            try {
                hotelBook.put("ProvabAuthKey", hotelTokenKey);
                hotelBook.put("Email", emailPrime.getText().toString());
                hotelBook.put("ContactNo", mobilePrime.getText().toString());
                hotelBook.put("AddressLine1", "E-city");
                hotelBook.put("City", "banglore");
                hotelBook.put("PinCode", "4456666");
                hotelBook.put("CountryCode", Global.countryList.get(spinn_countrycode.getSelectedItemPosition()).getIso());
                hotelBook.put("CountryName", Global.countryList.get(spinn_countrycode.getSelectedItemPosition()).getName());
                hotelBook.put("search_id", hotel_search_id);
                hotelBook.put("search_id", hotel_search_id);
                hotelBook.put("convenience_fee",String.format("%.2f", (Double.parseDouble(hotel_convi_fee) / Double.parseDouble(Global.currencyValue))));
                hotelBook.put("final_fare", Double.parseDouble(String.format("%.2f", ((Double.parseDouble(hotel_total_price)-(Double.parseDouble(discountValue) )) / Double.parseDouble(Global.currencyValue)))) + "");
                if (applicationPreference.getData("login_flag").equals("true"))
                {
                    hotelBook.put("customer_id", applicationPreference.getData("user_id"));
                } else {
                    hotelBook.put("customer_id", "");
                }
                hotelBook.put("payment_method", "PNHB1");
                hotelBook.put("promo_code", st_promo_code);
                hotelBook.put("promo_code_discount_val", discountValue);

                for (int i = 0; i < adultList.size(); i++) {
                    if (adultList.size() > 0 && adultList.get(i).getIsSelected() != 1) {
                        JSONObject object = new JSONObject();

                        object.put("Title", String.valueOf(adultList.get(i).getId()));
                        object.put("FirstName", adultList.get(i).getFirstName());
                        object.put("LastName", adultList.get(i).getLastName());
                        object.put("PassportIssueCountry", "");
                        object.put("PassportNumber", "");
                        object.put("PassportExpiry", "");
                        object.put("DateOfBirth", "");
                        object.put("Gender", String.valueOf(adultList.get(i).getGenderType()));
                        object.put("Pax_Type", "1");
                        passArray.put(object);
                    }
                }

                for (int i = 0; i < childList.size(); i++) {
                    if (childList.size() > 0 && childList.get(i).getIsSelected() != 1) {
                        JSONObject object = new JSONObject();
                        object.put("Title", String.valueOf(childList.get(i).getId()));
                        object.put("FirstName", childList.get(i).getFirstName());
                        object.put("LastName", childList.get(i).getLastName());
                        object.put("PassportIssueCountry", "");
                        object.put("PassportNumber", "");
                        object.put("PassportExpiry", "");
                        object.put("DateOfBirth","");
                        object.put("Gender", String.valueOf(childList.get(i).getGenderType()));
                        object.put("Pax_Type", "1");
                        passArray.put(object);
                    }
                }
                for (int i = 0; i < infantList.size(); i++) {
                    if (infantList.size() > 0 && infantList.get(i).getIsSelected() != 1) {
                        JSONObject object = new JSONObject();
                        object.put("Title", String.valueOf(infantList.get(i).getId()));
                        object.put("FirstName", infantList.get(i).getFirstName());
                        object.put("LastName", infantList.get(i).getLastName());
                        object.put("PassportIssueCountry", "");
                        object.put("PassportNumber", "");
                        object.put("PassportExpiry", "");
                        object.put("DateOfBirth", "");
                        object.put("Gender", String.valueOf(infantList.get(i).getGenderType()));
                        object.put("Pax_Type", "1");
                        passArray.put(object);
                    }
                }
                hotelBook.put("Passengers", passArray);

                params.put("hotel_params", hotelBook.toString());
                params.put("Token", hotelToken);
                params.put("Token_key", hotelTokenKey);
                if (applicationPreference.getData("login_flag").equals("true"))
                {
                    params.put("wallet_bal",walletSelectType);
                } else {
                    params.put("wallet_bal", "off");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (passArray.length() != adultCount + childCount + infantCount) {
            commonUtils.toastShort("Please add all Passangers properly", getActivity());
        } else {
            intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                    new TicketPreviewFragment(hotelResponse,
                            hotelBook.toString(),
                            params,discountValue, 2), null, true);
        }
    }

    public void createPreBookParamsBus() {

        if (mobilePrime.getText().toString() != null && !mobilePrime.getText().toString().isEmpty()
                && emailPrime.getText().toString() != null && !emailPrime.getText().toString().isEmpty()) {

            if (mobilePrime.getText().toString().length() == 10) {
                if (emailPrime.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

                    List<String> gender = new ArrayList<String>();
                    List<String> paxTitle = new ArrayList<String>();
                    List<String> contactName = new ArrayList<String>();
                    List<String> age = new ArrayList<String>();
                    ConfirmSeatMain confirmSeatMains = new ConfirmSeatMain();

                    gender.clear();
                    paxTitle.clear();
                    contactName.clear();
                    age.clear();

                    for (int i = 0; i < seatCountValue; i++) {
                        if (busTravellerInfos.get(i).getAgeValue() != null &&
                                busTravellerInfos.get(i).getContactName() != null &&
                                busTravellerInfos.get(i).getTitleValue() != null) {

                            String genderType = null;
                            if (busTravellerInfos.get(i).getTitleValue().equals("Mr")) {
                                // genderType = "1";
                                genderType = "Male";
                            } else if (busTravellerInfos.get(i).getTitleValue().equals("Ms")) {
                                // genderType = "2";
                                genderType = "Female";
                            } else if (busTravellerInfos.get(i).getTitleValue().equals("Miss")) {
                                // genderType = "2";
                                genderType = "Female";
                            } else if (busTravellerInfos.get(i).getTitleValue().equals("Master")) {
                                // genderType = "1";
                                genderType = "Male";
                            } else if (busTravellerInfos.get(i).getTitleValue().equals("Mrs")) {
                                //genderType = "1";
                                genderType = "Female";
                            }

                            gender.add(genderType);
                            //paxTitle.add("1");
                            paxTitle.add("" + busTravellerInfos.get(i).getTitleValue());
                            contactName.add(busTravellerInfos.get(i).getContactName());
                            if (Integer.parseInt(busTravellerInfos.get(i).getAgeValue()) > 10) {
                                age.add(busTravellerInfos.get(i).getAgeValue());
                            } else {
                                Toast.makeText(getActivity(), "Please provide valid age", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (i == (seatCountValue - 1)) {

                                confirmSeatMains = new ConfirmSeatMain(bus_searchId, gender, paxTitle, contactName,
                                        age, bus_token, bus_ResultToken, "book_bus", "PTBSID0000000003",
                                        mobilePrime.getText().toString(), "", emailPrime.getText().toString(),
                                        "on", "PNHB1");


                                bookingOutPut = gson.toJson(confirmSeatMains);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(bookingOutPut);
                                    jsonObject.put("payment_type", "EBS");
                                    if (applicationPreference.getData("login_flag").equals("true"))
                                    {
                                        jsonObject.put("customer_id", applicationPreference.getData("user_id"));
                                    } else {
                                        jsonObject.put("customer_id", "");
                                    }                                    jsonObject.put("promo_code", st_promo_code);
                                    jsonObject.put("promo_code_discount_val", discountValue);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String data = jsonObject.toString();
                                RequestParams params = new RequestParams();
                                params.put("confirm_book", data);
                                if (applicationPreference.getData("login_flag").equals("true"))
                                {
                                    params.put("wallet_bal",walletSelectType);
                                } else {
                                    params.put("wallet_bal", "off");
                                }
                                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                                        new TicketPreviewFragment(bus_token, params, seatCount, bus_seatValue, bus_arrival_date,
                                                emailPrime.getText().toString().trim(),
                                                mobilePrime.getText().toString().trim(),
                                                contactName,
                                                3), null, true);
                            }

                        } else {
                            Toast.makeText(getActivity(), "Please enter all traveller info to proceed", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Please Enter Valid Email-id", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Please Enter Valid Mobile number", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "Please Enter Booking Mobile number and Email-id", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("ValidFragment")
    public TravellerFragment(int adultCount, int childCount,
                             int infantCount, String totalPrice,
                             String fareUpdateResponse, int i) {
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.infantCount = infantCount;
        this.totalPrice = totalPrice;
        this.fareUpdateResponse = fareUpdateResponse;
        this.frameType = i;
        //frame type 1 is for flight
    }

    String hotelResponse = null;

    @SuppressLint("ValidFragment")
    public TravellerFragment(String hotelResponse, int adultCount, int childCount,
                             int infantCount, String totalPrice, int i) {
        this.hotelResponse = hotelResponse;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.infantCount = infantCount;
        this.totalPrice = totalPrice;
        this.frameType = i;
        //frame type 2 is for hotel
    }

    @SuppressLint("ValidFragment")
    public TravellerFragment(int seatCount, int adultCount, int childCount, int infantCount,
                             String totalPrice, int i) {
        this.seatCount = seatCount;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.infantCount = infantCount;
        this.totalPrice = totalPrice;
        this.frameType = i;
        //frame type 3 is for bus
    }

    public TravellerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressLoader = new ProgressLoader();
        dbAdapter = new DBAdapter(getActivity());
        webServiceController = new WebServiceController(getActivity(), TravellerFragment.this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.traveller_fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        loadCountries();
        if (!totalPrice.equals("")) {
            try {
                totalPriceText.setText(Global.currencySymbol + " " + Math.round(Float.parseFloat(totalPrice)));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        commonUtils.linearLayout(adultListView, getActivity());
        commonUtils.linearLayout(childListView, getActivity());
        commonUtils.linearLayout(infantsListView, getActivity());

        emailPrime.setText(applicationPreference.getData("user_email"));
        mobilePrime.setText(applicationPreference.getData("user_mobile"));
        if(applicationPreference.getData("login_flag").equalsIgnoreCase("true"))
        {
            cb_walletPaymentType.setVisibility(View.GONE);
        }
        else
        { cb_walletPaymentType.setVisibility(View.GONE);

        }


        updateAdult(addedAdult);
        updateChild(addedChild);
        updateInfant(addedInfant);

        if (frameType == 2) {
            infantCountText.setVisibility(View.GONE);
        }

        if (adultCount != 0) {
            adultLayout.setVisibility(View.VISIBLE);
            getTravellerData(1);
        }

        if (childCount != 0) {
            childLayout.setVisibility(View.VISIBLE);
            getTravellerData(2);
        }

        if (infantCount != 0) {
            infantLayout.setVisibility(View.VISIBLE);
            getTravellerData(3);
        }

        if (frameType == 1) {
            bookingDetail.setVisibility(View.VISIBLE);
            hotel_booking_detail.setVisibility(View.GONE);
            bus_booking_detail.setVisibility(View.GONE);
        } else if (frameType == 2) {
            hotel_booking_detail.setVisibility(View.GONE);
            bookingDetail.setVisibility(View.GONE);
            bus_booking_detail.setVisibility(View.GONE);
        } else if (frameType == 3) {
            top_layout.setVisibility(View.GONE);
            hotel_booking_detail.setVisibility(View.GONE);
            bookingDetail.setVisibility(View.GONE);
            bus_booking_detail.setVisibility(View.VISIBLE);
        }
        loadDetails();
    }

    public void loadDetails() {
        if (frameType == 1)
        {
            try {
                JSONObject mainjsonObj = new JSONObject(fareUpdateResponse);
                JSONObject dataObj = mainjsonObj.getJSONObject("data");
                booking_token = dataObj.getString("token");
                bookingTokenKey = dataObj.getString("token_key");
                bookingSearchId = dataObj.getString("search_id");
                flight_token_table_id = dataObj.getString("flight_token_table_id");
                search_hash_ssr = dataObj.getString("search_hash_ssr");
                convienceFee = String.valueOf(dataObj.get("convenience_fees"));
                JSONObject preBookObj = dataObj.getJSONObject("pre_booking_summery");
                JSONObject fareDetailObj = preBookObj.getJSONObject("FareDetails");
                JSONObject priceDetailObj = fareDetailObj.getJSONObject("b2c_PriceDetails");
                basefare = priceDetailObj.getString("BaseFare");
                tax = priceDetailObj.getString("TotalTax");
                GST = priceDetailObj.getString("GST");
                totPrice = priceDetailObj.getString("TotalFare");
                curr_symbol = priceDetailObj.getString("CurrencySymbol");
                JSONArray segmentArr = preBookObj.getJSONArray("SegmentSummary");
                JSONObject baggageObj = segmentArr.getJSONObject(0).getJSONObject("Baggage_Info");
                final String checkin_bag = baggageObj.getString("Baggage");
                final String hand_bag = baggageObj.getString("CabinBaggage");


                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run() {
                        if (hand_bag.equals(null)) {
                            tv_handbag.setText("-");
                        } else {
                            tv_handbag.setText(hand_bag);
                        }
                        if (checkin_bag.equals(null))
                        {
                            tv_chekinbag.setText("-");
                        } else {
                            tv_chekinbag.setText(checkin_bag);
                        }

                        tv_basefare.setText(Global.currencySymbol + " " + String.format("%.2f", (Double.parseDouble(basefare) / Double.parseDouble(Global.currencyValue))));
                        tv_taxes.setText(Global.currencySymbol + " " + String.format("%.2f", (Double.parseDouble(tax) / Double.parseDouble(Global.currencyValue))));
                        tv_confee.setText(Global.currencySymbol + " " + String.format("%.2f", (Double.parseDouble(convienceFee) / Double.parseDouble(Global.currencyValue))));
                        tv_gst.setText(Global.currencySymbol + " " + String.format("%.2f", (Double.parseDouble(GST) / Double.parseDouble(Global.currencyValue))));

                        tv_discount.setText(Global.currencySymbol + " " +discountValue);

                        tv_total.setText(Global.currencySymbol + " " + String.format("%.2f", ((Double.parseDouble(totPrice) + Double.parseDouble(convienceFee)) / Double.parseDouble(Global.currencyValue))));
                        totalPriceText.setText(Global.currencySymbol + " " +
                                String.format("%.2f", ((Double.parseDouble(totPrice) + Double.parseDouble(convienceFee)) / Double.parseDouble(Global.currencyValue))));
                        totalAmtAfterDiscount=String.format("%.2f", ((Double.parseDouble(totPrice) + Double.parseDouble(convienceFee)) / Double.parseDouble(Global.currencyValue)));
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (frameType == 2)
        {
            try {
                JSONObject mainhoteljsonObj = new JSONObject(hotelResponse);
                JSONObject dataObj = mainhoteljsonObj.getJSONObject("data");
                JSONObject prebookingObj = dataObj.getJSONObject("pre_booking_params");
                currencySymbol = prebookingObj.getString("default_currency");
                  hotel_total_price = dataObj.getString("total_price");
                hotel_tax = dataObj.getString("tax_service_sum");
                hotel_convi_fee = dataObj.getString("convenience_fees");
                hotel_search_id = dataObj.getString("search_id");
                hotelToken = dataObj.getString("token");
                hotelTokenKey = dataObj.getString("token_key");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                          totalPriceText.setText(Global.currencySymbol + " " + (Double.parseDouble(String.format("%.2f", (Double.parseDouble(hotel_total_price) / Double.parseDouble(Global.currencyValue))))));

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (frameType == 3)
        {
            bundle = getArguments();
            bus_arrival_date = bundle.getString("ArrivalDate");

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    commonUtils.linearLayout(travellerInfoLayout, getActivity());
                    totalPriceText.setText(Global.currencySymbol + " " + (Double.parseDouble(totalPrice)));
                    if (bundle != null) {
                        tv_seat_num.setText("Seat Number : " + bundle.getString("seats"));
                        bus_searchId = bundle.getString("search_id");
                        bus_token = bundle.getString("token");
                        bus_ResultToken = bundle.getString("ResultToken");
                        bus_tokenValue = bundle.getString("token_key");
                        bus_scheduler_id = bundle.getString("scheduler_id");
                        bus_journey_date = bundle.getString("journey_date");
                        bus_pickup_id = bundle.getString("pickup_id");
                        bus_seatValue = bundle.getString("seats");
                        count = bus_seatValue.split(",");
                        seatCountValue = seatCount;
                    }
                    for (int i = 0; i < seatCount; i++) {
                        busTravellerInfos.add(new BusTravellerInfo("Mr", null, null));
                    }

                    busBookingTravellerInfo = new BusBookingTravellerInfo(getActivity(), busTravellerInfos);
                    travellerInfoLayout.setAdapter(busBookingTravellerInfo);
                }

            });

        }
    }

    private void getTravellerData(int i)
    {
        List<TravellerModel> tempList = new ArrayList<TravellerModel>();
        switch (i) {

            case 1:
                dbAdapter.open();
                tempList.clear();
                tempList.addAll(dbAdapter.getTravellerList(1));
                dbAdapter.close();
                addedAdult = 0;

                if (adultList.size() != 0) {
                    int j = 0;
                    while (j < tempList.size()) {
                        int k = 0;
                        while (k < adultList.size()) {
                            if (adultList.get(k).getId() ==
                                    tempList.get(j).getId()) {
                                if (adultList.get(k).getIsSelected() == 2) {
                                    tempList.get(j).setIsSelected(2);
                                    addedAdult++;
                                } else {
                                    tempList.get(j).setIsSelected(1);
                                }
                                break;
                            }
                            k++;
                        }
                        j++;
                    }
                    adultList.clear();
                    adultList.addAll(tempList);
                    updateAdult(addedAdult);
                } else {
                    adultList.clear();
                    adultList.addAll(tempList);
                }

                notifyAdultAdapter();
                break;


            case 2:
                try {
                    dbAdapter.open();
                    tempList.clear();
                    tempList.addAll(dbAdapter.getTravellerList(2));
                    dbAdapter.close();
                    addedChild = 0;
                } catch (Exception e) {

                }


                if (childList.size() != 0) {
                    int j = 0;
                    while (j < tempList.size()) {
                        int k = 0;
                        while (k < childList.size()) {
                            if (childList.get(k).getId() ==
                                    tempList.get(j).getId()) {
                                if (childList.get(k).getIsSelected() == 2) {
                                    tempList.get(j).setIsSelected(2);
                                    addedChild++;
                                } else {
                                    tempList.get(j).setIsSelected(1);
                                }
                                break;
                            }
                            k++;
                        }
                        j++;
                    }
                    childList.clear();
                    childList.addAll(tempList);
                    updateChild(addedChild);
                } else {
                    childList.clear();
                    childList.addAll(tempList);
                }

                notifyChildAdapter();
                break;

            case 3:
                dbAdapter.open();
                tempList.clear();
                tempList = dbAdapter.getTravellerList(3);
                dbAdapter.close();
                addedInfant = 0;

                if (infantList.size() != 0) {
                    int j = 0;
                    while (j < tempList.size()) {
                        int k = 0;
                        while (k < infantList.size()) {
                            if (infantList.get(k).getId() ==
                                    tempList.get(j).getId()) {
                                if (infantList.get(k).getIsSelected() == 2) {
                                    tempList.get(j).setIsSelected(2);
                                    addedInfant++;
                                } else {
                                    tempList.get(j).setIsSelected(1);
                                }
                                break;
                            }
                            k++;
                        }
                        j++;
                    }
                    infantList.clear();
                    infantList.addAll(tempList);
                    updateInfant(addedInfant);
                } else {
                    infantList.clear();
                    infantList.addAll(tempList);
                }

                notifyInfantAdapter();
                break;
        }
    }

    public void updateAdult(Integer currentCount)
    {
        adultCountText.setText("AD\n" + currentCount + "/" + adultCount);
    }

    public void updateChild(Integer currentCount)
    {
        childCountText.setText("CH\n" + currentCount + "/" + childCount);
    }

    public void updateInfant(Integer currentCount)
    {
        infantCountText.setText("IN\n" + currentCount + "/" + infantCount);
    }

    private void notifyAdultAdapter()
    {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adultAdapter = new AdultAdapter(
                        getActivity(), TravellerFragment.this, adultList);
                adultListView.setAdapter(adultAdapter);
            }
        });
    }

    private void notifyChildAdapter() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                childAdapter = new ChildAdapter(
                        getActivity(), TravellerFragment.this, childList);
                childListView.setAdapter(childAdapter);
            }
        });
    }

    private void notifyInfantAdapter() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                infantAdapter = new InfantAdapter(
                        getActivity(), TravellerFragment.this, infantList);
                infantsListView.setAdapter(infantAdapter);
            }
        });
    }

    @Override
    public void notifyAddTraveller(Integer actionValue, boolean delete) {
        getActivity().onBackPressed();
        switch (actionValue) {
            case 1:
                getTravellerData(1);
                break;
            case 2:
                getTravellerData(2);
                break;
            case 3:
                getTravellerData(3);
                break;
        }
    }

    public void editAdult(Integer id, int editPos) {
        callAddView(1, id);
    }

    public void editChild(Integer id, int editPos) {
        callAddView(2, id);
    }

    public void editInfant(Integer id, int editPos) {
        callAddView(3, id);
    }

    public void adultSelection(int i, Integer isSelected) {
        switch (isSelected) {
            case 1:
                if (adultCount > addedAdult) {
                    addedAdult = addedAdult + 1;
                    adultList.get(i).setIsSelected(2);
                    updateAdult(addedAdult);
                } else {
                    commonUtils.toastShort("You have selected the max selected adult count", getActivity());
                }
                break;
            case 2:
                addedAdult = addedAdult - 1;
                adultList.get(i).setIsSelected(1);
                updateAdult(addedAdult);
                break;
        }
        notifyAdultAdapter();
    }

    public void childSelection(int i, Integer isSelected) {
        switch (isSelected) {
            case 1:
                if (childCount > addedChild) {
                    addedChild = addedChild + 1;
                    childList.get(i).setIsSelected(2);
                    updateChild(addedChild);
                } else {
                    commonUtils.toastShort("You have selected the max selected child count", getActivity());
                }
                break;
            case 2:
                addedChild = addedChild - 1;
                childList.get(i).setIsSelected(1);
                updateChild(addedChild);
                break;
        }
        notifyChildAdapter();
    }

    public void infantSelection(int i, Integer isSelected) {
        switch (isSelected) {
            case 1:
                if (infantCount > addedInfant) {
                    addedInfant = addedInfant + 1;
                    infantList.get(i).setIsSelected(2);
                    updateInfant(addedInfant);
                } else {
                    commonUtils.toastShort("You have selected the max selected infant count", getActivity());
                }
                break;
            case 2:
                addedInfant = addedInfant - 1;
                infantList.get(i).setIsSelected(1);
                updateInfant(addedInfant);
                break;
        }
        notifyInfantAdapter();
    }


    @Override
    public void getResponse(String response, int flag) {

        progressLoader.DismissProgress();

        switch (flag) {
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("country_list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        countryList.add(new CountryInfo(jsonArray.getJSONObject(i).getString("origin"),
                                jsonArray.getJSONObject(i).getString("name"),
                                jsonArray.getJSONObject(i).getString("country_code"),
                                jsonArray.getJSONObject(i).getString("country_code"),
                                jsonArray.getJSONObject(i).getString("iso_country_code")));
                        Global.arrCountry.add(jsonArray.getJSONObject(i).getString("name") + " ("
                                + jsonArray.getJSONObject(i).getString("country_code") + ")");
                    }
                    Global.countryList.addAll(countryList);
                    spinn_countrycode.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_item, R.id.spinn_text, Global.arrCountry));
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
                promocodeList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject promoObj = jsonArray.getJSONObject(i);



                            if (frameType == 1) {
                                if (jsonArray.getJSONObject(i).getString("module").equalsIgnoreCase("flight")) {
                                    promocodeList.add(new PromoCodeInfo(promoObj.getString("module"),
                                            promoObj.getString("promo_code"),
                                            promoObj.getString("description"),
                                            promoObj.getString("expiry_date"),
                                            promoObj.getString("status"),
                                            "http://" +
                                                    promoObj.getString("promo_code_image")


                                    ));
                                } else {

                                }
                            } else if (frameType == 2) {
                                if (jsonArray.getJSONObject(i).getString("module").equalsIgnoreCase("hotel")) {
                                    promocodeList.add(new PromoCodeInfo(promoObj.getString("module"),
                                            promoObj.getString("promo_code"),
                                            promoObj.getString("description"),
                                            promoObj.getString("expiry_date"),
                                            promoObj.getString("status"),
                                            "http://" +
                                                    promoObj.getString("promo_code_image")
                                    ));
                                } else {

                                }
                            } else {
                                if (jsonArray.getJSONObject(i).getString("module").equalsIgnoreCase("bus")) {
                                    promocodeList.add(new PromoCodeInfo(promoObj.getString("module"),
                                            promoObj.getString("promo_code"),
                                            promoObj.getString("description"),
                                            promoObj.getString("expiry_date"),
                                            promoObj.getString("status"),
                                            "http://" +
                                                    promoObj.getString("promo_code_image")
                                    ));
                                } else {

                                }

                            }

                        }
                        if (promocodeList.size() > 0) {

                            openPromoCodeDialog();
                        } else {
                            commonUtils.toastShort("No Promocode found", getActivity());
                        }

                    } else {
                        commonUtils.toastShort("No Promocode found", getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                Log.e("response",response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getInt("status")==1)
                    {

                        final String discountVal=jsonObject.getString("discount_value");
                        final String totalAmountVal=jsonObject.getString("total_amount_val");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run()
                            {
                                discountValue=discountVal;
                                if(frameType==1){
                                    tv_discount.setText(Global.currencySymbol + " " +discountVal);
                                    tv_total.setText(Global.currencySymbol + " " + totalAmountVal);
                                    totalPriceText.setText(Global.currencySymbol + " " +totalAmountVal);
                                }else if(frameType==2){
                                    totalPriceText.setText(Global.currencySymbol + " " +totalAmountVal);

                                }
                                totalAmtAfterDiscount=totalAmountVal;
                                commonUtils.toastShort("Promo code applied successfully!",getActivity());

                            }
                        });
                    }
                    else
                    {
                        commonUtils.toastShort(jsonObject.getString("message"),getActivity());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 4:

                Log.e("response",response);
                try {
                    JSONObject object = new JSONObject(response);
                    if(object.getInt("status")==1) {
                        cb_walletPaymentType.setText("Use Wallet balance ( " + Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(object.getString("wallet_balance"))/Double.parseDouble(Global.currencyValue)))+ " )");

                    }
                    else
                    {
                        cb_walletPaymentType.setText("Use Wallet balance ( " + Global.currencySymbol + " 0 )");

                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
        }
                }



    public void loadCountries(){
        if (Global.arrCountry.size()==0){
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

    public void openPromoCodeDialog(){
        promocode_dialog = new Dialog(getActivity());
        promocode_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        promocode_dialog.setCancelable(true);
        promocode_dialog.setContentView(R.layout.promocode_dialog);
        RecyclerView rv_promolist=promocode_dialog.findViewById(R.id.rv_promolist);
        commonUtils.linearLayout(rv_promolist,getActivity());
        PromocodeAdapter promocodeAdapter;
        promocodeAdapter = new PromocodeAdapter(TravellerFragment.this,getActivity(), promocodeList);
        rv_promolist.setAdapter(promocodeAdapter);

        TextView tv_close=promocode_dialog.findViewById(R.id.tv_close);
        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promocode_dialog.dismiss();
            }
        });
        promocode_dialog.show();
    }

    public void notifyPromocode(String code)
    {
        promocode_dialog.dismiss();
        st_promo_code=code;
        tv_select_promo.setText(code);
        iv_removePromoCode.setVisibility(View.VISIBLE);

    }


    @OnClick(R.id.iv_removePromoCode)
    public void removePromo()
    {
        if(st_promo_code.equalsIgnoreCase(""))
        {
            commonUtils.toastShort("Select Promocode",getActivity());
        }
        else
        {
            discountValue="0.00";
            st_promo_code="";
            tv_select_promo.setText("");
            if(frameType==1)
            {
                tv_discount.setText(Global.currencySymbol + " " +discountValue);

                tv_total.setText(Global.currencySymbol + " " + String.format("%.2f", ((Double.parseDouble(totPrice) + Double.parseDouble(convienceFee)) / Double.parseDouble(Global.currencyValue))));
                totalPriceText.setText(Global.currencySymbol + " " +
                        String.format("%.2f", ((Double.parseDouble(totPrice) + Double.parseDouble(convienceFee)) / Double.parseDouble(Global.currencyValue))));
                totalAmtAfterDiscount=String.format("%.2f", ((Double.parseDouble(totPrice) + Double.parseDouble(convienceFee)) / Double.parseDouble(Global.currencyValue)));

            }else if(frameType==2)
            {
                totalPriceText.setText(Global.currencySymbol + " " + (Double.parseDouble(String.format("%.2f", (Double.parseDouble(hotel_total_price) / Double.parseDouble(Global.currencyValue))))));

            }

        }
        commonUtils.toastShort("Promo code removed",getActivity());


    }

    public void callWalletAPI()
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("user_id",applicationPreference.getData("user_id"));
        webServiceController.postRequest(apiConstants.WALLET_DETAILS,requestParams,4);
    }








}