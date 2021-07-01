package com.TripToEasy.bus.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.main.activity.MyBookingsActivity;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.bus.BusBookHistoryInfo;
import com.TripToEasy.utils.Global;
import java.util.List;

public class BusBookingAdapter extends CommonRecyclerAdapter
{

    Activity activity;
    MyBookingsActivity myBookingsActivity;
    List<BusBookHistoryInfo> busbookingList;

    public BusBookingAdapter(Activity activity,MyBookingsActivity myBookingsActivity, List<BusBookHistoryInfo> busbookingList)
    {
        this.activity = activity;
        this.myBookingsActivity = myBookingsActivity;
        this.busbookingList = busbookingList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        return new CommonViewHolder(viewGroup, R.layout.bus_booking_history_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, final int position)
    {
        View view = holder.getView();
        TextView tv_cancel,tv_journey_date,tv_pnr,tv_operator,tv_depart_time,tv_arrive_time,tv_amout,tv_cancel_policy,tv_status,
                tv_arrive_city,tv_depart_city;
        tv_journey_date=view.findViewById(R.id.journey_date);
        tv_pnr=view.findViewById(R.id.bookingId);
        tv_operator=view.findViewById(R.id.busOperator);
        tv_depart_time=view.findViewById(R.id.departure_time);
        tv_depart_city=view.findViewById(R.id.depart_city);
        tv_arrive_time=view.findViewById(R.id.arrival_time);
        tv_arrive_city=view.findViewById(R.id.arrival_city);
        tv_amout=view.findViewById(R.id.totalFare);
        tv_cancel_policy=view.findViewById(R.id.refundable);
        tv_status=view.findViewById(R.id.bookingStatus);
        tv_cancel=view.findViewById(R.id.cancel_booking);
        tv_journey_date.setText(Global.changeDateFormat(busbookingList.get(position).getJourney_datetime()));
        tv_pnr.setText(busbookingList.get(position).getPnr());
        tv_operator.setText(busbookingList.get(position).getOperator());
        tv_depart_time.setText(Global.changeTimeFormat(busbookingList.get(position).getDeparture_datetime()));
        tv_depart_city.setText(busbookingList.get(position).getDeparture_from());
        tv_arrive_time.setText(busbookingList.get(position).getArrival_datetime());
        tv_arrive_city.setText(busbookingList.get(position).getArrival_to());
        tv_amout.setText(busbookingList.get(position).getCurrency()+" "+busbookingList.get(position).getFare());
        tv_cancel_policy.setText("-");
        tv_status.setText(busbookingList.get(position).getStatus());
        tv_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                myBookingsActivity.cancelBus(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return busbookingList.size();
    }

}

