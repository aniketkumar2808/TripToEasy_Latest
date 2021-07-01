package com.TripToEasy.hotel.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TripToEasy.utils.Global;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.hotel.adapter.FacilityAdapter;
import com.TripToEasy.hotel.adapter.HotelImageAdapter;
import com.TripToEasy.hotel.adapter.RoomListAdapter;
import com.TripToEasy.main.activity.MainActivity;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.hotel.roomList.RoomListModel;
import com.TripToEasy.traveller.TravellerFragment;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by ptblr-1195 on 26/3/18.
 */

public class HotelDetailFragment extends BaseFragment implements WebInterface{

    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    @BindView(R.id.main_layout)
    LinearLayout mainLayout;

    @BindView(R.id.super_layout)
    RelativeLayout superLayout;

    @BindView(R.id.room_list)
    RecyclerView roomListView;

    @BindView(R.id.facility_list)
    RecyclerView facilityListView;

    @BindView(R.id.hotel_name)
    TextView hotelNameText;

    @BindView(R.id.hotel_address)
    TextView hotelAddressText;

    @BindView(R.id.hotel_rating)
    RatingBar hotelRatingStar;

    @BindView(R.id.selected_room_name)
    TextView selectedRoomName;

    @BindView(R.id.price_per_person)
    TextView perPersonPrice;

    @BindView(R.id.check_in)
    TextView checkInText;

    @BindView(R.id.check_out)
    TextView checkOutText;

    @BindView(R.id.room_count)
    TextView roomCount;

    @BindView(R.id.guest_count)
    TextView guestCount;

    @BindView(R.id.final_price)
    TextView finalPriceText;

    @BindView(R.id.night_count)
    TextView nightCountText;

    @BindView(R.id.loader_giff)
    GifImageView loaderGiff;

    @BindView(R.id.hotel_description)
    TextView hotelDetailsText;

    @BindView(R.id.show_more)
    TextView shoMoreText;

    @BindView(R.id.total_price)
    TextView bookingPrice;

    @OnClick(R.id.show_more)
    void textLength(){
        if(descTextCount == 0){
            descTextCount++;
            updateTextMaxLength(10000);
            shoMoreText.setText(getActivity().getResources().getString(R.string.show_less));
        }else {
            descTextCount = 0;
            updateTextMaxLength(200);
            shoMoreText.setText(getActivity().getResources().getString(R.string.show_more));
        }

    }

    @BindView(R.id.select_room_text)
    TextView sRoomText;

    @BindView(R.id.hotel_detail)
    TextView hDetails;

    @BindView(R.id.map_view)
    TextView mView;

    @BindView(R.id.room_list_layout)
    RelativeLayout roomListLayout;

    @BindView(R.id.detail_layout)
    LinearLayout detailLayout;

    @BindView(R.id.map_layout)
    LinearLayout mapLayout;

    @BindView(R.id.rv_images)
    RecyclerView rv_images;

    @OnClick(R.id.select_room)
    void selectRoom(){
        sRoomText.setTextColor(getActivity().getResources().getColor(R.color.white));
        hDetails.setTextColor(getActivity().getResources().getColor(R.color.all_page_bg));
        mView.setTextColor(getActivity().getResources().getColor(R.color.all_page_bg));

        roomListLayout.setVisibility(View.VISIBLE);
        detailLayout.setVisibility(View.GONE);
        mapLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.hotel_detail)
    void hotelDetail(){
        hDetails.setTextColor(getActivity().getResources().getColor(R.color.white));
        sRoomText.setTextColor(getActivity().getResources().getColor(R.color.all_page_bg));
        mView.setTextColor(getActivity().getResources().getColor(R.color.all_page_bg));
        roomListLayout.setVisibility(View.GONE);
        detailLayout.setVisibility(View.VISIBLE);
        mapLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.map_view)
    void mapView(){
        mView.setTextColor(getActivity().getResources().getColor(R.color.white));
        hDetails.setTextColor(getActivity().getResources().getColor(R.color.all_page_bg));
        sRoomText.setTextColor(getActivity().getResources().getColor(R.color.all_page_bg));
        roomListLayout.setVisibility(View.GONE);
        detailLayout.setVisibility(View.GONE);
        mapLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.sell_all_amenities)
    void seeAllAmenities(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Facilities");

        RecyclerView recyclerView = new RecyclerView(getActivity());
        recyclerView.setPadding(10,10,10,10);
        commonUtils.linearLayout(recyclerView, getActivity());
        facilityAdapter = new FacilityAdapter(getActivity(),
                hotelFacilities, 2);
        recyclerView.setAdapter(facilityAdapter);
        alert.setView(recyclerView);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @BindView(R.id.mapView)
    MapView mapView;

    @OnClick(R.id.book_now)
    void bookNow(){
      /*  intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new TravellerFragment(Integer.parseInt(adultCount),
                        Integer.parseInt(childCount), 0,
                        roomPrice, 2), null, true);*/
      callRoomBlockApi();
    }

    @SuppressLint("ValidFragment")
    public HotelDetailFragment(String hotelDetailresponse,String roomListresponse, String token,
                               String bookingSource, String searchId,
                               String hotelResultIndex, String hotelCode,
                               String travellerCount, String roomCount, String checkIn,
                               String checkOut,
                               String nightCount, String childCount) {
        this.hotelDetailresponse = hotelDetailresponse;
        this.roomListresponse = roomListresponse;
        this.token = token;
        this.bookingSource = bookingSource;
        this.searchId = searchId;
        this.hotelResultIndex = hotelResultIndex;
        this.hotelCode = hotelCode;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
        this.roomCountValue = roomCount;
        this.adultCount = travellerCount;
        this.nightCount = nightCount;
        this.childCount = childCount;
    }

    public HotelDetailFragment() {
        /**
         * Empty constructor
         * */
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.hotel_detail_fragment;
    }

    BottomSheetBehavior bottomSheetBehavior;
    int height, superHeight;
    List<RoomListModel> roomListModelList = new ArrayList<RoomListModel>();
    List<String> imageList = new ArrayList<String>();
    List<String> hotelFacilities = new ArrayList<String>();
    RoomListAdapter roomListAdapter;
    FacilityAdapter facilityAdapter;
    String hotelDetailresponse,roomListresponse, token, bookingSource, searchId, hotelResultIndex,
            hotelCode, hotelDetail, paymentType, hotelName = null, hotelAddress = null,
            roomName = null, roomPrice, checkInDate, checkOutDate, roomCountValue,
            adultCount, childCount, nightCount, amenityOne = null, amenityTwo = null;
    Double latitude, longitude;
    Integer hotelRating, descTextCount = 0;
    Boolean refundable = false;
    WebServiceController webServiceController;
    private GoogleMap googleMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(),
                HotelDetailFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).hideToolBar(2);
        mapView.onResume();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.horizontalLayout(rv_images,getActivity());

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        ViewTreeObserver vto = bottomSheet.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    mainLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }else {
                    mainLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                height = mainLayout.getHeight();
                superHeight = superLayout.getHeight();

                height = superHeight - (height + 150);
                bottomSheetBehavior.setPeekHeight(height);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        checkInText.setText(checkInDate);
        checkOutText.setText(checkOutDate);
        roomCount.setText(roomCountValue+" Rooms");
        guestCount.setText(
                (Integer.parseInt(adultCount)+Integer.parseInt(childCount))+" Guest");

        commonUtils.linearLayout(roomListView, getActivity());
        roomListAdapter = new RoomListAdapter(getActivity(),
                HotelDetailFragment.this,
                roomListModelList);
        roomListView.setAdapter(roomListAdapter);

        commonUtils.gridLayout(facilityListView, getActivity(), 2);

        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        callDetailParsing();

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For dropping a marker at a point on the Map
                if(latitude!=null && longitude!=null)
                {
                    LatLng sydney = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions().position(sydney).
                            title(hotelName).snippet(getActivity().getResources().getString(R.string.Rs)
                            +" "+roomPrice));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });
    }
    String traceID,firstroomToken,firstroomTokenKey;
    String  roomUniqueID;
    private void callDetailParsing() {
        roomListModelList.clear();
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    JSONObject jsonObject = new JSONObject(hotelDetailresponse);
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject hotelInfoObject = dataObject.getJSONObject("HotelInfoResult");
                    final JSONObject hotelDetailObject = hotelInfoObject.getJSONObject("HotelDetails");
                    traceID=hotelInfoObject.getString("TraceId");

                    JSONObject customObj=hotelInfoObject.getJSONObject("Custom");
                    firstroomToken=customObj.getString("token");
                    firstroomTokenKey= customObj.getString("token_key");
                    hotelDetail = hotelDetailObject.getString("Description");
                    hotelName = hotelDetailObject.getString("HotelName");
                    hotelAddress = hotelDetailObject.getString("Address");
                    hotelRating = hotelDetailObject.getInt("StarRating");
                    try
                    {
                        latitude = hotelDetailObject.getDouble("Latitude");
                        longitude = hotelDetailObject.getDouble("Longitude");

                    }catch (Exception e)
                    {
                        latitude=0.0;
                        longitude=0.0;
                    }

                    if(!hotelDetailObject.isNull("Images"))
                    {
                        JSONArray imageArray = hotelDetailObject.getJSONArray("Images");
                        int i=0;
                        while (i< imageArray.length())
                        {
                            imageList.add(imageArray.getString(i));
                            i++;
                        }
                    }
                    else
                    {
                        imageList.add("http://www.flytripnow.com/mobile_webservices/mobile/index.php/");
                        Log.e("Images is ","null");
                    }

                    JSONArray facilityArray = hotelDetailObject.getJSONArray("HotelFacilities");
                    int j=0;
                    while (j<facilityArray.length()){
                        hotelFacilities.add(facilityArray.getString(j));
                        j++;
                    }

                    /**
                     * First Room Details
                     * */
                    JSONObject firstRoomDetailsObj = hotelDetailObject.getJSONObject("first_room_details");
                    roomName = firstRoomDetailsObj.getString("room_name");

                    JSONObject priceObject = firstRoomDetailsObj.getJSONObject("Price");
                    roomPrice =  String.format("%.0f",((Double.valueOf(priceObject.getInt("RoomPrice"))+Double.valueOf(priceObject.getInt("Tax")))));

                      //roomUniqueID=firstRoomDetailsObj.getJSONObject("Room_data").getString("RoomUniqueId");

                    if(hotelDetailObject.getString("first_rm_cancel_date").length() != 0){
                        refundable = true;
                    }else {
                        refundable = false;
                    }

                    JSONArray otherInclude = hotelDetailObject.optJSONArray("Amenities");
                    int k=0;
                    while (k<otherInclude.length()){
                        switch (k){
                            case 0:
                                amenityOne = otherInclude.getString(k);
                                break;
                            case 1:
                                amenityTwo = otherInclude.getString(k);
                                break;
                        }
                        k++;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

               /* roomListModelList.add(new RoomListModel(roomName, amenityOne,
                        amenityTwo, roomPrice, nightCount, refundable,traceID,searchId,"cancellation Policy",firstroomToken,firstroomTokenKey ,true));
*/
               notifyRoomResponse(roomListresponse);
                /*LatLng sydney = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(sydney)
                        .title(hotelName).snippet(roomPrice));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HotelImageAdapter hotelImgAdapter = new HotelImageAdapter(getActivity(),
                                HotelDetailFragment.this, imageList);
                        rv_images.setAdapter(hotelImgAdapter);

                        roomListAdapter.notifyDataSetChanged();
                        facilityAdapter = new FacilityAdapter(getActivity(),
                                hotelFacilities, 1);
                        facilityListView.setAdapter(facilityAdapter);

                        hotelNameText.setText(hotelName);
                        hotelAddressText.setText(hotelAddress);
                        hotelRatingStar.setRating(Float.parseFloat(String.valueOf(hotelRating)));

                        /*finalPriceText.setText(getActivity().getResources().getString(R.string.Rs)+" "+roomPrice);
                        perPersonPrice.setText(getActivity().getResources().getString(R.string.Rs)+" "+roomPrice);
                        selectedRoomName.setText(roomName);
                        bookingPrice.setText(getActivity().getResources().getString(R.string.Rs)+" "+roomPrice);*/

                        finalPriceText.setText(Global.currencySymbol+" "+String.format("%.0f",Double.parseDouble(roomPrice)/Double.parseDouble(Global.currencyValue)));
                        perPersonPrice.setText(Global.currencySymbol+" "+String.format("%.0f",Double.parseDouble(roomPrice)/Double.parseDouble(Global.currencyValue)));
                        selectedRoomName.setText(roomName);
                        bookingPrice.setText(Global.currencySymbol+" "+String.format("%.0f",Double.parseDouble(roomPrice)/Double.parseDouble(Global.currencyValue)));


                        String refText;
                        if(refundable){
                            refText = "Refundable";
                        }else {
                            refText = "Not Refundable";
                        }
                        nightCountText.setText("("+nightCount+" Nights) "+refText);

                        updateTextMaxLength(200);
                    }
                });
            }
        };
        thread.start();
    }

    private void updateTextMaxLength(int i) {
        int maxLength = i;
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        hotelDetailsText.setFilters(fArray);
        hotelDetailsText.setText(Html.fromHtml(hotelDetail));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).hideToolBar(1);
        mapView.onDestroy();
    }

    String mobileBLockResponse;
    @Override
    public void getResponse(String response, int flag) {
        switch (flag){
            case 1:
                this.hotelDetailresponse = response;
                callDetailParsing();
                break;
            case 2:
                mobileBLockResponse = response;
                intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new TravellerFragment(mobileBLockResponse,Integer.parseInt(adultCount),
                        Integer.parseInt(childCount), 0,
                        roomPrice, 2), null, true);
                break;
        }
    }

    /**
     * NOTE :
     * The reason we are not taking zero position list is
     * we have that data from detail API
     * so no need to add the same info again
     * */
    public void notifyRoomResponse(String response)
    {
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray dataArray = dataObject.getJSONArray("data");
            roomListModelList.clear();
            int i=0;
            while (i<dataArray.length()){
                JSONObject parsingObject = dataArray.getJSONObject(i);
                JSONObject roomObject = parsingObject.getJSONObject("room_data");

                JSONArray amenityList = roomObject.getJSONArray("Amenities");
                int j=0;
                String amenityOne = "", amenityTwo = "";
                while (j<amenityList.length()){
                    switch (j){
                        case 0:
                            amenityOne = amenityList.getString(j);
                            break;
                        case 1:
                            amenityTwo = amenityList.getString(j);
                            break;
                    }
                    j++;
                }

                if(roomObject.getString("LastCancellationDate").length() != 0){
                    refundable = true;
                }else {
                    refundable = false;
                }
                JSONObject customObj=roomObject.getJSONObject("custom");
                roomListModelList.add(new RoomListModel(
                        roomObject.getString("RoomTypeName"),
                        amenityOne,
                        amenityTwo,
                     //   String.valueOf(roomObject.getInt("RoomPrice")),
                        String.format("%.0f",((Double.valueOf(roomObject.getInt("RoomPrice"))+Double.valueOf(roomObject.getInt("Tax")))/Double.parseDouble(Global.currencyValue))),
                        /*Double.parseDouble(String.valueOf(roomObject.getInt("RoomPrice"))+Double.parseDouble(String.valueOf(roomObject.getInt("Tax"))))*/
                        nightCount,
                        refundable,
                        roomObject.getString("ResultIndex"),
                        roomObject.getString("search_id"),
                        roomObject.getString("CancellationPolicy"),
                        customObj.getString("token"),
                        customObj.getString("token_key"),
                        false));

                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        loaderGiff.setVisibility(View.GONE);
        roomListAdapter = new RoomListAdapter(getActivity(),
                HotelDetailFragment.this,
                roomListModelList);
        roomListView.setAdapter(roomListAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    String cancelPolicy;String roomtoken;String roomtokenKey,roomResultIndex;
    public void updateRoomDetail(String roomName,String roomPrice,boolean refund,String roomResultIndex,String cancelPolicy,String roomtoken,String roomtokenKey){
       /* finalPriceText.setText(getActivity().getResources().getString(R.string.Rs)+" "+roomPrice);
        perPersonPrice.setText(getActivity().getResources().getString(R.string.Rs)+" "+roomPrice);
        selectedRoomName.setText(roomName);
        bookingPrice.setText(getActivity().getResources().getString(R.string.Rs)+" "+roomPrice);*/

        finalPriceText.setText(Global.currencySymbol+" "+roomPrice);
        perPersonPrice.setText(Global.currencySymbol+" "+roomPrice);
        selectedRoomName.setText(roomName);
        bookingPrice.setText(Global.currencySymbol+" "+roomPrice);


        String refText;
        if(refund){
            refText = "Refundable";
        }else {
            refText = "Not Refundable";
        }
        nightCountText.setText("("+nightCount+" Nights) "+refText);
        this.roomResultIndex=roomResultIndex;
        this.cancelPolicy=cancelPolicy;
        this.roomtoken=roomtoken;
        this.roomtokenKey=roomtokenKey;
    }

    private void callRoomBlockApi(){
     String room_image_url="-";
     try{
         room_image_url=imageList.get(0);
     }catch (Exception e){
         room_image_url="not found";
     }

        HashMap<String,String> detailsparam=new HashMap<String, String>();
        RequestParams requestParams = new RequestParams();
        Gson gson = new Gson();
                if(Global.room_select_flag==1)
                {
                    detailsparam.put("HotelCode", hotelCode);
                    detailsparam.put("ResultIndex", roomResultIndex);
                    detailsparam.put("search_id", searchId);
                    detailsparam.put("TraceId", traceID);
                    detailsparam.put("HotelName", hotelName);
                    detailsparam.put("StarRating", hotelRating.toString());
                    detailsparam.put("HotelAddress", hotelAddress);
                    detailsparam.put("HotelImage",room_image_url);
                    detailsparam.put("CancellationPolicy", cancelPolicy);
                    requestParams.put("token", roomtoken);
                    requestParams.put("TokenId",roomtokenKey);
                    requestParams.put("details", gson.toJson(detailsparam));
                }else {
                    detailsparam.put("HotelCode", hotelCode);
                    detailsparam.put("ResultIndex", traceID);
                    detailsparam.put("search_id", searchId);
                    detailsparam.put("TraceId", traceID);
                    detailsparam.put("HotelName", hotelName);
                    detailsparam.put("StarRating", hotelRating.toString());
                    detailsparam.put("HotelAddress", hotelAddress);
                    detailsparam.put("HotelImage",room_image_url);
                    detailsparam.put("CancellationPolicy", roomListModelList.get(0).getCancelPolicy());
                    requestParams.put("token", roomListModelList.get(0).getToken());
                    requestParams.put("TokenId",roomListModelList.get(0).getTokenKey());
                    requestParams.put("details", gson.toJson(detailsparam));
                }

                webServiceController.postRequest(
                apiConstants.HOTEL_ROOM_BLOCK, requestParams, 2);

    }
}