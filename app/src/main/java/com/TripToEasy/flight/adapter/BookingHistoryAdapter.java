package com.TripToEasy.flight.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.activity.MyBookingsActivity;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.flight.BookingHistoryInfo;
import com.TripToEasy.utils.Global;

import java.util.List;

public class BookingHistoryAdapter extends CommonRecyclerAdapter {

    Activity activity;
    MyBookingsActivity bookHistoryActivity;
    List<BookingHistoryInfo> bookingLists;


    public BookingHistoryAdapter(Activity activity,
                                 MyBookingsActivity bookHistoryActivity,
                                 List<BookingHistoryInfo> bookingLists) {
        this.activity = activity;
        this.bookHistoryActivity = bookHistoryActivity;
        this.bookingLists = bookingLists;

    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.flight_booking_history_item) {
            @Override
            public void onItemSelected(int position) {
                //bookHistoryFragment.goToDetail(position);
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position) {
        View view = holder.getView();
        ImageView airlineImage;
        TextView tv_journey_date,tv_pnr,tv_operator,tv_depart_time,tv_arrive_time,tv_amout,tv_trip_type,tv_status,tv_arrive_city,tv_depart_city,tv_cancel;
        tv_journey_date=view.findViewById(R.id.journey_date);
        tv_pnr=view.findViewById(R.id.bookingId);
        airlineImage=view.findViewById(R.id.airline_image);
        tv_depart_time=view.findViewById(R.id.departure_time);
        tv_depart_city=view.findViewById(R.id.depart_city);
        tv_arrive_time=view.findViewById(R.id.arrival_time);
        tv_arrive_city=view.findViewById(R.id.arrival_city);
        tv_amout=view.findViewById(R.id.totalFare);
        tv_trip_type=view.findViewById(R.id.trip_type);
        tv_status=view.findViewById(R.id.bookingStatus);
        tv_cancel=view.findViewById(R.id.cancel_booking);


        tv_journey_date.setText(Global.changeDateFormat(bookingLists.get(position).getJourney_start()));
        tv_pnr.setText(bookingLists.get(position).getPnr());
        tv_depart_time.setText(Global.changeTimeFormat(bookingLists.get(position).getJourney_start()));
        tv_depart_city.setText(bookingLists.get(position).getFrom_loc());
        tv_arrive_time.setText(Global.changeTimeFormat(bookingLists.get(position).getJourney_end()));
        tv_arrive_city.setText(bookingLists.get(position).getTo_loc());
        tv_trip_type.setText(bookingLists.get(position).getTrip_type().toUpperCase());
        tv_status.setText(bookingLists.get(position).getStatus());

        if(bookingLists.get(position).getStatus().equals("BOOKING_CONFIRMED")){
            tv_cancel.setVisibility(View.VISIBLE);
        }else {
            tv_cancel.setVisibility(View.GONE);
        }

        tv_amout.setText(bookingLists.get(position).getCurrency()+" "+bookingLists.get(position).getTotal_fare());

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookHistoryActivity.cancelFlight(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingLists.size();
    }

 /*   public String convertDateFormat(String dateString){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = null;
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
            System.out.println(dateString);
            formattedDate = targetFormat.format(convertedDate);
        } catch (ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return formattedDate;
    }*/

    /*public String changeTimeFormat(String departureDate){//2018-05-31 05:00:00
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try{
            Date endDate = dateFormat.parse(departureDate);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm",Locale.ENGLISH);
            departureDate = sdf.format(endDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return departureDate;
    }*/
}
