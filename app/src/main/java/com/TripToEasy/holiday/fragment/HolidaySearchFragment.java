package com.TripToEasy.holiday.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.holiday.adapter.PackageAdapter;
import com.TripToEasy.holiday.adapter.PackageDurationAdapter;
import com.TripToEasy.holiday.interfaces.HolidayNotify;
import com.TripToEasy.interfaces.DatePickerNotify;
import com.TripToEasy.main.adapter.landingAdapter.InternationalPackageLanding;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.PromoCodeInfo;
import com.TripToEasy.model.parsingModel.holiday.PackageDuration;
import com.TripToEasy.model.parsingModel.holiday.PackageListModel;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.OnClick;

public class HolidaySearchFragment extends BaseFragment
        implements WebInterface, HolidayNotify,DatePickerNotify
{

    @BindView(R.id.package_type)
    TextView packageNameText;

    @BindView(R.id.country_name)
    TextView countryNameText;

    @BindView(R.id.duration)
    TextView durationText;

    @BindView(R.id.search_city_name)
    TextView countrySearchName;

    @BindView(R.id.doj)
    TextView dateOfJourney;

    /**
     * navigate to HolidayCitySearchFragment
     * */
    @OnClick(R.id.country_name)
    void countrySearch(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new HolidayCitySearch(HolidaySearchFragment.this), null, true);
    }



    InternationalPackageLanding internationalPackageLanding;

    @BindView(R.id.package_list)
    RecyclerView packageListView;
    List<PromoCodeInfo> packageModels = new ArrayList<PromoCodeInfo>();

    public HolidaySearchFragment()
    {

    }
    /**
     * Lode data in popup
     * */
    PackageAdapter packageAdapter;
    String packageTypeId=null;
    Dialog holidaytypedialog;
    @OnClick(R.id.package_type)
    void selectPackage(){
       /* alert = new AlertDialog.Builder(getActivity()).create();
        alert.setTitle("Package Type");
        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, getActivity());

         packageAdapter = new PackageAdapter(
                getActivity(), HolidaySearchFragment.this, packageListModels);
        recyclerView.setAdapter(packageAdapter);

        alert.setView(recyclerView);
        alert.show();*/
        holidaytypedialog = new Dialog(getActivity());
        holidaytypedialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        holidaytypedialog.setCancelable(true);
        holidaytypedialog.setContentView(R.layout.holiday_type_dialog);
        holidaytypedialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        TextView tv_all=holidaytypedialog.findViewById(R.id.tv_all);
        TextView tv_domestic=holidaytypedialog.findViewById(R.id.tv_domestic);
        TextView tv_international=holidaytypedialog.findViewById(R.id.tv_international);

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageNameText.setText("All");
                packageTypeId="";
                holidaytypedialog.dismiss();
            }
        });
        tv_domestic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageNameText.setText("Domestic");
                packageTypeId="domestic";
                holidaytypedialog.dismiss();
            }
        });

        tv_international.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                packageNameText.setText("International");
                packageTypeId="international";
                holidaytypedialog.dismiss();
            }
        });

        holidaytypedialog.show();
    }

    /**
     * Lode data in popup
     * */
    PackageDurationAdapter packageDurationAdapter;
    @OnClick(R.id.duration)
    void selectDuration(){
        alert = new AlertDialog.Builder(getActivity()).create();
        alert.setTitle("Package Duration");

        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, getActivity());

         packageDurationAdapter =
                new PackageDurationAdapter(getActivity(),
                        HolidaySearchFragment.this, packageDurations);
        recyclerView.setAdapter(packageDurationAdapter);

        alert.setView(recyclerView);
        alert.show();
    }

    @OnClick(R.id.doj)
    void setDoj(){
        commonUtils.callDatePicker(this,HolidaySearchFragment.this,checkInDate,
                1);
    }

    /**
     * Search Package
     * */
    @OnClick(R.id.search_holiday)
    void searchPackage(){
       /* if(countryNameText.getText().toString().equals("")){
           commonUtils.toastShort("Please Select Location", getActivity());
        }else if(durationText.getText().toString().equals("")){
            commonUtils.toastShort("Please Select Package Duration", getActivity());
        }else {
            applicationPreference.setData(
                    applicationPreference.holidayCountryName,
                    countryNameText.getText().toString());
            applicationPreference.setData(applicationPreference.holidayCountryId,
                    countryId);

            applicationPreference.setData(
                    applicationPreference.holidayPackageName,
                    packageNameText.getText().toString());
            applicationPreference.setData(
                    applicationPreference.holidayPackageId,
                    packageId);

            applicationPreference.setData(
                    applicationPreference.holidayDuration,
                    tripDuration);

            SearchPackage searchPackage = new SearchPackage(
                    countryNameText.getText().toString(), durationText.getText().toString(), checkInDate);
            Gson gson = new Gson();
            RequestParams requestParams = new RequestParams();
            requestParams.put("package_search",gson.toJson(searchPackage));

            webServiceController.postRequest(
                    apiConstants.PACKAGE_SEARCH,
                    requestParams,
                    1);
        }*/
        if(packageNameText.getText().toString().equals("")){
            commonUtils.toastShort("Select Holiday Type", getActivity());
            return;
        }
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("holiday_type",packageTypeId);
            RequestParams requestParams = new RequestParams();
            requestParams.put("package_search",jsonObject.toString());

            webServiceController.postRequest(
                    apiConstants.PACKAGE_SEARCH,
                    requestParams,
                    1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    WebServiceController webServiceController;
    String countryId = null, packageId = null, tripDuration = null,
            packageResponse = null, durationRespone = null;
    List<PackageListModel> packageListModels = new ArrayList<PackageListModel>();
    List<PackageDuration> packageDurations = new ArrayList<PackageDuration>();
    AlertDialog alert;
    String checkInDate="",startDate;
    @Override
    protected int getLayoutResource() {
        return R.layout.holiday_search_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                HolidaySearchFragment.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        
        startDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)
                +"-"+calendar.get(Calendar.DAY_OF_MONTH);
        notifyDate(startDate, 1);

        commonUtils.horizontalLayout(packageListView, getActivity());
        RequestParams params = new RequestParams();
        webServiceController.postRequest(apiConstants.URL_PROMOCODE_LIST, params, 0);
        //callPackageDurationApi();
        /*if(applicationPreference.getData(applicationPreference.holidayCountryName).length() != 0){
            countryNameText.setText(applicationPreference.getData(applicationPreference.holidayCountryName));
            countrySearchName.setText(applicationPreference.getData(applicationPreference.holidayCountryName));
            countryId = applicationPreference.getData(applicationPreference.holidayCountryId);

            packageNameText.setText(applicationPreference.getData(applicationPreference.holidayPackageName));
            packageId = applicationPreference.getData(applicationPreference.holidayPackageId);

            durationText.setText(applicationPreference.getData(applicationPreference.holidayDuration));
            tripDuration = applicationPreference.getData(applicationPreference.holidayDuration);
        }*/
    }

    @Override
    public void getResponse(String response, int flag) {
        switch (flag){
            case  0:

                packageModels.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1)
                    {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject promoObj = jsonArray.getJSONObject(i);
                            packageModels.add(new PromoCodeInfo(promoObj.getString("module"),
                                    promoObj.getString("promo_code"),
                                    promoObj.getString("description"),
                                    promoObj.getString("expiry_date"),
                                    promoObj.getString("status"),
                                    "https://" +
                                            promoObj.getString("promo_code_image")));
                            Log.e("PromoImage", "https://" +
                                    promoObj.getString("promo_code_image"));
                        }
                        internationalPackageLanding = new
                                InternationalPackageLanding(getActivity(), packageModels);
                        packageListView.setAdapter(internationalPackageLanding);

                    }
                    }
                catch (Exception e)
                {
                    e.printStackTrace();

                }



                break;
            case 1:
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                        new HolidayListingFragment(
                                response, packageNameText.getText().toString(),
                                tripDuration), null, true);
                break;
            case 2:
                packageResponse = response;
                packageListModels.clear();
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    int i=0;
                    while (i<jsonArray.length()){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        packageListModels.add(new PackageListModel(
                                jsonObject.getString("package_types_name"),
                                jsonObject.getString("package_types_id"),
                                jsonObject.optString("Duration"),
                                jsonObject.optString("Country_id")));
                        i++;
                    }
                    //packageAdapter.notifyDataSetChanged();
                    try {
                        packageId=packageListModels.get(0).getPackageTypeId();
                        packageNameText.setText(packageListModels.get(0).getPackageName());
                    }catch (Exception e1){
                        packageNameText.setText("");
                    }
                    RequestParams requestParams = new RequestParams();
                    requestParams.put("package_type_id", packageListModels.get(0).getPackageTypeId());
                    webServiceController.postRequest(
                            apiConstants.PACKAGE_DURATION,
                            requestParams,
                            3);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case 3:
                durationRespone = response;
                packageDurations.clear();
                try{
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("days");
                    int i=0;
                    while (i<jsonArray.length()){
                        packageDurations.add(new PackageDuration(jsonArray.getString(i)));
                        /*JSONObject jsonObject = jsonArray.getJSONObject(i);
                        packageDurations.add(new PackageDuration(
                                jsonObject.getString("min_days")
                                        +"-"+
                                        jsonObject.getString("max_days")));*/
                        i++;
                    }
                    //packageDurationAdapter.notifyDataSetChanged();
                    try {
                        tripDuration=packageDurations.get(0).getPackageDuration();
                        durationText.setText(packageDurations.get(0).getPackageDuration());
                    }catch (Exception e1){
                        durationText.setText("");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            case 4:

                break;
        }
    }

    /**n
     * On getting country id call package List
     * */
    @Override
    public void countryId(String countryId, String countryName) {
        this.countryId = countryId;
        countryNameText.setText(countryName);
        countrySearchName.setText(countryName);

       /* RequestParams requestParams = new RequestParams();
        webServiceController.postRequest(
                apiConstants.PACKAGE_TYPE_LIST,
                requestParams,
                2);*/
    }

    public void updatePackageDetails(String packageName, String packageTypeId,
                                     String duration) {
        alert.dismiss();
        this.packageId = packageTypeId;
        packageNameText.setText(packageName);

        RequestParams requestParams = new RequestParams();
        requestParams.put("package_type_id", packageId);
        webServiceController.postRequest(
                apiConstants.PACKAGE_DURATION,
                requestParams,
                3);
    }

    public void updateDuration(String packageDuration) {
        alert.dismiss();
        durationText.setText(packageDuration);
        tripDuration = packageDuration;
    }

    @Override
    public void notifyDate(String dateValue, Integer datePickerValue) {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date endDate = dateFormat.parse(dateValue);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            checkInDate = dateValue;
            dateOfJourney.setText(endDate_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void callPackageDurationApi()
    {
        RequestParams requestParams = new RequestParams();
        webServiceController.postRequest(
                apiConstants.PACKAGE_DURATION,
                requestParams,
                3);
    }


}