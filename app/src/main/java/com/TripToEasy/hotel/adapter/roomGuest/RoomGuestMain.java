package com.TripToEasy.hotel.adapter.roomGuest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.hotel.fragment.GuestRoomFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.hotel.travellerSelection.TravellerSelectionMain;
import com.TripToEasy.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptblr-1195 on 22/3/18.
 */

public class RoomGuestMain extends CommonRecyclerAdapter {

    Activity activity;
    GuestRoomFragment guestRoomFragment;
    List<TravellerSelectionMain> travellerSelectionMains;
    CommonUtils commonUtils;

    public RoomGuestMain(Activity activity,
                         GuestRoomFragment guestRoomFragment,
                         List<TravellerSelectionMain> travellerSelectionMains) {
        this.activity = activity;
        this.guestRoomFragment = guestRoomFragment;
        this.travellerSelectionMains = travellerSelectionMains;
        commonUtils = new CommonUtils();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.room_guest_list_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        TextView adultOne, adultTwo, adultThree, adultFour,
                childZero, childOne, childThree, roomCountValue;
        LinearLayout aOne, aTwo, aThree, aFour, cZero, cOne, cTwo;
        RecyclerView childListOne;
        ImageView closeView;

        List<String> age = new ArrayList<>();
        List<String> ageValue = new ArrayList<>();

        adultOne = view.findViewById(R.id.adult_one);
        adultTwo = view.findViewById(R.id.adult_two);
        adultThree = view.findViewById(R.id.adult_three);
        adultFour = view.findViewById(R.id.adult_four);

        childZero = view.findViewById(R.id.child_zero);
        childOne = view.findViewById(R.id.child_one);
        childThree = view.findViewById(R.id.child_two);

        roomCountValue = view.findViewById(R.id.room_count);

        aOne = view.findViewById(R.id.count_one);
        aTwo = view.findViewById(R.id.count_two);
        aThree = view.findViewById(R.id.count_three);
        aFour = view.findViewById(R.id.count_four);
        cZero = view.findViewById(R.id.child_count_zero);
        cOne = view.findViewById(R.id.child_count_one);
        cTwo = view.findViewById(R.id.child_count_two);

        closeView = view.findViewById(R.id.close_view);
        if(position == 0){
            closeView.setVisibility(View.GONE);
        }else {
            closeView.setVisibility(View.VISIBLE);
        }

        childListOne = view.findViewById(R.id.child_age_list);
        commonUtils.linearLayout(childListOne, activity);

        roomCountValue.setText("Room "+(position+1));

        switch (travellerSelectionMains.get(position).getAdultValue()){
            case 1:
                adultOne.setBackground(activity.getResources()
                        .getDrawable(R.drawable.ic_selection_indicator));
                adultOne.setTextColor(activity.getResources()
                        .getColor(R.color.blue_text));

                adultTwo.setBackground(null);
                adultTwo.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultThree.setBackground(null);
                adultThree.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultFour.setBackground(null);
                adultFour.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                break;
            case 2:
                adultTwo.setBackground(activity.getResources()
                        .getDrawable(R.drawable.ic_selection_indicator));
                adultTwo.setTextColor(activity.getResources()
                        .getColor(R.color.blue_text));

                adultOne.setBackground(null);
                adultOne.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultThree.setBackground(null);
                adultThree.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultFour.setBackground(null);
                adultFour.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                break;
            case 3:
                adultThree.setBackground(activity.getResources()
                        .getDrawable(R.drawable.ic_selection_indicator));
                adultThree.setTextColor(activity.getResources()
                        .getColor(R.color.blue_text));

                adultOne.setBackground(null);
                adultOne.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultTwo.setBackground(null);
                adultTwo.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultFour.setBackground(null);
                adultFour.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                break;
            case 4:
                adultFour.setBackground(activity.getResources()
                        .getDrawable(R.drawable.ic_selection_indicator));
                adultFour.setTextColor(activity.getResources()
                        .getColor(R.color.blue_text));

                adultOne.setBackground(null);
                adultOne.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultTwo.setBackground(null);
                adultTwo.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                adultThree.setBackground(null);
                adultThree.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));
                break;
        }

        switch (travellerSelectionMains.get(position).getChildValue()){
            case 0:
                childZero.setBackground(activity.getResources()
                        .getDrawable(R.drawable.ic_selection_indicator));
                childZero.setTextColor(activity.getResources()
                        .getColor(R.color.blue_text));

                childOne.setBackground(null);
                childOne.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                childThree.setBackground(null);
                childThree.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                break;
            case 1:
                childOne.setBackground(activity.getResources()
                        .getDrawable(R.drawable.ic_selection_indicator));
                childOne.setTextColor(activity.getResources()
                        .getColor(R.color.blue_text));

                childZero.setBackground(null);
                childZero.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                childThree.setBackground(null);
                childThree.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                break;
            case 2:
                childThree.setBackground(activity.getResources()
                        .getDrawable(R.drawable.ic_selection_indicator));
                childThree.setTextColor(activity.getResources()
                        .getColor(R.color.blue_text));

                childZero.setBackground(null);
                childZero.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                childOne.setBackground(null);
                childOne.setTextColor(activity.getResources()
                        .getColor(R.color.rating_not_selected));

                break;
        }

        if(travellerSelectionMains.get(position).getChildValue() != 0){
            int i=1;

            if(travellerSelectionMains.get(position).getChildValue() == 1){
                ageValue.add(travellerSelectionMains.get(position).getChildAgeOne());
            }else {
                ageValue.add(travellerSelectionMains.get(position).getChildAgeOne());
                ageValue.add(travellerSelectionMains.get(position).getChildAgeTwo());
            }

            while (i <= travellerSelectionMains.get(position).getChildValue()){
                age.add("Age of Child "+i);
                i++;
            }

            RoomGuestChildOne roomGuestChildOne =
                    new RoomGuestChildOne(activity, guestRoomFragment,
                            position,age, ageValue);
            childListOne.setAdapter(roomGuestChildOne);
        }else {
            RoomGuestChildOne roomGuestChildOne =
                    new RoomGuestChildOne(activity, guestRoomFragment,
                            position,age, ageValue);
            childListOne.setAdapter(roomGuestChildOne);
        }

        // Action type 3rd param is to get is it from adult or child in fragment
        // 1 Adult
        // 2 Child
        aOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.updateTravellerValue(position,
                        1, 1);
            }
        });

        aTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.updateTravellerValue(position,
                        2, 1);
            }
        });

        aThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.updateTravellerValue(position,
                        3, 1);
            }
        });

        aFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.updateTravellerValue(position,
                        4, 1);
            }
        });

        cZero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.updateTravellerValue(position,
                        0, 2);
            }
        });

        cOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.updateTravellerValue(position,
                        1, 2);
            }
        });

        cTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.updateTravellerValue(position,
                        2, 2);
            }
        });

        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guestRoomFragment.removeView(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return travellerSelectionMains.size();
    }

}