package com.TripToEasy.traveller.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.TravellerModel;
import com.TripToEasy.traveller.TravellerFragment;

import java.util.List;

/**
 * Created by ptblr-1195 on 17/4/18.
 */

public class ChildAdapter extends CommonRecyclerAdapter {

    Activity activity;
    TravellerFragment travellerFragment;
    List<TravellerModel> childList;

    public ChildAdapter(Activity activity,
                        TravellerFragment travellerFragment,
                        List<TravellerModel> childList) {
        this.activity = activity;
        this.travellerFragment = travellerFragment;
        this.childList = childList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonViewHolder(viewGroup, R.layout.traveller_item) {
            @Override
            public void onItemSelected(int position) {

            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder viewHolder, final int i) {
        View view = viewHolder.getView();
        TextView nameText = view.findViewById(R.id.name_text);
        ImageView editName = view.findViewById(R.id.edit_user);
        CheckBox isSelected = view.findViewById(R.id.is_selected);

        nameText.setText(childList.get(i).getFirstName()+" "
                +childList.get(i).getLastName());
        if(childList.get(i).getIsSelected() == 1){
            isSelected.setChecked(false);
        }else {
            isSelected.setChecked(true);
        }

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                travellerFragment.editChild(childList.get(i).getId(),i);
            }
        });

        isSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                travellerFragment.childSelection(i, childList.get(i).getIsSelected());
            }
        });
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }
}
