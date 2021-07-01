package com.TripToEasy.bus.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.TripToEasy.R;


/**
 * Created by ptblr-1195 on 5/6/17.
 */

public class BusBookingTravellorVH extends RecyclerView.ViewHolder {

    public Spinner titleSelection;
    public EditText travellerName,travellerAge;
    Activity context = null;

    public BusBookingTravellorVH(View itemView,
                                 final Activity context) {
        super(itemView);
        this.context = context;
        titleSelection = (Spinner) itemView.findViewById(R.id.title_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.array_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSelection.setAdapter(adapter);
        travellerName = (EditText) itemView.findViewById(R.id.traveller_name);
        travellerAge = (EditText) itemView.findViewById(R.id.traveller_age);

    }

}
