package com.TripToEasy.hotel.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.TripToEasy.R;
import com.TripToEasy.hotel.fragment.HotelListFragment;
import com.TripToEasy.main.adapter.common.CommonRecyclerAdapter;
import com.TripToEasy.main.adapter.common.CommonViewHolder;
import com.TripToEasy.model.parsingModel.hotel.listing.HotelListingMain;
import com.TripToEasy.utils.Global;
import java.util.ArrayList;
import java.util.List;


public class HotelListAdapter extends CommonRecyclerAdapter  implements Filterable {
    Activity activity;
    HotelListFragment hotelListFragment;
    List<HotelListingMain> hotelListing,dup_al;
    HotelSearchFilter myfilter;

    public HotelListAdapter(Activity activity,
                            HotelListFragment hotelListFragment,
                            List<HotelListingMain> hotelListing) {
        this.activity = activity;
        this.hotelListFragment = hotelListFragment;
        this.hotelListing = hotelListing;
        this.dup_al = hotelListing;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommonViewHolder(parent, R.layout.hotel_search_list_item) {
            @Override
            public void onItemSelected(int position) {
                hotelListFragment.navigateDetail(
                        hotelListing.get(position).getToken(),
                        hotelListing.get(position).getBookingSource(),
                        hotelListing.get(position).getSearchId(),
                        hotelListing.get(position).getHotelResultIndex(),
                        hotelListing.get(position).getHotelCode());
            }
        };
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        View view = holder.getView();
        TextView hotelName, hotelAddress, hotelPrice;
        ImageView hotelImage, wifi, breakfast, parking, pool;
        RatingBar hotelRating;

        hotelName = view.findViewById(R.id.hotel_name);
        hotelAddress = view.findViewById(R.id.hotel_address);
        hotelPrice = view.findViewById(R.id.price_text);
        hotelRating = view.findViewById(R.id.hotel_rating);
        hotelImage = view.findViewById(R.id.hotel_image);

        wifi = view.findViewById(R.id.wifi);
        breakfast = view.findViewById(R.id.breakfast);
        parking = view.findViewById(R.id.parking);
        pool = view.findViewById(R.id.pool);

        if(hotelListing.get(position).getWifi()){
            wifi.setVisibility(View.VISIBLE);
        }else {
            wifi.setVisibility(View.GONE);
        }

        if(hotelListing.get(position).getBreakFast()){
            breakfast.setVisibility(View.VISIBLE);
        }else {
            breakfast.setVisibility(View.GONE);
        }

        if(hotelListing.get(position).getParking()){
            parking.setVisibility(View.VISIBLE);
        }else {
            parking.setVisibility(View.GONE);
        }

        if(hotelListing.get(position).getPool()){
            pool.setVisibility(View.VISIBLE);
        }else {
            pool.setVisibility(View.GONE);
        }

        if(hotelListing.get(position).getHotelImage().length() != 0){
            Picasso.get()
                    .load(hotelListing.get(position).getHotelImage())
                    .placeholder(R.drawable.hotel_placeholder)
                    .error(R.drawable.hotel_placeholder)
                    .into(hotelImage);
        }else {
            hotelImage.setImageDrawable(activity.getResources()
                    .getDrawable(R.drawable.hotel_placeholder));
        }

        hotelName.setText(hotelListing.get(position).getHotelName());
        hotelAddress.setText(hotelListing.get(position).getHotelAddress());
       /* hotelPrice.setText(activity.getResources().getString(R.string.Rs)+" "
                +hotelListing.get(position).getHotelPrice());*/
        hotelPrice.setText(Global.currencySymbol+" "
                +hotelListing.get(position).getHotelPrice());
        hotelRating.setRating(Float.parseFloat(String.valueOf(
                hotelListing.get(position).getHotelRating())));
    }

    @Override
    public int getItemCount() {
        return hotelListing.size();
    }

    @Override
    public Filter getFilter() {
        if (myfilter == null) {
            myfilter = new HotelListAdapter.HotelSearchFilter();
        }
        return myfilter;
    }

    public class HotelSearchFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString();

            ArrayList<HotelListingMain> allitems = new ArrayList<>();

            FilterResults filterresults = new FilterResults();

            if (constraint != null && constraint.toString().length() > 0)
            {
                //  responseList.body().getData().getHotelSearchResult().getHotelResults().clear();
                Log.e("filter dup aft...",""+dup_al.size());

                for (int i = 0; i < dup_al.size(); i++)
                {
                    if (dup_al.get(i).getHotelName().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        // responseList.body().getData().getHotelSearchResult().getHotelResults().add(duplicatelist.body().getData().getHotelSearchResult().getHotelResults().get(i));
                        allitems.add(dup_al.get(i));
                    }
                }

                filterresults.count = allitems.size();
                filterresults.values = allitems;

            } else {

                System.out.println(" iam here..." + dup_al);

                synchronized (this) {
                    filterresults.count = dup_al.size();
                    filterresults.values = dup_al;
                }

            }

            return filterresults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            hotelListing = (ArrayList<HotelListingMain>) filterResults.values;
            if(hotelListing.size()>0)
            {
                HotelListFragment.hotelListing=hotelListing;
                HotelListAdapter.this.notifyDataSetChanged();
            }
        }
    }
}