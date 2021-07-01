package com.TripToEasy.hotel.adapter.roomGuest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.hotel.fragment.GuestRoomFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.utils.CommonUtils;

import java.util.List;

/**
 * Created by ptblr-1195 on 22/3/18.
 */

public class RoomGuestChildTwo extends CommonRecyclerAdapter {

    Activity activity;
    GuestRoomFragment guestRoomFragment;
    int position;
    List<String> ageList;
    String ageValue;
    int childOnePosition;
    CommonUtils commonUtils;

    public RoomGuestChildTwo(Activity activity,
                             GuestRoomFragment guestRoomFragment,
                             int position,
                             List<String> ageList,
                             String ageValue,
                             int childOnePosition) {
        this.activity = activity;
        this.guestRoomFragment = guestRoomFragment;
        this.position = position;
        this.ageList = ageList;
        this.ageValue = ageValue;
        this.childOnePosition = childOnePosition;
        commonUtils = new CommonUtils();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.room_guest_child_two) {
            @Override
            public void onItemSelected(int positionSecond) {
                //TODO : update position and age value
                guestRoomFragment.updateChildAge(position, childOnePosition, ageList.get(positionSecond));
            }
        };
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView ageText = view.findViewById(R.id.age_text);

        if(ageValue.equals(ageList.get(position))){
            ageText.setText(ageList.get(position));
            ageText.setTextColor(activity.getResources().getColor(R.color.white));
            ageText.setBackground(activity.getResources().getDrawable(R.drawable.orange_butten));
        }else {
            ageText.setText(ageList.get(position));
            ageText.setTextColor(activity.getResources().getColor(R.color.black));
            ageText.setBackground(activity.getResources().getDrawable(R.drawable.white_butten));
        }
    }

    @Override
    public int getItemCount() {
        return ageList.size();
    }

}