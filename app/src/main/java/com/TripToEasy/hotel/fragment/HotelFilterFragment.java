package com.TripToEasy.hotel.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.hotel.adapter.AmenitiesAdapter;
import com.TripToEasy.hotel.adapter.HotelLocationAdapter;
import com.TripToEasy.interfaces.HotelFilterNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.LocationModel;
import com.TripToEasy.model.parsingModel.hotel.AmenitiesModel;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelFilterFragment extends BaseFragment
{

    @BindView(R.id.range_bar)
    RangeSeekBar rangeSeekBar;

    @BindView(R.id.price_value)
    TextView priceValue;

    @BindView(R.id.amenities_list) RecyclerView amenityListView;
    @BindView(R.id.location_list) RecyclerView location_list;

    @BindView(R.id.star_one_layout)
    LinearLayout starOneLayout;

    @BindView(R.id.star_two_layout)
    LinearLayout starTwoLayout;

    @BindView(R.id.star_three_layout)
    LinearLayout starThreeLayout;

    @BindView(R.id.star_four_layout)
    LinearLayout starFourLayout;

    @BindView(R.id.star_five_layout)
    LinearLayout starFiveLayout;

    @BindView(R.id.text_one)
    TextView textOne;

    @BindView(R.id.text_two)
    TextView textTwo;

    @BindView(R.id.text_three)
    TextView textThree;

    @BindView(R.id.text_four)
    TextView textFour;

    @BindView(R.id.text_five)
    TextView textFive;

    @BindView(R.id.star_one)
    ImageView starOne;

    @BindView(R.id.star_two)
    ImageView starTwo;

    @BindView(R.id.star_three)
    ImageView starThree;

    @BindView(R.id.star_four)
    ImageView starFour;

    @BindView(R.id.star_five)
    ImageView starFive;

    @OnClick(R.id.star_one_layout)
    void oneStar(){
        if(starValue.size() != 0){
            if(starValue.contains(1)){
                starValue.remove(starValue.indexOf(1));
                removeStar();
            }else {
                starValue.add(1);
               addOneStar();
            }
        }else {
            starValue.add(1);
            addOneStar();
        }
    }

    @SuppressLint("NewApi")
    private void removeStar() {
        starOneLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.white_butten));
        textOne.setTextColor(getActivity().getResources().getColor(R.color.black));
        starOne.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_not_selected));
    }

    @SuppressLint("NewApi")
    private void addOneStar() {
        starOneLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.blue_butten));
        textOne.setTextColor(getActivity().getResources().getColor(R.color.white));
        starOne.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_selected));
    }

    @OnClick(R.id.star_two_layout)
    void twoStar(){
        if(starValue.size() != 0)
        {
            if(starValue.contains(2)){
                starValue.remove(starValue.indexOf(2));
                removeStarTwo();
            }else {
                starValue.add(2);
                addTwoStar();
            }
        }else {
            starValue.add(2);
            addTwoStar();
        }
    }

    @SuppressLint("NewApi")
    private void removeStarTwo() {
        starTwoLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.white_butten));
        textTwo.setTextColor(getActivity().getResources().getColor(R.color.black));
        starTwo.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_not_selected));
    }

    @SuppressLint("NewApi")
    private void addTwoStar() {
        starTwoLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.blue_butten));
        textTwo.setTextColor(getActivity().getResources().getColor(R.color.white));
        starTwo.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_selected));
    }

    @OnClick(R.id.star_three_layout)
    void threeStar(){
        if(starValue.size() != 0){
            if(starValue.contains(3)){
                starValue.remove(starValue.indexOf(3));
                removeStarThree();
            }else {
                starValue.add(3);
                addThreeStar();
            }
        }else {
            starValue.add(3);
            addThreeStar();
        }
    }

    @SuppressLint("NewApi")
    private void addThreeStar() {
        starThreeLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.blue_butten));
        textThree.setTextColor(getActivity().getResources().getColor(R.color.white));
        starThree.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_selected));
    }

    @SuppressLint("NewApi")
    private void removeStarThree()
    {
        starThreeLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.white_butten));
        textThree.setTextColor(getActivity().getResources().getColor(R.color.black));
        starThree.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_not_selected));
    }

    @OnClick(R.id.star_four_layout)
    void fourStar(){
        if(starValue.size() != 0){
            if(starValue.contains(4)){
                starValue.remove(starValue.indexOf(4));
                removeStarFour();
            }else {
                starValue.add(4);
                addFourStar();
            }
        }else {
            starValue.add(4);
            addFourStar();
        }
    }

    private void removeStarFour() {
        starFourLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.white_butten));
        textFour.setTextColor(getActivity().getResources().getColor(R.color.black));
        starFour.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_not_selected));
    }

    private void addFourStar() {
        starFourLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.blue_butten));
        textFour.setTextColor(getActivity().getResources().getColor(R.color.white));
        starFour.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_selected));
    }

    @OnClick(R.id.star_five_layout)
    void fiveStar(){
        if(starValue.size() != 0){
            if(starValue.contains(5)){
                starValue.remove(starValue.indexOf(5));
                removeStarFive();
            }else {
                starValue.add(5);
                addFiveStar();
            }
        }else {
            starValue.add(5);
            addFiveStar();
        }
    }

    private void removeStarFive() {
        starFiveLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.white_butten));
        textFive.setTextColor(getActivity().getResources().getColor(R.color.black));
        starFive.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_not_selected));
    }

    private void addFiveStar() {
        starFiveLayout.setBackground(getActivity().getResources()
                .getDrawable(R.drawable.blue_butten));
        textFive.setTextColor(getActivity().getResources().getColor(R.color.white));
        starFive.setImageDrawable(getActivity().getResources()
                .getDrawable(R.drawable.start_selected));
    }

    @OnClick(R.id.apply_filter)
    void applyFilter(){
        hotelLocationsList.clear();
        int i=0;
        while (i < filterHotelLocationsList.size()){
            if(filterHotelLocationsList.get(i).getSelected()){
                hotelLocationsList.add(filterHotelLocationsList.get(i));
            }
            i++;
        }

        hotelFilterNotify.hotelFilter(minPrice, maxPrice, filterAmenitiesList, starValue,hotelLocationsList);
        getActivity().onBackPressed();
    }

    @OnClick(R.id.clear_filter)
    void clearFilter()
    {
        minPrice = null;
        maxPrice = null;
        filterAmenitiesList.clear();
        hotelLocationsList.clear();
        filterHotelLocationsList.clear();
        starValue.clear();
        hotelFilterNotify.hotelFilter(minPrice, maxPrice, filterAmenitiesList, starValue,hotelLocationsList);
        getActivity().onBackPressed();
    }

    List<Integer> starValue = new ArrayList<Integer>();
    List<Integer> filterStarValue = new ArrayList<Integer>();
    List<AmenitiesModel> amenitiesList = new ArrayList<AmenitiesModel>();
    List<AmenitiesModel> filterAmenitiesList = new ArrayList<AmenitiesModel>();
    List<LocationModel> hotelLocationsList = new ArrayList<LocationModel>();
    List<LocationModel> filterHotelLocationsList = new ArrayList<LocationModel>();
    AmenitiesAdapter amenitiesAdapter;
    HotelLocationAdapter locationAdapter;
    String minPrice = null, maxPrice = null,fix_hotelMinPrice,fix_hotelMaxPrice;
    HotelFilterNotify hotelFilterNotify;

    @SuppressLint("ValidFragment")
    public HotelFilterFragment(String fix_hotelMinPrice,
                               String fix_hotelMaxPrice,
                               String hotelMinPrice,
                               String hotelMaxPrice,
                               List<AmenitiesModel> filterAmenities,
                               List<Integer> filterStarHotel,
                               List<LocationModel> hotelLocations,
                               List<LocationModel> filterHotelLocations,
                               Object object)
    {
        hotelLocationsList.clear();
        filterHotelLocationsList.clear();
        filterAmenitiesList.clear();
        filterStarValue.clear();
        this.fix_hotelMinPrice = fix_hotelMinPrice;
        this.fix_hotelMaxPrice = fix_hotelMaxPrice;
        this.minPrice = hotelMinPrice;
        this.maxPrice = hotelMaxPrice;
        this.filterStarValue.addAll(filterStarHotel);
        this.filterAmenitiesList.addAll(filterAmenities);
        this.hotelLocationsList.addAll(hotelLocations);
        this.filterHotelLocationsList.addAll(filterHotelLocations);
        hotelFilterNotify = (HotelFilterNotify) object;

        int i=0;
        Boolean isBreakfast = false, isParking = false,
                isWifi = false, isPool = false;
        while (i < filterAmenities.size()){
            if(filterAmenities.get(i).getAmenityName().equals("breakfast")){
                isBreakfast = true;
            }

            if(filterAmenities.get(i).getAmenityName().equals("parking")){
                isParking = true;
            }

            if(filterAmenities.get(i).getAmenityName().equals("wireless")){
                isWifi = true;
            }

            if(filterAmenities.get(i).getAmenityName().equals("pool")){
                isPool = true;
            }
            i++;
        }

        addMenities("breakfast", isBreakfast);
        addMenities("parking", isParking);
        addMenities("wireless", isWifi);
        addMenities("pool", isPool);
    }

    public HotelFilterFragment() {
        /*
        * empty constructor
        * */
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.hotel_filter_fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rangeSeekBar.setRangeValues(Math.round(Float.parseFloat(fix_hotelMinPrice)),Math.round(Float.parseFloat(fix_hotelMaxPrice)));
      /*  priceValue.setText(getActivity().getResources().getString(R.string.Rs)
                +" "+String.valueOf(fix_hotelMinPrice)+" - "+String.valueOf(fix_hotelMaxPrice));
*/
        priceValue.setText(Global.currencySymbol
                +" "+String.valueOf(fix_hotelMinPrice)+" - "+String.valueOf(fix_hotelMaxPrice));

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>(){

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValue, Integer maxValue) {
              /*  priceValue.setText(getActivity().getResources().getString(R.string.Rs)
                        +" "+String.valueOf(minValue)+" - "+String.valueOf(maxValue));*/
                priceValue.setText(Global.currencySymbol
                        +" "+String.valueOf(minValue)+" - "+String.valueOf(maxValue));
                minPrice = String.valueOf(minValue);
                maxPrice = String.valueOf(maxValue);
            }

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar,
                                                    int minValue, int maxValue) {
                /*priceValue.setText(getActivity().getResources().getString(R.string.Rs)
                        +" "+String.valueOf(minValue)+" - "+String.valueOf(maxValue));*/
                priceValue.setText(Global.currencySymbol
                        +" "+String.valueOf(minValue)+" - "+String.valueOf(maxValue));
                minPrice = String.valueOf(minValue);
                maxPrice = String.valueOf(maxValue);
            }
        });

        if(filterStarValue.size() != 0){
            int i=0;
            while (i < filterStarValue.size()){
                if(filterStarValue.get(i) == 1){
                    oneStar();
                }

                if(filterStarValue.get(i) == 2){
                    twoStar();
                }

                if(filterStarValue.get(i) == 3){
                    threeStar();
                }

                if(filterStarValue.get(i) == 4){
                    fourStar();
                }

                if(filterStarValue.get(i) == 5){
                    fiveStar();
                }

                i++;
            }
        }
        commonUtils.linearLayout(amenityListView, getActivity());
        commonUtils.linearLayout(location_list, getActivity());

        //Notify Adapter
        amenitiesAdapter = new AmenitiesAdapter(getActivity(), HotelFilterFragment.this, amenitiesList);
        amenityListView.setAdapter(amenitiesAdapter);

        locationAdapter = new HotelLocationAdapter(getActivity(), HotelFilterFragment.this, hotelLocationsList);
        location_list.setAdapter(locationAdapter);
        loadData();

    }


    private void loadData()
    {
        Thread thread = new Thread(){
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                super.run();
                if(minPrice != null){
                    priceValue.setText(Global.currencySymbol
                            +" "+minPrice+" - "+maxPrice);
                }else {
                    priceValue.setText(Global.currencySymbol
                            +" "+fix_hotelMinPrice+" - "+fix_hotelMaxPrice);
                }

                if(minPrice != null)
                {
                    rangeSeekBar.setSelectedMinValue(Math.round(Integer.parseInt(minPrice)));
                }

                if(maxPrice!=null)
                {
                    rangeSeekBar.setSelectedMaxValue(Math.round(Integer.parseInt(maxPrice)));
                }
                int i=0;
                if(hotelLocationsList.size() != 0)
                {
                    while (i < filterHotelLocationsList.size())
                    {
                        int j=0;
                        Boolean isSelected = false;
                        while (j < hotelLocationsList.size())
                        {
                            if(hotelLocationsList.get(j).getLocationName().equalsIgnoreCase(filterHotelLocationsList.get(i).getLocationName()))
                            {
                                isSelected = true;
                                break;
                            }
                            j++;
                        }
                        if(isSelected){
                            hotelLocationsList.add(new LocationModel(filterHotelLocationsList.get(i).getLocationName(), true));
                        }else {
                            hotelLocationsList.add(new LocationModel(filterHotelLocationsList.get(i).getLocationName(), false));
                        }
                        i++;
                    }
                }else {
                    while (i < filterHotelLocationsList.size()){
                        hotelLocationsList.add(new LocationModel(filterHotelLocationsList.get(i).getLocationName(), false));
                        i++;
                    }
                }

                locationAdapter.notifyDataSetChanged();






            }
        };
        thread.start();
    }

    private void addMenities(String value, boolean b)
    {
        amenitiesList.add(new AmenitiesModel(value, b));
    }

    public void notifyAction(int position, Boolean selected, String amenityName) {
        if(selected){
            amenitiesList.get(position).setSelected(false);
        }else {
            amenitiesList.get(position).setSelected(true);
        }

        amenitiesAdapter.notifyDataSetChanged();

        if(filterAmenitiesList.size() != 0){
            if(!selected){
                filterAmenitiesList.add(new AmenitiesModel(amenityName, true));
            }else {
                int i=0;
                while (i<filterAmenitiesList.size()){
                    if(filterAmenitiesList.get(i).getAmenityName().equals(amenityName)){
                        filterAmenitiesList.remove(i);
                        break;
                    }
                    i++;
                }
            }
        }else {
            filterAmenitiesList.add(new AmenitiesModel(amenityName, true));
        }
    }

    public void notifyLocationAction(int position, Boolean selected, String locationName)
    {
        if(selected){
            hotelLocationsList.get(position).setSelected(false);
        }else {
            hotelLocationsList.get(position).setSelected(true);
        }
        locationAdapter.notifyDataSetChanged();

        if(filterHotelLocationsList.size() != 0){
            if(!selected){
                filterHotelLocationsList.add(new LocationModel(locationName, true));
            }else {
                int i=0;
                while (i<filterHotelLocationsList.size()){
                    if(filterHotelLocationsList.get(i).getLocationName().equalsIgnoreCase(locationName)){
                        filterHotelLocationsList.remove(i);
                        break;
                    }
                    i++;
                }
            }
        }else {
            filterHotelLocationsList.add(new LocationModel(locationName, true));
        }



    }

}