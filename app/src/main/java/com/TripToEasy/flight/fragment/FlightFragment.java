package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.flight.adapter.MultiCityViewAdapter;
import com.TripToEasy.interfaces.AdvanceOptionNotify;
import com.TripToEasy.interfaces.CitySearch;
import com.TripToEasy.interfaces.DatePickerNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.requestModel.multiCity.MultiCityParser;
import com.TripToEasy.model.requestModel.multiCity.MultiCitySegment;
import com.TripToEasy.model.requestModel.oneWay.OneWayMain;
import com.TripToEasy.model.requestModel.oneWay.OneWaySegment;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

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

public class FlightFragment extends BaseFragment implements CitySearch,
        WebInterface, DatePickerNotify, AdvanceOptionNotify {

    @BindView(R.id.one_round_view)
    LinearLayout oneRoundView;

    @BindView(R.id.multi_city_view)
    LinearLayout multiCityView;

    @BindView(R.id.multi_city_place_list)
    RecyclerView multiCityList;

  /*  @BindView(R.id.from_station_code)
    TextView fromCityCode;
*/
    @BindView(R.id.from_station_name)
    TextView fromCityName;

  /*  @BindView(R.id.to_destination_code)
    TextView toCityCode;*/

    @BindView(R.id.to_destination_name)
    TextView toCityName;

    @BindView(R.id.one_way)
    TextView oneWayText;

    @BindView(R.id.round_trip)
    TextView roundTripText;

    @BindView(R.id.multi_city)
    TextView multiCityText;

    @BindView(R.id.return_date)
    LinearLayout returnDateView;

    @BindView(R.id.week_name)
    TextView weekName;

    @BindView(R.id.date_value)
    TextView dateValue;

    @BindView(R.id.to_week_name)
    TextView toWeekName;

    @BindView(R.id.to_date_value)
    TextView toDateValue;

    @BindView(R.id.tv_adult)
    TextView tv_adult;

    @BindView(R.id.tv_child)
    TextView tv_child;

    @BindView(R.id.tv_infant)
    TextView tv_infant;
    @OnClick(R.id.tv_adult)
    void setAdultCount(){
        addTraveller();
    }

    @OnClick(R.id.tv_child)
    void setChildCount(){
        addTraveller();
    }

    @OnClick(R.id.tv_infant)
    void setInfantCount(){
        addTraveller();
    }
    @BindView(R.id.iv_swap)
    ImageView iv_swap_city;

    @OnClick(R.id.iv_swap)
    void swapCity()
    {
     String temp="";
     String fromCity=fromCityName.getText().toString();
     String toCity=toCityName.getText().toString();

     temp=fromCityId;
     fromCityId=toCityId;
     toCityId=temp;
    // fromCityCode.setText(fromCityId);
   //  toCityCode.setText(toCityId);

     temp=fromCity;
     fromCity=toCity;
     toCity=temp;
     fromCityName.setText(fromCity);
     toCityName.setText(toCity);
    }

    @OnClick(R.id.from_station_name)
    void selcetFromCity(){
        callCitySearch(1);
    }

    @OnClick(R.id.to_destination_name)
    void selcetToCity(){
        callCitySearch(2);
    }

    @OnClick(R.id.one_way)
    void setOneWay(){
        multiCityListPosition = null;
        multiCitySegments.clear();
        selectTravelType(1);
    }

    @OnClick(R.id.round_trip)
    void setRoundTrip(){
        multiCityListPosition = null;
        multiCitySegments.clear();
        selectTravelType(2);
    }

    @OnClick(R.id.multi_city)
    void setMultiCity(){
        selectTravelType(3);
    }

    @OnClick(R.id.add_city_action)
    void addCityList(){
        boolean isValid = validateList();
        if(isValid){
            if(multiCitySegments.size() < 5){
                lodeEmptyData(1);
                multiCityViewAdapter.notifyDataSetChanged();
            }
            else
                {
                commonUtils.toastShort("You cannot add more than 5 stops.",getActivity());
            }
        }else {
            commonUtils.toastShort("Please enter all the data to proceed.",getActivity());
        }
    }


    public FlightFragment()
    {}



    @SuppressLint("NewApi")
    private void selectTravelType(int i)
    {
        switch (i){
            case 1:
                isRoundTrip = 0;
                oneWayText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));
                roundTripText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
                multiCityText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
                oneWayText.setTextColor(getActivity().getResources().getColor(R.color.white));
                roundTripText.setTextColor(getActivity().getResources().getColor(android.R.color.tab_indicator_text));
                multiCityText.setTextColor(getActivity().getResources().getColor(android.R.color.tab_indicator_text));
                returnDateView.setEnabled(false);
                returnDateView.setAlpha(0.5f);
                iv_swap_city.setVisibility(View.VISIBLE);
                fromCityName.setVisibility(View.VISIBLE);
                toCityName.setVisibility(View.VISIBLE);
                break;
            case 2:
                isRoundTrip = 1;
                oneWayText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
                roundTripText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));
                multiCityText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
                oneWayText.setTextColor(getActivity().getResources().getColor(android.R.color.tab_indicator_text));
                roundTripText.setTextColor(getActivity().getResources().getColor(R.color.white));
                multiCityText.setTextColor(getActivity().getResources().getColor(android.R.color.tab_indicator_text));

                returnDateView.setEnabled(true);
                returnDateView.setAlpha(1.0f);
                iv_swap_city.setVisibility(View.VISIBLE);
                fromCityName.setVisibility(View.VISIBLE);
                toCityName.setVisibility(View.VISIBLE);
                break;
            case 3:
                oneWayText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
                roundTripText.setBackground(getActivity().getResources().getDrawable(R.drawable.white_butten));
                multiCityText.setBackground(getActivity().getResources().getDrawable(R.drawable.orange_butten));
                oneWayText.setTextColor(getActivity().getResources().getColor(android.R.color.tab_indicator_text));
                roundTripText.setTextColor(getActivity().getResources().getColor(android.R.color.tab_indicator_text));
                multiCityText.setTextColor(getActivity().getResources().getColor(R.color.white));
                iv_swap_city.setVisibility(View.GONE);
                fromCityName.setVisibility(View.GONE);
                toCityName.setVisibility(View.GONE);
                break;
        }

        if(i == 1 || i == 2){
            oneRoundView.setVisibility(View.VISIBLE);
            multiCityView.setVisibility(View.GONE);
            //fromCityCode.setText("");
           // toCityCode.setText("");
          //  fromCityCode.setText(fromCityId);
          //  toCityCode.setText(toCityId);


        }else {
            multiCityView.setVisibility(View.VISIBLE);
            oneRoundView.setVisibility(View.GONE);
            multiCitySegments.clear();

            if(applicationPreference.getData(
                    applicationPreference.depFlightCode).length() != 0){
                multiCitySegments.add(new MultiCitySegment(
                        applicationPreference.getData(applicationPreference.depFlightCode),
                        applicationPreference.getData(applicationPreference.arriFlightCode),
                        "All",
                        startDate));

                multiCitySegments.add(new MultiCitySegment(
                        applicationPreference.getData(applicationPreference.arriFlightCode),
                        "",
                        "All",
                        ""));

                updatePlace();

            }else {
                lodeEmptyData(emptyListCount);
            }
            multiCityViewAdapter = new MultiCityViewAdapter(FlightFragment.this,multiCitySegments);
            multiCityList.setAdapter(multiCityViewAdapter);
        }
    }

    private void updatePlace() {
      //  fromCityCode.setText("");
     //   toCityCode.setText("");

        StringBuilder stringBuilderFrom = new StringBuilder();
        StringBuilder stringBuilderTo = new StringBuilder();

        int i=0;
        while (i<multiCitySegments.size()){
            stringBuilderFrom.append(multiCitySegments.get(i).getOrigin()+"\n");
            stringBuilderTo.append(multiCitySegments.get(i).getDestination()+"\n");
            i++;
        }

      //  fromCityCode.setText(stringBuilderFrom.toString());
      //  toCityCode.setText(stringBuilderTo.toString());
    }

    private void lodeEmptyData(int i) {
        int j=0;
        while (j < i){
            multiCitySegments.add(new MultiCitySegment(multiCitySegments.get(multiCitySegments.size()-1).getDestination(),"",
                    "All",""));
            j++;
        }
    }

    private void callCitySearch(int i) {
        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                new CitySearchFragment(FlightFragment.this,i),null,true);
    }



    @OnClick(R.id.departure_date)
    void departureDate(){
        //commonUtils.callDatePicker(this, FlightFragment.this, 1);
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new FairCalendarFragment(fromCityId, toCityId,FlightFragment.this,1), null, true);
    }

    @OnClick(R.id.return_date)
    void returnDate(){
        //commonUtils.callDatePicker(this, FlightFragment.this, 2);
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new FairCalendarFragment(toCityId,fromCityId, FlightFragment.this,2,returnDate), null, true);
    }

    @OnClick(R.id.search_flight)
    void searchFlight(){
        Gson gson = new Gson();
        RequestParams requestParams = new RequestParams();
        String parsingData;
        String ad[]=tv_adult.getText().toString().split(" ");
        adultCount= Integer.parseInt(ad[0]);
        String ch[]=tv_child.getText().toString().split(" ");
        childCount= Integer.parseInt(ch[0]);
        String inf[]=tv_infant.getText().toString().split(" ");
        infantCount= Integer.parseInt(inf[0]);

        if(travellerValidation(adultCount,childCount,infantCount).equals("OK"))
        {

            if (multiCityView.getVisibility() == View.VISIBLE)
            {

                boolean isValid = validateList();
                if (isValid) {
                    //TODO : PROCEED WITH NEXT FLOW
                    MultiCityParser multiCityParser = new MultiCityParser(
                            String.valueOf(adultCount), String.valueOf(childCount),
                            String.valueOf(infantCount), "multicity",
                            "", true, multiCitySegments);
                    parsingData = gson.toJson(multiCityParser);
                    requestParams.put("flight_search", parsingData);
                    lodeFlightData(requestParams, 2);
                } else {
                    commonUtils.toastShort("Please enter all the data to proceed.", getActivity());
                }

            } else {
                if (fromCityId != null && toCityId != null)
                {
                    applicationPreference.setData(
                            applicationPreference.depFlightCode, fromCityId);
                    applicationPreference.setData(
                            applicationPreference.depAirportName,
                            fromCityName.getText().toString());
                    applicationPreference.setData(
                            applicationPreference.arriFlightCode, toCityId);
                    applicationPreference.setData(
                            applicationPreference.arriAirportName,
                            toCityName.getText().toString());

                    switch (isRoundTrip) {
                        case 0:
                            if (departureDate != null) {
                                List<OneWaySegment> oneWaySegments = new ArrayList<OneWaySegment>();
                                oneWaySegments.add(new OneWaySegment(fromCityId, toCityId,
                                        cabinClass, departureDate + "T00:00:00"));
                                OneWayMain oneWayMain = new OneWayMain(String.valueOf(adultCount),
                                        String.valueOf(childCount), String.valueOf(infantCount),
                                        "oneway", oneWaySegments, "");

                                parsingData = gson.toJson(oneWayMain);
                                requestParams.put("flight_search", parsingData);
                                lodeFlightData(requestParams, 1);
                            } else {
                                commonUtils.toastShort("Please select departure date", getActivity());
                            }
                            break;
                        case 1:
                            if (departureDate != null && returnDate != null)
                            {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date date1 = null, date2 = null;
                                try {
                                    date1 = sdf.parse(departureDate);
                                    date2 = sdf.parse(returnDate);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                if (date1.before(date2) || date1.equals(date2)) {
                                    List<OneWaySegment> oneWaySegments = new ArrayList<OneWaySegment>();
                                    oneWaySegments.add(new OneWaySegment(fromCityId, toCityId,
                                            cabinClass, departureDate + "T00:00:00",
                                            returnDate + "T00:00:00"));
                                    OneWayMain oneWayMain = new OneWayMain(String.valueOf(adultCount),
                                            String.valueOf(childCount), String.valueOf(infantCount),
                                            "circle", oneWaySegments, "");

                                    parsingData = gson.toJson(oneWayMain);
                                    requestParams.put("flight_search", parsingData);
                                    lodeFlightData(requestParams, 3);
                                } else {
                                    commonUtils.toastShort("Return date cannot be less than departure date.", getActivity());
                                }

                            } else {
                                commonUtils.toastShort("Please select departure date and return date", getActivity());
                            }
                            break;
                    }
                } else {
                    commonUtils.toastShort("Please select from and to city", getActivity());
                }
            }
        }else {
            commonUtils.toastShort(travellerValidation(adultCount, childCount, infantCount), getActivity());
        }
    }

    private void lodeFlightData(RequestParams requestParams, int i) {
        webServiceController.postRequest(apiConstants.FLIGHT_SEARCH,requestParams,i);
    }


    @BindView(R.id.advanced_option)
    TextView tv_fareClass;

    @OnClick(R.id.advanced_option)
    void advanceOption(){
        commonUtils.showAdvanceOptionDialog(getActivity(),
                cabinClass, FlightFragment.this);
    }

    private boolean validateList() {
        int i=0;
        boolean isValid = false;
        while (i<multiCitySegments.size()){
            if(multiCitySegments.get(i).getCabinClass().length() != 0 &&
                    multiCitySegments.get(i).getDepartureDate().length() != 0 &&
                    multiCitySegments.get(i).getDestination().length() != 0 &&
                    multiCitySegments.get(i).getOrigin().length() != 0){
                isValid = true;
            }else {
                isValid = false;
                break;
            }
            i++;
        }
        return isValid;
    }

    String fromCityId = null, toCityId = null, startDate = null,
            departureDate = null, returnDate = null, cabinClass = "Economy";
    Integer adultCount = 1, childCount = 0, infantCount = 0, actionValue = 1,
            emptyListCount = 2, multiCityListPosition, datePickerValue = 1,
            isRoundTrip = 0;

    WebServiceController webServiceController;
    List<MultiCitySegment> multiCitySegments = new ArrayList<MultiCitySegment>();
    MultiCityViewAdapter multiCityViewAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.flight_search_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),FlightFragment.this);

    }

    @SuppressLint("NewApi")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(multiCityList,getActivity());

        Calendar calendar = Calendar.getInstance();
        startDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)
                +"-"+calendar.get(Calendar.DAY_OF_MONTH);
        //departure
        notifyDate(startDate, 1);
        //return
        notifyDate(startDate, 2);
        selectTravelType(1);
        tv_fareClass.setText("Class >>"+ cabinClass);

        if(applicationPreference.getData(
                applicationPreference.depFlightCode).length() != 0){
            fromCityName.setText(applicationPreference
                    .getData(applicationPreference.depAirportName));
            toCityName.setText(applicationPreference
                    .getData(applicationPreference.arriAirportName));

            fromCityId = applicationPreference
                    .getData(applicationPreference.depFlightCode);
            toCityId = applicationPreference
                    .getData(applicationPreference.arriFlightCode);

            //fromCityCode.setText(fromCityId);
           // toCityCode.setText(toCityId);
        }
    }

    /*@Override
    public void onItemSelected(int index) {
        switch (actionValue){
            case 1:
                adultCount = index + 1;
                Log.d("COUNT",String.valueOf(adultCount));
                break;
            case 2:
                childCount = index;
                Log.d("COUNT",String.valueOf(childCount));
                break;
            case 3:
                infantCount = index;
                Log.d("COUNT",String.valueOf(infantCount));
                break;
        }
    }*/

    @Override
    public void citySearchResult(Integer type, String cityName, String cityCode)
    {
        switch (type)
        {
            case 1:
                if(toCityId == null){
                    fromCityName.setText(cityName);
                    //fromCityCode.setText(cityCode);
                    fromCityId = cityCode;
                }else {
                    if(!toCityId.equals(cityCode)){
                        fromCityName.setText(cityName);
                      //  fromCityCode.setText(cityCode);
                        fromCityId = cityCode;
                    }else {
                        commonUtils.toastShort("From and To destination cannot be same.", getActivity());
                    }
                }
                break;
            case 2:
                if(fromCityId == null){
                    toCityName.setText(cityName);
                  //  toCityCode.setText(cityCode);
                    toCityId = cityCode;
                }else {
                    if(!fromCityId.equals(cityCode)){
                        toCityName.setText(cityName);
                    //    toCityCode.setText(cityCode);
                        toCityId = cityCode;
                    }else {
                        commonUtils.toastShort("From and To destination cannot be same.", getActivity());
                    }
                }
                break;
            case 3:
                if(multiCityListPosition != null){
                    multiCitySegments.get(multiCityListPosition).setOrigin(cityCode);
                    multiCityViewAdapter.notifyDataSetChanged();
                }
                updatePlace();
                break;
            case 4:
                if(multiCityListPosition != null){
                    multiCitySegments.get(multiCityListPosition).setDestination(cityCode);
                    multiCityViewAdapter.notifyDataSetChanged();
                }
                updatePlace();
                break;
        }
    }

    @Override
    public void getResponse(String response, int flag)
    {
        Log.e("Flight search response:",response+"");
        switch (flag){

            default:
                        String catchFileName = null;
                        Boolean isDomestic = false;
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.has("status")){
                                commonUtils.toastShort(jsonObject.getString("message"), getActivity());
                            }else {
                                catchFileName = jsonObject.getString("cache_file_name");
                                isDomestic = jsonObject.getBoolean("IsDomestic");
                                if(isDomestic){
                                    Global.is_domestic_flag="1";
                                }else {
                                    Global.is_domestic_flag="0";
                                }
                                Global.search_id=jsonObject.getString("search_id");
                                Global.booking_source=jsonObject.getString("booking_source");
                                if(cabinClass.equalsIgnoreCase("All")){
                                    cabinClass="";
                                }
                                switch (flag)
                                {
                                    case 1:
                                        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                                                new OneWayFragment(response, catchFileName,
                                                        departureDate, cabinClass), null , true);
                                        break;
                                    case 2:
                                        intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                                                new MultiCityFragment(response, catchFileName,
                                                        multiCitySegments.get(0).getDepartureDate(),
                                                        cabinClass,"MC"), null, true);
                                        break;
                                    case 3:
                                        if(isDomestic)
                                        {
                                            intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                                                    new TwoWayFragment(response, catchFileName,
                                                            departureDate,returnDate, cabinClass), null, true);
                                        }else {
                                            intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                                                    new MultiCityFragment(response, catchFileName,
                                                            departureDate,returnDate, cabinClass,"RT"), null, true);
                                        }
                                        break;
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                break;

            case 4:
                break;
        }


    }

    public void addCity(int position, int i) {
        multiCityListPosition = position;
        callCitySearch(i);
    }

    public void addDate(int position, String multiFromCity,String multiToCity) {
        multiCityListPosition = position;
        //commonUtils.callDatePicker(this, FlightFragment.this, 3);
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new FairCalendarFragment(multiFromCity, multiToCity, FlightFragment.this, 3), null, true);
    }

    public void removeView(int position) {
        multiCitySegments.remove(position);
        multiCityViewAdapter.notifyDataSetChanged();

        updatePlace();
    }

    @Override
    public void notifyDate(String startDate, Integer datePickerValue) {
        String endDate_str;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date endDate = dateFormat.parse(startDate);
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMMM-dd EEEE",Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy EEEE",Locale.ENGLISH);
            endDate_str = sdf.format(endDate);
            String[] splitDate = endDate_str.split(" ");

            switch (datePickerValue){
                case 1:
                    weekName.setText(splitDate[3]);
                    dateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);

                    toWeekName.setText(splitDate[3]);
                    toDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);

                    departureDate = startDate;
                    returnDate = startDate;
                    break;
                case 2:
                    toWeekName.setText(splitDate[3]);
                    toDateValue.setText(splitDate[0]+" "+splitDate[1]+"' "+splitDate[2]);
                    returnDate = startDate;
                    break;
                case 3:

                    if(multiCityListPosition-1>=0)
                    {
                        if(multiCityListPosition==0)
                        {
                            multiCitySegments.get(multiCityListPosition).setDepartureDate(startDate+"T00:00:00");
                            if(multiCitySegments.size()>=2)
                            {
                                for(int i=1;i<multiCitySegments.size();i++)
                                {
                                    multiCitySegments.get(i).setDepartureDate("");
                                }
                            }else
                            {
                                multiCitySegments.get(multiCityListPosition).setDepartureDate(startDate+"T00:00:00");
                            }
                            multiCityViewAdapter.notifyDataSetChanged();
                            return;
                        }
                        if(dateValidation(multiCitySegments.get(multiCityListPosition-1).getDepartureDate(),startDate))
                        {
                            multiCitySegments.get(multiCityListPosition).setDepartureDate(startDate+"T00:00:00");
                            multiCityViewAdapter.notifyDataSetChanged();
                        }else
                        {
                            commonUtils.toastShort("Please select proper date",getActivity().getApplicationContext());
                            multiCitySegments.get(multiCityListPosition).setDepartureDate("");
                            multiCityViewAdapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        if(multiCityListPosition==0)
                        {
                            multiCitySegments.get(multiCityListPosition).setDepartureDate(startDate+"T00:00:00");
                            if(multiCitySegments.size()>=2)
                            {
                                for(int i=1;i<multiCitySegments.size();i++)
                                {
                                    multiCitySegments.get(i).setDepartureDate("");
                                }
                            }else
                            {
                                multiCitySegments.get(multiCityListPosition).setDepartureDate(startDate+"T00:00:00");
                            }
                            multiCityViewAdapter.notifyDataSetChanged();
                        }
                    }


                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAdvance(String selectedValue) {
        cabinClass = selectedValue;
        tv_fareClass.setText("Class >>"+ cabinClass);
    }

    public String travellerValidation(int ad,int ch,int in){
        String response="";
        if(in>ad){
            response="Infants can not be more than adults";
        }
        else if(ad+ch+in>9){
            response="More than 9 travellers can not be added";
        }else {
            response="OK";
        }


        return response;
    }

    private boolean dateValidation(String pre_date,String curr_date)
    {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(pre_date);
            Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(curr_date);
          /*
            if(date2.after(date1))
            {
                return true;
            }else
            {
                return false;
            }*/

            if(date2.before(date1))
            {
                return false;
            }else
            {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addTraveller()
    {
        View view = getLayoutInflater().inflate(R.layout.add_traveller_dialog, null);

        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);
        dialog.show();
        final TextView tv_adult_count,tv_child_count,tv_infant_count;
        ImageView iv_add_ad,iv_add_ch,iv_add_in,iv_minus_ad,iv_minus_ch,iv_minus_in;
        tv_adult_count=dialog.findViewById(R.id.no_of_ad);
        tv_child_count=dialog.findViewById(R.id.no_of_ch);
        tv_infant_count=dialog.findViewById(R.id.no_of_in);

        iv_add_ad=dialog.findViewById(R.id.iv_plus_ad);
        iv_add_ch=dialog.findViewById(R.id.iv_plus_ch);
        iv_add_in=dialog.findViewById(R.id.iv_plus_in);

        iv_minus_ad=dialog.findViewById(R.id.iv_minus_ad);
        iv_minus_ch=dialog.findViewById(R.id.iv_minus_ch);
        iv_minus_in=dialog.findViewById(R.id.iv_minus_in);

        Button bt_done=dialog.findViewById(R.id.bt_done);

        tv_adult_count.setText(adultCount+"");
        tv_child_count.setText(childCount+"");
        tv_infant_count.setText(infantCount+"");

        iv_add_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_adult_count.getText().toString())<9)
                {
                    adultCount++;
                    tv_adult_count.setText(Integer.parseInt(tv_adult_count.getText().toString()) + 1 + "");
                }
            }
        });

        iv_add_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_child_count.getText().toString())<9) {
                    childCount++;
                    tv_child_count.setText(Integer.parseInt(tv_child_count.getText().toString()) + 1 + "");
                }
            }
        });

        iv_add_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_infant_count.getText().toString())<9) {
                    infantCount++;
                    tv_infant_count.setText(Integer.parseInt(tv_infant_count.getText().toString()) + 1 + "");
                }
            }
        });

        iv_minus_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_adult_count.getText().toString())>0){
                    adultCount--;
                    tv_adult_count.setText(Integer.parseInt(tv_adult_count.getText().toString())-1+"");
                }

            }
        });

        iv_minus_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Integer.parseInt(tv_child_count.getText().toString())>0) {
                    childCount--;
                    tv_child_count.setText(Integer.parseInt(tv_child_count.getText().toString()) - 1 + "");
                }
            }
        });

        iv_minus_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_infant_count.getText().toString())>0) {
                    infantCount--;
                    tv_infant_count.setText(Integer.parseInt(tv_infant_count.getText().toString()) - 1 + "");
                }
            }
        });

        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adultCount+childCount+infantCount<1){
                    commonUtils.toastShort("Add a traveller",getActivity());
                    return;
                }
                if(adultCount+childCount+infantCount>9){
                    commonUtils.toastShort("Max. 9 travellers can be add",getActivity());
                    return;
                }
                adultCount=Integer.parseInt(tv_adult_count.getText().toString());
                childCount=Integer.parseInt(tv_child_count.getText().toString());
                infantCount=Integer.parseInt(tv_infant_count.getText().toString());

                tv_adult.setText(adultCount+" Adults");
                tv_child.setText(childCount+" Children");
                tv_infant.setText(infantCount+" Infants");
                dialog.dismiss();
            }
        });
    }

}