package com.TripToEasy.hotel.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.TripToEasy.model.parsingModel.LocationModel;
import com.TripToEasy.utils.Global;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.hotel.adapter.HotelListAdapter;
import com.TripToEasy.interfaces.HotelFilterNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.hotel.AmenitiesModel;
import com.TripToEasy.model.parsingModel.hotel.listing.HotelListingMain;
import com.TripToEasy.model.requestModel.hotel.HotelDetailRequest;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.OnClick;


public class HotelListFragment extends BaseFragment
        implements HotelFilterNotify, WebInterface {

    @BindView(R.id.city_name_text)
    TextView cityNameText;

    @BindView(R.id.room_count)
    TextView roomCountText;

    @BindView(R.id.passenger_count)
    TextView passengerCount;

    @BindView(R.id.check_in)
    TextView checkInText;

    @BindView(R.id.check_out)
    TextView checkOutText;

    @BindView(R.id.hotel_list)
    RecyclerView hotelList;

    @BindView(R.id.hotels_found)
    TextView hotels_found;

    @BindView(R.id.search_hotel_name)
    EditText search_hotel_name;

    @OnClick(R.id.filter_action)
    void filterAction(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new HotelFilterFragment(
                        fix_hotelMinPrice,
                        fix_hotelMaxPrice,
                        hotelMinPrice,
                        hotelMaxPrice,
                        filterAmenities,
                        filterStarHotel,
                        hotelLocationsList,
                        filterHotelLocationsList,
                        HotelListFragment.this),
                null, true);
    }

    @SuppressLint("ValidFragment")
    public HotelListFragment(String cityName, String checkInDate, String checkOutDate,
                             String adultCount, String roomCount, String response,
                             String nightCount, String childCount) {
        this.cityName = cityName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.adultCount = adultCount;
        this.roomCount = roomCount;
        this.response = response;
        this.nightCount = nightCount;
        this.childCount = childCount;
    }

    public HotelListFragment() {

    }

    String cityName, checkInDate, checkOutDate, adultCount, roomCount,
            response, nightCount, childCount;
   public static List<HotelListingMain> hotelListing = new ArrayList<HotelListingMain>();
    List<HotelListingMain> hotelListingBackup = new ArrayList<HotelListingMain>();
    HotelListAdapter hotelListAdapter;
    String hotelMinPrice,fix_hotelMinPrice,hotelMaxPrice,fix_hotelMaxPrice;
    List<AmenitiesModel> filterAmenities = new ArrayList<AmenitiesModel>();
    List<Integer> filterStarHotel = new ArrayList<Integer>();
    List<LocationModel> hotelLocationsList = new ArrayList<LocationModel>();
    List<LocationModel> filterHotelLocationsList= new ArrayList<LocationModel>();
    WebServiceController webServiceController;
    String token, bookingSource,  searchId,  hotelResultIndex,  hotelCode;
    HotelDetailFragment hotelDetailFragment = null;

    @Override
    protected int getLayoutResource() {
        return R.layout.hotel_listing_fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        cityNameText.setText(cityName);
        roomCountText.setText(roomCount+" Rooms");
        try{
            int guest = Integer.parseInt(adultCount)+Integer.parseInt(childCount);
            passengerCount.setText(""+guest+" Guest");
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
        commonUtils.linearLayout(hotelList, getActivity());
        hotelListAdapter = new HotelListAdapter(getActivity(),
                HotelListFragment.this, hotelListing);
        hotelList.setAdapter(hotelListAdapter);

        search_hotel_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length()>0)
                {
                    hotelListAdapter.getFilter().filter(s.toString());
                }else
                {
                    hotelListAdapter.getFilter().filter(s.toString());
                }
            }
        });





        webServiceController = new WebServiceController(getActivity(),
                HotelListFragment.this);
        dateModify();
        lodeListData();
    }

    private void lodeListData() {
        hotelListing.clear();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject hotelSearchObject = dataObject.getJSONObject("HotelSearchResult");
                    JSONArray hotelSearchArray = hotelSearchObject.getJSONArray("HotelResults");
                    int i=0;
                    List<Integer> hotelPriceList = new ArrayList<Integer>();
                    List<LocationModel> locationList = new ArrayList<LocationModel>();
                    while (i < hotelSearchArray.length())
                    {
                        JSONObject hotelObject = hotelSearchArray.getJSONObject(i);
                        JSONObject priceObj = hotelObject.getJSONObject("Price");
                        String hotelPrice = String.format("%.0f",(Double.parseDouble(priceObj.getString("RoomPrice"))+Double.parseDouble(priceObj.getString("Tax")))/Double.parseDouble(Global.currencyValue));

                        Boolean wifi = false, breakFast = false,
                                parking = false, pool = false;
                        try{
                            hotelPriceList.add((int)Float.parseFloat(hotelPrice));
                        }catch (NumberFormatException e){
                            hotelPriceList.add((int) Double.parseDouble(hotelPrice));
                        }

                        JSONArray amenities = hotelObject.getJSONArray("HotelAmenities");
                        int j = 0;
                        while (j < amenities.length())
                        {
                            if(amenities.getString(j).toLowerCase().contains("breakfast")){
                                breakFast = true;
                            }

                            if(amenities.getString(j).toLowerCase().contains("parking")){
                                parking = true;
                            }

                            if(amenities.getString(j).toLowerCase().contains("wireless")){
                                wifi = true;
                            }

                            if(amenities.getString(j).toLowerCase().contains("pool")){
                                pool = true;
                            }

                            j++;
                        }

                        hotelListing.add(new HotelListingMain(
                                hotelObject.getString("HotelName"),
                                hotelObject.getString("HotelLocation"),
                                hotelObject.getString("HotelAddress"),
                                hotelObject.getString("HotelPicture"),
                                hotelObject.getInt("StarRating"),
                                hotelPrice,
                                hotelObject.getInt("ResultIndex"),
                                hotelObject.getString("HotelCode"),
                                wifi,breakFast,parking,pool,
                                hotelObject.getString("ResultToken"),
                                jsonObject.getString("booking_source"),
                                jsonObject.getString("search_id")));
                        i++;


                        locationList.add(new LocationModel(hotelObject.getString("HotelLocation"),false));
                    }
                    int sort_flag_arv=1;
                    Collections.sort(locationList, new LocationSort(sort_flag_arv));
                    hotelLocationsList.addAll(removeDuplicates(locationList));



                    Collections.sort(hotelPriceList);
                    fix_hotelMinPrice = String.valueOf(hotelPriceList.get(0));
                    fix_hotelMaxPrice = String.valueOf(hotelPriceList.get(hotelPriceList.size()-1));
                    hotelPriceList.clear();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hotelListingBackup.clear();
                        hotelListingBackup.addAll(hotelListing);
                        hotelListAdapter.notifyDataSetChanged();
                        hotels_found.setText(hotelListing.size()+" Hotels found / "+hotelListing.size());
                    }
                });
            }
        };
        thread.start();
    }

    private void dateModify() {
        String cIn, cOut;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            Date checkIn = dateFormat.parse(checkInDate);
            Date checkOut = dateFormat.parse(checkOutDate);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE dd, MMM",Locale.ENGLISH);
            cIn = sdf.format(checkIn);
            cOut = sdf.format(checkOut);

            checkInText.setText("Check In :"+cIn);
            checkOutText.setText("Check Out :"+cOut);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Priority for filter
     * 1. Hotel Start
     * 2. MinPrice and MaxPrice
     * 3. Amenities
     * */
    @Override
    public void hotelFilter(String minPrice, String maxPrice,
                            List<AmenitiesModel> amenities, List<Integer> starHotel,List<LocationModel> hotelLocations)
    {
        hotelMinPrice = minPrice;
        hotelMaxPrice = maxPrice;
        filterAmenities.clear();
        filterStarHotel.clear();
        filterHotelLocationsList.clear();
        filterAmenities.addAll(amenities);
        filterStarHotel.addAll(starHotel);
        filterHotelLocationsList.addAll(hotelLocations);
        if(minPrice == null && maxPrice == null &&hotelLocations.size()==0 && amenities.size() == 0 && starHotel.size() == 0)
        {
            hotelListing.clear();
            hotelListing.addAll(hotelListingBackup);
            hotelListAdapter.notifyDataSetChanged();
            hotels_found.setText(hotelListing.size()+" Hotels found / "+hotelListing.size());
        }else
            {
            List<HotelListingMain> hotelListingTemp = new ArrayList<HotelListingMain>();
            hotelListingTemp.addAll(hotelListingBackup);
            hotelListing.clear();
            if(starHotel.size() != 0)
            {
                int i=0;
                while (i < starHotel.size()){
                    int j=0;
                    while (j < hotelListingTemp.size()){
                        if(starHotel.get(i) == hotelListingTemp.get(j).getHotelRating()){
                            hotelListing.add(hotelListingTemp.get(j));
                        }
                        j++;
                    }
                    i++;
                }
                filterPrice(minPrice, maxPrice,hotelListing, amenities,hotelLocations);
            }else {
                filterPrice(minPrice, maxPrice,hotelListingTemp, amenities,hotelLocations);
            }
        }
    }

    private void filterPrice(String minPrice, String maxPrice,
                             List<HotelListingMain> hotelListing,
                             List<AmenitiesModel> amenities,List<LocationModel> hotelLocations) {
        if(minPrice != null && maxPrice != null)
        {

            List<HotelListingMain> hotelListingTemp = new ArrayList<HotelListingMain>();
            hotelListingTemp.addAll(hotelListing);
            this.hotelListing.clear();

            int i=0;
            while (i < hotelListingTemp.size()
                    ){
                if((int) Double.parseDouble(hotelListingTemp.get(i).getHotelPrice()) >= Integer.parseInt(minPrice)
                        && (int) Double.parseDouble(hotelListingTemp.get(i).getHotelPrice()) <= Integer.parseInt(maxPrice)){
                    this.hotelListing.add(hotelListingTemp.get(i));
                }
                i++;
            }
            filterAmenity(amenities, this.hotelListing,hotelLocations);
        }else {
            filterAmenity(amenities, hotelListing,hotelLocations);
        }
    }

    private void filterAmenity(List<AmenitiesModel> amenities,
                               List<HotelListingMain> hotelListing,List<LocationModel> hotelLocations) {
        if(amenities.size() != 0) {
            List<HotelListingMain> hotelListingTemp = new ArrayList<HotelListingMain>();
            hotelListingTemp.addAll(hotelListing);
            this.hotelListing.clear();

            int j = 0;
            while (j < hotelListingTemp.size())
            {
                int i = 0;
                Boolean isSuccess = false;
                while (i < amenities.size()) {
                    if (amenities.get(i).getAmenityName().equals("pool")) {
                        if (hotelListingTemp.get(j).getPool()) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                            break;
                        }
                    }

                    if (amenities.get(i).getAmenityName().equals("wireless")) {
                        if (hotelListingTemp.get(j).getWifi()) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                            break;
                        }
                    }

                    if (amenities.get(i).getAmenityName().equals("breakfast")) {
                        if (hotelListingTemp.get(j).getBreakFast()) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                            break;
                        }
                    }

                    if (amenities.get(i).getAmenityName().equals("parking")) {
                        if (hotelListingTemp.get(j).getParking()) {
                            isSuccess = true;
                        } else {
                            isSuccess = false;
                            break;
                        }
                    }

                    i++;
                }

                if (isSuccess) {
                    this.hotelListing.add(hotelListingTemp.get(j));
                }

                j++;

            }

            filterLocation(hotelLocations, this.hotelListing);
        }else {
            filterLocation(hotelLocations, hotelListing);
        }

    }
    public void filterLocation(List<LocationModel> hotelLocations, List<HotelListingMain> hotelListing)
    {
        if(hotelLocations.size() != 0)
        {
            List<HotelListingMain> hotelListingTemp = new ArrayList<HotelListingMain>();
            hotelListingTemp.addAll(hotelListing);
            this.hotelListing.clear();
            int i=0;
            while (i < hotelLocations.size()){
                int j=0;
                while (j < hotelListingTemp.size()){
                    if(hotelLocations.get(i).getLocationName().equalsIgnoreCase(hotelListingTemp.get(j).getHotelLocation())){
                        this.hotelListing.add(hotelListingTemp.get(j));
                    }
                    j++;
                }
                i++;
            }

        }
        if (this.hotelListing.size() != 0)
        {
            hotels_found.setText(this.hotelListing.size() + " Hotels found / " + hotelListingBackup.size());
            hotelListAdapter.notifyDataSetChanged();
        } else {
            hotels_found.setText(this.hotelListing.size() + " Hotels found / " + hotelListingBackup.size());
            commonUtils.toastShort("No Data Found for search parameter.",
                    getActivity());
            hotelListAdapter.notifyDataSetChanged();
        }


    }


    public void navigateDetail(String token, String bookingSource, String searchId,
                               Integer hotelResultIndex, String hotelCode) {
        this.token = token;
        this.bookingSource = bookingSource;
        this.searchId = searchId;
        this.hotelResultIndex = String.valueOf(hotelResultIndex);
        this.hotelCode = hotelCode;

        getHotelDetail("get_details");

    }

    private void getHotelDetail(String type) {
        HotelDetailRequest hotelDetailRequest = new HotelDetailRequest(
                token, hotelCode, token, bookingSource,
                type, searchId);
        Gson gson = new Gson();
        RequestParams requestParams = new RequestParams();
        switch (type){
            case "get_details":
                requestParams.put("hotel_details", gson.toJson(hotelDetailRequest));
                webServiceController.postRequest(
                        apiConstants.HOTEL_DETAIL_API, requestParams, 1);
                break;
            case "get_room_details":
                requestParams.put("room_list", gson.toJson(hotelDetailRequest));
                webServiceController.postRequest(
                        apiConstants.HOTEL_ROOM_LIST,requestParams,2);
                break;
        }
    }
    String hotelDetailResponse,roomListResponse;
    @Override
    public void getResponse(String response, int flag) {
        switch (flag){
            case 1:

                hotelDetailResponse=response;
                try {
                    JSONObject jsonObject=new JSONObject(hotelDetailResponse);
                    if(jsonObject.getInt("status")==0){
                        commonUtils.toastShort(jsonObject.getString("message"),getActivity());
                    }else {
                        getHotelDetail("get_room_details");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                break;
            case 2:
                roomListResponse=response;
                try {
                    JSONObject jsonObject=new JSONObject(roomListResponse);
                    if(jsonObject.getInt("staus")==0){
                        commonUtils.toastShort(jsonObject.getString("message"),getActivity());
                    }else {
                        Global.room_select_flag=0;
                        hotelDetailFragment = new HotelDetailFragment(hotelDetailResponse,roomListResponse, token, bookingSource,
                                searchId, hotelResultIndex, hotelCode, adultCount,
                                roomCount, checkInText.getText().toString(),
                                checkOutText.getText().toString(),
                                nightCount,
                                childCount);
                        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                                hotelDetailFragment,null, true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                break;
        }
    }
    public static List<LocationModel>  removeDuplicates(List<LocationModel> list){
        Set set = new TreeSet(new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                if(((LocationModel)o1).getLocationName().equalsIgnoreCase(((LocationModel)o2).getLocationName())){
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);

        final ArrayList newList = new ArrayList(set);
        return newList;
    }
}

class LocationSort  implements Comparator<LocationModel> {
    int sort_flag;
    public LocationSort(int flag
    ){
        sort_flag=flag;
    }
    public int compare(LocationModel a, LocationModel b)
    {
        String obj1 = a.getLocationName();
        String obj2 = b.getLocationName();
        if(sort_flag==0)
        {
            //low to high
            return obj2.compareTo(obj1);
        }else {
            //high to low
            return obj1.compareTo(obj2);
        }

    }

}
