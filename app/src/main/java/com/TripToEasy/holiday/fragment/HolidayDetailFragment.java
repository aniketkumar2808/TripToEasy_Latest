package com.TripToEasy.holiday.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.TripToEasy.R;
import com.TripToEasy.holiday.adapter.GalleryAdapter;
import com.TripToEasy.holiday.adapter.ItineraryAdapter;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.holiday.ItineraryModel;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.webservice.WebInterface;
import com.TripToEasy.utils.webservice.WebServiceController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ptblr-1195 on 17/4/18.
 */

public class HolidayDetailFragment extends BaseFragment implements WebInterface{

    @BindView(R.id.bottom_sheet)
    View bottomSheet;

    @BindView(R.id.main_layout)
    LinearLayout mainLayout;

    @BindView(R.id.holiday_bkg)
    ImageView iv_holiday_bkg;


    @BindView(R.id.super_layout)
    RelativeLayout superLayout;

    /**
     * User for both itinerary and gallery
     * itinerary - Linear Layout Manager
     * gallery - Grid Layout Manager
     * */
    @BindView(R.id.list_view)
    RecyclerView recyclerView;

    @BindView(R.id.itinerary_gallery_title)
    TextView itineraryGalleryTitle;

    @BindView(R.id.over_view_title)
    TextView overViewTitle;

    @BindView(R.id.over_view_description)
    TextView packageDescription;

    @BindView(R.id.holiday_name)
    TextView holidayName;

    @BindView(R.id.tour_code)
    TextView tourCode;

    @BindView(R.id.package_duration)
    TextView packageDuration;

    @BindView(R.id.price_per_person)
    TextView pricePerPerson;

    @BindView(R.id.over_view)
    TextView selectionOverView;
    int adultCount=1,childCount=0;

    @BindView(R.id.itinerary)
    TextView selectionItinerary;

    @BindView(R.id.gallery_view)
    TextView selectionGallery;

    @BindView(R.id.total_price)
    TextView totalPrice;

    @BindView(R.id.holiday_rating)
    RatingBar holidayRating;

    @BindView(R.id.over_view_main)
    LinearLayout overViewMain;

    @BindView(R.id.itinerary_gallery_main)
    LinearLayout itineraryGalleryMain;
    String price;

    @SuppressLint("ValidFragment")
    public HolidayDetailFragment(String packageId) {
        this.packageId = packageId;
    }

    public HolidayDetailFragment() {
        /**
         * Empty Constructor
         * */
    }

    @OnClick(R.id.over_view_layout)
    void overViewClick(){
        selectionOverView.setTextColor(getActivity().getResources().getColor(R.color.white));
        selectionItinerary.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        selectionGallery.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));

        overViewMain.setVisibility(View.VISIBLE);
        itineraryGalleryMain.setVisibility(View.GONE);

        parseAction(this.response, 1);

    }

    @OnClick(R.id.itinerary)
    void itineraryClick(){
        selectionOverView.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        selectionItinerary.setTextColor(getActivity().getResources().getColor(R.color.white));
        selectionGallery.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));

        itineraryGalleryMain.setVisibility(View.VISIBLE);
        overViewMain.setVisibility(View.GONE);

        commonUtils.linearLayout(recyclerView, getActivity());
        parseAction(this.response, 2);
    }

    @OnClick(R.id.gallery_view)
    void galleryClick(){
        selectionOverView.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        selectionItinerary.setTextColor(getActivity().getResources().getColor(R.color.black_sixty));
        selectionGallery.setTextColor(getActivity().getResources().getColor(R.color.white));

        itineraryGalleryMain.setVisibility(View.VISIBLE);
        overViewMain.setVisibility(View.GONE);

        commonUtils.gridLayout(recyclerView, getActivity(), 3);
        parseAction(this.response, 3);
    }

    @OnClick(R.id.send_query)
    void sendQuery(){
        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame,
                new HolidaySendQuery(packageId, holidayName.getText().toString()),
                null, true);
    }

    BottomSheetBehavior bottomSheetBehavior;
    int height, superHeight;
    String packageId, response;

    WebServiceController webServiceController;
    List<ItineraryModel> itineraryModels = new ArrayList<ItineraryModel>();
    List<String> galleryList = new ArrayList<String>();

    @Override
    protected int getLayoutResource() {
        return R.layout.holiday_detail_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webServiceController = new WebServiceController(getActivity(), HolidayDetailFragment.this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                superLayout.setVisibility(View.GONE);
            }
        });

        callDetailApi();
    }

    private void callDetailApi() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("package_id", packageId);
        webServiceController.postRequest(
                apiConstants.HOLIDAY_DETAIL_API,
                requestParams, 1);
    }

    @Override
    public void getResponse(String response, int flag)
    {
        this.response = response;
        parseAction(this.response, 1);
    }

    private void parseAction(String response, int type)
    {
        try{
            JSONObject jsonObject = new JSONObject(response);
            switch (type){
                case 1:
                    JSONObject packageObject = jsonObject.getJSONObject("package");
                    holidayName.setText(packageObject.getString("package_name"));
                    String rating = packageObject.optString("rating");
                    if(rating.length() != 0){
                        holidayRating.setRating(Float.parseFloat(rating));
                    }
                    tourCode.setText(packageObject.getString("package_code"));

                    Integer noOfDays = Integer.parseInt(
                            packageObject.getString("duration"));
                    packageDuration.setText((noOfDays-1)+" Night's / "+noOfDays+" Day's");

                    overViewTitle.setText("Overview of "
                            +packageObject.getString("package_name"));
                    packageDescription.setText(Html.fromHtml(
                            packageObject.getString("package_description")));

                    pricePerPerson.setText(
                           Global.currencySymbol+" "
                                   + String.format("%.0f",Double.parseDouble(packageObject.getString("price"))/Double.parseDouble(Global.currencyValue)));

                    totalPrice.setText(Global.currencySymbol+" " + String.format("%.0f",Double.parseDouble(packageObject.getString("price"))/Double.parseDouble(Global.currencyValue)));
                    price= String.format("%.0f",Double.parseDouble(packageObject.getString("price"))/Double.parseDouble(Global.currencyValue));

                            Picasso.get()
                            .load("http://"+packageObject.getString("image"))
                            .into(iv_holiday_bkg);
                    break;
                case 2:
                    itineraryGalleryTitle.setText("Detailed Itinerary of "+
                            holidayName.getText().toString());
                    itineraryModels.clear();
                    JSONArray itineraryArray = jsonObject.getJSONArray("package_itinerary");
                    int i=0;
                    while (i < itineraryArray.length()){
                        JSONObject itineraryObj = itineraryArray.getJSONObject(i);
                        itineraryModels.add(new ItineraryModel(
                                itineraryObj.getString("iti_id"),
                                itineraryObj.getString("package_id"),
                                itineraryObj.getString("day"),
                                itineraryObj.getString("package_city"),
                                itineraryObj.getString("place"),
                                itineraryObj.getString("itinerary_description"),
                                itineraryObj.getString("itinerary_image")));
                        i++;
                    }
                    ItineraryAdapter itineraryAdapter = new ItineraryAdapter(
                            getActivity(), itineraryModels);
                    recyclerView.setAdapter(itineraryAdapter);
                    break;
                case 3:
                    itineraryGalleryTitle.setText("Gallery of "+
                            holidayName.getText().toString());
                    galleryList.clear();
                    JSONArray packageImages = jsonObject.getJSONArray("package_traveller_photos");
                    int j=0;
                    while (j < packageImages.length()){
                        JSONObject imageObject = packageImages.getJSONObject(j);
                        galleryList.add("http://"+imageObject.getString("traveller_image"));
                        j++;
                    }

                    GalleryAdapter galleryAdapter = new GalleryAdapter(
                            getActivity(), galleryList);
                    recyclerView.setAdapter(galleryAdapter);

                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if(superLayout.getVisibility() == View.GONE){
            superLayout.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.tv_bookHoliday)
    public void bookHoliday()
    {

        View view = getLayoutInflater().inflate(R.layout.holiday_traveller_selection_dialog, null);
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(view);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
        final TextView tv_adult_count,tv_child_count,tv_infant_count;
        ImageView iv_add_ad,iv_add_ch,iv_add_in,iv_minus_ad,iv_minus_ch,iv_minus_in;
        dialog.setTitle("Number of Travellers");
        tv_adult_count=dialog.findViewById(R.id.no_of_ad);
        tv_child_count=dialog.findViewById(R.id.no_of_ch);

        iv_add_ad=dialog.findViewById(R.id.iv_plus_ad);
        iv_add_ch=dialog.findViewById(R.id.iv_plus_ch);
        iv_minus_ad=dialog.findViewById(R.id.iv_minus_ad);
        iv_minus_ch=dialog.findViewById(R.id.iv_minus_ch);
        Button bt_done=dialog.findViewById(R.id.bt_done);

        tv_adult_count.setText(adultCount+"");
        tv_child_count.setText(childCount+"");

        iv_add_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_adult_count.getText().toString())<11) {
                    adultCount++;
                    tv_adult_count.setText(Integer.parseInt(tv_adult_count.getText().toString()) + 1 + "");
                }
            }
        });

        iv_add_ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(tv_child_count.getText().toString())<5) {
                    childCount++;
                    tv_child_count.setText(Integer.parseInt(tv_child_count.getText().toString()) + 1 + "");
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


        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adultCount+childCount<1){
                    commonUtils.toastShort("Add a traveller",getActivity());
                    return;
                }
                if(adultCount+childCount>9){
                    commonUtils.toastShort("Max. 9 travellers can be add",getActivity());
                    return;
                }
                adultCount=Integer.parseInt(tv_adult_count.getText().toString());
                childCount=Integer.parseInt(tv_child_count.getText().toString());

                intentAndFragmentService.fragmentDisplay(getActivity(),R.id.main_frame,
                        new HolidayBookingFragment(adultCount,childCount,price,packageId, holidayName.getText().toString()),null,true);

                dialog.dismiss();
            }
        });

    }

}