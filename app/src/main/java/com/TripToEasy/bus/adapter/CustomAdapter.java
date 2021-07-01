package com.TripToEasy.bus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.model.parsingModel.bus.PickUpBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ptblr-1162 on 10/9/16.
 */
public class CustomAdapter extends ArrayAdapter<PickUpBean> {

    public Context mycontext;
    public List<PickUpBean> objects;


    public CustomAdapter(Context context, List<PickUpBean> list)
    {
        super(context, 0, list);
        this.mycontext=context;
        this.objects = list;
    }

    static class ViewHolder{
        TextView titlename;
        TextView timings;
        TextView idtttl;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        final ViewHolder holder;
        if (convertView == null ) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.boarding_lay, null);
            holder.titlename = (TextView)convertView.findViewById(R.id.boad_name);
            holder.timings = (TextView)convertView.findViewById(R.id.boad_timings);
            holder.idtttl = (TextView)convertView.findViewById(R.id.boad_id);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        PickUpBean deting = objects.get(position);
        if(deting != null){
            holder.titlename.setText(deting.getPickupname().trim()+" - "+ getHourMin(deting.getPickuptime()).trim());
            //	holder.timings.setText(SeatingArrange.getHourMin(deting.getPickuptime()).trim());

            //	System.out.println("seating hour is................"+SeatingArrange.getHourMin(deting.getPickuptime()).trim());

            holder.idtttl.setText(deting.getPickupid());


        }


        return convertView;
    }

    public static String getHourMin(String example){
        example = example.replace("T"," ");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date date = format.parse(example);
            SimpleDateFormat format11 = new SimpleDateFormat("hh:mm a");
            String rightdate = format11.format(date);
            return rightdate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}