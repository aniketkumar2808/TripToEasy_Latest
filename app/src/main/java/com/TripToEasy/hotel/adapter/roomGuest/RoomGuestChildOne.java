package com.TripToEasy.hotel.adapter.roomGuest;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.hotel.fragment.GuestRoomFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ptblr-1195 on 22/3/18.
 */

public class RoomGuestChildOne extends CommonRecyclerAdapter {

    Activity activity;
    GuestRoomFragment guestRoomFragment;
    int position;
    List<String> childAge;
    List<String> ageValue;
    CommonUtils commonUtils;

    public RoomGuestChildOne(Activity activity,
                             GuestRoomFragment guestRoomFragment,
                             int position,
                             List<String> childAge,
                             List<String> ageValue) {
        this.activity = activity;
        this.guestRoomFragment = guestRoomFragment;
        this.position = position;
        this.childAge = childAge;
        this.ageValue = ageValue;
        commonUtils = new CommonUtils();
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.room_guest_child_one) {
            @Override
            public void onItemSelected(int positionValue) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView ageValueText;
        RecyclerView ageListView;

        ageValueText = view.findViewById(R.id.age_text);
        ageListView = view.findViewById(R.id.age_list);

        ageValueText.setText(childAge.get(position));
        commonUtils.horizontalLayout(ageListView, activity);


        int j=1;
        List<String> ageList = new ArrayList<String>();
        while (j < 12){
            ageList.add(String.valueOf(j));
            j++;
        }

        RoomGuestChildTwo roomGuestChildTwo =
                new RoomGuestChildTwo(activity, guestRoomFragment,
                        this.position, ageList, ageValue.get(position), position);
        ageListView.setAdapter(roomGuestChildTwo);

    }

    @Override
    public int getItemCount() {
        return childAge.size();
    }
}