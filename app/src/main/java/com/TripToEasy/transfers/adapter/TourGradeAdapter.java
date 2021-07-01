package com.TripToEasy.transfers.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.TripToEasy.R;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.transfers.TourGradeTrips;
import com.TripToEasy.transfers.fragment.SelectTourgradeFragment;
import com.TripToEasy.utils.Global;

import java.util.List;



public class TourGradeAdapter extends CommonRecyclerAdapter {
    int row_index=0;
    Activity activity;
    SelectTourgradeFragment tourgradeFragment;
    List<TourGradeTrips> transfersList;

    public TourGradeAdapter(Activity activity,
                            SelectTourgradeFragment tourgradeFragment,
                            List<TourGradeTrips> transfersList) {
        this.activity = activity;
        this.tourgradeFragment = tourgradeFragment;
        this.transfersList = transfersList;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, final int i) {
        return new CommonViewHolder(parent, R.layout.select_transfer_item) {
            @Override
            public void onItemSelected(int position) {
                row_index=position;
                notifyDataSetChanged();
                tourgradeFragment.updateTourGrade(transfersList.get(position).getTourUniqueId(),
                                                  transfersList.get(position).getGradeTitle(),
                                                  transfersList.get(position).getGradeCode(),
                                                  transfersList.get(position).getGradeDescription(),
                                                  transfersList.get(position).getNetFare());
            }
        };
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int i) {
        View view = holder.getView();
        final RelativeLayout roomSelected;
        final LinearLayout buttonIndicator;
        TextView transferName = view.findViewById(R.id.transfer_name);
        TextView transfer_code = view.findViewById(R.id.transfer_code);
        TextView description = view.findViewById(R.id.description);
        TextView tv_amount = view.findViewById(R.id.tv_amount);
        TextView travellers = view.findViewById(R.id.travellers);
        TextView language = view.findViewById(R.id.language);
        TextView selectRoom = view.findViewById(R.id.select_room);
        roomSelected = view.findViewById(R.id.selected_layout);
        buttonIndicator = view.findViewById(R.id.button_indicator);

        transferName.setText(transfersList.get(i).getGradeTitle());
        transfer_code.setText(transfersList.get(i).getGradeCode());
        description.setText(transfersList.get(i).getGradeDescription());
        tv_amount.setText(Global.currencySymbol+" "+String.format("%.2f",(Double.parseDouble(transfersList.get(i).getNetFare())/Double.parseDouble(Global.currencyValue))));

        travellers.setText(transfersList.get(i).getTotalPax());
        language.setText(transfersList.get(i).getLangServices());

        if(row_index==i){
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
    }

    @Override
    public int getItemCount() {
        return transfersList.size();
    }

}
