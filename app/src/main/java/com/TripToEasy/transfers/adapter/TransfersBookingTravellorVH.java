package com.TripToEasy.transfers.adapter;

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

public class TransfersBookingTravellorVH extends RecyclerView.ViewHolder {

    public Spinner titleSelection,typeSelection;
    public EditText firstName,lastName;
    Activity context = null;

    public TransfersBookingTravellorVH(View itemView,
                                       final Activity context) {
        super(itemView);
        this.context = context;
        titleSelection = (Spinner) itemView.findViewById(R.id.sp_title);
        typeSelection = (Spinner) itemView.findViewById(R.id.sp_type);
        ArrayAdapter<CharSequence> titleadapter = ArrayAdapter.createFromResource(context, R.array.array_name, android.R.layout.simple_spinner_item);
        titleadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSelection.setAdapter(titleadapter);
        ArrayAdapter<CharSequence> typeadapter = ArrayAdapter.createFromResource(context, R.array.type_array_name, android.R.layout.simple_spinner_item);
        typeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelection.setAdapter(typeadapter);
        firstName = (EditText) itemView.findViewById(R.id.et_fname);
        lastName = (EditText) itemView.findViewById(R.id.et_lname);


    }

}
