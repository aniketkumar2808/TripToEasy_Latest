package com.TripToEasy.hotel.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.activity.MyBookingsActivity;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.hotel.listing.HotelBookHistoryInfo;
import com.TripToEasy.utils.Global;

import java.util.List;

/**
 * Created by ptblr-1174 on 7/6/18.
 */

public class HotelBookingAdapter extends CommonRecyclerAdapter {

    Activity activity;
    MyBookingsActivity myBookingsActivity;
    List<HotelBookHistoryInfo> hotelbookingList;

    public HotelBookingAdapter(Activity activity,
                               MyBookingsActivity myBookingsActivity,
                            List<HotelBookHistoryInfo> hotelbookingList) {
        this.activity = activity;
        this.myBookingsActivity = myBookingsActivity;
        this.hotelbookingList = hotelbookingList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.hotel_booking_history_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        TextView tv_checkin,tv_checkout,tv_bookingid,tv_hotelname,tv_hoteladdress,tv_price,tv_refundable,tv_bookingStatus,tv_cancel;
        tv_checkin=view.findViewById(R.id.check_in_date);
        tv_checkout=view.findViewById(R.id.check_out_date);
        tv_bookingid=view.findViewById(R.id.bookingId);
        tv_hotelname=view.findViewById(R.id.hotelName);
        tv_hoteladdress=view.findViewById(R.id.hotelAddress);
        tv_price=view.findViewById(R.id.totalFare);
        tv_refundable=view.findViewById(R.id.refundable);
        tv_bookingStatus=view.findViewById(R.id.bookingStatus);
        tv_cancel=view.findViewById(R.id.cancel_booking);

        tv_checkin.setText(Global.changeDateFormat(hotelbookingList.get(position).getHotel_check_in()));
        tv_checkout.setText(Global.changeDateFormat(hotelbookingList.get(position).getHotel_check_out()));
        tv_bookingid.setText(hotelbookingList.get(position).getBooking_id());
        tv_hotelname.setText(hotelbookingList.get(position).getHotelName());
        tv_hoteladdress.setText(hotelbookingList.get(position).getHotelAddress());
        tv_price.setText(hotelbookingList.get(position).getCurrency()+" "+hotelbookingList.get(position).getTotal_fare());
        tv_refundable.setText("-");
        tv_bookingStatus.setText(hotelbookingList.get(position).getBookingStatus());
        if(hotelbookingList.get(position).getBookingStatus().equals("BOOKING_CONFIRMED"))
        {
            tv_cancel.setVisibility(View.VISIBLE);
        }else {
            tv_cancel.setVisibility(View.GONE);
        }
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBookingsActivity.cancelHotel(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelbookingList.size();
    }
}

