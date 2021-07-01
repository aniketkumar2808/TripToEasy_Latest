package com.TripToEasy.hotel.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.TripToEasy.R;
import com.TripToEasy.hotel.adapter.roomGuest.RoomGuestMain;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.hotel.travellerSelection.TravellerSelectionMain;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ptblr-1195 on 22/3/18.
 */

public class GuestRoomFragment extends BaseFragment {

    @BindView(R.id.room_guest_main)
    RecyclerView roomGuestList;

    @SuppressLint("ValidFragment")
    public GuestRoomFragment(HotelSearchFragment hotelSearchFragment,
                             List<TravellerSelectionMain> travellerSelectionMains) {
        this.hotelSearchFragment = hotelSearchFragment;
        this.travellerSelectionMains.addAll(travellerSelectionMains);
    }

    public GuestRoomFragment() {
        /**
         * EMPTY CONSTRUCTOR
         * */
    }

    @OnClick(R.id.add_room)
    void addNewRoom(){
        if(travellerSelectionMains.size() < 3){
            addRoom();
        }else {
            commonUtils.toastShort("You cannot add more than 3 rooms.", getActivity());
        }
    }

    @OnClick(R.id.done_button)
    void doneAction(){
        int i=0;
        Boolean isSuccess = false;
        Integer finalAdultCount = 0;
        Integer finalChildCount = 0;
        Integer finalRoomCount = (travellerSelectionMains.size());

        while (i < travellerSelectionMains.size()){
            finalAdultCount = finalAdultCount + travellerSelectionMains.get(i).getAdultValue();
            finalChildCount = finalChildCount + travellerSelectionMains.get(i).getChildValue();

            Integer childCount = travellerSelectionMains.get(i).getChildValue();
            switch (childCount){
                case 0:
                    isSuccess = true;
                    break;
                case 1:
                    if(travellerSelectionMains.get(i).getChildAgeOne().equals("0")){
                        isSuccess = false;
                        break;
                    }else {
                        isSuccess = true;
                    }
                    break;
                case 2:
                    if(travellerSelectionMains.get(i).getChildAgeOne().equals("0") ||
                            travellerSelectionMains.get(i).getChildAgeTwo().equals("0")){
                        isSuccess = false;
                        break;
                    }else {
                        isSuccess = true;
                    }
                    break;
            }
            i++;
        }

        if(isSuccess){
            hotelSearchFragment.updateTravellerCount(finalAdultCount, finalChildCount, finalRoomCount, travellerSelectionMains);
            getActivity().onBackPressed();
        }else {
            commonUtils.toastShort("Please enter all the selected Children age to proceed.", getActivity());
        }
    }
    List<TravellerSelectionMain> travellerSelectionMains =
            new ArrayList<TravellerSelectionMain>();
    RoomGuestMain roomGuestMain;
    HotelSearchFragment hotelSearchFragment;

    @Override
    protected int getLayoutResource() {
        return R.layout.room_guest_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commonUtils.linearLayout(roomGuestList,getActivity());
        roomGuestMain = new RoomGuestMain(getActivity(),
                GuestRoomFragment.this,
                travellerSelectionMains);
        roomGuestList.setAdapter(roomGuestMain);

        if(travellerSelectionMains.size() == 0){
            travellerSelectionMains.clear();
            addRoom();
        }
    }

    private void addRoom() {
        travellerSelectionMains.add(new TravellerSelectionMain(1,
                0, "0","0"));
        roomGuestMain.notifyDataSetChanged();
    }

    public void updateTravellerValue(int position, int adultCount, int actionType) {
        switch (actionType){
            case 1:
                travellerSelectionMains.get(position).setAdultValue(adultCount);
                break;
            case 2:
                travellerSelectionMains.get(position).setChildValue(adultCount);
                switch (adultCount){
                    case 0:
                        travellerSelectionMains.get(position).setChildAgeOne("0");
                        travellerSelectionMains.get(position).setChildAgeTwo("0");
                        break;
                    case 1:
                        travellerSelectionMains.get(position).setChildAgeTwo("0");
                        break;
                }
                break;
        }
        roomGuestMain.notifyDataSetChanged();
    }

    public void removeView(int position) {
        travellerSelectionMains.remove(position);
        roomGuestMain.notifyDataSetChanged();
    }

    public void updateChildAge(int position, int childOnePosition, String ageValue) {
        switch (childOnePosition){
            case 0:
                travellerSelectionMains.get(position).setChildAgeOne(ageValue);
                break;
            case 1:
                travellerSelectionMains.get(position).setChildAgeTwo(ageValue);
                break;
        }
        roomGuestMain.notifyDataSetChanged();
    }


}