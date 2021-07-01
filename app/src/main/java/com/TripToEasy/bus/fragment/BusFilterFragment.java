package com.TripToEasy.bus.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.TripToEasy.R;
import com.TripToEasy.bus.adapter.BusOperatorAdapter;
import com.TripToEasy.interfaces.FilterNotify;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.bus.BusOperatorsInfo;
import com.TripToEasy.utils.Global;
import com.TripToEasy.utils.RangeSeekBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusFilterFragment extends BaseFragment {

    @BindView(R.id.rg_bustype)
    RadioGroup rg_bustype;

    @BindView(R.id.rb_ac_seater)
    RadioButton rb_ac_seater;

    @BindView(R.id.rb_nonac_seater)
    RadioButton rb_nonac_seater;

    @BindView(R.id.rb_ac_sleeper)
    RadioButton rb_ac_sleeper;

    @BindView(R.id.rb_nonac_sleeper)
    RadioButton rb_nonac_sleeper;


    @BindView(R.id.price_bar)
    CrystalRangeSeekbar priceBar;

    @BindView(R.id.range_bar)
    RangeSeekBar rangeSeekBar;

    @BindView(R.id.price_value)
    TextView priceValue;

    @BindView(R.id.seater)
    TextView tv_seater;

    @BindView(R.id.sleeper)
    TextView tv_sleeper;

    @BindView(R.id.non_ac)
    TextView tv_non_ac;

    @BindView(R.id.ac)
    TextView tv_ac;


    @BindView(R.id.bus_opr_list)
    RecyclerView oprListView;

    @BindView(R.id.price_group)
    RadioGroup priceGroup;

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

    public BusFilterFragment() {
        // Required empty public constructor
    }

    String busSeatType;
    @SuppressLint("ValidFragment")
    public BusFilterFragment(
                          String operatorsListString,
                          ArrayList<BusOperatorsInfo> busOprList,
                          String priceBreakup,
                          Object object,
                          String minPrice,
                          String maxPrice,
                          List<Integer> departureList,
                          List<Integer> arrivalList,
                          Boolean fromFragment,
                          String busType) {
        this.operatorsListString=operatorsListString;
       // this.busOprList=busOprList;
        this.priceBreakup = priceBreakup;
        filterNotify = (FilterNotify)object;
        this.minValue = minPrice;
        this.maxValue = maxPrice;
        this.preFilter.addAll(busOprList);
        this.departureList.addAll(departureList);
        this.arrivalList.addAll(arrivalList);
        this.fromFragment = fromFragment;
        this.busSeatType = busType;
    }

    String  operatorsListString,priceBreakup, minValue = null, maxValue = null;
    int position;
    Boolean fromFragment;

    FilterNotify filterNotify;
    List<BusOperatorsInfo> busOprList = new ArrayList<BusOperatorsInfo>();
    List<BusOperatorsInfo> preFilter = new ArrayList<BusOperatorsInfo>();
    List<Integer> departureList = new ArrayList<Integer>();
    List<Integer> arrivalList = new ArrayList<Integer>();
    List<Integer> busTypeList = new ArrayList<Integer>();


    BusOperatorAdapter opratorListAdapter;
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_bus_filter;
    }

    @OnClick(R.id.apply_filter)
    void applyFilter(){

        if(rb_ac_seater.isChecked()){
            busSeatType="AC_SEATER";
        }else if(rb_nonac_seater.isChecked()){
            busSeatType="NON_AC_SEATER";
        }else if(rb_ac_sleeper.isChecked()){
            busSeatType="AC_SLEEPER";
        }else if(rb_nonac_sleeper.isChecked()){
            busSeatType="NON_AC_SLEEPER";
        }else {
            busSeatType="NONE";
        }

        preFilter.clear();
        int i=0;
        while (i < busOprList.size()){
            if(busOprList.get(i).getSelected()){
                preFilter.add(busOprList.get(i));
            }
            i++;
        }

      /*  if(fromFragment){
            filterNotify.filterTypeTwo(preFilter, minValue, maxValue);
        }else {
            filterNotify.filterTypeOne(preFilter, busOprList, departureList,
                    arrivalList, minValue, maxValue);
        }*/

        filterNotify.filterTypeBus(preFilter,departureList,arrivalList,minValue,maxValue,busSeatType);
        getActivity().onBackPressed();
    }

    @OnClick(R.id.exit_filter)
    void exitFilter(){
        preFilter.clear();
        busOprList.clear();
        departureList.clear();
        arrivalList.clear();
        minValue = null;
        maxValue = null;
       /* if(fromFragment){
            filterNotify.filterTypeTwo(preFilter, minValue, maxValue);
        }else {
            filterNotify.filterTypeOne(preFilter, stopList, departureList,
                    arrivalList, minValue, maxValue);
        }*/
        filterNotify.filterTypeBus(preFilter,departureList,arrivalList,minValue,maxValue,busSeatType);
        getActivity().onBackPressed();
    }


    @OnClick(R.id.ac)
    void busAc(){

        if(busTypeList.size() != 0){
            if(busTypeList.contains(0)){
                position = busTypeList.indexOf(0);
                busTypeList.remove(position);
                setTypeView(1);
            }else {
                busTypeList.add(0);
                setTypeView(0);
            }
        }else {
            busTypeList.add(0);
            setTypeView(0);
        }

    }

    @OnClick(R.id.non_ac)
    void busNonAc(){

        if(busTypeList.size() != 0){
            if(busTypeList.contains(1)){
                position = busTypeList.indexOf(1);
                busTypeList.remove(position);
                setTypeView(3);
            }else {
                busTypeList.add(1);
                setTypeView(2);
            }
        }else {
            busTypeList.add(1);
            setTypeView(2);
        }

    }

    @OnClick(R.id.seater)
    void busTypeSeater(){

        if(busTypeList.size() != 0){
            if(busTypeList.contains(2)){
                position = busTypeList.indexOf(2);
                busTypeList.remove(position);
                setTypeView(5);
            }else {
                busTypeList.add(2);
                setTypeView(4);
            }
        }else {
            busTypeList.add(2);
            setTypeView(4);
        }

    }

    @OnClick(R.id.sleeper)
    void busTypeSleeper(){

        if(busTypeList.size() != 0){
            if(busTypeList.contains(3)){
                position = busTypeList.indexOf(3);
                busTypeList.remove(position);
                setTypeView(7);
            }else {
                busTypeList.add(3);
                setTypeView(4);
            }
        }else {
            busTypeList.add(3);
            setTypeView(6);
        }

    }

    @SuppressLint("NewApi")
    private void setTypeView(int i) {
        switch (i){
            case 0:
                tv_ac.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                tv_ac.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                break;
            case 1:
                tv_ac.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                tv_ac.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                break;
            case 2:
                tv_non_ac.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                tv_non_ac.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                break;
            case 3:
                tv_non_ac.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                tv_non_ac.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                break;
            case 4:
                tv_seater.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                tv_seater.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                break;
            case 5:
                tv_seater.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                tv_seater.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                break;

            case 6:
                tv_sleeper.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.blue_butten));
                tv_sleeper.setTextColor(getActivity().getResources()
                        .getColor(R.color.white));
                break;
            case 7:
                tv_sleeper.setBackground(getActivity().getResources()
                        .getDrawable(R.drawable.white_butten));
                tv_sleeper.setTextColor(getActivity().getResources()
                        .getColor(R.color.black_sixty));
                break;
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


    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bus_filter, container, false);
    }
*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(oprListView,getActivity());
        opratorListAdapter = new BusOperatorAdapter(getActivity(),this,busOprList);
        oprListView.setAdapter(opratorListAdapter);


        if(busSeatType!=null)
        {
            if(busSeatType.equals("AC_SEATER"))
            {
                rb_ac_seater.setChecked(true);
            }else if(busSeatType.equals("NON_AC_SEATER"))
            {
                rb_nonac_seater.setChecked(true);
            }else if(busSeatType.equals("AC_SLEEPER"))
            {
                rb_ac_sleeper.setChecked(true);
            }else if(busSeatType.equals("NON_AC_SLEEPER"))
            {
                rb_nonac_sleeper.setChecked(true);
            }
        }

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

        /*if(busTypeList.size() != 0){
            if(busTypeList.contains(0)){
                setTypeView(0);
            }

            if(busTypeList.contains(1)){
                setTypeView(2);
            }

            if(busTypeList.contains(2)){
                setTypeView(4);
            }
            if(busTypeList.contains(3)){
                setTypeView(6);
            }
        }*/

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
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                try{
                    JSONObject priceObj = new JSONObject(priceBreakup);
                    //String minVal = String.valueOf(priceObj.getDouble("min"));
                    String minVal = String.format("%.2f",(priceObj.getDouble("min")/Double.parseDouble(Global.currencyValue)));

                    //String maxVal = String.valueOf(priceObj.getDouble("max"));
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

                    JSONArray flightArray = new JSONArray(operatorsListString);
                    int i=0;
                    if(preFilter.size() != 0){
                        while (i < flightArray.length()){
                            JSONObject flightObj = flightArray.getJSONObject(i);
                            int j=0;
                            Boolean isSelected = false;
                            while (j < preFilter.size()){
                                if(preFilter.get(j).getOperatorCode()
                                        .equals(flightObj.getString("code"))){
                                    isSelected = true;
                                    break;
                                }
                                j++;
                            }
                            if(isSelected){
                                busOprList.add(new BusOperatorsInfo(flightObj.getString("name"),
                                        flightObj.getString("code"), true));
                            }else {
                                busOprList.add(new BusOperatorsInfo(flightObj.getString("name"),
                                        flightObj.getString("code"), false));
                            }
                            i++;
                        }
                    }else {
                        while (i < flightArray.length()){
                            JSONObject flightObj = flightArray.getJSONObject(i);
                            busOprList.add(new BusOperatorsInfo(flightObj.getString("name"),
                                    flightObj.getString("code"), false));
                            i++;
                        }
                    }

                    /*if(minValue != null){
                        priceValue.setText(getActivity().getResources().getString(R.string.Rs)
                                +" "+minValue+" - "+maxValue);
                    }else {
                        priceValue.setText(getActivity().getResources().getString(R.string.Rs)
                                +" "+minVal+" - "+maxVal);
                    }*/

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

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void notifyFlight(int position, Boolean selected) {
        if(selected){
            busOprList.get(position).setSelected(false);
        }else {
            busOprList.get(position).setSelected(true);
        }

        opratorListAdapter.notifyDataSetChanged();
    }


}
