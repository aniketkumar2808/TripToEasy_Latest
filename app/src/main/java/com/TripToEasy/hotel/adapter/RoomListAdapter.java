package com.TripToEasy.hotel.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.hotel.fragment.HotelDetailFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.hotel.roomList.RoomListModel;
import com.TripToEasy.utils.Global;

import java.util.List;

/**
 * Created by ptblr-1195 on 27/3/18.
 */

public class RoomListAdapter extends CommonRecyclerAdapter {

    Activity activity;
    HotelDetailFragment hotelDetailFragment;
    List<RoomListModel> roomListModelList;
    LinearLayout bg_ll;
    int row_index=0;
    public RoomListAdapter(Activity activity,
                           HotelDetailFragment hotelDetailFragment,
                           List<RoomListModel> roomListModelList) {
        this.activity = activity;
        this.hotelDetailFragment = hotelDetailFragment;
        this.roomListModelList = roomListModelList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.hotel_room_list_item) {
            @Override
            public void onItemSelected(int position) {
                row_index=position;
                notifyDataSetChanged();
                Global.room_select_flag=1;
                /*for(int i=0;i<roomListModelList.size();i++)
                {
                    View v= viewGroup.getChildAt(i);
                    LinearLayout layout =(LinearLayout)v.findViewById(R.id.bg_ll);
                    if(i==position)
                    {
                       layout.setBackgroundColor(activity.getResources().getColor(R.color.gray));
                    }
                    else
                    {
                        layout.setBackgroundColor(activity.getResources().getColor(R.color.white));
                    }
                }*/

                hotelDetailFragment.updateRoomDetail(
                        roomListModelList.get(position).getRoomName(),
                        roomListModelList.get(position).getPrice(),
                        roomListModelList.get(position).getRefundable(),
                        roomListModelList.get(position).getResultIndex(),
                        roomListModelList.get(position).getCancelPolicy(),
                        roomListModelList.get(position).getToken(),
                        roomListModelList.get(position).getTokenKey());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, final int position)
    {
        View view = viewHolder.getView();
        final TextView roomName, selectRoom, roomPrice, nightCount,tv_cancel_policy,
                refundable, amenityOne, amenityTwo;
        final RelativeLayout roomSelected;
        final LinearLayout buttonIndicator;
        CardView roomcard;

        roomcard=view.findViewById(R.id.card_view);
        roomName = view.findViewById(R.id.room_name);
        selectRoom = view.findViewById(R.id.select_room);
        roomPrice = view.findViewById(R.id.room_price);
        nightCount = view.findViewById(R.id.night_count);
        refundable = view.findViewById(R.id.is_refundable);
        amenityOne = view.findViewById(R.id.amenity_one);
        amenityTwo = view.findViewById(R.id.amenity_two);

        bg_ll = view.findViewById(R.id.bg_ll);

        roomSelected = view.findViewById(R.id.selected_layout);
        buttonIndicator = view.findViewById(R.id.button_indicator);
        tv_cancel_policy=view.findViewById(R.id.cancellation_policy);

        roomName.setText(roomListModelList.get(position).getRoomName());
        /*roomPrice.setText(activity.getResources().getString(R.string.Rs)+
                " "+roomListModelList.get(position).getPrice());*/
        roomPrice.setText(Global.currencySymbol+" "+roomListModelList.get(position).getPrice());

        nightCount.setText("("+roomListModelList.get(position).getNightCount()+" Nights)");

        if(roomListModelList.get(position).getRefundable()){
            refundable.setText("Refundable");
        }else {
            refundable.setText("NotRefundable");
        }

      /*  if(roomListModelList.get(position).getSelected()){
            buttonIndicator.setBackground(activity.getResources().getDrawable(R.drawable.orange_butten));
            roomSelected.setVisibility(View.VISIBLE);
            selectRoom.setVisibility(View.GONE);
        }else {
            buttonIndicator.setBackground(activity.getResources().getDrawable(R.drawable.white_butten));
            selectRoom.setVisibility(View.VISIBLE);
            roomSelected.setVisibility(View.GONE);
        }*/

        if(row_index==position){
            buttonIndicator.setBackground(activity.getResources().getDrawable(R.drawable.orange_butten));
            roomSelected.setVisibility(View.VISIBLE);
            selectRoom.setVisibility(View.GONE);
        }
        else
        {
            buttonIndicator.setBackground(activity.getResources().getDrawable(R.drawable.white_butten));
            selectRoom.setVisibility(View.VISIBLE);
            roomSelected.setVisibility(View.GONE);
        }

        if(roomListModelList.get(position).getAmenityOne() != null &&
                roomListModelList.get(position).getAmenityOne().length() != 0){
            amenityOne.setVisibility(View.VISIBLE);
            amenityOne.setText(roomListModelList.get(position).getAmenityOne());
        }else {
            amenityOne.setVisibility(View.INVISIBLE);
        }

        if(roomListModelList.get(position).getAmenityTwo() != null &&
                roomListModelList.get(position).getAmenityTwo().length() != 0){
            amenityTwo.setVisibility(View.VISIBLE);
            amenityTwo.setText(roomListModelList.get(position).getAmenityTwo());
        }else {
            amenityTwo.setVisibility(View.INVISIBLE);
        }

        tv_cancel_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelPolicyDialog(roomListModelList.get(position).getCancelPolicy());
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomListModelList.size();
    }

    public void openCancelPolicyDialog(String cp){
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.cancel_policy_dialog);
        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.cp);
        text.setText(cp);

        TextView dialogButton = (TextView) dialog.findViewById(R.id.close);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}