package com.TripToEasy.flight.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.main.activity.MainActivity;
import com.TripToEasy.flight.adapter.FlightListAdapter;
import com.TripToEasy.interfaces.FilterNotify;
import com.TripToEasy.model.parsingModel.flight.FlightList;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ptblr-1195 on 13/3/18.
 */

/**
 * NOTE : Filter priority
 *
 * 1. Flights
 * 2. Min and max price
 * 3. no. of stops ( 0 - 1 - 2 )
 * 4. Departure Time ( 6 - 12 - 18 - 24 )
 * 5. Arrival Time ( 6 - 12 - 18 - 24 )
 *
 * */
public class FilterFragment extends BaseFragment {

    @BindView(R.id.price_bar)
    CrystalRangeSeekbar priceBar;

    @BindView(R.id.price_value)
    TextView priceValue;

    @BindView(R.id.flight_list)
    RecyclerView flightListView;

    @BindView(R.id.zero_stop)
    TextView zeroStop;

    @BindView(R.id.one_stop)
    TextView oneStop;

    @BindView(R.id.two_plus_stop)
    TextView twoPlusStop;

    @BindView(R.id.dep_time_one)
    TextView depOne;

    @BindView(R.id.dep_time_two)
    TextView depTwo;

    @BindView(R.id.dep_time_three)
    TextView depThree;

    @BindView(R.id.dep_time_four)
    TextView depFour;

    @BindView(R.id.arri_time_one)
    TextView arriOne;

    @BindView(R.id.arri_time_two)
    TextView arriTwo;

    @BindView(R.id.arri_time_three)
    TextView arriThree;

    @BindView(R.id.arri_time_four)
    TextView arriFour;

    @BindView(R.id.image_one)
    ImageView imageOne;

    @BindView(R.id.image_two)
    ImageView imageTwo;

    @BindView(R.id.image_three)
    ImageView imageThree;

    @BindView(R.id.image_four)
    ImageView imageFour;

    @BindView(R.id.image_five)
    ImageView imageFive;

    @BindView(R.id.image_six)
    ImageView imageSix;

    @BindView(R.id.image_seven)
    ImageView imageSeven;

    @BindView(R.id.image_eight)
    ImageView imageEight;

    @BindView(R.id.dep_early_layout)
    LinearLayout depEarlyLayout;

    @BindView(R.id.dep_morning_layout)
    LinearLayout depMorningLayout;

    @BindView(R.id.dep_noon_layout)
    LinearLayout depNoonLayout;

    @BindView(R.id.dep_evening_layout)
    LinearLayout depEveningLayout;

    @BindView(R.id.arri_early_layout)
    LinearLayout arriEarlyLayout;

    @BindView(R.id.arri_morning_layout)
    LinearLayout arriMorningLayout;

    @BindView(R.id.arri_noon_layout)
    LinearLayout arriNoonLayout;

    @BindView(R.id.arri_evening_layout)
    LinearLayout arriEveningLayout;

    @BindView(R.id.stop_layout)
    LinearLayout stopLayout;

    @BindView(R.id.departure_layout)
    LinearLayout departureLayout;

    @BindView(R.id.arrival_layout)
    LinearLayout arrivalLayout;

    @OnClick(R.id.apply_filter)
    void applyFilter(){
        preFilter.clear();
        int i=0;
        while (i < flightLists.size()){
            if(flightLists.get(i).getSelected()){
                preFilter.add(flightLists.get(i));
            }
            i++;
        }

        if(fromFragment){
            filterNotify.filterTypeOne(preFilter,stopList, departureList,
                    arrivalList,minValue, maxValue);
        }else {
            filterNotify.filterTypeOne(preFilter, stopList, departureList,
                    arrivalList, minValue, maxValue);
        }

        getActivity().onBackPressed();
    }

    @OnClick(R.id.exit_filter)
    void exitFilter(){
        preFilter.clear();
        stopList.clear();
        departureList.clear();
        arrivalList.clear();
        minValue = null;
        maxValue = null;
        if(fromFragment){
            filterNotify.filterTypeOne(preFilter,stopList, departureList,
                    arrivalList, minValue, maxValue);
        }else {
            filterNotify.filterTypeOne(preFilter, stopList, departureList,
                    arrivalList, minValue, maxValue);
        }
        getActivity().onBackPressed();
    }

    /**
     * 1 ID : 2131362173 -> 1
     *        2131361961 -> 1
     *        2131361859 -> 1
     *        2131362006 -> 1
     *
     * 2 ID : 2131362172 -> 2
     *        2131361964 -> 2
     *        2131361862 -> 2
     *        2131362249 -> 2
     * */
    @OnClick(R.id.apply_sort)
    void exitSort(){
        /*int priceId = priceId();
        int departureId = departureId();
        int arrivalId = arrivalId();
        int durationId = durationId();
*/
       /* if(priceId != -1){
            if(priceId == 2131362173){
                priceId = 1;
            }else {
                priceId = 2;
            }
        }

        if(departureId != -1){
            if(departureId == 2131361961){
                departureId = 1;
            }else {
                departureId = 2;
            }
        }

        if(arrivalId != -1){
            if(arrivalId == 2131361859){
                arrivalId = 1;
            }else {
                arrivalId = 2;
            }
        }

        if(durationId != -1){
            if(durationId == 2131362006){
                durationId = 1;
            }else {
                durationId = 2;
            }
        }

        filterNotify.sortingNotify(priceId, departureId, arrivalId, durationId);
        getActivity().onBackPressed();*/
    }

    @OnClick(R.id.exit_sort)
    void exitSortView(){
        exitFilter();
    }

    @OnClick(R.id.zero_stop)
    void zeroStop(){

        if(stopList.size() != 0){
            if(stopList.contains(0)){
                position = stopList.indexOf(0);
                stopList.remove(position);
                setTypeView(1);
            }else {
                stopList.add(0);
                setTypeView(0);
            }
        }else {
            stopList.add(0);
            setTypeView(0);
        }

    }

    @SuppressLint("NewApi")
    private void setTypeView(int i) {
        switch (i){
            case 0:
                zeroStop.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                zeroStop.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                break;
            case 1:
                zeroStop.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                zeroStop.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                break;
            case 2:
                oneStop.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                oneStop.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                break;
            case 3:
                oneStop.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                oneStop.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                break;
            case 4:
                twoPlusStop.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                twoPlusStop.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                break;
            case 5:
                twoPlusStop.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                twoPlusStop.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                break;
        }
    }

    @OnClick(R.id.one_stop)
    void oneStop(){

        if(stopList.size() != 0){
            if(stopList.contains(1)){
                position = stopList.indexOf(1);
                stopList.remove(position);
                setTypeView(3);
            }else {
                stopList.add(1);
                setTypeView(2);
            }
        }else {
            stopList.add(1);
            setTypeView(2);
        }

    }

    @OnClick(R.id.two_plus_stop)
    void twoPlusStop(){

        if(stopList.size() != 0){
            if(stopList.contains(2)){
                position = stopList.indexOf(2);
                stopList.remove(position);
                setTypeView(5);
            }else {
                stopList.add(2);
                setTypeView(4);
            }
        }else {
            stopList.add(2);
            setTypeView(4);
        }

    }

    @SuppressLint("NewApi")
    @OnClick(R.id.dep_early_layout)
    void depTimeOne(){

        if(departureList.size() != 0){
            if(departureList.contains(0)){
                position = departureList.indexOf(0);
                departureList.remove(position);
                setDepartureView(1);
            }else {
                departureList.add(0);
                setDepartureView(0);
            }
        }else {
            departureList.add(0);
            setDepartureView(0);
        }

    }

    @SuppressLint("NewApi")
    private void setDepartureView(int i) {
        switch (i){
            case 0:
                depEarlyLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                depOne.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageOne.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_early_selected));
                break;
            case 1:
                depEarlyLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                depOne.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageOne.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_early_not_selected));
                break;
            case 2:
                depMorningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                depTwo.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageTwo.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_morning_selected));
                break;
            case 3:
                depMorningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                depTwo.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageTwo.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_morning_not_selected));
                break;
            case 4:
                depNoonLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                depThree.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageThree.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_noon_selected));
                break;
            case 5:
                depNoonLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                depThree.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageThree.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_noon_not_selected));
                break;
            case 6:
                depEveningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                depFour.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageFour.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_evening_selected));
                break;
            case 7:
                depEveningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                depFour.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageFour.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_evening_not_selected));
                break;
        }
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.dep_morning_layout)
    void depTimeTwo(){

        if(departureList.size() != 0){
            if(departureList.contains(6)){
                position = departureList.indexOf(6);
                departureList.remove(position);
                setDepartureView(3);
            }else {
                departureList.add(6);
                setDepartureView(2);
            }
        }else {
            departureList.add(6);
            setDepartureView(2);
        }

    }

    @SuppressLint("NewApi")
    @OnClick(R.id.dep_noon_layout)
    void depTimeThree(){

        if(departureList.size() != 0){
            if(departureList.contains(12)){
                position = departureList.indexOf(12);
                departureList.remove(position);
                setDepartureView(5);
            }else {
                departureList.add(12);
                setDepartureView(4);
            }
        }else {
            departureList.add(12);
            setDepartureView(4);
        }

    }

    @SuppressLint("NewApi")
    @OnClick(R.id.dep_evening_layout)
    void depTimeFour(){

        if(departureList.size() != 0){
            if(departureList.contains(18)){
                position = departureList.indexOf(18);
                departureList.remove(position);
                setDepartureView(7);
            }else {
                departureList.add(18);
                setDepartureView(6);
            }
        }else {
            departureList.add(18);
            setDepartureView(6);
        }

    }

    @SuppressLint("NewApi")
    @OnClick(R.id.arri_early_layout)
    void arriTimeOne(){

        if(arrivalList.size() != 0){
            if(arrivalList.contains(0)){
                position = arrivalList.indexOf(0);
                arrivalList.remove(position);
                setArrivalView(1);
            }else {
                arrivalList.add(0);
                setArrivalView(0);
            }
        }else {
            arrivalList.add(0);
            setArrivalView(0);
        }

    }

    @SuppressLint("NewApi")
    private void setArrivalView(int i) {
        switch (i){
            case 0:
                arriEarlyLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                arriOne.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageFive.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_early_selected));
                break;
            case 1:
                arriEarlyLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                arriOne.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageFive.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_early_not_selected));
                break;
            case 2:
                arriMorningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                arriTwo.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageSix.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_morning_selected));
                break;
            case 3:
                arriMorningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                arriTwo.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageSix.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_morning_not_selected));
                break;
            case 4:
                arriNoonLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                arriThree.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageSeven.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_noon_selected));
                break;
            case 5:
                arriNoonLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                arriThree.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageSeven.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_noon_not_selected));
                break;
            case 6:
                arriEveningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                arriFour.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                imageEight.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_evening_selected));
                break;
            case 7:
                arriEveningLayout.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                arriFour.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                imageEight.setImageDrawable(getActivity().getResources()
                        .getDrawable(R.drawable.ic_evening_not_selected));
                break;
        }
    }

    @SuppressLint("NewApi")
    @OnClick(R.id.arri_morning_layout)
    void arriTimeTwo(){

        if(arrivalList.size() != 0){
            if(arrivalList.contains(6)){
                position = arrivalList.indexOf(6);
                arrivalList.remove(position);
                setArrivalView(3);
            }else {
                arrivalList.add(6);
                setArrivalView(2);
            }
        }else {
            arrivalList.add(6);
            setArrivalView(2);
        }

    }

    @SuppressLint("NewApi")
    @OnClick(R.id.arri_noon_layout)
    void arriTimeThree(){

        if(arrivalList.size() != 0){
            if(arrivalList.contains(12)){
                position = arrivalList.indexOf(12);
                arrivalList.remove(position);
                setArrivalView(5);
            }else {
                arrivalList.add(12);
                setArrivalView(4);
            }
        }else {
            arrivalList.add(12);
            setArrivalView(4);
        }

    }

    @SuppressLint("NewApi")
    @OnClick(R.id.arri_evening_layout)
    void arriTimeFour(){

        if(arrivalList.size() != 0){
            if(arrivalList.contains(18)){
                position = arrivalList.indexOf(18);
                arrivalList.remove(position);
                setArrivalView(7);
            }else {
                arrivalList.add(18);
                setArrivalView(6);
            }
        }else {
            arrivalList.add(18);
            setArrivalView(6);
        }

    }

    @BindView(R.id.range_bar)
    RangeSeekBar rangeSeekBar;

    @BindView(R.id.filter_layout)
    RelativeLayout filterLayout;

    @BindView(R.id.sort_layout)
    LinearLayout sortLayout;

    @BindView(R.id.filter_view)
    View filterView;

    @BindView(R.id.sort_view)
    View sortView;

    @OnClick(R.id.filter_action)
    void filterAction(){
        filterLayout.setVisibility(View.VISIBLE);
        sortLayout.setVisibility(View.GONE);
        filterView.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        sortView.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
    }

    @OnClick(R.id.sort_action)
    void sortAction(){
        sortLayout.setVisibility(View.VISIBLE);
        filterLayout.setVisibility(View.GONE);
        sortView.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
        filterView.setBackgroundColor(getActivity().getResources().getColor(android.R.color.transparent));
    }

    @BindView(R.id.price_group)
    RadioGroup priceGroup;

   /* @BindView(R.id.departure_group)
    RadioGroup departureGroup;

    @BindView(R.id.arrival_group)
    RadioGroup arrivalGroup;

    @BindView(R.id.duration_group)
    RadioGroup durationGroup;*/

    @SuppressLint("ValidFragment")
    public FilterFragment(String airlineList,
                          String priceBreakup,
                          Object object,
                          List<FlightList> flightLists,
                          String minPrice,
                          String maxPrice,
                          List<Integer> stopList,
                          List<Integer> departureList,
                          List<Integer> arrivalList,
                          Boolean fromFragment) {
        this.airlineList = airlineList;
        this.priceBreakup = priceBreakup;
        filterNotify = (FilterNotify)object;
        minValue = minPrice;
        maxValue = maxPrice;
        this.preFilter.addAll(flightLists);
        this.stopList.addAll(stopList);
        this.departureList.addAll(departureList);
        this.arrivalList.addAll(arrivalList);
        this.fromFragment = fromFragment;
    }

    public FilterFragment(){
        /**
         * Empty Constructor
         * */
    }

    String airlineList, priceBreakup, minValue = null, maxValue = null;
    int position;
    Boolean fromFragment;

    FilterNotify filterNotify;
    List<FlightList> flightLists = new ArrayList<FlightList>();
    List<FlightList> preFilter = new ArrayList<FlightList>();
    List<Integer> stopList = new ArrayList<Integer>();
    List<Integer> departureList = new ArrayList<Integer>();
    List<Integer> arrivalList = new ArrayList<Integer>();

    FlightListAdapter flightListAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.filter_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(fromFragment){
            stopLayout.setVisibility(View.VISIBLE);
            departureLayout.setVisibility(View.VISIBLE);
            arrivalLayout.setVisibility(View.VISIBLE);
        }else {
            stopLayout.setVisibility(View.VISIBLE);
            departureLayout.setVisibility(View.VISIBLE);
            arrivalLayout.setVisibility(View.VISIBLE);
        }

        commonUtils.linearLayout(flightListView,getActivity());
        flightListAdapter = new FlightListAdapter(getActivity(),this,flightLists);
        flightListView.setAdapter(flightListAdapter);

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>(){

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar,
                                                    Integer minValuePrice,
                                                    Integer maxValuePrice) {
                /*priceValue.setText(getActivity().getResources().getString(R.string.Rs)
                        +" "+String.valueOf(minValuePrice)+" - "+String.valueOf(maxValuePrice));
                */
                priceValue.setText(Global.currencySymbol
                        +" "+String.valueOf(minValuePrice)+" - "+String.valueOf(maxValuePrice));
                minValue = String.valueOf(minValuePrice);
                maxValue = String.valueOf(maxValuePrice);
            }

            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar,
                                                    int minValuePrice,
                                                    int maxValuePrice) {
                /*priceValue.setText(getActivity().getResources().getString(R.string.Rs)
                        +" "+String.valueOf(minValuePrice)+" - "+String.valueOf(maxValuePrice));
                */
                priceValue.setText(Global.currencySymbol
                        +" "+String.valueOf(minValuePrice)+" - "+String.valueOf(maxValuePrice));

                minValue = String.valueOf(minValuePrice);
                maxValue = String.valueOf(maxValuePrice);
            }
        });

        if(stopList.size() != 0){
            if(stopList.contains(0)){
                setTypeView(0);
            }

            if(stopList.contains(1)){
                setTypeView(2);
            }

            if(stopList.contains(2)){
                setTypeView(4);
            }
        }

        if(departureList.size() != 0){
            if(departureList.contains(0)){
                setDepartureView(0);
            }

            if(departureList.contains(6)){
                setDepartureView(2);
            }

            if(departureList.contains(12)){
                setDepartureView(4);
            }

            if(departureList.contains(18)){
                setDepartureView(6);
            }
        }

        if(arrivalList.size() != 0){
            if(arrivalList.contains(0)){
                setArrivalView(0);
            }

            if(arrivalList.contains(6)){
                setArrivalView(2);
            }

            if(arrivalList.contains(12)){
                setArrivalView(4);
            }

            if(arrivalList.contains(18)){
                setArrivalView(6);
            }
        }

        loadData();
    }

    private void loadData() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    JSONObject priceObj = new JSONObject(priceBreakup);
                    //String minVal = String.valueOf(priceObj.getDouble("min"));
                    String minVal = String.format("%.2f",(priceObj.getDouble("min")/Double.parseDouble(Global.currencyValue)));

                    // String maxVal = String.valueOf(priceObj.getDouble("max"));
                    String maxVal = String.format("%.2f",(priceObj.getDouble("max")/Double.parseDouble(Global.currencyValue)));

                    //priceBar.setMinValue(Float.parseFloat(minVal));
                    //priceBar.setMaxValue(Float.parseFloat(maxVal));
                    //Integer finalMin = Integer.parseInt(minVal);
                    //Integer finalMax = Integer.parseInt(maxVal);

                    rangeSeekBar.setRangeValues(
                            Math.round(Float.parseFloat(minVal)),
                            Math.round(Float.parseFloat(maxVal)));

                    /*if(minValue != null && maxValue != null){
                        priceBar.setMinStartValue(Float.parseFloat(minValue));
                        priceBar.setMaxStartValue(Float.parseFloat(maxValue));
                    }*/

                    JSONArray flightArray = new JSONArray(airlineList);
                    int i=0;
                    if(preFilter.size() != 0)
                    {
                        while (i < flightArray.length())
                        {
                            JSONObject flightObj = flightArray.getJSONObject(i);
                            int j=0;
                            Boolean isSelected = false;
                            while (j < preFilter.size())
                            {
                                if(preFilter.get(j).getAirlineCode()
                                        .equals(flightObj.getString("code")))
                                {
                                    isSelected = true;
                                    break;
                                }
                                j++;
                            }
                            if(isSelected){
                                flightLists.add(new FlightList(flightObj.getString("name"),
                                        flightObj.getString("code"), true));
                            }else {
                                flightLists.add(new FlightList(flightObj.getString("name"),
                                        flightObj.getString("code"), false));
                            }
                            i++;
                        }
                    }else {
                        while (i < flightArray.length()){
                            JSONObject flightObj = flightArray.getJSONObject(i);
                            flightLists.add(new FlightList(flightObj.getString("name"),
                                    flightObj.getString("code"), false));
                            i++;
                        }
                    }

                    if(minValue != null){
                        priceValue.setText(Global.currencySymbol
                                +" "+minValue+" - "+maxValue);
                    }else {
                        priceValue.setText(Global.currencySymbol
                                +" "+minVal+" - "+maxVal);
                    }

                    if(minValue != null){
                        rangeSeekBar.setSelectedMinValue(Math.round(Integer.parseInt(minValue)));
                    }

                    if(maxValue != null){
                        rangeSeekBar.setSelectedMaxValue(Math.round(Integer.parseInt(maxValue)));
                    }

                    flightListAdapter.notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void notifyFlight(int position, Boolean selected) {
        if(selected){
            flightLists.get(position).setSelected(false);
        }else {
            flightLists.get(position).setSelected(true);
        }

        flightListAdapter.notifyDataSetChanged();
    }

    /**
     * Sorting Methods
     * */
    /*int priceId(){
        int selectionValue = priceGroup.getCheckedRadioButtonId();
        return selectionValue;
    }

    int departureId(){
        int selectedValue = departureGroup.getCheckedRadioButtonId();
        return selectedValue;
    }

    int arrivalId(){
        int selectedValue = arrivalGroup.getCheckedRadioButtonId();
        return selectedValue;
    }

    int durationId(){
        int selectedValue = durationGroup.getCheckedRadioButtonId();
        return selectedValue;
    }*/

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).hideToolBar(2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).hideToolBar( 1);
    }


}