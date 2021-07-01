package com.TripToEasy.sight_seeing.sightseeing_adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.TripToEasy.R;

import java.util.ArrayList;

public class SightseeingCityAdapter  extends ArrayAdapter {
    private final Activity context;
    private ArrayList<String> data = null;


    public SightseeingCityAdapter(Activity context, int resource , ArrayList<String> data ) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, final ViewGroup parent) {

        ViewHolder viewholder = null;
        if (view == null) {
            viewholder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.city_list_item, null, true);
            viewholder.cityName = (TextView) view.findViewById(R.id.place_name);
            viewholder.cityName.setText(data.get(position));
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }

       /* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusClickInterface.onItemClicked(position);
            }
        });*/


        return view;
    }

    static class ViewHolder {
        TextView cityName;
    }
}
