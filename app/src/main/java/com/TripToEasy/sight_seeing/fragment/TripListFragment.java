package com.TripToEasy.sight_seeing.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.TripToEasy.R;
import com.TripToEasy.main.fragment.BaseFragment;
import com.TripToEasy.model.parsingModel.sightSeeing.TripData;
import com.TripToEasy.sight_seeing.sightseeing_adapters.TripListAdapter;
import com.TripToEasy.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TripListFragment extends BaseFragment implements  TripListAdapter.onBookNow{

    @BindView(R.id.txtTripDetails)
    TextView txtTripDetails;

    @BindView(R.id.rvTrip)
    RecyclerView rvTrip;

    List<TripData> mTripDataList = new ArrayList<>();
    TripListAdapter mTripAdapter;


  /*  @OnClick(R.id.back_button)
    public void goBack() {
        getActivity().onBackPressed();
    }
*/

    @OnClick(R.id.llMain)
    public void mainClick() {
        return;
    }


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_trip_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mTripDataList = (List<TripData>) getArguments().getSerializable("trip_list");
        commonUtils.linearLayout(rvTrip, getContext());
        LoadData();
    }


    private void LoadData()
    {
        if (mTripDataList.size() > 0) {
            mTripAdapter = new TripListAdapter(mTripDataList,this);
            rvTrip.setAdapter(mTripAdapter);
            Date lDate = CommonUtils.convertStrToDate(mTripDataList.get(0).getBookingDate(), "yyyy-MM-dd");
            String lBookingDate = CommonUtils.conDateToStr(lDate, "EEEE dd MMM yyyy");
            rvTrip.setAdapter(mTripAdapter);
            txtTripDetails.setText("Pricing Based on " + mTripDataList.get(0).getTotalPax() + " Adult " + lBookingDate);

        }
    }

    @Override
    public void onBookClicked(int position)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("search_id", getArguments().getInt("search_id"));
        bundle.putString("tokenData", getArguments().getString("tokenData"));
        bundle.putSerializable("sel_data",  mTripDataList.get(position));

        intentAndFragmentService.fragmentDisplay(getActivity(), R.id.main_frame, new TravellerDetails(), bundle, true);

    }
}
